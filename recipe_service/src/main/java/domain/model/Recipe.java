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
import javax.persistence.OneToMany;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@OneToMany
	private List<Utensil> utensils;
	@OneToMany
	private List<Ingredient> ingredients;
	@OneToMany
	private List<Comment> comments;
	private String name;
	

	private short preparationTime;
	private String difficulty;
	private short nbPersonnes;
	private String photo;
	private String preparation;
	private long auteur;
	private Date datePublication;
	private String categoriePlat;
	private String typeCuisine;
	private float note;
	

	
}
