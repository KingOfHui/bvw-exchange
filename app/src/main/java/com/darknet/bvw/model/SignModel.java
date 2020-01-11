package com.darknet.bvw.model;

public class SignModel {

    private String privKey;
    private String inputs;
    private String decodedTransaction;

    public String getPrivKey() {
        return privKey;
    }

    public void setPrivKey(String privKey) {
        this.privKey = privKey;
    }

    public String getInputs() {
        return inputs;
    }

    public void setInputs(String inputs) {
        this.inputs = inputs;
    }

    public String getDecodedTransaction() {
        return decodedTransaction;
    }

    public void setDecodedTransaction(String decodedTransaction) {
        this.decodedTransaction = decodedTransaction;
    }

    @Override
    public String toString() {
        return "SignModel{" +
                "privKey='" + privKey + '\'' +
                ", inputs='" + inputs + '\'' +
                ", decodedTransaction='" + decodedTransaction + '\'' +
                '}';
    }
}
