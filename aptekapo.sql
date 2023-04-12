-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 10 Lut 2022, 19:48
-- Wersja serwera: 10.4.21-MariaDB
-- Wersja PHP: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `aptekapo`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `faktury`
--

CREATE TABLE `faktury` (
  `id_faktury` int(11) NOT NULL,
  `data_wystawienia_faktury` datetime(6) DEFAULT NULL,
  `id_klienta` int(11) DEFAULT NULL,
  `klienci_id_klienta` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `faktury`
--

INSERT INTO `faktury` (`id_faktury`, `data_wystawienia_faktury`, `id_klienta`, `klienci_id_klienta`) VALUES
(1, '2022-02-03 23:17:36.000000', 1, 1),
(2, '2022-02-04 00:00:57.000000', 3, 3),
(3, '2022-02-04 00:06:55.000000', 6, 6),
(5, '2022-02-04 00:14:16.000000', 10, 10),
(6, '2022-02-04 12:20:44.000000', 12, 12),
(7, '2022-02-04 12:37:55.000000', 13, 13),
(8, '2022-02-04 13:46:09.000000', 12, 12),
(9, '2022-02-04 17:43:08.000000', 1, 1),
(10, '2022-02-08 14:58:02.000000', 1, 1),
(11, '2022-02-08 15:08:11.000000', 1, 1),
(12, '2022-02-09 10:09:19.000000', 13, 13),
(13, '2022-02-09 18:57:14.000000', 13, 13);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `faktury_produkty`
--

CREATE TABLE `faktury_produkty` (
  `id_faktury_produkty` int(11) NOT NULL,
  `cena_zakupu` double DEFAULT NULL,
  `id_faktury` int(11) DEFAULT NULL,
  `id_produktu` int(11) DEFAULT NULL,
  `ilosc_sztuk` int(11) DEFAULT NULL,
  `faktury_id_faktury` int(11) DEFAULT NULL,
  `produkty_id_produktu` int(11) DEFAULT NULL,
  `nazwa_produktu` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `faktury_produkty`
--

INSERT INTO `faktury_produkty` (`id_faktury_produkty`, `cena_zakupu`, `id_faktury`, `id_produktu`, `ilosc_sztuk`, `faktury_id_faktury`, `produkty_id_produktu`, `nazwa_produktu`) VALUES
(1, 25.5, 1, 1, 5, 1, 1, 'Polopiryna'),
(2, 5.5, 1, 2, 5, 1, 2, 'Ibum'),
(3, 5.55, 1, 3, 5, 1, 3, 'Nospa'),
(4, 15.99, 1, 4, 5, 1, 4, 'Polopiryna LEKAM'),
(5, 15.99, 1, 5, 5, 1, 5, 'Apap'),
(6, 15.99, 2, 5, 77, 2, 5, 'Apap'),
(7, 15.99, 2, 5, 55, 2, 5, 'Apap'),
(8, 5.5, 2, 2, 10, 2, 2, 'Ibum'),
(9, 15.99, 2, 5, 8, 2, 5, 'Apap'),
(10, 15.99, 3, 5, 1, 3, 5, 'Apap'),
(11, 15.99, 5, 5, 300, 5, 5, 'Apap'),
(12, 15.99, 6, 5, 100, 6, 5, 'Apap'),
(13, 5.5, 6, 2, 6, 6, 2, 'Ibum'),
(14, 25.5, 6, 1, 40, 6, 1, 'Polopiryna'),
(15, 25.5, 7, 1, 150, 7, 1, 'Polopiryna'),
(16, 15.99, 7, 4, 55, 7, 4, 'Polopiryna LEKAM'),
(17, 15.99, 7, 5, 100, 7, 5, 'Apap'),
(18, 5.5, 7, 2, 5, 7, 2, 'Ibum'),
(19, 15.99, 8, 5, 100, 8, 5, 'Apap'),
(20, 15.99, 9, 4, 10, 9, 4, 'Polopiryna LEKAM'),
(21, 25.5, 10, 1, 20, 10, 1, 'Polopiryna'),
(22, 15.99, 10, 4, 90, 10, 4, 'Polopiryna LEKAM'),
(23, 5.55, 11, 3, 20, 11, 3, 'Nospa'),
(24, 250, 12, 6, 100, 12, 6, 'Sterydiany'),
(25, 15.99, 12, 4, 100, 12, 4, 'Polopiryna LEKAM'),
(26, 250, 13, 6, 100, 13, 6, 'Sterydiany');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `kategorie`
--

CREATE TABLE `kategorie` (
  `id_kategorii` int(11) NOT NULL,
  `nazwa_kategorii` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `kategorie`
--

INSERT INTO `kategorie` (`id_kategorii`, `nazwa_kategorii`) VALUES
(1, 'Problemy pokarmowe'),
(2, 'Krążenie, krew'),
(3, 'Serce'),
(4, 'Zdrowa skóra'),
(5, 'Antykoncepcja, ciąża, zdrowie intymne'),
(6, 'Leczenie hormonalne'),
(7, 'Leki przeciwinfekcyjne'),
(8, 'Odporność, leci przeciwnowotworowe'),
(9, 'Stawy, kości, mięśnie'),
(10, 'Układ nerwowy'),
(11, 'Przeciw pasożytom i owadom'),
(12, 'Przeziębienie i alergia'),
(13, 'Zdrowy słuch i wzrok');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `klienci`
--

CREATE TABLE `klienci` (
  `id_klienta` int(11) NOT NULL,
  `adres` varchar(100) DEFAULT NULL,
  `imie` varchar(100) DEFAULT NULL,
  `nazwisko` varchar(100) DEFAULT NULL,
  `pesel` varchar(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `klienci`
--

INSERT INTO `klienci` (`id_klienta`, `adres`, `imie`, `nazwisko`, `pesel`) VALUES
(1, 'Zakopane', 'Adam', 'Małysz', '11111111112'),
(2, 'Wisła', 'Piotr', 'Żyła', '22222222222'),
(3, 'Rzeszów', 'Rafał', 'Gawlak', '33333333333'),
(6, 'Frysztak', 'Krystian', 'Klosowicz', '97051604156'),
(8, 'Sanok', 'Kamil', 'Dejta', '99101299998'),
(10, 'Warszawa', 'Jacek', 'Nowak', '99051611111'),
(11, 'Krosno', 'Kamil', 'Stoch', '99010111111'),
(12, 'Zakopane', 'Klemens', 'Muranka', '95050511111'),
(13, 'Rzeszów', 'Mariusz', 'Pudzianowski', '88121211111'),
(14, 'Fryszytak 125/A ', 'Kamil', 'Robak', '99051699994'),
(15, 'Polna 13', 'Adam', 'Bąk', '99101299997'),
(16, 'Polna 17', 'Adam', 'Brown', '99051699993'),
(17, 'Krakowska 13', 'Adam', 'Tabak', '99050522222');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `producenci`
--

CREATE TABLE `producenci` (
  `id_producenta` int(11) NOT NULL,
  `NIP` varchar(11) DEFAULT NULL,
  `adres` varchar(100) DEFAULT NULL,
  `nazwa_producenta` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `producenci`
--

INSERT INTO `producenci` (`id_producenta`, `NIP`, `adres`, `nazwa_producenta`) VALUES
(1, '1111111111', 'New Brunswick, New Jersey, Stany Zjednoczone', 'Johnson & Johnson'),
(2, '2222222222', 'New York, Stany Zjedoczone', 'Pfizer'),
(3, '1234567899', 'Bazylea, Szwajcaria', 'Roche'),
(4, '2222222222', 'Meksyk', 'Kalmora'),
(6, '1111111111', 'Bordeux', 'Laa roche Posay');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `produkty`
--

CREATE TABLE `produkty` (
  `id_produktu` int(11) NOT NULL,
  `cena` double DEFAULT NULL,
  `id_kategorii` int(11) DEFAULT NULL,
  `id_producenta` int(11) DEFAULT NULL,
  `ilosc_sztuk` int(11) DEFAULT NULL,
  `nazwa_produktu` varchar(100) DEFAULT NULL,
  `kategorie_id_kategorii` int(11) DEFAULT NULL,
  `producenci_id_producenta` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `produkty`
--

INSERT INTO `produkty` (`id_produktu`, `cena`, `id_kategorii`, `id_producenta`, `ilosc_sztuk`, `nazwa_produktu`, `kategorie_id_kategorii`, `producenci_id_producenta`) VALUES
(1, 25.5, 7, 4, 0, 'Polopiryna', 7, 4),
(2, 5.5, 12, 4, 50, 'Ibum', 12, 4),
(3, 5.55, 1, 2, 10, 'Nospa', 1, 2),
(4, 15.99, 12, 3, 300, 'Polopiryna LEKAM', 12, 3),
(5, 15.99, 6, 3, 700, 'Apap', 6, 3),
(6, 250, 9, 1, 1300, 'Sterydiany', 9, 1);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `faktury`
--
ALTER TABLE `faktury`
  ADD PRIMARY KEY (`id_faktury`),
  ADD KEY `FK9qbx6fip02vnue93kyt9g5bbr` (`klienci_id_klienta`);

--
-- Indeksy dla tabeli `faktury_produkty`
--
ALTER TABLE `faktury_produkty`
  ADD PRIMARY KEY (`id_faktury_produkty`),
  ADD KEY `FK86c4iofndxfewa6xmi96tbwmv` (`faktury_id_faktury`),
  ADD KEY `FKa6wlegs1m9gv36x0rpm6puhyn` (`produkty_id_produktu`);

--
-- Indeksy dla tabeli `kategorie`
--
ALTER TABLE `kategorie`
  ADD PRIMARY KEY (`id_kategorii`);

--
-- Indeksy dla tabeli `klienci`
--
ALTER TABLE `klienci`
  ADD PRIMARY KEY (`id_klienta`);

--
-- Indeksy dla tabeli `producenci`
--
ALTER TABLE `producenci`
  ADD PRIMARY KEY (`id_producenta`);

--
-- Indeksy dla tabeli `produkty`
--
ALTER TABLE `produkty`
  ADD PRIMARY KEY (`id_produktu`),
  ADD KEY `FK865498ketgqlpuq4u1yefryvw` (`producenci_id_producenta`),
  ADD KEY `FK7bg54h9mask9g7lv1cll2tc4d` (`kategorie_id_kategorii`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `faktury`
--
ALTER TABLE `faktury`
  MODIFY `id_faktury` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT dla tabeli `faktury_produkty`
--
ALTER TABLE `faktury_produkty`
  MODIFY `id_faktury_produkty` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT dla tabeli `kategorie`
--
ALTER TABLE `kategorie`
  MODIFY `id_kategorii` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT dla tabeli `klienci`
--
ALTER TABLE `klienci`
  MODIFY `id_klienta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT dla tabeli `producenci`
--
ALTER TABLE `producenci`
  MODIFY `id_producenta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT dla tabeli `produkty`
--
ALTER TABLE `produkty`
  MODIFY `id_produktu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `faktury`
--
ALTER TABLE `faktury`
  ADD CONSTRAINT `FK9qbx6fip02vnue93kyt9g5bbr` FOREIGN KEY (`klienci_id_klienta`) REFERENCES `klienci` (`id_klienta`);

--
-- Ograniczenia dla tabeli `faktury_produkty`
--
ALTER TABLE `faktury_produkty`
  ADD CONSTRAINT `FK86c4iofndxfewa6xmi96tbwmv` FOREIGN KEY (`faktury_id_faktury`) REFERENCES `faktury` (`id_faktury`),
  ADD CONSTRAINT `FKa6wlegs1m9gv36x0rpm6puhyn` FOREIGN KEY (`produkty_id_produktu`) REFERENCES `produkty` (`id_produktu`);

--
-- Ograniczenia dla tabeli `produkty`
--
ALTER TABLE `produkty`
  ADD CONSTRAINT `FK7bg54h9mask9g7lv1cll2tc4d` FOREIGN KEY (`kategorie_id_kategorii`) REFERENCES `kategorie` (`id_kategorii`),
  ADD CONSTRAINT `FK865498ketgqlpuq4u1yefryvw` FOREIGN KEY (`producenci_id_producenta`) REFERENCES `producenci` (`id_producenta`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
