package domain.model;
import java.math.BigDecimal;
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
@Table(name="BDD_ingredient")
public class Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;
    @Column(name = "NOM")
	private @Getter String nom;
    @Column(name ="POIDS_MOYEN_G")
    private int poid_moyen;
    @Column(name = "UNITES") 
    private String unite;
    @Column(name ="CATEGORIE")
    private String categorie;
    
	
}