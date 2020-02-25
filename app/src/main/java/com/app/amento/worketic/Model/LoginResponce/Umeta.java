
package uz.itmaker.naft.Model.LoginResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Umeta extends RealmObject {

    @SerializedName("profile_id")
    @Expose
    private Integer profileId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_login")
    @Expose
    private String userLogin;
    @SerializedName("user_pass")
    @Expose
    private String userPass;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
