package domain.service;

import javax.enterprise.context.ApplicationScoped;

import domain.model.Ingredient;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@ApplicationScoped
public class IngredientServiceImpl implements IngredientService {
	@PersistenceContext(unitName = "IngredientPU")
	private EntityManager em;
	public IngredientServiceImpl() {
	}

	public IngredientServiceImpl(EntityManager em) {
		this();
		this.em = em;
	}

@Override
public Ingredient get(Long id) {
	return em.find(Ingredient.class, id);
}

@Override
public void create(Ingredient ingredient) {
	em.persist(ingredient);
}


@Override
public List<Ingredient> getAllIngredients() {
	CriteriaBuilder builder = em.getCriteriaBuilder();
	CriteriaQuery<Ingredient> criteria = builder.createQuery(Ingredient.class);
	criteria.select(criteria.from(Ingredient.class));

	return em.createQuery("select g from Ingredient g",Ingredient.class).getResultList();
}


@Override
public List<Object[]> getAllMinInfos(){ //Id, nom, unité
	
	
	CriteriaBuilder builder = em.getCriteriaBuilder();
	CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
	Root<Ingredient> c = criteria.from(Ingredient.class);
	criteria.multiselect(c.get("id"), c.get("name"), c.get("unity"));
	
	return em.createQuery(criteria).getResultList();
	
}

@Override
public List<Object[]> getIdName(){ //Id, nom, unité
	
	
	CriteriaBuilder builder = em.getCriteriaBuilder();
	CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
	Root<Ingredient> c = criteria.from(Ingredient.class);
	criteria.multiselect(c.get("id"), c.get("name"));
	
	return em.createQuery(criteria).getResultList();
	
}



}