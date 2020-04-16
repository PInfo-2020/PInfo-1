package domain.service;

import java.util.List;

import domain.model.Ingredient;

public interface IngredientService {
	List<Ingredient> getAll();
	Ingredient get(Long id);
	Long count();
    String helloWorld();
	void create(Ingredient ingredient);
}