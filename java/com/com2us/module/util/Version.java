package com.com2us.module.util;

import java.util.StringTokenizer;

public class Version {
    int build;
    int major;
    int minor;

    public Version(int major, int minor, int build) {
        this.major = major;
        this.minor = minor;
        this.build = build;
    }

    public String toString() {
        return this.major + "." + this.minor + "." + this.build;
    }

    public int compare(int major, int minor, int build) {
        return (((this.major - major) << 24) + ((this.minor - minor) << 16)) + (this.build - build);
    }

    public int compare(Version version) {
        return compare(version.major, version.minor, version.build);
    }

    public int compare(String version) throws Exception {
        StringTokenizer st = new StringTokenizer(version, ".");
        if (st.countTokens() < 3) {
            throw new IllegalArgumentException("wrong version format");
        }
        int major;
        int minor;
        int build;
        if (st.hasMoreElements()) {
            major = Integer.parseInt(st.nextToken());
        } else {
            major = 0;
        }
        if (st.hasMoreElements()) {
            minor = Integer.parseInt(st.nextToken());
        } else {
            minor = 0;
        }
        if (st.hasMoreElements()) {
            build = Integer.parseInt(st.nextToken());
        } else {
            build = 0;
        }
        return compare(major, minor, build);
    }

    public static Version parseVersion(String s) throws Exception {
        int parseInt;
        int i = 0;
        StringTokenizer st = new StringTokenizer(s, ".");
        int parseInt2 = Integer.parseInt(st.nextToken());
        if (st.hasMoreTokens()) {
            parseInt = Integer.parseInt(st.nextToken());
        } else {
            parseInt = 0;
        }
        if (st.hasMoreTokens()) {
            i = Integer.parseInt(st.nextToken());
        }
        return new Version(parseInt2, parseInt, i);
    }
}
