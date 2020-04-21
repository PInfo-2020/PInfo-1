package domain.model;

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
	private String ingredients[];
	
	
}
