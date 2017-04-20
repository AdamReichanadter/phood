package com.doughepi.services;

import com.doughepi.models.UserModel;

import java.util.UUID;

/**
 * Created by dough on 2017-02-06.
 */

/**
 * Michigan Technological University
 * CS3141: Team Software Project
 * <p>
 * Phood
 * <p>
 * A website for the management of recipes.
 * <p>
 * The <code>UserService</code> interface to help handle user related interactions
 *
 * @author Piper Dougherty
 * @author Adam Reichanadter
 * @author De'Shawn Presley
 * @author Tyler Schlomer
 * @author Daniel Morgan
 * @version 1.0.0-Alpha
 * @since 4/20/2016
 */
public interface UserService
{

    /**
     * @return The currently logged in UserModel
     */
    UserModel getCurrentLoggedInUser();

    /**
     * @param username String containing the username for the user you are looking for
     * @return A UserModel with the associated username
     */
    UserModel findByUsername(String username);

    /**
     * @param email String containing the email for the user you are looking for
     * @return A UserModel with the associated email
     */
    UserModel findByEmail(String email);

    /**
     * @param testAccountId UUID associated with the UserModel
     * @param testAccountUsername Username associated with the UserModel
     * @param testAccountPassword Password associated with the UserModel
     * @param testAccountEmail Email associated with the UserModel
     * @param testAccountFirstName First name associated with the UserModel
     * @param testAccountMiddleInitial Middle initial associated with the UserModel
     * @param testAccountLastName Last name associated with the UserModel
     * @return A new UserModel
     */
    UserModel createTestUser(
            UUID testAccountId,
            String testAccountUsername,
            String testAccountPassword,
            String testAccountEmail,
            String testAccountFirstName,
            String testAccountMiddleInitial,
            String testAccountLastName);

    /**
     * @param userModel UserModel you are trying to save
     * @return A UserModel
     */
    UserModel save(UserModel userModel);
}
