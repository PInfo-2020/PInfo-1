drop sequence if exists RECIPE_SEQ;
create sequence RECIPE_SEQ start with 1 increment by 1;
drop sequence if exists INGREDIENT_SEQ;
create sequence INGREDIENT_SEQ start with 1 increment by 1;
drop sequence if exists COMMENT_SEQ;
create sequence COMMENT_SEQ start with 1 increment by 1;

DROP TABLE if exists Recipe cascade;
DROP TABLE if exists Ingredient cascade;
DROP TABLE if exists Comment cascade;

CREATE TABLE IF NOT EXISTS Recipe ( id bigint not null, primary key (id), name varchar(255), picture varchar(255), people smallint, preparationTime smallint, difficulty smallint, preparation varchar(255), author varchar(255), publicationDate varchar(255), grade float);
CREATE TABLE IF NOT EXISTS Ingredient ( id bigint not null, primary key (id), quantity smallint, detailsID bigint, RECIPE_ID long, foreign key (RECIPE_ID) REFERENCES Recipe(id) );
CREATE TABLE IF NOT EXISTS Comment ( id bigint not null, primary key (id), text varchar(255), userID varchar(255), grade smallint, RECIPE_ID long, foreign key (RECIPE_ID) REFERENCES Recipe(id));



INSERT INTO Recipe (id, name, picture, people, preparationTime, difficulty, preparation, author, publicationDate, grade) VALUES (RECIPE_SEQ.nextval,'Tarte aux citrons', 'monImage', 5, 10, 5, 'Prépare la tarte', 'testId', '2020-07-12', 4.5);
INSERT INTO Recipe (id, name, picture, people, preparationTime, difficulty, preparation, author, publicationDate, grade) VALUES (RECIPE_SEQ.nextval,'Choux à la crème', 'monAutreImage', 2, 15, 3, 'Prépare le choux', 'autreId', '2020-04-24', 3);

INSERT INTO Ingredient  (id, quantity, detailsID, RECIPE_ID) VALUES  (INGREDIENT_SEQ.nextval,4,0,1);
INSERT INTO Ingredient (id, quantity, detailsID, RECIPE_ID) VALUES  (INGREDIENT_SEQ.nextval,12,1,1);
INSERT INTO Ingredient  (id, quantity, detailsID, RECIPE_ID) VALUES  (INGREDIENT_SEQ.nextval,3,2,2);
INSERT INTO Ingredient (id, quantity, detailsID, RECIPE_ID) VALUES  (INGREDIENT_SEQ.nextval,42,3,2);

INSERT INTO Comment (id, text, userID, grade, RECIPE_ID) VALUES  (COMMENT_SEQ.nextval, 'Pas mal', 'commentateur',3,1);
INSERT INTO Comment (id, text, userID, grade, RECIPE_ID) VALUES  (COMMENT_SEQ.nextval, 'Très bon', 'AutreCommentateur',4,1);
INSERT INTO Comment (id, text, userID, grade, RECIPE_ID) VALUES  (COMMENT_SEQ.nextval,'Dégeulasse', 'randomGuy',1,1);
INSERT INTO Comment (id, text, userID, grade, RECIPE_ID) VALUES  (COMMENT_SEQ.nextval,'passable', 'moi',3,2);
