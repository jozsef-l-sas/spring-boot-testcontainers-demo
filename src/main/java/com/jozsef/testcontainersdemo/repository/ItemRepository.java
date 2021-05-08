package com.jozsef.testcontainersdemo.repository;

import com.jozsef.testcontainersdemo.entity.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ItemRepository extends CrudRepository<Item, UUID> {
}
