package build.dream.common.models.weixin;

import build.dream.common.models.BasicModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateMenuModel extends BasicModel {
    @SerializedName(value = "button", alternate = "buttons")
    private List<Button> buttons;

    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

    public static class Button extends BasicModel {
        @SerializedName(value = "sub_button", alternate = "subButtons")
        private List<SubButton> subButtons;

        private String type;

        private String name;

        private String key;

        private String url;

        @SerializedName(value = "media_id", alternate = "mediaId")
        private String mediaId;

        @SerializedName(value = "appid", alternate = "appId")
        private String appId;

        @SerializedName(value = "pagepath", alternate = "pagePath")
        private String pagePath;

        public List<SubButton> getSubButtons() {
            return subButtons;
        }

        public void setSubButtons(List<SubButton> subButtons) {
            this.subButtons = subButtons;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getPagePath() {
            return pagePath;
        }

        public void setPagePath(String pagePath) {
            this.pagePath = pagePath;
        }
    }

    public static class SubButton extends BasicModel {
        private String type;

        private String name;

        private String key;

        private String url;

        @SerializedName(value = "media_id", alternate = "mediaId")
        private String mediaId;

        @SerializedName(value = "appid", alternate = "appId")
        private String appId;

        @SerializedName(value = "pagepath", alternate = "pagePath")
        private String pagePath;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getPagePath() {
            return pagePath;
        }

        public void setPagePath(String pagePath) {
            this.pagePath = pagePath;
        }
    }
}
