
package domain.service;

import java.sql.Date;
import javax.enterprise.context.ApplicationScoped;
import java.lang.Character;

import domain.model.Comment;
import domain.model.Ingredient;
import domain.model.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;


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
	@Transactional
	public long create(Recipe recipe) {
		em.persist(recipe);
		List<Recipe> recipes = getAllRecipes();
		int indexRecipe = recipes.indexOf(recipe);
		Recipe recipedb = recipes.get(indexRecipe);
		long id = recipedb.getId();
		return id;
		
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
	    int total = 0;
	    int n = 0;
	    for(Comment currentComment : commentList) {
	    	n=n+1;
	    	total = total + currentComment.getGrade();
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
	    int total = 0;
	    int n = 0;
	    for(Comment comment : commentList) {
	    	n=n+1;
	    	total = total + comment.getGrade();
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
	
	@Override
	public List<String> cleanSearch(String search){
		
		//We delete some characters and we put everything in lowercase
		search = search.replace(",", " ");
		search = search.replace(";", " ");
		search = search.replace(".", " ");
		search = search.replace(":", " ");
		search = search.replace("l'", "");
		search = search.replace("d'", "");
		
		search = search.toLowerCase();

		//Parse of the string
		List<String> words = new ArrayList<String>(Arrays.asList(search.split(" ")));
		
		//delete last s of the words
		for (int i = 0; i < words.size(); i++) {
			String currentWord = words.get(i);
			if(currentWord.endsWith("s")) {
				currentWord = currentWord.substring(0, currentWord.length() - 1);
			    words.set(i, currentWord);
			}
		}


		//We clean the words :
		
		ListIterator<String> w = words.listIterator();
		while(w.hasNext()){
			String currentWord = w.next();
			
			
			//Delete all common words
			List<String> wordsToDelete = Arrays.asList("et", "au", "aux", "a", "à", "la", "le", "les", "de", "du", "des", "un", "une", "en", "pour",
					"recette", "recettes", "plat", "plats", "cuisine", "cuisines");
			

			if (wordsToDelete.contains(currentWord) || currentWord.length()<=2) {
				w.remove();
			}
			

		}
		
		
		return words;
	}
	
	
	@Override
	public List<Recipe> searchRecipes(String search){
		List<String> words = cleanSearch(search);
		
		
		
		List<Recipe> allRecipes = getAllRecipes();
		List<Recipe> foundRecipes = new ArrayList<Recipe>();

		for (Recipe currentRecipe : allRecipes) {
	    	boolean missSomething = false;
    		String currentName = currentRecipe.getName();
    		currentName = currentName.toLowerCase();
	    	for(String currentWord : words) {
	    		
	    		//Searching in recipe's name
	    		if(! (currentName.contains(currentWord))) {
	    			missSomething = true;
	    		}
	    	}
			
			if(missSomething == false) {
				foundRecipes.add(currentRecipe);
			}
	    }
	    
		if(! foundRecipes.isEmpty()) {
			return foundRecipes;
		}
		return null;
	}
	
	
	
	private Ingredient getRandomIngredient() {
		Ingredient ingredient = new Ingredient();
		ingredient.setDetailsID((long) (Math.random()*1000));
		ingredient.setQuantity((short) (Math.random()*1000));
		
		return ingredient;
	}
	
	private Comment getRandomComment() {
		Comment comment = new Comment();
		comment.setText(UUID.randomUUID().toString());
		comment.setUserID(UUID.randomUUID().toString());
		comment.setGrade((short)(Math.random() * ((5 - 0) + 1)));
		
		return comment;
	}
	
	
	public Recipe getRandomRecipe() {
		
		List<Ingredient> listIng = new ArrayList<Ingredient>();
		Ingredient ingredient1 = getRandomIngredient();
		Ingredient ingredient2 = getRandomIngredient();
		listIng.add(ingredient1);
		listIng.add(ingredient2);
		
		Comment comment1 = getRandomComment();
		Comment comment2 = getRandomComment();
		List<Comment> listComment = new ArrayList<Comment>();
		listComment.add(comment1);
		listComment.add(comment2);
		
		Recipe i = new Recipe();
		i.setName(UUID.randomUUID().toString());
		i.setPreparationTime((short) (Math.random()*1000));
		i.setDifficulty((short) (Math.random()*1000));
		i.setNbPersons((short) (Math.random()*1000));
		i.setPicture(UUID.randomUUID().toString());
		i.setPreparation(UUID.randomUUID().toString());
		i.setAuthor(UUID.randomUUID().toString());
		i.setPublicationDate(Date.valueOf("2019-01-26"));
		i.setGrade((float) (Math.random()*1000));
		i.setComments(listComment);
		i.setIngredients(listIng);

		
		return i;
	}

	
	
}


