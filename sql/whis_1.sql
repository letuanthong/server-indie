-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 02, 2025 at 05:13 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `whis_1`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `id` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `create_time` timestamp NULL DEFAULT current_timestamp(),
  `update_time` timestamp NULL DEFAULT current_timestamp(),
  `ban` tinyint(1) NOT NULL DEFAULT 0,
  `is_admin` tinyint(1) NOT NULL DEFAULT 0,
  `last_time_login` timestamp NOT NULL DEFAULT '2002-07-30 17:00:00',
  `last_time_logout` timestamp NOT NULL DEFAULT '2002-07-30 17:00:00',
  `ip_address` varchar(50) DEFAULT NULL,
  `active` int(11) NOT NULL DEFAULT 0,
  `thoi_vang` int(11) NOT NULL DEFAULT 0,
  `server_login` int(11) NOT NULL DEFAULT -1,
  `bd_player` double DEFAULT 1,
  `is_gift_box` tinyint(1) DEFAULT 0,
  `gift_time` varchar(255) DEFAULT '0',
  `reward` longtext DEFAULT NULL,
  `cash` int(11) NOT NULL DEFAULT 0,
  `danap` int(11) NOT NULL DEFAULT 0,
  `token` text NOT NULL,
  `xsrf_token` text NOT NULL,
  `newpass` text NOT NULL,
  `luotquay` int(11) NOT NULL DEFAULT 0,
  `vang` bigint(20) NOT NULL DEFAULT 0,
  `event_point` int(11) NOT NULL DEFAULT 0,
  `vip` int(11) NOT NULL DEFAULT 0,
  `vnd` int(11) NOT NULL,
  `tichdiem` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`id`, `username`, `password`, `email`, `create_time`, `update_time`, `ban`, `is_admin`, `last_time_login`, `last_time_logout`, `ip_address`, `active`, `thoi_vang`, `server_login`, `bd_player`, `is_gift_box`, `gift_time`, `reward`, `cash`, `danap`, `token`, `xsrf_token`, `newpass`, `luotquay`, `vang`, `event_point`, `vip`, `vnd`, `tichdiem`) VALUES
(3500, 'dev1sme', 'dev1sme', '', '2025-02-16 06:16:27', '2025-02-26 02:03:33', 0, 1, '2025-03-02 03:31:53', '2025-03-02 07:00:23', '14.226.105.157', 1, 0, -1, 1, 0, '0', NULL, 0, 0, '', '', '', 0, 0, 0, 0, 31464764, 0);

-- --------------------------------------------------------

--
-- Table structure for table `clan`
--

CREATE TABLE `clan` (
  `id` int(11) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `NAME_2` varchar(4) NOT NULL,
  `slogan` varchar(255) NOT NULL DEFAULT '',
  `img_id` int(11) NOT NULL DEFAULT 0,
  `power_point` bigint(20) NOT NULL DEFAULT 0,
  `max_member` smallint(6) NOT NULL DEFAULT 10,
  `clan_point` int(11) NOT NULL DEFAULT 0,
  `LEVEL` int(11) NOT NULL DEFAULT 1,
  `members` text NOT NULL,
  `tops` text NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `clan`
--
-- INSERT INTO `clan` (`id`, `NAME`, `NAME_2`, `slogan`, `img_id`, `power_point`, `max_member`, `clan_point`, `LEVEL`, `members`, `tops`, `create_time`) VALUES
-- (3, 'Dev Clan', 'DEV', 'Clan của Dev1sme', 0, 0, 50, 0, 1, '[\"ducrio\"]', '[]', '2025-02-24 02:10:36');
-- --------------------------------------------------------

--
-- Table structure for table `exchange`
--

CREATE TABLE `exchange` (
  `thoivang` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `exchange`
--

INSERT INTO `exchange` (`thoivang`) VALUES
(1);

-- --------------------------------------------------------

--
-- Table structure for table `giftcode`
--

CREATE TABLE `giftcode` (
  `id` int(11) NOT NULL,
  `code` text NOT NULL,
  `count_left` int(11) NOT NULL,
  `detail` text NOT NULL,
  `datecreate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `expired` timestamp NOT NULL DEFAULT '2037-12-31 17:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `giftcode`
--

INSERT INTO `giftcode` (`id`, `code`, `count_left`, `detail`, `datecreate`, `expired`) VALUES
(0, 'whis001', 9994, '[{\"id\":457,\"quantity\":10000,\"options\":[{\"id\":73,\"param\":100}]},{\"id\":1416,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1417,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1418,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1419,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1420,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1421,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1422,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1423,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1438,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1439,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]}]', '2025-02-24 02:10:36', '2030-01-01 06:12:53'),
(1, 'whis002', 9999, '[{\"id\":1587,\"quantity\":1,\"options\":[{\"id\":50,\"param\":23},{\"id\":77,\"param\":22},{\"id\":103,\"param\":22}]},{\"id\":1590,\"quantity\":1,\"options\":[{\"id\":50,\"param\":23},{\"id\":77,\"param\":22},{\"id\":103,\"param\":22}]}]', '2025-02-14 23:12:32', '2025-01-01 06:12:53'),
(2, 'whis003', 9999, '[{\"id\":1436,\"quantity\":1,\"options\":[{\"id\":50,\"param\":24},{\"id\":77,\"param\":27},{\"id\":103,\"param\":27},{\"id\":106,\"param\":1}]}]', '2025-02-14 23:12:32', '2025-01-01 06:12:54'),
(3, 'whis004', 9999, '[{\"id\":447,\"quantity\":10000,\"options\":[{\"id\":73,\"param\":100}]},{\"id\":1416,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1417,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1418,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1423,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1420,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1421,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1422,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":965,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1438,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1439,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]}]\r\n', '2025-02-14 23:12:32', '2037-12-31 17:00:00'),
(4, 'whis005', 9999, '[{\"id\":1345,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1346,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1363,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1443,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1455,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1465,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1466,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1468,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1477,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1487,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1513,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1534,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1541,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1554,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1555,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1563,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1578,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1598,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1603,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1625,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1676,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1677,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1678,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1704,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1711,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1724,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1733,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1734,\"quantity\":1,\"options\":[{\"id\":73,\"param\":1}]}]', '2025-02-14 23:12:32', '2037-12-31 17:00:00'),
(5, 'whis006', 9999, '[{\"id\":77,\"quantity\":500000,\"options\":[{\"id\":73,\"param\":1}]]', '2025-02-14 23:12:32', '2037-12-31 17:00:00'),
(6, 'whis007', 9999, '[{\"id\":447,\"quantity\":10000,\"options\":[{\"id\":73,\"param\":100}]},{\"id\":1472,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1473,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1474,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]},{\"id\":1475,\"quantity\":1000,\"options\":[{\"id\":73,\"param\":1}]}]', '2025-02-14 23:12:32', '2037-12-31 17:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `giveaway`
--

CREATE TABLE `giveaway` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `start_date` datetime NOT NULL DEFAULT current_timestamp(),
  `end_date` datetime NOT NULL,
  `prize` varchar(255) NOT NULL,
  `winners` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `status` enum('pending','active','completed','cancelled') DEFAULT 'pending',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `threadId` varchar(255) DEFAULT NULL,
  `msgId` text NOT NULL,
  `participants` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `maxParticipants` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `giveaway`
--

INSERT INTO `giveaway` (`id`, `name`, `description`, `start_date`, `end_date`, `prize`, `winners`, `status`, `created_at`, `updated_at`, `threadId`, `msgId`, `participants`, `maxParticipants`) VALUES
(1, 'Dev', '@dev1sme test', '2024-08-29 15:47:08', '2024-08-29 15:48:08', '@dev1sme test', '[\"861503598693034382\"]', 'active', '2024-08-29 08:47:08', '2024-08-29 08:49:00', '6864601008611641913', '5778798145515', '[]', 10);

-- --------------------------------------------------------

--
-- Table structure for table `history_transaction`
--

CREATE TABLE `history_transaction` (
  `id` int(11) NOT NULL,
  `player_1` varchar(255) NOT NULL,
  `player_2` varchar(255) NOT NULL,
  `item_player_1` text NOT NULL,
  `item_player_2` text NOT NULL,
  `bag_1_before_tran` text NOT NULL,
  `bag_2_before_tran` text NOT NULL,
  `bag_1_after_tran` text NOT NULL,
  `bag_2_after_tran` text NOT NULL,
  `time_tran` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Table structure for table `napthe`
--

CREATE TABLE `napthe` (
  `id` int(11) NOT NULL,
  `user_nap` varchar(100) NOT NULL,
  `telco` varchar(255) NOT NULL,
  `serial` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `amount` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `refNo` varchar(255) NOT NULL,
  `date` text NOT NULL,
  `amount` int(11) NOT NULL,
  `status` varchar(50) NOT NULL,
  `bank` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`id`, `name`, `refNo`, `date`, `amount`, `status`, `bank`) VALUES
(1, '350', '2501', '2024-12-15 00:00:00', 10000, '1', '');

-- --------------------------------------------------------

--
-- Table structure for table `player`
--

CREATE TABLE `player` (
  `id` int(11) NOT NULL,
  `account_id` int(11) DEFAULT NULL,
  `name` varchar(20) NOT NULL,
  `head` int(11) NOT NULL DEFAULT 102,
  `gender` int(11) NOT NULL,
  `have_tennis_space_ship` tinyint(1) DEFAULT 0,
  `clan_id` int(11) NOT NULL DEFAULT -1,
  `data_inventory` text NOT NULL,
  `data_location` text NOT NULL,
  `data_point` text NOT NULL,
  `data_magic_tree` text NOT NULL,
  `items_body` text NOT NULL,
  `items_bag` text NOT NULL,
  `items_box` text NOT NULL,
  `items_box_lucky_round` text NOT NULL,
  `items_daban` text NOT NULL,
  `friends` text NOT NULL,
  `enemies` text NOT NULL,
  `data_intrinsic` text NOT NULL,
  `data_item_time` text NOT NULL,
  `data_task` text NOT NULL,
  `data_mabu_egg` text NOT NULL,
  `data_charm` text NOT NULL,
  `skills` text NOT NULL,
  `skills_shortcut` text NOT NULL,
  `pet` text NOT NULL,
  `data_black_ball` text NOT NULL,
  `data_side_task` text NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `notify` text CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `baovetaikhoan` varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '[]',
  `captcha` varchar(1000) NOT NULL DEFAULT '[]',
  `data_card` varchar(10000) NOT NULL DEFAULT '[]',
  `lasttimepkcommeson` bigint(20) NOT NULL DEFAULT 0,
  `bandokhobau` varchar(250) NOT NULL DEFAULT '[]',
  `doanhtrai` bigint(20) NOT NULL DEFAULT 0,
  `conduongrandoc` varchar(255) NOT NULL DEFAULT '[]',
  `masterDoesNotAttack` text NOT NULL DEFAULT 0,
  `nhanthoivang` varchar(200) NOT NULL DEFAULT '[]',
  `ruonggo` varchar(255) NOT NULL DEFAULT '[]',
  `sieuthanthuy` varchar(255) NOT NULL DEFAULT '[]',
  `vodaisinhtu` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '[]',
  `rongxuong` bigint(20) NOT NULL DEFAULT 0,
  `data_item_event` varchar(1000) NOT NULL DEFAULT '[]',
  `data_luyentap` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '[]',
  `data_clan_task` varchar(255) NOT NULL DEFAULT '[]',
  `data_vip` text DEFAULT NULL,
  `rank` int(11) NOT NULL DEFAULT 0,
  `data_achievement` text NOT NULL DEFAULT '[]',
  `giftcode` text NOT NULL DEFAULT '',
  `event_point` int(11) NOT NULL DEFAULT 0,
  `data_event` text DEFAULT NULL,
  `dataBadges` text DEFAULT NULL,
  `dataTaskBadges` text DEFAULT NULL,
  `firstTimeLogin` timestamp NOT NULL DEFAULT current_timestamp(),
  `BoughtSkill` text DEFAULT NULL,
  `LearnSkill` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `player`
--

-- INSERT INTO `player` (`id`, `account_id`, `name`, `head`, `gender`, `have_tennis_space_ship`, `clan_id`, `data_inventory`, `data_location`, `data_point`, `data_magic_tree`, `items_body`, `items_bag`, `items_box`, `items_box_lucky_round`, `items_daban`, `friends`, `enemies`, `data_intrinsic`, `data_item_time`, `data_task`, `data_mabu_egg`, `data_charm`, `skills`, `skills_shortcut`, `pet`, `data_black_ball`, `data_side_task`, `create_time`, `notify`, `baovetaikhoan`, `captcha`, `data_card`, `lasttimepkcommeson`, `bandokhobau`, `doanhtrai`, `conduongrandoc`, `masterDoesNotAttack`, `nhanthoivang`, `ruonggo`, `sieuthanthuy`, `vodaisinhtu`, `rongxuong`, `data_item_event`, `data_luyentap`, `data_clan_task`, `data_vip`, `rank`, `data_achievement`, `giftcode`, `event_point`, `data_event`, `dataBadges`, `dataTaskBadges`, `firstTimeLogin`, `BoughtSkill`, `LearnSkill`) VALUES
-- (350, 350, 'dev1sme', 32, 1, 0, 3, '[36892000,1047,0,0,0]', '[5,1083,408]', '[0,2479,2500,1000,1000,100,200,10,0,0,0,0,4,2]', '[1,5,0,1740535891759,1740535438625]', '[\"[1,1,\\\"[\\\\\\\"[47,2]\\\\\\\"]\\\",1740535438625]\",\"[7,1,\\\"[\\\\\\\"[6,20]\\\\\\\"]\\\",1740535438625]\",\"[-1,0,\\\"[]\\\",1740923545839]\",\"[-1,0,\\\"[]\\\",1740923545839]\",\"[12,1,\\\"[\\\\\\\"[14,1]\\\\\\\"]\\\",1740535438625]\",\"[-1,0,\\\"[]\\\",1740923545839]\",\"[-1,0,\\\"[]\\\",1740923545839]\",\"[1629,1,\\\"[\\\\\\\"[73,0]\\\\\\\"]\\\",1740806640018]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\"]', '[\"[63,10,\\\"[\\\\\\\"[2,8]\\\\\\\"]\\\",1740535438625]\",\"[13,5,\\\"[\\\\\\\"[48,100]\\\\\\\"]\\\",1740535451628]\",\"[1505,393642,\\\"[\\\\\\\"[73,0]\\\\\\\"]\\\",1740807234218]\",\"[1510,1,\\\"[\\\\\\\"[73,0]\\\\\\\"]\\\",1740808882166]\",\"[1511,1,\\\"[\\\\\\\"[73,0]\\\\\\\"]\\\",1740808889164]\",\"[1508,9940,\\\"[\\\\\\\"[73,0]\\\\\\\"]\\\",1740808801011]\",\"[1509,99999,\\\"[\\\\\\\"[30,1]\\\\\\\",\\\\\\\"[97,30]\\\\\\\",\\\\\\\"[73,0]\\\\\\\"]\\\",1740808831309]\",\"[1507,9990,\\\"[\\\\\\\"[73,0]\\\\\\\"]\\\",1740808876100]\",\"[-1,0,\\\"[]\\\",1740923608327]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\"]', '[\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\",\"[-1,0,\\\"[]\\\",1740923545840]\"]', '[]', '[]', '[]', '[]', '[0,0,0,0,false,0,0,0]', '[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]', '[22,0,0,0]', '[]', '[1740535438628,1740535438628,1740535438628,1740535438628,1740535438628,1740535438628,1740535438628,1740535438628,1740535438628,1740535438628]', '[\"[2,1,0,0]\",\"[3,0,0,0]\",\"[7,0,0,0]\",\"[11,0,0,0]\",\"[12,0,0,0]\",\"[17,0,0,0]\",\"[18,0,0,0]\",\"[19,0,0,0]\"]', '[2,-1,-1,-1,-1,-1,-1,-1,-1,-1]', '[]', '[\"[0,0,0]\",\"[0,0,0]\",\"[0,0,0]\",\"[0,0,0]\",\"[0,0,0]\",\"[0,0,0]\",\"[0,0,0]\"]', '[-1,0,0,0,10,0]', '2025-02-26 02:03:58', 'null', '[0,false,1740535438662]', '[]', '[]', 0, '[0,1740535438662]', 0, '[false,0,false,false]', '0', '[false,0]', '[0,50000000,100,1740535438663,0]', '[false,0,false]', '[false,0,0,0]', 0, '[0,0,0,0,0,0]', '[0,false,-1,0,1740923632443,0,0,0,0,0]', '[-1,0,0,0,5,0]', '[0,0,false,false,false,0,0]', 6, '[\"[0,false]\",\"[0,false]\",\"[0,false]\",\"[0,false]\",\"[0,false]\",\"[47,false]\",\"[0,false]\",\"[0,false]\",\"[1549000,false]\",\"[0,false]\",\"[0,false]\",\"[0,false]\",\"[0,false]\",\"[3,false]\",\"[0,false]\",\"[0,false]\",\"[0,false]\",\"[0,false]\",\"[0,false]\",\"[0,false]\"]', '[]', 0, '[0,0,0,0,0,0,false,false,false,false]', '[]', '[{\"id\":\"1\",\"count\":\"0\",\"countMax\":\"1000000\",\"idBadgesReward\":\"218\"},{\"id\":\"2\",\"count\":\"0\",\"countMax\":\"100\",\"idBadgesReward\":\"219\"},{\"id\":\"3\",\"count\":\"0\",\"countMax\":\"300\",\"idBadgesReward\":\"220\"},{\"id\":\"4\",\"count\":\"0\",\"countMax\":\"5\",\"idBadgesReward\":\"221\"},{\"id\":\"5\",\"count\":\"0\",\"countMax\":\"1\",\"idBadgesReward\":\"222\"},{\"id\":\"6\",\"count\":\"0\",\"countMax\":\"10\",\"idBadgesReward\":\"223\"},{\"id\":\"7\",\"count\":\"0\",\"countMax\":\"20\",\"idBadgesReward\":\"-1\"},{\"id\":\"8\",\"count\":\"0\",\"countMax\":\"5\",\"idBadgesReward\":\"-1\"},{\"id\":\"9\",\"count\":\"0\",\"countMax\":\"500\",\"idBadgesReward\":\"224\"},{\"id\":\"10\",\"count\":\"0\",\"countMax\":\"30\",\"idBadgesReward\":\"225\"},{\"id\":\"11\",\"count\":\"0\",\"countMax\":\"30\",\"idBadgesReward\":\"-1\"}]', '2025-02-26 02:03:58', '[2]', '[-1,-1,0]');

-- --------------------------------------------------------

--
-- Table structure for table `shop_ky_gui`
--

CREATE TABLE `shop_ky_gui` (
  `id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `player_name` text NOT NULL,
  `tab` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `gold` int(11) NOT NULL,
  `gem` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `itemOption` varchar(10000) NOT NULL DEFAULT '[]',
  `lastTime` bigint(20) NOT NULL,
  `isBuy` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Table structure for table `super_rank`
--

CREATE TABLE `super_rank` (
  `id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `name` text NOT NULL,
  `rank` int(11) NOT NULL,
  `last_pk_time` bigint(20) NOT NULL,
  `last_reward_time` bigint(20) NOT NULL,
  `ticket` int(11) NOT NULL,
  `win` int(11) NOT NULL,
  `lose` int(11) NOT NULL,
  `history` text NOT NULL,
  `info` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `super_rank`
--

-- INSERT INTO `super_rank` (`id`, `player_id`, `name`, `rank`, `last_pk_time`, `last_reward_time`, `ticket`, `win`, `lose`, `history`, `info`) VALUES
-- (1, 350, 'dev1sme', 1, 0, 0, 0, 0, 0, '[]', '[]');
-- --------------------------------------------------------

--
-- Table structure for table `task_badges_template`
--

CREATE TABLE `task_badges_template` (
  `id` int(11) NOT NULL,
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `maxCount` int(11) NOT NULL DEFAULT 0,
  `idBadgesReward` int(11) NOT NULL DEFAULT -1
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `task_badges_template`
--

INSERT INTO `task_badges_template` (`id`, `NAME`, `maxCount`, `idBadgesReward`) VALUES
(1, 'Nạp Tích luỹ 1 Triệu Trong Ngày', 1000000, 218),
(2, 'Ước Rồng Thần 1 Sao X100 Lần', 100, 219),
(3, 'Hạ Gục Cumber, Black Goku, Cooler, Xên ( 300 Lần )', 300, 220),
(4, 'Đập 5 Trang Bị +7 Trong Ngày', 5, 221),
(5, 'Top 1 Đại Hội Võ Đài Siêu Hạng', 1, 222),
(6, 'Hoàn Thành 10 Nhiệm Vụ Siêu Khó Tại Bò Mộng', 10, 223),
(7, 'Đánh Bại, Hoặc Cho Xương Sói 20 Lần', 20, -1),
(8, 'Hoàn Thành Nhiệm Vụ 5 Lần Cho Xinbato Nước', 5, -1),
(9, 'Nhặt Đồ Trong Ngày 500 Lần', 500, 224),
(10, 'Tiêu diệt 30 Boss Ăn Trộm', 30, 225),
(11, 'Tiêu Diệt 30 Boss Ở Dơ', 30, -1);

-- --------------------------------------------------------

--
-- Table structure for table `transaction_banking`
--

CREATE TABLE `transaction_banking` (
  `id` bigint(20) NOT NULL,
  `player_id` bigint(20) NOT NULL,
  `amount` bigint(20) NOT NULL,
  `description` varchar(6) NOT NULL,
  `status` bit(1) DEFAULT b'0',
  `is_recieve` bit(1) DEFAULT b'0',
  `last_time_check` timestamp NOT NULL DEFAULT current_timestamp(),
  `created_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `transaction_banking`
--

INSERT INTO `transaction_banking` (`id`, `player_id`, `amount`, `description`, `status`, `is_recieve`, `last_time_check`, `created_date`) VALUES
(2641, 51016, 10000, 'QYF4I2', b'0', b'0', '2024-09-20 14:56:17', '2024-09-20 14:56:17');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `uid` text NOT NULL,
  `fullname` text NOT NULL,
  `coins` bigint(20) NOT NULL DEFAULT 0,
  `ban` tinyint(1) NOT NULL DEFAULT 0,
  `last_daily_claim` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `uid`, `fullname`, `coins`, `ban`, `last_daily_claim`) VALUES
(1, '140755075297616678', 'Dev', 0, 0, NULL),

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD UNIQUE KEY `username` (`username`) USING BTREE;

--
-- Indexes for table `clan`
--
ALTER TABLE `clan`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Indexes for table `giftcode`
--
ALTER TABLE `giftcode`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Indexes for table `giveaway`
--
ALTER TABLE `giveaway`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Indexes for table `history_transaction`
--
ALTER TABLE `history_transaction`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Indexes for table `napthe`
--
ALTER TABLE `napthe`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Indexes for table `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD UNIQUE KEY `account_id` (`account_id`) USING BTREE;

--
-- Indexes for table `shop_ky_gui`
--
ALTER TABLE `shop_ky_gui`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Indexes for table `super_rank`
--
ALTER TABLE `super_rank`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Indexes for table `task_badges_template`
--
ALTER TABLE `task_badges_template`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Indexes for table `transaction_banking`
--
ALTER TABLE `transaction_banking`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1000192;

--
-- AUTO_INCREMENT for table `giftcode`
--
ALTER TABLE `giftcode`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1247;

--
-- AUTO_INCREMENT for table `giveaway`
--
ALTER TABLE `giveaway`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT for table `history_transaction`
--
ALTER TABLE `history_transaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=260;

--
-- AUTO_INCREMENT for table `napthe`
--
ALTER TABLE `napthe`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `player`
--
ALTER TABLE `player`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1001908;

--
-- AUTO_INCREMENT for table `shop_ky_gui`
--
ALTER TABLE `shop_ky_gui`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `super_rank`
--
ALTER TABLE `super_rank`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=196;

--
-- AUTO_INCREMENT for table `task_badges_template`
--
ALTER TABLE `task_badges_template`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `transaction_banking`
--
ALTER TABLE `transaction_banking`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2642;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1488;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `player`
--
ALTER TABLE `player`
  ADD CONSTRAINT `player_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
