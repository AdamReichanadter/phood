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

import com.doughepi.models.UserModel;
import com.doughepi.services.RecipeService;
import com.doughepi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Michigan Technological University
 * CS3141: Team Software Project
 * <p>
 * Phood
 * <p>
 * A website for the management of recipes.
 * <p>
 * The <code>ProfileController</code> is responsible for routing requests to the /profile url to the profile template.
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
@RequestMapping(value = "/profile", method = RequestMethod.GET)
public class ProfileController {

    private final
    UserService userService;

    private final
    RecipeService recipeService;

    @Autowired
    public ProfileController(UserService userService, RecipeService recipeService) {
        this.userService = userService;
        this.recipeService = recipeService;
    }

    /**
     * @param model The model injected by Spring for each page.
     * @return The location of the profile template in the /templates directory.
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
