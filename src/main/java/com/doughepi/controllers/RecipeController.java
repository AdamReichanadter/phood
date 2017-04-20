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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
 * The <code>HelpController</code> is responsible for routing requests to the /help url to the help template.
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
     * @param userService Injects userService
     * @param userRepository Injects userRepository
     * @param recipeService Injects recipeService
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
     * @param model The model injected by Spring for each page.
     * @param recipeID // The UUID of the recipe
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
     * @param recipeModel
     * @return
     */
    @RequestMapping(value = "/new", params = {"_submit"}, method = RequestMethod.POST)
    public String jsonInsertion(@RequestBody String recipeModel) {
        recipeService.createRecipe(recipeModel);
        return "index";
    }

    @RequestMapping(value = "/like", params = {"recipeID"}, method = RequestMethod.POST)
    public String likeRecipe(@RequestParam("recipeID") UUID recipeID) {
        RecipeModel recipe = recipeRepository.findOne(recipeID);
        recipe.setLikes(recipe.getLikes() + 1);
        recipeRepository.save(recipe);
        return "success";
    }

    @RequestMapping(value = "/dislike", params = {"recipeID"}, method = RequestMethod.POST)
    public String dislikeRecipe(@RequestParam("recipeID") UUID recipeID) {
        RecipeModel recipe = recipeRepository.findOne(recipeID);
        recipe.setLikes(recipe.getLikes() - 1);
        recipeRepository.save(recipe);
        return "success";
    }

}
