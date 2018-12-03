-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Lun 25 Décembre 2017 à 11:00
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `test`
--

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE IF NOT EXISTS `client` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `nom` varchar(10) NOT NULL,
  `password` varchar(10) NOT NULL,
  `age` int(10) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `ville` varchar(10) DEFAULT NULL,
  `prenom` varchar(10) DEFAULT NULL,
  `pays` varchar(10) DEFAULT NULL,
  `statut` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=47 ;

--
-- Contenu de la table `client`
--

INSERT INTO `client` (`id`, `nom`, `password`, `age`, `email`, `ville`, `prenom`, `pays`, `statut`) VALUES
(7, 'haddad', '123', 23, 'hamza16haddad@gmail.com', 'Fnideq', 'hamza', 'maroc', 'hamdo lilaah '),
(15, 'jabbar', '123', 22, 'hanan@exemple.com', 'tetouan', 'hanan', 'maroc', 'statut de hanan');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
