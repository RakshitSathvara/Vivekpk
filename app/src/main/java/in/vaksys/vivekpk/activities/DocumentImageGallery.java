package in.vaksys.vivekpk.activities;

import android.app.Activity;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.adapter.GalleryImageAdapter;
import in.vaksys.vivekpk.adapter.ImageAdapter;
import in.vaksys.vivekpk.dbPojo.UserImages;
import in.vaksys.vivekpk.extras.AppConfig;
import in.vaksys.vivekpk.extras.GetPathImage;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.extras.ResetApi;
import in.vaksys.vivekpk.extras.VolleyHelper;
import in.vaksys.vivekpk.model.ImageMessage;
import in.vaksys.vivekpk.model.ReplaceImage;
import in.vaksys.vivekpk.model.data;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vishal on 08/06/2016.
 */
public class DocumentImageGallery extends AppCompatActivity {
    @Bind(R.id.rec_document_image_gallery)
    RecyclerView document_image_gallery;
    @Bind(R.id.img_cancelGallery)
    ImageView img_cancelGallery;
    private Realm realm;
    private RealmResults<UserImages> results;
    private GalleryImageAdapter imageAdapter;
    public static int PICK_IMAGE_REQUEST = 1;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static String timeStamp, myImageUrl;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final String IMAGE_DIRECTORY_NAME = "Photos";
    Uri picUri;
    private Uri filePath;
    String realPath;

    private static final String TAG = "DocumentImageGallery";

    String img_id;
    String pic_gallery_camera;
    private String documenttype;
    private String v_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driving_license_gallery);

        ButterKnife.bind(this);


        realm = Realm.getDefaultInstance();

        document_image_gallery.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(DocumentImageGallery.this, LinearLayoutManager.VERTICAL, false);
        document_image_gallery.setLayoutManager(manager);
        results = realm.where(UserImages.class).findAll();
        imageAdapter = new GalleryImageAdapter(DocumentImageGallery.this, results);
        document_image_gallery.setAdapter(imageAdapter);
        imageAdapter.notifyDataSetChanged();

        results.addChangeListener(new RealmChangeListener<RealmResults<UserImages>>() {
            @Override
            public void onChange(RealmResults<UserImages> element) {
                imageAdapter.notifyDataSetChanged();
            }
        });


        img_cancelGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }





    @Subscribe
    public void onEvent(ReplaceImage message) {
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
                Toast.makeText(DocumentImageGallery.this,
                        "You cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(DocumentImageGallery.this,
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

                    realPath = GetPathImage.getRealPathFromURI_BelowAPI11(DocumentImageGallery.this, filePath);
                    //   myApplication.showLog("version sdk < 11", realPath);
                }
                // SDK >= 11 && SDK < 19
                else if (Build.VERSION.SDK_INT < 19) {


                    realPath = GetPathImage.getRealPathFromURI_API11to18(DocumentImageGallery.this, data.getData());
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

//    private void uploadwithRetrofit(String realPath, final String documenttype, final String img_id, final String v_id) {
//
//        MyApplication.getInstance().DialogMessage("Upload Documents...");
//        MyApplication.getInstance().showDialog();
//
//        final String imgname = realPath.substring(realPath.lastIndexOf("/") + 1);
//        MyApplication.getInstance().showLog("name", imgname);
//
//        final String rnd = "Licence" + String.valueOf(GenerteRandomNumber());
//
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), new File(realPath));
//
//        // MultipartBody.Part is used to send also the actual file name
//
//        //note: file=key , vishal = iamgennnnname(randam) , reuestfile = pick image url
//
//        MultipartBody.Part body =
//                MultipartBody.Part.createFormData("file", rnd, requestFile);
////        MultipartBody.Part body =
////                MultipartBody.Part.create(requestFile);
//
////
//        OkHttpClient okHttpClient1 = new OkHttpClient().newBuilder()
//                .connectTimeout(120, TimeUnit.SECONDS)
//                .writeTimeout(120, TimeUnit.SECONDS)
//                .readTimeout(120, TimeUnit.SECONDS)
//                .build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(AppConfig.URL_UPLOAD_DOC)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient1)
//                .build();
//
//        ResetApi resetApi = retrofit.create(ResetApi.class);
//
//        retrofit2.Call<data> call = resetApi.getTasks("52d8c0efea5039cd0d778db7521889cf", body);
//
//        call.enqueue(new retrofit2.Callback<data>() {
//            @Override
//            public void onResponse(retrofit2.Call<data> call, Response<data> response) {
//
//                MyApplication.getInstance().hideDialog();
//
//                int code = response.code();
//                MyApplication.getInstance().showLog("code", "" + code);
//
//                //    Toast.makeText(getActivity(), "Respose Code " + code, Toast.LENGTH_SHORT).show();
//
//                if (response.code() == 500) {
//
//                    Toast.makeText(DocumentImageGallery.this, "Server Side Error", Toast.LENGTH_SHORT).show();
//                }
//
//                if (response.code() == 200) {
//
//                    data myResp = response.body();
//                    boolean error = myResp.isError();
//
//                    if (!error) {
//
//                        String imageurl = myResp.getResult();
//
//                        MyApplication.getInstance().showLog("respose", imageurl);
//                        //     Toast.makeText(getActivity(), "Url" + imageurl, Toast.LENGTH_SHORT).show();
//
//                        AddVehicalesDocument(imageurl, documenttype, img_id, rnd, v_id);
//
//
//                    } else {
//                        MyApplication.getInstance().hideDialog();
//
//                        Toast.makeText(DocumentImageGallery.this, "Error For Uploading Documnent", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                } else {
//
//                    MyApplication.getInstance().hideDialog();
//
//                    Toast.makeText(DocumentImageGallery.this, "Error Some Fatch Data", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<data> call, Throwable t) {
//
//                MyApplication.getInstance().hideDialog();
//
//                if (t instanceof TimeoutError || t instanceof NoConnectionError) {
//                    MyApplication.getInstance().ErrorSnackBar(DocumentImageGallery.this);
//                }
//
//            }
//        });
//
//    }
//
//
//    private void AddVehicalesDocument(final String imageurl, final String documenttype, final String img_id, final String imgname, final String v_id) {
//
//
//        String tag_string_req = "req_delete_vehicle";
//
//        MyApplication.getInstance().DialogMessage("Update  Document...");
//        MyApplication.getInstance().showDialog();
//
//        StringRequest strReq = new StringRequest(Request.Method.PUT,
//                AppConfig.URL_UPDATE_DOC, new com.android.volley.Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                MyApplication.getInstance().hideDialog();
//
//                MyApplication.getInstance().showLog("respodse", response);
//
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    boolean error = jObj.getBoolean("error");
//                    Log.e(TAG, "onResponse: " + jObj.toString());
//
//                    // Check for error node in json
//                    if (!error) {
//                        Toast.makeText(DocumentImageGallery.this,
//                                "Document updated... ", Toast.LENGTH_LONG).show();
//
//                        // parsing the user profile information
//
//
//                        UpdateImageIntoDatabase(img_id, imageurl, imgname);
//
//                        // DeleteContactToDatabase(contactid);
//                    } else {
//                        // Error in login. Get the error message
//                        String errorMsg = jObj.getString("message");
//                        Toast.makeText(DocumentImageGallery.this,
//                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    // JSON error
//                    e.printStackTrace();
//                    Toast.makeText(DocumentImageGallery.this, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                Log.e(TAG, "Login Error: " + error.getMessage());
//                MyApplication.getInstance().ErrorSnackBar((Activity) DocumentImageGallery.this);
//                MyApplication.getInstance().hideDialog();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> stringMap = new HashMap<>();
//                stringMap.put("id", img_id);
//                stringMap.put("url", imageurl);
//
//
//                return stringMap;
//
////                vehicleNo
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
//                MyApplication.getInstance().showLog(TAG, String.valueOf("passed auth"));
//                return headers;
//
//            }
//
//
//        };
//        // Adding request to request queue
//        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
//
//
//    }
//
//    private void UpdateImageIntoDatabase(String img_id, String imageurl, String name) {
//        realm = Realm.getDefaultInstance();
//
//        realm.beginTransaction();
//        UserImages userImages = realm.where(UserImages.class).equalTo("id", img_id).findFirst();
//
//        userImages.setImageName(name);
//        userImages.setImagesurl(imageurl);
//
//        realm.commitTransaction();
//
//        results.addChangeListener(new RealmChangeListener<RealmResults<UserImages>>() {
//            @Override
//            public void onChange(RealmResults<UserImages> element) {
//                imageAdapter.notifyDataSetChanged();
//            }
//        });
//
//    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                MyApplication.getInstance().showLog(TAG, "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");
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


    private int GenerteRandomNumber() {
        Random r = new Random();
        return r.nextInt(9999 - 1000) + 1000;
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", filePath);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
