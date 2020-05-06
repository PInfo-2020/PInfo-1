package domain.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import domain.model.Comment;
import domain.model.Ingredient;
import domain.model.Recipe;
import domain.model.Utensil;

public interface RecipeService {
	List<Recipe> getAllRecipes();
	Recipe get(Long id);
	void create(Recipe recipe);
	Recipe createRecipe(String name, List<Ingredient> ingredients, List<Utensil> utensils, short prepTime, short difficulty, short nbPersonnes,
			String photo, String preparation, String auteur, Date date,
			String categorie, String type, float note, List<Comment> comments);
	Ingredient createIngredient(long detailsID, short quantity);
	Comment createComment(String text, String userID,short grade);
	Utensil createUtensil(String name);
	List<Recipe> getListRecipesFromUserId(String userId);
	void addComment(long recipeId, Comment comment);
}
