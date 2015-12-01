package com.com2us.module.util;

import java.util.StringTokenizer;

public class VersionEx extends Version {
    private int revision;
    private String status;

    public VersionEx(int major, int minor, int build, String status, int revision) {
        super(major, minor, build);
        this.status = status;
        this.revision = revision;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (this.status != null) {
            sb.append('_').append(this.status);
        }
        if (this.revision != 0) {
            sb.append('_').append(this.revision);
        }
        return sb.toString();
    }

    public int compare(String version) throws Exception {
        int _revision = 0;
        int idx = version.indexOf(95);
        if (idx <= 0) {
            return -1;
        }
        version = version.substring(0, idx);
        int ret = super.compare(version);
        if (ret != 0) {
            return ret;
        }
        StringTokenizer st = new StringTokenizer(version.substring(idx + 1), "_");
        String _status = st.hasMoreElements() ? st.nextToken() : a.d;
        if (st.hasMoreElements()) {
            _revision = Integer.parseInt(st.nextToken());
        }
        ret = this.status.compareTo(_status);
        if (ret == 0) {
            return this.revision - _revision;
        }
        return ret;
    }
}
