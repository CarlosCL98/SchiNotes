-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2019-03-26 06:03:13.792

-- tables
-- Table: Actividad
CREATE TABLE Actividad (
    id int  NOT NULL,
    nombre varchar(50)  NOT NULL,
    descripcion text  NOT NULL,
    fecha date  NOT NULL,
    CONSTRAINT Actividad_pk PRIMARY KEY (id)
);

-- Table: ActividadPorHorario
CREATE TABLE ActividadPorHorario (
    Actividad_id int  NOT NULL,
    Horario_nombre varchar(50)  NOT NULL,
    CONSTRAINT ActividadPorHorario_pk PRIMARY KEY (Actividad_id,Horario_nombre)
);

-- Table: Amigo
CREATE TABLE Amigo (
    fecha date  NOT NULL,
    Usuario_identificacion int  NOT NULL,
    Usuario_2_identificacion int  NOT NULL,
    CONSTRAINT Amigo_pk PRIMARY KEY (Usuario_identificacion,Usuario_2_identificacion)
);

-- Table: Cuenta
CREATE TABLE Cuenta (
    correo varchar(50)  NOT NULL,
    contrasena varchar(50)  NOT NULL,
    nickname varchar(50)  NOT NULL,
    CONSTRAINT nickname UNIQUE (nickname) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT correo PRIMARY KEY (correo)
);

-- Table: DiasDeLaSemana
CREATE TABLE DiasDeLaSemana (
    nombre varchar(50)  NOT NULL,
    CONSTRAINT DiasDeLaSemana_pk PRIMARY KEY (nombre)
);

-- Table: DiasPorHorario
CREATE TABLE DiasPorHorario (
    Horario_nombre varchar(50)  NOT NULL,
    DiasDeLaSemana_nombre varchar(50)  NOT NULL,
    CONSTRAINT DiasPorHorario_pk PRIMARY KEY (Horario_nombre,DiasDeLaSemana_nombre)
);

-- Table: Grupo
CREATE TABLE Grupo (
    identificacion int  NOT NULL,
    nombre varchar(50)  NOT NULL,
    descripcion text  NOT NULL,
    Horario_nombre varchar(50)  NOT NULL,
    Tablero_nombre varchar(50)  NOT NULL,
    CONSTRAINT Grupo_pk PRIMARY KEY (identificacion)
);

-- Table: GrupoDeTrabajo
CREATE TABLE GrupoDeTrabajo (
    rol varchar(50)  NOT NULL,
    Grupo_identificacion int  NOT NULL,
    Usuario_identificacion int  NOT NULL,
    CONSTRAINT GrupoDeTrabajo_pk PRIMARY KEY (Grupo_identificacion,Usuario_identificacion)
);

-- Table: Horario
CREATE TABLE Horario (
    nombre varchar(50)  NOT NULL,
    Usuario_identificacion int  NOT NULL,
    CONSTRAINT Horario_pk PRIMARY KEY (nombre)
);

-- Table: Nota
CREATE TABLE Nota (
    id int  NOT NULL,
    color varchar(50)  NOT NULL,
    template bytea  NOT NULL,
    fechaCreacio date  NOT NULL,
    Usuario_identificacion int  NOT NULL,
    Grupo_identificacion int  NOT NULL,
    CONSTRAINT Nota_pk PRIMARY KEY (id)
);

-- Table: THoras
CREATE TABLE THoras (
    hora time  NOT NULL,
    Horario_nombre varchar(50)  NOT NULL,
    CONSTRAINT THoras_pk PRIMARY KEY (hora)
);

-- Table: Tablero
CREATE TABLE Tablero (
    nombre varchar(50)  NOT NULL,
    template bytea  NOT NULL,
    CONSTRAINT Tablero_pk PRIMARY KEY (nombre)
);

-- Table: Usuario
CREATE TABLE Usuario (
    identificacion int  NOT NULL,
    nombre varchar(20)  NOT NULL,
    apellido varchar(30)  NOT NULL,
    foto bytea  NULL,
    intereses text  NULL,
    Cuenta_correo varchar(50)  NOT NULL,
    CONSTRAINT Usuario_pk PRIMARY KEY (identificacion)
);

-- foreign keys
-- Reference: ActividadPorHorario_Actividad (table: ActividadPorHorario)
ALTER TABLE ActividadPorHorario ADD CONSTRAINT ActividadPorHorario_Actividad
    FOREIGN KEY (Actividad_id)
    REFERENCES Actividad (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: ActividadPorHorario_Horario (table: ActividadPorHorario)
ALTER TABLE ActividadPorHorario ADD CONSTRAINT ActividadPorHorario_Horario
    FOREIGN KEY (Horario_nombre)
    REFERENCES Horario (nombre)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Amigo_Usuario (table: Amigo)
ALTER TABLE Amigo ADD CONSTRAINT Amigo_Usuario
    FOREIGN KEY (Usuario_identificacion)
    REFERENCES Usuario (identificacion)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Amigo_Usuario_2 (table: Amigo)
ALTER TABLE Amigo ADD CONSTRAINT Amigo_Usuario_2
    FOREIGN KEY (Usuario_2_identificacion)
    REFERENCES Usuario (identificacion)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: DiasPorHorario_DiasDeLaSemana (table: DiasPorHorario)
ALTER TABLE DiasPorHorario ADD CONSTRAINT DiasPorHorario_DiasDeLaSemana
    FOREIGN KEY (DiasDeLaSemana_nombre)
    REFERENCES DiasDeLaSemana (nombre)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: DiasPorHorario_Horario (table: DiasPorHorario)
ALTER TABLE DiasPorHorario ADD CONSTRAINT DiasPorHorario_Horario
    FOREIGN KEY (Horario_nombre)
    REFERENCES Horario (nombre)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: GrupoDeTrabajo_Grupo (table: GrupoDeTrabajo)
ALTER TABLE GrupoDeTrabajo ADD CONSTRAINT GrupoDeTrabajo_Grupo
    FOREIGN KEY (Grupo_identificacion)
    REFERENCES Grupo (identificacion)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: GrupoDeTrabajo_Usuario (table: GrupoDeTrabajo)
ALTER TABLE GrupoDeTrabajo ADD CONSTRAINT GrupoDeTrabajo_Usuario
    FOREIGN KEY (Usuario_identificacion)
    REFERENCES Usuario (identificacion)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Grupo_Horario (table: Grupo)
ALTER TABLE Grupo ADD CONSTRAINT Grupo_Horario
    FOREIGN KEY (Horario_nombre)
    REFERENCES Horario (nombre)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Grupo_Tablero (table: Grupo)
ALTER TABLE Grupo ADD CONSTRAINT Grupo_Tablero
    FOREIGN KEY (Tablero_nombre)
    REFERENCES Tablero (nombre)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Horario_Usuario (table: Horario)
ALTER TABLE Horario ADD CONSTRAINT Horario_Usuario
    FOREIGN KEY (Usuario_identificacion)
    REFERENCES Usuario (identificacion)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Nota_Grupo (table: Nota)
ALTER TABLE Nota ADD CONSTRAINT Nota_Grupo
    FOREIGN KEY (Grupo_identificacion)
    REFERENCES Grupo (identificacion)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Nota_Usuario (table: Nota)
ALTER TABLE Nota ADD CONSTRAINT Nota_Usuario
    FOREIGN KEY (Usuario_identificacion)
    REFERENCES Usuario (identificacion)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: THoras_Horario (table: THoras)
ALTER TABLE THoras ADD CONSTRAINT THoras_Horario
    FOREIGN KEY (Horario_nombre)
    REFERENCES Horario (nombre)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Usuario_Cuenta (table: Usuario)
ALTER TABLE Usuario ADD CONSTRAINT Usuario_Cuenta
    FOREIGN KEY (Cuenta_correo)
    REFERENCES Cuenta (correo)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- End of file.

