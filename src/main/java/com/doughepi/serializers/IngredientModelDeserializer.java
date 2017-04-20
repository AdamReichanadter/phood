package com.doughepi.serializers;

import com.doughepi.models.IngredientModel;
import com.google.gson.*;

import java.lang.reflect.Type;

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
 * The <code>IngredientModelDeserializer</code> Takes in json elements and turn them into java objects
 *
 * @author Piper Dougherty
 * @author Adam Reichanadter
 * @author De'Shawn Presley
 * @author Tyler Schlomer
 * @author Daniel Morgan
 * @version 1.0.0-Alpha
 * @since 4/20/2016
 */
public class IngredientModelDeserializer implements JsonDeserializer<IngredientModel> {

    /**
     * @param jsonElement Json element being injected for deserialization
     * @param type Type is the common superinterface for all types in the Java
     * programming language. These include raw types, parameterized types,
     * array types, type variables and primitive types.
     * @param jsonDeserializationContext Injects as instance of JsonDeserializationContext by spring
     * @return an IngredientModel object
     * @throws JsonParseException
     */
    @Override
    public IngredientModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject ingredientJson = jsonElement.getAsJsonObject();

        IngredientModel ingredientModel = new IngredientModel();

        String ingredientQuantity = ingredientJson.get("ingredientQuantity").getAsString();
        String ingredientUnit = ingredientJson.get("ingredientUnit").getAsString();
        String ingredientName = ingredientJson.get("ingredientName").getAsString();

        ingredientModel.setIngredientQuantity(Double.parseDouble(ingredientQuantity));
        ingredientModel.setIngredientUnit(ingredientUnit);
        ingredientModel.setIngredientName(ingredientName);

        return ingredientModel;
    }
}