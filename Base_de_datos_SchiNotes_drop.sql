-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2019-03-26 06:03:13.792

-- foreign keys
ALTER TABLE ActividadPorHorario
    DROP CONSTRAINT ActividadPorHorario_Actividad;

ALTER TABLE ActividadPorHorario
    DROP CONSTRAINT ActividadPorHorario_Horario;

ALTER TABLE Amigo
    DROP CONSTRAINT Amigo_Usuario;

ALTER TABLE Amigo
    DROP CONSTRAINT Amigo_Usuario_2;

ALTER TABLE DiasPorHorario
    DROP CONSTRAINT DiasPorHorario_DiasDeLaSemana;

ALTER TABLE DiasPorHorario
    DROP CONSTRAINT DiasPorHorario_Horario;

ALTER TABLE GrupoDeTrabajo
    DROP CONSTRAINT GrupoDeTrabajo_Grupo;

ALTER TABLE GrupoDeTrabajo
    DROP CONSTRAINT GrupoDeTrabajo_Usuario;

ALTER TABLE Grupo
    DROP CONSTRAINT Grupo_Horario;

ALTER TABLE Grupo
    DROP CONSTRAINT Grupo_Tablero;

ALTER TABLE Horario
    DROP CONSTRAINT Horario_Usuario;

ALTER TABLE Nota
    DROP CONSTRAINT Nota_Grupo;

ALTER TABLE Nota
    DROP CONSTRAINT Nota_Usuario;

ALTER TABLE THoras
    DROP CONSTRAINT THoras_Horario;

ALTER TABLE Usuario
    DROP CONSTRAINT Usuario_Cuenta;

-- tables
DROP TABLE Actividad;

DROP TABLE ActividadPorHorario;

DROP TABLE Amigo;

DROP TABLE Cuenta;

DROP TABLE DiasDeLaSemana;

DROP TABLE DiasPorHorario;

DROP TABLE Grupo;

DROP TABLE GrupoDeTrabajo;

DROP TABLE Horario;

DROP TABLE Nota;

DROP TABLE THoras;

DROP TABLE Tablero;

DROP TABLE Usuario;

-- End of file.

