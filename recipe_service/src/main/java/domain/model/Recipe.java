package domain.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@XmlRootElement
@Table(name ="Recipe")
public class Recipe {
	@Id
	@SequenceGenerator(name = "RECIPE_SEQ", sequenceName = "RECIPE_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECIPE_SEQ")
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	
	private String name;
	private String picture;
	private short nbPersons;
	private short preparationTime;
	private short difficulty;
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, targetEntity=Ingredient.class, mappedBy = "recipeIng", fetch = FetchType.LAZY)
	//@OneToMany(cascade = CascadeType.ALL, targetEntity=Ingredient.class, mappedBy="recipeIng")
	private List<Ingredient> ingredients;
	private String preparation;

	private String author;

	private Date publicationDate;
	

	private float grade;
	
	
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//@JoinColumn(name = "Recipe_ID", nullable = true)
	//private Set<Ingredient> ingredients;
	
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//@JoinColumn(name = "Recipe_ID", nullable = true)
	//private Set<Comment> comments;
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, targetEntity=Comment.class, mappedBy = "recipe", fetch = FetchType.LAZY)
	//@OneToMany(cascade = CascadeType.ALL, targetEntity=Comment.class, mappedBy="recipe")
	private List<Comment> comments;
	
	

	
}
