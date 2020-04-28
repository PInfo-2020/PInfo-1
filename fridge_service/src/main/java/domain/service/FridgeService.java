package domain.service;

import java.util.List;

import domain.model.Fridge;
import domain.model.Ingredient;

public interface FridgeService {
	Fridge get(Long id);
	void create(Fridge fridge);
	List<Fridge> getAll();
	List<Ingredient> getAllIngredient(Long id);
	void updateIngredient(long id, List<Ingredient> newIngredient);
	void deleteFridge(long id);
}
