package domain.service;
import java.sql.Date;
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
	public List<Recipe> getAllRecipes() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Recipe> criteria = builder.createQuery(Recipe.class);
		criteria.select(criteria.from(Recipe.class));
		List<Recipe> recipes = em.createQuery("select g from Recipe g",Recipe.class).getResultList();
		return recipes;
	}
	
	@Override
	public Recipe get(Long id) {
		return em.find(Recipe.class, id);
	}

	@Override
	public void create(Recipe recipe) {
		em.persist(recipe);
	}
	@Override
	public Recipe createRecipe(String nom, Map<Long, Short> ingredientsList, List<String> ustensiles, List<String> tags, short temps, String difficulte, short nbPersonnes,
			String photo, String preparation, long auteur, Date date,
			String categorie, String type, float note, long commentaires) {
		Recipe i = new Recipe();
		i.setNom(nom);
		i.setIngredientsOfRecipe(ingredientsList);
		i.setUstensiles(ustensiles);
		i.setTags(tags);
		i.setTempsPreparation(temps);
		i.setDifficulte(difficulte);
		i.setNbPersonnes(nbPersonnes);
		i.setPhoto(photo);
		i.setPreparation(preparation);
		i.setAuteur(auteur);
		i.setDatePublication(date);
		i.setCategoriePlat(categorie);
		i.setTypeCuisine(type);
		i.setNote(note);
		i.setCommentaires(commentaires);

		return i;
	}
}

