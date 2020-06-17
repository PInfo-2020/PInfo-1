package domain.service;

import java.util.List;

import domain.model.Comment;
import domain.model.Recipe;


public interface RecipeService {
	List<Recipe> getAllRecipes();
	Recipe get(Long id);
	long create(Recipe recipe);
	List<Recipe> getListRecipesFromUserId(String userId);
	long addComment(long recipeId, Comment comment);
	void deleteComment(long recipeId, long commentId);
	Comment getComment(long recipeId, long commentId);
	List<Recipe> searchRecipes(String search, List<Long> idIngredientFromFridge, List<Long> idQuantityFromFridge);
	void delete(long idRecipe);
}