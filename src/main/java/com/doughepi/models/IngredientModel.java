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

package com.doughepi.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * Michigan Technological University
 * CS3141: Team Software Project
 * <p>
 * Phood
 * <p>
 * A website for the management of recipes.
 * <p>
 * The <code>IngredientModel</code> represents an ingredient in the database.
 *
 * @author Piper Dougherty
 * @author Adam Reichanadter
 * @author De'Shawn Presley
 * @author Tyler Schlomer
 * @author Daniel Morgan
 * @version 1.0.0-Alpha
 * @since 4/20/2016
 */
@Entity
@Table(name = "ingredient")
public class IngredientModel {

    /**
     * The unique id of the ingredient, and is the PRIMARY KEY for the database.
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ingredient_id", length = 16)
    private UUID ingredientID;


    /**
     * The parent recipeModel for this ingredient.
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    private RecipeModel recipeModel;

    /**
     * The number of this ingredient needed for the recipe.
     */
    @Column(name = "ingredient_quantity")
    private double ingredientQuantity;

    /**
     * The name of this ingredient needed for the recipe.
     */
    @Column(name = "ingredient_name")
    private String ingredientName;

    /**
     * The unit needed for this
     */
    @Column(name = "ingredient_unit")
    private String ingredientUnit;


    /**
     * Get the PRIMARY KEY for this ingredient.
     *
     * @return The PRIMARY KEY for this ingredient.
     */
    public UUID getIngredientID() {
        return ingredientID;
    }

    /**
     * Set the new PRIMARY KEY for this ingredient. You probably shouldn't use this in
     * production. Consider making a new ingredient instead.
     *
     * @param ingredientID The new PRIMARY KEY for this ingredient.
     */
    public void setIngredientID(UUID ingredientID) {
        this.ingredientID = ingredientID;
    }


    /**
     * Get the parent recipeModel for this ingredient.
     *
     * @return The parent recipeModel for this ingredient.
     */
    public RecipeModel getRecipeModel() {
        return recipeModel;
    }

    /**
     * Set the parent recipeModel for this ingredient.
     *
     * @param recipeModel The new parent recipeModel for this ingredient.
     */
    public void setRecipeModel(RecipeModel recipeModel) {
        this.recipeModel = recipeModel;
    }


    /**
     * Get the number of this ingredient needed for the recipe.
     *
     * @return The number of this ingredient needed for the recipe.
     */
    public double getIngredientQuantity() {
        return ingredientQuantity;
    }

    /**
     * Update the quantity of this ingredient needed for the recipe.
     *
     * @param ingredientQuantity The new quantity needed for this recipe.
     */
    public void setIngredientQuantity(double ingredientQuantity) {
        this.ingredientQuantity = ingredientQuantity;
    }

    /**
     * Get the name of this ingredient.
     *
     * @return The name of this ingredient.
     */
    public String getIngredientName() {
        return ingredientName;
    }

    /**
     * Set the ingredient's name.
     *
     * @param ingredientName The new name for the ingredient.
     */
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    /**
     * Get the ingredient's unit.
     *
     * @return The ingredient's unit.
     */
    public String getIngredientUnit() {
        return ingredientUnit;
    }

    /**
     * Set the ingredient's unit.
     *
     * @param ingredientUnit The new unit for the ingredient.
     */
    public void setIngredientUnit(String ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
    }
}
