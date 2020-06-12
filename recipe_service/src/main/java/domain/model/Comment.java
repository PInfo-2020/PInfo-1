package domain.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name ="Comment")
public class Comment {
	@Id
	@SequenceGenerator(name = "COMMENT_SEQ", sequenceName = "COMMENT_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_SEQ")
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	 
    //@ManyToOne
    //private Recipe recipe;
	private String text;
	private String userID;
	private short grade;
	
	@JsonBackReference
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "Recipe_id")
	private Recipe recipe;
	
}
