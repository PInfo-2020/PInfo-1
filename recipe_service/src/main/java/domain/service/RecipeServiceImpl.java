package domain.service;
import java.sql.Date;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;

import domain.model.Comment;
import domain.model.Ingredient;
import domain.model.Recipe;
import domain.model.Utensil;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CompoundSelection;

@ApplicationScoped
public class RecipeServiceImpl implements RecipeService{
	@PersistenceContext(unitName = "RecipePU")
	private EntityManager em;
	public RecipeServiceImpl() {
	}

	public RecipeServiceImpl(EntityManager em) {
		this();
		this.em = em;
	}
	
	@Override
	public List<Recipe> getAllRecipes() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Recipe> criteria = builder.createQuery(Recipe.class);
		criteria.select(criteria.from(Recipe.class));
		List<Recipe> recipes = em.createQuery("select g from Recipe g",Recipe.class).getResultList();
		return recipes;
	}
	
	@Override
	public Recipe get(Long id) {
		return em.find(Recipe.class, id);
	}

	@Override
	public void create(Recipe recipe) {
		em.persist(recipe);
	}
	@Override
	public Recipe createRecipe(String name, List<Ingredient> ingredients, List<Utensil> utensils, short prepTime, String difficulty, short nbPersonnes,
			String photo, String preparation, long auteur, Date date,
			String categorie, String type, float note, List<Comment> comments) {
		Recipe i = new Recipe();
		i.setName(name);
		i.setIngredients(ingredients);
		i.setUtensils(utensils);
		i.setPreparationTime(prepTime);
		i.setDifficulty(difficulty);
		i.setNbPersonnes(nbPersonnes);
		i.setPhoto(photo);
		i.setPreparation(preparation);
		i.setAuteur(auteur);
		i.setDatePublication(date);
		i.setCategoriePlat(categorie);
		i.setTypeCuisine(type);
		i.setNote(note);
		i.setComments(comments);

		return i;

	}
	@Override
	public Ingredient createIngredient(short quantity, long detailsID) {
		Ingredient ingredient = new Ingredient();
		ingredient.setDetailsID(detailsID);
		ingredient.setQuantity(quantity);
		
		return ingredient;
		
	}
	@Override
	public Comment createComment(String text, long userID,short grade) {
		Comment comment = new Comment();
		comment.setText(text);
		comment.setGrade(grade);
		comment.setUserID(userID);
		return comment;
	}
	@Override
	public Utensil createUtensil(String name) {
		Utensil utensil = new Utensil();
		utensil.setName(name);
		return utensil;
	}
}

