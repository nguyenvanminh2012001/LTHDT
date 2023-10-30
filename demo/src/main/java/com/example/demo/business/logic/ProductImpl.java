package com.example.demo.business.logic;

import com.example.demo.entities.ProductCategoryEntity;
import com.example.demo.entities.ProductEntity;
import com.example.demo.entities.ProductImageEntity;
import com.example.demo.entities.ProductInventoryEntity;
import com.example.demo.models.ProductModel;
import com.example.demo.repositories.*;
import com.example.demo.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductImpl {
    private ProductRepository productRepository;
    private ProductCategoryRepository productCategoryRepository;
    private ProductImageRepository productImageRepository;
    private ProductInventoryRepository productInventoryRepository;
    private RecommendProductRepository recommendProductRepository;

    public List<ProductModel> getAllProduct() {
        List<ProductEntity> productEntityArrayList = productRepository.findAll();

        List<ProductModel> productModelList = new ArrayList<>();
        productEntityArrayList.forEach(productEntity -> {
            ProductModel productModel = new ProductModel();
            productEntityToProductModel(productEntity, productModel);

            productModelList.add(productModel);
        });
        Collections.shuffle(productModelList);

        return productModelList;
    }

    public List<ProductModel> getProductRelated(long productId) {
        Long categoryId = productRepository.findCategoryIdById(productId);
        List<ProductModel> productModelList = new ArrayList<>();

        List<ProductEntity> productEntityList = productRepository.findAllByCategoryId(categoryId);
        productEntityList.forEach(productEntity -> {
            ProductModel productModel = new ProductModel();
            productEntityToProductModel(productEntity, productModel);

            productModelList.add(productModel);
        });

        Collections.shuffle(productModelList);
        List<ProductModel> productModelResult = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            productModelResult.add(productModelList.get(i));
        }
        return productModelResult;
    }

    public List<ProductModel> getProductRecommend(Long userId) {
        List<Long> categoryIdList = recommendProductRepository.findAllCategoryIdByUserId(userId);
        List<ProductModel> productModelList = new ArrayList<>();
        categoryIdList.forEach(categoryId -> {
            List<ProductEntity> productEntityList = productRepository.findAllByCategoryId(categoryId);
            productEntityList.forEach(productEntity -> {
                ProductModel productModel = new ProductModel();
                productEntityToProductModel(productEntity, productModel);

                productModelList.add(productModel);
            });
        });

        Collections.shuffle(productModelList);
        List<ProductModel> productModelResult = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            productModelResult.add(productModelList.get(i));
        }
        return productModelResult;
    }

    public List<ProductModel> getAllProductSearch(String keyword) {
        List<ProductEntity> productEntityArrayList = productRepository.findAll();
        System.out.println("search");
        List<ProductModel> productModelList = new ArrayList<>();
        productEntityArrayList.forEach(productEntity -> {
            String productName = StringUtils.removeAccent(productEntity.getName()).toLowerCase();
            String key = StringUtils.removeAccent(keyword).toLowerCase();
            if (productName.contains(key)) {

                System.out.println(productName.indexOf(key));
                System.out.println(productName);
                System.out.println(key);
                ProductModel productModel = new ProductModel();
                productEntityToProductModel(productEntity, productModel);

                productModelList.add(productModel);
            }
        });
        Collections.shuffle(productModelList);

        return productModelList;
    }

    public List<ProductModel> getAllProductByCategory(Long categoryId) {
        List<ProductEntity> productEntityList = productRepository.findAllByCategoryId(categoryId);
        List<ProductModel> productModelList = new ArrayList<>();
        productEntityList.forEach(productEntity -> {
            ProductModel productModel = new ProductModel();
            productEntityToProductModel(productEntity, productModel);

            productModelList.add(productModel);
        });
        Collections.shuffle(productModelList);

        return productModelList;
    }

    public ProductModel getProductById(Long productId) {
        ProductModel productModel = new ProductModel();
        ProductEntity productEntity = productRepository.getById(productId);
        productEntityToProductModel(productEntity, productModel);
        return productModel;
    }

    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }


    public void createProduct(ProductModel productModel) {
        long categoryId = productCategoryRepository.findIdByCategoryName(productModel.getCategory()).orElseGet(() -> {
            ProductCategoryEntity productCategoryEntity = productCategoryRepository.save(new ProductCategoryEntity(productModel.getCategory()));
            return productCategoryEntity.getId();
        });

        ProductImageEntity productImageEntity = new ProductImageEntity(productModel.getProductImageEntity().getImage1(),
                productModel.getProductImageEntity().getImage2(),
                productModel.getProductImageEntity().getImage3(),
                productModel.getProductImageEntity().getImage4());
        productImageRepository.save(productImageEntity);

        long imageId = productImageEntity.getId();

        ProductInventoryEntity productInventoryEntity = new ProductInventoryEntity();
        productInventoryEntity.setQuantity(1000);              // set quantity default 1000
        productInventoryRepository.save(productInventoryEntity);

        long inventoryId = productInventoryEntity.getId();

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productModel.getName());
        productEntity.setDescription(productModel.getDescription());
        productEntity.setBuyingPrice(productModel.getBuyingPrice());
        productEntity.setPrice(productModel.getPrice());
        productEntity.setCategoryId(categoryId);
        productEntity.setImageId(imageId);
        productEntity.setInventoryId(inventoryId);

        productRepository.save(productEntity);
    }

    private void productEntityToProductModel(ProductEntity productEntity, ProductModel productModel) {
        productModel.setId(productEntity.getId());
        productModel.setName(productEntity.getName());
        productModel.setDescription(productEntity.getDescription());
        productModel.setPrice(productEntity.getPrice());
        productModel.setCategory(productCategoryRepository.findById(productEntity.getCategoryId()).get().getName());
        productModel.setProductImageEntity(productImageRepository.findById(productEntity.getImageId()).get());
    }
}
