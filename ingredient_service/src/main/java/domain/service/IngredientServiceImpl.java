package domain.service;

import javax.enterprise.context.ApplicationScoped;

import domain.model.Ingredient;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import domain.model.Ingredient;

@ApplicationScoped
public class IngredientServiceImpl implements IngredientService {
	@PersistenceContext(unitName = "IngredientPU")
	private EntityManager em;

public String helloWorld(){
    return "Hello World2";
}

@Override
public Ingredient get(Long id) {
	return em.find(Ingredient.class, id);
}

@Override
public void create(Ingredient ingredient) {
	if (ingredient.getId() != 0) {
		throw new IllegalArgumentException("Instrument already exists : " + ingredient.getId());
	}
	em.persist(ingredient);
}

@Override
public Long count() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<Ingredient> getAll() {
	CriteriaBuilder builder = em.getCriteriaBuilder();
	CriteriaQuery<Ingredient> criteria = builder.createQuery(Ingredient.class);
	criteria.from(Ingredient.class);
	return em.createQuery(criteria).getResultList();
}


}