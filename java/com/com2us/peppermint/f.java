package com.com2us.peppermint;

class f implements Runnable {
    final /* synthetic */ Peppermint a;
    private final /* synthetic */ PeppermintCallback f14a;
    private final /* synthetic */ String f15a;
    private final /* synthetic */ boolean f16a;

    f(Peppermint peppermint, String str, boolean z, PeppermintCallback peppermintCallback) {
        this.a = peppermint;
        this.f15a = str;
        this.f16a = z;
        this.f14a = peppermintCallback;
    }

    public void run() {
        this.a.f10a = new PeppermintDialog(this.a, this.a.f9a, this.f15a, this.f16a, this.f14a, new g(this));
        this.a.f9a.show();
    }
}
