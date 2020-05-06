package domain.service;

import java.sql.Date;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;

import domain.model.Comment;
import domain.model.Ingredient;
import domain.model.Recipe;
import domain.model.Utensil;

import java.util.List;
import java.util.ListIterator;
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
	public Recipe createRecipe(String name, List<Ingredient> ingredients, List<Utensil> utensils, short prepTime, short difficulty, short nbPersonnes,
			String photo, String preparation, String auteur, Date date,
			String categorie, String type, float note, List<Comment> comments) {
		
		Recipe i = new Recipe();
		i.setName(name);
		i.setIngredients(ingredients);
		i.setUtensils(utensils);
		i.setPreparationTime(prepTime);
		i.setDifficulty(difficulty);
		i.setNbPersons(nbPersonnes);
		i.setPicture(photo);
		i.setPreparation(preparation);
		i.setAuthor(auteur);
		i.setPublicationDate(date);
		i.setPlateCategory(categorie);
		i.setKitchenType(type);
		i.setGrade(note);
		i.setComments(comments);

		return i;

	}
	@Override
	public Ingredient createIngredient(long detailsID, short quantity) {
		Ingredient ingredient = new Ingredient();
		ingredient.setDetailsID(detailsID);
		ingredient.setQuantity(quantity);
		
		return ingredient;
	}
	
	@Override
	public Comment createComment(String text, String userID,short grade) {
		Comment comment = new Comment();
		comment.setText(text);
		comment.setUserID(userID);
		comment.setGrade(grade);
		return comment;
	}
	
	@Override
	public Utensil createUtensil(String name) {
		Utensil utensil = new Utensil();
		utensil.setName(name);
		
		return utensil;
	}
	
	@Override
	public List<Recipe> getListRecipesFromUserId(String userId){

		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Recipe> criteria = builder.createQuery(Recipe.class);
		Root<Recipe> c = criteria.from(Recipe.class);
		criteria.select(c).where(builder.equal(c.get("author"), userId));
		List<Recipe> Listrecipes = em.createQuery(criteria).getResultList();
		
		return Listrecipes;
		
	}
	
	@Override
	public void addComment(long recipeId, Comment comment) {
		Recipe recipe = em.find(Recipe.class, recipeId);
		
		//Addition of the comment
		List<Comment> commentList = recipe.getComments();
		commentList.add(comment);
		recipe.setComments(commentList);
		
		//Update of recipe grade
	    ListIterator<Comment> c = commentList.listIterator();
	    int total = 0;
	    int n = 0;
	    while(c.hasNext()){
	    	n=n+1;
	    	total = total + c.next().getGrade();
	    }
	    float result = (float)total/n;
	    recipe.setGrade(result);
		
		em.flush(); //Update of the recipe
	}
	
	@Override
	public void deleteComment(long recipeId, Comment comment) {
		Recipe recipe = em.find(Recipe.class, recipeId);
		
		//Addition of the comment
		List<Comment> commentList = recipe.getComments();
		long commentId = comment.getId();
		ListIterator<Comment> c1 = commentList.listIterator();
	    while(c1.hasNext()){
	    	if(c1.next().getId()==commentId) {
	    		c1.remove();
	    	}
	    }
		recipe.setComments(commentList);
		
		//Update of recipe grade
	    ListIterator<Comment> c = commentList.listIterator();
	    int total = 0;
	    int n = 0;
	    while(c.hasNext()){
	    	n=n+1;
	    	total = total + c.next().getGrade();
	    }
	    float result = (float)total/n;
	    recipe.setGrade(result);
		
		em.flush(); //Update of the recipe
	}
	
}

