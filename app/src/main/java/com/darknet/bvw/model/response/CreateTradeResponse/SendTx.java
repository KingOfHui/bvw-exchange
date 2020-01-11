package com.darknet.bvw.model.response.CreateTradeResponse;

import java.util.List;

public class SendTx {

    private String sendHex;
    private String requiredFee;
    private TransactionRAW decodeRawTx;
    private List<Unspent> unspent;
    private String error;

    public String getSendHex() {
        return sendHex;
    }

    public void setSendHex(String sendHex) {
        this.sendHex = sendHex;
    }

    public String getRequiredFee() {
        return requiredFee;
    }

    public void setRequiredFee(String requiredFee) {
        this.requiredFee = requiredFee;
    }

    public TransactionRAW getDecodeRawTx() {
        return decodeRawTx;
    }

    public void setDecodeRawTx(TransactionRAW decodeRawTx) {
        this.decodeRawTx = decodeRawTx;
    }

    public List<Unspent> getUnspent() {
        return unspent;
    }

    public void setUnspent(List<Unspent> unspent) {
        this.unspent = unspent;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
