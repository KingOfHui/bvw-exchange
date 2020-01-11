package com.darknet.bvw.model.response.CreateTradeResponse;

public class TransactionRAWVin {
    String txid = null;
    Long vout = null;
    ScriptSig scriptSig = null;
    String coinbase = null;
    Long sequence = null;

    /**
     *
     */
    public TransactionRAWVin() {
        super();
        scriptSig = new ScriptSig();
    }

    /**
     * @return the txid
     */
    public String getTxid() {
        return txid;
    }

    /**
     * @param txid the txid to set
     */
    public void setTxid(String txid) {
        this.txid = txid;
    }

    /**
     * @return the vout
     */
    public Long getVout() {
        return vout;
    }

    /**
     * @param vout the vout to set
     */
    public void setVout(long vout) {
        this.vout = vout;
    }

    /**
     * @return the scriptSig
     */
    public ScriptSig getScriptSig() {
        return scriptSig;
    }

    /**
     * @param scriptSig the scriptSig to set
     */
    public void setScriptSig(ScriptSig scriptSig) {
        this.scriptSig = scriptSig;
    }

    /**
     * @return the sequence
     */
    public long getSequence() {
        return sequence;
    }

    /**
     * @param sequence the sequence to set
     */
    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    /**
     * @return the coinbase
     */
    public String getCoinbase() {
        return coinbase;
    }

    /**
     * @param coinbase the coinbase to set
     */
    public void setCoinbase(String coinbase) {
        this.coinbase = coinbase;
    }



}

