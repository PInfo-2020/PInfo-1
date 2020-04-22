drop sequence if exists RECIPE_SEQ;
create sequence RECIPE_SEQ start with 1 increment by 50;
CREATE TABLE IF NOT EXISTS Recipe (id bigint not null, primary key (id), nom varchar(255), tempsPreparation smallint, difficulte varchar(255),nbPersonnes smallint, photo varchar(255), preparation varchar(255), auteur bigint, datePublication date, categoriePlat varchar(255), typeCuisine varchar(255), note float, commentaires bigint);

TRUNCATE TABLE Recipe; 
INSERT INTO Recipe (id, nom, tempsPreparation, difficulte, nbPersonnes, photo, preparation, auteur, datePublication, categoriePlat, typeCuisine, note, commentaires) VALUES (RECIPE_SEQ.nextval, 'Abricot sucré', 5, 'facile', 2, 'maPhoto', 'Prenez des abricots, coupez-le en deux, enlevez le noyau, ajoutez du sucre,', 372, '2020-04-22', 'dessert', 'français', 4, 32);
INSERT INTO Recipe (id, nom, tempsPreparation, difficulte, nbPersonnes, photo, preparation, auteur, datePublication, categoriePlat, typeCuisine, note, commentaires) VALUES (RECIPE_SEQ.nextval, 'Abricot sec sucré', 2, 'facile', 2, 'monAutrePhoto', 'Prenez des abricots secs, ajoutez du sucre,', 383, '2020-04-23', 'dessert', 'suedois', 3, 37);


CREATE TABLE IF NOT EXISTS Ustensiles (id serial primary key, id_recipe bigint not null, ustensile varchar(255) not null, FOREIGN KEY (id_recipe) REFERENCES Recipe (id), UNIQUE (id_recipe, ustensile));

TRUNCATE TABLE Ustensiles; 
INSERT INTO Ustensiles(id_recipe, ustensile) VALUES ((SELECT id from Recipe WHERE nom='Abricot sucré'), 'Casserole');
INSERT INTO Ustensiles(id_recipe, ustensile) VALUES ((SELECT id from Recipe WHERE nom='Abricot sucré'), 'poele');
INSERT INTO Ustensiles(id_recipe, ustensile) VALUES ((SELECT id from Recipe WHERE nom='Abricot sec sucré'), 'spatulle');
INSERT INTO Ustensiles(id_recipe, ustensile) VALUES ((SELECT id from Recipe WHERE nom='Abricot sec sucré'), 'four');


CREATE TABLE IF NOT EXISTS Tags (id serial primary key, id_recipe bigint not null, tag varchar(255) not null, FOREIGN KEY (id_recipe) REFERENCES Recipe (id), UNIQUE (id_recipe, tag));

TRUNCATE TABLE Tags; 
INSERT INTO Tags(id_recipe, tag) VALUES ((SELECT id from Recipe WHERE nom='Abricot sucré'), 'vegan');
INSERT INTO Tags(id_recipe, tag) VALUES ((SELECT id from Recipe WHERE nom='Abricot sucré'), 'sucré');
INSERT INTO Tags(id_recipe, tag) VALUES ((SELECT id from Recipe WHERE nom='Abricot sec sucré'), 'sec');
INSERT INTO Tags(id_recipe, tag) VALUES ((SELECT id from Recipe WHERE nom='Abricot sec sucré'), 'sucré');


CREATE TABLE IF NOT EXISTS IngredientsRecipe (id serial primary key, id_recipe bigint not null, ingredient varchar(255) not null, quantite smallint, FOREIGN KEY (id_recipe) REFERENCES Recipe (id), UNIQUE (id_recipe, ingredient));

TRUNCATE TABLE IngredientsRecipe; 
INSERT INTO IngredientsRecipe(id_recipe, ingredient, quantite) VALUES ((SELECT id from Recipe WHERE nom='Abricot sucré'), 1, 4);
INSERT INTO IngredientsRecipe(id_recipe, ingredient, quantite) VALUES ((SELECT id from Recipe WHERE nom='Abricot sucré'), 735, 30);
INSERT INTO IngredientsRecipe(id_recipe, ingredient, quantite) VALUES ((SELECT id from Recipe WHERE nom='Abricot sec sucré'), 2, 10);
INSERT INTO IngredientsRecipe(id_recipe, ingredient, quantite) VALUES ((SELECT id from Recipe WHERE nom='Abricot sec sucré'), 735, 20);
