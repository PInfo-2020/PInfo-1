package domain.service;

import domain.model.Ingredient;

public interface IngredientService {
	Ingredient get(Long id);
	Long count();
    String helloWorld();
	void create(Ingredient ingredient);
}