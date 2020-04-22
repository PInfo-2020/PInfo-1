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
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CompoundSelection;

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
public List<Object[]> getAllBaseInfo(){ //Id, nom, unité
	
	//ArrayList<ArrayList<Object>> tab=new ArrayList<>();
	//List<Ingredient> ingredients = getAllIngredients();
	
	//for (int i=0; i<ingredients.size(); i++) {
	//	tab.add(new ArrayList<>());
	//	tab.get(i).add(ingredients.get(i).getId());
	//	tab.get(i).add(ingredients.get(i).getNom());
	//	tab.get(i).add(ingredients.get(i).getUnite());
	//}
	
	//return tab;
	
	CriteriaBuilder builder = em.getCriteriaBuilder();
	CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
	Root<Ingredient> c = criteria.from(Ingredient.class);
	criteria.multiselect(c.get("id"), c.get("nom"), c.get("unite"));
	List<Object[]> results = em.createQuery(criteria).getResultList();
	
	return results;
	
}


@Override
public Long count() {
	CriteriaBuilder qb = em.getCriteriaBuilder();
	CriteriaQuery<Long> cq = qb.createQuery(Long.class);
	cq.select(qb.count(cq.from(Ingredient.class)));
	return em.createQuery(cq).getSingleResult();
}



}