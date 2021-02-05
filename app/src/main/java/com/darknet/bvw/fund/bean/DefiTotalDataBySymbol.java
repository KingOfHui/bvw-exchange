package com.darknet.bvw.fund.bean;

import java.util.List;

public class DefiTotalDataBySymbol {

    /**
     * balance :
     * historyBonus :
     * investAmount :
     * symbol :
     * @ApiModelProperty("用户可提现余额 BTD, 不区分币种")
     * private String balance;
     * @ApiModelProperty("用户历史总收益 BTD, 不区分币种")
     * private String historyBonus;
     * @ApiModelProperty("挖矿投资币种持仓统计列表,区分币种")
     * List<DeFiTotalInvestVO> investVOList = Lists.newArrayList();
     * public class DeFiTotalInvestVO {
     *     @ApiModelProperty("挖矿投资币种")
     *     private String symbol;
     *     @ApiModelProperty("用户当前币种持仓金额")
     *     private String investAmount;
     * }
     */

    private String balance;
    private String historyBonus;
    private String investAmount;
    private String symbol;
    private List<DeFiTotalInvestVO> investVOList;

    public List<DeFiTotalInvestVO> getInvestVOList() {
        return investVOList;
    }

    public void setInvestVOList(List<DeFiTotalInvestVO> investVOList) {
        this.investVOList = investVOList;
    }

    public class DeFiTotalInvestVO {
//        @ApiModelProperty("挖矿投资币种")
        private String symbol;
//        @ApiModelProperty("用户当前币种持仓金额")
        private String investAmount;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getInvestAmount() {
            return investAmount;
        }

        public void setInvestAmount(String investAmount) {
            this.investAmount = investAmount;
        }
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getHistoryBonus() {
        return historyBonus;
    }

    public void setHistoryBonus(String historyBonus) {
        this.historyBonus = historyBonus;
    }

    public String getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(String investAmount) {
        this.investAmount = investAmount;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
