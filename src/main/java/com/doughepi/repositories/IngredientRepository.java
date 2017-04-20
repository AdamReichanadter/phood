package com.doughepi.repositories;

import com.doughepi.models.IngredientModel;
import org.springframework.data.jpa.repository.JpaRepository;

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
 * The <code>IngredientRepository</code> Represents an interface for the ingredients between the database and everything else
 *
 * @author Piper Dougherty
 * @author Adam Reichanadter
 * @author De'Shawn Presley
 * @author Tyler Schlomer
 * @author Daniel Morgan
 * @version 1.0.0-Alpha
 * @since 4/20/2016
 */
public interface IngredientRepository extends JpaRepository<IngredientModel, UUID>{
}
