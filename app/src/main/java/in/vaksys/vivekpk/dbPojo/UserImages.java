package in.vaksys.vivekpk.dbPojo;

import io.realm.RealmObject;

/**
 * Created by Harsh on 20-05-2016.
 */
public class UserImages extends RealmObject {

    public String getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(String vehicleid) {
        this.vehicleid = vehicleid;
    }

    public String vehicleid;

    public String getImagesurl() {
        return imagesurl;
    }

    public void setImagesurl(String imagesurl) {
        this.imagesurl = imagesurl;
    }

    private String imagesurl;
    private String id;
    private String ImageName;

    public String getImageType() {
        return ImageType;
    }

    public void setImageType(String imageType) {
        ImageType = imageType;
    }

    private String ImageType;


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
