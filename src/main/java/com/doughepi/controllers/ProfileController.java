package com.doughepi.controllers;

import com.doughepi.models.UserModel;
import com.doughepi.services.RecipeService;
import com.doughepi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ajreicha on 2/18/17.
 */
@Controller
@RequestMapping(value = "/profile", method = RequestMethod.GET)
public class ProfileController {

    @Autowired
    private
    UserService userService;

    @Autowired
    private
    RecipeService recipeService;

    /**
     * @param model The model injected by Spring for each page.
     * @return The location of the template in the /templates directory.
     */
    @RequestMapping
    public String showProfile(Model model) {
        UserModel currentLoggedInUser = userService.getCurrentLoggedInUser();
        model.addAttribute("user", currentLoggedInUser);
        double totalLikesForUser = recipeService.totalLikesForUser(currentLoggedInUser);
        int numberOfRecipes = currentLoggedInUser.getRecipeModels().size();

        if (numberOfRecipes == 0) {
            model.addAttribute("average", 0);
        } else {
            model.addAttribute("average", totalLikesForUser / numberOfRecipes);
        }

        model.addAttribute("totalLikes", totalLikesForUser);



        model.addAttribute("username", currentLoggedInUser.getUserUsername());
        model.addAttribute("name", String.format("%s %s %s", currentLoggedInUser.getUserFirstName(),
                currentLoggedInUser.getUserMiddleInitial(),
                currentLoggedInUser.getUserLastName()));
        model.addAttribute("profileCategories", recipeService.getAllByCategory(currentLoggedInUser));

        return "profile";
    }
}
