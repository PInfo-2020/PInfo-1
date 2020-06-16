package domain.model;


import javax.persistence.Column;

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
@Table(name ="Ingredient")
public class Ingredient {
	@Id
	@SequenceGenerator(name = "INGREDIENT_SEQ", sequenceName = "INGREDIENT_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INGREDIENT_SEQ")	
	private long id;
	@Column(name="name")
	private String name;
	@Column(name = "averageWeight")
    private Integer averageWeight;
	@Column(name = "unity")
    private String unity;
	@Column(name="category")
    private String category;
    
	
}