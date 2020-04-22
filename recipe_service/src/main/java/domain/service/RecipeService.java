package domain.service;

import java.util.List;


import domain.model.Recipe;

public interface RecipeService {

	Recipe get(Long id);
	void create(Recipe recipe);
}
