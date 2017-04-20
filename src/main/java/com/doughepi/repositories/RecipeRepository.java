package com.doughepi.repositories;

import com.doughepi.models.RecipeCategory;
import com.doughepi.models.RecipeModel;
import com.doughepi.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * Created by ajreicha on 2/28/17.
 */

/**
 * Michigan Technological University
 * CS3141: Team Software Project
 * <p>
 * Phood
 * <p>
 * A website for the management of recipes.
 * <p>
 * The <code>RecipeRepository</code> Represents an interface for the Recipe between the database and everything else
 *
 * @author Piper Dougherty
 * @author Adam Reichanadter
 * @author De'Shawn Presley
 * @author Tyler Schlomer
 * @author Daniel Morgan
 * @version 1.0.0-Alpha
 * @since 4/20/2016
 */
public interface RecipeRepository extends JpaRepository<RecipeModel, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM recipe where recipe_category = ?1 order by likes DESC limit 5")
    List<RecipeModel> getCategoryTopTen(String categoryName);

    List<RecipeModel> findRecipeModelsByRecipeCategoryAndUserModel(RecipeCategory recipeCategory, UserModel userModel);

}
