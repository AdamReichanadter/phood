/*
 * Copyright (c) 2017 Piper Dougherty, Adam Reichanadter, De'Shawn Presley, Tyler Schlomer, Daniel Morgan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.doughepi.serializers;

import com.doughepi.models.IngredientModel;
import com.doughepi.models.RecipeCategory;
import com.doughepi.models.RecipeModel;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Arrays;

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
