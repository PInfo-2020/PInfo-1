package domain.model;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
	import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="Ingredient")
public class Ingredient {
	@Id
	@SequenceGenerator(name = "INGREDIENT_SEQ", sequenceName = "INGREDIENT_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INGREDIENT_SEQ")	
	private long id;
	private String nom;
    private Integer poid_moyen;
    private String unite;
    private String categorie;
    
	
}