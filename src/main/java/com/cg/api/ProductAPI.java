package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.model.Category;
import com.cg.model.Product;
import com.cg.model.dto.product.*;
import com.cg.service.category.ICategoryService;
import com.cg.service.product.IProductService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class    ProductAPI {
    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ValidateUtils validateUtils;

    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public ResponseEntity<?> showProducts(){

        List<ProductDTO> productDTOS = productService.findAllProductDTO();
        return new ResponseEntity<>(productDTOS,HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> findByIdProduct(@PathVariable("productId") String productIdStr){

        if (!validateUtils.isNumberValid(productIdStr)) {
            throw new DataInputException("Mã sản phẩm không hợp lệ");
        }
        Long productId = Long.valueOf(productIdStr);

        Product product = productService.findByIdAndDeletedFalse(productId).orElseThrow(() -> {
            throw new DataInputException("Mã sản phẩm không tồn tại");
        });
        ProductDTO productDTO = product.toProductDTO();
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@ModelAttribute ProductCreReqDTO productCreReqDTO, BindingResult bindingResult) {

        if (!validateUtils.isNumberValid(productCreReqDTO.getCategoryId())) {
            throw new DataInputException("Mã danh mục không hợp lệ");
        }

        Long idCategory = Long.parseLong(productCreReqDTO.getCategoryId());
        Category category = categoryService.findByIdAndDeletedFalse(idCategory).orElseThrow(() -> {
            throw new DataInputException("Mã danh mục không tồn tại");
        });

        new ProductCreReqDTO().validate(productCreReqDTO, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }


        Product product = productService.createProduct(productCreReqDTO, category);

        ProductCreResDTO productCreResDTO = product.toProductCreResDTO();
        return new ResponseEntity<>(productCreResDTO, HttpStatus.CREATED);
    }
    @PatchMapping("/edit/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") String productIdStr,@ModelAttribute ProductUpReqDTO productUpReqDTO, BindingResult bindingResult){

        if (!validateUtils.isNumberValid(productIdStr)) {
             throw new DataInputException("Mã sản phẩm không hợp lệ");
        }

        Long productId = Long.parseLong(productIdStr);
        Optional<Product> productOptional = productService.findByIdAndDeletedFalse(productId);
        if (!productOptional.isPresent()) {
            throw new DataInputException("Mã sản phẩm không tồn tại");
        }

        new ProductUpReqDTO().validate(productUpReqDTO,bindingResult);
        if (bindingResult.hasFieldErrors()){
            return appUtils.mapErrorToResponse(bindingResult);
        }

        if (!validateUtils.isNumberValid(productUpReqDTO.getCategoryId())) {
            throw new DataInputException("Mã danh mục không hợp lệ");
        }

        Long idCategory = Long.parseLong(productUpReqDTO.getCategoryId());
        Category category = categoryService.findByIdAndDeletedFalse(idCategory).orElseThrow(() -> {
            throw new DataInputException("Mã danh mục không tồn tại");
        });

        if (productUpReqDTO.getAvatar() == null) {
            Product product = productUpReqDTO.toProductChangeImage(category);
            product.setId(productOptional.get().getId());
            product.setProductAvatar(productOptional.get().getProductAvatar());
            productService.save(product);
            return new ResponseEntity<>(product.toProductUpResDTO(), HttpStatus.OK);
        }
        else {
            Product productUpdate = productService.update(productId,productUpReqDTO,category);
            ProductUpResDTO productUpResDTO = productUpdate.toProductUpResDTO();
            return new ResponseEntity<>(productUpResDTO, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") String productIdStr) {
        if (!validateUtils.isNumberValid(productIdStr)) {
            throw new DataInputException("Mã sản phẩm không hợp lệ");
        }
        Long productId = Long.parseLong(productIdStr);

        Product product = productService.findByIdAndDeletedFalse(productId).orElseThrow(() -> {
            throw new DataInputException("Mã sản phẩm không tồn tại");
        });

        productService.deleteByIdTrue(product);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductBycategory(@PathVariable("categoryId") String categoryIdStr){

        if (!validateUtils.isNumberValid(categoryIdStr)) {
            throw new DataInputException("Mã danh mục không hợp lệ");
        }
        Long categoryId = Long.parseLong(categoryIdStr);
        categoryService.findById(categoryId).orElseThrow(() -> {
           throw new DataInputException("Mã danh mục không tồn tại");
        });

         List<ProductDTO> productDTO  = productService.findAllByCategoryLike(categoryId);

         return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }

    @GetMapping("/searchName/{keySearch}")
    public ResponseEntity<List<ProductDTO>> getProductByName(@PathVariable("keySearch") String keySearch){
        keySearch = '%' + keySearch + '%';

        List<ProductDTO> productDTO = productService.findProductByName(keySearch);
        if (productDTO.isEmpty()){
            throw new DataInputException("Sản phẩm này không tồn tại");
        }
        return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }

    @GetMapping("{/categoryId}/{keySearch}")
    public ResponseEntity<List<ProductDTO>> searchProductCategory(@PathVariable("keySearch") String keySearch,@PathVariable("categoryId") String categoryIdStr){
        if (!validateUtils.isNumberValid(categoryIdStr)) {
            throw new DataInputException("Mã danh mục không hợp lệ");
        }
        Long categoryId = Long.parseLong(categoryIdStr);
        categoryService.findByIdAndDeletedFalse(categoryId).orElseThrow(() -> {
           throw new DataInputException("Mã danh mục không tồn tại");
        });
        keySearch = '%' + keySearch + '%';

         List<ProductDTO> productDTO  = productService.findAllByCategoryLikeAndAndTitleLike(categoryId,keySearch);
         if (productDTO.isEmpty()){
            throw new DataInputException("Sản phẩm này không tồn tại");
        }

         return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProductDTO>> getAllProductDTO(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        try {
            Page<ProductDTO> productDTOS = productService.findAllProductDTOPage(PageRequest.of(page - 1, pageSize));

            if (productDTOS.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(productDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
