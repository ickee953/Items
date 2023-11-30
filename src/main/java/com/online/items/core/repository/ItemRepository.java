/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, November 2021
 */


package com.online.items.core.repository;

import com.online.items.core.domain.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Repository interface to access {@link Item}s.
 *
 */

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    Set<Item> findByTitle(String title );
}
