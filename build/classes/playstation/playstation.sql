-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 08, 2021 at 03:39 AM
-- Server version: 10.1.28-MariaDB
-- PHP Version: 7.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `playstation`
--

-- --------------------------------------------------------

--
-- Table structure for table `actions`
--

CREATE TABLE `actions` (
  `id` int(12) NOT NULL,
  `actionDate` varchar(15) NOT NULL,
  `actionTime` varchar(15) NOT NULL,
  `details` varchar(260) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `actions`
--

INSERT INTO `actions` (`id`, `actionDate`, `actionTime`, `details`) VALUES
(1, '02-08-2021', '08:41:09', 'aقام باضافة غرفة جديدة'),
(2, '02-08-2021', '08:41:23', 'aقام باضافة غرفة جديدة'),
(3, '02-08-2021', '08:42:13', 'aقام باضافة قسم جديد'),
(4, '02-08-2021', '08:42:30', 'aقام باضافة قسم جديد'),
(5, '02-08-2021', '08:42:58', 'aقام باضافة قسم جديد'),
(6, '02-08-2021', '08:44:35', 'aتمت الاضافة جهاز جديد'),
(7, '02-08-2021', '08:44:40', 'aتمت الاضافة جهاز جديد'),
(8, '02-08-2021', '08:45:59', 'aقام باضافة غرفة جديدة'),
(9, '02-08-2021', '08:46:42', 'aتمت الاضافة جهاز جديد'),
(10, '02-08-2021', '08:46:51', 'aتمت الاضافة جهاز جديد'),
(11, '02-08-2021', '08:47:07', 'aتمت الاضافة جهاز جديد'),
(12, '03-08-2021', '01:08:27', 'تم تسجيل الدخول الي السيستم من قبل a'),
(13, '03-08-2021', '01:22:36', 'تم تسجيل الدخول الي السيستم من قبل a'),
(14, '03-08-2021', '01:33:27', 'تم تسجيل الدخول الي السيستم من قبل a'),
(15, '03-08-2021', '09:53:49', 'تم تسجيل الدخول الي السيستم من قبل a'),
(16, '03-08-2021', '09:55:22', 'تم تسجيل الدخول الي السيستم من قبل مو صلاح'),
(17, '04-08-2021', '12:24:18', 'تم تسجيل الدخول الي السيستم من قبل a'),
(18, '04-08-2021', '12:29:29', 'تم تسجيل الدخول الي السيستم من قبل a'),
(19, '04-08-2021', '01:35:26', 'تم تسجيل الدخول الي السيستم من قبل a'),
(20, '04-08-2021', '01:48:55', 'تم تسجيل الدخول الي السيستم من قبل a'),
(21, '04-08-2021', '01:52:54', 'تم تسجيل الدخول الي السيستم من قبل a'),
(22, '04-08-2021', '01:55:59', 'تم تسجيل الدخول الي السيستم من قبل a'),
(23, '04-08-2021', '09:47:06', 'تم تسجيل الدخول الي السيستم من قبل a'),
(24, '04-08-2021', '10:01:05', 'تم تسجيل الدخول الي السيستم من قبل a'),
(25, '04-08-2021', '10:03:37', 'تم تسجيل الدخول الي السيستم من قبل a'),
(26, '04-08-2021', '10:33:44', 'تم تسجيل الدخول الي السيستم من قبل a'),
(27, '04-08-2021', '10:40:58', 'تم تسجيل الدخول الي السيستم من قبل a'),
(28, '04-08-2021', '10:45:48', 'تم تسجيل الدخول الي السيستم من قبل a'),
(29, '04-08-2021', '10:49:29', 'تم تسجيل الدخول الي السيستم من قبل a'),
(30, '04-08-2021', '10:55:10', 'تم تسجيل الدخول الي السيستم من قبل a'),
(31, '04-08-2021', '10:59:23', 'تم تسجيل الدخول الي السيستم من قبل a'),
(32, '04-08-2021', '11:03:47', 'تم تسجيل الدخول الي السيستم من قبل a'),
(33, '04-08-2021', '11:27:45', 'تم تسجيل الدخول الي السيستم من قبل a'),
(34, '04-08-2021', '11:33:26', 'تم تسجيل الدخول الي السيستم من قبل a'),
(35, '04-08-2021', '11:42:43', 'تم تسجيل الدخول الي السيستم من قبل a'),
(36, '05-08-2021', '09:51:41', 'تم تسجيل الدخول الي السيستم من قبل a'),
(37, '05-08-2021', '09:59:59', 'تم تسجيل الدخول الي السيستم من قبل a'),
(38, '05-08-2021', '10:05:23', 'تم تسجيل الدخول الي السيستم من قبل a'),
(39, '05-08-2021', '10:10:49', 'تم تسجيل الدخول الي السيستم من قبل a'),
(40, '05-08-2021', '10:14:11', 'تم تسجيل الدخول الي السيستم من قبل a'),
(41, '05-08-2021', '10:35:45', 'تم تسجيل الدخول الي السيستم من قبل a'),
(42, '05-08-2021', '10:39:29', 'تم تسجيل الدخول الي السيستم من قبل a'),
(43, '05-08-2021', '10:45:14', 'aتمت الاضافة جهاز جديد'),
(44, '05-08-2021', '11:24:29', 'تم تسجيل الدخول الي السيستم من قبل a'),
(45, '05-08-2021', '04:15:07', 'تم تسجيل الدخول الي السيستم من قبل a'),
(46, '05-08-2021', '04:16:52', 'تم تسجيل الدخول الي السيستم من قبل a'),
(47, '05-08-2021', '04:20:57', 'تم تسجيل الدخول الي السيستم من قبل a'),
(48, '05-08-2021', '04:22:37', 'تم تسجيل الدخول الي السيستم من قبل a'),
(49, '05-08-2021', '04:24:16', 'تم تسجيل الدخول الي السيستم من قبل a'),
(50, '05-08-2021', '04:28:50', 'تم تسجيل الدخول الي السيستم من قبل a'),
(51, '05-08-2021', '04:34:17', 'تم تسجيل الدخول الي السيستم من قبل a'),
(52, '05-08-2021', '04:38:29', 'تم تسجيل الدخول الي السيستم من قبل a'),
(53, '05-08-2021', '04:41:34', 'تم تسجيل الدخول الي السيستم من قبل a'),
(54, '05-08-2021', '04:47:14', 'aتمت الاضافة جهاز جديد'),
(55, '05-08-2021', '04:52:03', 'تم تسجيل الدخول الي السيستم من قبل a'),
(56, '05-08-2021', '04:53:40', 'تم تسجيل الدخول الي السيستم من قبل a'),
(57, '05-08-2021', '04:55:53', 'aقام باضافة غرفة جديدة'),
(58, '05-08-2021', '04:56:02', 'aقام باضافة غرفة جديدة'),
(59, '05-08-2021', '04:56:41', 'aتمت الاضافة جهاز جديد'),
(60, '05-08-2021', '04:56:47', 'aتمت الاضافة جهاز جديد'),
(61, '05-08-2021', '04:56:54', 'aتمت الاضافة جهاز جديد'),
(62, '05-08-2021', '04:57:08', 'aتمت الاضافة جهاز جديد'),
(63, '05-08-2021', '04:57:15', 'aتمت الاضافة جهاز جديد'),
(64, '05-08-2021', '04:57:26', 'aتمت الاضافة جهاز جديد'),
(65, '05-08-2021', '05:00:01', 'تم تسجيل الدخول الي السيستم من قبل a'),
(66, '05-08-2021', '05:06:41', 'تم تسجيل الدخول الي السيستم من قبل a'),
(67, '05-08-2021', '05:38:36', 'تم تسجيل الدخول الي السيستم من قبل a'),
(68, '05-08-2021', '05:58:48', 'تم تسجيل الدخول الي السيستم من قبل a'),
(69, '05-08-2021', '06:02:38', 'تم تسجيل الدخول الي السيستم من قبل a'),
(70, '05-08-2021', '06:04:02', 'تم تسجيل الدخول الي السيستم من قبل a'),
(71, '05-08-2021', '06:10:00', 'تم تسجيل الدخول الي السيستم من قبل a'),
(72, '05-08-2021', '06:11:21', 'تم تسجيل الدخول الي السيستم من قبل a'),
(73, '05-08-2021', '06:13:13', 'تم تسجيل الدخول الي السيستم من قبل a'),
(74, '05-08-2021', '06:17:58', 'تم تسجيل الدخول الي السيستم من قبل a'),
(75, '05-08-2021', '06:22:12', 'تم تسجيل الدخول الي السيستم من قبل a'),
(76, '05-08-2021', '06:33:02', 'تم تسجيل الدخول الي السيستم من قبل a'),
(77, '05-08-2021', '06:35:29', 'تم تسجيل الدخول الي السيستم من قبل a'),
(78, '05-08-2021', '06:43:04', 'تم تسجيل الدخول الي السيستم من قبل a'),
(79, '05-08-2021', '06:46:34', 'تم تسجيل الدخول الي السيستم من قبل a'),
(80, '05-08-2021', '06:49:14', 'تم تسجيل الدخول الي السيستم من قبل a'),
(81, '05-08-2021', '09:10:06', 'تم تسجيل الدخول الي السيستم من قبل a'),
(82, '05-08-2021', '09:16:35', 'تم تسجيل الدخول الي السيستم من قبل a'),
(83, '05-08-2021', '09:20:54', 'تم تسجيل الدخول الي السيستم من قبل a'),
(84, '05-08-2021', '09:22:53', 'تم تسجيل الدخول الي السيستم من قبل a'),
(85, '05-08-2021', '09:25:00', 'تم تسجيل الدخول الي السيستم من قبل a'),
(86, '05-08-2021', '09:29:39', 'تم تسجيل الدخول الي السيستم من قبل a'),
(87, '05-08-2021', '09:33:10', 'تم تسجيل الدخول الي السيستم من قبل a'),
(88, '05-08-2021', '09:36:49', 'تم تسجيل الدخول الي السيستم من قبل a'),
(89, '05-08-2021', '09:39:07', 'تم تسجيل الدخول الي السيستم من قبل a'),
(90, '05-08-2021', '09:42:28', 'تم تسجيل الدخول الي السيستم من قبل a'),
(91, '05-08-2021', '09:44:11', 'تم تسجيل الدخول الي السيستم من قبل a'),
(92, '06-08-2021', '07:50:35', 'تم تسجيل الدخول الي السيستم من قبل a'),
(93, '06-08-2021', '08:04:44', 'تم تسجيل الدخول الي السيستم من قبل a'),
(94, '06-08-2021', '08:12:54', 'تم تسجيل الدخول الي السيستم من قبل a'),
(95, '06-08-2021', '08:29:37', 'تم تسجيل الدخول الي السيستم من قبل a'),
(96, '06-08-2021', '08:32:01', 'تم تسجيل الدخول الي السيستم من قبل a'),
(97, '06-08-2021', '08:41:22', 'تم تسجيل الدخول الي السيستم من قبل a'),
(98, '06-08-2021', '08:47:01', 'تم تسجيل الدخول الي السيستم من قبل a'),
(99, '06-08-2021', '09:01:10', 'تم تسجيل الدخول الي السيستم من قبل a'),
(100, '06-08-2021', '09:07:55', 'تم تسجيل الدخول الي السيستم من قبل a'),
(101, '06-08-2021', '09:11:11', 'تم تسجيل الدخول الي السيستم من قبل a'),
(102, '06-08-2021', '09:32:44', 'تم تسجيل الدخول الي السيستم من قبل a'),
(103, '06-08-2021', '09:37:49', 'تم تسجيل الدخول الي السيستم من قبل a'),
(104, '06-08-2021', '09:49:51', 'تم تسجيل الدخول الي السيستم من قبل a'),
(105, '06-08-2021', '09:52:22', 'تم تسجيل الدخول الي السيستم من قبل a'),
(106, '06-08-2021', '09:55:36', 'تم تسجيل الدخول الي السيستم من قبل a'),
(107, '06-08-2021', '11:42:06', 'تم تسجيل الدخول الي السيستم من قبل a'),
(108, '06-08-2021', '11:54:26', 'تم تسجيل الدخول الي السيستم من قبل a'),
(109, '06-08-2021', '08:41:08', 'تم تسجيل الدخول الي السيستم من قبل a'),
(110, '07-08-2021', '08:07:06', 'تم تسجيل الدخول الي السيستم من قبل a'),
(111, '07-08-2021', '08:13:33', 'تم تسجيل الدخول الي السيستم من قبل a'),
(112, '07-08-2021', '08:15:10', 'تم تسجيل الدخول الي السيستم من قبل a'),
(113, '07-08-2021', '08:21:36', 'تم تسجيل الدخول الي السيستم من قبل a'),
(114, '07-08-2021', '08:25:34', 'تم تسجيل الدخول الي السيستم من قبل a'),
(115, '07-08-2021', '08:34:26', 'تم تسجيل الدخول الي السيستم من قبل a'),
(116, '07-08-2021', '08:39:28', 'تم تسجيل الدخول الي السيستم من قبل a'),
(117, '07-08-2021', '10:01:59', 'تم تسجيل الدخول الي السيستم من قبل a'),
(118, '07-08-2021', '10:07:00', 'تم تسجيل الدخول الي السيستم من قبل a'),
(119, '07-08-2021', '10:07:59', 'تم تسجيل الدخول الي السيستم من قبل a'),
(120, '07-08-2021', '10:12:18', 'تم تسجيل الدخول الي السيستم من قبل a'),
(121, '07-08-2021', '10:20:00', 'تم تسجيل الدخول الي السيستم من قبل a'),
(122, '07-08-2021', '10:36:21', 'تم تسجيل الدخول الي السيستم من قبل a'),
(123, '07-08-2021', '10:38:22', 'تم تسجيل الدخول الي السيستم من قبل a'),
(124, '07-08-2021', '10:54:28', 'تم تسجيل الدخول الي السيستم من قبل a'),
(125, '07-08-2021', '10:55:55', 'تم تسجيل الدخول الي السيستم من قبل a'),
(126, '07-08-2021', '11:44:55', 'تم تسجيل الدخول الي السيستم من قبل a'),
(127, '07-08-2021', '06:25:03', 'تم تسجيل الدخول الي السيستم من قبل a'),
(128, '07-08-2021', '06:30:27', 'تم تسجيل الدخول الي السيستم من قبل a'),
(129, '07-08-2021', '06:31:39', 'تم تسجيل الدخول الي السيستم من قبل a'),
(130, '07-08-2021', '06:32:44', 'تم تسجيل الدخول الي السيستم من قبل a');

-- --------------------------------------------------------

--
-- Table structure for table `cafebuydetails`
--

CREATE TABLE `cafebuydetails` (
  `id` int(11) NOT NULL,
  `receiptNumber` int(11) NOT NULL,
  `itemCode` int(11) NOT NULL,
  `quantity` float NOT NULL,
  `buyPrice` float NOT NULL,
  `payPrice` float NOT NULL,
  `repository_id` int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cafebuydetails`
--

INSERT INTO `cafebuydetails` (`id`, `receiptNumber`, `itemCode`, `quantity`, `buyPrice`, `payPrice`, `repository_id`) VALUES
(1, 1, 10001, 2, 2.5, 5, 1),
(2, 1, 1003, 4, 3, 4, 1),
(3, 1, 1002, 4, 4, 5, 1),
(4, 2, 1008, 2, 3, 4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `cafebuymaininfo`
--

CREATE TABLE `cafebuymaininfo` (
  `id` int(12) NOT NULL,
  `receiptNumber` int(12) NOT NULL,
  `userId` int(12) NOT NULL,
  `date` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cafebuymaininfo`
--

INSERT INTO `cafebuymaininfo` (`id`, `receiptNumber`, `userId`, `date`) VALUES
(1, 1, 1, '2021-08-07'),
(2, 2, 1, '2021-08-07');

-- --------------------------------------------------------

--
-- Table structure for table `cafeitem`
--

CREATE TABLE `cafeitem` (
  `id` int(12) NOT NULL,
  `itemCode` int(12) NOT NULL,
  `secction_id` int(12) NOT NULL,
  `item_name` varchar(60) NOT NULL,
  `itemBuy` float NOT NULL,
  `itemPrice` float NOT NULL,
  `notificatioNumber` int(5) NOT NULL DEFAULT '5'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cafeitem`
--

INSERT INTO `cafeitem` (`id`, `itemCode`, `secction_id`, `item_name`, `itemBuy`, `itemPrice`, `notificatioNumber`) VALUES
(1, 10001, 1, 'اندومي', 2.5, 5, 10),
(2, 1002, 1, 'بيبسي كبير', 4, 5, 5),
(3, 1003, 1, 'بيبسي صغير', 3, 4, 5),
(4, 1004, 1, 'عصير مانجو', 3, 4, 5),
(5, 1005, 1, 'عصير تفاح', 3, 4, 5),
(6, 1006, 1, 'عصير انانس', 3, 4, 5),
(7, 1007, 1, 'عصير كوكتيل', 3, 4, 5),
(8, 1008, 1, 'حليب بشكولاتة', 3, 4, 5);

-- --------------------------------------------------------

--
-- Table structure for table `cafesection`
--

CREATE TABLE `cafesection` (
  `id` int(12) NOT NULL,
  `cafeSectionName` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cafesection`
--

INSERT INTO `cafesection` (`id`, `cafeSectionName`) VALUES
(1, 'مشروبات');

-- --------------------------------------------------------

--
-- Table structure for table `companydata`
--

CREATE TABLE `companydata` (
  `id` int(12) NOT NULL,
  `company_name` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `tel` varchar(15) NOT NULL,
  `welcomeMessage` varchar(200) NOT NULL,
  `tailMessage` varchar(200) NOT NULL,
  `logoPicturePAth` varchar(200) NOT NULL,
  `facebookLink` varchar(240) NOT NULL,
  `instgramLink` varchar(240) NOT NULL,
  `user_id` int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `companydata`
--

INSERT INTO `companydata` (`id`, `company_name`, `address`, `tel`, `welcomeMessage`, `tailMessage`, `logoPicturePAth`, `facebookLink`, `instgramLink`, `user_id`) VALUES
(1, 'MO SALAH', '30 St El MEhalmy ', '01212707730', '', '', '', '', '', 1);

-- --------------------------------------------------------

--
-- Table structure for table `customerreceiptdetailedinfo`
--

CREATE TABLE `customerreceiptdetailedinfo` (
  `id` int(12) NOT NULL,
  `receiptNumber` int(12) NOT NULL,
  `itemCode` int(12) NOT NULL,
  `quantity` float NOT NULL,
  `payPrice` float NOT NULL,
  `repositiry_id` int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customerreceiptdetailedinfo`
--

INSERT INTO `customerreceiptdetailedinfo` (`id`, `receiptNumber`, `itemCode`, `quantity`, `payPrice`, `repositiry_id`) VALUES
(1, 1, 1002, 2, 5, 1),
(2, 2, 1008, 1, 4, 1),
(3, 3, 1008, 1, 4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `customerreceiptmaininfo`
--

CREATE TABLE `customerreceiptmaininfo` (
  `id` int(12) NOT NULL,
  `receiptNumber` int(12) NOT NULL,
  `transactionNumber` int(12) NOT NULL,
  `user_id` int(12) NOT NULL,
  `date` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customerreceiptmaininfo`
--

INSERT INTO `customerreceiptmaininfo` (`id`, `receiptNumber`, `transactionNumber`, `user_id`, `date`) VALUES
(1, 1, 555, 1, '2021-08-07'),
(2, 2, 555, 1, '2021-08-07'),
(3, 3, 555, 1, '2021-08-07');

-- --------------------------------------------------------

--
-- Table structure for table `enteroutmoney`
--

CREATE TABLE `enteroutmoney` (
  `id` int(12) NOT NULL,
  `transactionNumber` int(12) NOT NULL,
  `total` float NOT NULL,
  `safty_id` int(12) NOT NULL,
  `user_id` int(12) NOT NULL,
  `date` varchar(12) NOT NULL,
  `account_id` int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `enteroutmoney`
--

INSERT INTO `enteroutmoney` (`id`, `transactionNumber`, `total`, `safty_id`, `user_id`, `date`, `account_id`) VALUES
(1, 555, 18, 1, 1, '07-08-2021', 2),
(2, 1000, 12, 1, 1, '07-08-2021', 1),
(3, 1000, 18, 1, 1, '07-08-2021', 2),
(4, 1002, 8, 1, 1, '07-08-2021', 1),
(5, 1002, 16, 1, 1, '07-08-2021', 2),
(6, 1001, 24, 1, 1, '07-08-2021', 1),
(7, 1001, 4, 1, 1, '07-08-2021', 2),
(8, 1111, 990, 1, 1, '07-08-2021', 3),
(9, 555, 43, 1, 1, '07-08-2021', 2),
(10, 1003, 11, 1, 1, '07-08-2021', 1),
(11, 1003, 0, 1, 1, '07-08-2021', 2),
(12, 555, 10, 2, 1, '07-08-2021', 2),
(13, 555, 4, 2, 1, '07-08-2021', 2),
(14, 555, 4, 2, 1, '07-08-2021', 2);

-- --------------------------------------------------------

--
-- Table structure for table `equimpent`
--

CREATE TABLE `equimpent` (
  `id` int(12) NOT NULL,
  `Code` int(12) NOT NULL,
  `EquimpentName` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `father`
--

CREATE TABLE `father` (
  `ID` int(12) NOT NULL,
  `Name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `father`
--

INSERT INTO `father` (`ID`, `Name`) VALUES
(1, 'ارباح');

-- --------------------------------------------------------

--
-- Table structure for table `followingdevice`
--

CREATE TABLE `followingdevice` (
  `id` int(12) NOT NULL,
  `transactionNumber` int(12) NOT NULL,
  `startTime` varchar(10) NOT NULL,
  `endTime` varchar(10) NOT NULL,
  `timeType` int(2) NOT NULL,
  `Device_id` int(12) NOT NULL,
  `isOffer` int(2) NOT NULL,
  `offer_id` int(12) NOT NULL DEFAULT '0',
  `player_id` int(12) NOT NULL,
  `user_id` int(12) NOT NULL,
  `stillRunning` int(1) NOT NULL DEFAULT '1',
  `dateToday` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `followingdevice`
--

INSERT INTO `followingdevice` (`id`, `transactionNumber`, `startTime`, `endTime`, `timeType`, `Device_id`, `isOffer`, `offer_id`, `player_id`, `user_id`, `stillRunning`, `dateToday`) VALUES
(1, 1000, '09:38:47', '10:39:44', 0, 2, 0, -1, 6, 1, 0, '07-08-2021'),
(2, 1001, '08:38:55', '10:40:10', 0, 11, 0, -1, 2, 1, 0, '07-08-2021'),
(3, 1002, '10:00:02', '10:39:54', 0, 9, 0, -1, 4, 1, 0, '07-08-2021'),
(4, 1003, '10:00:11', '10:56:49', 0, 6, 0, -1, 15, 1, 0, '07-08-2021');

-- --------------------------------------------------------

--
-- Table structure for table `newdevice`
--

CREATE TABLE `newdevice` (
  `id` int(12) NOT NULL,
  `section_id` int(12) NOT NULL,
  `deviceNumber` varchar(50) NOT NULL,
  `deviceName` varchar(100) NOT NULL,
  `room_id` int(12) NOT NULL,
  `devicePrice` float NOT NULL,
  `deviceDateBuy` varchar(15) NOT NULL,
  `Note` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `newdevice`
--

INSERT INTO `newdevice` (`id`, `section_id`, `deviceNumber`, `deviceName`, `room_id`, `devicePrice`, `deviceDateBuy`, `Note`) VALUES
(1, 1, '1', 'playstation 4 - 1', 1, 12000, '2021-08-02', ''),
(2, 1, '2', 'playstation 4 - 2', 1, 12000, '2021-08-02', ''),
(3, 2, '4', 'طرابيزة يميين', 3, 7000, '2021-08-02', ''),
(4, 2, '5', 'طرابيزة شمال', 3, 7000, '2021-08-02', ''),
(5, 3, '6', 'pool - 1', 3, 7000, '2021-08-02', ''),
(6, 1, '31231', 'playstation4- 3', 1, 10000, '2021-08-16', ''),
(7, 1, '3131', 'playstation 4-5', 4, 10000, '2021-08-05', ''),
(8, 1, '3132', 'playstation 4-6', 4, 10000, '2021-08-05', ''),
(9, 1, '31327', 'playstation 4-7', 4, 10000, '2021-08-05', ''),
(10, 1, '313271', 'playstation 4-8', 5, 10000, '2021-08-05', ''),
(11, 1, '3132711', 'playstation 4-9', 5, 10000, '2021-08-05', ''),
(12, 1, '31327112', 'playstation 4-10', 5, 10000, '2021-08-05', '');

-- --------------------------------------------------------

--
-- Table structure for table `offer`
--

CREATE TABLE `offer` (
  `id` int(12) NOT NULL,
  `section_id` int(12) NOT NULL,
  `mainUnit` float NOT NULL,
  `addUnit` float NOT NULL,
  `startDate` varchar(15) NOT NULL,
  `endDate` varchar(15) NOT NULL,
  `offerName` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `players`
--

CREATE TABLE `players` (
  `id` int(12) NOT NULL,
  `playerName` varchar(100) NOT NULL,
  `telPhone` varchar(16) NOT NULL,
  `points` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `players`
--

INSERT INTO `players` (`id`, `playerName`, `telPhone`, `points`) VALUES
(1, 'زائر', '1', 0),
(2, 'اسحاق', '22', 0),
(3, 'isaac', '213123', 0),
(4, 'بيشوي القس', '1423423', 0),
(5, 'مريم نبيل صبحي', '31231312', 0),
(6, 'ساندي حلمي', '13123123123', 0),
(7, 'ابانوب يواقيم', '1313123', 0),
(8, 'ابراهيم الفخري', '234234234234', 0),
(9, 'ماريام اشرف', '312312312', 0),
(10, 'جوزيف جورج', '12312312312', 0),
(11, 'ماركو مجدي', '31321312', 0),
(12, 'كاراس', '13123555433', 0),
(13, 'فيلوباتير', '2343424234', 0),
(14, 'ايمن عماد', '31312312312312', 0),
(15, 'منير جورجيوس', '1321312312312', 0);

-- --------------------------------------------------------

--
-- Table structure for table `repository`
--

CREATE TABLE `repository` (
  `id` int(12) NOT NULL,
  `repositoryName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `repository`
--

INSERT INTO `repository` (`id`, `repositoryName`) VALUES
(1, 'رئيسي');

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE `rooms` (
  `Id` int(12) NOT NULL,
  `roomNumber` varchar(12) NOT NULL,
  `roomName` varchar(100) NOT NULL,
  `isVIB` int(2) NOT NULL,
  `vibHourPrice` float NOT NULL,
  `vipPriceMulti` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `rooms`
--

INSERT INTO `rooms` (`Id`, `roomNumber`, `roomName`, `isVIB`, `vibHourPrice`, `vipPriceMulti`) VALUES
(1, '1', 'غرفة البلايستين ', 0, 0, 0),
(2, '2', 'غرفة الكمبيوتر', 0, 0, 0),
(3, '3', 'الصالة ', 0, 0, 0),
(4, '123', 'غرفة 2', 0, 0, 0),
(5, '1232', 'غرفة 3', 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `safe`
--

CREATE TABLE `safe` (
  `id` int(12) NOT NULL,
  `nameOfSafe` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `safe`
--

INSERT INTO `safe` (`id`, `nameOfSafe`) VALUES
(1, 'الادارة'),
(2, 'بيع 1 '),
(3, 'بيع 2');

-- --------------------------------------------------------

--
-- Table structure for table `section`
--

CREATE TABLE `section` (
  `id` int(12) NOT NULL,
  `sectionName` varchar(100) NOT NULL,
  `sectionHourPrice` float NOT NULL,
  `multiHourPrice` float NOT NULL,
  `SectionGamePrice` float NOT NULL,
  `imagePath` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `section`
--

INSERT INTO `section` (`id`, `sectionName`, `sectionHourPrice`, `multiHourPrice`, `SectionGamePrice`, `imagePath`) VALUES
(1, 'playstation 4', 12, 15, 4, 'D:\\NetBeansProjects\\PlayStation\\src\\playstation\\playstionsss.png'),
(2, 'ping pong', 5, 8, 3, 'D:\\NetBeansProjects\\PlayStation\\src\\playstation\\pingpong.png'),
(3, 'pool', 12, 12, 3, 'D:\\NetBeansProjects\\PlayStation\\src\\playstation\\following.png');

-- --------------------------------------------------------

--
-- Table structure for table `son`
--

CREATE TABLE `son` (
  `Id` int(12) NOT NULL,
  `father_id` int(12) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `son`
--

INSERT INTO `son` (`Id`, `father_id`, `name`) VALUES
(1, 1, 'ارباح الاجهزة'),
(2, 1, 'ارباح الكافية'),
(3, 1, 'حساب السايبر');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(12) NOT NULL,
  `userName` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `permission` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `userName`, `password`, `permission`) VALUES
(1, 'a', '1', 1),
(2, 'مو صلاح', '1', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `actions`
--
ALTER TABLE `actions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cafebuydetails`
--
ALTER TABLE `cafebuydetails`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cafebuymaininfo`
--
ALTER TABLE `cafebuymaininfo`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `receiptNumber` (`receiptNumber`);

--
-- Indexes for table `cafeitem`
--
ALTER TABLE `cafeitem`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `item_name` (`item_name`),
  ADD UNIQUE KEY `itemCode` (`itemCode`);

--
-- Indexes for table `cafesection`
--
ALTER TABLE `cafesection`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cafeSectionName` (`cafeSectionName`);

--
-- Indexes for table `companydata`
--
ALTER TABLE `companydata`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customerreceiptdetailedinfo`
--
ALTER TABLE `customerreceiptdetailedinfo`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customerreceiptmaininfo`
--
ALTER TABLE `customerreceiptmaininfo`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `receiptNumber` (`receiptNumber`);

--
-- Indexes for table `enteroutmoney`
--
ALTER TABLE `enteroutmoney`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `equimpent`
--
ALTER TABLE `equimpent`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `Code` (`Code`),
  ADD UNIQUE KEY `EquimpentName` (`EquimpentName`);

--
-- Indexes for table `father`
--
ALTER TABLE `father`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `followingdevice`
--
ALTER TABLE `followingdevice`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `newdevice`
--
ALTER TABLE `newdevice`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `deviceNumber` (`deviceNumber`),
  ADD UNIQUE KEY `deviceName` (`deviceName`);

--
-- Indexes for table `offer`
--
ALTER TABLE `offer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `offerName` (`offerName`);

--
-- Indexes for table `players`
--
ALTER TABLE `players`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `telPhone` (`telPhone`),
  ADD UNIQUE KEY `playerName` (`playerName`);

--
-- Indexes for table `repository`
--
ALTER TABLE `repository`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `repositoryName` (`repositoryName`);

--
-- Indexes for table `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `roomNumber` (`roomNumber`),
  ADD UNIQUE KEY `roomName` (`roomName`);

--
-- Indexes for table `safe`
--
ALTER TABLE `safe`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nameOfSafe` (`nameOfSafe`);

--
-- Indexes for table `section`
--
ALTER TABLE `section`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `sectionName` (`sectionName`);

--
-- Indexes for table `son`
--
ALTER TABLE `son`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `userName` (`userName`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
