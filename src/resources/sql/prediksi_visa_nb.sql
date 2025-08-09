-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 09, 2025 at 04:13 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `prediksi_visa_nb`
--

-- --------------------------------------------------------

--
-- Table structure for table `data_training`
--

CREATE TABLE `data_training` (
  `id` int(11) NOT NULL,
  `kriteria1` varchar(255) DEFAULT NULL,
  `kriteria2` varchar(255) DEFAULT NULL,
  `kriteria3` varchar(255) DEFAULT NULL,
  `kriteria4` varchar(255) DEFAULT NULL,
  `kriteria5` varchar(255) DEFAULT NULL,
  `label` varchar(20) DEFAULT NULL,
  `pemohon_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `data_training`
--

INSERT INTO `data_training` (`id`, `kriteria1`, `kriteria2`, `kriteria3`, `kriteria4`, `kriteria5`, `label`, `pemohon_id`) VALUES
(21, 'Jelas', 'Ada', '3 bulan terakhir', 'Relevan', 'Jelas', 'Lolos Cepat', 1),
(22, 'Terhalang jari', 'Tidak ada', 'Kurang dari 3 bulan terakhir', 'Tidak relevan', 'Tidak jelas', 'Terhambat', 2),
(23, 'Jelas', 'Ada', '3 bulan terakhir', 'Relevan', 'Tidak jelas', 'Lolos Cepat', 3),
(24, 'Terhalang jari', 'Tidak ada', '3 bulan terakhir', 'Tidak relevan', 'Jelas', 'Terhambat', 4);

-- --------------------------------------------------------

--
-- Table structure for table `pemohon`
--

CREATE TABLE `pemohon` (
  `id` int(11) NOT NULL,
  `nama_pemohon` varchar(100) NOT NULL,
  `no_paspor` varchar(20) NOT NULL,
  `jenis_visa` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pemohon`
--

INSERT INTO `pemohon` (`id`, `nama_pemohon`, `no_paspor`, `jenis_visa`) VALUES
(1, 'Budi Santoso', 'A12345678', 'Turis'),
(2, 'Siti Aminah', 'B98765432', 'Bisnis'),
(3, 'Andi Wijaya', 'C11223344', 'Pelajar'),
(4, 'Rina Susanti', 'D55667788', 'Pekerja');

-- --------------------------------------------------------

--
-- Table structure for table `prediction`
--

CREATE TABLE `prediction` (
  `id` int(11) NOT NULL,
  `penjualan` varchar(100) DEFAULT NULL,
  `absensi` varchar(100) DEFAULT NULL,
  `pelayanan` varchar(100) DEFAULT NULL,
  `kedisiplinan` varchar(100) DEFAULT NULL,
  `lama_kerja` varchar(100) DEFAULT NULL,
  `predicted_label` varchar(50) DEFAULT NULL,
  `lady_yakult_id` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `fullname` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `role_id`, `username`, `email`, `password`, `fullname`, `created_at`, `updated_at`) VALUES
(1, NULL, 'john_doe', 'john@example.com', 'password123', 'John Doe', '2025-05-14 12:34:16', '2025-05-14 12:34:16'),
(2, NULL, 'jane_smith', 'jane@example.com', 'password456', 'Jane Smith', '2025-05-14 12:34:16', '2025-05-14 12:34:16'),
(3, NULL, 'admin', 'admin@example.com', 'admin', 'Administrator', '2025-05-14 12:34:16', '2025-05-14 12:36:02'),
(4, NULL, 'budi123', 'budi@example.com', 'rahasiabudi', 'Budi Santoso', '2025-05-14 12:34:16', '2025-05-14 12:34:16'),
(5, NULL, 'sari89', 'sari@example.com', 'sari12345', 'Sari Indah', '2025-05-14 12:34:16', '2025-05-14 12:34:16');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `data_training`
--
ALTER TABLE `data_training`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_pemohon_id` (`pemohon_id`);

--
-- Indexes for table `pemohon`
--
ALTER TABLE `pemohon`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `prediction`
--
ALTER TABLE `prediction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `lady_yakult_id` (`lady_yakult_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `data_training`
--
ALTER TABLE `data_training`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `pemohon`
--
ALTER TABLE `pemohon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `prediction`
--
ALTER TABLE `prediction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `data_training`
--
ALTER TABLE `data_training`
  ADD CONSTRAINT `fk_pemohon_id` FOREIGN KEY (`pemohon_id`) REFERENCES `pemohon` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
