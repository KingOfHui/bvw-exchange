package com.darknet.bvw.model.response;

public class BidStateResponse extends BaseResponse {


    private BidStateModel data;

    public BidStateModel getData() {
        return data;
    }

    public void setData(BidStateModel data) {
        this.data = data;
    }

    public static class BidStateModel{
        private int status;
        private String lid;
        private String rid;
        private String referer_id;
        private String invite_code;

        public String getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

        public String getReferer_id() {
            return referer_id;
        }

        public void setReferer_id(String referer_id) {
            this.referer_id = referer_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getLid() {
            return lid;
        }

        public void setLid(String lid) {
            this.lid = lid;
        }

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        @Override
        public String toString() {
            return "BidStateModel{" +
                    "status=" + status +
                    ", lid='" + lid + '\'' +
                    ", rid='" + rid + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BidStateResponse{" +
                "data=" + data +
                '}';
    }
}
