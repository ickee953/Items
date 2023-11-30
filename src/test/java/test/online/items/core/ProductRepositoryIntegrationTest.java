/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package test.online.items.core;

import java.math.BigDecimal;
import java.util.Set;

import com.online.items.core.domain.Item;
import com.online.items.core.repository.ItemRepository;
import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import test.online.items.AbstractIntegrationTest;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration tests for {@link ItemRepository}.
 * 
 * @author
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductRepositoryIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	ItemRepository itemRepository;

	@Test
	public void test1_createProduct() {

		Item product = new Item("Camera bag", new BigDecimal(49.99));
		product = itemRepository.save(product);

		assertNotNull( product );
		assertThat( product.getTitle(), Matchers.is("Camera bag") );
		assertThat( product.getPrice(), Matchers.is(new BigDecimal(49.99)) );
	}


	@Test
	public void test2_findProductByTittle() {

		Set<Item> items = itemRepository.findByTitle("Dock");

		Item product = items.stream().findFirst().orElse(null);

		assertNotNull( product );
		assertThat( product.getTitle(), Matchers.is("Dock") );
		assertThat( product.getPrice(), Matchers.is(new BigDecimal(49.0)) );
	}
}
