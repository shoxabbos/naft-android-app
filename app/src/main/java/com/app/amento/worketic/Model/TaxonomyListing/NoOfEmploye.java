
package uz.itmaker.naft.Model.TaxonomyListing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoOfEmploye {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("search_title")
    @Expose
    private String searchTitle;
    @SerializedName("value")
    @Expose
    private Integer value;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSearchTitle() {
        return searchTitle;
    }

    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
