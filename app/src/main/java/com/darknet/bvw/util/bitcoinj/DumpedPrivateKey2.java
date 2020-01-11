//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.darknet.bvw.util.bitcoinj;

import com.google.common.base.Preconditions;

import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.AddressFormatException.InvalidDataLength;
import org.bitcoinj.core.AddressFormatException.InvalidPrefix;
import org.bitcoinj.core.AddressFormatException.WrongNetwork;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PrefixedChecksummedBytes;
import org.bitcoinj.params.Networks;

import java.util.Arrays;
import java.util.Iterator;

import javax.annotation.Nullable;

public class DumpedPrivateKey2 extends PrefixedChecksummedBytes {
    public static DumpedPrivateKey2 fromBase58(@Nullable NetworkParameters params, String base58) throws AddressFormatException, WrongNetwork {
        byte[] versionAndDataBytes = Base58.decodeChecked(base58);
        int version = versionAndDataBytes[0] & 255;
        byte[] bytes = Arrays.copyOfRange(versionAndDataBytes, 1, versionAndDataBytes.length);
        if (params == null) {
            Iterator var5 = Networks.get().iterator();

            NetworkParameters p;
            do {
                if (!var5.hasNext()) {
                    throw new InvalidPrefix("No network found for version " + version);
                }

                p = (NetworkParameters)var5.next();
            } while(version != p.getDumpedPrivateKeyHeader());

            return new DumpedPrivateKey2(p, bytes);
        } else if (version == params.getDumpedPrivateKeyHeader()) {
            return new DumpedPrivateKey2(params, bytes);
        } else {
            throw new WrongNetwork(version);
        }
    }

    private DumpedPrivateKey2(NetworkParameters params, byte[] bytes) {
        super(params, bytes);
        if (bytes.length != 32 && bytes.length != 33) {
            throw new InvalidDataLength("Wrong number of bytes for a private key (32 or 33): " + bytes.length);
        }
    }

    DumpedPrivateKey2(NetworkParameters params, byte[] keyBytes, boolean compressed) {
        this(params, encode(keyBytes, compressed));
    }

    public String toBase58() {
        return Base58.encodeChecked(this.params.getDumpedPrivateKeyHeader(), this.bytes);
    }

    private static byte[] encode(byte[] keyBytes, boolean compressed) {
        Preconditions.checkArgument(keyBytes.length == 32, "Private keys must be 32 bytes");
        if (!compressed) {
            return keyBytes;
        } else {
            byte[] bytes = new byte[33];
            System.arraycopy(keyBytes, 0, bytes, 0, 32);
            bytes[32] = 1;
            return bytes;
        }
    }

    public ECKey2 getKey() {
        return ECKey2.fromPrivate(Arrays.copyOf(this.bytes, 32), this.isPubKeyCompressed());
    }

    public boolean isPubKeyCompressed() {
        return this.bytes.length == 33 && this.bytes[32] == 1;
    }

    public String toString() {
        return this.toBase58();
    }
}
