-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-08-2026 a las 16:08:34
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `kd_electronics`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `codigo` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `precio_base` decimal(10,2) DEFAULT NULL CHECK (`precio_base` > 0),
  `precio_venta` decimal(10,2) DEFAULT NULL CHECK (`precio_venta` >= `precio_base`),
  `categoria` varchar(50) NOT NULL,
  `cantidad` int(11) DEFAULT NULL CHECK (`cantidad` >= 0),
  `estado` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`codigo`, `nombre`, `descripcion`, `precio_base`, `precio_venta`, `categoria`, `cantidad`, `estado`) VALUES
('PRODUCTO-1001', 'Smart TV 55\"', 'Televisor 4K UHD 55\"', 1200000.00, 1500000.00, 'Televisores', 10, 1),
('PRODUCTO-1002', 'Televisor Smart TV 45\"', 'Televisor smart 4K UHD 45\"', 1250000.00, 1550000.00, 'Televisores', 8, 1),
('PRODUCTO-1003', 'Portatil Dell 3550', 'Portatil Dell procesador i7 de 13th Generacion- Memoria RAM 16GB-SSD 512GB', 1200000.00, 1500000.00, 'Computo', 10, 1),
('PRODUCTO-1004', 'Portatil HP 14xxx', 'portatil HP- procesador Core i5- 8GB de RAM- SSD 256- grafica intregrada', 1200000.00, 1500000.00, 'computo', 10, 1),
('PRODUCTO-1005', 'TELEVISOR 65\" SAMSUNG', 'TV SAMSUNG 65\" Pulgadas 165,1 cm 65M80H 4K-UHD Mini LEDSmart TV con IA', 1200000.00, 1500000.00, 'Televisores', 10, 1),
('PRODUCTO-1006', 'Celular S25 ultra', 'Pantalla: Super AMOLED de 6.6 pulgadasResolucion: Full HD+ (1080 x 2340 pixeles)Tasa de refresco: 120 HzProteccion: Gorilla Glass Victus+', 1200000.00, 1350000.00, 'celulares', 10, 1),
('PRODUCTO-1007', 'Telefono IP Yealink', 'Telefono IP Yealink modelo: T21 E2', 160000.00, 200000.00, 'Telefonia', 20, 1),
('PRODUCTO-1008', 'Telefono IP Yealink', 'Telefono IP yealink sip-t31p', 245000.00, 300000.00, 'Telefonia', 10, 1),
('PRODUCTO-1009', 'Celular Iphone 17 Pro Max', 'Iphone 17 Pro Max-XDR OLED de 6.9-Chip A19 Pro-12 GB', 6799000.00, 7500000.00, 'Celulares', 5, 1),
('PRODUCTO-1010', 'MONITOR 22\"', 'MONITOR SAMSUNG 22\" REFERENCIA HD124', 1200000.00, 1550000.00, 'MONITORES', 5, 1),
('PRODUCTO-1011', 'MONITOR LG 24\"', 'MONITOR LG 24\" CURVO REF. MG35G', 1600000.00, 1900000.00, 'MONITORES', 6, 0),
('PRODUCTO-1012', 'LICENCIA WINDOWS 11 PRO', 'LICENCIA WIN 11 PRO ALL LENGUAJE 64 BITS', 550000.00, 750000.00, 'LICENCIAS', 10, 1),
('PRODUCTO-1013', 'CELULAR IPHONE 14 PRO MAX', 'CELULAR IPHONE 14 PRO MAX- pantalla OLED de 6.7 pulgadas -  camara principal de 48 MP - Clasificacion IP68.', 2080000.00, 2550000.00, 'CELULARES', 15, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`codigo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
