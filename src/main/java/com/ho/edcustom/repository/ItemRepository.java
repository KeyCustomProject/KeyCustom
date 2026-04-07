package com.ho.edcustom.repository;

import com.ho.edcustom.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findItemByEmail(String email);
}
