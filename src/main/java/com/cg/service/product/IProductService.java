package com.cg.service.product;

import com.cg.model.Category;
import com.cg.model.Product;
import com.cg.model.dto.product.ProductCreReqDTO;
import com.cg.model.dto.product.ProductDTO;
import com.cg.model.dto.product.ProductUpReqDTO;
import com.cg.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProductService extends IGeneralService<Product,Long> {
    Product createProduct(ProductCreReqDTO productCreReqDTO, Category category);

    List<ProductDTO> findAllProductDTO();

    Product update(Long productId,ProductUpReqDTO productUpReqDTO, Category category);

    void deleteByIdTrue(Product product);

    List<ProductDTO> findAllByCategoryLike(Long categoryId);

    List<ProductDTO> findProductByName(String keySearch);

    List<ProductDTO> findAllByCategoryLikeAndAndTitleLike(Long categoryId,String keySearch);

    Page<ProductDTO> findAllProductDTOPage(Pageable pageable);

    Optional<Product> findByIdAndDeletedFalse(Long id);
}
