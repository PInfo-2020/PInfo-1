package domain.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
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
	//@OneToMany(cascade = CascadeType.ALL)
	//private List<Utensil> utensils;

	
	private String name;
	private String picture;
	private short nbPersons;
	private short preparationTime;
	private short difficulty;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Ingredient> ingredients;
	private String preparation;
	// pas dans le json
	private String author;
	//pas de json
	private Date publicationDate;
	//private String plateCategory;
	//private String KitchenType;
	
	// commence a null
	private float grade;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Comment> comments;
	
	

	
}
