/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, November 2021
 */

package com.online.items.core.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document( collection = "category")
public class ItemCategory extends AbstractDocument {
    @NotNull(message = "is required")
    @NotEmpty
    @Indexed( unique = true )
    private String name;

    private String      titlePicture;

    public ItemCategory(){
    }

    public ItemCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTitlePicture() {
        return titlePicture;
    }

    public void setTitlePicture(String titlePicture) {
        this.titlePicture = titlePicture;
    }
}
