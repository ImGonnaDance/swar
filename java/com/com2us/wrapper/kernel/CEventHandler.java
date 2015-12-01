package com.com2us.wrapper.kernel;

import com.com2us.module.manager.ModuleConfig;
import com.com2us.wrapper.kernel.CWrapperKernel.r;
import java.nio.ByteBuffer;
import java.util.Vector;
import jp.co.dimage.android.o;

public class CEventHandler extends CWrapper {
    private final int a;
    private final int b;
    private Vector<a> c;
    private boolean d;
    private ByteBuffer e;
    private Runnable f;
    private EventData g;

    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a = new int[r.values().length];

        static {
            try {
                a[r.APPLICATION_EXITED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    public class EventData {
        public int front = 0;
        public int rear = 0;
    }

    class a {
        public short a;
        public short b;
        public short c;
        public byte d;
        public byte e;
        final /* synthetic */ CEventHandler f;

        public a(CEventHandler cEventHandler, int i, int i2, int i3, int i4, int i5) {
            this.f = cEventHandler;
            this.a = (short) i;
            this.b = (short) i2;
            this.c = (short) i3;
            this.d = (byte) i4;
            this.e = (byte) i5;
        }
    }

    public CEventHandler() {
        this.a = ModuleConfig.SOCIAL_MEDIA_MOUDLE;
        this.b = 8;
        this.c = new Vector();
        this.d = false;
        this.e = null;
        this.f = null;
        this.g = null;
        this.e = ByteBuffer.allocateDirect(ModuleConfig.OFFERWALL_MODULE);
        this.g = new EventData();
        nativeEventHandlerInitialize(this.g, this.e, 8, ModuleConfig.SOCIAL_MEDIA_MOUDLE);
    }

    private synchronized boolean a(short s, short s2, short s3, byte b, byte b2) {
        boolean z;
        int i = this.g.front;
        if ((i + 1) % ModuleConfig.SOCIAL_MEDIA_MOUDLE != this.g.rear) {
            if (!this.e.hasRemaining()) {
                this.e.rewind();
            }
            this.e.putShort(s);
            this.e.putShort(s2);
            this.e.putShort(s3);
            this.e.put(b);
            this.e.put(b2);
            this.g.front = (i + 1) % ModuleConfig.SOCIAL_MEDIA_MOUDLE;
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    private native void nativeEventHandlerInitialize(EventData eventData, ByteBuffer byteBuffer, int i, int i2);

    private native void nativeRemoveAll();

    public synchronized void addEvent(int i, int i2, int i3, int i4, int i5) {
        if (this.d) {
            this.c.add(new a(this, i, i2, i3, i4, i5));
        } else if (!a((short) i, (short) i2, (short) i3, (byte) i4, (byte) i5)) {
            this.c.add(new a(this, i, i2, i3, i4, i5));
            this.d = true;
            new Thread(new Runnable(this) {
                final /* synthetic */ CEventHandler b;

                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                    r10 = this;
                    r9 = 1;
                L_0x0001:
                    r8 = r8;
                    monitor-enter(r8);
                L_0x0004:
                    r2 = r10.b;	 Catch:{ all -> 0x003c }
                    r2 = r2.c;	 Catch:{ all -> 0x003c }
                    r2 = r2.isEmpty();	 Catch:{ all -> 0x003c }
                    if (r2 != 0) goto L_0x003f;
                L_0x0010:
                    r2 = r10.b;	 Catch:{ all -> 0x003c }
                    r2 = r2.c;	 Catch:{ all -> 0x003c }
                    r3 = 0;
                    r2 = r2.get(r3);	 Catch:{ all -> 0x003c }
                    r0 = r2;
                    r0 = (com.com2us.wrapper.kernel.CEventHandler.a) r0;	 Catch:{ all -> 0x003c }
                    r7 = r0;
                    r2 = r10.b;	 Catch:{ all -> 0x003c }
                    r3 = r7.a;	 Catch:{ all -> 0x003c }
                    r4 = r7.b;	 Catch:{ all -> 0x003c }
                    r5 = r7.c;	 Catch:{ all -> 0x003c }
                    r6 = r7.d;	 Catch:{ all -> 0x003c }
                    r7 = r7.e;	 Catch:{ all -> 0x003c }
                    r2 = r2.a(r3, r4, r5, r6, r7);	 Catch:{ all -> 0x003c }
                    if (r2 != r9) goto L_0x003f;
                L_0x0031:
                    r2 = r10.b;	 Catch:{ all -> 0x003c }
                    r2 = r2.c;	 Catch:{ all -> 0x003c }
                    r3 = 0;
                    r2.remove(r3);	 Catch:{ all -> 0x003c }
                    goto L_0x0004;
                L_0x003c:
                    r2 = move-exception;
                    monitor-exit(r8);	 Catch:{ all -> 0x003c }
                    throw r2;
                L_0x003f:
                    r2 = r10.b;	 Catch:{ all -> 0x003c }
                    r2 = r2.c;	 Catch:{ all -> 0x003c }
                    r2 = r2.isEmpty();	 Catch:{ all -> 0x003c }
                    if (r2 != r9) goto L_0x0053;
                L_0x004b:
                    r2 = r10.b;	 Catch:{ all -> 0x003c }
                    r3 = 0;
                    r2.d = r3;	 Catch:{ all -> 0x003c }
                    monitor-exit(r8);	 Catch:{ all -> 0x003c }
                    return;
                L_0x0053:
                    monitor-exit(r8);	 Catch:{ all -> 0x003c }
                    r2 = 10;
                    java.lang.Thread.sleep(r2);	 Catch:{ Exception -> 0x005a }
                    goto L_0x0001;
                L_0x005a:
                    r2 = move-exception;
                    goto L_0x0001;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.com2us.wrapper.kernel.CEventHandler.1.run():void");
                }
            }).start();
        }
    }

    public void onKernelStateChanged(r rVar) {
        super.onKernelStateChanged(rVar);
        switch (AnonymousClass2.a[rVar.ordinal()]) {
            case o.a /*1*/:
                removeEventAll();
                this.c.clear();
                this.e.clear();
                return;
            default:
                return;
        }
    }

    public synchronized void removeEventAll() {
        nativeRemoveAll();
        this.e.rewind();
    }
}
