-- MySQL Script generated by MySQL Workbench
-- Tue 08 May 2018 19:59:57 EET
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema Book_Store
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `Book_Store` ;

-- -----------------------------------------------------
-- Schema Book_Store
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Book_Store` DEFAULT CHARACTER SET utf8 ;
USE `Book_Store` ;

-- -----------------------------------------------------
-- Table `Book_Store`.`Publisher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Book_Store`.`Publisher` ;

CREATE TABLE IF NOT EXISTS `Book_Store`.`Publisher` (
  `Name` VARCHAR(255) NOT NULL,
  `Address` VARCHAR(255) NULL,
  `Telephone` VARCHAR(15) NULL,
  PRIMARY KEY (`Name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Book_Store`.`Book`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Book_Store`.`Book` ;

CREATE TABLE IF NOT EXISTS `Book_Store`.`Book` (
  `ISBN` INT NOT NULL,
  `Title` VARCHAR(255) NOT NULL,
  `Publisher_Name` VARCHAR(255) NOT NULL,
  `Publication_Year` VARCHAR(4) NULL,
  `Selling_Price` INT NOT NULL,
  `Category` VARCHAR(10) NOT NULL,
  `no_Copies` INT NOT NULL,
  `Min_quantity` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`ISBN`),
  UNIQUE INDEX `Title_UNIQUE` (`Title` ASC),
  INDEX `fk_Book_Publisher_idx` (`Publisher_Name` ASC),
  CONSTRAINT `fk_Book_Publisher`
    FOREIGN KEY (`Publisher_Name`)
    REFERENCES `Book_Store`.`Publisher` (`Name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Book_Store`.`Book_Order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Book_Store`.`Book_Order` ;

CREATE TABLE IF NOT EXISTS `Book_Store`.`Book_Order` (
  `ISBN` INT NOT NULL,
  `num_of_books` INT NOT NULL,
  PRIMARY KEY (`ISBN`),
  CONSTRAINT `fk_Book_Order_Book1`
    FOREIGN KEY (`ISBN`)
    REFERENCES `Book_Store`.`Book` (`ISBN`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Book_Store`.`Author`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Book_Store`.`Author` ;

CREATE TABLE IF NOT EXISTS `Book_Store`.`Author` (
  `Author_name` VARCHAR(255) NOT NULL,
  `Telephone` VARCHAR(45) NULL,
  PRIMARY KEY (`Author_name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Book_Store`.`Book_has_Author`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Book_Store`.`Book_has_Author` ;

CREATE TABLE IF NOT EXISTS `Book_Store`.`Book_has_Author` (
  `Book_ISBN` INT NOT NULL,
  `Author_Author_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`Book_ISBN`, `Author_Author_name`),
  INDEX `fk_Book_has_Author_Author1_idx` (`Author_Author_name` ASC),
  INDEX `fk_Book_has_Author_Book1_idx` (`Book_ISBN` ASC),
  CONSTRAINT `fk_Book_has_Author_Book1`
    FOREIGN KEY (`Book_ISBN`)
    REFERENCES `Book_Store`.`Book` (`ISBN`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Book_has_Author_Author1`
    FOREIGN KEY (`Author_Author_name`)
    REFERENCES `Book_Store`.`Author` (`Author_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Book_Store`.`Manager`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Book_Store`.`Manager` ;

CREATE TABLE IF NOT EXISTS `Book_Store`.`Manager` (
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(255) NULL,
  `lastname` VARCHAR(255) NULL,
  `email` VARCHAR(255) NULL,
  `phone` VARCHAR(11) NULL,
  `shipping_address` VARCHAR(255) NULL,
  PRIMARY KEY (`username`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Book_Store`.`Customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Book_Store`.`Customer` ;

CREATE TABLE IF NOT EXISTS `Book_Store`.`Customer` (
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(255) NULL,
  `lastname` VARCHAR(255) NULL,
  `email` VARCHAR(255) NULL,
  `phone` VARCHAR(11) NULL,
  `shipping_address` VARCHAR(255) NULL,
  PRIMARY KEY (`username`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `Book_Store`.`Customer_has_Book` ;

CREATE TABLE IF NOT EXISTS `Book_Store`.`Customer_has_Book` (
  `id` INT auto_increment,
  `Book_ISBN` INT NOT NULL,
  `username` VARCHAR(255) NOT NULL,
  `total_price` INT,
  `datey` date,
  PRIMARY KEY (`id`),
  INDEX `fk_Customer_has_Book_Customer1_idx` (`username` ASC),
  INDEX `fk_Customer_has_Book_Book1_idx` (`Book_ISBN` ASC),
  CONSTRAINT `fk_Customer_has_Book_Book1`
    FOREIGN KEY (`Book_ISBN`)
    REFERENCES `Book_Store`.`Book` (`ISBN`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Customer_has_Book_Customer1`
    FOREIGN KEY (`username`)
    REFERENCES `Book_Store`.`Customer` (`username`)
    ON DELETE cascade
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


Create index idx_title
on Book (title);

Create index idx_category
on Book (category);

Create index idx_date
on Customer_has_Book (datey);

delete from Customer where username = 'ahmedezzat';
insert into Customer(username, password) values ('ahmedezzat', 'aaaaa');
Select * from Customer where username = 'ahmedezzat' and password = 'aaaaa'
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;