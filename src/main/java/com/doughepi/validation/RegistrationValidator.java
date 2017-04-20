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

package com.doughepi.validation;

import com.doughepi.models.UserModel;
import com.doughepi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Michigan Technological University
 * CS3141: Team Software Project
 * <p>
 * Phood
 * <p>
 * A website for the management of recipes.
 * <p>
 * The <code>RegistrationValidator</code> Validates user input during registration
 *
 * @author Piper Dougherty
 * @author Adam Reichanadter
 * @author De'Shawn Presley
 * @author Tyler Schlomer
 * @author Daniel Morgan
 * @version 1.0.0-Alpha
 * @since 4/20/2016
 */
@Component
public class RegistrationValidator implements Validator
{

    @Value("${validation}")
    private boolean validationEnabled;

    @Autowired
    private UserService userService;

    private PageEnum pageEnum;

    /**
     * @param aClass The class validation will be attempted on
     * @return returns true if its same as the UserModel class
     */
    @Override
    public boolean supports(Class<?> aClass)
    {
        return UserModel.class.equals(aClass);
    }

    /**
     * @param o Object the is being validated
     * @param errors The error class in the spring model that contains all the form fields to validate
     */
    @Override
    public void validate(Object o, Errors errors)
    {
        if (!validationEnabled)
        {
            return;
        }

        UserModel userModel = ((UserModel) o);

        switch (pageEnum)
        {
            case REGISTRATION_PAGE:
                //Validation logic for the first page of the registration form.

                //Validate for empty fields.
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userUsername", "Registration.emptyUsername");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userEmail", "Registration.emptyEmail");
                ValidationUtils.rejectIfEmptyOrWhitespace(
                        errors,
                        "userPassword",
                        "Registration.password.emptyPassword");
                ValidationUtils.rejectIfEmptyOrWhitespace(
                        errors,
                        "userConfirmationPassword",
                        "Registration.emptyConfirmationPassword");

                //Validate for password that doesn't equal confirmation password.
                if (!userModel.getUserConfirmationPassword().equals(userModel.getUserPassword()))
                {
                    errors.rejectValue("userConfirmationPassword", "Registration.passwordMatch");
                }

                //Validate for password of length less than 8 or greater than 24.
                int passwordLength = userModel.getUserPassword().length();
                if (passwordLength > 24)
                {
                    errors.rejectValue("userPassword", "Registration.password.tooLong");
                } else if (passwordLength < 8)
                {
                    errors.rejectValue("userPassword", "Registration.password.tooShort");
                }

                //Validate for username of length less than 8 or greater than 24.
                int usernameLength = userModel.getUserUsername().length();
                if (usernameLength > 24)
                {
                    errors.rejectValue("userUsername", "Registration.username.tooLong");
                } else if (usernameLength < 8)
                {
                    errors.rejectValue("userUsername", "Registration.username.tooShort");
                }

                //Validate for username or email already in use.
                String username = userModel.getUserUsername();
                String email = userModel.getUserEmail();
                if (userService.findByUsername(username) != null)
                {
                    errors.rejectValue("userUsername", "Registration.usernameTaken");
                }

                if (userService.findByEmail(email) != null)
                {
                    errors.rejectValue("userEmail", "Registration.emailTaken");
                }

                break;
            case PERSONAL_DETAILS_PAGE:
                //Validation logic for the second page of the registration form.
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userFirstName", "Registration.emptyFirstName");
                ValidationUtils.rejectIfEmptyOrWhitespace(
                        errors,
                        "userMiddleInitial",
                        "Registration.emptyMiddleInitial");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userLastName", "Registration.emptyLastName");
                break;
        }
    }

    /**
     * @param userModel the UserModel that is being validated
     * @param errors The error class in the spring model that contains all the form fields to validate
     */
    public void validateRegistration(UserModel userModel, Errors errors)
    {
        setPage(PageEnum.REGISTRATION_PAGE);
        validate(userModel, errors);
    }

    /**
     * @param pageEnum Keeps track of what registration page you're on
     */
    private void setPage(PageEnum pageEnum)
    {
        this.pageEnum = pageEnum;
    }

    /**
     * @param userModel the UserModel that is being validated
     * @param errors The error class in the spring model that contains all the form fields to validate
     */
    public void validatePersonal(UserModel userModel, Errors errors)
    {
        setPage(PageEnum.PERSONAL_DETAILS_PAGE);
        validate(userModel, errors);
    }

    private enum PageEnum
{
    REGISTRATION_PAGE, PERSONAL_DETAILS_PAGE
}
}
