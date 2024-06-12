package com.example.ecommerce.config;

import java.util.ArrayList;
import java.util.Arrays;

public class Constant {
    public static final int MAX_AGE_COOKIE = 7 * 24 * 60 * 60;

    public static final int PUBLIC_POST = 1;
    public static final int DRAFT_POST = 0;

    public static final int ORDER_STATUS = 1;
    public static final int DELIVERY_STATUS = 2;
    public static final int COMPLETE_STATUS = 3;
    public static final int RETURNED_STATUS = 4;
    public static final int CANCELED_STATUS = 5;
    public static ArrayList<Integer> LIST_ORDER_STATUS
            = new ArrayList<>(Arrays
                    .asList(ORDER_STATUS, DELIVERY_STATUS, COMPLETE_STATUS, RETURNED_STATUS, CANCELED_STATUS));
}
