drop sequence if exists FRIDGE_SEQ;
create sequence FRIDGE_SEQ start with 1 increment by 1;
drop sequence if exists INGREDIENT_SEQ;
create sequence INGREDIENT_SEQ start with 1 increment by 1;

DROP TABLE if exists Fridge cascade;
DROP TABLE if exists Ingredient cascade;

CREATE TABLE IF NOT EXISTS Fridge ( id bigint not null, primary key (id), userId varchar(255));
CREATE TABLE IF NOT EXISTS Ingredient ( id bigint not null, primary key (id), detailsId bigint, quantity bigint, expiration varchar(255), Fridge_id long, foreign key (Fridge_Id) REFERENCES Fridge(id));
CREATE TABLE IF NOT EXISTS FRIDGE_INGREDIENT (Fridge_id bigint, ingredients_id bigint);

INSERT INTO Fridge (id, userId) VALUES (FRIDGE_SEQ.nextval,'testId');
INSERT INTO Fridge (id, userId) VALUES (FRIDGE_SEQ.nextval,'ksakjxnbc');
INSERT INTO Fridge (id, userId) VALUES (FRIDGE_SEQ.nextval,'teasukbxb');

INSERT INTO Ingredient  (id, detailsId, quantity, expiration, Fridge_id) VALUES  (INGREDIENT_SEQ.nextval,0,5,'2020-07-12',1);
INSERT INTO Ingredient (id, detailsId, quantity, expiration, Fridge_id) VALUES  (INGREDIENT_SEQ.nextval,1,10,'2020-08-26',1);
INSERT INTO Ingredient  (id, detailsId, quantity, expiration, Fridge_id) VALUES  (INGREDIENT_SEQ.nextval,2,40,'2020-09-17',2);
INSERT INTO Ingredient (id, detailsId, quantity, expiration, Fridge_id) VALUES  (INGREDIENT_SEQ.nextval,3,12,'2020-06-01',3);


