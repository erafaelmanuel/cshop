-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 21, 2018 at 10:59 AM
-- Server version: 5.6.26
-- PHP Version: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbcshop`
--

-- --------------------------------------------------------

--
-- Table structure for table `tblattribute`
--

CREATE TABLE IF NOT EXISTS `tblattribute` (
  `id` bigint(20) NOT NULL,
  `title` varchar(45) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tblcategory`
--

CREATE TABLE IF NOT EXISTS `tblcategory` (
  `id` bigint(20) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `parentId` bigint(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblcategory`
--

INSERT INTO `tblcategory` (`id`, `name`, `description`, `parentId`) VALUES
(1, 'Cloth', 'Should we start class now, or should we wait for everyone to get here?', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `tblitem`
--

CREATE TABLE IF NOT EXISTS `tblitem` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `price` decimal(11,2) DEFAULT NULL,
  `discount` decimal(11,2) DEFAULT NULL,
  `categoryId` bigint(20) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblitem`
--

INSERT INTO `tblitem` (`id`, `name`, `description`, `price`, `discount`, `categoryId`) VALUES
(2, 'Black Pants', 'A purple pig and a green donkey flew a kite in the middle of the night and ended up sunburnt.', '200.00', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tblitem_attribute`
--

CREATE TABLE IF NOT EXISTS `tblitem_attribute` (
  `id` bigint(20) NOT NULL,
  `itemId` bigint(20) NOT NULL,
  `attributeId` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tblitem_image`
--

CREATE TABLE IF NOT EXISTS `tblitem_image` (
  `id` bigint(20) NOT NULL,
  `itemId` bigint(20) NOT NULL,
  `src` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tblitem_tag`
--

CREATE TABLE IF NOT EXISTS `tblitem_tag` (
  `id` bigint(20) NOT NULL,
  `itemId` bigint(20) NOT NULL,
  `tagId` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tblrelated_tag`
--

CREATE TABLE IF NOT EXISTS `tblrelated_tag` (
  `id` bigint(20) NOT NULL,
  `tagId` bigint(20) NOT NULL,
  `relatedTagId` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tblrole`
--

CREATE TABLE IF NOT EXISTS `tblrole` (
  `id` bigint(20) NOT NULL,
  `name` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbltag`
--

CREATE TABLE IF NOT EXISTS `tbltag` (
  `id` bigint(20) NOT NULL,
  `title` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `keyword` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbluser`
--

CREATE TABLE IF NOT EXISTS `tbluser` (
  `id` bigint(20) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2018766583248179 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbluser`
--

INSERT INTO `tbluser` (`id`, `name`, `email`, `username`, `password`, `enabled`) VALUES
(2018766583248178, 'Rafael Manuel', 'erafaelmanuel@gmail.com', 'erafaelmanuel', '123', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbluser_role`
--

CREATE TABLE IF NOT EXISTS `tbluser_role` (
  `id` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tblverification_token`
--

CREATE TABLE IF NOT EXISTS `tblverification_token` (
  `id` bigint(20) NOT NULL,
  `token` varchar(100) DEFAULT NULL,
  `expiryDate` varchar(45) DEFAULT NULL,
  `userId` bigint(20) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tblattribute`
--
ALTER TABLE `tblattribute`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tblcategory`
--
ALTER TABLE `tblcategory`
  ADD PRIMARY KEY (`id`),
  ADD KEY `parentId` (`parentId`);

--
-- Indexes for table `tblitem`
--
ALTER TABLE `tblitem`
  ADD PRIMARY KEY (`id`),
  ADD KEY `categoryId` (`categoryId`);

--
-- Indexes for table `tblitem_attribute`
--
ALTER TABLE `tblitem_attribute`
  ADD PRIMARY KEY (`id`),
  ADD KEY `itemId` (`itemId`),
  ADD KEY `attributeId` (`attributeId`);

--
-- Indexes for table `tblitem_image`
--
ALTER TABLE `tblitem_image`
  ADD PRIMARY KEY (`id`),
  ADD KEY `itemId` (`itemId`);

--
-- Indexes for table `tblitem_tag`
--
ALTER TABLE `tblitem_tag`
  ADD PRIMARY KEY (`id`),
  ADD KEY `itemId` (`itemId`),
  ADD KEY `tagId` (`tagId`);

--
-- Indexes for table `tblrelated_tag`
--
ALTER TABLE `tblrelated_tag`
  ADD PRIMARY KEY (`id`),
  ADD KEY `tagId` (`tagId`),
  ADD KEY `relatedTagId` (`relatedTagId`);

--
-- Indexes for table `tblrole`
--
ALTER TABLE `tblrole`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbltag`
--
ALTER TABLE `tbltag`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbluser`
--
ALTER TABLE `tbluser`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbluser_role`
--
ALTER TABLE `tbluser_role`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userId` (`userId`),
  ADD KEY `roleId` (`roleId`);

--
-- Indexes for table `tblverification_token`
--
ALTER TABLE `tblverification_token`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userId` (`userId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tblattribute`
--
ALTER TABLE `tblattribute`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tblcategory`
--
ALTER TABLE `tblcategory`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `tblitem`
--
ALTER TABLE `tblitem`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `tblitem_attribute`
--
ALTER TABLE `tblitem_attribute`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tblitem_image`
--
ALTER TABLE `tblitem_image`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tblitem_tag`
--
ALTER TABLE `tblitem_tag`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tblrelated_tag`
--
ALTER TABLE `tblrelated_tag`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tblrole`
--
ALTER TABLE `tblrole`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbltag`
--
ALTER TABLE `tbltag`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbluser`
--
ALTER TABLE `tbluser`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2018766583248179;
--
-- AUTO_INCREMENT for table `tbluser_role`
--
ALTER TABLE `tbluser_role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tblverification_token`
--
ALTER TABLE `tblverification_token`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `tblcategory`
--
ALTER TABLE `tblcategory`
  ADD CONSTRAINT `tblcategory_ibfk_1` FOREIGN KEY (`parentId`) REFERENCES `tblcategory` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tblitem`
--
ALTER TABLE `tblitem`
  ADD CONSTRAINT `tblitem_ibfk_1` FOREIGN KEY (`categoryId`) REFERENCES `tblcategory` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tblitem_attribute`
--
ALTER TABLE `tblitem_attribute`
  ADD CONSTRAINT `tblitem_attribute_ibfk_1` FOREIGN KEY (`itemId`) REFERENCES `tblitem` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tblitem_attribute_ibfk_2` FOREIGN KEY (`attributeId`) REFERENCES `tblattribute` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tblitem_image`
--
ALTER TABLE `tblitem_image`
  ADD CONSTRAINT `tblitem_image_ibfk_1` FOREIGN KEY (`itemId`) REFERENCES `tblitem` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tblitem_tag`
--
ALTER TABLE `tblitem_tag`
  ADD CONSTRAINT `tblitem_tag_ibfk_1` FOREIGN KEY (`itemId`) REFERENCES `tblitem` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tblitem_tag_ibfk_2` FOREIGN KEY (`tagId`) REFERENCES `tbltag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tblrelated_tag`
--
ALTER TABLE `tblrelated_tag`
  ADD CONSTRAINT `tblrelated_tag_ibfk_1` FOREIGN KEY (`tagId`) REFERENCES `tbltag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tblrelated_tag_ibfk_2` FOREIGN KEY (`relatedTagId`) REFERENCES `tbltag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tbluser_role`
--
ALTER TABLE `tbluser_role`
  ADD CONSTRAINT `tbluser_role_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `tbluser` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tbluser_role_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `tblrole` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tblverification_token`
--
ALTER TABLE `tblverification_token`
  ADD CONSTRAINT `tblverification_token_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `tbluser` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
