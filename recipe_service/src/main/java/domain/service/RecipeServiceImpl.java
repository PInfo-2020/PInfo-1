
package domain.service;

import java.sql.Date;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

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

import api.msg.RecipeProducer;


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
		em.flush();
		long id = recipe.getId();
		
		return id;
		
	}
	
	@Override
	@Transactional
	public void delete(long id_recipe) {
		Recipe recipe = get(id_recipe);
		em.remove(recipe);
		em.flush();
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
	@Transactional
	public long addComment(long recipeId, Comment comment) {
		Recipe recipe = get(recipeId);
		comment.setRecipe(recipe);
		
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
		
		return comment.getId();
	}
	
	@Override
	@Transactional
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
	
	
	
	
	private List<String> cleanSearch(String search){
		
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
	public List<Object> searchRecipes(String search, Map<Long, String> idNom){
		List<String> words = cleanSearch(search);
		
		// a faire dans searchRecipes
				// getrecipes
				// on a une liste de recipe. on regarde pour chaque recette si un 
				// ingrédient correspnd a la list d'id précédente. si oui, into the result
				
		
		List<String> notFound = new ArrayList<String>();
		boolean foundInRecipeName = false;
		List<Recipe> foundRecipes = new ArrayList<Recipe>();
		List<Recipe> allRecipes = getAllRecipes();
		
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
			
			if(!(missSomething)) {
				foundRecipes.add(currentRecipe);
				foundInRecipeName = true;
			}
	    }
		if(foundRecipes.isEmpty()) {
			List<List<Long>> bigTable = new ArrayList<>();
			for (String currentWord : words) {
				boolean added = false;
				List<Long> littleTable = new ArrayList<>();
				for (Map.Entry<Long, String> entry : idNom.entrySet()) {
					String name = entry.getValue().toLowerCase();
					if(name.contains(currentWord)) {
						littleTable.add(entry.getKey());
						added = true;
					}
				}
				if(!(added)) {
					notFound.add(currentWord);
				}else{
					bigTable.add(littleTable);
				}
			}
			
			
			for (Recipe currentRecipe : allRecipes) {
				List<Ingredient> currentIngredients = currentRecipe.getIngredients();
		    	boolean missSomething = false;
		    	for(List<Long> smallTable :bigTable) {
		    		for (Ingredient ingredient : currentIngredients) {
		
			    		if (!(smallTable.contains(ingredient.getDetailsID()))) {
			    			missSomething = true;
			    		}
		    		}
		    	}
				
				if(!(missSomething)) {
					foundRecipes.add(currentRecipe);
				}
			}
		}
		if(! foundRecipes.isEmpty()) {
			List<Object> result = new ArrayList<>();
			result.add(foundRecipes);
			result.add(foundInRecipeName);
			result.add(notFound);
			return result;
		}
		return null;
	}


	
	
}


