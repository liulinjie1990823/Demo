package cn.com.llj.demo;

import com.common.library.llj.base.BaseReponse;

import java.util.List;

/**
 * Created by liulj on 15/9/1.
 */
public class ImageListResponse extends BaseReponse {
    private ImageListData data;
    private String server_time;

    public String getServer_time() {
        return server_time;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }

    public ImageListData getData() {
        return data;
    }

    public void setData(ImageListData data) {
        this.data = data;
    }

    public static class ImageListData {
        private String time;
        private List<Image> list;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<Image> getList() {
            return list;
        }

        public void setList(List<Image> list) {
            this.list = list;
        }

        public static class Image {
            private String pic;

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

        }
    }
}
