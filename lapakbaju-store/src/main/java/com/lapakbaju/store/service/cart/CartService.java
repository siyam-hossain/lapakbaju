package com.lapakbaju.store.service.cart;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.entity.cart.CartItemEntity;
import com.lapakbaju.store.entity.product.Product;
import com.lapakbaju.store.entity.user_profile.UserOrderEntity;
import com.lapakbaju.store.repository.cart.CartRepository;
import com.lapakbaju.store.repository.product.ProductRepository;
import com.lapakbaju.store.repository.user_profile.UserOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserOrderRepository userOrderRepository;

    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       UserOrderRepository userOrderRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userOrderRepository = userOrderRepository;
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
    public UserOrderEntity processCheckoutAndPay(UserEntity user, String shippingAddress, String city, String postalCode, String country, String paymentMethod) {
        List<CartItemEntity> cartItems = cartRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cannot checkout with an empty cart");
        }

        // 1. Calculate total price and deduct inventory stock
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItemEntity item : cartItems) {
            int stock = item.getProduct().getInventory() != null
                    ? item.getProduct().getInventory().getStockQuantity()
                    : 0;

            if (item.getQuantity() > stock) {
                throw new IllegalStateException("Insufficient stock for item: " + item.getProduct().getName());
            }

            BigDecimal itemTotal = item.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            totalPrice = totalPrice.add(itemTotal);

            // Deduct stock from inventory
            item.getProduct().getInventory().setStockQuantity(stock - item.getQuantity());
        }

        // 2. Save new order record and link to user
        UserOrderEntity order = new UserOrderEntity();
        order.setUser(user); // Foreign key association
        order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setTotalPrice(totalPrice);
        order.setStatus("PAID");
        order.setOrderDate(LocalDateTime.now());

        UserOrderEntity savedOrder = userOrderRepository.save(order);

        // 3. Clear cart from database
        cartRepository.deleteByUser(user);

        return savedOrder;
    }
}