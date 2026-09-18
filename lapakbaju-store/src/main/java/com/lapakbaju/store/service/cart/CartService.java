package com.lapakbaju.store.service.cart;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.entity.cart.CartItemEntity;
import com.lapakbaju.store.entity.product.Product;
import com.lapakbaju.store.repository.cart.CartRepository;
import com.lapakbaju.store.repository.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public List<CartItemEntity> getCartByUser(UserEntity user) {
        return cartRepository.findByUser(user);
    }

    public int getCartCount(UserEntity user) {
        return cartRepository.findByUser(user).stream()
                .mapToInt(CartItemEntity::getQuantity)
                .sum();
    }

    @Transactional
    public CartItemEntity addToCart(UserEntity user, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getInventory() == null) {
            throw new IllegalArgumentException("Product inventory is not available");
        }

        int stock = product.getInventory().getStockQuantity();

        return cartRepository.findByUserAndProductId(user, productId)
                .map(item -> {
                    int newQuantity = item.getQuantity() + quantity;
                    if (newQuantity > stock) {
                        throw new IllegalArgumentException("Requested quantity exceeds available stock (" + stock + ")");
                    }
                    item.setQuantity(newQuantity);
                    return cartRepository.save(item);
                })
                .orElseGet(() -> {
                    if (quantity > stock) {
                        throw new IllegalArgumentException("Requested quantity exceeds available stock (" + stock + ")");
                    }
                    return cartRepository.save(new CartItemEntity(null, user, product, quantity));
                });
    }

    @Transactional
    public void updateQuantity(UserEntity user, Long cartItemId, int quantity) {
        CartItemEntity item = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));

        if (!item.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Unauthorized action");
        }

        if (quantity <= 0) {
            cartRepository.delete(item);
            return;
        }

        int availableStock = item.getProduct().getInventory() != null
                ? item.getProduct().getInventory().getStockQuantity()
                : 0;

        if (quantity > availableStock) {
            throw new IllegalArgumentException("Requested quantity exceeds available stock (" + availableStock + ")");
        }

        item.setQuantity(quantity);
        cartRepository.save(item);
    }

    @Transactional
    public void processCheckout(UserEntity user) {
        List<CartItemEntity> cartItems = cartRepository.findByUser(user);

        // Deduct inventory stock for each checked out item
        for (CartItemEntity item : cartItems) {
            if (item.getProduct().getInventory() != null) {
                int currentStock = item.getProduct().getInventory().getStockQuantity();
                if (item.getQuantity() > currentStock) {
                    throw new IllegalStateException("Stock limit exceeded for " + item.getProduct().getName());
                }
                item.getProduct().getInventory().setStockQuantity(currentStock - item.getQuantity());
            }
        }

        // Clear cart items for this user from database
        cartRepository.deleteByUser(user);
    }

    @Transactional
    public void clearCart(UserEntity user) {
        cartRepository.deleteByUser(user);
    }
}