package com.doughepi.repositories;

import com.doughepi.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by dough on 1/30/2017.
 */

/**
 * Michigan Technological University
 * CS3141: Team Software Project
 * <p>
 * Phood
 * <p>
 * A website for the management of recipes.
 * <p>
 * The <code>UserRepository</code> Represents an interface for the Users between the database and everything else
 *
 * @author Piper Dougherty
 * @author Adam Reichanadter
 * @author De'Shawn Presley
 * @author Tyler Schlomer
 * @author Daniel Morgan
 * @version 1.0.0-Alpha
 * @since 4/20/2016
 */
public interface UserRepository extends JpaRepository<UserModel, UUID>
{
    UserModel findByUserUsername(String userUsername);
    UserModel findByUserEmail(String userEmail);
}
