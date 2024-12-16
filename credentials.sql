-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 18, 2024 at 04:23 PM
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
-- Database: `credentials`
--

-- --------------------------------------------------------

--
-- Table structure for table `accountdetails`
--

CREATE TABLE `accountdetails` (
  `Fname` varchar(251) NOT NULL,
  `Password` varchar(251) NOT NULL,
  `Address` varchar(300) NOT NULL,
  `Sex` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `adherence`
--

CREATE TABLE `adherence` (
  `DateCheck` varchar(50) NOT NULL,
  `MinVal` int(255) NOT NULL,
  `MaxVal` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `alarmsettings`
--

CREATE TABLE `alarmsettings` (
  `Switch` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `alarmsettings`
--

INSERT INTO `alarmsettings` (`Switch`) VALUES
('On');

-- --------------------------------------------------------

--
-- Table structure for table `alreadylogged`
--

CREATE TABLE `alreadylogged` (
  `Logged` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `currentuser`
--

CREATE TABLE `currentuser` (
  `HomeName` varchar(251) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `medicinedetails`
--

CREATE TABLE `medicinedetails` (
  `Name` varchar(255) NOT NULL,
  `MedicineName` varchar(255) NOT NULL,
  `Dosage` varchar(255) NOT NULL,
  `Frequency` varchar(255) NOT NULL,
  `Strength` varchar(255) NOT NULL,
  `Time1` varchar(255) NOT NULL,
  `Time2` varchar(255) NOT NULL,
  `Time3` varchar(255) NOT NULL,
  `Duration` varchar(255) NOT NULL,
  `Instruction` varchar(255) NOT NULL,
  `Take1` varchar(255) NOT NULL,
  `Take2` varchar(251) NOT NULL,
  `Take3` varchar(251) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accountdetails`
--
ALTER TABLE `accountdetails`
  ADD PRIMARY KEY (`Fname`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
