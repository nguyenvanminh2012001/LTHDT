package com.example.demo.business.logic;


import com.example.demo.entities.*;
import com.example.demo.models.OrderDetailModel;
import com.example.demo.models.OrderModel;
import com.example.demo.models.UserInfoModel;
import com.example.demo.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderImpl {
    private CartRepository cartRepository;
    private OrderDetailRepository orderDetailRepository;
    private OrderRepository orderRepository;
    private UserInfoRepository userInfoRepository;
    private UserRepository userRepository;

    @Transactional
    public void order(Long userId, UserInfoModel orderModel) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(userId);
        orderEntity.setAddress(orderModel.getAddress().trim() + " " + orderModel.getCity() + " " + orderModel.getCountry());
        orderEntity.setStatus("Pending");
        orderEntity.setUserId(userId);
        orderRepository.save(orderEntity);

        UserInfoEntity userInfoEntity = orderModelToUserInfo(orderModel, userId);
        userInfoRepository.save(userInfoEntity);

        UserEntity userEntity = userRepository.findUserById(userId);
        userEntity.setFirstName(orderModel.getFirstName());
        userEntity.setLastName(orderModel.getLastName());
        userRepository.save(userEntity);

        List<CartEntity> cartList = cartRepository.findAllByUserId(userId).get();

        cartList.forEach(x -> {
            orderDetailRepository.save(cartToOrder(x, orderEntity.getId()));
            cartRepository.deleteCartEntityByUserId(userId);
        });
    }

    public List<OrderModel> getOrder(Long userId) {
        List<OrderModel> orderModelList = new ArrayList<>();
        List<OrderEntity> orderEntityList = orderRepository.findAllByUserId(userId);
        orderEntityList.forEach(x->{
            orderModelList.add(orderEntityToOrderModel(x));
        });
        return orderModelList;
    }

    private OrderModel orderEntityToOrderModel(OrderEntity orderEntity) {
        OrderModel orderModel = new OrderModel();

        orderModel.setId(orderEntity.getId());
        orderModel.setStatus(orderEntity.getStatus());
        orderModel.setAddress(orderEntity.getAddress());
        orderModel.setUserName(orderEntity.getUserEntity().getFirstName()+" "+orderEntity.getUserEntity().getLastName());

        Date date = orderEntity.getCreatedAt();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(date);

        orderModel.setDate(strDate.substring(0,10));
        double total = 0.0;
        List<OrderDetailModel> orderDetailModelList = new ArrayList<>();
        List<OrderDetailEntity> orderDetailEntityList = orderDetailRepository.findAllByOrderId(orderEntity.getId());
        for (OrderDetailEntity x : orderDetailEntityList) {
            OrderDetailModel orderDetailModel = new OrderDetailModel();
            orderDetailModel.setPrice(x.getProductEntity().getPrice());
            orderDetailModel.setProductName(x.getProductEntity().getName());
            orderDetailModel.setQuantity(x.getQuantity());

            orderDetailModelList.add(orderDetailModel);
            total += x.getProductEntity().getPrice() * x.getQuantity();
        }
        orderModel.setOrderDetailModelList(orderDetailModelList);
        orderModel.setTotal(total);
        return orderModel;
    }

    private OrderDetailEntity cartToOrder(CartEntity cartEntity, Long orderId) {
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        orderDetailEntity.setProductId(cartEntity.getProductId());
        orderDetailEntity.setQuantity(cartEntity.getQuantity());
        orderDetailEntity.setOrderId(orderId);
        return orderDetailEntity;
    }

    private UserInfoEntity orderModelToUserInfo(UserInfoModel orderModel, Long userId) {
        UserInfoEntity userInfoEntity = userInfoRepository.findByUserId(userId).orElse(new UserInfoEntity());
        userInfoEntity.setAddress(orderModel.getAddress());
        userInfoEntity.setCity(orderModel.getCity());
        userInfoEntity.setCountry(orderModel.getCountry());
        userInfoEntity.setEmail(orderModel.getEmail());
        userInfoEntity.setTelephone(orderModel.getPhone());
        userInfoEntity.setUserId(userId);
        return userInfoEntity;
    }

    public List<OrderModel> getAllOrder() {
        List<OrderModel> orderModelList = new ArrayList<>();
        List<OrderEntity> orderEntityList = orderRepository.findAll();
        orderEntityList.forEach(x->{
            orderModelList.add(orderEntityToOrderModel(x));
        });
        return orderModelList;
    }

    public void acceptOrder(long orderId) {
        OrderEntity orderEntity = orderRepository.getById(orderId);
        orderEntity.setStatus("Accept");

        orderRepository.save(orderEntity);
    }
}
