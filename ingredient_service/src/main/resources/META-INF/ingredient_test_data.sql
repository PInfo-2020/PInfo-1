drop sequence if exists INGREDIENT_SEQ;
create sequence INGREDIENT_SEQ start with 1 increment by 50;
CREATE TABLE IF NOT EXISTS Ingredient ( id bigint not null, primary key (id), name varchar(255), averageWeight int, unity varchar(255), category varchar(255) );
TRUNCATE TABLE Ingredient; 
INSERT INTO Ingredient (id, name, averageWeight, unity, category) VALUES (INGREDIENT_SEQ.nextval,'Abricot',60,'unité/g','Fruits/Fruits frais');
INSERT INTO Ingredient  (id, name, averageWeight, unity, category) VALUES  (INGREDIENT_SEQ.nextval,'Abricot, sec',NULL,'g','Fruits/Fruits secs');
INSERT INTO Ingredient (id, name, averageWeight, unity, category) VALUES  (INGREDIENT_SEQ.nextval,'Abricot, sucré, conserve',NULL,'g','Fruits/Fruits cuits (conserves comprises)');
