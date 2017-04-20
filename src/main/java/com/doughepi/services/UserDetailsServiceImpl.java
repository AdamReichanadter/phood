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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Michigan Technological University
 * CS3141: Team Software Project
 * <p>
 * Phood
 * <p>
 * A website for the management of recipes.
 * <p>
 * The <code>UserDetailsServiceImpl</code> allows for Spring Security to find a user for login.
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
public class UserDetailsServiceImpl implements UserDetailsService
{
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {this.userRepository = userRepository;}

    /**
     * Find a user in the database by username.
     *
     * @param username The username to search for a user with.
     * @return The user object found.
     * @throws UsernameNotFoundException If the username was not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUserUsername(username);
        if (userModel == null) {
            throw new UsernameNotFoundException("The supplied username does not exist in the database.");
        }

        // Link a user's roles to the user.
        Set<GrantedAuthority> grantedAuthorities = userModel.getRoleSet().stream().map(roleModel -> new
                SimpleGrantedAuthority(roleModel.getRoleName())).collect(Collectors.toSet());
        return new User(userModel.getUserUsername(), userModel.getUserPassword(), grantedAuthorities);
    }
}
