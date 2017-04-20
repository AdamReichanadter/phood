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

import javax.persistence.*;
import java.util.UUID;

/**
 * Michigan Technological University
 * CS3141: Team Software Project
 * <p>
 * Phood
 * <p>
 * A website for the management of recipes.
 * <p>
 * The <code>ImageModel</code> represents an image in the database.
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
@Table(name = "image")
public class ImageModel {

    /**
     * The unique id of the image, and is the PRIMARY KEY for the database.
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "image_id", length = 16)
    private UUID imageID;


    /**
     * The parent recipeModel for this image.
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    private RecipeModel recipeModel;

    /**
     * The byte array representing the image itself.
     */
    @Column(name = "image_data")
    private Byte[] imageData;


    /**
     * Get the PRIMARY KEY for this imageModel.
     *
     * @return The PRIMARY KEY for this imageModel.
     */
    public UUID getImageID() {
        return imageID;
    }

    /**
     * Give this imageModel a new PRIMARY KEY. You probably shouldn't
     * use this in production. Consider making a new image.
     *
     * @param imageID The new PRIMARY KEY for the imageModel.
     */
    public void setImageID(UUID imageID) {
        this.imageID = imageID;
    }


    /**
     * Get the parent recipeModel for this imageModel.
     *
     * @return The parent recipeModel for this imageModel.
     */
    public RecipeModel getRecipeModel() {
        return recipeModel;
    }

    /**
     * Set the parent recipeModel for this imageModel.
     *
     * @param recipeModel The new recipeModel that should contain this image.
     */
    public void setRecipeModel(RecipeModel recipeModel) {
        this.recipeModel = recipeModel;
    }


    /**
     * Get the imageData for this imageModel.
     *
     * @return The byte array of data that represents this imageModel.
     */
    public Byte[] getImageData() {
        return imageData;
    }

    /**
     * Set the imageData for this imageModel.
     *
     * @param imageData The byte array of data for the new image this imageModel should represent.
     */
    public void setImageData(Byte[] imageData) {
        this.imageData = imageData;
    }
}
