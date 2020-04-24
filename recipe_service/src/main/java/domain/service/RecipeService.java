package domain.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import domain.model.Recipe;

public interface RecipeService {
	List<Recipe> getAllRecipes();
	Recipe get(Long id);
	void create(Recipe recipe);
	Recipe createRecipe(String nom, Map<Long, Short> ingredientsList, List<String> ustensiles, List<String> tags, short temps, String difficulte, short nbPersonnes,
			String photo, String preparation, long auteur, Date date,
			String categorie, String type, float note, long commentaires);
}
