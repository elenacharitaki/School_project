-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Εξυπηρετητής: 127.0.0.1
-- Χρόνος δημιουργίας: 26 Απρ 2022 στις 19:15:53
-- Έκδοση διακομιστή: 10.4.21-MariaDB
-- Έκδοση PHP: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Βάση δεδομένων: `db_school`
--

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `courses`
--

CREATE TABLE `courses` (
  `id` int(11) NOT NULL,
  `title` varchar(127) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `courses`
--

INSERT INTO `courses` (`id`, `title`) VALUES
(29, 'Course1'),
(30, 'Course2'),
(31, 'Course3');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `studentcourse`
--

CREATE TABLE `studentcourse` (
  `id` int(11) NOT NULL,
  `studentId` int(11) NOT NULL,
  `CourseId` int(11) NOT NULL,
  `grade` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `studentcourse`
--

INSERT INTO `studentcourse` (`id`, `studentId`, `CourseId`, `grade`) VALUES
(22, 4, 29, 10),
(23, 4, 30, NULL);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `students`
--

CREATE TABLE `students` (
  `id` int(11) NOT NULL,
  `firstName` varchar(63) NOT NULL,
  `lastName` varchar(63) NOT NULL,
  `gender` set('Male','Female') NOT NULL,
  `birthDate` varchar(15) NOT NULL,
  `address` varchar(127) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `students`
--

INSERT INTO `students` (`id`, `firstName`, `lastName`, `gender`, `birthDate`, `address`) VALUES
(4, 'First name', 'Last name', 'Female', '01/01/2010', 'Address');

--
-- Ευρετήρια για άχρηστους πίνακες
--

--
-- Ευρετήρια για πίνακα `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `studentcourse`
--
ALTER TABLE `studentcourse`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Course_to_courseId` (`CourseId`),
  ADD KEY `Student_to_studentId` (`studentId`);

--
-- Ευρετήρια για πίνακα `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT για άχρηστους πίνακες
--

--
-- AUTO_INCREMENT για πίνακα `courses`
--
ALTER TABLE `courses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT για πίνακα `studentcourse`
--
ALTER TABLE `studentcourse`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT για πίνακα `students`
--
ALTER TABLE `students`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Περιορισμοί για άχρηστους πίνακες
--

--
-- Περιορισμοί για πίνακα `studentcourse`
--
ALTER TABLE `studentcourse`
  ADD CONSTRAINT `Course_to_courseId` FOREIGN KEY (`CourseId`) REFERENCES `courses` (`id`) ON UPDATE NO ACTION,
  ADD CONSTRAINT `Student_to_studentId` FOREIGN KEY (`studentId`) REFERENCES `students` (`id`) ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
