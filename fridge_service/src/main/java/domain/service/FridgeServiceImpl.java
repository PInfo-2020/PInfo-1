package domain.service;

import java.util.ArrayList;
import java.util.List;


import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;


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
	public Fridge get(Long fridgeId) {
		return em.find(Fridge.class, fridgeId);
	}
	
	@Override
	public Fridge getByUserId(String userId) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Fridge> criteria = builder.createQuery(Fridge.class);
		Root<Fridge> c = criteria.from(Fridge.class);
		criteria.select(c).where(builder.equal(c.get("userId"), userId));
		
		if(em.createQuery(criteria).getResultList().isEmpty()) {
			return null;
		}
		return em.createQuery(criteria).getResultList().get(0);
	}

	@Override
	@Transactional
	public void create(Fridge fridge) {
		em.persist(fridge);
		em.flush();
	}


	@Override
	public List<Fridge> getAll() {
		return  em.createQuery("select g from Fridge g",Fridge.class).getResultList();
	}


	
	@Override
	@Transactional
	public Boolean updateFridge(Fridge fridge) {
		Fridge myFridge = getByUserId(fridge.getUserId());
		if(myFridge == null) {
			return false;
		}
		else {
			myFridge.getIngredients().clear();

			List<Ingredient> newIngredients = fridge.getIngredients();
			for(Ingredient ingr : newIngredients) {
				ingr.setFridge(myFridge);
			}
			
			myFridge.getIngredients().addAll(newIngredients);
			em.flush();
			return true;
		}
	}
	
	@Override
	public List<Long> getIngredientsId(Fridge fridge){
		List<Long> listIds = new ArrayList<>();
		List<Ingredient> ingredients = fridge.getIngredients();
		if (ingredients != null) {
			for (Ingredient ingredient : ingredients) {
				listIds.add(ingredient.getDetailsID());
			}
		}
			
		return listIds;

	}

	@Override
	@Transactional
	public void deleteFridge(long id) {
		Fridge fridge = em.find(Fridge.class, id);
		em.remove(fridge);
		em.flush();
	}

}
