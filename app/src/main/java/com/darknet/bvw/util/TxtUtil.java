package com.darknet.bvw.util;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;

public class TxtUtil {

    public static boolean isBTCValidAddress(String input) {
        try {
            NetworkParameters networkParameters = null;
            networkParameters = MainNetParams.get();
            Address address = Address.fromString(networkParameters, input);
            if (address != null)
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

}
