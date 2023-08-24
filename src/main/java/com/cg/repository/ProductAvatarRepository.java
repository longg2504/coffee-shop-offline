package com.cg.repository;

import com.cg.model.ProductAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAvatarRepository extends JpaRepository<ProductAvatar,Long> {
}
