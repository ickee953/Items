/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, November 2021
 */

package com.online.items.core.service;

import com.online.items.core.domain.Item;
import com.online.items.core.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Business logic for {@link Item}s.
 *
 */

@Service
public class ItemService {

    private final ItemRepository repository;

    @Autowired
    public ItemService( ItemRepository repository ) {
        this.repository = repository;
    }

    public Item update( Item item ) { return repository.save( item ); }

    public Optional<Item> readById( String id ){
        return repository.findById( id );
    }

    public List<Item> readAll(){
        return repository.findAll();
    }

    public boolean remove( String id ){

        Optional<Item> item = repository.findById( id );

        if( !item.isPresent() ) return false;

        repository.delete( item.get() );

        return true;
    }

    public Item incViewCount( Item item ){

        Integer viewCount = item.getViewCount();

        if( viewCount == null ) viewCount = 0;

        viewCount++;
        item.setViewCount( viewCount );

        return repository.save( item );
    }

}
