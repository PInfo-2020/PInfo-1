package domain.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name ="Recipe")
public class Recipe {
	@Id
	@SequenceGenerator(name = "RECIPE_SEQ", sequenceName = "RECIPE_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECIPE_SEQ")
	private long id;
	private String nom;
	private short tempsPreparation;
	private String difficulte;
	private short nbPersonnes;
	private String photo;
	private String preparation;
	private long auteur;
	private Date datePublication;
	private String categoriePlat;
	private String typeCuisine;
	private float note;
	private long commentaires;
	

}
