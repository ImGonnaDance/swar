package com.mobileapptracker;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class MATEventItem {
    public String attribute_sub1;
    public String attribute_sub2;
    public String attribute_sub3;
    public String attribute_sub4;
    public String attribute_sub5;
    public String itemname;
    public int quantity;
    public double revenue;
    public double unitPrice;

    public MATEventItem(String itemname) {
        this.itemname = itemname;
    }

    public String getAttrStringByName(String name) {
        return name.equals("itemname") ? this.itemname : name.equals("quantity") ? Integer.toString(this.quantity) : name.equals("unitPrice") ? Double.toString(this.unitPrice) : name.equals("revenue") ? Double.toString(this.revenue) : name.equals("attribute_sub1") ? this.attribute_sub1 : name.equals("attribute_sub2") ? this.attribute_sub2 : name.equals("attribute_sub3") ? this.attribute_sub3 : name.equals("attribute_sub4") ? this.attribute_sub4 : name.equals("attribute_sub5") ? this.attribute_sub5 : null;
    }

    public JSONObject toJSON() {
        Map hashMap = new HashMap();
        if (this.itemname != null) {
            hashMap.put("item", this.itemname);
        }
        hashMap.put("quantity", Integer.toString(this.quantity));
        hashMap.put("unit_price", Double.toString(this.unitPrice));
        if (this.revenue != 0.0d) {
            hashMap.put("revenue", Double.toString(this.revenue));
        }
        if (this.attribute_sub1 != null) {
            hashMap.put("attribute_sub1", this.attribute_sub1);
        }
        if (this.attribute_sub2 != null) {
            hashMap.put("attribute_sub2", this.attribute_sub2);
        }
        if (this.attribute_sub3 != null) {
            hashMap.put("attribute_sub3", this.attribute_sub3);
        }
        if (this.attribute_sub4 != null) {
            hashMap.put("attribute_sub4", this.attribute_sub4);
        }
        if (this.attribute_sub5 != null) {
            hashMap.put("attribute_sub5", this.attribute_sub5);
        }
        return new JSONObject(hashMap);
    }

    public MATEventItem withAttribute1(String attribute) {
        this.attribute_sub1 = attribute;
        return this;
    }

    public MATEventItem withAttribute2(String attribute) {
        this.attribute_sub2 = attribute;
        return this;
    }

    public MATEventItem withAttribute3(String attribute) {
        this.attribute_sub3 = attribute;
        return this;
    }

    public MATEventItem withAttribute4(String attribute) {
        this.attribute_sub4 = attribute;
        return this;
    }

    public MATEventItem withAttribute5(String attribute) {
        this.attribute_sub5 = attribute;
        return this;
    }

    public MATEventItem withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public MATEventItem withRevenue(double revenue) {
        this.revenue = revenue;
        return this;
    }

    public MATEventItem withUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }
}
