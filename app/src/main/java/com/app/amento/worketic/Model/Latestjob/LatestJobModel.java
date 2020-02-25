
package uz.itmaker.naft.Model.Latestjob;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LatestJobModel implements Serializable {

    @SerializedName("favorit")
    @Expose
    private String favorit;
    @SerializedName("job_id")
    @Expose
    private Integer jobId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("_is_verified")
    @Expose
    private String isVerified;
    @SerializedName("_featured_job_string")
    @Expose
    private String featuredJobString;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("project_level")
    @Expose
    private ProjectLevel projectLevel;
    @SerializedName("project_type")
    @Expose
    private String projectType;
    @SerializedName("project_duration")
    @Expose
    private String projectDuration;
    @SerializedName("attanchents")
    @Expose
    private List<Attanchent> attanchents = null;
    @SerializedName("skills")
    @Expose
    private List<Skill> skills = null;
    @SerializedName("employer_name")
    @Expose
    private String employerName;
    @SerializedName("project_title")
    @Expose
    private String projectTitle;
    @SerializedName("project_content")
    @Expose
    private String projectContent;
    @SerializedName("featured_url")
    @Expose
    private String featuredUrl;
    @SerializedName("featured_color")
    @Expose
    private String featuredColor;

    public String getFavorit() {
        return favorit;
    }

    public void setFavorit(String favorit) {
        this.favorit = favorit;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getFeaturedJobString() {
        return featuredJobString;
    }

    public void setFeaturedJobString(String featuredJobString) {
        this.featuredJobString = featuredJobString;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ProjectLevel getProjectLevel() {
        return projectLevel;
    }

    public void setProjectLevel(ProjectLevel projectLevel) {
        this.projectLevel = projectLevel;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectDuration() {
        return projectDuration;
    }

    public void setProjectDuration(String projectDuration) {
        this.projectDuration = projectDuration;
    }

    public List<Attanchent> getAttanchents() {
        return attanchents;
    }

    public void setAttanchents(List<Attanchent> attanchents) {
        this.attanchents = attanchents;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    public String getFeaturedUrl() {
        return featuredUrl;
    }

    public void setFeaturedUrl(String featuredUrl) {
        this.featuredUrl = featuredUrl;
    }

    public String getFeaturedColor() {
        return featuredColor;
    }

    public void setFeaturedColor(String featuredColor) {
        this.featuredColor = featuredColor;
    }

}
