package com.lapakbaju.store.repository.user_profile;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.entity.user_profile.UserOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Long> {

    List<UserOrderEntity> findByUserId(Long id);

    List<UserOrderEntity> findByUser(UserEntity user);

    // Projection interface to capture aggregated query results
    interface MonthlyRevenueProjection {
        String getMonthName();
        BigDecimal getTotalRevenue();
    }

    // Custom query to group total price by month and year
    // Note: Adjust DATE_FORMAT / TO_CHAR syntax based on your database (MySQL vs PostgreSQL vs H2)
    @Query(value = """
        SELECT 
            DATE_FORMAT(order_date, '%b %Y') AS monthName, 
            SUM(total_price) AS totalRevenue
        FROM user_order_entity
        GROUP BY YEAR(order_date), MONTH(order_date), DATE_FORMAT(order_date, '%b %Y')
        ORDER BY YEAR(order_date) ASC, MONTH(order_date) ASC
        """, nativeQuery = true)
    List<MonthlyRevenueProjection> findMonthlyRevenueTrend();

    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM UserOrderEntity o")
    BigDecimal findTotalEarning();

    @Query("SELECT COUNT(o) FROM UserOrderEntity o")
    Long findTotalOrders();
}