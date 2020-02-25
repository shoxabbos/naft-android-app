
package uz.itmaker.naft.Model.Provider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Badge implements Serializable {

    @SerializedName("badget_url")
    @Expose
    private String badgetUrl;
    @SerializedName("badget_color")
    @Expose
    private String badgetColor;

    public String getBadgetUrl() {
        return badgetUrl;
    }

    public void setBadgetUrl(String badgetUrl) {
        this.badgetUrl = badgetUrl;
    }

    public String getBadgetColor() {
        return badgetColor;
    }

    public void setBadgetColor(String badgetColor) {
        this.badgetColor = badgetColor;
    }

}
