package com.doughepi.controllers;

import com.doughepi.models.UserModel;
import com.doughepi.services.UserService;
import com.doughepi.validation.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.logging.Logger;

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
 * The <code>HelpController</code> is responsible for routing requests to the /register url to the register templates.
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
@RequestMapping(value = "/register")
@SessionAttributes("user")
public class RegistrationController {

    @Autowired
    private
    UserService userService;

    private final RegistrationValidator registrationValidator;
    private Logger logger = Logger.getLogger(getClass().getName());

    /**
     * @param registrationValidator Injects an instance of the RegistrationValidator by spring
     */
    @Autowired
    public RegistrationController(RegistrationValidator registrationValidator) {
        this.registrationValidator =
                registrationValidator;
    }

    /**
     * @return The location of the register-1 template in the /templates/registration directory.
     */
    @RequestMapping
    public String paramRedirect() {
        return "redirect:/register?page=1";
    }

    /**
     * @param model The model injected by Spring for each page.
     * @return The location of the register-1 template in the /templates/registration directory.
     */
    @RequestMapping(params = "page=1")
    public String initialPage(final Model model) {
        //Insert a blank user into the model for the registration page.
        UserModel userModel = new UserModel();
        model.addAttribute("user", userModel);
        return "/registration/register-1";
    }

    /**
     * @param userModel Injects an instance of UserModel by spring
     * @param bindingResult Injects an instance of BindingResult by spring
     * @return The location of the register-2 template in the /templates/registration directory.
     */
    @RequestMapping(params = "page=2")
    public String secondPage(
            final @ModelAttribute("user") UserModel userModel,
            final BindingResult bindingResult
    ) {
        //If the previous page has errors, return to the previous page.
        registrationValidator.validateRegistration(userModel, bindingResult);
        if (bindingResult.hasErrors()) {
            logger.warning(String.format("Validation failed for user: %s " +
                    "Failed fields include:", userModel));
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                logger.warning(String.format("Code: %s", fieldError.getCode()));
            }
            return "/registration/register-1";
        }

        return "/registration/register-2";
    }

    /**
     * @param userModel Injects an instance of UserModel by spring
     * @param bindingResult Injects an instance of BindingResult by spring
     * @param sessionStatus Injects an instance of SessionStatus by spring
     * @return The location of the register-3 template in the /templates/registration directory.
     */
    @RequestMapping(params = "page=3")
    public String thirdPage(
            final @ModelAttribute("user") UserModel userModel,
            final BindingResult bindingResult,
            final SessionStatus sessionStatus
    ) {
        //If the previous page has errors, return to the previous page.
        registrationValidator.validatePersonal(userModel, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/registration/register-2";
        }

        return "/registration/register-3";
    }

    /**
     * @param userModel Injects an instance of UserModel by spring
     * @param sessionStatus Injects an instance of SessionStats by spring
     * @return The location of the index template in the /templates directory.
     */
    @RequestMapping(params = "_confirm")
    public String processConfirm(
            final @ModelAttribute("user") UserModel userModel,
            final SessionStatus sessionStatus
    ) {
        //If the user clicks confirm, save the account and redirect to the root page.
        userService.save(userModel);
        sessionStatus.setComplete();
        return "index";
    }

    /**
     * @param sessionStatus Injects and instance os SessionStatus by spring
     * @return The location of the index template in the /templates directory.
     */
    @RequestMapping(params = "_cancel")
    public String processCancel(
            final SessionStatus sessionStatus
    ) {
        sessionStatus.setComplete();
        return "index";
    }
}
