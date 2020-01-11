package com.darknet.bvw.model;

import com.darknet.bvw.model.response.CreateTradeResponse.TransactionRAW;
import com.darknet.bvw.model.response.CreateTradeResponse.Unspent;

import java.util.List;

public class SignModelTwo {

    private TransactionRAW decodedTransaction;
    private List<Unspent> inputs;
    private String privKey;

    public TransactionRAW getDecodedTransaction() {
        return decodedTransaction;
    }

    public void setDecodedTransaction(TransactionRAW decodedTransaction) {
        this.decodedTransaction = decodedTransaction;
    }

    public List<Unspent> getInputs() {
        return inputs;
    }

    public void setInputs(List<Unspent> inputs) {
        this.inputs = inputs;
    }

    public String getPrivKey() {
        return privKey;
    }

    public void setPrivKey(String privKey) {
        this.privKey = privKey;
    }
}
