package com.example.demo.business.logic;

import com.example.demo.entities.CartEntity;
import com.example.demo.entities.ProductEntity;
import com.example.demo.models.ProductCartModel;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartImpl {
    private CartRepository cartRepository;
    private ProductRepository productRepository;

    public void addToCart(long userId, long productId) {
        if (!cartRepository.getCartEntityByUserIdAndProductId(userId, productId).isPresent()) {
            CartEntity cartEntity = new CartEntity(userId, productId);
            cartEntity.setQuantity(1);
            cartRepository.save(cartEntity);
        }
    }

    @Transactional
    public void deleteProductInCart(long userId, long productId) {
        cartRepository.deleteByUserIdAndProductId(userId, productId);
    }


    public List<ProductCartModel> getCart(long userId) {
        List<CartEntity> cartEntityList = cartRepository.findAllByUserId(userId).get();
        List<ProductCartModel> cart = new ArrayList<>();
        cartEntityList.forEach(x -> {
            ProductEntity productEntity = productRepository.findById(x.getProductId()).get();
            ProductCartModel productCartModel = new ProductCartModel();
            productCartModel.setId(productEntity.getId());
            productCartModel.setName(productEntity.getName());
            productCartModel.setPrice(productEntity.getPrice());
            productCartModel.setImage(productEntity.getProductImageEntity().getImage1());
            productCartModel.setQuantity(x.getQuantity());
            productCartModel.setTotal(productEntity.getPrice() * x.getQuantity());
            cart.add(productCartModel);
        });
        return cart;
    }

    public void incQuantityInCart(long userId, long productId) {
        CartEntity cartEntity = cartRepository.getCartEntityByUserIdAndProductId(userId, productId).get();
        int quantity = cartEntity.getQuantity();
        quantity += 1;
        cartEntity.setQuantity(quantity);
        cartRepository.save(cartEntity);
    }

    public void desQuantityInCart(long userId, long productId) {
        CartEntity cartEntity = cartRepository.getCartEntityByUserIdAndProductId(userId, productId).get();
        int quantity = cartEntity.getQuantity();
        quantity -= 1;
        cartEntity.setQuantity(quantity);
        cartRepository.save(cartEntity);
    }
}
