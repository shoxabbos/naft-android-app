package uz.itmaker.naft.Model;

public class UploadImage {
    String name;
    String type;
    String base64_string;

    public UploadImage(String name, String type, String base64_string) {
        this.name = name;
        this.type = type;
        this.base64_string = base64_string;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBase64_string() {
        return base64_string;
    }

    public void setBase64_string(String base64_string) {
        this.base64_string = base64_string;
    }
}
