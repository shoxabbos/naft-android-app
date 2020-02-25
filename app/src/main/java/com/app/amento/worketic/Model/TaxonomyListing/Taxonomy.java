
package uz.itmaker.naft.Model.TaxonomyListing;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Taxonomy {

    @SerializedName("skills")
    @Expose
    private List<Skill> skills = null;
    @SerializedName("locations")
    @Expose
    private List<Location> locations = null;
    @SerializedName("languages")
    @Expose
    private List<Language> languages = null;
    @SerializedName("department")
    @Expose
    private List<Department> department = null;
    @SerializedName("project_cat")
    @Expose
    private List<ProjectCat> projectCat = null;
    @SerializedName("rates")
    @Expose
    private List<Rate> rates = null;
    @SerializedName("english_levels")
    @Expose
    private List<EnglishLevel> englishLevels = null;
    @SerializedName("freelancer_level")
    @Expose
    private List<FreelancerLevel> freelancerLevel = null;
    @SerializedName("duration_list")
    @Expose
    private List<DurationList> durationList = null;
    @SerializedName("no_of_employes")
    @Expose
    private List<NoOfEmploye> noOfEmployes = null;

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<Department> getDepartment() {
        return department;
    }

    public void setDepartment(List<Department> department) {
        this.department = department;
    }

    public List<ProjectCat> getProjectCat() {
        return projectCat;
    }

    public void setProjectCat(List<ProjectCat> projectCat) {
        this.projectCat = projectCat;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public List<EnglishLevel> getEnglishLevels() {
        return englishLevels;
    }

    public void setEnglishLevels(List<EnglishLevel> englishLevels) {
        this.englishLevels = englishLevels;
    }

    public List<FreelancerLevel> getFreelancerLevel() {
        return freelancerLevel;
    }

    public void setFreelancerLevel(List<FreelancerLevel> freelancerLevel) {
        this.freelancerLevel = freelancerLevel;
    }

    public List<DurationList> getDurationList() {
        return durationList;
    }

    public void setDurationList(List<DurationList> durationList) {
        this.durationList = durationList;
    }

    public List<NoOfEmploye> getNoOfEmployes() {
        return noOfEmployes;
    }

    public void setNoOfEmployes(List<NoOfEmploye> noOfEmployes) {
        this.noOfEmployes = noOfEmployes;
    }

}
