package domain.service;

import java.util.List;

import domain.model.Fridge;
import domain.model.Ingredient;

public interface FridgeService {
	Fridge get(Long id);
	void create(Fridge fridge);
	List<Fridge> getAll();
	List<Ingredient> getAllIngredient(Long id);
	void updateIngredient(long fridgeId, Ingredient ingredient);
	void addQuantity(long fridgeId, long ingredientId, short quantity);
	public void removeQuantity(long fridgeId, long ingredientId, short quantity);
	void addIngredient(long fridgeId, Ingredient ingredient);
	void deleteIngredient(long fridgeId, long idIngredient);
	void updateFridge(long fridgeId, List<Ingredient> ingredients);
	void deleteFridge(long id);
}
