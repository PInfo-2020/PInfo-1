package domain.service;

import java.sql.Date;
import javax.enterprise.context.ApplicationScoped;

import domain.model.Comment;
import domain.model.Ingredient;
import domain.model.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
		
		return em.createQuery("select g from Recipe g",Recipe.class).getResultList();
	}
	
	@Override
	public Recipe get(Long id) {
		return em.find(Recipe.class, id);
	}

	@Override
	public void create(Recipe recipe) {
		em.persist(recipe);
	}
	
	
	public void addRecipe(String name, String picture, short nbPersons, short preparationTime, short difficulty, Map<Long, Short> ingredients, String preparation, String author) {

		Recipe recipe = new Recipe();
		recipe.setName(name);
		recipe.setPreparationTime(preparationTime);
		recipe.setDifficulty(difficulty);
		recipe.setNbPersons(nbPersons);
		recipe.setPicture(picture);
		recipe.setPreparation(preparation);
		
		recipe.setAuthor(author);

		Date date = new java.sql.Date(System.currentTimeMillis());
		recipe.setPublicationDate(date);
		recipe.setGrade(-1);
		List<Comment> comments = new ArrayList<Comment>();
		recipe.setComments(null);
		
		List<Ingredient> ingredientList = new ArrayList<Ingredient>();
		for (Map.Entry<Long,Short> entry : ingredients.entrySet()) {
			Ingredient ing = new Ingredient();
			ing.setDetailsID(entry.getKey());
			ing.setQuantity(entry.getValue());
			ingredientList.add(ing);
		}
		recipe.setIngredients(ingredientList);

		create(recipe);

	}
	

	
	@Override
	public List<Recipe> getListRecipesFromUserId(String userId){

		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Recipe> criteria = builder.createQuery(Recipe.class);
		Root<Recipe> c = criteria.from(Recipe.class);
		criteria.select(c).where(builder.equal(c.get("author"), userId));
		
		return em.createQuery(criteria).getResultList();
		
	}
	
	

	@Override
	public void addComment(long recipeId, Comment comment) {
		Recipe recipe = get(recipeId);
		
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
	    if (n != 0) {
	    	float result = (float)total/n;
	    	recipe.setGrade(result);
	    }
	    
		em.flush(); //Update of the recipe
	}
	
	@Override
	public void deleteComment(long recipeId, long commentId) {
		Recipe recipe = get(recipeId);
		
		//Addition of the comment
		List<Comment> commentList = recipe.getComments();
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
	    if (n!= 0) {
		    float result = (float)total/n;
		    recipe.setGrade(result);
	    }else {
	    	recipe.setGrade(-1);
	    	recipe.setComments(null);
	    }
		em.flush(); //Update of the recipe
	}

	@Override
	public Comment getComment(long recipeId, long commentId) {
		Recipe recipe = get(recipeId);
		List<Comment> commentList = recipe.getComments();
		for (Comment comment : commentList) {
			if (comment.getId() == commentId) {
				return comment;
			}
		}
		return null;
	}
	
}

