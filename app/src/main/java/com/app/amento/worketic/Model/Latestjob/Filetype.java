
package uz.itmaker.naft.Model.Latestjob;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Filetype implements Serializable {

    @SerializedName("ext")
    @Expose
    private String ext;
    @SerializedName("type")
    @Expose
    private String type;

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
