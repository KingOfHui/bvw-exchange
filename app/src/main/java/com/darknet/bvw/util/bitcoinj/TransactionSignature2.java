//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.darknet.bvw.util.bitcoinj;

import com.google.common.base.Preconditions;

import org.bitcoinj.core.SignatureDecodeException;
import org.bitcoinj.core.Transaction.SigHash;
import org.bitcoinj.core.VerificationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

public class TransactionSignature2 extends ECKey2.ECDSASignature {
    public final int sighashFlags;

    public TransactionSignature2(BigInteger r, BigInteger s) {
        this(r, s, SigHash.ALL.value);
    }

    public TransactionSignature2(BigInteger r, BigInteger s, int sighashFlags) {
        super(r, s);
        this.sighashFlags = sighashFlags;
    }

    public TransactionSignature2(ECKey2.ECDSASignature signature, SigHash mode, boolean anyoneCanPay) {
        super(signature.r, signature.s);
        this.sighashFlags = calcSigHashValue(mode, anyoneCanPay);
    }

    public static TransactionSignature2 dummy() {
        BigInteger val = ECKey2.HALF_CURVE_ORDER;
        return new TransactionSignature2(val, val);
    }

    public static int calcSigHashValue(SigHash mode, boolean anyoneCanPay) {
        Preconditions.checkArgument(SigHash.ALL == mode || SigHash.NONE == mode || SigHash.SINGLE == mode);
        int sighashFlags = mode.value;
        if (anyoneCanPay) {
            sighashFlags |= SigHash.ANYONECANPAY.value;
        }

        return sighashFlags;
    }

    public static boolean isEncodingCanonical(byte[] signature) {
        if (signature.length >= 9 && signature.length <= 73) {
            int hashType = signature[signature.length - 1] & 255 & ~SigHash.ANYONECANPAY.value;
            if (hashType >= SigHash.ALL.value && hashType <= SigHash.SINGLE.value) {
                if ((signature[0] & 255) == 48 && (signature[1] & 255) == signature.length - 3) {
                    int lenR = signature[3] & 255;
                    if (5 + lenR < signature.length && lenR != 0) {
                        int lenS = signature[5 + lenR] & 255;
                        if (lenR + lenS + 7 == signature.length && lenS != 0) {
                            if (signature[2] == 2 && (signature[4] & 128) != 128) {
                                if (lenR > 1 && signature[4] == 0 && (signature[5] & 128) != 128) {
                                    return false;
                                } else if (signature[6 + lenR - 2] == 2 && (signature[6 + lenR] & 128) != 128) {
                                    return lenS <= 1 || signature[6 + lenR] != 0 || (signature[6 + lenR + 1] & 128) == 128;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean anyoneCanPay() {
        return (this.sighashFlags & SigHash.ANYONECANPAY.value) != 0;
    }

    public SigHash sigHashMode() {
        int mode = this.sighashFlags & 31;
        if (mode == SigHash.NONE.value) {
            return SigHash.NONE;
        } else {
            return mode == SigHash.SINGLE.value ? SigHash.SINGLE : SigHash.ALL;
        }
    }

    public byte[] encodeToBitcoin() {
        try {
            ByteArrayOutputStream bos = this.derByteStream();
            bos.write(this.sighashFlags);
            return bos.toByteArray();
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public ECKey2.ECDSASignature toCanonicalised() {
        return new TransactionSignature2(super.toCanonicalised(), this.sigHashMode(), this.anyoneCanPay());
    }

    public static TransactionSignature2 decodeFromBitcoin(byte[] bytes, boolean requireCanonicalEncoding, boolean requireCanonicalSValue) throws SignatureDecodeException, VerificationException {
        if (requireCanonicalEncoding && !isEncodingCanonical(bytes)) {
            throw new VerificationException("Signature encoding is not canonical.");
        } else {
            ECKey2.ECDSASignature sig = ECKey2.ECDSASignature.decodeFromDER(bytes);
            if (requireCanonicalSValue && !sig.isCanonical()) {
                throw new VerificationException("S-value is not canonical.");
            } else {
                return new TransactionSignature2(sig.r, sig.s, bytes[bytes.length - 1]);
            }
        }
    }
}
