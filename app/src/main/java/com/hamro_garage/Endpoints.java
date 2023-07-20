package com.hamro_garage;

public class Endpoints {

    private static final String BASE_URL = "http://192.168.1.14/hamro_garage_backend/";

    public static final String SIGNUP_URL = BASE_URL + "user_signup.php";
    public static final String LOGIN_URL = BASE_URL + "user_login.php";
    public static final String ADMIN_LOGIN_URL = BASE_URL + "admin_login.php";
    public static final String USER_PROFILE = BASE_URL + "getprofile.php";
    public static final String SERVICES_REQUESTS = BASE_URL + "services.php";
    public static final String SAVE_LOCATION = BASE_URL + "save_location.php";
    public static final String GET_SERVICES = BASE_URL + "getservices.php";
    public static final String GET_edituserprofile = BASE_URL + "edituserprofile.php";
    public static final String GET_GARAGE_WONER_PROFILE = BASE_URL + "garage_owner_profile.php";
    public static final String FETCH_LOCATIONS= BASE_URL + "fetch_locations.php";
    public static final String CREATEGARAGE= BASE_URL + "creategarage.php";
    public static final String get_garage_detail= BASE_URL + "get_garage_detail.php";
    public static final String get_edit_garage_detail= BASE_URL + "edit_garage_detail.php";
    public static final String get_business= BASE_URL + "getbusiness.php";
    public static final String approve= BASE_URL + "approve.php";
    public static final String pending= BASE_URL + "pending.php";
}