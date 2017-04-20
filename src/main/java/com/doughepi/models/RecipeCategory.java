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

/**
 * Michigan Technological University
 * CS3141: Team Software Project
 * <p>
 * Phood
 * <p>
 * A website for the management of recipes.
 * <p>
 * The <code>RecipeCategory</code> represents an ingredient's category.
 *
 * @author Piper Dougherty
 * @author Adam Reichanadter
 * @author De'Shawn Presley
 * @author Tyler Schlomer
 * @author Daniel Morgan
 * @version 1.0.0-Alpha
 * @since 4/20/2016
 */
public enum RecipeCategory {

    /**
     * The possible categories that a user can register their recipe under.
     */
    BEEF("Beef"), CHICKEN("Chicken"), PASTA("Pasta"), PORK("Pork"), SALMON("Salmon"),
    GLUTEN_FREE("Gluten Free"), BREADS("Breads"), CAKES("Cakes"), SALADS("Salads"),
    SMOOTHIES("Smoothies"), SOUPS("Soups"), OTHER("Other");

    /**
     * A more user-friendly string for displaying categories.
     */
    private final String enumText;

    RecipeCategory(String enumText) {
        this.enumText = enumText;
    }

    /**
     * Match an enum instance with the user-friendly string representation.
     *
     * @param categoryName The user-friendly string for the enum we are looking for.
     * @return The matched enum, or the OTHER type if no match was found.
     */
    public static RecipeCategory mapFrom(String categoryName) {
        for (RecipeCategory recipeCategory : RecipeCategory.values()) {
            if (categoryName.equalsIgnoreCase(recipeCategory.getEnumText())) {
                return recipeCategory;
            }
        }
        return OTHER;
    }

    /**
     * Get the user-friendly string representation of this enum instance.
     *
     * @return The user-friendly string of this enum instance.
     */
    public String getEnumText() {
        return enumText;
    }

    /**
     * @return The user-friendly string of this enum instance.
     */
    @Override
    public String toString() {
        return this.getEnumText();
    }
}
