package com.maximoapps.in.localgudie_mapnavigation.helpers;

/**
 * Created by ${ChandraMohanReddy} on 1/25/2017.
 */

public class Constants {

    public static String APP_LANGUAGE = "en";   // ORIGINAL API_KEY  >>>BILLING ADDED
    //CONCENTRATE ON XINCHENG TOWNSHIP PLACE IN HUALIEN, TAIWAN
    public static String API_KEY = "AIzaSyABpsBAhnFtxKZzC4W-PjH2o7pABWywcC0";   // ORIGINAL API_KEY  >>>BILLING ADDED
    //public static String API_KEY = "AIzaSyBG8DU_2LXgffbPnsn_25AYSCm2Ro8lUbE";   // ORIGINAL API_KEY  >>>BILLING ADDED
    public static String DEVICE_OID;
    public static String REG_EMAIL_ID = null;
    public static String REG_DATE_TIME = null;
    public static String IMEI, IMSI, ANDROID_ID, DEVICE_MANUFACTURER, DEVICE_MODEL, OS_VERSION, NETWORK_TYPE, NETWORK_NAME, REGISTERED_EMAIL_ID, CREATION_DATE, DEVICE_WIDTH, DEVICE_HEIGHT, SIM_SERIAL_NUMBER, CURRENT_LAT_REG, CURRNET_LON_REG;
    public static int USER_VERIFIED_STATUS = 0;
    public static int USER_LOGIN_STATUS = 0;
    public static int USER_STATUS = 0;

    //LOGIN DETAILS
    public static String FCM_TOKEN, MOBILE_NO, PASSWORD_LOGIN, USER_PHONE_LOGIN;
    public static double USER_LAT_LOGIN, USER_LON_LOGIN;
    public static String FORGET_PASSWORD_MOBILE_NO;
    public static String USER_OID;
    public static String NEXT_PAGE_TOKEN;

    public static final CharSequence[] professionals = {"Car Dealer",
            "Dentist", "Doctor", "Lawyer", "Physiotherapist"};

    public static final CharSequence[] services = {"Atm",
            "Bank", "Bowling Alley", "Bus Station", "Car Rental", "Car Repair", "Car Wash", "Electrician", "Gas Station", "Library", "Movie Theater", "Night Club", "Painter", "Parking", "Plumber", "Police", "Post Office", "Spa", "Veterinary Care"};

    public static final CharSequence[] property = {"Park",
            "Real Estate Agency", "Stadium"};

    public static final CharSequence[] business = {"Bakery",
            "Bar", "Beauty Salon", "Book Store", "Cafe", "Clothing Store", "Convenience Store", "Department Store", "Electronics Store", "Furniture Store", "Florist", "Gym", "Hair Care", "Hardware Store", "Home Goods Store", "hospital", "Insurance Agency", "Jewelry Store", "Laundry", "Liquor Store", "Lodging", "Pet Store", "Pharmacy", "Restaurant", "School", "Shoe Store", "Shopping Mall", "Travel Agency"};

    //ACCOUNT
    public static final String ACCOUNT_TYPE = "com.listoit.location";
    public static final String ACCOUNT_NAME = "Listo";
    public static final String ACCOUNT_TOKEN = "12345";

//    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";

    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
}