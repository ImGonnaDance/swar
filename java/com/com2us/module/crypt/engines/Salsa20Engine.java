package com.com2us.module.crypt.engines;

import com.com2us.module.crypt.CipherParameters;
import com.com2us.module.crypt.DataLengthException;
import com.com2us.module.crypt.MaxBytesExceededException;
import com.com2us.module.crypt.StreamCipher;
import com.com2us.module.crypt.params.KeyParameter;
import com.com2us.module.crypt.params.ParametersWithIV;
import com.com2us.module.crypt.util.Strings;

public class Salsa20Engine implements StreamCipher {
    private static final byte[] sigma = Strings.toByteArray("expand 32-byte k");
    private static final int stateSize = 16;
    private static final byte[] tau = Strings.toByteArray("expand 16-byte k");
    private int cW0;
    private int cW1;
    private int cW2;
    private int[] engineState = new int[stateSize];
    private int index = 0;
    private boolean initialised = false;
    private byte[] keyStream = new byte[64];
    private byte[] workingIV = null;
    private byte[] workingKey = null;
    private int[] x = new int[stateSize];

    public void init(boolean forEncryption, CipherParameters params) {
        if (params instanceof ParametersWithIV) {
            ParametersWithIV ivParams = (ParametersWithIV) params;
            byte[] iv = ivParams.getIV();
            if (iv == null || iv.length != 8) {
                throw new IllegalArgumentException("Salsa20 requires exactly 8 bytes of IV");
            } else if (ivParams.getParameters() instanceof KeyParameter) {
                this.workingKey = ((KeyParameter) ivParams.getParameters()).getKey();
                this.workingIV = iv;
                setKey(this.workingKey, this.workingIV);
                return;
            } else {
                throw new IllegalArgumentException("Salsa20 Init parameters must include a key");
            }
        }
        throw new IllegalArgumentException("Salsa20 Init parameters must include an IV");
    }

    public String getAlgorithmName() {
        return "Salsa20";
    }

    public byte returnByte(byte in) {
        if (limitExceeded()) {
            throw new MaxBytesExceededException("2^70 byte limit per IV; Change IV");
        }
        if (this.index == 0) {
            salsa20WordToByte(this.engineState, this.keyStream);
            int[] iArr = this.engineState;
            iArr[8] = iArr[8] + 1;
            if (this.engineState[8] == 0) {
                iArr = this.engineState;
                iArr[9] = iArr[9] + 1;
            }
        }
        byte out = (byte) (this.keyStream[this.index] ^ in);
        this.index = (this.index + 1) & 63;
        return out;
    }

    public void processBytes(byte[] in, int inOff, int len, byte[] out, int outOff) {
        if (!this.initialised) {
            throw new IllegalStateException(getAlgorithmName() + " not initialised");
        } else if (inOff + len > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + len > out.length) {
            throw new DataLengthException("output buffer too short");
        } else if (limitExceeded(len)) {
            throw new MaxBytesExceededException("2^70 byte limit per IV would be exceeded; Change IV");
        } else {
            for (int i = 0; i < len; i++) {
                if (this.index == 0) {
                    salsa20WordToByte(this.engineState, this.keyStream);
                    int[] iArr = this.engineState;
                    iArr[8] = iArr[8] + 1;
                    if (this.engineState[8] == 0) {
                        iArr = this.engineState;
                        iArr[9] = iArr[9] + 1;
                    }
                }
                out[i + outOff] = (byte) (this.keyStream[this.index] ^ in[i + inOff]);
                this.index = (this.index + 1) & 63;
            }
        }
    }

    public void reset() {
        setKey(this.workingKey, this.workingIV);
    }

    private void setKey(byte[] keyBytes, byte[] ivBytes) {
        byte[] constants;
        this.workingKey = keyBytes;
        this.workingIV = ivBytes;
        this.index = 0;
        resetCounter();
        int offset = 0;
        this.engineState[1] = byteToIntLittle(this.workingKey, 0);
        this.engineState[2] = byteToIntLittle(this.workingKey, 4);
        this.engineState[3] = byteToIntLittle(this.workingKey, 8);
        this.engineState[4] = byteToIntLittle(this.workingKey, 12);
        if (this.workingKey.length == 32) {
            constants = sigma;
            offset = stateSize;
        } else {
            constants = tau;
        }
        this.engineState[11] = byteToIntLittle(this.workingKey, offset);
        this.engineState[12] = byteToIntLittle(this.workingKey, offset + 4);
        this.engineState[13] = byteToIntLittle(this.workingKey, offset + 8);
        this.engineState[14] = byteToIntLittle(this.workingKey, offset + 12);
        this.engineState[0] = byteToIntLittle(constants, 0);
        this.engineState[5] = byteToIntLittle(constants, 4);
        this.engineState[10] = byteToIntLittle(constants, 8);
        this.engineState[15] = byteToIntLittle(constants, 12);
        this.engineState[6] = byteToIntLittle(this.workingIV, 0);
        this.engineState[7] = byteToIntLittle(this.workingIV, 4);
        int[] iArr = this.engineState;
        this.engineState[9] = 0;
        iArr[8] = 0;
        this.initialised = true;
    }

    private void salsa20WordToByte(int[] input, byte[] output) {
        int i;
        System.arraycopy(input, 0, this.x, 0, input.length);
        for (i = 0; i < 10; i++) {
            int[] iArr = this.x;
            iArr[4] = iArr[4] ^ rotl(this.x[0] + this.x[12], 7);
            iArr = this.x;
            iArr[8] = iArr[8] ^ rotl(this.x[4] + this.x[0], 9);
            iArr = this.x;
            iArr[12] = iArr[12] ^ rotl(this.x[8] + this.x[4], 13);
            iArr = this.x;
            iArr[0] = iArr[0] ^ rotl(this.x[12] + this.x[8], 18);
            iArr = this.x;
            iArr[9] = iArr[9] ^ rotl(this.x[5] + this.x[1], 7);
            iArr = this.x;
            iArr[13] = iArr[13] ^ rotl(this.x[9] + this.x[5], 9);
            iArr = this.x;
            iArr[1] = iArr[1] ^ rotl(this.x[13] + this.x[9], 13);
            iArr = this.x;
            iArr[5] = iArr[5] ^ rotl(this.x[1] + this.x[13], 18);
            iArr = this.x;
            iArr[14] = iArr[14] ^ rotl(this.x[10] + this.x[6], 7);
            iArr = this.x;
            iArr[2] = iArr[2] ^ rotl(this.x[14] + this.x[10], 9);
            iArr = this.x;
            iArr[6] = iArr[6] ^ rotl(this.x[2] + this.x[14], 13);
            iArr = this.x;
            iArr[10] = iArr[10] ^ rotl(this.x[6] + this.x[2], 18);
            iArr = this.x;
            iArr[3] = iArr[3] ^ rotl(this.x[15] + this.x[11], 7);
            iArr = this.x;
            iArr[7] = iArr[7] ^ rotl(this.x[3] + this.x[15], 9);
            iArr = this.x;
            iArr[11] = iArr[11] ^ rotl(this.x[7] + this.x[3], 13);
            iArr = this.x;
            iArr[15] = iArr[15] ^ rotl(this.x[11] + this.x[7], 18);
            iArr = this.x;
            iArr[1] = iArr[1] ^ rotl(this.x[0] + this.x[3], 7);
            iArr = this.x;
            iArr[2] = iArr[2] ^ rotl(this.x[1] + this.x[0], 9);
            iArr = this.x;
            iArr[3] = iArr[3] ^ rotl(this.x[2] + this.x[1], 13);
            iArr = this.x;
            iArr[0] = iArr[0] ^ rotl(this.x[3] + this.x[2], 18);
            iArr = this.x;
            iArr[6] = iArr[6] ^ rotl(this.x[5] + this.x[4], 7);
            iArr = this.x;
            iArr[7] = iArr[7] ^ rotl(this.x[6] + this.x[5], 9);
            iArr = this.x;
            iArr[4] = iArr[4] ^ rotl(this.x[7] + this.x[6], 13);
            iArr = this.x;
            iArr[5] = iArr[5] ^ rotl(this.x[4] + this.x[7], 18);
            iArr = this.x;
            iArr[11] = iArr[11] ^ rotl(this.x[10] + this.x[9], 7);
            iArr = this.x;
            iArr[8] = iArr[8] ^ rotl(this.x[11] + this.x[10], 9);
            iArr = this.x;
            iArr[9] = iArr[9] ^ rotl(this.x[8] + this.x[11], 13);
            iArr = this.x;
            iArr[10] = iArr[10] ^ rotl(this.x[9] + this.x[8], 18);
            iArr = this.x;
            iArr[12] = iArr[12] ^ rotl(this.x[15] + this.x[14], 7);
            iArr = this.x;
            iArr[13] = iArr[13] ^ rotl(this.x[12] + this.x[15], 9);
            iArr = this.x;
            iArr[14] = iArr[14] ^ rotl(this.x[13] + this.x[12], 13);
            iArr = this.x;
            iArr[15] = iArr[15] ^ rotl(this.x[14] + this.x[13], 18);
        }
        int offset = 0;
        for (i = 0; i < stateSize; i++) {
            intToByteLittle(this.x[i] + input[i], output, offset);
            offset += 4;
        }
        for (i = stateSize; i < this.x.length; i++) {
            intToByteLittle(this.x[i], output, offset);
            offset += 4;
        }
    }

    private byte[] intToByteLittle(int x, byte[] out, int off) {
        out[off] = (byte) x;
        out[off + 1] = (byte) (x >>> 8);
        out[off + 2] = (byte) (x >>> stateSize);
        out[off + 3] = (byte) (x >>> 24);
        return out;
    }

    private int rotl(int x, int y) {
        return (x << y) | (x >>> (-y));
    }

    private int byteToIntLittle(byte[] x, int offset) {
        return (((x[offset] & 255) | ((x[offset + 1] & 255) << 8)) | ((x[offset + 2] & 255) << stateSize)) | (x[offset + 3] << 24);
    }

    private void resetCounter() {
        this.cW0 = 0;
        this.cW1 = 0;
        this.cW2 = 0;
    }

    private boolean limitExceeded() {
        this.cW0++;
        if (this.cW0 != 0) {
            return false;
        }
        this.cW1++;
        if (this.cW1 != 0) {
            return false;
        }
        this.cW2++;
        if ((this.cW2 & 32) != 0) {
            return true;
        }
        return false;
    }

    private boolean limitExceeded(int len) {
        if (this.cW0 >= 0) {
            this.cW0 += len;
            return false;
        }
        this.cW0 += len;
        if (this.cW0 < 0) {
            return false;
        }
        this.cW1++;
        if (this.cW1 != 0) {
            return false;
        }
        this.cW2++;
        if ((this.cW2 & 32) != 0) {
            return true;
        }
        return false;
    }
}
