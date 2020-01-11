package com.darknet.bvw.model.response;

public class UserInfoResponse extends BaseResponse {

    private UserInfoModel data;

    public UserInfoModel getData() {
        return data;
    }

    public void setData(UserInfoModel data) {
        this.data = data;
    }

    public static class UserInfoModel{
        private String lid;  //类似邀请码,邀请进来的用户放置左节点下面
        private String rid;  //类似邀请码,邀请进来的用户放置右节点下面
        private String id;
        private String nickname;  //昵称
        private String avatar;  //头像地址
        private String vip_level;



//        private Integer sex;  //性别 0男 1女
//        private Integer status;  //用户状态
//        private String create_at;  //创建时间
//        private String update_at;  //更新时间
//        private String login_at;  //最后登陆时间
//        private String login_ip;  //最后登陆IP
//        private String address;//xchain地址
//        private Long referer_id;
//        private Integer referer_type;
//        private Long parent_id;
//        private Long left_id;
//        private Long right_id;
//        private Integer layer;
//        private Integer genesis_bid;


        public String getVip_level() {
            return vip_level;
        }

        public void setVip_level(String vip_level) {
            this.vip_level = vip_level;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        @Override
        public String toString() {
            return "UserInfoModel{" +
                    "lid='" + lid + '\'' +
                    ", rid='" + rid + '\'' +
                    ", id='" + id + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", avatar='" + avatar + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserInfoResponse{" +
                "data=" + data +
                '}';
    }
}
