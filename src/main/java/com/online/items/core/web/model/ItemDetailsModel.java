/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, November 2021
 */

package com.online.items.core.web.model;

import com.online.items.core.domain.Item;

public class ItemDetailsModel extends AbstractModel {

    private String      description;
    private int         viewCount;
    private RatingModel rating;

    public ItemDetailsModel() {
    }

    public ItemDetailsModel( Item item ) {
        this.description = item.getDescription();
        this.viewCount = item.getViewCount();
        this.rating = new RatingModel( item.getRating() );
    }

    public ItemDetailsModel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public RatingModel getRating() {
        return rating;
    }

    public void setRating(RatingModel rating) {
        this.rating = rating;
    }
}
