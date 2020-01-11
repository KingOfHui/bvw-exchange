package com.darknet.bvw.model.response.CreateTradeResponse;

import java.util.List;

public class AssetInfo extends BalanceAsset {
    List<AssetIssues> issues = null;

    /**
     * @return the issues
     */
    public List<AssetIssues> getIssues() {
        return issues;
    }

    /**
     * @param issues the issues to set
     */
    public void setIssues(List<AssetIssues> issues) {
        this.issues = issues;
    }

}
