
package uz.itmaker.naft.Model.Latestjob;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProjectLevel implements Serializable {

    @SerializedName("level_title")
    @Expose
    private String levelTitle;
    @SerializedName("level_sign")
    @Expose
    private Integer levelSign;

    public String getLevelTitle() {
        return levelTitle;
    }

    public void setLevelTitle(String levelTitle) {
        this.levelTitle = levelTitle;
    }

    public Integer getLevelSign() {
        return levelSign;
    }

    public void setLevelSign(Integer levelSign) {
        this.levelSign = levelSign;
    }

}
