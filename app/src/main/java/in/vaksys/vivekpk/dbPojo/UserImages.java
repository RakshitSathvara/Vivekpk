package in.vaksys.vivekpk.dbPojo;

import io.realm.RealmObject;

/**
 * Created by Harsh on 20-05-2016.
 */
public class UserImages extends RealmObject {
    private String images;
    private String id;
    private String ImageName;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }
}
