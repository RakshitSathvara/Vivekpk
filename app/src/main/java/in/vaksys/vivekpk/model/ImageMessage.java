package in.vaksys.vivekpk.model;

/**
 * Created by vishal on 08/06/2016.
 */
public class ImageMessage {

    public String getDocumenttype() {
        return documenttype;
    }

    public ImageMessage(String documenttype, String vehicle_id, String gallery) {

        this.documenttype = documenttype;
        this.vehicle_id = vehicle_id ;
        this.pic_gallery_camera = gallery;
    }

    private String documenttype;
    private String vehicle_id;

    public String getPic_gallery_camera() {
        return pic_gallery_camera;
    }

    private String pic_gallery_camera;


    public String getVehicle_id() {
        return vehicle_id;
    }
}
