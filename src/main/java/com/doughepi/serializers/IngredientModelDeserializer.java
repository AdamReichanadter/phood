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
import com.google.gson.*;

import java.lang.reflect.Type;

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