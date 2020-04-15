package domain.service;

import javax.enterprise.context.ApplicationScoped;

import domain.model.Ingredient;

@ApplicationScoped
public class IngredientServiceImpl implements IngredientService {

public String helloWorld(){

    return "Hello World2";
}


}