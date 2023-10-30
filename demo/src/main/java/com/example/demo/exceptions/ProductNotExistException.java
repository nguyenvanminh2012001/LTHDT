package com.example.demo.exceptions;

public class ProductNotExistException extends SystemException {
    private static final Integer CODE = 400;
    private static final String USER_MESSAGE = "Product not exist";
    private static final String DETAIL_MESSAGE = "Product not exist, please try another product id.";

    public ProductNotExistException(long productId) {
        super.setCode(CODE);
        super.setUserMessage(USER_MESSAGE);
        super.setDetailMessage(String.format(DETAIL_MESSAGE, productId));
    }
}
