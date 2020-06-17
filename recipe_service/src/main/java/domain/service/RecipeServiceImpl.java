
package domain.service;


import javax.enterprise.context.ApplicationScoped;


import domain.model.Comment;
import domain.model.Ingredient;
import domain.model.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

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

		return recipe.getId();
		
	}
	
	@Override
	@Transactional
	public void delete(long idRecipe) {
		Recipe recipe = get(idRecipe);
		em.remove(recipe);
		em.flush();
	}
	

	
	@Override
	public List<Recipe> getListRecipesFromUserId(String userId){

		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Recipe> criteria = builder.createQuery(Recipe.class);
		Root<Recipe> c = criteria.from(Recipe.class);
		criteria.select(c).where(builder.equal(c.get("authorId"), userId));
		
		return em.createQuery(criteria).getResultList();
		
	}
	
	

	@Override
	@Transactional
	public long addComment(long recipeId, Comment comment) {
		Recipe recipe = get(recipeId);
		comment.setRecipe(recipe);
		
		//Addition of the comment
		List<Comment> commentList = recipe.getComments();
		//If the user has already commented this recipe, we modify this comment
		Boolean commentExists = false;
		long idOfComment = -1;
		for(Comment myComment: commentList) {
			if(myComment.getUserID().equals(comment.getUserID())) {
				commentExists = true;
				myComment.setText(comment.getText());
				myComment.setGrade(comment.getGrade());
				idOfComment = myComment.getId();
			}
		}
		if(!commentExists) {
			commentList.add(comment);
		}
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
		if(commentExists) {
			return idOfComment;
		}
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
		List<String> words = new ArrayList<>(Arrays.asList(search.split(" ")));
		
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
	public List<Recipe> searchRecipes(String search, List<Long> idIngredientFromFridge, List<Long> idQuantityFromFridge){
		
		List<String> words = cleanSearch(search);
		
			
		List<Recipe> foundRecipes = new ArrayList<>();
		List<Recipe> allRecipes = getAllRecipes();
		
		//recherche dans nom recette
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
			}
	    }
		
		if(! foundRecipes.isEmpty()) {
			if(!(idIngredientFromFridge.isEmpty())) {
				
				Map<Long,Long> idQuantity = new HashMap<Long, Long>();
				Iterator<Long> idi = idIngredientFromFridge.iterator();
				Iterator<Long> quantityi = idQuantityFromFridge.iterator();
				while (idi.hasNext() && quantityi.hasNext()) {
					idQuantity.put(idi.next(), quantityi.next());
				}
				
				
				ListIterator<Recipe> i = foundRecipes.listIterator();
				while (i.hasNext()) {
					boolean hasEverythingWithEnoughQuantity = true;
					Recipe recipe = i.next();
					List<Ingredient> currentIngredients = recipe.getIngredients();
	
		    		for (Ingredient ingredient : currentIngredients) {
		    			Boolean hasItWithEnoughQuantity = false;
		    			for (Map.Entry<Long, Long> entry : idQuantity.entrySet()) {
		    				if(entry.getKey()==ingredient.getDetailsID() && entry.getValue() >= ingredient.getQuantity()) {
		    					hasItWithEnoughQuantity = true;
		    				}
		    			}
			    		if (!hasItWithEnoughQuantity) {
			    			hasEverythingWithEnoughQuantity = false;	
			    		}
		    		}
					if(!(hasEverythingWithEnoughQuantity)) {
						i.remove();
					}
				}
			}
			
			
			return foundRecipes;
		}
		return Collections.emptyList();
	}


	
	
}


