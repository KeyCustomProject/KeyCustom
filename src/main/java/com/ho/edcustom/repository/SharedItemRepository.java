package com.ho.edcustom.repository;

import com.ho.edcustom.entity.Item;
import com.ho.edcustom.entity.SharedItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SharedItemRepository extends JpaRepository<SharedItem,Long> {
    List<SharedItem> findSharedItemByEmail(String email);
}
