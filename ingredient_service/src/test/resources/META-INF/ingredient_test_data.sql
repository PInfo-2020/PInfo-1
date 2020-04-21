drop sequence if exists INGREDIENT_SEQ;
create sequence INGREDIENT_SEQ start with 1 increment by 50;
CREATE TABLE IF NOT EXISTS Ingredient ( id bigint not null, primary key (id), nom varchar(255), poid_moyen bigint, unite varchar(255), categorie varchar(255) );
TRUNCATE TABLE Ingredient; 
INSERT INTO Ingredient (id, nom, poid_moyen, unite, categorie) VALUES (INGREDIENT_SEQ.nextval,'Abricot',60,'unité/g','Fruits/Fruits frais');
INSERT INTO Ingredient  (id, nom, poid_moyen, unite, categorie) VALUES  (INGREDIENT_SEQ.nextval,'Abricot, sec',NULL,'g','Fruits/Fruits secs');
INSERT INTO Ingredient (id, nom, poid_moyen, unite, categorie) VALUES  (INGREDIENT_SEQ.nextval,'Abricot, sucré, conserve',NULL,'g','Fruits/Fruits cuits (conserves comprises)');
