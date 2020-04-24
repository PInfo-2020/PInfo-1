drop sequence if exists RECIPE_SEQ;
create sequence RECIPE_SEQ start with 1 increment by 1;

drop table IngredientsRecipe if exists;
drop table Recipe if exists;
CREATE TABLE IF NOT EXISTS IngredientsRecipe (id serial primary key, id_recipe bigint not null, ingredient bigint not null, quantite smallint, FOREIGN KEY (id_recipe) REFERENCES Recipe (id), UNIQUE (id_recipe, ingredient));
CREATE TABLE IF NOT EXISTS Recipe (id bigint not null, primary key (id), nom varchar(255), tempsPreparation smallint, difficulte varchar(255),nbPersonnes smallint, photo varchar(255), preparation varchar(255), auteur bigint, datePublication date, categoriePlat varchar(255), typeCuisine varchar(255), note float, commentaires bigint);

TRUNCATE TABLE Recipe; 
INSERT INTO Recipe (id, nom, tempsPreparation, difficulte, nbPersonnes, photo, preparation, auteur, datePublication, categoriePlat, typeCuisine, note, commentaires) VALUES (RECIPE_SEQ.nextval, 'Abricot sucré', 5, 'facile', 2, 'maPhoto', 'Prenez des abricots, coupez-le en deux, enlevez le noyau, ajoutez du sucre,', 372, '2020-04-22', 'dessert', 'français', 4, 32);
INSERT INTO Recipe (id, nom, tempsPreparation, difficulte, nbPersonnes, photo, preparation, auteur, datePublication, categoriePlat, typeCuisine, note, commentaires) VALUES (RECIPE_SEQ.nextval, 'Abricot sec sucré', 2, 'facile', 2, 'monAutrePhoto', 'Prenez des abricots secs, ajoutez du sucre,', 383, '2020-04-23', 'dessert', 'suedois', 3, 37);



TRUNCATE TABLE IngredientsRecipe; 
INSERT INTO IngredientsRecipe(id_recipe, ingredient, quantite) VALUES ((SELECT id from Recipe WHERE nom='Abricot sucré'), 1, 4);
INSERT INTO IngredientsRecipe(id_recipe, ingredient, quantite) VALUES ((SELECT id from Recipe WHERE nom='Abricot sucré'), 735, 30);
INSERT INTO IngredientsRecipe(id_recipe, ingredient, quantite) VALUES ((SELECT id from Recipe WHERE nom='Abricot sec sucré'), 2, 10);
INSERT INTO IngredientsRecipe(id_recipe, ingredient, quantite) VALUES ((SELECT id from Recipe WHERE nom='Abricot sec sucré'), 735, 20);

