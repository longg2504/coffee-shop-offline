package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.dto.product.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndDeletedFalse(Long id);
    @Query("SELECT NEW com.cg.model.dto.product.ProductDTO (" +
            "pro.id, " +
            "pro.title, " +
            "pro.price, " +
            "pro.unit, " +
            "pro.category, " +
            "pro.productAvatar" +
            ") " +
            "FROM Product as pro " +
            "WHERE pro.deleted = false"
    )
    List<ProductDTO> findAllProductDTO();

    @Query("SELECT NEW com.cg.model.dto.product.ProductDTO (" +
            "pr.id, " +
            "pr.title, " +
            "pr.price, " +
            "pr.unit, " +
            "pr.category, " +
            "pr. productAvatar " +
            ") " +
            "From Product AS pr " +
            "WHERE pr.category.id = :categoryId")
    List<ProductDTO> findAllByCategoryLike(Long categoryId);

    @Query("SELECT NEW com.cg.model.dto.product.ProductDTO (" +
            "pro.id, " +
            "pro.title, " +
            "pro.price, " +
            "pro.unit, " +
            "pro.category, " +
            "pro.productAvatar" +
            ") " +
            "FROM Product as pro " +
            "WHERE pro.title like :keySearch"
    )
    List<ProductDTO> findProductByName(String keySearch);


    @Query("SELECT NEW com.cg.model.dto.product.ProductDTO (" +
            "pro.id, " +
            "pro.title, " +
            "pro.price, " +
            "pro.unit, " +
            "pro.category, " +
            "pro.productAvatar" +
            ") " +
            "FROM Product as pro " +
            "WHERE pro.title like :keySearch and pro.category.id = :categoryId"
    )
    List<ProductDTO> findAllByCategoryLikeAndAndTitleLike(@Param("categoryId") Long categoryId,@Param("keySearch") String keySearch);

    @Query("SELECT NEW com.cg.model.dto.product.ProductDTO ( " +
            "pro.id, " +
            "pro.title, " +
            "pro.price, " +
            "pro.unit, " +
            "pro.category, " +
            "pro.productAvatar " +
            ") " +
            "FROM Product as pro " +
            "WHERE pro.deleted = false " +
            "ORDER BY pro.id ASC"
    )
    Page<ProductDTO> findAllProductDTOPage(Pageable pageable);
}
