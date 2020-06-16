package domain.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;



@Getter
@Setter
@Entity
@Table(name ="Recipe")
public class Recipe {
	@Id
	@SequenceGenerator(name = "RECIPE_SEQ", sequenceName = "RECIPE_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECIPE_SEQ")
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	
	private String name;
	private String picture;
	private short people;
	private short preparationTime;
	private short difficulty;
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, targetEntity=Ingredient.class, mappedBy = "recipeIng", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Ingredient> ingredients;
	private String preparation;

	private String author;

	private Date publicationDate;
	

	private float grade;
	
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, targetEntity=Comment.class, mappedBy = "recipe", fetch = FetchType.EAGER, orphanRemoval=true)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Comment> comments;
	
	

	
}
