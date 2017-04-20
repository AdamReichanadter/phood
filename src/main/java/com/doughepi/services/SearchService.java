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

import com.doughepi.models.RecipeModel;
import com.doughepi.models.SearchResults;
import com.doughepi.repositories.RecipeRepository;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pjdoughe on 3/23/17.
 */
@Service
public class SearchService {

    private final RecipeRepository recipeRepository;

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager entityManager;

    @Autowired
    public SearchService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    /**
     * Return a list of RecipeModel for a given search query string.
     *
     * @param query The string to match recipes with.
     * @return A SearchResults container that contains a list of results for a search.
     */
    @Transactional
    public SearchResults<RecipeModel> getSearchResults(String query) {

        // Start the timer.
        long start = System.currentTimeMillis();

        //We build a query for the database using the Hibernate Search library.
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(RecipeModel.class).get();

        // We can search for through a recipe's description, category, name, and the username of the recipe itself.
        org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword().fuzzy().onFields("recipeName",
                "recipeDescription", "categoryName", "userModel.userUsername").matching(query).createQuery();
        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, RecipeModel.class);
        List resultList = jpaQuery.getResultList();

        // Cast the list into another list.
        ArrayList<RecipeModel> recipeList = new ArrayList<>();
        for (Object o : resultList) {
            if (o instanceof RecipeModel) {
                recipeList.add(((RecipeModel) o));
            }
        }

        // Stop the timer, operations have completed.
        long end = System.currentTimeMillis();

        // Create and return the new SearchResults object.
        long total = end - start;
        return new SearchResults<>(query, recipeList, total, "ms");
    }
}
