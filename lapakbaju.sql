-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 30, 2026 at 04:29 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lapakbaju`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart_items`
--

CREATE TABLE `cart_items` (
  `id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cart_items`
--

INSERT INTO `cart_items` (`id`, `quantity`, `product_id`, `user_id`) VALUES
(8, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `bg` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `default_active` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`, `icon`, `bg`, `color`, `default_active`) VALUES
(1, 'All', 'fa-solid fa-border-all', '#f3f4f6', '#1f2937', 1),
(2, 'Men Clothing', 'fa-solid fa-shirt', '#e0f2fe', '#0284c7', 0),
(3, 'Women Clothing', 'fa-solid fa-person-dress', '#fce7f3', '#db2777', 0),
(4, 'Footwear', 'fa-solid fa-shoe-prints', '#fef3c7', '#d97706', 0),
(5, 'Jackets & Outerwear', 'fa-solid fa-vest', '#e0e7ff', '#4f46e5', 0),
(6, 'Accessories', 'fa-solid fa-glasses', '#d1fae5', '#059669', 0),
(7, 'Bags & Luggage', 'fa-solid fa-bag-shopping', '#fae8ff', '#c026d3', 0),
(8, 'Sales & Discounts', 'fa-solid fa-tags', '#fee2e2', '#dc2626', 0),
(9, 'Accessories', 'fa-solid fa-clock', '#ffffff', '#000000', 1),
(10, 'Cartoon', 'fa-bomb', '#f3f4f6', '#1f2937', 0),
(11, 'Gaming Accessories', 'fa-gamepad', '#E8E4FF', '#3B17CD', 0),
(12, 'Smartwatches', 'fa-clock', '#E8E4FF', '#3B17CD', 0),
(13, 'Audio', 'fa-headphones', '#E8E4FF', '#3B17CD', 0),
(14, 'Cameras', 'fa-camera', '#FFF4CC', '#B8860B', 0),
(15, 'Men\'s Clothing', 'fa-shirt', '#F2F2F2', '#351C14', 0);

-- --------------------------------------------------------

--
-- Table structure for table `flyway_schema_history`
--

CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `flyway_schema_history`
--

INSERT INTO `flyway_schema_history` (`installed_rank`, `version`, `description`, `type`, `script`, `checksum`, `installed_by`, `installed_on`, `execution_time`, `success`) VALUES
(1, '1', 'create category', 'SQL', 'V1__create_category.sql', -912458185, 'root', '2026-09-17 01:37:31', 12, 1),
(2, '2', 'insert category', 'SQL', 'V2__insert_category.sql', 988429753, 'root', '2026-09-17 01:37:31', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `inventories`
--

CREATE TABLE `inventories` (
  `id` bigint(20) NOT NULL,
  `recorder_threshold` int(11) DEFAULT NULL,
  `stock_quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inventories`
--

INSERT INTO `inventories` (`id`, `recorder_threshold`, `stock_quantity`) VALUES
(1, 5, 20),
(3, 10, 24),
(4, 10, 18),
(5, 10, 31),
(6, 10, 23),
(7, 10, 20),
(8, 10, 11),
(9, 10, 30),
(10, 10, 24),
(11, 10, 28);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `full_description` text DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `is_featured` bit(1) DEFAULT NULL,
  `is_hot` bit(1) DEFAULT NULL,
  `is_new` bit(1) DEFAULT NULL,
  `name` varchar(150) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `short_description` text DEFAULT NULL,
  `sku` varchar(50) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `image_id` int(11) DEFAULT NULL,
  `inventory_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `created_at`, `full_description`, `is_active`, `is_featured`, `is_hot`, `is_new`, `name`, `price`, `short_description`, `sku`, `updated_at`, `category_id`, `image_id`, `inventory_id`) VALUES
(1, '2026-09-17 07:38:28.000000', 'High quality leather strap wristwatch with modern design.', b'1', b'1', b'1', b'1', 'Classic Leather Watch', 199.99, 'Elegant minimalist wristwatch.', 'WATCH-0001', '2026-09-20 13:23:19.000000', 1, 1, 1),
(3, '2026-09-30 07:32:00.000000', 'The PlayStation 5 DualSense Wireless Controller provides immersive gaming with advanced haptic feedback, adaptive triggers, a built-in microphone, motion controls, and a comfortable ergonomic design.', b'1', b'0', b'0', b'1', 'PlayStation 5 DualSense Wireless Controller', 69.99, 'Wireless controller with immersive haptic feedback and adaptive triggers.', 'SKU-5008', '2026-09-30 07:38:55.000000', 11, 3, 3),
(4, '2026-09-30 07:33:51.000000', 'Apple Watch Series 10 features a refined design, advanced health and fitness tracking, sleep monitoring, heart-rate monitoring, notifications, and seamless integration with iPhone.', b'1', b'0', b'0', b'0', 'Apple Watch Series 10', 399.00, 'Sleek smartwatch with health tracking, fitness features, and smart notifications.', 'SKU-8F3K7M', '2026-09-30 07:39:02.000000', 12, 4, 4),
(5, '2026-09-30 07:35:55.000000', 'Premium wireless headphones featuring active noise cancellation, high-quality audio, Bluetooth connectivity, a comfortable over-ear design, and a long-lasting battery for everyday listening.', b'1', b'0', b'0', b'0', 'Wireless Noise-Cancelling Headphones', 129.99, 'Comfortable wireless headphones with active noise cancellation and rich sound.', 'SKU-6Q9X2L', '2026-09-30 07:39:09.000000', 13, 5, 5),
(6, '2026-09-30 07:38:40.000000', 'Nike Air Max 270 combines a modern athletic design with responsive cushioning and a lightweight construction. Its breathable upper and supportive sole provide comfort for everyday activities, casual outings, and light exercise.', b'1', b'0', b'0', b'1', 'Nike Air Max 270', 150.00, 'Stylish and comfortable Nike sneakers designed for everyday wear.', 'SKU-4R8N6P', '2026-09-30 07:38:40.000000', 4, 6, 6),
(7, '2026-09-30 07:40:54.000000', 'Samsung Galaxy Watch 7 is an Android-compatible smartwatch featuring advanced fitness and health tracking, heart-rate monitoring, sleep tracking, GPS, smart notifications, and a durable design for everyday use.', b'1', b'0', b'0', b'0', 'Samsung Galaxy Watch 7', 299.99, 'Android smartwatch with fitness tracking, health monitoring, and smart notifications.', 'SKU-7M2K9X', '2026-09-30 07:40:54.000000', 12, 7, 7),
(8, '2026-09-30 07:42:27.000000', 'A stylish compact digital camera with a vibrant yellow finish, designed for convenient everyday photography. Its lightweight body makes it easy to carry while capturing photos and videos during travel, events, and daily activities.', b'1', b'0', b'0', b'0', 'Sony Yellow Compact Digital Camera', 449.99, 'Compact yellow Sony camera designed for everyday photography and travel.', 'SKU-5T8Q3Z', '2026-09-30 07:42:27.000000', 14, 8, 8),
(9, '2026-09-30 07:43:58.000000', 'Nike Air Force 1 \'07 features the iconic low-top silhouette, durable construction, cushioned sole, and timeless styling. Designed for everyday casual wear, these sneakers offer a comfortable fit while maintaining Nike\'s classic streetwear aesthetic.', b'1', b'0', b'0', b'0', 'Nike Air Force 1 \'07', 115.00, 'Classic Nike sneakers with a clean design and comfortable everyday fit.', 'SKU-9W4L7C', '2026-09-30 07:43:58.000000', 4, 9, 9),
(10, '2026-09-30 07:45:46.000000', 'A versatile men\'s outfit combo featuring a clean white cotton shirt and classic black denim jeans. Designed for casual outings, everyday wear, and smart-casual occasions, this combination offers a timeless look with comfortable materials and easy styling.', b'1', b'0', b'0', b'0', 'White Shirt & Black Denim Combo', 84.99, 'Classic white shirt paired with stylish black denim jeans for a complete casual outfit.', 'SKU-7Q4M8T', '2026-09-30 07:45:46.000000', 15, 10, 10),
(11, '2026-09-30 07:47:19.000000', 'A versatile men\'s outfit combo featuring a rich maroon shirt and comfortable black denim pants. Perfect for casual outings, dinners, social events, and everyday wear, offering a modern look that is easy to style with sneakers or casual shoes.', b'1', b'0', b'0', b'0', 'Maroon Shirt & Black Denim Pant Combo', 89.99, 'Stylish maroon shirt paired with classic black denim pants for a modern casual look.', 'SKU-5N8R2K', '2026-09-30 07:47:19.000000', 15, 11, 11);

-- --------------------------------------------------------

--
-- Table structure for table `products_tags`
--

CREATE TABLE `products_tags` (
  `product_id` bigint(20) NOT NULL,
  `tags_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `product_images`
--

CREATE TABLE `product_images` (
  `id` int(11) NOT NULL,
  `image_url` varchar(255) NOT NULL,
  `is_main` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product_images`
--

INSERT INTO `product_images` (`id`, `image_url`, `is_main`) VALUES
(1, '5835ca77-a0d8-4ab4-8fcd-8ee3c06fd089.jpg', b'1'),
(3, '98873813-0633-4c35-8144-c04119692d5f.jpg', b'1'),
(4, 'b14ace92-5357-4503-b898-4297f6f51328.jpg', b'1'),
(5, '485d92b9-aeae-4bdb-a31d-11310b6b2737.jpg', b'1'),
(6, '4a065252-eaa9-4766-a252-35d3dc737dd4.jpg', b'1'),
(7, '4245f6be-cbd0-4c2b-bccd-8771b0eea92f.jpg', b'1'),
(8, 'eb26d02a-8283-4a67-85b1-7f40e8047529.jpg', b'1'),
(9, '96848492-efb5-4dd6-b41f-946e3d08e66e.jpg', b'1'),
(10, '486fbb59-3529-42e2-b95b-7769fd9e07a3.jpg', b'1'),
(11, '04b4e89e-39b2-45d0-bd1c-3a030ced59be.jpg', b'1');

-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

CREATE TABLE `tags` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_address_entity`
--

CREATE TABLE `user_address_entity` (
  `id` bigint(20) NOT NULL,
  `address_line` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_address_entity`
--

INSERT INTO `user_address_entity` (`id`, `address_line`, `city`, `country`, `postal_code`) VALUES
(1, 'DSCC 11/14', 'Dhaka', 'Bangladesh', '1361'),
(2, 'Ideal Road', 'Dhaka', 'Bangladesh', '1221'),
(3, 'DSCC 123', 'Dhaka', 'Bangladesh', '1254');

-- --------------------------------------------------------

--
-- Table structure for table `user_entity`
--

CREATE TABLE `user_entity` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `address_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_entity`
--

INSERT INTO `user_entity` (`id`, `email`, `name`, `password`, `profile_image`, `role`, `address_id`) VALUES
(1, 'admin@gmail.com', 'admin', '$2a$10$Es50KI5NeeKri2jkKLgP4eZY8dSaRS6qg3kI7FnvLvX2eSwzzf.Ui', 'c648ea05-c167-467b-a74b-3685b34d23bb.png', 'USER', 1),
(2, 'x@gmail.com', 'x', '$2a$10$N2.rDFAldsgOThKvgsK0Re1jXqJCDBqitPt1j1MtGxp7mBUUvln.K', 'b33f1f87-2972-4e22-83c8-00e5d0ed04c2.jpg', 'ADMIN', 2),
(3, 'a@gmail.com', 'A', '$2a$10$Htm5wSS48GFtJxcjw9rmH.u3FjFbe3yzCgXu8vDs0wQIexRCORamK', 'e019d9a4-0436-405b-ae1e-3d1378be4599.jpg', 'USER', 3);

-- --------------------------------------------------------

--
-- Table structure for table `user_entity_orders`
--

CREATE TABLE `user_entity_orders` (
  `user_entity_id` bigint(20) NOT NULL,
  `orders_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_order_entity`
--

CREATE TABLE `user_order_entity` (
  `id` bigint(20) NOT NULL,
  `order_date` datetime(6) DEFAULT NULL,
  `order_number` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_price` decimal(38,2) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_order_entity`
--

INSERT INTO `user_order_entity` (`id`, `order_date`, `order_number`, `status`, `total_price`, `user_id`) VALUES
(1, '2026-08-18 23:12:05.000000', 'ORD-EEC4BE2B', 'PAID', 399.98, 1),
(2, '2026-07-18 23:26:03.000000', 'ORD-4015183A', 'PAID', 199.99, 1),
(3, '2026-09-19 06:28:56.000000', 'ORD-560EE15A', 'PAID', 199.99, 1),
(5, '2026-01-26 09:38:17.000000', 'ORD-B62CFCA5', 'PAID', 199.99, 1),
(6, '2026-02-25 07:55:46.000000', 'ORD-92954CA8', 'PAID', 199.98, 3),
(7, '2026-03-30 07:55:46.000000', 'ORD-FE5A7DA7', 'PAID', 150.00, 3),
(8, '2026-04-30 07:55:53.000000', 'ORD-02DDBCAD', 'PAID', 84.99, 3),
(9, '2026-05-30 07:56:00.000000', 'ORD-D15B2625', 'PAID', 449.99, 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart_items`
--
ALTER TABLE `cart_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1re40cjegsfvw58xrkdp6bac6` (`product_id`),
  ADD KEY `FKb9itso7lyeoyv3duxqpnixr7q` (`user_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `flyway_schema_history`
--
ALTER TABLE `flyway_schema_history`
  ADD PRIMARY KEY (`installed_rank`),
  ADD KEY `flyway_schema_history_s_idx` (`success`);

--
-- Indexes for table `inventories`
--
ALTER TABLE `inventories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKfhmd06dsmj6k0n90swsh8ie9g` (`sku`),
  ADD UNIQUE KEY `UKaox0mf8fyerjsh7u4evl70r5e` (`image_id`),
  ADD UNIQUE KEY `UKh6o83dhts1gke4vojdaffpmm3` (`inventory_id`),
  ADD KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`);

--
-- Indexes for table `products_tags`
--
ALTER TABLE `products_tags`
  ADD KEY `FK9f89byp75bd6wdttkrcngfu23` (`tags_id`),
  ADD KEY `FKt6jksc02nxg0qutvynpis3lyo` (`product_id`);

--
-- Indexes for table `product_images`
--
ALTER TABLE `product_images`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKt48xdq560gs3gap9g7jg36kgc` (`name`);

--
-- Indexes for table `user_address_entity`
--
ALTER TABLE `user_address_entity`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_entity`
--
ALTER TABLE `user_entity`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK4xad1enskw4j1t2866f7sodrx` (`email`),
  ADD UNIQUE KEY `UKjy98nnfa26nv1twbioppjtpeu` (`address_id`);

--
-- Indexes for table `user_entity_orders`
--
ALTER TABLE `user_entity_orders`
  ADD UNIQUE KEY `UKenkeibsmcd4blsut5v79brvpn` (`orders_id`),
  ADD KEY `FKumym80r2mygxwdx2qsbausrf` (`user_entity_id`);

--
-- Indexes for table `user_order_entity`
--
ALTER TABLE `user_order_entity`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKbpflqjql10r3m9gviwol5go0o` (`order_number`),
  ADD KEY `FKio8kfbn2iwsugnlw0yf9ucqov` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart_items`
--
ALTER TABLE `cart_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `inventories`
--
ALTER TABLE `inventories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `product_images`
--
ALTER TABLE `product_images`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `tags`
--
ALTER TABLE `tags`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_address_entity`
--
ALTER TABLE `user_address_entity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user_entity`
--
ALTER TABLE `user_entity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user_order_entity`
--
ALTER TABLE `user_order_entity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart_items`
--
ALTER TABLE `cart_items`
  ADD CONSTRAINT `FK1re40cjegsfvw58xrkdp6bac6` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `FKb9itso7lyeoyv3duxqpnixr7q` FOREIGN KEY (`user_id`) REFERENCES `user_entity` (`id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `FK6bxocinrsauuylaj9p19jl1h8` FOREIGN KEY (`inventory_id`) REFERENCES `inventories` (`id`),
  ADD CONSTRAINT `FKlt04u5ij71wigiyvaix2o9aki` FOREIGN KEY (`image_id`) REFERENCES `product_images` (`id`),
  ADD CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

--
-- Constraints for table `products_tags`
--
ALTER TABLE `products_tags`
  ADD CONSTRAINT `FK9f89byp75bd6wdttkrcngfu23` FOREIGN KEY (`tags_id`) REFERENCES `tags` (`id`),
  ADD CONSTRAINT `FKt6jksc02nxg0qutvynpis3lyo` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `user_entity`
--
ALTER TABLE `user_entity`
  ADD CONSTRAINT `FK2m3ll5y78dwstw8jbfhc8ak3h` FOREIGN KEY (`address_id`) REFERENCES `user_address_entity` (`id`);

--
-- Constraints for table `user_entity_orders`
--
ALTER TABLE `user_entity_orders`
  ADD CONSTRAINT `FK2rkj4nql6qokc9rp0pr472oci` FOREIGN KEY (`orders_id`) REFERENCES `user_order_entity` (`id`),
  ADD CONSTRAINT `FKumym80r2mygxwdx2qsbausrf` FOREIGN KEY (`user_entity_id`) REFERENCES `user_entity` (`id`);

--
-- Constraints for table `user_order_entity`
--
ALTER TABLE `user_order_entity`
  ADD CONSTRAINT `FKio8kfbn2iwsugnlw0yf9ucqov` FOREIGN KEY (`user_id`) REFERENCES `user_entity` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
