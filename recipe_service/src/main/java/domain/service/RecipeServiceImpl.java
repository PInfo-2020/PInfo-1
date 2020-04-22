package domain.service;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;

import domain.model.Recipe;

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
public class RecipeServiceImpl implements RecipeService{
	@PersistenceContext(unitName = "RecipePU")
	private EntityManager em;
	public RecipeServiceImpl() {
	}

	public RecipeServiceImpl(EntityManager em) {
		this();
		this.em = em;
	}
	
	@Override
	public Recipe get(Long id) {
		return em.find(Recipe.class, id);
	}

	@Override
	public void create(Recipe recipe) {
		em.persist(recipe);
	}
}
