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

package test.online.items;

import com.online.items.core.domain.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * Base class for integration tests adding some sample data through the MongoDB Java driver.
 * 
 * @author
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring-dispatcher-servlet.xml",
		"file:src/main/webapp/WEB-INF/spring-data-configuration.xml"
})
public abstract class AbstractIntegrationTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Before
	public void setUp() {

		//Product categories
		mongoTemplate.dropCollection("category");
		ItemCategory itemCategory1 = new ItemCategory("category 1");
		ItemCategory itemCategory2 = new ItemCategory("category 2");

		mongoTemplate.insert( itemCategory1, "category" );
		mongoTemplate.insert( itemCategory2, "category" );

		// Products
		mongoTemplate.dropCollection("item");

		Item iPad 		= new Item(
								"iPad",
									new BigDecimal(499.0),
						"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
								"eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad " +
								"minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea " +
								"commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit " +
								"esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat " +
								"non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n",
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
								"eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad");
		iPad.setCategory( itemCategory1 );

		Item macBook 	= new Item(
							"MacBook Pro",
							new BigDecimal(1299.0),
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
						"eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad " +
						"minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea " +
						"commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit " +
						"esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat " +
						"non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
						"eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad");
		macBook.setCategory( itemCategory1 );

		Item dock		= new Item( "Dock",
									new BigDecimal(49.0),
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
						"eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad " +
						"minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea " +
						"commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit " +
						"esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat " +
						"non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
						"eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad");
		dock.setCategory( itemCategory1 );

		mongoTemplate.insert(iPad, "item");
		mongoTemplate.insert(macBook, "item");
		mongoTemplate.insert(dock, "item");

		mongoTemplate.insert(new Item("iPad", new BigDecimal(499.0), itemCategory2), "item");
		mongoTemplate.insert(new Item( "Dock", new BigDecimal(49.0), itemCategory2 ), "item");
		mongoTemplate.insert(new Item("iPad", new BigDecimal(499.0), itemCategory2), "item");
		mongoTemplate.insert(new Item( "Dock", new BigDecimal(49.0), itemCategory2 ), "item");
		mongoTemplate.insert(new Item( "Dock", new BigDecimal(49.0), itemCategory2 ), "item");
		mongoTemplate.insert(new Item("iPad", new BigDecimal(499.0), itemCategory2), "item");
	}
}
