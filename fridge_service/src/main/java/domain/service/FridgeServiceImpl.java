package domain.service;

import java.util.List;
import java.util.ListIterator;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import domain.model.Fridge;
import domain.model.Ingredient;

@ApplicationScoped
public class FridgeServiceImpl implements FridgeService {
	@PersistenceContext(unitName = "FridgePUT")
	private EntityManager em;
	public FridgeServiceImpl() {
	}

	public FridgeServiceImpl(EntityManager em) {
		this();
		this.em = em;
	}
	
	@Override
	public Fridge get(Long id) {
		return em.find(Fridge.class, id);
	}

	@Override
	public void create(Fridge fridge) {
		em.persist(fridge);
	}

	//@Override
	//public List<Ingredient> getAllIngredient(Long id) {
		//Query query = em.createQuery("select g.ingredients from Fridge g where g.id = :id");
		//query.setParameter("id", id);
		//List<Ingredient> ingredient = query.getResultList();
		//return ingredient;
	//}
	
	@Override
	public List<Ingredient> getAllIngredient(Long id) {
		Fridge fridge = get(id);
		List<Ingredient> IngredientList = fridge.getIngredients();
		return IngredientList;
	}

	@Override
	public List<Fridge> getAll() {
		List<Fridge> fridges = em.createQuery("select g from Fridge g",Fridge.class).getResultList();
		return fridges;
	}

	
	@Override
	public void addIngredient(long fridgeId, Ingredient ingredient) {
		Fridge fridge = get(fridgeId);
		List<Ingredient> ingredients = fridge.getIngredients();
		ingredients.add(ingredient);
		fridge.setIngredients(ingredients);
		em.flush();
	}
	
	@Override
	public void updateIngredient(long fridgeId, Ingredient ingredient) {
		Fridge fridge = get(fridgeId);
		List<Ingredient> ingredients = fridge.getIngredients();
		if(ingredient.getQuantity() == 0) {
			deleteIngredient(fridgeId, ingredient.getDetailsID());
		}
		else {
			ListIterator<Ingredient> i = ingredients.listIterator();
		    while(i.hasNext()){
		    	if(i.next().getDetailsID()==ingredient.getDetailsID()) {
		    		i.set(ingredient);
		    	}
		    }
			fridge.setIngredients(ingredients);
			em.flush();
		}
	}
	
	@Override
	public void addQuantity(long fridgeId, long ingredientId, short quantity) {
		Fridge fridge = get(fridgeId);
		List<Ingredient> ingredients = fridge.getIngredients();
		ListIterator<Ingredient> i = ingredients.listIterator();
		boolean exists = false;
		while(i.hasNext()){
			Ingredient ingredient = i.next();
		    if(ingredient.getDetailsID()==ingredientId) {
		    	exists = true;
		    	short currentQuantity = ingredient.getQuantity();
		    	ingredient.setQuantity((short)(currentQuantity + quantity));
		    	i.set(ingredient);
		    }
		}
		if(exists) {
			fridge.setIngredients(ingredients);
			em.flush();
		}
		else {
			Ingredient ingredient = new Ingredient();
			ingredient.setDetailsID(ingredientId);
			ingredient.setQuantity(quantity);
			addIngredient(fridgeId, ingredient);
		}

	}
	
	@Override
	public void removeQuantity(long fridgeId, long ingredientId, short quantity) {
		Fridge fridge = get(fridgeId);
		List<Ingredient> ingredients = fridge.getIngredients();
		ListIterator<Ingredient> i = ingredients.listIterator();
		boolean exists = false;
		while(i.hasNext()){
			Ingredient ingredient = i.next();
		    if(ingredient.getDetailsID()==ingredientId) {
		    	exists = true;
		    	short currentQuantity = ingredient.getQuantity();
		    	short newQuantity = (short)(currentQuantity - quantity);
		    	if(newQuantity <= 0) {
		    		deleteIngredient(fridgeId, ingredientId);
		    	}
		    	else {
			    	ingredient.setQuantity(newQuantity);
			    	i.set(ingredient);
		    	}
		    	fridge.setIngredients(ingredients);
				em.flush();

		    }
		}
		if(! exists) {
			//return error
		}

	}
	
	@Override
	public void deleteIngredient(long fridgeId, long idIngredient) {
		Fridge fridge = get(fridgeId);
		List<Ingredient> ingredients = fridge.getIngredients();
		ListIterator<Ingredient> i = ingredients.listIterator();
	    while(i.hasNext()){
	    	if(i.next().getDetailsID()==idIngredient) {
	    		i.remove();
	    	}
	    }
		fridge.setIngredients(ingredients);
		em.flush();
	}
	
	@Override
	public void updateFridge(long fridgeId, List<Ingredient> ingredients) {
		Fridge fridge = get(fridgeId);
		fridge.setIngredients(ingredients);
		em.flush();
	}

	@Override
	public void deleteFridge(long id) {
		Fridge fridge = em.find(Fridge.class, id);
		em.remove(fridge);
	}

}
