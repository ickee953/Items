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

package test.online.items.web.request;

import com.online.items.core.domain.Item;
import com.online.items.core.repository.ItemRepository;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;

public class ItemWebHandlerIntegrationTest extends AbstractWebHandlerIntegrationTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void whenLogRequest_thenOK() {
        given().log().all()
                .when().get("/product/list")
                .then().statusCode(200);
    }

    @Test
    public void whenLogResponse_thenOK() {
        when().get("/product/list")
                .then().log().body().statusCode(200);
    }

    @Test
    public void whenLogOnlyIfValidationFailed_thenSuccess() {
        when().get("/product/list")
                .then().log().ifValidationFails().statusCode(200);

        given().log().ifValidationFails()
                .when().get("/product/list")
                .then().statusCode(200);
    }

    @Test
    public void findProductByIdRestTest() {
        Set<Item> items = itemRepository.findByTitle("iPad");

        Item iPad = items.stream().findFirst().orElse(null);

        assertNotNull( iPad );

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", iPad.getId())
                .when()
                .get("/product/{id}")
                .then()
                .statusCode(200)
                .body("tittle", equalTo("iPad"))
                .body("price", equalTo(499));
    }

}
