package com.darknet.bvw.model.response;

import java.util.List;

public class FenLieScrollviewResponse extends BaseResponse {



    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FenLieScrollviewResponse{" +
                "data=" + data +
                '}';
    }
}
