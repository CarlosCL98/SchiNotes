-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2019-04-09 00:06:18.896

-- foreign keys
ALTER TABLE Actividad
    DROP CONSTRAINT Actividad_Hora;

ALTER TABLE Actividad_Por_Horario
    DROP CONSTRAINT Actividad_Por_Horario_Actividad;

ALTER TABLE Actividad_Por_Horario
    DROP CONSTRAINT Actividad_Por_Horario_Horario;

ALTER TABLE Amigo
    DROP CONSTRAINT Amigo_Usuario;

ALTER TABLE Amigo
    DROP CONSTRAINT Amigo_Usuario_2;

ALTER TABLE Dias_Por_Horario
    DROP CONSTRAINT Dias_Por_Horario_Dia;

ALTER TABLE Dias_Por_Horario
    DROP CONSTRAINT Dias_Por_Horario_Horario;

ALTER TABLE Grupo_De_Trabajo
    DROP CONSTRAINT GrupoDeTrabajo_Grupo;

ALTER TABLE Grupo_De_Trabajo
    DROP CONSTRAINT GrupoDeTrabajo_Usuario;

ALTER TABLE Grupo
    DROP CONSTRAINT Grupo_Horario;

ALTER TABLE Grupo
    DROP CONSTRAINT Grupo_Tablero;

ALTER TABLE Hora
    DROP CONSTRAINT Hora_Dias_Por_Horario;

ALTER TABLE Horario
    DROP CONSTRAINT Horario_Usuario;

ALTER TABLE Nota
    DROP CONSTRAINT Nota_Grupo;

ALTER TABLE Nota
    DROP CONSTRAINT Nota_Usuario;

ALTER TABLE Usuario
    DROP CONSTRAINT Usuario_Cuenta;

-- tables
DROP TABLE Actividad;

DROP TABLE Actividad_Por_Horario;

DROP TABLE Amigo;

DROP TABLE Cuenta;

DROP TABLE Dia;

DROP TABLE Dias_Por_Horario;

DROP TABLE Grupo;

DROP TABLE Grupo_De_Trabajo;

DROP TABLE Hora;

DROP TABLE Horario;

DROP TABLE Nota;

DROP TABLE Tablero;

DROP TABLE Usuario;

-- End of file.

