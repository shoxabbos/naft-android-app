
package uz.itmaker.naft.Model.Latestjob;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Attanchent implements Serializable {

    @SerializedName("document_name")
    @Expose
    private String documentName;
//    @SerializedName("file_size")
//    @Expose
//    private Integer fileSize;
//    @SerializedName("filetype")
//    @Expose
//    private Filetype filetype;
//    @SerializedName("extension")
//    @Expose
//    private String extension;
    @SerializedName("url")
    @Expose
    private String url;

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
//
//    public Integer getFileSize() {
//        return fileSize;
//    }
//
//    public void setFileSize(Integer fileSize) {
//        this.fileSize = fileSize;
//    }
//
//    public Filetype getFiletype() {
//        return filetype;
//    }
//
//    public void setFiletype(Filetype filetype) {
//        this.filetype = filetype;
//    }
//
//    public String getExtension() {
//        return extension;
//    }
//
//    public void setExtension(String extension) {
//        this.extension = extension;
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
