
package uz.itmaker.naft.Model.LoginResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Pmeta extends RealmObject {
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("profile_img")
    @Expose
    private String profileImg;
    @SerializedName("banner_img")
    @Expose
    private String bannerImg;
    @SerializedName("_tag_line")
    @Expose
    private String tagLine;
    @SerializedName("_gender")
    @Expose
    private String gender;
    @SerializedName("_is_verified")
    @Expose
    private String isVerified;
    @SerializedName("_featured_timestamp")
    @Expose
    private String featuredTimestamp;
    @SerializedName("_freelancer_type")
    @Expose
    private String freelancerType;
    @SerializedName("full_name")
    @Expose
    private String fullName;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getFeaturedTimestamp() {
        return featuredTimestamp;
    }

    public void setFeaturedTimestamp(String featuredTimestamp) {
        this.featuredTimestamp = featuredTimestamp;
    }

    public String getFreelancerType() {
        return freelancerType;
    }

    public void setFreelancerType(String freelancerType) {
        this.freelancerType = freelancerType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
