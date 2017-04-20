package com.doughepi.controllers;

import com.doughepi.models.SearchResults;
import com.doughepi.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by pjdoughe on 3/23/17.
 */

/**
 * Michigan Technological University
 * CS3141: Team Software Project
 * <p>
 * Phood
 * <p>
 * A website for the management of recipes.
 * <p>
 * The <code>HelpController</code> is responsible for routing requests to the /search url to the search template.
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
public class SearchController {

    @Autowired
    private
    SearchService searchService;

    /**
     * @param model The model injected by Spring for each page.
     * @param query Injects a search query in the form of a string
     * @return The location of the search template in the /templates directory.
     */
    @GetMapping(value = "/search", params = {"query"})
    public String showSearch(Model model, @RequestParam("query") String query) {
        SearchResults searchResults = searchService.getSearchResults(query);
        model.addAttribute("query", searchResults.getQuery());
        model.addAttribute("count", searchResults.getCount());
        model.addAttribute("duration", searchResults.getDuration());
        model.addAttribute("unit", searchResults.getUnit());
        model.addAttribute("results", searchResults.getResultList());
        return "search";
    }
}
