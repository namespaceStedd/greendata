-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Апр 02 2024 г., 22:30
-- Версия сервера: 8.0.31
-- Версия PHP: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `greendata`
--

-- --------------------------------------------------------

--
-- Структура таблицы `organization_form`
--

DROP TABLE IF EXISTS `organization_form`;
CREATE TABLE IF NOT EXISTS `organization_form` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(49) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `organization_form`
--

INSERT INTO `organization_form` (`id`, `name`) VALUES
(1, 'Физическое лицо'),
(2, 'Юридическое лицо'),
(3, 'Открытое акционерное общество'),
(4, 'Закрытое акционерное общество'),
(5, 'Товарищество'),
(6, 'Кооператив'),
(7, 'Индивидуальный предприниматель'),
(8, 'Инвестиционный фонд'),
(9, 'Представительство'),
(10, 'Филиал'),
(11, 'Учреждение'),
(12, 'Профсоюз'),
(13, 'Политическая партия'),
(14, 'Общество с ограниченной ответственностью'),
(15, 'Унитарное предприятие'),
(16, 'Хозяйственное партнёрство'),
(17, 'Садоводческое товарищество');
COMMIT;

-- --------------------------------------------------------

--
-- Структура таблицы `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(101) COLLATE utf8mb4_unicode_ci NOT NULL,
  `shortname` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(101) COLLATE utf8mb4_unicode_ci NOT NULL,
  `organization_form` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `shortname` (`shortname`),
  FOREIGN KEY (organization_form) REFERENCES organization_form (id)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `customer`
--

INSERT INTO `customer` (`id`, `name`, `shortname`, `address`, `organization_form`) VALUES
(1, 'Корпорация Уоллес', 'Уоллес', 'Земля', 5),
(2, 'Автоматизированные системы Фаро', 'АСФ', 'Юта', 12),
(3, 'Уэйн Энтерпрайзес', 'Уэйн', 'Готэм-Сити', 8),
(4, 'Галактическая федерация', 'Федерация', 'Громфлом Прайм', 13),
(5, 'Ночной Дозор', 'Дозор', 'Москва', 17);

-- --------------------------------------------------------

--
-- Структура таблицы `bank`
--

DROP TABLE IF EXISTS `bank`;
CREATE TABLE IF NOT EXISTS `bank` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bic` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `bic` (`bic`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `bank`
--

INSERT INTO `bank` (`id`, `name`, `bic`) VALUES
(1, 'Тинькофф Банк', 44525974),
(2, 'Клюква', 45773790),
(3, 'Росбанк', 44525256),
(4, 'Сбербанк', 44525225),
(5, 'ВТБ', 44525187);

-- --------------------------------------------------------

--
-- Структура таблицы `contribution`
--

DROP TABLE IF EXISTS `contribution`;
CREATE TABLE IF NOT EXISTS `contribution` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer` int NOT NULL,
  `bank` int NOT NULL,
  `opening` date NOT NULL,
  `percentage` int NOT NULL,
  `duration` int NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (customer) REFERENCES customer (id),
  FOREIGN KEY (bank) REFERENCES bank (id)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `contribution`
--

INSERT INTO `contribution` (`id`, `customer`, `bank`, `opening`, `percentage`, `duration`) VALUES
(1, 2, 1, '2024-03-28', 17, 24),
(2, 5, 2, '2024-03-29', 15, 12),
(3, 3, 3, '2024-03-30', 16, 6),
(4, 1, 4, '2024-03-31', 10, 1),
(5, 4, 5, '2024-04-01', 13, 3);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
