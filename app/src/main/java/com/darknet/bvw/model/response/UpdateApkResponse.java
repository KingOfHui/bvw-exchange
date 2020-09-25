package com.darknet.bvw.model.response;

public class UpdateApkResponse extends BaseResponse {


    private UpdateModel data;

    public UpdateModel getData() {
        return data;
    }

    public void setData(UpdateModel data) {
        this.data = data;
    }

    public static class UpdateModel{


//        @ApiModelProperty(value = "版本")
//        @Column(name = "version")
//        private String version;  //版本
//        @ApiModelProperty(value = "更新内容")
//        @Column(name = "content")
//        private String content;  //更新内容
//        @ApiModelProperty(value = "更新时间")
//        @Column(name = "update_at")
//        private String update_at;  //更新时间
//        @ApiModelProperty(value = "下载地址")
//        @Column(name = "url_address")
//        private String url_address;  //下载地址
//        @ApiModelProperty(value = "版本号")
//        @Column(name = "version_code")
//        private Integer version_code;


        private int updateId;
        private String type;
        private String version;
        private String content;
        private String updateTime;
        private String url_address;
        private String version_code;
        private String abroad_android_url;
        private String internal_android_url;

        public String getAbroad_android_url() {
            return abroad_android_url;
        }

        public void setAbroad_android_url(String abroad_android_url) {
            this.abroad_android_url = abroad_android_url;
        }

        public String getInternal_android_url() {
            return internal_android_url;
        }

        public void setInternal_android_url(String internal_android_url) {
            this.internal_android_url = internal_android_url;
        }

        public String getVersion_code() {
            return version_code;
        }

        public void setVersion_code(String version_code) {
            this.version_code = version_code;
        }

        public int getUpdateId() {
            return updateId;
        }

        public void setUpdateId(int updateId) {
            this.updateId = updateId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUrl_address() {
            return url_address;
        }

        public void setUrl_address(String url_address) {
            this.url_address = url_address;
        }

        @Override
        public String toString() {
            return "UpdateModel{" +
                    "updateId=" + updateId +
                    ", type='" + type + '\'' +
                    ", version='" + version + '\'' +
                    ", content='" + content + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    ", url_address='" + url_address + '\'' +
                    '}';
        }
    }

}
