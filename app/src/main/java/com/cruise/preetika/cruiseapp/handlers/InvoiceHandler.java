package com.cruise.preetika.cruiseapp.handlers;

/**
 * Created by 300283413 on 7/31/2018.
 */

public class InvoiceHandler {

    private String service;
    public double charges;
    public String date;
    public String bookingPerson;

    public double getCharges() {
        return charges;
    }

    public void setCharges(double charges) {
        this.charges = charges;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBookingPerson() {
        return bookingPerson;
    }

    public void setBookingPerson(String bookingPerson) {
        this.bookingPerson = bookingPerson;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
