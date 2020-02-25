
package uz.itmaker.naft.Model.EmployerOfferProjects;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeerOfferProjects {

    @SerializedName("projects")
    @Expose
    private List<Project> projects = null;
    @SerializedName("type")
    @Expose
    private String type;

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
