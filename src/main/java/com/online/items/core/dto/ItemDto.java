/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, November 2021
 */

package com.online.items.core.dto;

import com.online.items.core.domain.Item;

import java.math.BigDecimal;

public class ItemDto extends AbstractDto {

    private String              title;
    private String              titlePicUrl;
    private String              descriptionShort;
    private BigDecimal          price;
    private ItemCategoryDto category;
    private Integer             viewCount;
    private RatingDto rating;


    public ItemDto() {
    }

    public ItemDto(
            String id,
            String title,
            String descriptionShort,
            BigDecimal price,
            ItemCategoryDto category,
            Integer viewCount,
            RatingDto rating
    ) {
        this.id = id;
        this.title = title;
        this.descriptionShort = descriptionShort;
        this.price = price;
        this.category = category;
        this.viewCount = viewCount;
        this.rating = rating;
    }

    public ItemDto(Item item) {
        this.id                 = item.getId();
        this.title              = item.getTitle();
        this.titlePicUrl        = item.getTitlePicture();
        this.descriptionShort   = item.getDescriptionShort();
        this.price              = item.getPrice();
        this.category           = new ItemCategoryDto(item.getCategory());
        this.viewCount          = item.getViewCount();
        this.rating             = new RatingDto( item.getRating() );
    }

    public String getTitle() {
        return title;
    }

    public String getTitlePicUrl() {
        return titlePicUrl;
    }

    public void setTitlePicUrl(String titlePicUrl) {
        this.titlePicUrl = titlePicUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptionShort() {
        return descriptionShort;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ItemCategoryDto getCategory() {
        return category;
    }

    public void setCategory(ItemCategoryDto category) {
        this.category = category;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public RatingDto getRating() {
        return rating;
    }

    public void setRating(RatingDto rating) {
        this.rating = rating;
    }
}
