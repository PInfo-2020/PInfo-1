package domain.service;

import java.util.List;

import domain.model.Recipe;

public interface RecipeService {
	List<Recipe> getAllRecipes();
	Recipe get(Long id);
	void create(Recipe recipe);
}
