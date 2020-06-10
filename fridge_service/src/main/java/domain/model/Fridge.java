package domain.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
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
