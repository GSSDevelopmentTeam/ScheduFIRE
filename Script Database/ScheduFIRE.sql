CREATE SCHEMA ScheduFIRE;

USE ScheduFIRE;

CREATE TABLE Credenziali (
username			VARCHAR(20) 	NOT NULL,
pass					CHAR(64) 			NOT NULL,
ruolo					VARCHAR(20) 	NOT NULL,
PRIMARY KEY (username));


CREATE TABLE Capoturno (
email					VARCHAR(50) 	NOT NULL,
nome 					VARCHAR(20) 	NOT NULL,
cognome				VARCHAR(20) 	NOT NULL,
turno 					CHAR(1)			 	NOT NULL,	
username			VARCHAR(20) 	NOT NULL,
PRIMARY KEY (email),
FOREIGN KEY (username) REFERENCES Credenziali(username)
		ON UPDATE CASCADE
		ON DELETE NO ACTION);
        
        
CREATE TABLE Vigile (
email 										VARCHAR(50) 	NOT NULL,
nome 										VARCHAR(20) 	NOT NULL,
cognome 								VARCHAR(20) 	NOT NULL,
turno 										CHAR(1) 			NOT NULL,
mansione 								VARCHAR(50) 	NOT NULL,
giorniferieannocorrente			INT 						NOT NULL,
giorniferieannoprecedente		INT 						NOT NULL,
caricolavoro								INT 						NOT NULL,
adoperabile								BOOLEAN 			NOT NULL,
grado									VARCHAR(20)			NOT NULL,
username								VARCHAR(20) 	NOT NULL,
PRIMARY KEY (email),
FOREIGN KEY (username) REFERENCES Credenziali(username)
				ON UPDATE CASCADE
                ON DELETE NO ACTION);
                
            
CREATE TABLE ListaSquadre (
giornoLavorativo				DATE 					NOT NULL,
oraIniziale						INT						NOT NULL,
emailCT							VARCHAR(50) 	NOT NULL,
PRIMARY KEY (giornoLavorativo),
FOREIGN KEY (emailCT) REFERENCES Capoturno(email)
				ON UPDATE CASCADE
                ON DELETE NO ACTION);
                

CREATE TABLE Squadra (
tipologia 									VARCHAR(50) 	NOT NULL,
giornoLavorativo						DATE 					NOT NULL,
caricoLavoro							INT 						NOT NULL,
PRIMARY KEY (tipologia, giornoLavorativo),
FOREIGN KEY (giornoLavorativo) REFERENCES ListaSquadre(giornoLavorativo)
				ON UPDATE CASCADE
                ON DELETE CASCADE);
                
                
CREATE TABLE ComponenteDellaSquadra(
emailVF					VARCHAR(50) 	NOT NULL,
tipologia					VARCHAR(50) 	NOT NULL,
giornoLavorativo		DATE 					NOT NULL,
PRIMARY KEY (emailVF, tipologia, giornoLavorativo),
FOREIGN KEY(emailVF) REFERENCES Vigile(email)
				ON UPDATE CASCADE
                ON DELETE CASCADE,
FOREIGN KEY(tipologia, giornoLavorativo) REFERENCES Squadra(tipologia, giornoLavorativo)
				ON UPDATE CASCADE
                ON DELETE CASCADE);
                

CREATE TABLE Ferie(
id							INT						NOT NULL		AUTO_INCREMENT,
dataInizio				DATE 					NOT NULL,
dataFine				DATE 					NOT NULL,
emailCT				VARCHAR(50) 	NOT NULL,
emailVF				VARCHAR(50) 	NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (emailCT) REFERENCES Capoturno(email)
			ON UPDATE CASCADE
            ON DELETE NO ACTION,
FOREIGN KEY (emailVF) REFERENCES Vigile(email)
			ON UPDATE CASCADE 
            ON DELETE NO ACTION);
 
 
CREATE TABLE Malattia(
id								INT						NOT NULL		AUTO_INCREMENT,
dataInizio					DATE 					NOT NULL,
dataFine					DATE 					NOT NULL,
emailCT					VARCHAR(50) 	NOT NULL,
emailVF					VARCHAR(50) 	NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (emailCT) REFERENCES Capoturno(email)
			ON UPDATE CASCADE
            ON DELETE NO ACTION,
FOREIGN KEY (emailVF) REFERENCES Vigile(email)
			ON UPDATE CASCADE 
            ON DELETE NO ACTION);