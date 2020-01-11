//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.darknet.bvw.util.bitcoinj;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedBytes;

import org.bitcoin.NativeSecp256k1;
import org.bitcoin.NativeSecp256k1Util.AssertFailException;
import org.bitcoin.Secp256k1Context;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.SignatureDecodeException;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.VarInt;
import org.bitcoinj.crypto.EncryptableItem;
import org.bitcoinj.crypto.EncryptedData;
import org.bitcoinj.crypto.KeyCrypter;
import org.bitcoinj.crypto.KeyCrypterException;
import org.bitcoinj.crypto.KeyCrypterException.InvalidCipherText;
import org.bitcoinj.crypto.LazyECPoint;
import org.bitcoinj.crypto.LinuxSecureRandom;
import org.bitcoinj.wallet.Protos.Wallet.EncryptionType;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequenceGenerator;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.asn1.x9.X9IntegerConverter;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;
import org.bouncycastle.math.ec.ECAlgorithms;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.bouncycastle.math.ec.FixedPointUtil;
import org.bouncycastle.math.ec.custom.sec.SecP256K1Curve;
import org.bouncycastle.util.Properties;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.Comparator;

import javax.annotation.Nullable;

public class ECKey2 implements EncryptableItem {
    private static final Logger log = LoggerFactory.getLogger(ECKey2.class);
    public static final Comparator<ECKey2> AGE_COMPARATOR = new Comparator<ECKey2>() {
        public int compare(ECKey2 k1, ECKey2 k2) {
            if (k1.creationTimeSeconds == k2.creationTimeSeconds) {
                return 0;
            } else {
                return k1.creationTimeSeconds > k2.creationTimeSeconds ? 1 : -1;
            }
        }
    };
    public static final Comparator<ECKey2> PUBKEY_COMPARATOR = new Comparator<ECKey2>() {
        private Comparator<byte[]> comparator = UnsignedBytes.lexicographicalComparator();

        public int compare(ECKey2 k1, ECKey2 k2) {
            return this.comparator.compare(k1.getPubKey(), k2.getPubKey());
        }
    };
    private static final X9ECParameters CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1");
    public static final ECDomainParameters CURVE;
    public static final BigInteger HALF_CURVE_ORDER;
    private static final SecureRandom secureRandom;
    @Nullable
    protected final BigInteger priv;
    protected final LazyECPoint pub;
    protected long creationTimeSeconds;
    protected KeyCrypter keyCrypter;
    protected EncryptedData encryptedPrivateKey;
    private byte[] pubKeyHash;
    @VisibleForTesting
    public static boolean FAKE_SIGNATURES;
    private static final String BITCOIN_SIGNED_MESSAGE_HEADER = "Bitcoin Signed Message:\n";
    private static final byte[] BITCOIN_SIGNED_MESSAGE_HEADER_BYTES;

    public ECKey2() {
        this(secureRandom);
    }

    public ECKey2(SecureRandom secureRandom) {
        ECKeyPairGenerator generator = new ECKeyPairGenerator();
        ECKeyGenerationParameters keygenParams = new ECKeyGenerationParameters(CURVE, secureRandom);
        generator.init(keygenParams);
        AsymmetricCipherKeyPair keypair = generator.generateKeyPair();
        ECPrivateKeyParameters privParams = (ECPrivateKeyParameters)keypair.getPrivate();
        ECPublicKeyParameters pubParams = (ECPublicKeyParameters)keypair.getPublic();
        this.priv = privParams.getD();
        this.pub = new LazyECPoint(CURVE.getCurve(), pubParams.getQ().getEncoded(true));
        this.creationTimeSeconds = Utils.currentTimeSeconds();
    }

    protected ECKey2(@Nullable BigInteger priv, ECPoint pub) {
        this(priv, new LazyECPoint((ECPoint)Preconditions.checkNotNull(pub)));
    }

    protected ECKey2(@Nullable BigInteger priv, LazyECPoint pub) {
        if (priv != null) {
            Preconditions.checkArgument(priv.bitLength() <= 256, "private key exceeds 32 bytes: %s bits", priv.bitLength());
            Preconditions.checkArgument(!priv.equals(BigInteger.ZERO));
            Preconditions.checkArgument(!priv.equals(BigInteger.ONE));
        }

        this.priv = priv;
        this.pub = (LazyECPoint)Preconditions.checkNotNull(pub);
    }

    public static ECPoint compressPoint(ECPoint point) {
        return getPointWithCompression(point, true);
    }

    public static LazyECPoint compressPoint(LazyECPoint point) {
        return point.isCompressed() ? point : new LazyECPoint(compressPoint(point.get()));
    }

    public static ECPoint decompressPoint(ECPoint point) {
        return getPointWithCompression(point, false);
    }

    public static LazyECPoint decompressPoint(LazyECPoint point) {
        return !point.isCompressed() ? point : new LazyECPoint(decompressPoint(point.get()));
    }

    private static ECPoint getPointWithCompression(ECPoint point, boolean compressed) {
        if (point.isCompressed() == compressed) {
            return point;
        } else {
            point = point.normalize();
            BigInteger x = point.getAffineXCoord().toBigInteger();
            BigInteger y = point.getAffineYCoord().toBigInteger();
            return CURVE.getCurve().createPoint(x, y, compressed);
        }
    }

    public static ECKey2 fromASN1(byte[] asn1privkey) {
        return extractKeyFromASN1(asn1privkey);
    }

    public static ECKey2 fromPrivate(BigInteger privKey) {
        return fromPrivate(privKey, true);
    }

    public static ECKey2 fromPrivate(BigInteger privKey, boolean compressed) {
        ECPoint point = publicPointFromPrivate(privKey);
        return new ECKey2(privKey, getPointWithCompression(point, compressed));
    }

    public static ECKey2 fromPrivate(byte[] privKeyBytes) {
        return fromPrivate(new BigInteger(1, privKeyBytes));
    }

    public static ECKey2 fromPrivate(byte[] privKeyBytes, boolean compressed) {
        return fromPrivate(new BigInteger(1, privKeyBytes), compressed);
    }

    public static ECKey2 fromPrivateAndPrecalculatedPublic(BigInteger priv, ECPoint pub) {
        return new ECKey2(priv, pub);
    }

    public static ECKey2 fromPrivateAndPrecalculatedPublic(byte[] priv, byte[] pub) {
        Preconditions.checkNotNull(priv);
        Preconditions.checkNotNull(pub);
        return new ECKey2(new BigInteger(1, priv), CURVE.getCurve().decodePoint(pub));
    }

    public static ECKey2 fromPublicOnly(ECPoint pub) {
        return new ECKey2((BigInteger)null, pub);
    }

    public static ECKey2 fromPublicOnly(byte[] pub) {
        return new ECKey2((BigInteger)null, CURVE.getCurve().decodePoint(pub));
    }

    public ECKey2 decompress() {
        return !this.pub.isCompressed() ? this : new ECKey2(this.priv, decompressPoint(this.pub.get()));
    }

    /** @deprecated */
    @Deprecated
    public ECKey2(@Nullable byte[] privKeyBytes, @Nullable byte[] pubKey) {
        this(privKeyBytes == null ? null : new BigInteger(1, privKeyBytes), pubKey);
    }

    /** @deprecated */
    @Deprecated
    public ECKey2(EncryptedData encryptedPrivateKey, byte[] pubKey, KeyCrypter keyCrypter) {
        this((byte[])null, pubKey);
        this.keyCrypter = (KeyCrypter)Preconditions.checkNotNull(keyCrypter);
        this.encryptedPrivateKey = encryptedPrivateKey;
    }

    public static ECKey2 fromEncrypted(EncryptedData encryptedPrivateKey, KeyCrypter crypter, byte[] pubKey) {
        ECKey2 key = fromPublicOnly(pubKey);
        key.encryptedPrivateKey = (EncryptedData)Preconditions.checkNotNull(encryptedPrivateKey);
        key.keyCrypter = (KeyCrypter)Preconditions.checkNotNull(crypter);
        return key;
    }

    /** @deprecated */
    @Deprecated
    public ECKey2(@Nullable BigInteger privKey, @Nullable byte[] pubKey, boolean compressed) {
        if (privKey == null && pubKey == null) {
            throw new IllegalArgumentException("ECKey requires at least private or public key");
        } else {
            this.priv = privKey;
            if (pubKey == null) {
                ECPoint point = publicPointFromPrivate(privKey);
                point = getPointWithCompression(point, compressed);
                this.pub = new LazyECPoint(point);
            } else {
                this.pub = new LazyECPoint(CURVE.getCurve(), pubKey);
            }

        }
    }

    /** @deprecated */
    @Deprecated
    private ECKey2(@Nullable BigInteger privKey, @Nullable byte[] pubKey) {
        this(privKey, pubKey, false);
    }

    public boolean isPubKeyOnly() {
        return this.priv == null;
    }

    public boolean hasPrivKey() {
        return this.priv != null;
    }

    public boolean isWatching() {
        return this.isPubKeyOnly() && !this.isEncrypted();
    }

    public byte[] toASN1() {
        try {
            byte[] privKeyBytes = this.getPrivKeyBytes();
            ByteArrayOutputStream baos = new ByteArrayOutputStream(400);
            DERSequenceGenerator seq = new DERSequenceGenerator(baos);
            seq.addObject(new ASN1Integer(1L));
            seq.addObject(new DEROctetString(privKeyBytes));
            seq.addObject(new DERTaggedObject(0, CURVE_PARAMS.toASN1Primitive()));
            seq.addObject(new DERTaggedObject(1, new DERBitString(this.getPubKey())));
            seq.close();
            return baos.toByteArray();
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }
    }

    public static byte[] publicKeyFromPrivate(BigInteger privKey, boolean compressed) {
        ECPoint point = publicPointFromPrivate(privKey);
        return point.getEncoded(compressed);
    }

    public static ECPoint publicPointFromPrivate(BigInteger privKey) {
        if (privKey.bitLength() > CURVE.getN().bitLength()) {
            privKey = privKey.mod(CURVE.getN());
        }

        return (new FixedPointCombMultiplier()).multiply(CURVE.getG(), privKey);
    }

    public byte[] getPubKeyHash() {
        if (this.pubKeyHash == null) {
            this.pubKeyHash = Utils.sha256hash160(this.pub.getEncoded());
        }

        return this.pubKeyHash;
    }

    public byte[] getPubKey() {
        return this.pub.getEncoded();
    }

    public ECPoint getPubKeyPoint() {
        return this.pub.get();
    }

    public BigInteger getPrivKey() {
        if (this.priv == null) {
            throw new ECKey2.MissingPrivateKeyException();
        } else {
            return this.priv;
        }
    }

    public boolean isCompressed() {
        return this.pub.isCompressed();
    }

    public ECKey2.ECDSASignature sign(Sha256Hash input) throws KeyCrypterException {
        return this.sign(input, (KeyParameter)null);
    }

    public ECKey2.ECDSASignature sign(Sha256Hash input, @Nullable KeyParameter aesKey) throws KeyCrypterException {
        KeyCrypter crypter = this.getKeyCrypter();
        if (crypter != null) {
            if (aesKey == null) {
                throw new ECKey2.KeyIsEncryptedException();
            } else {
                return this.decrypt(aesKey).sign(input);
            }
        } else if (this.priv == null) {
            throw new ECKey2.MissingPrivateKeyException();
        } else {
            return this.doSign(input, this.priv);
        }
    }

    protected ECKey2.ECDSASignature doSign(Sha256Hash input, BigInteger privateKeyForSigning) {
        if (Secp256k1Context.isEnabled()) {
            try {
                byte[] signature = NativeSecp256k1.sign(input.getBytes(), Utils.bigIntegerToBytes(privateKeyForSigning, 32));
                return ECKey2.ECDSASignature.decodeFromDER(signature);
            } catch (AssertFailException var6) {
                log.error("Caught AssertFailException inside secp256k1", var6);
                throw new RuntimeException(var6);
            } catch (SignatureDecodeException var7) {
                throw new RuntimeException(var7);
            }
        } else if (FAKE_SIGNATURES) {
            return TransactionSignature2.dummy();
        } else {
            Preconditions.checkNotNull(privateKeyForSigning);
            ECDSASigner signer = new ECDSASigner(new HMacDSAKCalculator(new SHA256Digest()));
            ECPrivateKeyParameters privKey = new ECPrivateKeyParameters(privateKeyForSigning, CURVE);
            signer.init(true, privKey);
            BigInteger[] components = signer.generateSignature(input.getBytes());
            return (new ECKey2.ECDSASignature(components[0], components[1])).toCanonicalised();
        }
    }

    public static boolean verify(byte[] data, ECKey2.ECDSASignature signature, byte[] pub) {
        if (FAKE_SIGNATURES) {
            return true;
        } else if (Secp256k1Context.isEnabled()) {
            try {
                return NativeSecp256k1.verify(data, signature.encodeToDER(), pub);
            } catch (AssertFailException var6) {
                log.error("Caught AssertFailException inside secp256k1", var6);
                return false;
            }
        } else {
            ECDSASigner signer = new ECDSASigner();
            ECPublicKeyParameters params = new ECPublicKeyParameters(CURVE.getCurve().decodePoint(pub), CURVE);
            signer.init(false, params);

            try {
                return signer.verifySignature(data, signature.r, signature.s);
            } catch (NullPointerException var7) {
                log.error("Caught NPE inside bouncy castle", var7);
                return false;
            }
        }
    }

    public static boolean verify(byte[] data, byte[] signature, byte[] pub) throws SignatureDecodeException {
        if (Secp256k1Context.isEnabled()) {
            try {
                return NativeSecp256k1.verify(data, signature, pub);
            } catch (AssertFailException var4) {
                log.error("Caught AssertFailException inside secp256k1", var4);
                return false;
            }
        } else {
            return verify(data, ECKey2.ECDSASignature.decodeFromDER(signature), pub);
        }
    }

    public boolean verify(byte[] hash, byte[] signature) throws SignatureDecodeException {
        return verify(hash, signature, this.getPubKey());
    }

    public boolean verify(Sha256Hash sigHash, ECKey2.ECDSASignature signature) {
        return verify(sigHash.getBytes(), signature, this.getPubKey());
    }

    public void verifyOrThrow(byte[] hash, byte[] signature) throws SignatureDecodeException, SignatureException {
        if (!this.verify(hash, signature)) {
            throw new SignatureException();
        }
    }

    public void verifyOrThrow(Sha256Hash sigHash, ECKey2.ECDSASignature signature) throws SignatureException {
        if (!verify(sigHash.getBytes(), signature, this.getPubKey())) {
            throw new SignatureException();
        }
    }

    public static boolean isPubKeyCanonical(byte[] pubkey) {
        if (pubkey.length < 33) {
            return false;
        } else {
            if (pubkey[0] == 4) {
                if (pubkey.length != 65) {
                    return false;
                }
            } else {
                if (pubkey[0] != 2 && pubkey[0] != 3) {
                    return false;
                }

                if (pubkey.length != 33) {
                    return false;
                }
            }

            return true;
        }
    }

    private static ECKey2 extractKeyFromASN1(byte[] asn1privkey) {
        try {
            ASN1InputStream decoder = new ASN1InputStream(asn1privkey);
            DLSequence seq = (DLSequence)decoder.readObject();
            Preconditions.checkArgument(decoder.readObject() == null, "Input contains extra bytes");
            decoder.close();
            Preconditions.checkArgument(seq.size() == 4, "Input does not appear to be an ASN.1 OpenSSL EC private key");
            Preconditions.checkArgument(((ASN1Integer)seq.getObjectAt(0)).getValue().equals(BigInteger.ONE), "Input is of wrong version");
            byte[] privbits = ((ASN1OctetString)seq.getObjectAt(1)).getOctets();
            BigInteger privkey = new BigInteger(1, privbits);
            ASN1TaggedObject pubkey = (ASN1TaggedObject)seq.getObjectAt(3);
            Preconditions.checkArgument(pubkey.getTagNo() == 1, "Input has 'publicKey' with bad tag number");
            byte[] pubbits = ((DERBitString)pubkey.getObject()).getBytes();
            Preconditions.checkArgument(pubbits.length == 33 || pubbits.length == 65, "Input has 'publicKey' with invalid length");
            int encoding = pubbits[0] & 255;
            Preconditions.checkArgument(encoding >= 2 && encoding <= 4, "Input has 'publicKey' with invalid encoding");
            boolean compressed = pubbits.length == 33;
            ECKey2 key = new ECKey2(privkey, (byte[])null, compressed);
            if (!Arrays.equals(key.getPubKey(), pubbits)) {
                throw new IllegalArgumentException("Public key in ASN.1 structure does not match private key.");
            } else {
                return key;
            }
        } catch (IOException var10) {
            throw new RuntimeException(var10);
        }
    }

    public String signMessage(String message) throws KeyCrypterException {
        return this.signMessage(message, (KeyParameter)null);
    }

    public String signMessage(String message, @Nullable KeyParameter aesKey) throws KeyCrypterException {
        byte[] data = formatMessageForSigning(message);
        Sha256Hash hash = Sha256Hash.twiceOf(data);
        ECKey2.ECDSASignature sig = this.sign(hash, aesKey);
        byte recId = this.findRecoveryId(hash, sig);
        int headerByte = recId + 27 + (this.isCompressed() ? 4 : 0);
        byte[] sigData = new byte[65];
        sigData[0] = (byte)headerByte;
        System.arraycopy(Utils.bigIntegerToBytes(sig.r, 32), 0, sigData, 1, 32);
        System.arraycopy(Utils.bigIntegerToBytes(sig.s, 32), 0, sigData, 33, 32);
        return new String(Base64.encode(sigData), StandardCharsets.UTF_8);
    }

    public static ECKey2 signedMessageToKey(String message, String signatureBase64) throws SignatureException {
        byte[] signatureEncoded;
        try {
            signatureEncoded = Base64.decode(signatureBase64);
        } catch (RuntimeException var12) {
            throw new SignatureException("Could not decode base64", var12);
        }

        if (signatureEncoded.length < 65) {
            throw new SignatureException("Signature truncated, expected 65 bytes and got " + signatureEncoded.length);
        } else {
            int header = signatureEncoded[0] & 255;
            if (header >= 27 && header <= 34) {
                BigInteger r = new BigInteger(1, Arrays.copyOfRange(signatureEncoded, 1, 33));
                BigInteger s = new BigInteger(1, Arrays.copyOfRange(signatureEncoded, 33, 65));
                ECKey2.ECDSASignature sig = new ECKey2.ECDSASignature(r, s);
                byte[] messageBytes = formatMessageForSigning(message);
                Sha256Hash messageHash = Sha256Hash.twiceOf(messageBytes);
                boolean compressed = false;
                if (header >= 31) {
                    compressed = true;
                    header -= 4;
                }

                int recId = header - 27;
                ECKey2 key = recoverFromSignature(recId, sig, messageHash, compressed);
                if (key == null) {
                    throw new SignatureException("Could not recover public key from signature");
                } else {
                    return key;
                }
            } else {
                throw new SignatureException("Header byte out of range: " + header);
            }
        }
    }

    public void verifyMessage(String message, String signatureBase64) throws SignatureException {
        ECKey2 key = signedMessageToKey(message, signatureBase64);
        if (!key.pub.equals(this.pub)) {
            throw new SignatureException("Signature did not match for message");
        }
    }

    public byte findRecoveryId(Sha256Hash hash, ECKey2.ECDSASignature sig) {
        byte recId = -1;

        for(byte i = 0; i < 4; ++i) {
            ECKey2 k = recoverFromSignature(i, sig, hash, this.isCompressed());
            if (k != null && k.pub.equals(this.pub)) {
                recId = i;
                break;
            }
        }

        if (recId == -1) {
            throw new RuntimeException("Could not construct a recoverable key. This should never happen.");
        } else {
            return recId;
        }
    }

    @Nullable
    public static ECKey2 recoverFromSignature(int recId, ECKey2.ECDSASignature sig, Sha256Hash message, boolean compressed) {
        Preconditions.checkArgument(recId >= 0, "recId must be positive");
        Preconditions.checkArgument(sig.r.signum() >= 0, "r must be positive");
        Preconditions.checkArgument(sig.s.signum() >= 0, "s must be positive");
        Preconditions.checkNotNull(message);
        BigInteger n = CURVE.getN();
        BigInteger i = BigInteger.valueOf((long)recId / 2L);
        BigInteger x = sig.r.add(i.multiply(n));
        BigInteger prime = SecP256K1Curve.q;
        if (x.compareTo(prime) >= 0) {
            return null;
        } else {
            ECPoint R = decompressKey(x, (recId & 1) == 1);
            if (!R.multiply(n).isInfinity()) {
                return null;
            } else {
                BigInteger e = message.toBigInteger();
                BigInteger eInv = BigInteger.ZERO.subtract(e).mod(n);
                BigInteger rInv = sig.r.modInverse(n);
                BigInteger srInv = rInv.multiply(sig.s).mod(n);
                BigInteger eInvrInv = rInv.multiply(eInv).mod(n);
                ECPoint q = ECAlgorithms.sumOfTwoMultiplies(CURVE.getG(), eInvrInv, R, srInv);
                return fromPublicOnly(q.getEncoded(compressed));
            }
        }
    }

    private static ECPoint decompressKey(BigInteger xBN, boolean yBit) {
        X9IntegerConverter x9 = new X9IntegerConverter();
        byte[] compEnc = x9.integerToBytes(xBN, 1 + x9.getByteLength(CURVE.getCurve()));
        compEnc[0] = (byte)(yBit ? 3 : 2);
        return CURVE.getCurve().decodePoint(compEnc);
    }

    public byte[] getPrivKeyBytes() {
        return Utils.bigIntegerToBytes(this.getPrivKey(), 32);
    }

    public DumpedPrivateKey2 getPrivateKeyEncoded(NetworkParameters params) {
        return new DumpedPrivateKey2(params, this.getPrivKeyBytes(), this.isCompressed());
    }

    public long getCreationTimeSeconds() {
        return this.creationTimeSeconds;
    }

    public void setCreationTimeSeconds(long newCreationTimeSeconds) {
        if (newCreationTimeSeconds < 0L) {
            throw new IllegalArgumentException("Cannot set creation time to negative value: " + newCreationTimeSeconds);
        } else {
            this.creationTimeSeconds = newCreationTimeSeconds;
        }
    }

    public ECKey2 encrypt(KeyCrypter keyCrypter, KeyParameter aesKey) throws KeyCrypterException {
        Preconditions.checkNotNull(keyCrypter);
        byte[] privKeyBytes = this.getPrivKeyBytes();
        EncryptedData encryptedPrivateKey = keyCrypter.encrypt(privKeyBytes, aesKey);
        ECKey2 result = fromEncrypted(encryptedPrivateKey, keyCrypter, this.getPubKey());
        result.setCreationTimeSeconds(this.creationTimeSeconds);
        return result;
    }

    public ECKey2 decrypt(KeyCrypter keyCrypter, KeyParameter aesKey) throws KeyCrypterException {
        Preconditions.checkNotNull(keyCrypter);
        if (this.keyCrypter != null && !this.keyCrypter.equals(keyCrypter)) {
            throw new KeyCrypterException("The keyCrypter being used to decrypt the key is different to the one that was used to encrypt it");
        } else {
            Preconditions.checkState(this.encryptedPrivateKey != null, "This key is not encrypted");
            byte[] unencryptedPrivateKey = keyCrypter.decrypt(this.encryptedPrivateKey, aesKey);
            if (unencryptedPrivateKey.length != 32) {
                throw new InvalidCipherText("Decrypted key must be 32 bytes long, but is " + unencryptedPrivateKey.length);
            } else {
                ECKey2 key = fromPrivate(unencryptedPrivateKey);
                if (!this.isCompressed()) {
                    key = key.decompress();
                }

                if (!Arrays.equals(key.getPubKey(), this.getPubKey())) {
                    throw new KeyCrypterException("Provided AES key is wrong");
                } else {
                    key.setCreationTimeSeconds(this.creationTimeSeconds);
                    return key;
                }
            }
        }
    }

    public ECKey2 decrypt(KeyParameter aesKey) throws KeyCrypterException {
        KeyCrypter crypter = this.getKeyCrypter();
        if (crypter == null) {
            throw new KeyCrypterException("No key crypter available");
        } else {
            return this.decrypt(crypter, aesKey);
        }
    }

    public ECKey2 maybeDecrypt(@Nullable KeyParameter aesKey) throws KeyCrypterException {
        return this.isEncrypted() && aesKey != null ? this.decrypt(aesKey) : this;
    }

    public static boolean encryptionIsReversible(ECKey2 originalKey, ECKey2 encryptedKey, KeyCrypter keyCrypter, KeyParameter aesKey) {
        try {
            ECKey2 rebornUnencryptedKey = encryptedKey.decrypt(keyCrypter, aesKey);
            byte[] originalPrivateKeyBytes = originalKey.getPrivKeyBytes();
            byte[] rebornKeyBytes = rebornUnencryptedKey.getPrivKeyBytes();
            if (!Arrays.equals(originalPrivateKeyBytes, rebornKeyBytes)) {
                log.error("The check that encryption could be reversed failed for {}", originalKey);
                return false;
            } else {
                return true;
            }
        } catch (KeyCrypterException var7) {
            log.error(var7.getMessage());
            return false;
        }
    }

    public boolean isEncrypted() {
        return this.keyCrypter != null && this.encryptedPrivateKey != null && this.encryptedPrivateKey.encryptedBytes.length > 0;
    }

    @Nullable
    public EncryptionType getEncryptionType() {
        return this.keyCrypter != null ? this.keyCrypter.getUnderstoodEncryptionType() : EncryptionType.UNENCRYPTED;
    }

    @Nullable
    public byte[] getSecretBytes() {
        return this.hasPrivKey() ? this.getPrivKeyBytes() : null;
    }

    @Nullable
    public EncryptedData getEncryptedData() {
        return this.getEncryptedPrivateKey();
    }

    @Nullable
    public EncryptedData getEncryptedPrivateKey() {
        return this.encryptedPrivateKey;
    }

    @Nullable
    public KeyCrypter getKeyCrypter() {
        return this.keyCrypter;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && o instanceof ECKey2) {
            ECKey2 other = (ECKey2)o;
            return Objects.equal(this.priv, other.priv) && Objects.equal(this.pub, other.pub) && Objects.equal(this.creationTimeSeconds, other.creationTimeSeconds) && Objects.equal(this.keyCrypter, other.keyCrypter) && Objects.equal(this.encryptedPrivateKey, other.encryptedPrivateKey);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.pub.hashCode();
    }

    public String toString() {
        return this.toString(false, (KeyParameter)null, (NetworkParameters)null);
    }

    public String toStringWithPrivate(@Nullable KeyParameter aesKey, NetworkParameters params) {
        return this.toString(true, aesKey, params);
    }

    public String getPrivateKeyAsHex() {
        return Utils.HEX.encode(this.getPrivKeyBytes());
    }

    public String getPublicKeyAsHex() {
        return Utils.HEX.encode(this.pub.getEncoded());
    }

    public String getPrivateKeyAsWiF(NetworkParameters params) {
        return this.getPrivateKeyEncoded(params).toString();
    }

    private String toString(boolean includePrivate, @Nullable KeyParameter aesKey, @Nullable NetworkParameters params) {
        ToStringHelper helper = MoreObjects.toStringHelper(this).omitNullValues();
        helper.add("pub HEX", this.getPublicKeyAsHex());
        if (includePrivate) {
            ECKey2 decryptedKey = this.isEncrypted() ? this.decrypt((KeyParameter)Preconditions.checkNotNull(aesKey)) : this;

            try {
                helper.add("priv HEX", decryptedKey.getPrivateKeyAsHex());
                helper.add("priv WIF", decryptedKey.getPrivateKeyAsWiF(params));
            } catch (IllegalStateException var8) {
            } catch (Exception var9) {
                String message = var9.getMessage();
                helper.add("priv EXCEPTION", var9.getClass().getName() + (message != null ? ": " + message : ""));
            }
        }

        if (this.creationTimeSeconds > 0L) {
            helper.add("creationTimeSeconds", this.creationTimeSeconds);
        }

        helper.add("keyCrypter", this.keyCrypter);
        if (includePrivate) {
            helper.add("encryptedPrivateKey", this.encryptedPrivateKey);
        }

        helper.add("isEncrypted", this.isEncrypted());
        helper.add("isPubKeyOnly", this.isPubKeyOnly());
        return helper.toString();
    }

//    public void formatKeyWithAddress(boolean includePrivateKeys, @Nullable KeyParameter aesKey, StringBuilder builder, NetworkParameters params, ScriptType outputScriptType, @Nullable String comment) {
//        builder.append("  addr:");
//        if (outputScriptType != null) {
//            builder.append(Address.fromKey(params, this, outputScriptType));
//        } else {
//            builder.append(LegacyAddress.fromKey(params, this));
//            if (this.isCompressed()) {
//                builder.append(',').append(SegwitAddress.fromKey(params, this));
//            }
//        }
//
//        if (!this.isCompressed()) {
//            builder.append("  UNCOMPRESSED");
//        }
//
//        builder.append("  hash160:");
//        builder.append(Utils.HEX.encode(this.getPubKeyHash()));
//        if (this.creationTimeSeconds > 0L) {
//            builder.append("  creationTimeSeconds:").append(this.creationTimeSeconds).append(" [").append(Utils.dateTimeFormat(this.creationTimeSeconds * 1000L)).append("]");
//        }
//
//        if (comment != null) {
//            builder.append("  (").append(comment).append(")");
//        }
//
//        builder.append("\n");
//        if (includePrivateKeys) {
//            builder.append("  ");
//            builder.append(this.toStringWithPrivate(aesKey, params));
//            builder.append("\n");
//        }
//
//    }

    private static byte[] formatMessageForSigning(String message) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.write(BITCOIN_SIGNED_MESSAGE_HEADER_BYTES.length);
            bos.write(BITCOIN_SIGNED_MESSAGE_HEADER_BYTES);
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
            VarInt size = new VarInt((long)messageBytes.length);
            bos.write(size.encode());
            bos.write(messageBytes);
            return bos.toByteArray();
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }
    }

    static {
        if (Utils.isAndroidRuntime()) {
            new LinuxSecureRandom();
        }

        FixedPointUtil.precompute(CURVE_PARAMS.getG());
        CURVE = new ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(), CURVE_PARAMS.getH());
        HALF_CURVE_ORDER = CURVE_PARAMS.getN().shiftRight(1);
        secureRandom = new SecureRandom();
        FAKE_SIGNATURES = false;
        BITCOIN_SIGNED_MESSAGE_HEADER_BYTES = "MultiChain Signed Message:\n".getBytes(StandardCharsets.UTF_8);
    }

    public static class KeyIsEncryptedException extends ECKey2.MissingPrivateKeyException {
        public KeyIsEncryptedException() {
        }
    }

    public static class MissingPrivateKeyException extends RuntimeException {
        public MissingPrivateKeyException() {
        }
    }

    public static class ECDSASignature {
        public final BigInteger r;
        public final BigInteger s;

        public ECDSASignature(BigInteger r, BigInteger s) {
            this.r = r;
            this.s = s;
        }

        public boolean isCanonical() {
            return this.s.compareTo(ECKey2.HALF_CURVE_ORDER) <= 0;
        }

        public ECKey2.ECDSASignature toCanonicalised() {
            return !this.isCanonical() ? new ECKey2.ECDSASignature(this.r, ECKey2.CURVE.getN().subtract(this.s)) : this;
        }

        public byte[] encodeToDER() {
            try {
                return this.derByteStream().toByteArray();
            } catch (IOException var2) {
                throw new RuntimeException(var2);
            }
        }

        public static ECKey2.ECDSASignature decodeFromDER(byte[] bytes) throws SignatureDecodeException {
            ASN1InputStream decoder = null;

            ECKey2.ECDSASignature var6;
            try {
                Properties.setThreadOverride("org.bouncycastle.asn1.allow_unsafe_integer", true);
                decoder = new ASN1InputStream(bytes);
                ASN1Primitive seqObj = decoder.readObject();
                if (seqObj == null) {
                    throw new SignatureDecodeException("Reached past end of ASN.1 stream.");
                }

                if (!(seqObj instanceof DLSequence)) {
                    throw new SignatureDecodeException("Read unexpected class: " + seqObj.getClass().getName());
                }

                DLSequence seq = (DLSequence)seqObj;

                ASN1Integer r;
                ASN1Integer s;
                try {
                    r = (ASN1Integer)seq.getObjectAt(0);
                    s = (ASN1Integer)seq.getObjectAt(1);
                } catch (ClassCastException var16) {
                    throw new SignatureDecodeException(var16);
                }

                var6 = new ECKey2.ECDSASignature(r.getPositiveValue(), s.getPositiveValue());
            } catch (IOException var17) {
                throw new SignatureDecodeException(var17);
            } finally {
                if (decoder != null) {
                    try {
                        decoder.close();
                    } catch (IOException var15) {
                    }
                }

                Properties.removeThreadOverride("org.bouncycastle.asn1.allow_unsafe_integer");
            }

            return var6;
        }

        protected ByteArrayOutputStream derByteStream() throws IOException {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(72);
            DERSequenceGenerator seq = new DERSequenceGenerator(bos);
            seq.addObject(new ASN1Integer(this.r));
            seq.addObject(new ASN1Integer(this.s));
            seq.close();
            return bos;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (o != null && this.getClass() == o.getClass()) {
                ECKey2.ECDSASignature other = (ECKey2.ECDSASignature)o;
                return this.r.equals(other.r) && this.s.equals(other.s);
            } else {
                return false;
            }
        }

        public int hashCode() {
            return Objects.hashCode(new Object[]{this.r, this.s});
        }
    }
}
