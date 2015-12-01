package defpackage;

import android.graphics.Point;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.com2us.wrapper.function.CFunction;
import com.com2us.wrapper.ui.CTextInput.b;
import jp.co.cyberz.fox.a.a.i;
import jp.co.cyberz.fox.notify.a;

public class h {
    private int a = -1;
    private FrameLayout b = null;
    private FrameLayout c = null;
    private EditText d = null;
    private TextWatcher e = null;
    private String f = i.a;
    private int g = a.j;
    private int h = 3;
    private int i = 2;
    private int[] j = new int[4];
    private int k = -16777216;
    private int l = -2;
    private int m = 0;
    private int n = 1;
    private boolean o = false;
    private boolean p = false;
    private int q;
    private int r;
    private int s = 0;
    private String t = i.a;
    private boolean u = false;
    private b v = null;
    private InputFilter[] w;

    public h(int i, FrameLayout frameLayout, b bVar, int[] iArr, int i2) {
        this.a = i;
        this.b = frameLayout;
        this.v = bVar;
        this.w = new InputFilter[1];
        Point convertOriginaltoDisplay = CFunction.convertOriginaltoDisplay(new Point(13, 13));
        int i3 = convertOriginaltoDisplay.x;
        int i4 = convertOriginaltoDisplay.y;
        if (i3 >= i4) {
            i3 = i4;
        }
        this.q = i3;
        a(iArr, i2);
    }

    private void a(int[] iArr, int i) {
        this.j = iArr;
        this.r = i;
        CFunction.runOnUiThread(new h$1(this, iArr));
        CFunction.runOnUiThread(new h$12(this, i));
        CFunction.runOnUiThread(new h$13(this));
    }

    public void a() {
        CFunction.runOnUiThread(new h$14(this));
    }

    public void a(int i) {
        this.g = i;
        CFunction.runOnUiThread(new h$16(this, i));
    }

    public void a(String str) {
        this.f = str;
        CFunction.runOnUiThread(new h$15(this, str));
    }

    public void a(boolean z) {
        this.o = z;
        CFunction.runOnUiThread(new h$6(this, z));
    }

    public void a(int[] iArr) {
        this.j = iArr;
        CFunction.runOnUiThread(new h$19(this, iArr));
    }

    public String b() {
        return this.f;
    }

    public void b(int i) {
        this.h = i;
        CFunction.runOnUiThread(new h$17(this, i));
    }

    public void b(String str) {
        this.t = str;
        CFunction.runOnUiThread(new h$10(this, str));
    }

    public void b(boolean z) {
        this.p = z;
        CFunction.runOnUiThread(new h$7(this, z));
    }

    public int c() {
        return this.f == null ? 0 : this.f.length();
    }

    public void c(int i) {
        this.i = i;
        CFunction.runOnUiThread(new h$18(this, i));
    }

    public void c(boolean z) {
        this.u = z;
    }

    public int d() {
        return this.f == null ? 0 : this.f.getBytes().length;
    }

    public void d(int i) {
        this.k = i;
        CFunction.runOnUiThread(new h$2(this, i));
    }

    public void d(boolean z) {
        CFunction.runOnUiThread(new h$11(this, z));
    }

    public int e() {
        return this.g;
    }

    public void e(int i) {
        this.l = i;
        CFunction.runOnUiThread(new h$3(this, i));
    }

    public int f() {
        return this.h;
    }

    public void f(int i) {
        this.m = i;
        CFunction.runOnUiThread(new h$4(this, i));
    }

    public int g() {
        return this.i;
    }

    public void g(int i) {
        this.n = i;
        CFunction.runOnUiThread(new h$5(this, i));
    }

    public void h(int i) {
        this.q = i;
        CFunction.runOnUiThread(new h$8(this, i));
    }

    public int[] h() {
        return this.j;
    }

    public int i() {
        return this.k;
    }

    public void i(int i) {
        this.s = i;
        CFunction.runOnUiThread(new h$9(this, i));
    }

    public int j() {
        return this.l;
    }

    public int k() {
        return this.m;
    }

    public int l() {
        return this.n;
    }

    public boolean m() {
        return this.o;
    }

    public boolean n() {
        return this.p;
    }

    public int o() {
        return this.q;
    }

    public int p() {
        return this.s;
    }

    public String q() {
        return this.t;
    }

    public boolean r() {
        return this.u;
    }

    public int s() {
        return this.r;
    }

    public void t() {
        this.v.a(this.a, 3);
        if (!this.u) {
            b(false);
        }
    }
}
