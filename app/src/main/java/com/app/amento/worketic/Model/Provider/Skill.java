
package uz.itmaker.naft.Model.Provider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Skill implements Serializable {

    @SerializedName("skill_val")
    @Expose
    private String skillVal;
    @SerializedName("skill_name")
    @Expose
    private String skillName;

    public String getSkillVal() {
        return skillVal;
    }

    public void setSkillVal(String skillVal) {
        this.skillVal = skillVal;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

}
