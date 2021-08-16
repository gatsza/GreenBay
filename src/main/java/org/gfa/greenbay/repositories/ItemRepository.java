package org.gfa.greenbay.repositories;

import org.gfa.greenbay.models.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {

}
