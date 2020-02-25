
package uz.itmaker.naft.Model.Latestjob;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Skill implements Serializable {

    @SerializedName("skill_link")
    @Expose
    private String skillLink;
    @SerializedName("skill_name")
    @Expose
    private String skillName;

    public String getSkillLink() {
        return skillLink;
    }

    public void setSkillLink(String skillLink) {
        this.skillLink = skillLink;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

}
