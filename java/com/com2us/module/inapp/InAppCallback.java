package com.com2us.module.inapp;

import jp.co.cyberz.fox.a.a.i;

public interface InAppCallback {
    public static final int AMAZON_MARKETPLACE = 40;
    public static final int AMAZON_PRODUCTID = 21;
    @Deprecated
    public static final int AMAZON_PURCHASEDATE = 39;
    @Deprecated
    public static final int AMAZON_PURCHASETOKEN = 23;
    public static final int AMAZON_RECEIPTID = 23;
    public static final int AMAZON_REQUESTID = 24;
    public static final int AMAZON_USERID = 22;
    @Deprecated
    public static final int GOOGLEINAPP_RECEIPT = 2;
    @Deprecated
    public static final int GOOGLEINAPP_SIGNATURE = 3;
    public static final int GOOGLEPLAY_RECEIPT = 35;
    public static final int GOOGLEPLAY_SIGNATURE = 36;
    public static final int HAMI_DATELOCAL = 11;
    public static final int KDDI_SIGNATURE = 26;
    public static final int KDDI_TRANSACTION = 25;
    public static final int LEBI_BILLINGNUM = 12;
    public static final int MARKET_NAME = 1;
    public static final int MARKET_NUMBER = 0;
    public static final int MAX_SUCCESS_STATE_VALUE = 41;
    public static final int MBIZ_EMONEY = 29;
    public static final int MBIZ_PURCHASEDATE = 30;
    public static final int MM_ORDERID = 27;
    public static final int MM_PURCHASEDATE = 28;
    public static final int OLLEHMARKET_DATELOCAL = 7;
    public static final int OLLEHMARKET_TRANSACTIONID = 6;
    public static final int OZSTORE_DATELOCAL = 8;
    public static final int OZSTORE_TXID = 38;
    public static final int OZSTORE_UKEY = 37;
    public static final int PLASMA_ITEMID = 15;
    public static final int PLASMA_PARAM1 = 17;
    public static final int PLASMA_PARAM2 = 18;
    public static final int PLASMA_PARAM3 = 19;
    public static final int PLASMA_PAYMENTID = 13;
    public static final int PLASMA_PURCHASEDATE = 20;
    public static final int PLASMA_PURCHASEID = 14;
    public static final int PLASMA_VERIFYURL = 16;
    public static final int QIIP_DATELOCAL = 10;
    public static final int THIRDPARTY_ADDITIONALINFO = 9;
    @Deprecated
    public static final int THIRDPARTY_ORDERKEY = 9;
    @Deprecated
    public static final int TSTORE_DATELOCAL = 5;
    @Deprecated
    public static final int TSTORE_TID = 4;
    public static final int TSTORE_TRANSACTION = 4;

    public static class ErrorStateValue {
        public String errorCode = i.a;
        public String errorMsg = i.a;
        public String errorValue = i.a;

        public ErrorStateValue(String errorCode, String errorValue, String errorMsg) {
            this.errorCode = errorCode;
            this.errorValue = errorValue;
            this.errorMsg = errorMsg;
        }

        public ErrorStateValue(int errorCode, int errorValue, String errorMsg) {
            try {
                this.errorCode = String.valueOf(errorCode);
            } catch (Exception e) {
                this.errorCode = i.a;
            }
            try {
                this.errorValue = String.valueOf(errorValue);
            } catch (Exception e2) {
                this.errorValue = i.a;
            }
            this.errorMsg = errorMsg;
        }
    }

    public static class ItemList {
        public long amountMicros = 0;
        public String currencyCode = i.a;
        public String formattedString = i.a;
        public String localizedDescription = i.a;
        public String localizedTitle = i.a;
        public String productID = i.a;
    }

    void BUY_CANCELED(String str, int i, String str2, String str3, Object obj);

    void BUY_FAILED(String str, int i, String str2, String str3, Object obj);

    void BUY_SUCCESS(String str, int i, String str2, String str3, Object obj);

    void GET_ITEM_LIST(String str, int i, String str2, String str3, Object obj);

    void PURCHASE_UPDATED(String str, int i, String str2, String str3, Object obj);

    void RESTORE_SUCCESS(String str, int i, String str2, String str3, Object obj);
}
