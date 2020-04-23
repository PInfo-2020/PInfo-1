package domain.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OrderColumn;
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
	
	@ElementCollection
	@CollectionTable(name="Ustensiles",joinColumns=@JoinColumn(name="id_recipe"))
	@Column(name="ustensile")
	private List<String> ustensiles;
	
	@ElementCollection
	@CollectionTable(name="Tags",joinColumns=@JoinColumn(name="id_recipe"))
	@Column(name="tag")
	private List<String> tags;
	
	@ElementCollection
	@CollectionTable(name = "IngredientsRecipe", 
	       joinColumns = @JoinColumn(name = "id_recipe"))
	@MapKeyColumn(name = "ingredient")
	@Column(name = "quantite")
	private Map<String, Short> ingredientsOfRecipe;
	
}
