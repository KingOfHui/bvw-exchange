package com.darknet.bvw.model.response;

import java.util.List;

public class PublicAddressResponse extends  BaseResponse {

    private List<PublicAddressModel> data;

    public List<PublicAddressModel> getData() {
        return data;
    }

    public void setData(List<PublicAddressModel> data) {
        this.data = data;
    }

    public class PublicAddressModel{
        private int id;
        private String k;
        private String v;
        private String type;
        private String remark;
        private String create_time;
        private String update_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        @Override
        public String toString() {
            return "PublicAddressModel{" +
                    "id=" + id +
                    ", k='" + k + '\'' +
                    ", v='" + v + '\'' +
                    ", type='" + type + '\'' +
                    ", remark='" + remark + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PublicAddressResponse{" +
                "data=" + data +
                '}';
    }
}
