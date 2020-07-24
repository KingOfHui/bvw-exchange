package com.darknet.bvw.model.response;

import java.math.BigDecimal;
import java.util.List;

public class YuBiResponse extends BaseResponse{


    private List<YuBiListModel> data;


    public List<YuBiListModel> getData() {
        return data;
    }

    public void setData(List<YuBiListModel> data) {
        this.data = data;
    }

    public static class YuBiListModel{
        private BigDecimal amount;
        private String create_at;
        private String date_limit;
        private String end_time;
        private String id;
        private String start_time;
        private String user_address;
        private String user_id;


        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String getDate_limit() {
            return date_limit;
        }

        public void setDate_limit(String date_limit) {
            this.date_limit = date_limit;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getUser_address() {
            return user_address;
        }

        public void setUser_address(String user_address) {
            this.user_address = user_address;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        @Override
        public String toString() {
            return "YuBiListModel{" +
                    "amount=" + amount +
                    ", create_at='" + create_at + '\'' +
                    ", date_limit='" + date_limit + '\'' +
                    ", end_time='" + end_time + '\'' +
                    ", id='" + id + '\'' +
                    ", start_time='" + start_time + '\'' +
                    ", user_address='" + user_address + '\'' +
                    ", user_id='" + user_id + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "YuBiResponse{" +
                "data=" + data +
                '}';
    }
}
