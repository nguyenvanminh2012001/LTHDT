package com.example.demo.exceptions;

public class CartEntityNotExistException extends SystemException {
    private static final Integer CODE = 400;
    private static final String USER_MESSAGE = "Cart entity not exist!";
    private static final String DETAIL_MESSAGE = "Cart entity not exist, cart user id = %d is empty.";

    public CartEntityNotExistException(long userId) {
        super.setCode(CODE);
        super.setUserMessage(USER_MESSAGE);
        super.setDetailMessage(String.format(DETAIL_MESSAGE, userId));
    }
}
