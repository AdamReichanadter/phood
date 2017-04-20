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
import com.doughepi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.UUID;

/**
 * Michigan Technological University
 * CS3141: Team Software Project
 * <p>
 * Phood
 * <p>
 * A website for the management of recipes.
 * <p>
 * The <code>UserServiceImpl</code> is an implementation of the UserService for operations on User objects.
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
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * Get the current logged in user.
     *
     * @return The current logged in user, or null if there is no logged in user.
     */
    @Override
    public UserModel getCurrentLoggedInUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(name);
        return findByUsername(name);
    }

    /**
     * Use the userRepository to find a user by their username.
     *
     * @param username The username to obtain a user object for.
     * @return The user object found, or null if the user doesn't exist.
     */
    @Override
    public UserModel findByUsername(String username) {
        return userRepository.findByUserUsername(username);
    }

    /**
     * Use the userRepository to find a user by their email.
     *
     * @param email The email address to obtain a user object for.
     * @return The user object found, or null if the user doesn't exist.
     */
    @Override
    public UserModel findByEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

    /**
     * Create a test user.
     *
     * @param testAccountId            The PRIMARY KEY of the user.
     * @param testAccountUsername      The username of the test user.
     * @param testAccountPassword      The password of the test user.
     * @param testAccountEmail         The email of the test user.
     * @param testAccountFirstName     The first name of the test user.
     * @param testAccountMiddleInitial The middle initial of the test user.
     * @param testAccountLastName      The last name of the test user.
     * @return The newly created test user.
     */
    @Override
    public UserModel createTestUser(
            UUID testAccountId,
            String testAccountUsername,
            String testAccountPassword,
            String testAccountEmail,
            String testAccountFirstName,
            String testAccountMiddleInitial,
            String testAccountLastName) {
        UserModel userModel = new UserModel();
        userModel.setUserID(testAccountId);
        userModel.setUserUsername(testAccountUsername);
        userModel.setUserPassword(testAccountPassword);
        userModel.setUserEmail(testAccountEmail);
        userModel.setUserFirstName(testAccountFirstName);
        userModel.setUserMiddleInitial(testAccountMiddleInitial);
        userModel.setUserLastName(testAccountLastName);
        return userModel;
    }

    /**
     * Save the user into the database.
     *
     * @param userModel The userModel to save.
     * @return The newly saved test user.
     */
    @Override
    public UserModel save(UserModel userModel) {

        //Encrypt the password.
        userModel.setUserPassword(bCryptPasswordEncoder.encode(userModel.getUserPassword()));

        //An empty role set.
        userModel.setRoleSet(new HashSet<>());

        //Save the user.
        return userRepository.save(userModel);
    }
}
