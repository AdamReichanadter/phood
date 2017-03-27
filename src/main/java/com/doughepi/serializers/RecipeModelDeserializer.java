package com.doughepi.serializers;

import com.doughepi.models.IngredientModel;
import com.doughepi.models.RecipeModel;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Created by ajreicha on 3/16/17.
 */
public class RecipeModelDeserializer implements JsonDeserializer<RecipeModel> {
    @Override
    public RecipeModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        RecipeModel recipeModel = new RecipeModel();

        JsonObject recipeJson = jsonElement.getAsJsonObject();

        String recipeName = recipeJson.get("name").getAsString();
        String recipeDescription = recipeJson.get("description").getAsString();
        String recipeCategory = recipeJson.get("category").getAsString();

        IngredientModel[] ingredients = jsonDeserializationContext.deserialize(recipeJson.get("ingredientList"), IngredientModel[].class);

        for (IngredientModel ingredient : ingredients) {
            ingredient.setRecipeModel(recipeModel);
        }

        recipeModel.setIngredientModels(Arrays.asList(ingredients));
        recipeModel.setRecipeName(recipeName);
        recipeModel.setRecipeDescription(recipeDescription);
        recipeModel.setRecipeCategory(recipeCategory);

        return recipeModel;
    }
}
