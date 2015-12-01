package it.partytrack.sdk;

public class Version {
    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void main(java.lang.String[] r6) {
        /*
        r1 = 0;
        r0 = it.partytrack.sdk.Version.class;
        r2 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r3 = it.partytrack.sdk.Version.class;
        r3 = r3.getSimpleName();	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r3 = java.lang.String.valueOf(r3);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r2.<init>(r3);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r3 = ".class";
        r2 = r2.append(r3);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r2 = r2.toString();	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r0 = r0.getResource(r2);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r0 = r0.toExternalForm();	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r2 = 0;
        r3 = it.partytrack.sdk.Version.class;
        r3 = r3.getName();	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r4 = ".";
        r5 = "/";
        r3 = r3.replace(r4, r5);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r3 = r0.lastIndexOf(r3);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r0 = r0.substring(r2, r3);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r2 = new java.net.URL;	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r3 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r0 = java.lang.String.valueOf(r0);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r3.<init>(r0);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r0 = "META-INF/MANIFEST.MF";
        r0 = r3.append(r0);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r0 = r0.toString();	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r2.<init>(r0);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r1 = r2.openStream();	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r0 = new java.util.jar.Manifest;	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r0.<init>(r1);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r0 = r0.getMainAttributes();	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r2 = "Built-Date";
        r0 = r0.getValue(r2);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r2 = "1.10.0";
        r3 = java.lang.System.out;	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r4 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r5 = "Version : ";
        r4.<init>(r5);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r2 = r4.append(r2);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r2 = r2.toString();	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r3.println(r2);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r2 = java.lang.System.out;	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r3 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r4 = "Built-Date: ";
        r3.<init>(r4);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r0 = r3.append(r0);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r0 = r0.toString();	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r2.println(r0);	 Catch:{ FileNotFoundException -> 0x0094, MalformedURLException -> 0x00a1, IOException -> 0x00ae }
        r1.close();	 Catch:{ IOException -> 0x00c5 }
    L_0x0093:
        return;
    L_0x0094:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x00bb }
        r1.close();	 Catch:{ IOException -> 0x009c }
        goto L_0x0093;
    L_0x009c:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0093;
    L_0x00a1:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x00bb }
        r1.close();	 Catch:{ IOException -> 0x00a9 }
        goto L_0x0093;
    L_0x00a9:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0093;
    L_0x00ae:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x00bb }
        r1.close();	 Catch:{ IOException -> 0x00b6 }
        goto L_0x0093;
    L_0x00b6:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0093;
    L_0x00bb:
        r0 = move-exception;
        r1.close();	 Catch:{ IOException -> 0x00c0 }
    L_0x00bf:
        throw r0;
    L_0x00c0:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00bf;
    L_0x00c5:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0093;
        */
        throw new UnsupportedOperationException("Method not decompiled: it.partytrack.sdk.Version.main(java.lang.String[]):void");
    }
}
