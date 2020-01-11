package com.darknet.bvw.model.response;

import java.util.List;

public class PeopleListResponse extends BaseResponse {

    private PeopleListData data;

    public PeopleListData getData() {
        return data;
    }

    public void setData(PeopleListData data) {
        this.data = data;
    }

    public static class PeopleListData{

        private int totalCount;
        private int page;
        private int limit;
        private int totalPage;
        private List<PeopleModel> items;


        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<PeopleModel> getItems() {
            return items;
        }

        public void setItems(List<PeopleModel> items) {
            this.items = items;
        }
    }

    public static class PeopleModel{
        private int id;
        private String user_id;
        private String name;
        private String address;
        private String remark;
        private String create_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }
    }

}
