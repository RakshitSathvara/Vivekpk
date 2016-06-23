package in.vaksys.vivekpk.model;

/**
 * Created by vishal on 08/06/2016.
 */
public class ReplaceImageFull {

    public String getPic_gallery_camera() {
        return pic_gallery_camera;
    }

    public String getImg_id() {
        return img_id;
    }

    private String pic_gallery_camera;
    private String img_id;
    private String document_type;

    public String getVehicalid() {
        return vehicalid;
    }

    private String vehicalid;

    public String getDocument_type() {
        return document_type;
    }



    public ReplaceImageFull(String pic_gallery_camera, String id , String documenttype , String vehicalid) {
        this.pic_gallery_camera = pic_gallery_camera;
        this.img_id = id;
        this.document_type = documenttype;
        this.vehicalid = vehicalid;
    }




}
