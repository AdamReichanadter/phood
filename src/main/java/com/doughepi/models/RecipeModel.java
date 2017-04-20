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
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Michigan Technological University
 * CS3141: Team Software Project
 * <p>
 * Phood
 * <p>
 * A website for the management of recipes.
 * <p>
 * The <code>RecipeModel</code> represents a recipe owned by a user.
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
@Table(name = "recipe")
@Indexed
public class RecipeModel {

    /**
     * The PRIMARY KEY for this recipe.
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "recipe_id", length = 16)
    private UUID recipeID;

    /**
     * The parent userModel that owns this recipe.
     */
    @IndexedEmbedded
    @ManyToOne(cascade = CascadeType.MERGE)
    private UserModel userModel;

    /**
     * The date this recipe was inserted into the database.
     */
    @Column(name = "creation_date", columnDefinition = "DATETIME NOT NULL DEFAULT NOW()")
    private Date creationDate;

    /**
     * The name of this recipe.
     */
    @Column(name = "recipe_name")
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String recipeName;

    /**
     * The description of this recipe.
     */
    @Column(name = "recipe_description", columnDefinition = "TEXT")
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String recipeDescription;

    /**
     * The preparation instructions for this recipe.
     */
    @Column(name = "recipe_preperation_instructions")
    private String recipePreparationInstructions;

    /**
     * The recipeCategory this recipe is filed under.
     */
    @Column(name = "recipe_category")
    @Enumerated(EnumType.STRING)
    private RecipeCategory recipeCategory;

    /**
     * A wrapper for the recipeCateogory.getEnumText() method to allow Hibernate Search to index
     * a recipe by its category name.
     */
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
    @Transient
    private String categoryName;

    /**
     * A list of ingredientModels this recipe contains.
     */
    @OneToMany(mappedBy = "recipeModel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<IngredientModel> ingredientModels;

    /**
     * A list of imageModels this recipe owns.
     */
    @OneToMany(mappedBy = "recipeModel", cascade = CascadeType.ALL)
    private List<ImageModel> imageModels;

    /**
     * The number of likes this recipe has accrued.
     */
    @Column(name = "likes")
    private int likes;

    /*
     * Getters and setters.
     */

    public UUID getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(UUID recipeID) {
        this.recipeID = recipeID;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public String getRecipePreparationInstructions() {
        return recipePreparationInstructions;
    }

    public void setRecipePreparationInstructions(String recipePreparationInstructions) {
        this.recipePreparationInstructions = recipePreparationInstructions;
    }

    public RecipeCategory getRecipeCategory() {
        return recipeCategory;
    }

    public void setRecipeCategory(RecipeCategory recipeCategory) {
        this.recipeCategory = recipeCategory;
    }

    public List<IngredientModel> getIngredientModels() {
        return ingredientModels;
    }

    public void setIngredientModels(List<IngredientModel> ingredientModels) {
        this.ingredientModels = ingredientModels;
    }

    public String getCategoryName() {
        return recipeCategory.getEnumText();
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<ImageModel> getImageModels() {
        return imageModels;
    }

    public void setImageModels(List<ImageModel> imageModels) {
        this.imageModels = imageModels;
    }
}
