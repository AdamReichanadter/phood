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

package com.doughepi.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Store;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Michigan Technological University
 * CS3141: Team Software Project
 * <p>
 * Phood
 * <p>
 * A website for the management of recipes.
 * <p>
 * The <code>UserModel</code> represents a registered user.
 *
 * @author Piper Dougherty
 * @author Adam Reichanadter
 * @author De'Shawn Presley
 * @author Tyler Schlomer
 * @author Daniel Morgan
 * @version 1.0.0-Alpha
 * @since 4/20/2016
 */
@Entity
@Table(name = "\"user\"")
public class UserModel {

    /**
     * The PRIMARY KEY of this user.
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_id", length = 16)
    private UUID userID;

    /**
     * The username of the user.
     */
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "user_username")
    private String userUsername;

    /**
     * The password of the user.
     */
    @Column(name = "user_password")
    private String userPassword;

    /**
     * The password of the user entered for the second time for
     * validation. Is only used during the registration steps.
     */
    @Transient
    private String userConfirmationPassword;

    /**
     * The email of the user.
     */
    @Column(name = "user_email")
    private String userEmail;

    /**
     * The first name of the user.
     */
    @Column(name = "user_firstName")
    private String userFirstName;

    /**
     * The middle initial of the user.
     */
    @Column(name = "user_middle_initial")
    private String userMiddleInitial;

    /**
     * The last name of the user.
     */
    @Column(name = "user_lastname")
    private String userLastName;

    /**
     * Simple UUID container used for testing.
     */
    @Column(name = "other", length = 16)
    private UUID other;

    /**
     * The set of roles this user has assigned.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles", joinColumns = @JoinColumn(name = "user_id", columnDefinition = "BINARY(16)"),
            inverseJoinColumns = @JoinColumn(name = "role_id", columnDefinition = "BINARY(16)"))
    private Set<RoleModel> roleSet;

    /**
     * The list of recipes this user owns.
     */
    // Foreign key linking recipes to the user
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userModel", cascade = CascadeType.ALL)
    private List<RecipeModel> recipeModels;

    /*
     * Getters and setters.
     */

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserConfirmationPassword() {
        return userConfirmationPassword;
    }

    public void setUserConfirmationPassword(String userConfirmationPassword) {
        this.userConfirmationPassword = userConfirmationPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserMiddleInitial() {
        return userMiddleInitial;
    }

    public void setUserMiddleInitial(String userMiddleInitial) {
        this.userMiddleInitial = userMiddleInitial;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public Set<RoleModel> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<RoleModel> roleSet) {
        this.roleSet = roleSet;
    }

    public UUID getOther() {
        return other;
    }

    public void setOther(UUID other) {
        this.other = other;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UserModel && ((UserModel) obj).getUserID().equals(getUserID());
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public List<RecipeModel> getRecipeModels() {
        return recipeModels;
    }

    public void setRecipeModels(List<RecipeModel> recipeModels) {
        this.recipeModels = recipeModels;
    }

    @Override
    public String toString() {
        return String.format("[UUID: %s, Username: %s, Email: %s, FirstName: %s, LastName: %s]",
                getUserID(), getUserUsername(), getUserEmail(), getUserFirstName(), getUserLastName());
    }
}
