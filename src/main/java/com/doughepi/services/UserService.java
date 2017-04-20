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

import com.doughepi.models.UserModel;

import java.util.UUID;

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
