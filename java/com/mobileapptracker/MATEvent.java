package com.mobileapptracker;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MATEvent implements Serializable {
    public static final String ACHIEVEMENT_UNLOCKED = "achievement_unlocked";
    public static final String ADDED_PAYMENT_INFO = "added_payment_info";
    public static final String ADD_TO_CART = "add_to_cart";
    public static final String ADD_TO_WISHLIST = "add_to_wishlist";
    public static final String CHECKOUT_INITIATED = "checkout_initiated";
    public static final String CONTENT_VIEW = "content_view";
    public static final String DEVICE_FORM_WEARABLE = "wearable";
    public static final String INVITE = "invite";
    public static final String LEVEL_ACHIEVED = "level_achieved";
    public static final String LOGIN = "login";
    public static final String PURCHASE = "purchase";
    public static final String RATED = "rated";
    public static final String REGISTRATION = "registration";
    public static final String RESERVATION = "reservation";
    public static final String SEARCH = "search";
    public static final String SHARE = "share";
    public static final String SPENT_CREDITS = "spent_credits";
    public static final String TUTORIAL_COMPLETE = "tutorial_complete";
    private String a;
    private int b;
    private double c;
    private String d;
    private String e;
    private List<MATEventItem> f;
    private String g;
    private String h;
    private String i;
    private String j;
    private int k;
    private int l;
    private String m;
    private double n;
    private Date o;
    private Date p;
    private String q;
    private String r;
    private String s;
    private String t;
    private String u;
    private String v;

    public MATEvent(int eventId) {
        this.b = eventId;
    }

    public MATEvent(String eventName) {
        this.a = eventName;
    }

    public String getAttribute1() {
        return this.q;
    }

    public String getAttribute2() {
        return this.r;
    }

    public String getAttribute3() {
        return this.s;
    }

    public String getAttribute4() {
        return this.t;
    }

    public String getAttribute5() {
        return this.u;
    }

    public String getContentId() {
        return this.j;
    }

    public String getContentType() {
        return this.i;
    }

    public String getCurrencyCode() {
        return this.d;
    }

    public Date getDate1() {
        return this.o;
    }

    public Date getDate2() {
        return this.p;
    }

    public String getDeviceForm() {
        return this.v;
    }

    public int getEventId() {
        return this.b;
    }

    public List<MATEventItem> getEventItems() {
        return this.f;
    }

    public String getEventName() {
        return this.a;
    }

    public int getLevel() {
        return this.k;
    }

    public int getQuantity() {
        return this.l;
    }

    public double getRating() {
        return this.n;
    }

    public String getReceiptData() {
        return this.g;
    }

    public String getReceiptSignature() {
        return this.h;
    }

    public String getRefId() {
        return this.e;
    }

    public double getRevenue() {
        return this.c;
    }

    public String getSearchString() {
        return this.m;
    }

    public MATEvent withAdvertiserRefId(String refId) {
        this.e = refId;
        return this;
    }

    public MATEvent withAttribute1(String attribute1) {
        this.q = attribute1;
        return this;
    }

    public MATEvent withAttribute2(String attribute2) {
        this.r = attribute2;
        return this;
    }

    public MATEvent withAttribute3(String attribute3) {
        this.s = attribute3;
        return this;
    }

    public MATEvent withAttribute4(String attribute4) {
        this.t = attribute4;
        return this;
    }

    public MATEvent withAttribute5(String attribute5) {
        this.u = attribute5;
        return this;
    }

    public MATEvent withContentId(String contentId) {
        this.j = contentId;
        return this;
    }

    public MATEvent withContentType(String contentType) {
        this.i = contentType;
        return this;
    }

    public MATEvent withCurrencyCode(String currencyCode) {
        this.d = currencyCode;
        return this;
    }

    public MATEvent withDate1(Date date1) {
        this.o = date1;
        return this;
    }

    public MATEvent withDate2(Date date2) {
        this.p = date2;
        return this;
    }

    public MATEvent withDeviceForm(String deviceForm) {
        this.v = deviceForm;
        return this;
    }

    public MATEvent withEventItems(List<MATEventItem> items) {
        this.f = items;
        return this;
    }

    public MATEvent withLevel(int level) {
        this.k = level;
        return this;
    }

    public MATEvent withQuantity(int quantity) {
        this.l = quantity;
        return this;
    }

    public MATEvent withRating(double rating) {
        this.n = rating;
        return this;
    }

    public MATEvent withReceipt(String receiptData, String receiptSignature) {
        this.g = receiptData;
        this.h = receiptSignature;
        return this;
    }

    public MATEvent withRevenue(double revenue) {
        this.c = revenue;
        return this;
    }

    public MATEvent withSearchString(String searchString) {
        this.m = searchString;
        return this;
    }
}
