
package uz.itmaker.naft.Model.LoginResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Profile extends RealmObject {

    @SerializedName("pmeta")
    @Expose
    private Pmeta pmeta;
    @SerializedName("umeta")
    @Expose
    private Umeta umeta;

    public Pmeta getPmeta() {
        return pmeta;
    }

    public void setPmeta(Pmeta pmeta) {
        this.pmeta = pmeta;
    }

    public Umeta getUmeta() {
        return umeta;
    }

    public void setUmeta(Umeta umeta) {
        this.umeta = umeta;
    }

}
