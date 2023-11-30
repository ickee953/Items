/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, November 2021
 */

package com.online.items.core.web.model;

import com.online.items.core.domain.ItemCategory;

public class ItemCategoryModel extends AbstractModel {
    private String name;

    public ItemCategoryModel() {
    }

    public ItemCategoryModel(String id, String name) {
        this.id     = id;
        this.name   = name;
    }

    public ItemCategoryModel(ItemCategory category) {
        this( category.getId(), category.getName() );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
