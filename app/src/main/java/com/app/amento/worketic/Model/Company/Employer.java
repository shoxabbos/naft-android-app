
package uz.itmaker.naft.Model.Company;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employer implements Serializable {

    @SerializedName("favorit")
    @Expose
    private String favorit;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("employ_id")
    @Expose
    private String employId;
    @SerializedName("profile_id")
    @Expose
    private Integer profileId;
    @SerializedName("company_link")
    @Expose
    private String companyLink;
    @SerializedName("profile_img")
    @Expose
    private String profileImg;
    @SerializedName("User_profileID")
    @Expose
    private Integer userProfileID;
    @SerializedName("banner_img")
    @Expose
    private String bannerImg;
    @SerializedName("employer_des")
    @Expose
    private String employerDes;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("_longitude")
    @Expose
    private String longitude;
    @SerializedName("_latitude")
    @Expose
    private String latitude;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("_address")
    @Expose
    private String address;
    @SerializedName("_tag_line")
    @Expose
    private String tagLine;
    @SerializedName("_following_employers")
    @Expose
    private List<Object> followingEmployers = null;
    @SerializedName("_saved_projects")
    @Expose
    private String savedProjects;
    @SerializedName("_is_verified")
    @Expose
    private String isVerified;
//    @SerializedName("_featured_timestamp")
//    @Expose
//    private String featuredTimestamp;
//    @SerializedName("projects")
//    @Expose
//    private List<Project> projects = null;

    public String getFavorit() {
        return favorit;
    }

    public void setFavorit(String favorit) {
        this.favorit = favorit;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getEmployId() {
        return employId;
    }

    public void setEmployId(String employId) {
        this.employId = employId;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getCompanyLink() {
        return companyLink;
    }

    public void setCompanyLink(String companyLink) {
        this.companyLink = companyLink;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public Integer getUserProfileID() {
        return userProfileID;
    }

    public void setUserProfileID(Integer userProfileID) {
        this.userProfileID = userProfileID;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public String getEmployerDes() {
        return employerDes;
    }

    public void setEmployerDes(String employerDes) {
        this.employerDes = employerDes;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public List<Object> getFollowingEmployers() {
        return followingEmployers;
    }

    public void setFollowingEmployers(List<Object> followingEmployers) {
        this.followingEmployers = followingEmployers;
    }

    public String getSavedProjects() {
        return savedProjects;
    }

    public void setSavedProjects(String savedProjects) {
        this.savedProjects = savedProjects;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

//    public String getFeaturedTimestamp() {
//        return featuredTimestamp;
//    }
//
//    public void setFeaturedTimestamp(String featuredTimestamp) {
//        this.featuredTimestamp = featuredTimestamp;
//    }

//    public List<Project> getProjects() {
//        return projects;
//    }
//
//    public void setProjects(List<Project> projects) {
//        this.projects = projects;
//    }

}
