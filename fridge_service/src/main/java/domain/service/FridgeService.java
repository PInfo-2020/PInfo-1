package domain.service;

import java.util.List;

import domain.model.Fridge;


public interface FridgeService {
	Fridge get(Long id);
	Fridge getByUserId(String userId);
	void create(Fridge fridge);
	List<Fridge> getAll();
	Boolean updateFridge(Fridge fridge);
	void deleteFridge(long id);
	List<Long> getIngredientsId(Fridge fridge);
}
