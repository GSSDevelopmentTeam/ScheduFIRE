use schedufire;
insert into Credenziali values ("turnoB","220EDACE958D53B8A30AEC59FF0C1B93934DCEE7FA526712888B44ED236B737D","vigiledelfuoco");
insert into Credenziali values ("capoturno","BCEE59C152CF518D3C6FBBCE6EB4E7CA3757323D5939A59B816197DFAA372EE4","capoturno");

insert into Vigile values("michele73.sica@vigilfuoco.it","Michele","Sica","B","Capo Squadra",20,0,0,true,"Esperto","turnoB");
insert into Vigile values("rosario.marmo@vigilfuoco.it","Rosario","Marmo","B","Capo Squadra",20,0,0,true,"Esperto","turnoB");
insert into Vigile values("franco.mammato@vigilfuoco.it","Franco","Mammato","B","Capo Squadra",20,0,0,true,"Esperto","turnoB");
insert into Vigile values("mario.delregno@vigilfuoco.it","Mario","Del Regno","B","Capo Squadra",20,0,0,true,"Esperto","turnoB");
insert into Vigile values("michele.granato@vigilfuoco.it","Michele","Granato","B","Capo Squadra",20,0,0,true,"Semplice","turnoB");
insert into Vigile values("salvatore.malaspina@vigilfuoco.it","Salvatore","Malaspina","B","Capo Squadra",20,0,0,true,"Semplice","turnoB");
insert into Vigile values("gianluca.iovino@vigilfuoco.it","Gianluca","Iovino","B","Autista",20,0,0,true,"Esperto","turnoB");
insert into Vigile values("carmine.sarraino@vigilfuoco.it","Carmine","Sarraino","B","Autista",20,0,0,true,"Qualificato","turnoB");
insert into Vigile values("alberto.barbarulo@vigilfuoco.it","Alberto","Barbarulo","B","Autista",20,0,0,true,"Esperto","turnoB");
insert into Vigile values("stefano.galdi@vigilfuoco.it","Stefano","Galdi","B","Autista",20,0,0,true,"Coordinatore","turnoB");
insert into Vigile values("ciro.fattorusso@vigilfuoco.it","Ciro","Fattorusso","B","Vigile",20,0,0,true,"Qualificato","turnoB");
insert into Vigile values("alfonso.grieco@vigilfuoco.it","Alfonso","Grieco","B","Vigile",20,0,0,true,"Qualificato","turnoB");
insert into Vigile values("luca.raimondi@vigilfuoco.it","Luca","Raimondi","B","Vigile",20,0,0,true,"Qualificato","turnoB");
insert into Vigile values("fabrizio.pepe@vigilfuoco.it","Fabrizio","Pepe","B","Vigile",20,0,0,true,"Coordinatore","turnoB");
insert into Vigile values("roberto.santoro@vigilfuoco.it","Roberto","Santoro","B","Vigile",20,0,0,true,"Qualificato","turnoB");
insert into Vigile values("maurizio.marsano@vigilfuoco.it","Maurizio","Marsano","B","Vigile",20,0,0,true,"Qualificato","turnoB");
insert into Vigile values("domenico.giordano@vigilfuoco.it","Domenico","Giordano","B","Vigile",5,2,0,true,"Esperto","turnoB");
insert into Vigile values("marzia.mancuso@vigilfuoco.it","Marzia","Mancuso","B","Vigile",20,0,0,true,"Qualificato","turnoB");
insert into Vigile values("roberto.noschese@vigilfuoco.it","Roberto","Noschese","B","Vigile",20,0,0,true,"Esperto","turnoB");
insert into Vigile values("gerardo.citarella@vigilfuoco.it","Gerardo","Citarella","B","Vigile",20,0,0,true,"Coordinatore","turnoB");
insert into Vigile values("modesto.spedaliere@vigilfuoco.it","Modesto","Spedaliere","B","Vigile",20,0,0,true,"Esperto","turnoB");

insert into Capoturno values("capoturno","capoturno","capoturno","B","capoturno");

insert into listasquadre values("2019-12-24",8,"capoturno");
insert into listasquadre values("2019-12-25",20,"capoturno");
insert into listasquadre values("2020-01-02",20,"capoturno");




insert into squadra values("Prima Partenza","2019-12-24",3);
insert into squadra values("Auto Scala","2019-12-24",2);
insert into squadra values("Auto Botte","2019-12-24",1);
insert into squadra values("Sala Operativa","2019-12-24",3);

insert into squadra values("Prima Partenza","2019-12-25",3);
insert into squadra values("Auto Scala","2019-12-25",2);
insert into squadra values("Auto Botte","2019-12-25",1);
insert into squadra values("Sala Operativa","2019-12-25",3);

insert into squadra values("Prima Partenza","2020-01-02",3);
insert into squadra values("Auto Scala","2020-01-02",2);
insert into squadra values("Auto Botte","2020-01-02",1);
insert into squadra values("Sala Operativa","2020-01-02",3);



insert into componentedellasquadra values ("michele73.sica@vigilfuoco.it","Sala Operativa","2019-12-24");
insert into componentedellasquadra values ("rosario.marmo@vigilfuoco.it","Sala Operativa","2019-12-24");
insert into componentedellasquadra values ("franco.mammato@vigilfuoco.it","Sala Operativa","2019-12-24");
insert into componentedellasquadra values ("mario.delregno@vigilfuoco.it","Prima Partenza","2019-12-24");
insert into componentedellasquadra values ("michele.granato@vigilfuoco.it","Prima Partenza","2019-12-24");
insert into componentedellasquadra values ("salvatore.malaspina@vigilfuoco.it","Prima Partenza","2019-12-24");
insert into componentedellasquadra values ("gianluca.iovino@vigilfuoco.it","Prima Partenza","2019-12-24");
insert into componentedellasquadra values ("carmine.sarraino@vigilfuoco.it","Prima Partenza","2019-12-24");
insert into componentedellasquadra values ("alberto.barbarulo@vigilfuoco.it","Auto Scala","2019-12-24");
insert into componentedellasquadra values ("stefano.galdi@vigilfuoco.it","Auto Scala","2019-12-24");
insert into componentedellasquadra values ("ciro.fattorusso@vigilfuoco.it","Auto Botte","2019-12-24");
insert into componentedellasquadra values ("alfonso.grieco@vigilfuoco.it","Auto Botte","2019-12-24");


insert into componentedellasquadra values ("luca.raimondi@vigilfuoco.it","Sala Operativa","2019-12-25");
insert into componentedellasquadra values ("fabrizio.pepe@vigilfuoco.it","Sala Operativa","2019-12-25");
insert into componentedellasquadra values ("roberto.santoro@vigilfuoco.it","Sala Operativa","2019-12-25");
insert into componentedellasquadra values ("gerardo.citarella@vigilfuoco.it","Prima Partenza","2019-12-25");
insert into componentedellasquadra values ("modesto.spedaliere@vigilfuoco.it","Prima Partenza","2019-12-25");
insert into componentedellasquadra values ("michele73.sica@vigilfuoco.it","Prima Partenza","2019-12-25");
insert into componentedellasquadra values ("rosario.marmo@vigilfuoco.it","Prima Partenza","2019-12-25");
insert into componentedellasquadra values ("franco.mammato@vigilfuoco.it","Prima Partenza","2019-12-25");
insert into componentedellasquadra values ("mario.delregno@vigilfuoco.it","Auto Scala","2019-12-25");
insert into componentedellasquadra values ("michele.granato@vigilfuoco.it","Auto Scala","2019-12-25");
insert into componentedellasquadra values ("salvatore.malaspina@vigilfuoco.it","Auto Botte","2019-12-25");
insert into componentedellasquadra values ("gianluca.iovino@vigilfuoco.it","Auto Botte","2019-12-25");


insert into componentedellasquadra values ("carmine.sarraino@vigilfuoco.it","Sala Operativa","2020-01-02");
insert into componentedellasquadra values ("alberto.barbarulo@vigilfuoco.it","Sala Operativa","2020-01-02");
insert into componentedellasquadra values ("stefano.galdi@vigilfuoco.it","Sala Operativa","2020-01-02");
insert into componentedellasquadra values ("ciro.fattorusso@vigilfuoco.it","Prima Partenza","2020-01-02");
insert into componentedellasquadra values ("alfonso.grieco@vigilfuoco.it","Prima Partenza","2020-01-02");
insert into componentedellasquadra values ("luca.raimondi@vigilfuoco.it","Prima Partenza","2020-01-02");
insert into componentedellasquadra values ("fabrizio.pepe@vigilfuoco.it","Prima Partenza","2020-01-02");
insert into componentedellasquadra values ("roberto.santoro@vigilfuoco.it","Prima Partenza","2020-01-02");
insert into componentedellasquadra values ("maurizio.marsano@vigilfuoco.it","Auto Scala","2020-01-02");
insert into componentedellasquadra values ("domenico.giordano@vigilfuoco.it","Auto Scala","2020-01-02");
insert into componentedellasquadra values ("marzia.mancuso@vigilfuoco.it","Auto Botte","2020-01-02");



insert into malattia (dataInizio,dataFine,emailCT,emailVF) values (	"2019-12-23","2019-12-24","capoturno","roberto.noschese@vigilfuoco.it");
insert into malattia (dataInizio,dataFine,emailCT,emailVF) values (	"2020-01-06","2020-01-09","capoturno","gerardo.citarella@vigilfuoco.it");



insert into ferie (dataInizio,dataFine,emailCT,emailVF) values (	"2019-12-29","2019-12-29","capoturno","modesto.spedaliere@vigilfuoco.it");
insert into ferie (dataInizio,dataFine,emailCT,emailVF) values (	"2020-01-10","2020-01-12","capoturno","michele73.sica@vigilfuoco.it");
insert into ferie (dataInizio,dataFine,emailCT,emailVF) values (	"2020-01-15","2020-01-15","capoturno","rosario.marmo@vigilfuoco.it");
