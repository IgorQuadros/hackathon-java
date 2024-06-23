-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 23/06/2024 às 14:12
-- Versão do servidor: 10.4.28-MariaDB
-- Versão do PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `banco_hackathon`
--
CREATE DATABASE IF NOT EXISTS `banco_hackathon` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `banco_hackathon`;

-- --------------------------------------------------------

--
-- Estrutura para tabela `agenda`
--

CREATE TABLE `agenda` (
  `id` int(11) NOT NULL,
  `idoso_id` int(11) NOT NULL,
  `agente_de_saude_id` int(11) NOT NULL,
  `data` date NOT NULL,
  `hora` time NOT NULL,
  `descricao` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `agenda_has_lembretes`
--

CREATE TABLE `agenda_has_lembretes` (
  `id` int(11) NOT NULL,
  `agenda_id` int(11) NOT NULL,
  `lembretes_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `agente_de_saude`
--

CREATE TABLE `agente_de_saude` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `Telefone` varchar(11) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `especialidade` varchar(100) NOT NULL,
  `data_contratacao` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `agente_de_saude`
--

INSERT INTO `agente_de_saude` (`id`, `nome`, `Telefone`, `cpf`, `especialidade`, `data_contratacao`) VALUES
(2, 'Jorge de Sousa', '666666666', '888888888', 'Pediatria', '2016-05-23');

-- --------------------------------------------------------

--
-- Estrutura para tabela `cuidador`
--

CREATE TABLE `cuidador` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `telefone` varchar(11) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `data_registro` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `cuidador_has_idoso`
--

CREATE TABLE `cuidador_has_idoso` (
  `id` int(11) NOT NULL,
  `idoso_id` int(11) NOT NULL,
  `cuidador_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `historico`
--

CREATE TABLE `historico` (
  `id` int(11) NOT NULL,
  `idoso_id` int(11) DEFAULT NULL,
  `doencas_preexistentes` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `historico`
--

INSERT INTO `historico` (`id`, `idoso_id`, `doencas_preexistentes`) VALUES
(1, 1, 'Alergia a abelhas'),
(2, 2, 'Rinite e sinusite');

-- --------------------------------------------------------

--
-- Estrutura para tabela `historico_has_vacina`
--

CREATE TABLE `historico_has_vacina` (
  `id` int(11) NOT NULL,
  `historico_id` int(11) NOT NULL,
  `vacina_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `idosos`
--

CREATE TABLE `idosos` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `telefone` varchar(11) NOT NULL,
  `email` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `idosos`
--

INSERT INTO `idosos` (`id`, `nome`, `telefone`, `email`) VALUES
(1, 'Idoso Teste', '999999999', 'teste@gmail'),
(2, 'Idoso segundo teste', '55555555', 'teste@hotmail');

-- --------------------------------------------------------

--
-- Estrutura para tabela `lembretes`
--

CREATE TABLE `lembretes` (
  `id` int(11) NOT NULL,
  `descricao` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `vacina`
--

CREATE TABLE `vacina` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `data_validade` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `agenda`
--
ALTER TABLE `agenda`
  ADD PRIMARY KEY (`id`),
  ADD KEY `f1` (`idoso_id`),
  ADD KEY `f2` (`agente_de_saude_id`);

--
-- Índices de tabela `agenda_has_lembretes`
--
ALTER TABLE `agenda_has_lembretes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `a1` (`agenda_id`),
  ADD KEY `a2` (`lembretes_id`);

--
-- Índices de tabela `agente_de_saude`
--
ALTER TABLE `agente_de_saude`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `cuidador`
--
ALTER TABLE `cuidador`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `cuidador_has_idoso`
--
ALTER TABLE `cuidador_has_idoso`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk1` (`idoso_id`),
  ADD KEY `fk2` (`cuidador_id`);

--
-- Índices de tabela `historico`
--
ALTER TABLE `historico`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idoso_id` (`idoso_id`);

--
-- Índices de tabela `historico_has_vacina`
--
ALTER TABLE `historico_has_vacina`
  ADD PRIMARY KEY (`id`),
  ADD KEY `k1` (`historico_id`),
  ADD KEY `k2` (`vacina_id`);

--
-- Índices de tabela `idosos`
--
ALTER TABLE `idosos`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `lembretes`
--
ALTER TABLE `lembretes`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `vacina`
--
ALTER TABLE `vacina`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `agenda`
--
ALTER TABLE `agenda`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `agenda_has_lembretes`
--
ALTER TABLE `agenda_has_lembretes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `agente_de_saude`
--
ALTER TABLE `agente_de_saude`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `cuidador`
--
ALTER TABLE `cuidador`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `cuidador_has_idoso`
--
ALTER TABLE `cuidador_has_idoso`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `historico`
--
ALTER TABLE `historico`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `historico_has_vacina`
--
ALTER TABLE `historico_has_vacina`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `idosos`
--
ALTER TABLE `idosos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `lembretes`
--
ALTER TABLE `lembretes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `vacina`
--
ALTER TABLE `vacina`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `agenda`
--
ALTER TABLE `agenda`
  ADD CONSTRAINT `f1` FOREIGN KEY (`idoso_id`) REFERENCES `idosos` (`id`),
  ADD CONSTRAINT `f2` FOREIGN KEY (`agente_de_saude_id`) REFERENCES `agente_de_saude` (`id`);

--
-- Restrições para tabelas `agenda_has_lembretes`
--
ALTER TABLE `agenda_has_lembretes`
  ADD CONSTRAINT `a1` FOREIGN KEY (`agenda_id`) REFERENCES `agenda` (`id`),
  ADD CONSTRAINT `a2` FOREIGN KEY (`lembretes_id`) REFERENCES `lembretes` (`id`);

--
-- Restrições para tabelas `cuidador_has_idoso`
--
ALTER TABLE `cuidador_has_idoso`
  ADD CONSTRAINT `fk1` FOREIGN KEY (`idoso_id`) REFERENCES `idosos` (`id`),
  ADD CONSTRAINT `fk2` FOREIGN KEY (`cuidador_id`) REFERENCES `cuidador` (`id`);

--
-- Restrições para tabelas `historico`
--
ALTER TABLE `historico`
  ADD CONSTRAINT `historico_ibfk_1` FOREIGN KEY (`idoso_id`) REFERENCES `idosos` (`id`) ON DELETE CASCADE;

--
-- Restrições para tabelas `historico_has_vacina`
--
ALTER TABLE `historico_has_vacina`
  ADD CONSTRAINT `k1` FOREIGN KEY (`historico_id`) REFERENCES `historico` (`id`),
  ADD CONSTRAINT `k2` FOREIGN KEY (`vacina_id`) REFERENCES `vacina` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
