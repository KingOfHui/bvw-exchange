package com.darknet.bvw.model.request;

public class CreateTradeRequest {

    private String amount;
    private String symbol;
    private String to_address;

//    public int getAmount() {
//        return amount;
//    }
//
//    public void setAmount(int amount) {
//        this.amount = amount;
//    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    @Override
    public String toString() {
        return "CreateTradeRequest{" +
                "amount=" + amount +
                ", symbol='" + symbol + '\'' +
                ", to_address='" + to_address + '\'' +
                '}';
    }
}
