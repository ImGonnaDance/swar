package com.com2us.peppermint;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class NotificationCenter {
    HashMap<String, Observable> a = new HashMap();

    public void addObserver(String str, Observer observer) {
        Observable observable = (Observable) this.a.get(str);
        if (observable == null) {
            observable = new Observable();
            this.a.put(str, observable);
        }
        observable.addObserver(observer);
    }

    public void postNotification(String str, Object obj) {
        Observable observable = (Observable) this.a.get(str);
        if (observable == null) {
            return;
        }
        if (obj == null) {
            observable.notifyObservers();
        } else {
            observable.notifyObservers(obj);
        }
    }

    public void removeObserver(String str, Observer observer) {
        Observable observable = (Observable) this.a.get(str);
        if (observable != null) {
            observable.deleteObserver(observer);
        }
    }
}
