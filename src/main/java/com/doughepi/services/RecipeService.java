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

package com.doughepi.services;

import com.doughepi.models.IngredientModel;
import com.doughepi.models.RecipeCategory;
import com.doughepi.models.RecipeModel;
import com.doughepi.models.UserModel;
import com.doughepi.repositories.RecipeRepository;
import com.doughepi.repositories.UserRepository;
import com.doughepi.serializers.IngredientModelDeserializer;
import com.doughepi.serializers.RecipeModelDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Michigan Technological University
 * CS3141: Team Software Project
 * <p>
 * Phood
 * <p>
 * A website for the management of recipes.
 * <p>
 * The <code>RecipeService</code> Handles all recipe related interactions
 *
 * @author Piper Dougherty
 * @author Adam Reichanadter
 * @author De'Shawn Presley
 * @author Tyler Schlomer
 * @author Daniel Morgan
 * @version 1.0.0-Alpha
 * @since 4/20/2016
 */
@Service
public class RecipeService {

    @Autowired
    private
    UserRepository userRepository;
    @Autowired
    private
    UserService userService;

    @Autowired
    private
    RecipeRepository recipeRepository;


    /**
     * @param recipeModel Json string to be turned into a java object
     */
    public void createRecipe(@RequestBody String recipeModel) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(RecipeModel.class, new RecipeModelDeserializer());
        gsonBuilder.registerTypeAdapter(IngredientModel.class, new IngredientModelDeserializer());

        Gson gson = gsonBuilder.create();

        RecipeModel createdRecipe = gson.fromJson(recipeModel, RecipeModel.class);
        createdRecipe.setCreationDate(new Date());

        UserModel currentLoggedInUser = userService.getCurrentLoggedInUser();
        createdRecipe.setUserModel(currentLoggedInUser);
        currentLoggedInUser.getRecipeModels().add(createdRecipe);

        UserModel save = userRepository.save(currentLoggedInUser);
        for (RecipeModel model : save.getRecipeModels()) {
            System.out.println(model.getRecipeID());
            System.out.println(model.getRecipeName());
        }
    }


    /**
     * @return A list of RecipeModels
     */
    public List<List<RecipeModel>> getTopRecipeforCategories() {
        List<List<RecipeModel>> categoryRecipeLists = new ArrayList<>();

        for (RecipeCategory recipeCategory : RecipeCategory.values()) {
            List<RecipeModel> recipeList = recipeRepository.getCategoryTopTen(recipeCategory.name());

            if (recipeList != null && !recipeList.isEmpty()) {
                categoryRecipeLists.add(recipeList);
            }
        }
        return categoryRecipeLists;
    }


    /**
     * @param currentLoggedInUser A UserModel of the currently logged in user
     * @return A list of RecipeModels associated with the UserModel
     */
    public List<List<RecipeModel>> getAllByCategory(UserModel currentLoggedInUser) {
        List<List<RecipeModel>> resultList = new ArrayList<>();
        for (RecipeCategory recipeCategory : RecipeCategory.values()) {
            List<RecipeModel> recipeModelList = recipeRepository.findRecipeModelsByRecipeCategoryAndUserModel(
                    recipeCategory,
                    currentLoggedInUser);

            if (recipeModelList != null && !recipeModelList.isEmpty()) {
                resultList.add(recipeModelList);
            }
        }
        return resultList;
    }

    /**
     * @param userModel A UserModel object
     * @return A list of RecipeModels associated with the UserModel
     */
    public List<RecipeModel> getTopTwoForUser(UserModel userModel) {
        return userModel.getRecipeModels().stream().sorted((recipeModel, t1) -> t1.getLikes() - recipeModel.getLikes())
                .limit(2).collect(Collectors.toList());
    }

    /**
     * @param currentLoggedInUser A UserModel of the currently logged in user
     * @return An integer representing the total number of likes the UserModel has received
     */
    public int totalLikesForUser(UserModel currentLoggedInUser) {
        int totalLikes = 0;
        for (RecipeModel recipeModel : currentLoggedInUser.getRecipeModels()) {
            totalLikes += recipeModel.getLikes();
        }
        return totalLikes;
    }
}
