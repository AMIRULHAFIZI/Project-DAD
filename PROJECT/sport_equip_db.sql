-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 27, 2024 at 10:19 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sport_equip_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `bookingId` int(4) NOT NULL,
  `equipId` int(6) NOT NULL,
  `borrowDate` date NOT NULL,
  `returnDate` date NOT NULL,
  `userName` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`bookingId`, `equipId`, `borrowDate`, `returnDate`, `userName`) VALUES
(1, 111111, '2024-06-01', '2024-06-02', 'nurul'),
(2, 100000, '2024-06-15', '2024-06-18', 'abu'),
(3, 130000, '2024-05-01', '2024-05-02', 'iman'),
(4, 130000, '2024-06-25', '2024-06-27', 'irfan'),
(5, 120000, '2024-06-01', '2024-06-05', 'kimi'),
(6, 120000, '2024-06-20', '2024-06-21', 'misha');

-- --------------------------------------------------------

--
-- Table structure for table `equipment`
--

CREATE TABLE `equipment` (
  `equipId` int(6) NOT NULL,
  `equipName` char(255) NOT NULL,
  `equipType` char(255) NOT NULL,
  `equipStatus` char(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `equipment`
--

INSERT INTO `equipment` (`equipId`, `equipName`, `equipType`, `equipStatus`) VALUES
(100000, 'Wilson Tennis Racket', 'Racket Sport', 'Not Available'),
(111111, 'Basketball', 'Ball Sport', 'Not Available'),
(120000, 'Yonex Shuttlecock', 'Racket Sport', 'Available'),
(130000, 'Bola Sepak Umbro', 'Ball Sport', 'Available');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`bookingId`),
  ADD KEY `fk_booking_equipment` (`equipId`);

--
-- Indexes for table `equipment`
--
ALTER TABLE `equipment`
  ADD PRIMARY KEY (`equipId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `bookingId` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `fk_booking_equipment` FOREIGN KEY (`equipId`) REFERENCES `equipment` (`equipId`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
