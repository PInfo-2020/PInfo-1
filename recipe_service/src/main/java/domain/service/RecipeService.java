package domain.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import domain.model.Comment;
import domain.model.Ingredient;
import domain.model.Recipe;


public interface RecipeService {
	List<Recipe> getAllRecipes();
	Recipe get(Long id);
	long create(Recipe recipe);
	List<Recipe> getListRecipesFromUserId(String userId);
	long addComment(long recipeId, Comment comment);
	void deleteComment(long recipeId, long commentId);
	Comment getComment(long recipeId, long commentId);
	void addRecipe(String name, String picture, short nbPersons, short preparationTime, short difficulty, Map<Long, Short> ingredients, String preparation, String author);
	List<Recipe> searchRecipes(String search);
	List<String> cleanSearch(String search);
	void delete(long id_recipe);
}