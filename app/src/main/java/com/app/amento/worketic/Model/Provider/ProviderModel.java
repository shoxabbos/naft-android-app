
package uz.itmaker.naft.Model.Provider;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProviderModel implements Serializable {

    @SerializedName("favorit")
    @Expose
    private String favorit;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("profile_id")
    @Expose
    private Integer profileId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("member_since")
    @Expose
    private String memberSince;
    @SerializedName("freelancer_link")
    @Expose
    private String freelancerLink;
    @SerializedName("profile_img")
    @Expose
    private String profileImg;
    @SerializedName("total_earnings")
    @Expose
    private String totalEarnings;
    @SerializedName("banner_img")
    @Expose
    private String bannerImg;
    @SerializedName("badge")
    @Expose
    private Badge badge;
    @SerializedName("rating_filter")
    @Expose
    private String ratingFilter;
    @SerializedName("wt_average_rating")
    @Expose
    private Integer wtAverageRating;
    @SerializedName("wt_total_rating")
    @Expose
    private Integer wtTotalRating;
    @SerializedName("wt_total_percentage")
    @Expose
    private Integer wtTotalPercentage;
    @SerializedName("_projects")
    @Expose
    private List<Project> projects = null;
    @SerializedName("_awards")
    @Expose
    private List<Award> awards = null;
    @SerializedName("_educations")
    @Expose
    private List<Education> educations = null;
    @SerializedName("_experience")
    @Expose
    private List<Experience> experience = null;
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
    @SerializedName("_gender")
    @Expose
    private String gender;
//    @SerializedName("_following_employers")
//    @Expose
//    private List<String> followingEmployers = null;
//    @SerializedName("_saved_projects")
//    @Expose
//    private List<String> savedProjects = null;
    @SerializedName("_is_verified")
    @Expose
    private String isVerified;
//    @SerializedName("_languages")
//    @Expose
//    private String languages;
    @SerializedName("_english_level")
    @Expose
    private String englishLevel;
    @SerializedName("_profile_blocked")
    @Expose
    private String profileBlocked;
    @SerializedName("_profile_searchable")
    @Expose
    private String profileSearchable;
//    @SerializedName("_featured_timestamp")
//    @Expose
//    private FeaturedTimestamp featuredTimestamp;
    @SerializedName("_freelancer_type")
    @Expose
    private String freelancerType;
    @SerializedName("_categories")
    @Expose
    private String categories;
    @SerializedName("_perhour_rate")
    @Expose
    private String perhourRate;
    @SerializedName("skills")
    @Expose
    private List<Skill> skills = null;
    @SerializedName("completed_jobs")
    @Expose
    private Integer completedJobs;
    @SerializedName("ongoning_jobs")
    @Expose
    private Integer ongoningJobs;
    @SerializedName("cancelled_jobs")
    @Expose
    private Integer cancelledJobs;
    @SerializedName("reviews")
    @Expose
    private List<Review> reviews = null;


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
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }

    public String getFreelancerLink() {
        return freelancerLink;
    }

    public void setFreelancerLink(String freelancerLink) {
        this.freelancerLink = freelancerLink;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
    public String getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(String totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public String getRatingFilter() {
        return ratingFilter;
    }

    public void setRatingFilter(String ratingFilter) {
        this.ratingFilter = ratingFilter;
    }

    public Integer getWtAverageRating() {
        return wtAverageRating;
    }

    public void setWtAverageRating(Integer wtAverageRating) {
        this.wtAverageRating = wtAverageRating;
    }

    public Integer getWtTotalRating() {
        return wtTotalRating;
    }

    public void setWtTotalRating(Integer wtTotalRating) {
        this.wtTotalRating = wtTotalRating;
    }

    public Integer getWtTotalPercentage() {
        return wtTotalPercentage;
    }

    public void setWtTotalPercentage(Integer wtTotalPercentage) {
        this.wtTotalPercentage = wtTotalPercentage;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Award> getAwards() {
        return awards;
    }

    public void setAwards(List<Award> awards) {
        this.awards = awards;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<Experience> getExperience() {
        return experience;
    }

    public void setExperience(List<Experience> experience) {
        this.experience = experience;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
//
//    public List<String> getFollowingEmployers() {
//        return followingEmployers;
//    }
//
//    public void setFollowingEmployers(List<String> followingEmployers) {
//        this.followingEmployers = followingEmployers;
//    }
//
//    public List<String> getSavedProjects() {
//        return savedProjects;
//    }
//
//    public void setSavedProjects(List<String> savedProjects) {
//        this.savedProjects = savedProjects;
//    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

//    public String getLanguages() {
//        return languages;
//    }
//
//    public void setLanguages(String languages) {
//        this.languages = languages;
//    }

    public String getEnglishLevel() {
        return englishLevel;
    }

    public void setEnglishLevel(String englishLevel) {
        this.englishLevel = englishLevel;
    }

    public String getProfileBlocked() {
        return profileBlocked;
    }

    public void setProfileBlocked(String profileBlocked) {
        this.profileBlocked = profileBlocked;
    }

    public String getProfileSearchable() {
        return profileSearchable;
    }

    public void setProfileSearchable(String profileSearchable) {
        this.profileSearchable = profileSearchable;
    }

//    public FeaturedTimestamp getFeaturedTimestamp() {
//        return featuredTimestamp;
//    }
//
//    public void setFeaturedTimestamp(FeaturedTimestamp featuredTimestamp) {
//        this.featuredTimestamp = featuredTimestamp;
//    }

    public String getFreelancerType() {
        return freelancerType;
    }

    public void setFreelancerType(String freelancerType) {
        this.freelancerType = freelancerType;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getPerhourRate() {
        return perhourRate;
    }

    public void setPerhourRate(String perhourRate) {
        this.perhourRate = perhourRate;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public Integer getCompletedJobs() {
        return completedJobs;
    }

    public void setCompletedJobs(Integer completedJobs) {
        this.completedJobs = completedJobs;
    }

    public Integer getOngoningJobs() {
        return ongoningJobs;
    }

    public void setOngoningJobs(Integer ongoningJobs) {
        this.ongoningJobs = ongoningJobs;
    }

    public Integer getCancelledJobs() {
        return cancelledJobs;
    }

    public void setCancelledJobs(Integer cancelledJobs) {
        this.cancelledJobs = cancelledJobs;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

}
