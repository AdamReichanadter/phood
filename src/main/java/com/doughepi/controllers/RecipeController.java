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

package com.doughepi.controllers;

import com.doughepi.models.RecipeCategory;
import com.doughepi.models.RecipeModel;
import com.doughepi.repositories.RecipeRepository;
import com.doughepi.repositories.UserRepository;
import com.doughepi.services.RecipeService;
import com.doughepi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by pjdoughe on 2/28/17.
 */

/**
 * Michigan Technological University
 * CS3141: Team Software Project
 * <p>
 * Phood
 * <p>
 * A website for the management of recipes.
 * <p>
 * The <code>RecipeController</code> is responsible for routing requests to the /recipe url to the recipe template.
 *
 * @author Piper Dougherty
 * @author Adam Reichanadter
 * @author De'Shawn Presley
 * @author Tyler Schlomer
 * @author Daniel Morgan
 * @version 1.0.0-Alpha
 * @since 4/20/2016
 */
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final RecipeService recipeService;

    /**
     * @param recipeRepository Injects recipeRepository
     * @param userService      Injects userService
     * @param userRepository   Injects userRepository
     * @param recipeService    Injects recipeService
     */
    @Autowired
    public RecipeController(RecipeRepository recipeRepository, UserService userService, UserRepository userRepository, RecipeService recipeService) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.recipeService = recipeService;
    }

    /**
     * @param model The model injected by Spring for each page.
     * @return The location of the create-recipe template in the /templates directory.
     */
    @RequestMapping("/new")
    public String showRecipeForm(Model model) {
        model.addAttribute("categories", RecipeCategory.values());
        return "create-recipe";
    }

    /**
     * @param model    The model injected by Spring for each page.
     * @param recipeID The UUID of the recipe
     * @return The location of the recipe template in the /templates directory.
     */
    @RequestMapping(params = {"recipeID"})
    public String showRecipePage(Model model, @RequestParam("recipeID") UUID recipeID) {

        RecipeModel recipeModel = recipeRepository.findOne(recipeID);
        model.addAttribute("recipe", recipeModel);
        List<RecipeModel> topTwoForUser = recipeService.getTopTwoForUser(recipeModel.getUserModel());
        model.addAttribute("topTwo", topTwoForUser);
        return "recipe";
    }

    /**
     * @param recipeModel The incoming json payload for a recipe object
     * @return The location of the index template in the /templates directory.
     */
    @RequestMapping(value = "/new", params = {"_submit"}, method = RequestMethod.POST)
    public String jsonInsertion(@RequestBody String recipeModel) {
        recipeService.createRecipe(recipeModel);
        return "index";
    }

    /**
     * @param recipeID The UUID associated with the recipe
     * @return Returns success if the operation succeeded
     */
    @RequestMapping(value = "/like", params = {"recipeID"}, method = RequestMethod.POST)
    public @ResponseBody
    String likeRecipe(@RequestParam("recipeID") UUID recipeID) {
        RecipeModel recipe = recipeRepository.findOne(recipeID);
        recipe.setLikes(recipe.getLikes() + 1);
        recipeRepository.save(recipe);
        return "success";
    }

    /**
     * @param recipeID The UUID associated with the recipe
     * @return Returns success if the operation succeeded
     */
    @RequestMapping(value = "/dislike", params = {"recipeID"}, method = RequestMethod.POST)
    public @ResponseBody
    String dislikeRecipe(@RequestParam("recipeID") UUID recipeID) {
        RecipeModel recipe = recipeRepository.findOne(recipeID);
        recipe.setLikes(recipe.getLikes() - 1);
        recipeRepository.save(recipe);
        return "success";
    }
}
