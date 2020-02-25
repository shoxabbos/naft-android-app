
package uz.itmaker.naft.Model.Provider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Review implements Serializable {

    @SerializedName("project_title")
    @Expose
    private String projectTitle;
    @SerializedName("post_date")
    @Expose
    private String postDate;
    @SerializedName("employer_image")
    @Expose
    private String employerImage;
    @SerializedName("_is_verified")
    @Expose
    private String isVerified;
    @SerializedName("employer_name")
    @Expose
    private String employerName;
    @SerializedName("project_location")
    @Expose
    private String projectLocation;
    @SerializedName("project_rating")
    @Expose
    private String projectRating;
    @SerializedName("review_content")
    @Expose
    private String reviewContent;
    @SerializedName("level_title")
    @Expose
    private String levelTitle;
    @SerializedName("level_sign")
    @Expose
    private Integer levelSign;

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getEmployerImage() {
        return employerImage;
    }

    public void setEmployerImage(String employerImage) {
        this.employerImage = employerImage;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getProjectLocation() {
        return projectLocation;
    }

    public void setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation;
    }

    public String getProjectRating() {
        return projectRating;
    }

    public void setProjectRating(String projectRating) {
        this.projectRating = projectRating;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

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
