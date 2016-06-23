package in.vaksys.vivekpk.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.adapter.FullGalleryImageAdapter;
import in.vaksys.vivekpk.adapter.GalleryImageAdapter;
import in.vaksys.vivekpk.dbPojo.UserImages;
import in.vaksys.vivekpk.extras.GetPathImage;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.extras.VolleyHelper;
import in.vaksys.vivekpk.model.ReplaceImage;
import in.vaksys.vivekpk.model.ReplaceImageFull;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by vishal on 16/06/2016.
 */
public class FullDocumentImage extends AppCompatActivity{
    private Realm realm;
    @Bind(R.id.rec_document_image_gallery)
    RecyclerView document_image_gallery;
    @Bind(R.id.img_cancelGallery)
    ImageView imageView;
    private RealmResults<UserImages> results;
    private FullGalleryImageAdapter imageAdapter;
    @Bind(R.id.ll_toolbar)
    LinearLayout linearLayout;

    String img_id;
    String pic_gallery_camera;
    private String documenttype;
    private String v_id;
    public static int PICK_IMAGE_REQUEST = 1;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static String timeStamp, myImageUrl;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final String IMAGE_DIRECTORY_NAME = "Photos";
    Uri picUri;
    private Uri filePath;
    String realPath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driving_license_gallery);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();

        linearLayout = (LinearLayout) findViewById(R.id.ll_toolbar);

        linearLayout.setVisibility(View.GONE);

        document_image_gallery.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(FullDocumentImage.this, LinearLayoutManager.HORIZONTAL, false);
        document_image_gallery.setLayoutManager(manager);
        results = realm.where(UserImages.class).findAll();
        imageAdapter = new FullGalleryImageAdapter(FullDocumentImage.this, results);
        document_image_gallery.setAdapter(imageAdapter);
        imageAdapter.notifyDataSetChanged();
      //  license_gallery_full_view

        results.addChangeListener(new RealmChangeListener<RealmResults<UserImages>>() {
            @Override
            public void onChange(RealmResults<UserImages> element) {
                imageAdapter.notifyDataSetChanged();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        //   myApplication.showLog(TAG, " " + resultCode + " " + requestCode + " " + data.toString());
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == -1) {
                // successfully captured the image
                // display it in image vie
                String type = documenttype;
                String v_idd = v_id ;
                Uri uri = picUri;
                String imgid = img_id;

                String captuepath = uri.getPath();

//                  uploadwithRetrofit(captuepath, type,imgid, v_id);
                // uploadwithRetrofit(captuepath, documenttype, img_id, v_id);

                VolleyHelper helper = new VolleyHelper(this);
                helper.uploadwithRetrofit(captuepath, documenttype, img_id, v_id);
                //   myApplication.showLog("camarea image path ", "" + captuepath);


            } else if (resultCode == 0) {
                // user cancelled Image capture
                Toast.makeText(FullDocumentImage.this,
                        "You cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(FullDocumentImage.this,
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
            if (resultCode == -1) {

//                String type = documenttype;
//                String v_id = vehicle_id;
//
//                myApplication.showLog("type and id----------->", type + v_id);

                filePath = data.getData();
                //  myApplication.showLog("file paths", "" + filePath);


                ///  String imagePath = getRealPathFromURI(filePath);

                if (Build.VERSION.SDK_INT < 11) {


                    // String imagePath = getRealPathFromURI(filePath);

                    realPath = GetPathImage.getRealPathFromURI_BelowAPI11(FullDocumentImage.this, filePath);
                    //   myApplication.showLog("version sdk < 11", realPath);
                }
                // SDK >= 11 && SDK < 19
                else if (Build.VERSION.SDK_INT < 19) {


                    realPath = GetPathImage.getRealPathFromURI_API11to18(FullDocumentImage.this, data.getData());
                    //  myApplication.showLog("version SDK >= 11 && SDK < 19", realPath);
                    // SDK > 19 (Android 4.4)
                } else {

                    //// TODO: 10/06/2016 change real path
                    realPath = getRealPathFromURI(filePath);
                    //  myApplication.showLog("versionSDK > 19 (Android 4.4)", realPath);

                }
                // String imagePath = getRealPathFromURI(filePath);

                //      myApplication.showLog("file image  paths", imagePath);


                VolleyHelper helper = new VolleyHelper(this);
                helper.uploadwithRetrofit(realPath, documenttype, img_id, v_id);


                //  uploadwithRetrofit(realPath, documenttype, img_id, v_id);

                //   calculateFileSize(realPath);


                //  results = realm.where(UserImages.class).findAll();
//                if (results.size() < 5) {
//                    //   bitmap = MediaStore.Images.Media.getBitmap(DocumentImageGallery.this.getContentResolver(), filePath);
//
//
//                    // uploadimage(imagePath);
//
//                    //   Send(bitmap);
//                    /*String encoded = BitmapToString(bitmap);
//
//                    SendToServer(encoded);
//                    imageAdapter.saveImageToDatabase(encoded);
////                        myApplication.showLog(TAG, "preview " + results.size());
////                        myApplication.showLog(TAG, "pick");
//                    if (results.size() == 1) {
//                        SetImagesViews();
//                    }*/
//                } else {
//                    Toast.makeText(DocumentImageGallery.this,
//                            "Sorry! You can't add more then 4 Driving Licences.", Toast.LENGTH_SHORT)
//                            .show();
//                }
//            } else if (resultCode == 0) {
//                Toast.makeText(DocumentImageGallery.this,
//                        "You cancelled image Selcetion", Toast.LENGTH_SHORT)
//                        .show();
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Subscribe
    public void onEvent(ReplaceImageFull message) {
        Log.e("car datata", message.getPic_gallery_camera());
//        Toast.makeText(DocumentImageGallery.this, message.getDocumenttype(), Toast.LENGTH_SHORT).show();
//

        img_id = message.getImg_id();
        pic_gallery_camera = message.getPic_gallery_camera();
        documenttype = message.getDocument_type();
        v_id = message.getVehicalid();

        if (pic_gallery_camera.equalsIgnoreCase("gallery")) {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

        if (pic_gallery_camera.equalsIgnoreCase("camera")) {

            Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

            File file = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            picUri = Uri.fromFile(file); // create
            i.putExtra(MediaStore.EXTRA_OUTPUT, picUri); // set the image file

            startActivityForResult(i, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", filePath);
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                MyApplication.getInstance().showLog("TAG", "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
// Create a media file name
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


}
