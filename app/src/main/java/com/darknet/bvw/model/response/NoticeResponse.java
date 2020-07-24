package com.darknet.bvw.model.response;

import java.io.Serializable;

public class NoticeResponse extends BaseResponse {

//    {"code":0,"msg":"success","data":{"id":1,"title":"用户须知","content":"最终解释权归本店所有，嘿嘿嘿",
//            "image_url":"","create_at":"2019-11-03 10:43:39",
//            "update_at":"2019-11-03 10:43:39","sort_no":0,"is_top":1,
//            "top_at":"2019-11-03 10:49:55","is_show":1},"success":true}


    private NoticeData data;

    public NoticeData getData() {
        return data;
    }

    public void setData(NoticeData data) {
        this.data = data;
    }

    public static class NoticeData implements Serializable {
        private int id;
        private String title;
        private String content;
        private String image_url;
        private String create_at;
        private String update_at;
        private String sort_no;
        private String top_at;
        private int is_show;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String getUpdate_at() {
            return update_at;
        }

        public void setUpdate_at(String update_at) {
            this.update_at = update_at;
        }

        public String getSort_no() {
            return sort_no;
        }

        public void setSort_no(String sort_no) {
            this.sort_no = sort_no;
        }

        public String getTop_at() {
            return top_at;
        }

        public void setTop_at(String top_at) {
            this.top_at = top_at;
        }

        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }

        @Override
        public String toString() {
            return "NoticeData{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", image_url='" + image_url + '\'' +
                    ", create_at='" + create_at + '\'' +
                    ", update_at='" + update_at + '\'' +
                    ", sort_no='" + sort_no + '\'' +
                    ", top_at='" + top_at + '\'' +
                    ", is_show=" + is_show +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NoticeResponse{" +
                "data=" + data +
                '}';
    }
}
