package com.example.mrrs.mob402_asm_ps05854;

public class Constants {
    public static final String BASE_URL = "http://192.168.254.2/";
    public static final String REGISTER_OPERATION = "register";
    public static final String LOGIN_OPERATION = "login";
    public static final String CHANGE_PASSWORD_OPERATION = "chgPass";

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String IS_LOGGED_IN = "isLoggedIn";

    public static final String NAME = "name";
    public static final String EMAIL = "email";

    public static final String UNIQUE_ID = "unique_id";

    public static final String TAG = "LearnAPI";

    public static String url_all_products =
            "http://192.168.254.2/learn-login-register/get_all_product.php";
    public static String url_create_products =
            "http://192.168.254.2/learn-login-register/create_product.php";
    public static String url_product_detail =
            "http://192.168.254.2/learn-login-register/get_product_detail.php";
    public static String url_update_product =
            "http://192.168.254.2/learn-login-register/update_product.php";
    public static String url_delete_product =
            "http://192.168.254.2/learn-login-register/delete_product.php";
    // JSON Node names
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_PRODUCTS = "products";
    public static final String TAG_PRODUCT = "product";
    public static final String TAG_PID = "pid";
    public static final String TAG_NAME = "name";
    public static final String TAG_PRICE = "price";
    public static final String TAG_DESCRIPTION = "description";
}