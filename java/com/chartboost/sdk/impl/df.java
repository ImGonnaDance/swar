package com.chartboost.sdk.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

final class df<K, V> implements dh<K, V>, Map<K, V> {
    private final ConcurrentMap<K, V> a;
    private final dh<K, V> b;

    public static <K, V> Map<K, V> a(dh<K, V> dhVar) {
        return new df(dg.c(), dhVar);
    }

    df(ConcurrentMap<K, V> concurrentMap, dh<K, V> dhVar) {
        this.a = (ConcurrentMap) dc.a("map", concurrentMap);
        this.b = (dh) dc.a("function", dhVar);
    }

    public V get(Object key) {
        while (true) {
            V v = this.a.get(key);
            if (v != null) {
                return v;
            }
            Object a = this.b.a(key);
            if (a == null) {
                return null;
            }
            this.a.putIfAbsent(key, a);
        }
    }

    public V a(K k) {
        return get(k);
    }

    public int size() {
        return this.a.size();
    }

    public boolean isEmpty() {
        return this.a.isEmpty();
    }

    public boolean containsKey(Object key) {
        return this.a.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.a.containsValue(value);
    }

    public V put(K key, V value) {
        return this.a.put(key, value);
    }

    public V remove(Object key) {
        return this.a.remove(key);
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        this.a.putAll(m);
    }

    public void clear() {
        this.a.clear();
    }

    public Set<K> keySet() {
        return this.a.keySet();
    }

    public Collection<V> values() {
        return this.a.values();
    }

    public Set<Entry<K, V>> entrySet() {
        return this.a.entrySet();
    }

    public boolean equals(Object o) {
        return this.a.equals(o);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
