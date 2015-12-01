package com.com2us.common.security.base64;

import com.com2us.module.inapp.DefaultBilling;
import com.com2us.module.manager.ModuleConfig;
import java.io.UnsupportedEncodingException;

public class Base64 {
    static final /* synthetic */ boolean $assertionsDisabled = (!Base64.class.desiredAssertionStatus() ? true : $assertionsDisabled);
    public static final int CRLF = 4;
    public static final int DEFAULT = 0;
    public static final int NO_CLOSE = 16;
    public static final int NO_PADDING = 1;
    public static final int NO_WRAP = 2;
    public static final int URL_SAFE = 8;

    static abstract class Coder {
        public int op;
        public byte[] output;

        public abstract int maxOutputSize(int i);

        public abstract boolean process(byte[] bArr, int i, int i2, boolean z);

        Coder() {
        }
    }

    static class Decoder extends Coder {
        private static final int[] DECODE;
        private static final int[] DECODE_WEBSAFE;
        private static final int EQUALS = -2;
        private static final int SKIP = -1;
        private final int[] alphabet;
        private int state;
        private int value;

        static {
            int[] iArr = new int[ModuleConfig.SOCIAL_MEDIA_MOUDLE];
            iArr[Base64.DEFAULT] = SKIP;
            iArr[Base64.NO_PADDING] = SKIP;
            iArr[Base64.NO_WRAP] = SKIP;
            iArr[3] = SKIP;
            iArr[Base64.CRLF] = SKIP;
            iArr[5] = SKIP;
            iArr[6] = SKIP;
            iArr[7] = SKIP;
            iArr[Base64.URL_SAFE] = SKIP;
            iArr[9] = SKIP;
            iArr[10] = SKIP;
            iArr[11] = SKIP;
            iArr[12] = SKIP;
            iArr[13] = SKIP;
            iArr[14] = SKIP;
            iArr[15] = SKIP;
            iArr[Base64.NO_CLOSE] = SKIP;
            iArr[17] = SKIP;
            iArr[18] = SKIP;
            iArr[19] = SKIP;
            iArr[20] = SKIP;
            iArr[21] = SKIP;
            iArr[22] = SKIP;
            iArr[23] = SKIP;
            iArr[24] = SKIP;
            iArr[25] = SKIP;
            iArr[26] = SKIP;
            iArr[27] = SKIP;
            iArr[28] = SKIP;
            iArr[29] = SKIP;
            iArr[30] = SKIP;
            iArr[31] = SKIP;
            iArr[32] = SKIP;
            iArr[33] = SKIP;
            iArr[34] = SKIP;
            iArr[35] = SKIP;
            iArr[36] = SKIP;
            iArr[37] = SKIP;
            iArr[38] = SKIP;
            iArr[39] = SKIP;
            iArr[40] = SKIP;
            iArr[41] = SKIP;
            iArr[42] = SKIP;
            iArr[43] = 62;
            iArr[44] = SKIP;
            iArr[45] = SKIP;
            iArr[46] = SKIP;
            iArr[47] = 63;
            iArr[48] = 52;
            iArr[49] = 53;
            iArr[50] = 54;
            iArr[51] = 55;
            iArr[52] = 56;
            iArr[53] = 57;
            iArr[54] = 58;
            iArr[55] = 59;
            iArr[56] = 60;
            iArr[57] = 61;
            iArr[58] = SKIP;
            iArr[59] = SKIP;
            iArr[60] = SKIP;
            iArr[61] = EQUALS;
            iArr[62] = SKIP;
            iArr[63] = SKIP;
            iArr[64] = SKIP;
            iArr[66] = Base64.NO_PADDING;
            iArr[67] = Base64.NO_WRAP;
            iArr[68] = 3;
            iArr[69] = Base64.CRLF;
            iArr[70] = 5;
            iArr[71] = 6;
            iArr[72] = 7;
            iArr[73] = Base64.URL_SAFE;
            iArr[74] = 9;
            iArr[75] = 10;
            iArr[76] = 11;
            iArr[77] = 12;
            iArr[78] = 13;
            iArr[79] = 14;
            iArr[80] = 15;
            iArr[81] = Base64.NO_CLOSE;
            iArr[82] = 17;
            iArr[83] = 18;
            iArr[84] = 19;
            iArr[85] = 20;
            iArr[86] = 21;
            iArr[87] = 22;
            iArr[88] = 23;
            iArr[89] = 24;
            iArr[90] = 25;
            iArr[91] = SKIP;
            iArr[92] = SKIP;
            iArr[93] = SKIP;
            iArr[94] = SKIP;
            iArr[95] = SKIP;
            iArr[96] = SKIP;
            iArr[97] = 26;
            iArr[98] = 27;
            iArr[99] = 28;
            iArr[100] = 29;
            iArr[DefaultBilling.AUTHORIZE_LICENSE_FAILED] = 30;
            iArr[102] = 31;
            iArr[103] = 32;
            iArr[104] = 33;
            iArr[105] = 34;
            iArr[106] = 35;
            iArr[107] = 36;
            iArr[108] = 37;
            iArr[109] = 38;
            iArr[110] = 39;
            iArr[111] = 40;
            iArr[112] = 41;
            iArr[113] = 42;
            iArr[114] = 43;
            iArr[115] = 44;
            iArr[116] = 45;
            iArr[117] = 46;
            iArr[118] = 47;
            iArr[119] = 48;
            iArr[120] = 49;
            iArr[121] = 50;
            iArr[122] = 51;
            iArr[123] = SKIP;
            iArr[124] = SKIP;
            iArr[125] = SKIP;
            iArr[126] = SKIP;
            iArr[127] = SKIP;
            iArr[ModuleConfig.ACTIVEUSER_MODULE] = SKIP;
            iArr[129] = SKIP;
            iArr[130] = SKIP;
            iArr[131] = SKIP;
            iArr[132] = SKIP;
            iArr[133] = SKIP;
            iArr[134] = SKIP;
            iArr[135] = SKIP;
            iArr[136] = SKIP;
            iArr[137] = SKIP;
            iArr[138] = SKIP;
            iArr[139] = SKIP;
            iArr[140] = SKIP;
            iArr[141] = SKIP;
            iArr[142] = SKIP;
            iArr[143] = SKIP;
            iArr[144] = SKIP;
            iArr[145] = SKIP;
            iArr[146] = SKIP;
            iArr[147] = SKIP;
            iArr[148] = SKIP;
            iArr[149] = SKIP;
            iArr[150] = SKIP;
            iArr[151] = SKIP;
            iArr[152] = SKIP;
            iArr[153] = SKIP;
            iArr[154] = SKIP;
            iArr[155] = SKIP;
            iArr[156] = SKIP;
            iArr[157] = SKIP;
            iArr[158] = SKIP;
            iArr[159] = SKIP;
            iArr[160] = SKIP;
            iArr[161] = SKIP;
            iArr[162] = SKIP;
            iArr[163] = SKIP;
            iArr[164] = SKIP;
            iArr[165] = SKIP;
            iArr[166] = SKIP;
            iArr[167] = SKIP;
            iArr[168] = SKIP;
            iArr[169] = SKIP;
            iArr[170] = SKIP;
            iArr[171] = SKIP;
            iArr[172] = SKIP;
            iArr[173] = SKIP;
            iArr[174] = SKIP;
            iArr[175] = SKIP;
            iArr[176] = SKIP;
            iArr[177] = SKIP;
            iArr[178] = SKIP;
            iArr[179] = SKIP;
            iArr[180] = SKIP;
            iArr[181] = SKIP;
            iArr[182] = SKIP;
            iArr[183] = SKIP;
            iArr[184] = SKIP;
            iArr[185] = SKIP;
            iArr[186] = SKIP;
            iArr[187] = SKIP;
            iArr[188] = SKIP;
            iArr[189] = SKIP;
            iArr[190] = SKIP;
            iArr[191] = SKIP;
            iArr[192] = SKIP;
            iArr[193] = SKIP;
            iArr[194] = SKIP;
            iArr[195] = SKIP;
            iArr[196] = SKIP;
            iArr[197] = SKIP;
            iArr[198] = SKIP;
            iArr[199] = SKIP;
            iArr[SelectTarget.TARGETING_SUCCESS] = SKIP;
            iArr[SelectTarget.TARGETING_FAILED] = SKIP;
            iArr[202] = SKIP;
            iArr[203] = SKIP;
            iArr[204] = SKIP;
            iArr[205] = SKIP;
            iArr[206] = SKIP;
            iArr[207] = SKIP;
            iArr[208] = SKIP;
            iArr[209] = SKIP;
            iArr[210] = SKIP;
            iArr[211] = SKIP;
            iArr[212] = SKIP;
            iArr[213] = SKIP;
            iArr[214] = SKIP;
            iArr[215] = SKIP;
            iArr[216] = SKIP;
            iArr[217] = SKIP;
            iArr[218] = SKIP;
            iArr[219] = SKIP;
            iArr[220] = SKIP;
            iArr[221] = SKIP;
            iArr[222] = SKIP;
            iArr[223] = SKIP;
            iArr[224] = SKIP;
            iArr[225] = SKIP;
            iArr[226] = SKIP;
            iArr[227] = SKIP;
            iArr[228] = SKIP;
            iArr[229] = SKIP;
            iArr[230] = SKIP;
            iArr[231] = SKIP;
            iArr[232] = SKIP;
            iArr[233] = SKIP;
            iArr[234] = SKIP;
            iArr[235] = SKIP;
            iArr[236] = SKIP;
            iArr[237] = SKIP;
            iArr[238] = SKIP;
            iArr[239] = SKIP;
            iArr[240] = SKIP;
            iArr[241] = SKIP;
            iArr[242] = SKIP;
            iArr[243] = SKIP;
            iArr[244] = SKIP;
            iArr[245] = SKIP;
            iArr[246] = SKIP;
            iArr[247] = SKIP;
            iArr[248] = SKIP;
            iArr[249] = SKIP;
            iArr[250] = SKIP;
            iArr[251] = SKIP;
            iArr[252] = SKIP;
            iArr[253] = SKIP;
            iArr[254] = SKIP;
            iArr[255] = SKIP;
            DECODE = iArr;
            iArr = new int[ModuleConfig.SOCIAL_MEDIA_MOUDLE];
            iArr[Base64.DEFAULT] = SKIP;
            iArr[Base64.NO_PADDING] = SKIP;
            iArr[Base64.NO_WRAP] = SKIP;
            iArr[3] = SKIP;
            iArr[Base64.CRLF] = SKIP;
            iArr[5] = SKIP;
            iArr[6] = SKIP;
            iArr[7] = SKIP;
            iArr[Base64.URL_SAFE] = SKIP;
            iArr[9] = SKIP;
            iArr[10] = SKIP;
            iArr[11] = SKIP;
            iArr[12] = SKIP;
            iArr[13] = SKIP;
            iArr[14] = SKIP;
            iArr[15] = SKIP;
            iArr[Base64.NO_CLOSE] = SKIP;
            iArr[17] = SKIP;
            iArr[18] = SKIP;
            iArr[19] = SKIP;
            iArr[20] = SKIP;
            iArr[21] = SKIP;
            iArr[22] = SKIP;
            iArr[23] = SKIP;
            iArr[24] = SKIP;
            iArr[25] = SKIP;
            iArr[26] = SKIP;
            iArr[27] = SKIP;
            iArr[28] = SKIP;
            iArr[29] = SKIP;
            iArr[30] = SKIP;
            iArr[31] = SKIP;
            iArr[32] = SKIP;
            iArr[33] = SKIP;
            iArr[34] = SKIP;
            iArr[35] = SKIP;
            iArr[36] = SKIP;
            iArr[37] = SKIP;
            iArr[38] = SKIP;
            iArr[39] = SKIP;
            iArr[40] = SKIP;
            iArr[41] = SKIP;
            iArr[42] = SKIP;
            iArr[43] = SKIP;
            iArr[44] = SKIP;
            iArr[45] = 62;
            iArr[46] = SKIP;
            iArr[47] = SKIP;
            iArr[48] = 52;
            iArr[49] = 53;
            iArr[50] = 54;
            iArr[51] = 55;
            iArr[52] = 56;
            iArr[53] = 57;
            iArr[54] = 58;
            iArr[55] = 59;
            iArr[56] = 60;
            iArr[57] = 61;
            iArr[58] = SKIP;
            iArr[59] = SKIP;
            iArr[60] = SKIP;
            iArr[61] = EQUALS;
            iArr[62] = SKIP;
            iArr[63] = SKIP;
            iArr[64] = SKIP;
            iArr[66] = Base64.NO_PADDING;
            iArr[67] = Base64.NO_WRAP;
            iArr[68] = 3;
            iArr[69] = Base64.CRLF;
            iArr[70] = 5;
            iArr[71] = 6;
            iArr[72] = 7;
            iArr[73] = Base64.URL_SAFE;
            iArr[74] = 9;
            iArr[75] = 10;
            iArr[76] = 11;
            iArr[77] = 12;
            iArr[78] = 13;
            iArr[79] = 14;
            iArr[80] = 15;
            iArr[81] = Base64.NO_CLOSE;
            iArr[82] = 17;
            iArr[83] = 18;
            iArr[84] = 19;
            iArr[85] = 20;
            iArr[86] = 21;
            iArr[87] = 22;
            iArr[88] = 23;
            iArr[89] = 24;
            iArr[90] = 25;
            iArr[91] = SKIP;
            iArr[92] = SKIP;
            iArr[93] = SKIP;
            iArr[94] = SKIP;
            iArr[95] = 63;
            iArr[96] = SKIP;
            iArr[97] = 26;
            iArr[98] = 27;
            iArr[99] = 28;
            iArr[100] = 29;
            iArr[DefaultBilling.AUTHORIZE_LICENSE_FAILED] = 30;
            iArr[102] = 31;
            iArr[103] = 32;
            iArr[104] = 33;
            iArr[105] = 34;
            iArr[106] = 35;
            iArr[107] = 36;
            iArr[108] = 37;
            iArr[109] = 38;
            iArr[110] = 39;
            iArr[111] = 40;
            iArr[112] = 41;
            iArr[113] = 42;
            iArr[114] = 43;
            iArr[115] = 44;
            iArr[116] = 45;
            iArr[117] = 46;
            iArr[118] = 47;
            iArr[119] = 48;
            iArr[120] = 49;
            iArr[121] = 50;
            iArr[122] = 51;
            iArr[123] = SKIP;
            iArr[124] = SKIP;
            iArr[125] = SKIP;
            iArr[126] = SKIP;
            iArr[127] = SKIP;
            iArr[ModuleConfig.ACTIVEUSER_MODULE] = SKIP;
            iArr[129] = SKIP;
            iArr[130] = SKIP;
            iArr[131] = SKIP;
            iArr[132] = SKIP;
            iArr[133] = SKIP;
            iArr[134] = SKIP;
            iArr[135] = SKIP;
            iArr[136] = SKIP;
            iArr[137] = SKIP;
            iArr[138] = SKIP;
            iArr[139] = SKIP;
            iArr[140] = SKIP;
            iArr[141] = SKIP;
            iArr[142] = SKIP;
            iArr[143] = SKIP;
            iArr[144] = SKIP;
            iArr[145] = SKIP;
            iArr[146] = SKIP;
            iArr[147] = SKIP;
            iArr[148] = SKIP;
            iArr[149] = SKIP;
            iArr[150] = SKIP;
            iArr[151] = SKIP;
            iArr[152] = SKIP;
            iArr[153] = SKIP;
            iArr[154] = SKIP;
            iArr[155] = SKIP;
            iArr[156] = SKIP;
            iArr[157] = SKIP;
            iArr[158] = SKIP;
            iArr[159] = SKIP;
            iArr[160] = SKIP;
            iArr[161] = SKIP;
            iArr[162] = SKIP;
            iArr[163] = SKIP;
            iArr[164] = SKIP;
            iArr[165] = SKIP;
            iArr[166] = SKIP;
            iArr[167] = SKIP;
            iArr[168] = SKIP;
            iArr[169] = SKIP;
            iArr[170] = SKIP;
            iArr[171] = SKIP;
            iArr[172] = SKIP;
            iArr[173] = SKIP;
            iArr[174] = SKIP;
            iArr[175] = SKIP;
            iArr[176] = SKIP;
            iArr[177] = SKIP;
            iArr[178] = SKIP;
            iArr[179] = SKIP;
            iArr[180] = SKIP;
            iArr[181] = SKIP;
            iArr[182] = SKIP;
            iArr[183] = SKIP;
            iArr[184] = SKIP;
            iArr[185] = SKIP;
            iArr[186] = SKIP;
            iArr[187] = SKIP;
            iArr[188] = SKIP;
            iArr[189] = SKIP;
            iArr[190] = SKIP;
            iArr[191] = SKIP;
            iArr[192] = SKIP;
            iArr[193] = SKIP;
            iArr[194] = SKIP;
            iArr[195] = SKIP;
            iArr[196] = SKIP;
            iArr[197] = SKIP;
            iArr[198] = SKIP;
            iArr[199] = SKIP;
            iArr[SelectTarget.TARGETING_SUCCESS] = SKIP;
            iArr[SelectTarget.TARGETING_FAILED] = SKIP;
            iArr[202] = SKIP;
            iArr[203] = SKIP;
            iArr[204] = SKIP;
            iArr[205] = SKIP;
            iArr[206] = SKIP;
            iArr[207] = SKIP;
            iArr[208] = SKIP;
            iArr[209] = SKIP;
            iArr[210] = SKIP;
            iArr[211] = SKIP;
            iArr[212] = SKIP;
            iArr[213] = SKIP;
            iArr[214] = SKIP;
            iArr[215] = SKIP;
            iArr[216] = SKIP;
            iArr[217] = SKIP;
            iArr[218] = SKIP;
            iArr[219] = SKIP;
            iArr[220] = SKIP;
            iArr[221] = SKIP;
            iArr[222] = SKIP;
            iArr[223] = SKIP;
            iArr[224] = SKIP;
            iArr[225] = SKIP;
            iArr[226] = SKIP;
            iArr[227] = SKIP;
            iArr[228] = SKIP;
            iArr[229] = SKIP;
            iArr[230] = SKIP;
            iArr[231] = SKIP;
            iArr[232] = SKIP;
            iArr[233] = SKIP;
            iArr[234] = SKIP;
            iArr[235] = SKIP;
            iArr[236] = SKIP;
            iArr[237] = SKIP;
            iArr[238] = SKIP;
            iArr[239] = SKIP;
            iArr[240] = SKIP;
            iArr[241] = SKIP;
            iArr[242] = SKIP;
            iArr[243] = SKIP;
            iArr[244] = SKIP;
            iArr[245] = SKIP;
            iArr[246] = SKIP;
            iArr[247] = SKIP;
            iArr[248] = SKIP;
            iArr[249] = SKIP;
            iArr[250] = SKIP;
            iArr[251] = SKIP;
            iArr[252] = SKIP;
            iArr[253] = SKIP;
            iArr[254] = SKIP;
            iArr[255] = SKIP;
            DECODE_WEBSAFE = iArr;
        }

        public Decoder(int flags, byte[] output) {
            this.output = output;
            this.alphabet = (flags & Base64.URL_SAFE) == 0 ? DECODE : DECODE_WEBSAFE;
            this.state = Base64.DEFAULT;
            this.value = Base64.DEFAULT;
        }

        public int maxOutputSize(int len) {
            return ((len * 3) / Base64.CRLF) + 10;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean process(byte[] r12, int r13, int r14, boolean r15) {
            /*
            r11 = this;
            r9 = r11.state;
            r10 = 6;
            if (r9 != r10) goto L_0x0007;
        L_0x0005:
            r9 = 0;
        L_0x0006:
            return r9;
        L_0x0007:
            r5 = r13;
            r14 = r14 + r13;
            r7 = r11.state;
            r8 = r11.value;
            r2 = 0;
            r4 = r11.output;
            r0 = r11.alphabet;
        L_0x0012:
            if (r5 < r14) goto L_0x001f;
        L_0x0014:
            r3 = r2;
        L_0x0015:
            if (r15 != 0) goto L_0x0110;
        L_0x0017:
            r11.state = r7;
            r11.value = r8;
            r11.op = r3;
            r9 = 1;
            goto L_0x0006;
        L_0x001f:
            if (r7 != 0) goto L_0x0069;
        L_0x0021:
            r9 = r5 + 4;
            if (r9 > r14) goto L_0x004f;
        L_0x0025:
            r9 = r12[r5];
            r9 = r9 & 255;
            r9 = r0[r9];
            r9 = r9 << 18;
            r10 = r5 + 1;
            r10 = r12[r10];
            r10 = r10 & 255;
            r10 = r0[r10];
            r10 = r10 << 12;
            r9 = r9 | r10;
            r10 = r5 + 2;
            r10 = r12[r10];
            r10 = r10 & 255;
            r10 = r0[r10];
            r10 = r10 << 6;
            r9 = r9 | r10;
            r10 = r5 + 3;
            r10 = r12[r10];
            r10 = r10 & 255;
            r10 = r0[r10];
            r8 = r9 | r10;
            if (r8 >= 0) goto L_0x0053;
        L_0x004f:
            if (r5 < r14) goto L_0x0069;
        L_0x0051:
            r3 = r2;
            goto L_0x0015;
        L_0x0053:
            r9 = r2 + 2;
            r10 = (byte) r8;
            r4[r9] = r10;
            r9 = r2 + 1;
            r10 = r8 >> 8;
            r10 = (byte) r10;
            r4[r9] = r10;
            r9 = r8 >> 16;
            r9 = (byte) r9;
            r4[r2] = r9;
            r2 = r2 + 3;
            r5 = r5 + 4;
            goto L_0x0021;
        L_0x0069:
            r6 = r5 + 1;
            r9 = r12[r5];
            r9 = r9 & 255;
            r1 = r0[r9];
            switch(r7) {
                case 0: goto L_0x0076;
                case 1: goto L_0x0085;
                case 2: goto L_0x0098;
                case 3: goto L_0x00bb;
                case 4: goto L_0x00f6;
                case 5: goto L_0x0107;
                default: goto L_0x0074;
            };
        L_0x0074:
            r5 = r6;
            goto L_0x0012;
        L_0x0076:
            if (r1 < 0) goto L_0x007d;
        L_0x0078:
            r8 = r1;
            r7 = r7 + 1;
            r5 = r6;
            goto L_0x0012;
        L_0x007d:
            r9 = -1;
            if (r1 == r9) goto L_0x0074;
        L_0x0080:
            r9 = 6;
            r11.state = r9;
            r9 = 0;
            goto L_0x0006;
        L_0x0085:
            if (r1 < 0) goto L_0x008f;
        L_0x0087:
            r9 = r8 << 6;
            r8 = r9 | r1;
            r7 = r7 + 1;
            r5 = r6;
            goto L_0x0012;
        L_0x008f:
            r9 = -1;
            if (r1 == r9) goto L_0x0074;
        L_0x0092:
            r9 = 6;
            r11.state = r9;
            r9 = 0;
            goto L_0x0006;
        L_0x0098:
            if (r1 < 0) goto L_0x00a3;
        L_0x009a:
            r9 = r8 << 6;
            r8 = r9 | r1;
            r7 = r7 + 1;
            r5 = r6;
            goto L_0x0012;
        L_0x00a3:
            r9 = -2;
            if (r1 != r9) goto L_0x00b2;
        L_0x00a6:
            r3 = r2 + 1;
            r9 = r8 >> 4;
            r9 = (byte) r9;
            r4[r2] = r9;
            r7 = 4;
            r2 = r3;
            r5 = r6;
            goto L_0x0012;
        L_0x00b2:
            r9 = -1;
            if (r1 == r9) goto L_0x0074;
        L_0x00b5:
            r9 = 6;
            r11.state = r9;
            r9 = 0;
            goto L_0x0006;
        L_0x00bb:
            if (r1 < 0) goto L_0x00d8;
        L_0x00bd:
            r9 = r8 << 6;
            r8 = r9 | r1;
            r9 = r2 + 2;
            r10 = (byte) r8;
            r4[r9] = r10;
            r9 = r2 + 1;
            r10 = r8 >> 8;
            r10 = (byte) r10;
            r4[r9] = r10;
            r9 = r8 >> 16;
            r9 = (byte) r9;
            r4[r2] = r9;
            r2 = r2 + 3;
            r7 = 0;
            r5 = r6;
            goto L_0x0012;
        L_0x00d8:
            r9 = -2;
            if (r1 != r9) goto L_0x00ed;
        L_0x00db:
            r9 = r2 + 1;
            r10 = r8 >> 2;
            r10 = (byte) r10;
            r4[r9] = r10;
            r9 = r8 >> 10;
            r9 = (byte) r9;
            r4[r2] = r9;
            r2 = r2 + 2;
            r7 = 5;
            r5 = r6;
            goto L_0x0012;
        L_0x00ed:
            r9 = -1;
            if (r1 == r9) goto L_0x0074;
        L_0x00f0:
            r9 = 6;
            r11.state = r9;
            r9 = 0;
            goto L_0x0006;
        L_0x00f6:
            r9 = -2;
            if (r1 != r9) goto L_0x00fe;
        L_0x00f9:
            r7 = r7 + 1;
            r5 = r6;
            goto L_0x0012;
        L_0x00fe:
            r9 = -1;
            if (r1 == r9) goto L_0x0074;
        L_0x0101:
            r9 = 6;
            r11.state = r9;
            r9 = 0;
            goto L_0x0006;
        L_0x0107:
            r9 = -1;
            if (r1 == r9) goto L_0x0074;
        L_0x010a:
            r9 = 6;
            r11.state = r9;
            r9 = 0;
            goto L_0x0006;
        L_0x0110:
            switch(r7) {
                case 0: goto L_0x011b;
                case 1: goto L_0x011d;
                case 2: goto L_0x0123;
                case 3: goto L_0x012b;
                case 4: goto L_0x013b;
                default: goto L_0x0113;
            };
        L_0x0113:
            r2 = r3;
        L_0x0114:
            r11.state = r7;
            r11.op = r2;
            r9 = 1;
            goto L_0x0006;
        L_0x011b:
            r2 = r3;
            goto L_0x0114;
        L_0x011d:
            r9 = 6;
            r11.state = r9;
            r9 = 0;
            goto L_0x0006;
        L_0x0123:
            r2 = r3 + 1;
            r9 = r8 >> 4;
            r9 = (byte) r9;
            r4[r3] = r9;
            goto L_0x0114;
        L_0x012b:
            r2 = r3 + 1;
            r9 = r8 >> 10;
            r9 = (byte) r9;
            r4[r3] = r9;
            r3 = r2 + 1;
            r9 = r8 >> 2;
            r9 = (byte) r9;
            r4[r2] = r9;
            r2 = r3;
            goto L_0x0114;
        L_0x013b:
            r9 = 6;
            r11.state = r9;
            r9 = 0;
            goto L_0x0006;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.com2us.common.security.base64.Base64.Decoder.process(byte[], int, int, boolean):boolean");
        }
    }

    static class Encoder extends Coder {
        static final /* synthetic */ boolean $assertionsDisabled;
        private static final byte[] ENCODE = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 43, (byte) 47};
        private static final byte[] ENCODE_WEBSAFE = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 45, (byte) 95};
        public static final int LINE_GROUPS = 19;
        private final byte[] alphabet;
        private int count;
        public final boolean do_cr;
        public final boolean do_newline;
        public final boolean do_padding;
        private final byte[] tail;
        int tailLen;

        static {
            boolean z;
            if (Base64.class.desiredAssertionStatus()) {
                z = $assertionsDisabled;
            } else {
                z = true;
            }
            $assertionsDisabled = z;
        }

        public Encoder(int flags, byte[] output) {
            boolean z;
            boolean z2 = true;
            this.output = output;
            this.do_padding = (flags & Base64.NO_PADDING) == 0 ? true : $assertionsDisabled;
            if ((flags & Base64.NO_WRAP) == 0) {
                z = true;
            } else {
                z = $assertionsDisabled;
            }
            this.do_newline = z;
            if ((flags & Base64.CRLF) == 0) {
                z2 = $assertionsDisabled;
            }
            this.do_cr = z2;
            this.alphabet = (flags & Base64.URL_SAFE) == 0 ? ENCODE : ENCODE_WEBSAFE;
            this.tail = new byte[Base64.NO_WRAP];
            this.tailLen = Base64.DEFAULT;
            this.count = this.do_newline ? LINE_GROUPS : -1;
        }

        public int maxOutputSize(int len) {
            return ((len * Base64.URL_SAFE) / 5) + 10;
        }

        public boolean process(byte[] r15, int r16, int r17, boolean r18) {
            /* JADX: method processing error */
/*
            Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:42)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:66)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:38)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:60)
	at jadx.core.ProcessClass.process(ProcessClass.java:43)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:292)
	at jadx.api.JavaClass.decompile(JavaClass.java:63)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:167)
*/
            /*
            r14 = this;
            r1 = r14.alphabet;
            r5 = r14.output;
            r3 = 0;
            r2 = r14.count;
            r6 = r16;
            r17 = r17 + r16;
            r10 = -1;
            r11 = r14.tailLen;
            switch(r11) {
                case 0: goto L_0x0011;
                case 1: goto L_0x00bf;
                case 2: goto L_0x00e4;
                default: goto L_0x0011;
            };
        L_0x0011:
            r11 = -1;
            if (r10 == r11) goto L_0x024b;
        L_0x0014:
            r4 = r3 + 1;
            r11 = r10 >> 18;
            r11 = r11 & 63;
            r11 = r1[r11];
            r5[r3] = r11;
            r3 = r4 + 1;
            r11 = r10 >> 12;
            r11 = r11 & 63;
            r11 = r1[r11];
            r5[r4] = r11;
            r4 = r3 + 1;
            r11 = r10 >> 6;
            r11 = r11 & 63;
            r11 = r1[r11];
            r5[r3] = r11;
            r3 = r4 + 1;
            r11 = r10 & 63;
            r11 = r1[r11];
            r5[r4] = r11;
            r2 = r2 + -1;
            if (r2 != 0) goto L_0x024b;
        L_0x003e:
            r11 = r14.do_cr;
            if (r11 == 0) goto L_0x0049;
        L_0x0042:
            r4 = r3 + 1;
            r11 = 13;
            r5[r3] = r11;
            r3 = r4;
        L_0x0049:
            r4 = r3 + 1;
            r11 = 10;
            r5[r3] = r11;
            r2 = 19;
            r7 = r6;
        L_0x0052:
            r11 = r7 + 3;
            r0 = r17;
            if (r11 <= r0) goto L_0x010b;
        L_0x0058:
            if (r18 == 0) goto L_0x0210;
        L_0x005a:
            r11 = r14.tailLen;
            r11 = r7 - r11;
            r12 = r17 + -1;
            if (r11 != r12) goto L_0x016a;
        L_0x0062:
            r8 = 0;
            r11 = r14.tailLen;
            if (r11 <= 0) goto L_0x0164;
        L_0x0067:
            r11 = r14.tail;
            r9 = r8 + 1;
            r11 = r11[r8];
            r8 = r9;
            r6 = r7;
        L_0x006f:
            r11 = r11 & 255;
            r10 = r11 << 4;
            r11 = r14.tailLen;
            r11 = r11 - r8;
            r14.tailLen = r11;
            r3 = r4 + 1;
            r11 = r10 >> 6;
            r11 = r11 & 63;
            r11 = r1[r11];
            r5[r4] = r11;
            r4 = r3 + 1;
            r11 = r10 & 63;
            r11 = r1[r11];
            r5[r3] = r11;
            r11 = r14.do_padding;
            if (r11 == 0) goto L_0x009a;
        L_0x008e:
            r3 = r4 + 1;
            r11 = 61;
            r5[r4] = r11;
            r4 = r3 + 1;
            r11 = 61;
            r5[r3] = r11;
        L_0x009a:
            r3 = r4;
            r11 = r14.do_newline;
            if (r11 == 0) goto L_0x00b1;
        L_0x009f:
            r11 = r14.do_cr;
            if (r11 == 0) goto L_0x00aa;
        L_0x00a3:
            r4 = r3 + 1;
            r11 = 13;
            r5[r3] = r11;
            r3 = r4;
        L_0x00aa:
            r4 = r3 + 1;
            r11 = 10;
            r5[r3] = r11;
            r3 = r4;
        L_0x00b1:
            r11 = $assertionsDisabled;
            if (r11 != 0) goto L_0x0202;
        L_0x00b5:
            r11 = r14.tailLen;
            if (r11 == 0) goto L_0x0202;
        L_0x00b9:
            r11 = new java.lang.AssertionError;
            r11.<init>();
            throw r11;
        L_0x00bf:
            r11 = r6 + 2;
            r0 = r17;
            if (r11 > r0) goto L_0x0011;
        L_0x00c5:
            r11 = r14.tail;
            r12 = 0;
            r11 = r11[r12];
            r11 = r11 & 255;
            r11 = r11 << 16;
            r7 = r6 + 1;
            r12 = r15[r6];
            r12 = r12 & 255;
            r12 = r12 << 8;
            r11 = r11 | r12;
            r6 = r7 + 1;
            r12 = r15[r7];
            r12 = r12 & 255;
            r10 = r11 | r12;
            r11 = 0;
            r14.tailLen = r11;
            goto L_0x0011;
        L_0x00e4:
            r11 = r6 + 1;
            r0 = r17;
            if (r11 > r0) goto L_0x0011;
        L_0x00ea:
            r11 = r14.tail;
            r12 = 0;
            r11 = r11[r12];
            r11 = r11 & 255;
            r11 = r11 << 16;
            r12 = r14.tail;
            r13 = 1;
            r12 = r12[r13];
            r12 = r12 & 255;
            r12 = r12 << 8;
            r11 = r11 | r12;
            r7 = r6 + 1;
            r12 = r15[r6];
            r12 = r12 & 255;
            r10 = r11 | r12;
            r11 = 0;
            r14.tailLen = r11;
            r6 = r7;
            goto L_0x0011;
        L_0x010b:
            r11 = r15[r7];
            r11 = r11 & 255;
            r11 = r11 << 16;
            r12 = r7 + 1;
            r12 = r15[r12];
            r12 = r12 & 255;
            r12 = r12 << 8;
            r11 = r11 | r12;
            r12 = r7 + 2;
            r12 = r15[r12];
            r12 = r12 & 255;
            r10 = r11 | r12;
            r11 = r10 >> 18;
            r11 = r11 & 63;
            r11 = r1[r11];
            r5[r4] = r11;
            r11 = r4 + 1;
            r12 = r10 >> 12;
            r12 = r12 & 63;
            r12 = r1[r12];
            r5[r11] = r12;
            r11 = r4 + 2;
            r12 = r10 >> 6;
            r12 = r12 & 63;
            r12 = r1[r12];
            r5[r11] = r12;
            r11 = r4 + 3;
            r12 = r10 & 63;
            r12 = r1[r12];
            r5[r11] = r12;
            r6 = r7 + 3;
            r3 = r4 + 4;
            r2 = r2 + -1;
            if (r2 != 0) goto L_0x024b;
        L_0x014e:
            r11 = r14.do_cr;
            if (r11 == 0) goto L_0x0159;
        L_0x0152:
            r4 = r3 + 1;
            r11 = 13;
            r5[r3] = r11;
            r3 = r4;
        L_0x0159:
            r4 = r3 + 1;
            r11 = 10;
            r5[r3] = r11;
            r2 = 19;
            r7 = r6;
            goto L_0x0052;
        L_0x0164:
            r6 = r7 + 1;
            r11 = r15[r7];
            goto L_0x006f;
        L_0x016a:
            r11 = r14.tailLen;
            r11 = r7 - r11;
            r12 = r17 + -2;
            if (r11 != r12) goto L_0x01e4;
        L_0x0172:
            r8 = 0;
            r11 = r14.tailLen;
            r12 = 1;
            if (r11 <= r12) goto L_0x01d9;
        L_0x0178:
            r11 = r14.tail;
            r9 = r8 + 1;
            r11 = r11[r8];
            r8 = r9;
            r6 = r7;
        L_0x0180:
            r11 = r11 & 255;
            r12 = r11 << 10;
            r11 = r14.tailLen;
            if (r11 <= 0) goto L_0x01de;
        L_0x0188:
            r11 = r14.tail;
            r9 = r8 + 1;
            r11 = r11[r8];
            r8 = r9;
        L_0x018f:
            r11 = r11 & 255;
            r11 = r11 << 2;
            r10 = r12 | r11;
            r11 = r14.tailLen;
            r11 = r11 - r8;
            r14.tailLen = r11;
            r3 = r4 + 1;
            r11 = r10 >> 12;
            r11 = r11 & 63;
            r11 = r1[r11];
            r5[r4] = r11;
            r4 = r3 + 1;
            r11 = r10 >> 6;
            r11 = r11 & 63;
            r11 = r1[r11];
            r5[r3] = r11;
            r3 = r4 + 1;
            r11 = r10 & 63;
            r11 = r1[r11];
            r5[r4] = r11;
            r11 = r14.do_padding;
            if (r11 == 0) goto L_0x01c1;
        L_0x01ba:
            r4 = r3 + 1;
            r11 = 61;
            r5[r3] = r11;
            r3 = r4;
        L_0x01c1:
            r11 = r14.do_newline;
            if (r11 == 0) goto L_0x00b1;
        L_0x01c5:
            r11 = r14.do_cr;
            if (r11 == 0) goto L_0x01d0;
        L_0x01c9:
            r4 = r3 + 1;
            r11 = 13;
            r5[r3] = r11;
            r3 = r4;
        L_0x01d0:
            r4 = r3 + 1;
            r11 = 10;
            r5[r3] = r11;
            r3 = r4;
            goto L_0x00b1;
        L_0x01d9:
            r6 = r7 + 1;
            r11 = r15[r7];
            goto L_0x0180;
        L_0x01de:
            r7 = r6 + 1;
            r11 = r15[r6];
            r6 = r7;
            goto L_0x018f;
        L_0x01e4:
            r11 = r14.do_newline;
            if (r11 == 0) goto L_0x01fe;
        L_0x01e8:
            if (r4 <= 0) goto L_0x01fe;
        L_0x01ea:
            r11 = 19;
            if (r2 == r11) goto L_0x01fe;
        L_0x01ee:
            r11 = r14.do_cr;
            if (r11 == 0) goto L_0x0249;
        L_0x01f2:
            r3 = r4 + 1;
            r11 = 13;
            r5[r4] = r11;
        L_0x01f8:
            r4 = r3 + 1;
            r11 = 10;
            r5[r3] = r11;
        L_0x01fe:
            r6 = r7;
            r3 = r4;
            goto L_0x00b1;
        L_0x0202:
            r11 = $assertionsDisabled;
            if (r11 != 0) goto L_0x0222;
        L_0x0206:
            r0 = r17;
            if (r6 == r0) goto L_0x0222;
        L_0x020a:
            r11 = new java.lang.AssertionError;
            r11.<init>();
            throw r11;
        L_0x0210:
            r11 = r17 + -1;
            if (r7 != r11) goto L_0x0228;
        L_0x0214:
            r11 = r14.tail;
            r12 = r14.tailLen;
            r13 = r12 + 1;
            r14.tailLen = r13;
            r13 = r15[r7];
            r11[r12] = r13;
            r6 = r7;
            r3 = r4;
        L_0x0222:
            r14.op = r3;
            r14.count = r2;
            r11 = 1;
            return r11;
        L_0x0228:
            r11 = r17 + -2;
            if (r7 != r11) goto L_0x0246;
        L_0x022c:
            r11 = r14.tail;
            r12 = r14.tailLen;
            r13 = r12 + 1;
            r14.tailLen = r13;
            r13 = r15[r7];
            r11[r12] = r13;
            r11 = r14.tail;
            r12 = r14.tailLen;
            r13 = r12 + 1;
            r14.tailLen = r13;
            r13 = r7 + 1;
            r13 = r15[r13];
            r11[r12] = r13;
        L_0x0246:
            r6 = r7;
            r3 = r4;
            goto L_0x0222;
        L_0x0249:
            r3 = r4;
            goto L_0x01f8;
        L_0x024b:
            r7 = r6;
            r4 = r3;
            goto L_0x0052;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.com2us.common.security.base64.Base64.Encoder.process(byte[], int, int, boolean):boolean");
        }
    }

    public static byte[] decode(String str, int flags) {
        try {
            return decode(str.getBytes("UTF-8"), flags);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decode(byte[] input, int flags) {
        return decode(input, DEFAULT, input.length, flags);
    }

    public static byte[] decode(byte[] input, int offset, int len, int flags) {
        Decoder decoder = new Decoder(flags, new byte[((len * 3) / CRLF)]);
        if (!decoder.process(input, offset, len, true)) {
            throw new IllegalArgumentException("bad base-64");
        } else if (decoder.op == decoder.output.length) {
            return decoder.output;
        } else {
            byte[] temp = new byte[decoder.op];
            System.arraycopy(decoder.output, DEFAULT, temp, DEFAULT, decoder.op);
            return temp;
        }
    }

    public static String encodeToString(byte[] input, int flags) {
        try {
            return new String(encode(input, flags), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static String encodeToString(byte[] input, int offset, int len, int flags) {
        try {
            return new String(encode(input, offset, len, flags), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static byte[] encode(byte[] input, int flags) {
        return encode(input, DEFAULT, input.length, flags);
    }

    public static byte[] encode(byte[] input, int offset, int len, int flags) {
        Encoder encoder = new Encoder(flags, null);
        int output_len = (len / 3) * CRLF;
        if (!encoder.do_padding) {
            switch (len % 3) {
                case DEFAULT /*0*/:
                    break;
                case NO_PADDING /*1*/:
                    output_len += NO_WRAP;
                    break;
                case NO_WRAP /*2*/:
                    output_len += 3;
                    break;
                default:
                    break;
            }
        } else if (len % 3 > 0) {
            output_len += CRLF;
        }
        if (encoder.do_newline && len > 0) {
            int i;
            int i2 = ((len - 1) / 57) + NO_PADDING;
            if (encoder.do_cr) {
                i = NO_WRAP;
            } else {
                i = NO_PADDING;
            }
            output_len += i * i2;
        }
        encoder.output = new byte[output_len];
        encoder.process(input, offset, len, true);
        if ($assertionsDisabled || encoder.op == output_len) {
            return encoder.output;
        }
        throw new AssertionError();
    }

    private Base64() {
    }
}
