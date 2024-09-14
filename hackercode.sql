-- MSQL Migrate for hackercode DB
-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 14, 2019 at 02:24 AM
-- Server version: 5.7.25
-- PHP Version: 7.1.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hackercode`
--

-- --------------------------------------------------------

--
-- Table structure for table `hc_articles`
--

CREATE TABLE `hc_articles` (
  `id` int(11) NOT NULL,
  `name` varchar(800) NOT NULL,
  `tags` varchar(800) NOT NULL,
  `content` longtext NOT NULL,
  `category` int(3) NOT NULL,
  `sub_category` int(3) NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `a_is_approved` int(1) NOT NULL DEFAULT '0',
  `a_is_active` int(1) NOT NULL DEFAULT '1',
  `is_special` int(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hc_categories`
--

CREATE TABLE `hc_categories` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `name_` varchar(500) NOT NULL,
  `is_active` enum('1','0') DEFAULT '1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hc_categories`
--

INSERT INTO `hc_categories` (`id`, `name`, `name_`, `is_active`, `created_at`, `updated_at`) VALUES
(1, 'Data Structures', 'data-structures', '1', '2019-06-10 07:17:34', '2019-06-10 07:17:34'),
(2, 'Algorithms', 'algo', '1', '2019-06-14 08:51:12', '2019-06-14 08:51:12'),
(3, 'Operation System', 'os', '1', '2019-06-14 08:51:12', '2019-06-14 08:51:12'),
(4, 'C++', 'cpp', '1', '2019-06-14 08:51:12', '2019-06-14 08:51:12'),
(5, 'Interview Experiences', 'interview-experiences', '1', '2019-06-14 08:54:13', '2019-06-14 08:54:13');

-- --------------------------------------------------------

--
-- Table structure for table `hc_chapters`
--

CREATE TABLE `hc_chapters` (
  `id` int(11) NOT NULL,
  `ch_c_code` varchar(1000) NOT NULL,
  `ch_code` varchar(700) NOT NULL,
  `ch_name` text NOT NULL,
  `ch_is_active` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hc_counter`
--

CREATE TABLE `hc_counter` (
  `id` int(11) NOT NULL,
  `counter` int(11) NOT NULL DEFAULT '2091'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hc_counter`
--

INSERT INTO `hc_counter` (`id`, `counter`) VALUES
(1, 2556);

-- --------------------------------------------------------

--
-- Table structure for table `hc_courses`
--

CREATE TABLE `hc_courses` (
  `id` int(11) NOT NULL,
  `c_code` text NOT NULL,
  `c_name` text NOT NULL,
  `c_price` int(5) NOT NULL DEFAULT '500',
  `c_total_days` int(11) NOT NULL DEFAULT '10',
  `c_desc` mediumtext,
  `c_tags` varchar(500) NOT NULL DEFAULT 'Demo ',
  `c_mrp` int(5) NOT NULL DEFAULT '0',
  `c_sub_desc` text,
  `c_cover` varchar(500) NOT NULL,
  `c_intro` varchar(500) NOT NULL,
  `c_is_paid` int(11) NOT NULL DEFAULT '1',
  `c_is_active` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hc_internships`
--

CREATE TABLE `hc_internships` (
  `id` int(11) NOT NULL,
  `f_name` varchar(100) NOT NULL,
  `l_name` varchar(100) NOT NULL,
  `ph_num` bigint(20) NOT NULL,
  `email` varchar(500) NOT NULL,
  `university` varchar(400) NOT NULL,
  `city` varchar(400) NOT NULL,
  `semester` int(1) NOT NULL,
  `course` varchar(200) NOT NULL,
  `applied_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hc_lessons`
--

CREATE TABLE `hc_lessons` (
  `id` int(11) NOT NULL,
  `l_code` varchar(100) NOT NULL,
  `l_c_code` varchar(1000) NOT NULL,
  `l_ch_code` text NOT NULL,
  `l_name` text NOT NULL,
  `l_resource` text NOT NULL,
  `l_duration` varchar(100) NOT NULL DEFAULT '10',
  `l_is_active` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hc_lesson_track`
--

CREATE TABLE `hc_lesson_track` (
  `lt_id` int(11) NOT NULL,
  `lt_username` varchar(500) NOT NULL,
  `lt_ch_code` varchar(10) NOT NULL,
  `lt_ln_code` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hc_password_reset`
--

CREATE TABLE `hc_password_reset` (
  `id` int(11) NOT NULL,
  `username` varchar(300) NOT NULL,
  `reset_key` varchar(500) NOT NULL,
  `is_active` int(1) NOT NULL DEFAULT '2',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hc_programs`
--

CREATE TABLE `hc_programs` (
  `p_id` int(11) NOT NULL,
  `p_name` varchar(255) NOT NULL,
  `p_code` varchar(255) DEFAULT NULL,
  `p_student_count` int(11) DEFAULT '0',
  `p_is_active` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hc_questions`
--

CREATE TABLE `hc_questions` (
  `q_question_id` int(11) NOT NULL,
  `q_set_id` varchar(50) DEFAULT NULL,
  `q_tag` varchar(100) DEFAULT NULL,
  `q_type` varchar(100) DEFAULT NULL,
  `q_content` longtext NOT NULL,
  `q_max_marks` int(11) NOT NULL,
  `q_negative_marks` int(11) NOT NULL,
  `q_options` text,
  `q_ans` text NOT NULL,
  `q_scope` enum('PUBLIC','PRIVATE','DEMO','PRACTICE') DEFAULT 'PUBLIC'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hc_resources`
--

CREATE TABLE `hc_resources` (
  `id` int(11) NOT NULL,
  `r_name` varchar(500) NOT NULL,
  `r_source` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hc_sets`
--

CREATE TABLE `hc_sets` (
  `s_id` int(11) NOT NULL,
  `s_test_id` int(11) NOT NULL,
  `s_name` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hc_sub_categories`
--

CREATE TABLE `hc_sub_categories` (
  `id` int(11) NOT NULL,
  `cat_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `name_` varchar(500) NOT NULL,
  `is_active` enum('1','0') DEFAULT '1',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hc_sub_categories`
--

INSERT INTO `hc_sub_categories` (`id`, `cat_id`, `name`, `name_`, `is_active`, `updated_at`) VALUES
(1, 1, 'Array', 'array', '1', '2019-06-10 07:19:21'),
(2, 1, 'Strings', 'strings', '1', '2019-06-10 07:19:22'),
(3, 1, 'Linked Lists', 'linked-list', '1', '2019-06-10 07:19:22'),
(4, 1, 'Trees', 'trees', '1', '2019-06-10 07:19:22'),
(5, 1, 'Heap', 'heap', '1', '2019-06-10 07:19:22'),
(6, 5, 'CodeVita', 'codevita', '1', '2019-06-14 08:56:43'),
(7, 5, 'HackWithInfy', 'hackwithinfy', '1', '2019-06-14 08:56:43'),
(8, 5, 'HSBC', 'hsbc', '1', '2019-06-14 08:56:43'),
(9, 5, 'Adobe', 'adobe', '1', '2019-06-14 08:56:43'),
(10, 5, 'Samsung R&D', 'samsung-RnD', '1', '2019-06-14 08:56:43');

-- --------------------------------------------------------

--
-- Table structure for table `hc_tags`
--

CREATE TABLE `hc_tags` (
  `tg_id` int(11) NOT NULL,
  `tg_name` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hc_temp_test`
--

CREATE TABLE `hc_temp_test` (
  `id` int(11) NOT NULL,
  `tt_user_id` varchar(100) NOT NULL,
  `tt_test_id` varchar(100) NOT NULL,
  `tt_time_remaining` varchar(10) NOT NULL,
  `tt_ans_object` longtext NOT NULL,
  `marks` int(11) DEFAULT NULL,
  `isFinished` tinyint(1) NOT NULL DEFAULT '0',
  `tt_maxMarks` bigint(255) DEFAULT '0',
  `tt_date` varchar(50) DEFAULT 'CURRENT_TIMESTAMP'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hc_tests`
--

CREATE TABLE `hc_tests` (
  `t_id` int(11) NOT NULL,
  `t_name` varchar(500) NOT NULL,
  `t_associated_program` varchar(100) DEFAULT NULL,
  `t_user_id` varchar(500) NOT NULL,
  `t_test_code` varchar(500) NOT NULL,
  `t_test_password` varchar(500) NOT NULL,
  `t_start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `t_end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `t_total_time` int(15) NOT NULL,
  `t_is_time_strict` enum('0','1') NOT NULL DEFAULT '0',
  `t_is_ans_shuffle` enum('0','1') NOT NULL DEFAULT '0',
  `t_file_path` varchar(1000) DEFAULT NULL,
  `t_is_active` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hc_tests`
--

INSERT INTO `hc_tests` (`t_id`, `t_name`, `t_associated_program`, `t_user_id`, `t_test_code`, `t_test_password`, `t_start_time`, `t_end_time`, `t_total_time`, `t_is_time_strict`, `t_is_ans_shuffle`, `t_file_path`, `t_is_active`) VALUES
(83, 'NEW TEST ', 'V2AF2V', 'admin@admin.com', 'BISHOP', 'bishop', '2019-05-21 19:57:22', '2019-01-01 18:30:00', 10800000, '1', '1', NULL, 1),
(84, 'New Test', '4QUF2R', 'admin@admin.com', 'TCS4567', '9090', '2019-01-01 18:30:00', '2019-01-31 18:31:00', 11400000, '1', '1', NULL, 1),
(85, 'New Test - 1', '4QUF2R', 'admin@admin.com', 'BISHOP - 1', 'aditya', '2019-06-12 06:48:06', '2019-01-01 18:30:00', 7320000, '1', '1', NULL, 1),
(86, 'asdjjg', 'RB186G', 'adityasaxena602@gmail.com', 'asdsad', 'asdasd', '2018-12-31 18:30:00', '2018-12-31 18:31:00', 14460000, '1', '1', NULL, 1),
(87, 'Test With @ As Delimiter', 'RB186G', 'admin@admin.com', 'geuddn324', '456', '2018-12-31 18:30:00', '2018-12-31 19:31:00', 11400000, '1', '1', NULL, 1),
(88, 'Mew test', 'RB186G', 'admin@admin.com', 'asdasdasd', 'asdsadsad', '2020-03-02 20:33:00', '2022-03-02 20:32:00', 14520000, '0', '1', NULL, 1),
(89, 'shdgggh', 'RB186G', 'admin@admin.com', '3456789', '2345', '2021-02-01 20:31:00', '2020-02-01 19:30:00', 7260000, '1', '1', NULL, 1),
(90, 'shdgggh', 'RB186G', 'admin@admin.com', '3456789', '2345', '2021-02-01 20:31:00', '2020-02-01 19:30:00', 7260000, '1', '1', NULL, 1),
(91, 'rty', 'RB186G', 'admin@admin.com', 'tyhg', 'tyhg', '2021-02-01 19:32:00', '2021-03-02 19:31:00', 7260000, '1', '1', NULL, 1),
(92, 'NEWW TEST', 'RB186G', 'admin@admin.com', 'RRTYE', 'RRETY', '2018-12-31 18:30:00', '2021-02-02 19:32:00', 11400000, '1', '1', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `hc_test_questions`
--

CREATE TABLE `hc_test_questions` (
  `tq_id` int(11) NOT NULL,
  `tq_test_id` varchar(100) NOT NULL,
  `tq_question_id` varchar(100) NOT NULL,
  `tq_is_active` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hc_test_questions`
--

INSERT INTO `hc_test_questions` (`tq_id`, `tq_test_id`, `tq_question_id`, `tq_is_active`) VALUES
(1, '60', '14', 1),
(2, '60', '15', 1),
(3, '60', '16', 1),
(4, '60', '17', 1),
(5, '60', '18', 1),
(6, '60', '19', 1),
(7, '60', '20', 1),
(8, '60', '21', 1),
(9, '60', '22', 1),
(10, '60', '23', 1),
(11, '60', '24', 1),
(12, '60', '25', 1),
(14, '61', '27', 1),
(15, '61', '28', 1),
(16, '61', '29', 1),
(17, '61', '30', 1),
(18, '61', '31', 1),
(19, '61', '32', 1),
(20, '61', '33', 1),
(21, '61', '34', 1),
(22, '61', '35', 1),
(23, '61', '36', 1),
(24, '61', '37', 1),
(25, '61', '38', 1),
(26, '61', '39', 1),
(27, '62', '40', 1),
(28, '62', '41', 1),
(29, '62', '42', 1),
(30, '62', '43', 1),
(31, '62', '44', 1),
(32, '62', '45', 1),
(33, '62', '46', 1),
(34, '62', '47', 1),
(35, '62', '48', 1),
(36, '62', '49', 1),
(37, '62', '50', 1),
(38, '62', '51', 1),
(39, '83', '52', 1),
(40, '83', '53', 1),
(41, '83', '54', 1),
(42, '83', '55', 1),
(43, '83', '56', 1),
(44, '83', '57', 1),
(45, '83', '58', 1),
(46, '83', '59', 1),
(47, '83', '60', 1),
(48, '83', '61', 1),
(49, '83', '62', 1),
(50, '83', '63', 1),
(51, '84', '64', 1),
(52, '84', '65', 1),
(53, '84', '66', 1),
(54, '84', '67', 1),
(55, '84', '68', 1),
(56, '84', '69', 1),
(57, '84', '70', 1),
(58, '84', '71', 1),
(59, '84', '72', 1),
(60, '84', '73', 1),
(61, '84', '74', 1),
(62, '84', '75', 1),
(63, '84', '76', 1),
(64, '84', '77', 1),
(65, '84', '78', 1),
(66, '85', '79', 1),
(67, '85', '80', 1),
(68, '85', '81', 1),
(69, '85', '82', 1),
(70, '85', '83', 1),
(71, '85', '84', 1),
(72, '85', '85', 1),
(73, '85', '86', 1),
(74, '85', '87', 1),
(75, '85', '88', 1),
(76, '85', '89', 1),
(77, '85', '90', 1),
(78, '85', '91', 1),
(79, '85', '92', 1),
(80, '85', '93', 1),
(81, '86', '94', 1),
(82, '86', '95', 1),
(83, '86', '96', 1),
(84, '86', '97', 1),
(85, '86', '98', 1),
(86, '86', '99', 1),
(87, '86', '100', 1),
(88, '86', '101', 1),
(89, '86', '102', 1),
(90, '86', '103', 1),
(91, '86', '104', 1),
(92, '86', '105', 1),
(93, '86', '106', 1),
(94, '86', '107', 1),
(95, '86', '108', 1),
(96, '87', '109', 1),
(97, '87', '110', 1),
(98, '87', '111', 1),
(99, '87', '112', 1),
(100, '87', '113', 1),
(101, '87', '114', 1),
(102, '87', '115', 1),
(103, '87', '116', 1),
(104, '87', '117', 1),
(105, '87', '118', 1),
(106, '87', '119', 1),
(107, '87', '120', 1),
(108, '87', '121', 1),
(109, '87', '122', 1),
(110, '87', '123', 1),
(111, '88', '124', 1),
(112, '88', '125', 1),
(113, '88', '126', 1),
(114, '88', '127', 1),
(115, '88', '128', 1),
(116, '88', '129', 1),
(117, '88', '130', 1),
(118, '88', '131', 1),
(119, '88', '132', 1),
(120, '88', '133', 1),
(121, '88', '134', 1),
(122, '88', '135', 1),
(123, '88', '136', 1),
(124, '88', '137', 1),
(125, '88', '138', 1),
(126, '92', '139', 1),
(127, '92', '140', 1),
(128, '92', '141', 1),
(129, '92', '142', 1),
(130, '92', '143', 1),
(131, '92', '144', 1),
(132, '92', '145', 1),
(133, '92', '146', 1),
(134, '92', '147', 1),
(135, '92', '148', 1),
(136, '92', '149', 1),
(137, '92', '150', 1),
(138, '92', '151', 1),
(139, '92', '152', 1),
(140, '92', '153', 1);

-- --------------------------------------------------------

--
-- Table structure for table `hc_transactions`
--

CREATE TABLE `hc_transactions` (
  `id` int(11) NOT NULL,
  `t_username` varchar(500) NOT NULL,
  `course_code` varchar(10) NOT NULL,
  `bank_name` varchar(500) DEFAULT NULL,
  `bank_txn_id` varchar(500) DEFAULT NULL,
  `currency` varchar(100) DEFAULT NULL,
  `gateway_name` varchar(500) DEFAULT NULL,
  `mid` varchar(500) DEFAULT NULL,
  `order_id` varchar(500) DEFAULT NULL,
  `payment_mode` varchar(500) DEFAULT NULL,
  `resp_code` varchar(5) DEFAULT NULL,
  `resp_msg` varchar(500) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `txn_amount` varchar(10) DEFAULT NULL,
  `txn_date` varchar(500) DEFAULT NULL,
  `txn_id` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hc_transactions`
--

INSERT INTO `hc_transactions` (`id`, `t_username`, `course_code`, `bank_name`, `bank_txn_id`, `currency`, `gateway_name`, `mid`, `order_id`, `payment_mode`, `resp_code`, `resp_msg`, `status`, `txn_amount`, `txn_date`, `txn_id`) VALUES
(1, 'adityasaxena602@gmail.com', 'courseCode', NULL, NULL, NULL, NULL, NULL, 'ORDER_FJBGPC8XS9', NULL, NULL, NULL, NULL, NULL, '', NULL),
(2, 'admin', 'courseCode', NULL, NULL, NULL, NULL, NULL, 'ORDER_ZRGXBCNYK9', NULL, NULL, NULL, NULL, NULL, '', NULL),
(3, 'admin', 'courseCode', NULL, NULL, NULL, NULL, NULL, 'ORDER_PRC3L7P34B', NULL, NULL, NULL, NULL, NULL, '', NULL),
(4, 'admin', 'courseCode', 'WALLET', '6845901', 'INR', 'WALLET', 'uytcBp59542228260983', 'ORDER_WGDEVELLGR', NULL, '01', 'Txn Success', 'TXN_SUCCESS', '500.00', '2019-05-07 17:13:29.0', '20190507111212800110168022500475061'),
(5, 'adityasaxena602@gmail.com', '3EC6LS', 'WALLET', '6846033', 'INR', 'WALLET', 'uytcBp59542228260983', 'ORDER_XSQGIFA5DL', NULL, '01', 'Txn Success', 'TXN_SUCCESS', '600.00', '2019-05-07 17:21:16.0', '20190507111212800110168831500469280'),
(6, 'adityasaxena602@gmail.com', '9FE2RB', 'WALLET', '6846248', 'INR', 'WALLET', 'uytcBp59542228260983', 'ORDER_FAM4BM7KIY', 'PPI', '01', 'Txn Success', 'TXN_SUCCESS', '600.00', '2019-05-07 17:42:18.0', '20190507111212800110168364500469717'),
(7, 'adityasaxena602@gmail.com', 'QUGXNO', 'WALLET', '6846382', 'INR', 'WALLET', 'uytcBp59542228260983', 'ORDER_FOQSCNKZ9F', 'PPI', '01', 'Txn Success', 'TXN_SUCCESS', '500.00', '2019-05-07 17:51:30.0', '20190507111212800110168675000478872'),
(8, 'adityasaxena602@gmail.com', '3EC6LS', NULL, NULL, NULL, NULL, NULL, 'ORDER_45HOPTN14L', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(9, 'adityasaxena602@gmail.com', '3EC6LS', NULL, NULL, NULL, NULL, NULL, 'ORDER_KNILVTXN5J', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(10, 'adityasaxena602@gmail.com', '5ZFHMZ', NULL, NULL, NULL, NULL, NULL, 'ORDER_KJUUTRVIU5', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(11, 'adityasaxena602@gmail.com', 'NC3F9A', 'WALLET', '6848235', 'INR', 'WALLET', 'uytcBp59542228260983', 'ORDER_L3XYFT0KS1', 'PPI', '01', 'Txn Success', 'TXN_SUCCESS', '600.00', '2019-05-07 22:35:03.0', '20190507111212800110168500100479284'),
(12, 'adityasaxena602@gmail.com', '9FE2RB', 'WALLET', '6848404', 'INR', 'WALLET', 'uytcBp59542228260983', 'ORDER_Z6YKWPSQAC', 'PPI', '01', 'Txn Success', 'TXN_SUCCESS', '600.00', '2019-05-07 23:18:11.0', '20190507111212800110168702600476381'),
(13, 'adityasaxena602@gmail.com', '5ZFHMZ', 'WALLET', '6853494', 'INR', 'WALLET', 'uytcBp59542228260983', 'ORDER_CEXCOILAWN', 'PPI', '01', 'Txn Success', 'TXN_SUCCESS', '500.00', '2019-05-08 13:35:55.0', '20190508111212800110168464300476415'),
(14, 'adityasaxena602@gmail.com', 'PNIEVH', NULL, NULL, NULL, NULL, NULL, 'ORDER_JMJHS5FFER', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(15, 'adityasaxena602@gmail.com', 'PNIEVH', 'WALLET', '6865657', 'INR', 'WALLET', 'uytcBp59542228260983', 'ORDER_9VBMOLHIO9', 'PPI', '01', 'Txn Success', 'TXN_SUCCESS', '500.00', '2019-05-09 10:08:48.0', '20190509111212800110168352900472632'),
(16, 'adityasaxena602@gmail.com', '5ZFHMZ', NULL, NULL, NULL, NULL, NULL, 'ORDER_M6KNX7AA6Z', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(17, 'adityasaxena602@gmail.com', '9FE2RB', NULL, NULL, NULL, NULL, NULL, 'ORDER_WTZHWSZKOZ', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(18, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_8J9SBCKVIY', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(19, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_VXN559NLQC', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(20, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_3F5Q3OOEU3', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(21, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_XTX9VESJX0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(22, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_VUYCB5V7PG', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(23, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_R0QKFIP3UI', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(24, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_2JPT8007IA', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(25, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_W8ROQX04OU', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(26, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_KBUSHIAPV9', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(27, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_C86A099UDO', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(28, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_EA61C5YFX7', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(29, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_20309J3C2R', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(30, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_OE7D2VVNOT', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(31, 'adityasaxena602@gmail.com', '9LHKDY', 'WALLET', '6881161', 'INR', 'WALLET', 'uytcBp59542228260983', 'ORDER_GE7JA8TZAQ', 'PPI', '01', 'Txn Success', 'TXN_SUCCESS', '2999.00', '2019-05-10 16:23:55.0', '20190510111212800110168978600486045'),
(32, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_QRNC6CW5KG', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(33, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_X18EKF17SG', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(34, 'adityasaxena602@gmail.com', '9LHKDY', 'WALLET', '6881386', 'INR', 'WALLET', 'uytcBp59542228260983', 'ORDER_RLZSKEWL65', 'PPI', '01', 'Txn Success', 'TXN_SUCCESS', '2999.00', '2019-05-10 16:35:03.0', '20190510111212800110168403900482597'),
(35, 'adityasaxena602@gmail.com', '9LHKDY', 'WALLET', '6883134', 'INR', 'WALLET', 'uytcBp59542228260983', 'ORDER_WH099ZFXG0', 'PPI', '01', 'Txn Success', 'TXN_SUCCESS', '2999.00', '2019-05-10 18:26:53.0', '20190510111212800110168378200495914'),
(36, 'asdasdasd@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_ZBNH2RYU7O', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(37, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_N83Z8AMP0W', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(38, 'aditya6asd@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_0HXYYMEUV0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(39, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_BPSGPBP8Y1', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(40, 'djadkhakdj@gmail.com', '9LHKDY', 'WALLET', '6884703', 'INR', 'WALLET', 'uytcBp59542228260983', 'ORDER_U9FMXQVDX2', 'PPI', '01', 'Txn Success', 'TXN_SUCCESS', '2999.00', '2019-05-10 22:39:44.0', '20190510111212800110168813200488585'),
(41, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_NKSEL9W0D6', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(42, 'adityadas@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_KXWOZYETSM', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(43, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_JB6NQDGLCT', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(44, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_AFLM9OHD1U', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(45, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_MAYCGQJTDA', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(46, 'adityasaxena602@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_AIUEHUVFF7', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(47, 'adit111ya@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_T9D6H8CC4P', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(48, 'asdasdsad@gmail.com', '9LHKDY', NULL, NULL, NULL, NULL, NULL, 'ORDER_TFNS7XKC8X', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(49, 'adityasaxena602@gmail.com', '9L6HEX', 'HDFC Bank', '4036217121962950', 'INR', 'HDFC', 'uytcBp59542228260983', 'ORDER_W1AKSQTP8F', 'CC', '01', 'Txn Success', 'TXN_SUCCESS', '876868.00', '2019-05-14 00:27:56.0', '20190514111212800110168995200483024'),
(50, 'adityasaxena602@gmail.com', 'EHJOOP', 'WALLET', '6903139', 'INR', 'WALLET', 'uytcBp59542228260983', 'ORDER_E2TXFNDKE7', 'PPI', '01', 'Txn Success', 'TXN_SUCCESS', '380.00', '2019-05-14 01:46:16.0', '20190514111212800110168894500502564'),
(51, 'adityasaxena602@gmail.com', 'NNFWGP', 'WALLET', '6904826', 'INR', 'WALLET', 'uytcBp59542228260983', 'ORDER_805E6OP70F', 'PPI', '01', 'Txn Success', 'TXN_SUCCESS', '2999.00', '2019-05-14 11:12:36.0', '20190514111212800110168377700502931'),
(52, 'adityasaxena602@gmail.com', 'V2AF2V', NULL, NULL, NULL, NULL, NULL, 'ORDER_5E98ZIR8RR', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(53, 'adityasaxena602@gmail.com', 'V2AF2V', NULL, NULL, NULL, NULL, NULL, 'ORDER_3OFH5NTHNS', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(54, 'adityasaxena602@gmail.com', 'V2AF2V', NULL, NULL, NULL, NULL, NULL, 'ORDER_105PZ83MF6', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(55, 'adityasaxena602@gmail.com', 'V2AF2V', NULL, NULL, NULL, NULL, NULL, 'ORDER_5VX9JW0B85', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(56, 'adityasaxena602@gmail.com', 'V2AF2V', 'WALLET', '6924890', 'INR', 'WALLET', 'uytcBp59542228260983', 'ORDER_ZKH26WLS4Y', 'PPI', '01', 'Txn Success', 'TXN_SUCCESS', '1500.00', '2019-05-16 12:17:59.0', '20190516111212800110168892400508454'),
(57, 'adityasaxena602@gmail.com', 'V2AF2V', NULL, NULL, NULL, NULL, NULL, 'ORDER_C74RSQORWA', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(58, 'adityasaxena602@gmail.com', 'V2AF2V', NULL, NULL, NULL, NULL, NULL, 'ORDER_SSBPBOCW9W', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(59, 'adityasaxena602@gmail.com', 'V2AF2V', NULL, NULL, NULL, NULL, NULL, 'ORDER_TL2CI24FTM', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(60, 'aa@gmail.com', 'V2AF2V', NULL, NULL, NULL, NULL, NULL, 'ORDER_B2FQ7B12RA', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(61, 'aa@gmail.com', 'V2AF2V', NULL, NULL, NULL, NULL, NULL, 'ORDER_LZVLEHDQ1X', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(62, 'aa@gmail.com', 'V2AF2V', NULL, NULL, NULL, NULL, NULL, 'ORDER_BMK2W9O834', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(63, 'aa@gmail.com', 'V2AF2V', 'WALLET', '6968282', 'INR', 'WALLET', 'uytcBp59542228260983', 'ORDER_WS426HWC0X', 'PPI', '01', 'Txn Success', 'TXN_SUCCESS', '1500.00', '2019-05-21 23:49:30.0', '20190521111212800110168373800523941'),
(64, 'adityasaxena602@gmail.com', 'V2AF2V', NULL, NULL, NULL, NULL, NULL, 'ORDER_W94GV4F15E', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(65, 'adityasaxena602@gmail.com', 'N3PDIT', NULL, NULL, NULL, NULL, NULL, 'ORDER_LS5CY9XBVJ', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(66, 'adityasaxena602@gmail.com', 'N3PDIT', NULL, NULL, NULL, NULL, NULL, 'ORDER_5OKK76030B', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(67, 'adityasaxena602@gmail.com', 'OMK27Z', NULL, NULL, NULL, NULL, NULL, 'ORDER_K42Z7V9BG9', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(68, 'adityasaxena602@gmail.com', 'RB186G', NULL, NULL, NULL, NULL, NULL, 'ORDER_EQB92IO7Z7', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `hc_user`
--

CREATE TABLE `hc_user` (
  `u_id` int(11) NOT NULL,
  `u_username` varchar(500) NOT NULL,
  `u_password` varchar(5000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hc_user_articles`
--

CREATE TABLE `hc_user_articles` (
  `id` int(11) NOT NULL,
  `user_name` varchar(500) NOT NULL,
  `article_id` int(11) NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hc_user_articles`
--

INSERT INTO `hc_user_articles` (`id`, `user_name`, `article_id`, `updated_at`) VALUES
(1, 'draft@hackercode.in', 1, '2019-06-10 15:57:57'),
(2, 'draft@hackercode.in', 2, '2019-06-10 15:58:20'),
(3, 'draft@hackercode.in', 3, '2019-06-10 16:00:41'),
(4, 'draft@hackercode.in', 4, '2019-06-10 16:01:41'),
(5, 'draft@hackercode.in', 5, '2019-06-10 16:03:15'),
(6, 'draft@hackercode.in', 6, '2019-06-11 18:48:42'),
(7, 'draft@hackercode.in', 7, '2019-06-11 18:51:40'),
(8, 'draft@hackercode.in', 8, '2019-06-11 18:54:53'),
(9, 'draft@hackercode.in', 9, '2019-06-11 18:55:04'),
(10, 'draft@hackercode.in', 10, '2019-06-11 18:55:59'),
(11, 'draft@hackercode.in', 11, '2019-06-11 20:01:55'),
(12, 'draft@hackercode.in', 12, '2019-06-11 21:13:41'),
(13, 'draft@hackercode.in', 13, '2019-06-12 16:18:01'),
(14, 'draft@hackercode.in', 14, '2019-06-12 16:26:59'),
(15, 'draft@hackercode.in', 15, '2019-06-12 16:29:34'),
(16, 'draft@hackercode.in', 16, '2019-06-12 16:30:50'),
(17, 'draft@hackercode.in', 17, '2019-06-14 07:03:17'),
(18, 'draft@hackercode.in', 18, '2019-06-14 08:35:43');

-- --------------------------------------------------------

--
-- Table structure for table `hc_user_details`
--

CREATE TABLE `hc_user_details` (
  `ud_id` int(11) NOT NULL,
  `ud_username` varchar(500) NOT NULL,
  `ud_firstname` varchar(100) NOT NULL,
  `ud_lastname` varchar(100) NOT NULL,
  `ud_phone` varchar(10) NOT NULL,
  `ud_institute` varchar(500) DEFAULT NULL,
  `ud_email` varchar(1000) NOT NULL,
  `ud_role` varchar(5) NOT NULL DEFAULT '0',
  `ud_img_path` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hc_user_program`
--

CREATE TABLE `hc_user_program` (
  `up_id` int(11) NOT NULL,
  `up_username` varchar(500) NOT NULL,
  `up_code` varchar(20) NOT NULL,
  `is_paid` tinyint(1) NOT NULL DEFAULT '0',
  `up_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hc_user_program`
--

INSERT INTO `hc_user_program` (`up_id`, `up_username`, `up_code`, `is_paid`, `up_date`) VALUES
(25, 'adityasaxena602@gmail.com', 'V2AF2V', 1, '2019-05-16 06:48:24'),
(26, 'aa@gmail.com', 'V2AF2V', 1, '2019-05-21 18:19:51'),
(27, 'adityasaxena602@gmail.com', 'RB186G', 1, '2019-06-08 11:13:02'),
(28, 'asdasdasdasd@gmail.com', 'RB186G', 1, '2019-06-13 12:42:41');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `hc_articles`
--
ALTER TABLE `hc_articles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hc_categories`
--
ALTER TABLE `hc_categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hc_chapters`
--
ALTER TABLE `hc_chapters`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hc_counter`
--
ALTER TABLE `hc_counter`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hc_courses`
--
ALTER TABLE `hc_courses`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hc_internships`
--
ALTER TABLE `hc_internships`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hc_lessons`
--
ALTER TABLE `hc_lessons`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hc_lesson_track`
--
ALTER TABLE `hc_lesson_track`
  ADD PRIMARY KEY (`lt_id`);

--
-- Indexes for table `hc_password_reset`
--
ALTER TABLE `hc_password_reset`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hc_programs`
--
ALTER TABLE `hc_programs`
  ADD PRIMARY KEY (`p_id`);

--
-- Indexes for table `hc_questions`
--
ALTER TABLE `hc_questions`
  ADD PRIMARY KEY (`q_question_id`);

--
-- Indexes for table `hc_resources`
--
ALTER TABLE `hc_resources`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hc_sets`
--
ALTER TABLE `hc_sets`
  ADD PRIMARY KEY (`s_id`),
  ADD KEY `s_test_id` (`s_test_id`);

--
-- Indexes for table `hc_sub_categories`
--
ALTER TABLE `hc_sub_categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hc_tags`
--
ALTER TABLE `hc_tags`
  ADD PRIMARY KEY (`tg_id`);

--
-- Indexes for table `hc_temp_test`
--
ALTER TABLE `hc_temp_test`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hc_tests`
--
ALTER TABLE `hc_tests`
  ADD PRIMARY KEY (`t_id`);

--
-- Indexes for table `hc_test_questions`
--
ALTER TABLE `hc_test_questions`
  ADD PRIMARY KEY (`tq_id`);

--
-- Indexes for table `hc_transactions`
--
ALTER TABLE `hc_transactions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hc_user`
--
ALTER TABLE `hc_user`
  ADD PRIMARY KEY (`u_id`),
  ADD UNIQUE KEY `username` (`u_username`);

--
-- Indexes for table `hc_user_articles`
--
ALTER TABLE `hc_user_articles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hc_user_details`
--
ALTER TABLE `hc_user_details`
  ADD PRIMARY KEY (`ud_id`),
  ADD UNIQUE KEY `ud_username` (`ud_username`);

--
-- Indexes for table `hc_user_program`
--
ALTER TABLE `hc_user_program`
  ADD PRIMARY KEY (`up_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `hc_articles`
--
ALTER TABLE `hc_articles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hc_categories`
--
ALTER TABLE `hc_categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `hc_chapters`
--
ALTER TABLE `hc_chapters`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hc_counter`
--
ALTER TABLE `hc_counter`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `hc_courses`
--
ALTER TABLE `hc_courses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hc_internships`
--
ALTER TABLE `hc_internships`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hc_lessons`
--
ALTER TABLE `hc_lessons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hc_lesson_track`
--
ALTER TABLE `hc_lesson_track`
  MODIFY `lt_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hc_password_reset`
--
ALTER TABLE `hc_password_reset`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hc_programs`
--
ALTER TABLE `hc_programs`
  MODIFY `p_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hc_questions`
--
ALTER TABLE `hc_questions`
  MODIFY `q_question_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hc_resources`
--
ALTER TABLE `hc_resources`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hc_sets`
--
ALTER TABLE `hc_sets`
  MODIFY `s_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hc_sub_categories`
--
ALTER TABLE `hc_sub_categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `hc_tags`
--
ALTER TABLE `hc_tags`
  MODIFY `tg_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hc_temp_test`
--
ALTER TABLE `hc_temp_test`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hc_tests`
--
ALTER TABLE `hc_tests`
  MODIFY `t_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=93;

--
-- AUTO_INCREMENT for table `hc_test_questions`
--
ALTER TABLE `hc_test_questions`
  MODIFY `tq_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=141;

--
-- AUTO_INCREMENT for table `hc_transactions`
--
ALTER TABLE `hc_transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;

--
-- AUTO_INCREMENT for table `hc_user`
--
ALTER TABLE `hc_user`
  MODIFY `u_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hc_user_articles`
--
ALTER TABLE `hc_user_articles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `hc_user_details`
--
ALTER TABLE `hc_user_details`
  MODIFY `ud_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hc_user_program`
--
ALTER TABLE `hc_user_program`
  MODIFY `up_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `hc_sets`
--
ALTER TABLE `hc_sets`
  ADD CONSTRAINT `hc_sets_ibfk_1` FOREIGN KEY (`s_test_id`) REFERENCES `hc_tests` (`t_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
