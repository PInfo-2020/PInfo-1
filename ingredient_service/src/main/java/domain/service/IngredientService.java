package domain.service;

import java.util.List;



import domain.model.Ingredient;

public interface IngredientService {
	List<Ingredient> getAllIngredients();
	Ingredient get(Long id);
	void create(Ingredient ingredient);
	List<Object[]> getAllMinInfos();
	List<Object[]> getIdName();
}