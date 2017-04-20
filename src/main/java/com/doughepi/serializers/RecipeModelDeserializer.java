package com.doughepi.serializers;

import com.doughepi.models.IngredientModel;
import com.doughepi.models.RecipeCategory;
import com.doughepi.models.RecipeModel;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Created by ajreicha on 3/16/17.
 */

/**
 * Michigan Technological University
 * CS3141: Team Software Project
 * <p>
 * Phood
 * <p>
 * A website for the management of recipes.
 * <p>
 * The <code>RecipeModelDeserializer</code> Takes in json elements and turn them into java objects
 *
 * @author Piper Dougherty
 * @author Adam Reichanadter
 * @author De'Shawn Presley
 * @author Tyler Schlomer
 * @author Daniel Morgan
 * @version 1.0.0-Alpha
 * @since 4/20/2016
 */
public class RecipeModelDeserializer implements JsonDeserializer<RecipeModel> {
    /**
     * @param jsonElement Json element being injected for deserialization
     * @param type Type is the common superinterface for all types in the Java
     * programming language. These include raw types, parameterized types,
     * array types, type variables and primitive types.
     * @param jsonDeserializationContext Injects as instance of JsonDeserializationContext by spring
     * @return a RecipeModel object
     * @throws JsonParseException
     */
    @Override
    public RecipeModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        RecipeModel recipeModel = new RecipeModel();
        JsonObject recipeJson = jsonElement.getAsJsonObject();

        String recipeName = recipeJson.get("name").getAsString();
        String recipeDescription = recipeJson.get("description").getAsString();
        String preparationInstructions = recipeJson.get("preparationInstructions").getAsString();
        RecipeCategory recipeCategory = RecipeCategory.mapFrom(recipeJson.get("category").getAsString());
        IngredientModel[] ingredients = jsonDeserializationContext.deserialize(recipeJson.get("ingredientList"), IngredientModel[].class);

        for (IngredientModel ingredient : ingredients) {
            ingredient.setRecipeModel(recipeModel);
        }

        recipeModel.setIngredientModels(Arrays.asList(ingredients));
        recipeModel.setRecipeName(recipeName);
        recipeModel.setRecipeDescription(recipeDescription);
        recipeModel.setRecipeCategory(recipeCategory);
        recipeModel.setRecipePreparationInstructions(preparationInstructions);
        return recipeModel;
    }
}
