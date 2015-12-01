package defpackage;

import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import java.util.LinkedList;

class g$a implements OnLoadCompleteListener {
    private LinkedList<g> a;

    private g$a() {
        this.a = new LinkedList();
    }

    public void a() {
        this.a.clear();
    }

    public void a(g gVar) {
        this.a.add(gVar);
    }

    public void b(g gVar) {
        this.a.remove(gVar);
    }

    public synchronized void onLoadComplete(SoundPool soundPool, int i, int i2) {
        for (int i3 = 0; i3 < this.a.size(); i3++) {
            ((g) this.a.get(i3)).b(i);
        }
    }
}
