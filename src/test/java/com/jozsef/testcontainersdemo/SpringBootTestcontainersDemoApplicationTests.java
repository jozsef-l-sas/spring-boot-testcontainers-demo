package com.jozsef.testcontainersdemo;

import com.jozsef.testcontainersdemo.entity.Item;
import com.jozsef.testcontainersdemo.repository.ItemRepository;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class SpringBootTestcontainersDemoApplicationTests {

    @ClassRule
    @Container
    private static TestDBContainer container = TestDBContainer.getInstance();

    @Autowired
    private ItemRepository itemRepository;

    @DynamicPropertySource
    private static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @Test
    void testSaveItem() {
        List<Item> existingItems = (List<Item>) itemRepository.findAll();
        assertThat(existingItems).isEmpty();

        Item item = Item.builder()
                        .name("whatever")
                        .type("whatever")
                        .build();
        Item savedItem = itemRepository.save(item);

        assertThat(savedItem.getId()).isNotNull();

        Optional<Item> itemOptional = itemRepository.findById(savedItem.getId());
        assertThat(itemOptional).isPresent();
        assertThat(itemOptional.get()).isEqualTo(savedItem);
    }

}
