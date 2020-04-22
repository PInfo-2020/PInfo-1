drop sequence if exists RECIPE_SEQ;
create sequence RECIPE_SEQ start with 1 increment by 50;
CREATE TABLE IF NOT EXISTS Recipe (
	id bigint not null,
	primary key (id),
	nom varchar(255),
	ustensiles varchar(max),
	tags varchar(max),
	tempsPreparation smallint,
	difficulte varchar(255),
	nbPersonnes smallint,
	photo varchar(255),
	preparation varchar(255),
	auteur bigint,
	datePublication date,
	categoriePlat varchar(255),
	typeCuisine varchar(255),
	note float(),
	commentaires bigint
	
	
);
