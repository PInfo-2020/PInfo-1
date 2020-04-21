package domain.service;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;

import domain.model.Ingredient;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import domain.model.Ingredient;

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
	public String helloWorld(){
    return "Hello World2";
	}

@Override
public Ingredient get(Long id) {
	return em.find(Ingredient.class, id);
}

@Override
public void create(Ingredient ingredient) {
	em.persist(ingredient);
}

//@Override
//public List<Ingredient> getAll() {
//	CriteriaBuilder builder = em.getCriteriaBuilder();
//	CriteriaQuery<Ingredient> criteria = builder.createQuery(Ingredient.class);
//	criteria.from(Ingredient.class);
//	return em.createQuery(criteria).getResultList();
//}

@Override
public List<Ingredient> getAllIngredients() {
	CriteriaBuilder builder = em.getCriteriaBuilder();
	CriteriaQuery<Ingredient> criteria = builder.createQuery(Ingredient.class);
	criteria.select(criteria.from(Ingredient.class));
	List<Ingredient> ingredients = em.createQuery("select g from Ingredient g",Ingredient.class).getResultList();
	return ingredients;
}


@Override
public ArrayList<ArrayList<Object>> getAllBaseInfo(){
	
	ArrayList<ArrayList<Object>> tab=new ArrayList<>();
	List<Ingredient> ingredients = getAllIngredients();
	
	for 
	tab.add(new ArrayList<>());
	tab.get(0).add("aaa");
	tab.get(0).add(2);
	tab.get(0).add(456.33);
	tab.add(new ArrayList<>());
	tab.get(0).add("ddd");
	tab.get(0).add(3);
	tab.get(0).add(11122.33);
	return tab;
}

@Override
public Long count() {
	CriteriaBuilder qb = em.getCriteriaBuilder();
	CriteriaQuery<Long> cq = qb.createQuery(Long.class);
	cq.select(qb.count(cq.from(Ingredient.class)));
	return em.createQuery(cq).getSingleResult();
}



}