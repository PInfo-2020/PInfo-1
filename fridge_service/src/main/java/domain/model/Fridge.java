package domain.model;


import java.util.List;


import javax.persistence.CascadeType;

import javax.persistence.OneToMany;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Fridge {
	@Id
	@SequenceGenerator(name = "FRIDGE_SEQ", sequenceName = "FRIDGE_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FRIDGE_SEQ")
	private long id;
	private String userId;
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, targetEntity=Ingredient.class, mappedBy = "fridge", fetch = FetchType.EAGER, orphanRemoval=true)
	private List<Ingredient> ingredients;
}
