package com.chartboost.sdk.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

abstract class db<K, V, M extends Map<K, V>> implements Serializable, ConcurrentMap<K, V> {
    private volatile M a;
    private final transient Lock b = new ReentrantLock();
    private final h<K, V> c;

    protected static abstract class a<E> implements Collection<E> {
        abstract Collection<E> a();

        protected a() {
        }

        public final boolean contains(Object o) {
            return a().contains(o);
        }

        public final boolean containsAll(Collection<?> c) {
            return a().containsAll(c);
        }

        public final Iterator<E> iterator() {
            return new f(a().iterator());
        }

        public final boolean isEmpty() {
            return a().isEmpty();
        }

        public final int size() {
            return a().size();
        }

        public final Object[] toArray() {
            return a().toArray();
        }

        public final <T> T[] toArray(T[] a) {
            return a().toArray(a);
        }

        public int hashCode() {
            return a().hashCode();
        }

        public boolean equals(Object obj) {
            return a().equals(obj);
        }

        public String toString() {
            return a().toString();
        }

        public final boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        public final boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }
    }

    private class b extends a<Entry<K, V>> implements Set<Entry<K, V>> {
        final /* synthetic */ db a;

        private b(db dbVar) {
            this.a = dbVar;
        }

        Collection<Entry<K, V>> a() {
            return this.a.a.entrySet();
        }

        public void clear() {
            this.a.b.lock();
            try {
                Map a = this.a.a();
                a.entrySet().clear();
                this.a.b(a);
            } finally {
                this.a.b.unlock();
            }
        }

        public boolean remove(Object o) {
            Map a;
            this.a.b.lock();
            try {
                if (contains(o)) {
                    a = this.a.a();
                    boolean remove = a.entrySet().remove(o);
                    this.a.b(a);
                    this.a.b.unlock();
                    return remove;
                }
                this.a.b.unlock();
                return false;
            } catch (Throwable th) {
                this.a.b.unlock();
            }
        }

        public boolean removeAll(Collection<?> c) {
            Map a;
            this.a.b.lock();
            try {
                a = this.a.a();
                boolean removeAll = a.entrySet().removeAll(c);
                this.a.b(a);
                this.a.b.unlock();
                return removeAll;
            } catch (Throwable th) {
                this.a.b.unlock();
            }
        }

        public boolean retainAll(Collection<?> c) {
            Map a;
            this.a.b.lock();
            try {
                a = this.a.a();
                boolean retainAll = a.entrySet().retainAll(c);
                this.a.b(a);
                this.a.b.unlock();
                return retainAll;
            } catch (Throwable th) {
                this.a.b.unlock();
            }
        }
    }

    public static abstract class h<K, V> {

        public enum a {
            STABLE {
                <K, V, M extends Map<K, V>> h<K, V> a(db<K, V, M> dbVar) {
                    dbVar.getClass();
                    return new c(dbVar);
                }
            },
            LIVE {
                <K, V, M extends Map<K, V>> h<K, V> a(db<K, V, M> dbVar) {
                    dbVar.getClass();
                    return new e(dbVar);
                }
            };

            abstract <K, V, M extends Map<K, V>> h<K, V> a(db<K, V, M> dbVar);
        }

        abstract Set<K> a();

        abstract Set<Entry<K, V>> b();

        abstract Collection<V> c();

        h() {
        }
    }

    final class c extends h<K, V> implements Serializable {
        final /* synthetic */ db a;

        c(db dbVar) {
            this.a = dbVar;
        }

        public Set<K> a() {
            return Collections.unmodifiableSet(this.a.a.keySet());
        }

        public Set<Entry<K, V>> b() {
            return Collections.unmodifiableSet(this.a.a.entrySet());
        }

        public Collection<V> c() {
            return Collections.unmodifiableCollection(this.a.a.values());
        }
    }

    private class d extends a<K> implements Set<K> {
        final /* synthetic */ db a;

        private d(db dbVar) {
            this.a = dbVar;
        }

        Collection<K> a() {
            return this.a.a.keySet();
        }

        public void clear() {
            this.a.b.lock();
            try {
                Map a = this.a.a();
                a.keySet().clear();
                this.a.b(a);
            } finally {
                this.a.b.unlock();
            }
        }

        public boolean remove(Object o) {
            return this.a.remove(o) != null;
        }

        public boolean removeAll(Collection<?> c) {
            Map a;
            this.a.b.lock();
            try {
                a = this.a.a();
                boolean removeAll = a.keySet().removeAll(c);
                this.a.b(a);
                this.a.b.unlock();
                return removeAll;
            } catch (Throwable th) {
                this.a.b.unlock();
            }
        }

        public boolean retainAll(Collection<?> c) {
            Map a;
            this.a.b.lock();
            try {
                a = this.a.a();
                boolean retainAll = a.keySet().retainAll(c);
                this.a.b(a);
                this.a.b.unlock();
                return retainAll;
            } catch (Throwable th) {
                this.a.b.unlock();
            }
        }
    }

    final class e extends h<K, V> implements Serializable {
        final /* synthetic */ db a;
        private final transient d b = new d();
        private final transient b c = new b();
        private final transient g d = new g();

        e(db dbVar) {
            this.a = dbVar;
        }

        public Set<K> a() {
            return this.b;
        }

        public Set<Entry<K, V>> b() {
            return this.c;
        }

        public Collection<V> c() {
            return this.d;
        }
    }

    private static class f<T> implements Iterator<T> {
        private final Iterator<T> a;

        public f(Iterator<T> it) {
            this.a = it;
        }

        public boolean hasNext() {
            return this.a.hasNext();
        }

        public T next() {
            return this.a.next();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private final class g extends a<V> {
        final /* synthetic */ db a;

        private g(db dbVar) {
            this.a = dbVar;
        }

        Collection<V> a() {
            return this.a.a.values();
        }

        public void clear() {
            this.a.b.lock();
            try {
                Map a = this.a.a();
                a.values().clear();
                this.a.b(a);
            } finally {
                this.a.b.unlock();
            }
        }

        public boolean remove(Object o) {
            Map a;
            this.a.b.lock();
            try {
                if (contains(o)) {
                    a = this.a.a();
                    boolean remove = a.values().remove(o);
                    this.a.b(a);
                    this.a.b.unlock();
                    return remove;
                }
                this.a.b.unlock();
                return false;
            } catch (Throwable th) {
                this.a.b.unlock();
            }
        }

        public boolean removeAll(Collection<?> c) {
            Map a;
            this.a.b.lock();
            try {
                a = this.a.a();
                boolean removeAll = a.values().removeAll(c);
                this.a.b(a);
                this.a.b.unlock();
                return removeAll;
            } catch (Throwable th) {
                this.a.b.unlock();
            }
        }

        public boolean retainAll(Collection<?> c) {
            Map a;
            this.a.b.lock();
            try {
                a = this.a.a();
                boolean retainAll = a.values().retainAll(c);
                this.a.b(a);
                this.a.b.unlock();
                return retainAll;
            } catch (Throwable th) {
                this.a.b.unlock();
            }
        }
    }

    abstract <N extends Map<? extends K, ? extends V>> M a(N n);

    protected <N extends Map<? extends K, ? extends V>> db(N n, a aVar) {
        this.a = (Map) dc.a("delegate", a((Map) dc.a("map", n)));
        this.c = ((a) dc.a("viewType", aVar)).a(this);
    }

    public final void clear() {
        this.b.lock();
        try {
            b(a(Collections.emptyMap()));
        } finally {
            this.b.unlock();
        }
    }

    public final V remove(Object key) {
        this.b.lock();
        Map a;
        try {
            if (this.a.containsKey(key)) {
                a = a();
                V remove = a.remove(key);
                b(a);
                this.b.unlock();
                return remove;
            }
            this.b.unlock();
            return null;
        } catch (Throwable th) {
            this.b.unlock();
        }
    }

    public boolean remove(Object key, Object value) {
        this.b.lock();
        try {
            if (this.a.containsKey(key) && a(value, this.a.get(key))) {
                Map a = a();
                a.remove(key);
                b(a);
                return true;
            }
            this.b.unlock();
            return false;
        } finally {
            this.b.unlock();
        }
    }

    public boolean replace(K key, V oldValue, V newValue) {
        this.b.lock();
        try {
            if (this.a.containsKey(key) && a(oldValue, this.a.get(key))) {
                Map a = a();
                a.put(key, newValue);
                b(a);
                this.b.unlock();
                return true;
            }
            this.b.unlock();
            return false;
        } catch (Throwable th) {
            this.b.unlock();
        }
    }

    public V replace(K key, V value) {
        Map a;
        this.b.lock();
        try {
            if (this.a.containsKey(key)) {
                a = a();
                V put = a.put(key, value);
                b(a);
                this.b.unlock();
                return put;
            }
            this.b.unlock();
            return null;
        } catch (Throwable th) {
            this.b.unlock();
        }
    }

    public final V put(K key, V value) {
        Map a;
        this.b.lock();
        try {
            a = a();
            V put = a.put(key, value);
            b(a);
            this.b.unlock();
            return put;
        } catch (Throwable th) {
            this.b.unlock();
        }
    }

    public V putIfAbsent(K key, V value) {
        Map a;
        this.b.lock();
        try {
            V v;
            if (this.a.containsKey(key)) {
                v = this.a.get(key);
                this.b.unlock();
            } else {
                a = a();
                v = a.put(key, value);
                b(a);
                this.b.unlock();
            }
            return v;
        } catch (Throwable th) {
            this.b.unlock();
        }
    }

    public final void putAll(Map<? extends K, ? extends V> t) {
        this.b.lock();
        try {
            Map a = a();
            a.putAll(t);
            b(a);
        } finally {
            this.b.unlock();
        }
    }

    protected M a() {
        this.b.lock();
        try {
            M a = a(this.a);
            return a;
        } finally {
            this.b.unlock();
        }
    }

    protected void b(M m) {
        this.a = m;
    }

    public final Set<Entry<K, V>> entrySet() {
        return this.c.b();
    }

    public final Set<K> keySet() {
        return this.c.a();
    }

    public final Collection<V> values() {
        return this.c.c();
    }

    public final boolean containsKey(Object key) {
        return this.a.containsKey(key);
    }

    public final boolean containsValue(Object value) {
        return this.a.containsValue(value);
    }

    public final V get(Object key) {
        return this.a.get(key);
    }

    public final boolean isEmpty() {
        return this.a.isEmpty();
    }

    public final int size() {
        return this.a.size();
    }

    public final boolean equals(Object o) {
        return this.a.equals(o);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public String toString() {
        return this.a.toString();
    }

    private boolean a(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        } else {
            return obj.equals(obj2);
        }
    }
}
