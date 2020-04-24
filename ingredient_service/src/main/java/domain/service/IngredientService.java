package domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import domain.model.Ingredient;

public interface IngredientService {
	List<Ingredient> getAllIngredients();
	Ingredient get(Long id);
	Long count();
	void create(Ingredient ingredient);
	List<Object[]> getAllMinInfos();
}