package com.cruise.preetika.cruiseapp.utils;

/**
 * Created by 300283413 on 7/19/2018.
 */

public class Constants {

    /**
     * DB Credentials
     */
    public static final String DATABASE_NAME = "cruise.db";
    public static final int DATABASE_VERSION = 1;

    /*
    table names
     */

    public static final String USER_INFO="user_info";
    public static final String ROOM="room";
    public static final String TRANSACTION="invoice";

    /*
    user_info fields
     */
    public static final String USERID="user_id";
    public static final String USERNAME="user_name";
    public static final String PASSWORD="password";
    public static final String NAME="uname";
    public static final String EMAIL="email";
    public static final String PHONE="contact";

    /*
    rooms table fields
     */
    public static final String ROOM_ID="room_id";
    public static final String ROOMT_TYPE="room_type"; // oceanView=0, Concierge=1, inside=2, verandah=3
    public static final String DECK_NO="deck_no";
    public static final String PEOPLE_ACCOMPANYING="people_accompanying";
    public static final String ROOM_INVOICE="room_invoice";

    /*
    transaction table fields
     */

    public static final String TRANSACTION_ID = "transaction_id";
    public static final String SERVICE_NAME="service_name";
    public static final String ACTIVITY_TYPE="activity_type"; //onboard=0, port of call =1, other service=2
    public static final String DATE="date";
    public static final String TIME="time";
    public static final String BOOKING_PERSON="booking_person";
    public static final String BOOKING_DATETIME="booking_datetime";
    public static final String CHARGES="charges";

}
