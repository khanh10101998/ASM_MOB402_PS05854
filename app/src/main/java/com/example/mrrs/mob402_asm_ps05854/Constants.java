package com.example.mrrs.mob402_asm_ps05854;

public class Constants {
    public static final String BASE_URL = "http://192.168.1.7/";
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
            BASE_URL+"learn-login-register/get_all_product.php";
    public static String url_user_products =
            BASE_URL+"learn-login-register/get_user_product.php";
    public static String url_create_products =
            BASE_URL+"learn-login-register/create_product.php";
    public static String url_product_detail =
            BASE_URL+"learn-login-register/get_product_detail.php";
    public static String url_update_product =
            BASE_URL+ "learn-login-register/update_product.php";
    public static String url_delete_product =
            BASE_URL+"learn-login-register/delete_product.php";
    // JSON Node names
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_PRODUCTS = "products";
    public static final String TAG_PRODUCT = "product";

    public static final String TAG_PID = "idProduct";
    public static final String TAG_UID = "idUser";
    public static final String TAG_NAME = "nameProduct";
    public static final String TAG_PRICE = "priceProduct";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_image = "imageProduct";

    // forgot pass
    public static final String RESET_PASSWORD_INITIATE = "resPassReq";
    public static final String RESET_PASSWORD_FINISH = "resPass";


}