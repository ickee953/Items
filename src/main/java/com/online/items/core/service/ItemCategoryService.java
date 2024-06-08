/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, November 2021
 */

package com.online.items.core.service;

import com.online.items.core.domain.ItemCategory;
import com.online.items.core.repository.ItemCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    @Autowired
    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public List<ItemCategory> readAll(){
        return itemCategoryRepository.findAll();
    }

    public ItemCategory update( ItemCategory itemCategory ){
        return itemCategoryRepository.save( itemCategory );
    }

    /**
     * remove item category by id
     *
     * @param id of item category
     * @return false if item category not found, true if item category deleted
     */
    public boolean remove( String id ){

        Optional<ItemCategory> itemCategory = itemCategoryRepository.findById( id );

        if( !itemCategory.isPresent() ) return false;

        itemCategoryRepository.delete( itemCategory.get() );

        return true;

    }
}
