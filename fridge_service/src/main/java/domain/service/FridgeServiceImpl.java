package domain.service;

import java.util.List;

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

	@Override
	public List<Ingredient> getAllIngredient(Long id) {
		Query query = em.createQuery("select g.ingredients from Fridge g where g.id = :id");
		query.setParameter("id", id);
		List<Ingredient> ingredient = query.getResultList();
		return ingredient;
	}

	@Override
	public List<Fridge> getAll() {
		List<Fridge> fridges = em.createQuery("select g from Fridge g",Fridge.class).getResultList();
		return fridges;
	}

	@Override
	public void updateIngredient(long id, List<Ingredient> newIngredient) {
		Fridge fridge = em.find(Fridge.class, id);
		fridge.setIngredients(newIngredient);
		em.flush();
	}

	@Override
	public void deleteFridge(long id) {
		Fridge fridge = em.find(Fridge.class, id);
		em.remove(fridge);
	}

}
