/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, November 2021
 */

package com.online.items.core.dto;

import com.online.items.core.domain.Item;

public class ItemDetailsDto extends AbstractDto {

    private String      description;
    private int         viewCount;
    private RatingDto rating;

    public ItemDetailsDto() {
    }

    public ItemDetailsDto(Item item ) {
        this.description = item.getDescription();
        this.viewCount = item.getViewCount();
        this.rating = new RatingDto( item.getRating() );
    }

    public ItemDetailsDto(String description) {
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

    public RatingDto getRating() {
        return rating;
    }

    public void setRating(RatingDto rating) {
        this.rating = rating;
    }
}
