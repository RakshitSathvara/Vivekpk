package in.vaksys.vivekpk.fragments;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.activities.DocumentImageGallery;
import in.vaksys.vivekpk.adapter.DocumentDetailsRecyclerViewAdapter;
import in.vaksys.vivekpk.adapter.ImageAdapter;
import in.vaksys.vivekpk.dbPojo.UserImages;
import in.vaksys.vivekpk.dbPojo.VehicleDetails;
import in.vaksys.vivekpk.extras.AppConfig;
import in.vaksys.vivekpk.extras.GetPathImage;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.extras.ResetApi;
import in.vaksys.vivekpk.model.ImageMessage;
import in.vaksys.vivekpk.model.Message;
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
 * A simple {@link Fragment} subclass.
 */
public class DocumentFragment extends Fragment {
    private static final String TAG = "DocumentFragment";
    private LinearLayout linearAddDrivingLicense;
    private LinearLayout linearAddGallery;
    private ImageView imgAddGallery;
    private LinearLayout linearAddCamera;
    private ImageView imgAddCamera;

    private LinearLayout linearYourDrivingLicense;
    private ImageView imgLicenseImgEdit;
    private ImageView imgLicenseImgOne;
    private ImageView imgLicenseImgTwo;
    private ImageView imgLicenseImThree;
    private ImageView imgLicenseImgFour;
    private LinearLayout linearRegisterVehicle;
    private LinearLayout linearRcBook;
    private LinearLayout linearInsurance;
    private LinearLayout linearEmission;
    private LinearLayout linearRcBills;
    private LinearLayout linearVehicleDeatilsListRaw;
    private ImageView imgEdit;
    private TextView tvDetailVehicelNumber;
    private TextView tvDetailVehicelBranch;
    private TextView tvDetailVehicelModel;
    private LinearLayout linearVehicelDocRcBook;
    private LinearLayout numberRcBook;
    private TextView tvRcBookCount;
    private LinearLayout linearVehicelDocInsurance;
    private LinearLayout numberInsurance;
    private TextView tvInssuranceCount;
    private LinearLayout linearVehicelDocEmission;
    private LinearLayout numberEmission;
    private TextView tvEmissionCount;
    private LinearLayout linearVehicelDocBills;
    private LinearLayout numberRcBills;
    private TextView tvBillsCount;

    private Button btnRegisterVehicle;
    private LinearLayout linearVehicleDetailsListRaw;

    public Uri fileUri;
    private static final String IMAGE_DIRECTORY_NAME = "Photos";
    public static int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static String timeStamp, myImageUrl;
    Bitmap bitmap;
    private Uri filePath;

    MyApplication myApplication;
    private Realm realm;

    OkHttpClient client;
    Uri picUri;
    RecyclerView DocumentDetailsRecyclerview;
    DocumentDetailsRecyclerViewAdapter documentDetailsRecyclerViewAdapter;
    RealmResults<VehicleDetails> detailsesResults;
    RealmResults<UserImages> imageDetails;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Bind(R.id.container)
    LinearLayout container;

    ImageAdapter imageAdapter;
    RecyclerView ImageRecyclerview;
    RealmResults<UserImages> results;
    //private MultiStateToggleButton multiStateToggleButton;
    private EventBus bus = EventBus.getDefault();


    String realPath;

    private String documenttype;
    private String vehicle_id;
    private String pic_gallery_camera;
    private String selection_type;


    public static DocumentFragment newInstance(int index) {
        DocumentFragment fragment = new DocumentFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_document, container, false);


        DocumentDetailsRecyclerview = (RecyclerView) view.findViewById(R.id.DocumentRecyclerView);


        BindViews(view);
        SetImagesViews();
        SetDocumentDetails();
        linearAddGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documenttype = "licence";
                showFileChooser();
            }
        });
        linearAddCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
//        linearYourDrivingLicense.setVisibility(View.GONE);
        myApplication.showLog(TAG, "create" + results.size());

        return view;
    }


    private void SetDocumentDetails() {
        DocumentDetailsRecyclerview.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        DocumentDetailsRecyclerview.setLayoutManager(manager);
        detailsesResults = realm.where(VehicleDetails.class).equalTo("type",selection_type).findAll();
        imageDetails = realm.where(UserImages.class).findAll();

        myApplication.showLog(TAG, " check---> " + imageDetails.size() + "  " + detailsesResults.size());
        myApplication.showLog(TAG, "inside details" + detailsesResults.size());
        if (detailsesResults.size() > 0) {

            documentDetailsRecyclerViewAdapter = new DocumentDetailsRecyclerViewAdapter(getActivity(), detailsesResults, imageDetails);
            DocumentDetailsRecyclerview.setAdapter(documentDetailsRecyclerViewAdapter);
            documentDetailsRecyclerViewAdapter.notifyDataSetChanged();


            detailsesResults.addChangeListener(new RealmChangeListener<RealmResults<VehicleDetails>>() {
                @Override
                public void onChange(RealmResults<VehicleDetails> element) {
                    documentDetailsRecyclerViewAdapter.notifyDataSetChanged();


                }
            });

        } else {
            myApplication.showLog(TAG, "details");
        }
    }


    private void SetImagesViews() {
        myApplication.showLog(TAG, "innerview");

        ImageRecyclerview.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        ImageRecyclerview.setLayoutManager(manager);
        results = realm.where(UserImages.class).equalTo("ImageType","licence").findAll();
        myApplication.showLog(TAG, "inside viwe" + results.size());
        if (results.size() > -1) {
            myApplication.showLog(TAG, "innerview11111");
            //     if (documenttype.equalsIgnoreCase("licence")) {
            linearYourDrivingLicense.setVisibility(View.VISIBLE);
            imageAdapter = new ImageAdapter(getActivity(), results);
            ImageRecyclerview.setAdapter(imageAdapter);
            imageAdapter.notifyDataSetChanged();
            //    } else {
            //      imageAdapter = new ImageAdapter(getActivity(), results);
            // }

        } else {
            myApplication.showLog(TAG, "innerview222222");
            linearYourDrivingLicense.setVisibility(View.GONE);
        }
    }

    private void BindViews(View view) {
        linearAddDrivingLicense = (LinearLayout) view.findViewById(R.id.linearAddDrivingLicense);
        linearAddGallery = (LinearLayout) view.findViewById(R.id.linear_addGallery);
        imgAddGallery = (ImageView) view.findViewById(R.id.img_addGallery);
        linearAddCamera = (LinearLayout) view.findViewById(R.id.linear_addCamera);
        imgAddCamera = (ImageView) view.findViewById(R.id.img_addCamera);
        linearYourDrivingLicense = (LinearLayout) view.findViewById(R.id.linearYourDrivingLicense);
        imgLicenseImgEdit = (ImageView) view.findViewById(R.id.img_licenseImgEdit);
       /* imgLicenseImgOne = (ImageView) view.findViewById(R.id.img_licenseImgOne);
        imgLicenseImgTwo = (ImageView) view.findViewById(R.id.img_licenseImgTwo);
        imgLicenseImThree = (ImageView) view.findViewById(R.id.img_licenseImThree);
        imgLicenseImgFour = (ImageView) view.findViewById(R.id.img_licenseImgFour);*/

        linearRegisterVehicle = (LinearLayout) view.findViewById(R.id.linearRegisterVehicle);
        linearRcBook = (LinearLayout) view.findViewById(R.id.linear_rcBook);
        linearInsurance = (LinearLayout) view.findViewById(R.id.linear_insurance);
        linearEmission = (LinearLayout) view.findViewById(R.id.linear_emission);
        linearRcBills = (LinearLayout) view.findViewById(R.id.linear_rcBills);
        linearVehicleDeatilsListRaw = (LinearLayout) view.findViewById(R.id.linearVehicleDeatilsListRaw);
       // imgEdit = (ImageView) view.findViewById(R.id.img_edit);
        tvDetailVehicelNumber = (TextView) view.findViewById(R.id.tv_detailVehicelNumber);
        tvDetailVehicelBranch = (TextView) view.findViewById(R.id.tv_detailVehicelBranch);
        tvDetailVehicelModel = (TextView) view.findViewById(R.id.tv_detailVehicelModel);
        linearVehicelDocRcBook = (LinearLayout) view.findViewById(R.id.linear_vehicelDocRcBook);
        numberRcBook = (LinearLayout) view.findViewById(R.id.number_rc_book);
        tvRcBookCount = (TextView) view.findViewById(R.id.tv_rcBookCount);
        linearVehicelDocInsurance = (LinearLayout) view.findViewById(R.id.linear_vehicelDocInsurance);
        numberInsurance = (LinearLayout) view.findViewById(R.id.number_insurance);
        tvInssuranceCount = (TextView) view.findViewById(R.id.tv_inssuranceCount);
        linearVehicelDocEmission = (LinearLayout) view.findViewById(R.id.linear_vehicelDocEmission);
        numberEmission = (LinearLayout) view.findViewById(R.id.number_emission);
        tvEmissionCount = (TextView) view.findViewById(R.id.tv_emissionCount);
        linearVehicelDocBills = (LinearLayout) view.findViewById(R.id.linear_vehicelDocBills);
        numberRcBills = (LinearLayout) view.findViewById(R.id.number_rc_bills);
        tvBillsCount = (TextView) view.findViewById(R.id.tv_billsCount);

        btnRegisterVehicle = (Button) view.findViewById(R.id.btn_registerVehicle);

        ImageRecyclerview = (RecyclerView) view.findViewById(R.id.ImagesRecyclerView);

        myApplication = MyApplication.getInstance();
        realm = Realm.getDefaultInstance();

        imgLicenseImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent h = new Intent(getActivity(), DocumentImageGallery.class);
                getActivity().startActivity(h);
            }
        });

    }

    public void showFileChooser() {

        results = realm.where(UserImages.class).equalTo("ImageType","licence").findAll();

        myApplication.showLog("total images --->",""+ results.size());

        if (results.size() == 4){

            Toast.makeText(getActivity(),
                    "Sorry! You can't add more then 4 Driving Licences.", Toast.LENGTH_SHORT)
                    .show();

        }else {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

    }

    private void captureImage() {


//        Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File f = new File(Environment.getExternalStorageDirectory(),);
//        chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//        fileUri = Uri.fromFile(f);
//        startActivityForResult(chooserIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

        results = realm.where(UserImages.class).equalTo("ImageType","licence").findAll();

        myApplication.showLog("total images --->",""+ results.size());

        if (results.size() == 4){

            Toast.makeText(getActivity(),
                    "Sorry! You can't add more then 4 Driving Licences.", Toast.LENGTH_SHORT)
                    .show();

        }else {

            Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

            File file = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            picUri = Uri.fromFile(file); // create
            i.putExtra(MediaStore.EXTRA_OUTPUT, picUri); // set the image file

            startActivityForResult(i, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }


//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//
//
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//
//        // start the image capture Intent
//        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", filePath);
    }

   /* @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        //   myApplication.showLog(TAG, " " + resultCode + " " + requestCode + " " + data.toString());




        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == -1) {
                // successfully captured the image
                // display it in image vie
                String type = documenttype;
                String v_id = vehicle_id;
                Uri uri = picUri;

                String captuepath = uri.getPath();

                uploadwithRetrofit(captuepath, type, v_id);


                myApplication.showLog("camarea image path ", "" + captuepath);


            } else if (resultCode == 0) {
                // user cancelled Image capture
                Toast.makeText(getActivity(),
                        "You cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getActivity(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
            if (resultCode == -1) {



                String type = documenttype;
                String v_id = vehicle_id;

                myApplication.showLog("type and id----------->", type + v_id);

                filePath = data.getData();
                myApplication.showLog("file paths", "" + filePath);


                ///  String imagePath = getRealPathFromURI(filePath);

                if (Build.VERSION.SDK_INT < 11) {


                    // String imagePath = getRealPathFromURI(filePath);

                    realPath = GetPathImage.getRealPathFromURI_BelowAPI11(getActivity(), filePath);
                    myApplication.showLog("version sdk < 11", realPath);
                }
                // SDK >= 11 && SDK < 19
                else if (Build.VERSION.SDK_INT < 19) {


                    realPath = GetPathImage.getRealPathFromURI_API11to18(getActivity(), data.getData());
                    myApplication.showLog("version SDK >= 11 && SDK < 19", realPath);
                    // SDK > 19 (Android 4.4)
                } else {

                    realPath = getRealPathFromURI(filePath);
                    myApplication.showLog("versionSDK > 19 (Android 4.4)", realPath);

                }
                // String imagePath = getRealPathFromURI(filePath);

                //      myApplication.showLog("file image  paths", imagePath);


                uploadwithRetrofit(realPath, type, v_id);

              //  calculateFileSize(realPath);


//                results = realm.where(UserImages.class).findAll();
//                if (results.size() < 5) {
//                    //   bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
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
//                    Toast.makeText(getActivity(),
//                            "Sorry! You can't add more then 4 Driving Licences.", Toast.LENGTH_SHORT)
//                            .show();
//                }
            } else if (resultCode == 0) {
                Toast.makeText(getActivity(),
                        "You cancelled image Selcetion", Toast.LENGTH_SHORT)
                        .show();
            }
        }


    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
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


//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private String getRealPathFromURI(Uri contentURI) {
//        Uri contentUri = Uri.parse(String.valueOf(contentURI));
//
//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = null;
//        try {
//
//            // Will return "image:x*"
//            String wholeID = DocumentsContract.getDocumentId(contentUri);
//            // Split at colon, use second item in the array
//            String id = wholeID.split(":")[1];
//            // where id is equal to
//            String sel = MediaStore.Images.Media._ID + "=?";
//
//            cursor = getActivity().getContentResolver().query(
//                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                    projection, sel, new String[]{id}, null);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String path = null;
//        try {
//            int column_index = cursor
//                    .getColumnIndex(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            path = cursor.getString(column_index).toString();
//            cursor.close();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//        return path;
//    }

    public String calculateFileSize(String filePath) {
        //String filepathstr=filepath.toString();
        File file = new File(filePath);
        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB / 1024;

        String calString = String.valueOf(fileSizeInMB);

        myApplication.showLog("image lenth is ------>>>", calString);

        return calString;
    }


//    private void previewCapturedImage() {
//        results = realm.where(UserImages.class).findAll();
//        if (results.size() < 5) {
//            try {
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 8;
//                bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
//                        options);
//                Send(bitmap);
//
//            } catch (NullPointerException e) {
//                e.printStackTrace();
//            }
//        } else {
//            Toast.makeText(getActivity(),
//                    "Sorry! You can't add more then 4 Driving Licences.", Toast.LENGTH_SHORT)
//                    .show();
//        }
//   }

//    private void Send(Bitmap bitmap) {
////        String encoded = BitmapToString(bitmap);
////        String rnd = "Licence" + GenerteRandomNumber();
//////        SendToServer(encoded, rnd);
////
////
////        imageAdapter.saveImageToDatabase(BitmapToString(bitmap), rnd);
////        results = realm.where(UserImages.class).findAll();
////        /*myApplication.showLog(TAG, "preview " + results.size());
////        myApplication.showLog(TAG, "capture");*/
////        if (results.size() == 1) {
////            SetImagesViews();
////        }
//    }

    private int GenerteRandomNumber() {
        Random r = new Random();
        return r.nextInt(9999 - 1000) + 1000;
    }


    private void uploadwithRetrofit(String imagePath, final String type, final String v_id) {

        MyApplication.getInstance().DialogMessage("Upload Documents...");
        MyApplication.getInstance().showDialog();

        final String imgname = imagePath.substring(imagePath.lastIndexOf("/") + 1);
        myApplication.showLog("name", imgname);

        final String rnd = "Licence" + String.valueOf(GenerteRandomNumber());

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), new File(imagePath));

        // MultipartBody.Part is used to send also the actual file name

        //note: file=key , vishal = iamgennnnname(randam) , reuestfile = pick image url

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", imgname, requestFile);
//        MultipartBody.Part body =
//                MultipartBody.Part.create(requestFile);

//
        OkHttpClient okHttpClient1 = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.URL_UPLOAD_DOC)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient1)
                .build();

        ResetApi resetApi = retrofit.create(ResetApi.class);

        retrofit2.Call<data> call = resetApi.getTasks("52d8c0efea5039cd0d778db7521889cf", body);

        call.enqueue(new retrofit2.Callback<data>() {
            @Override
            public void onResponse(retrofit2.Call<data> call, Response<data> response) {

                MyApplication.getInstance().hideDialog();

                int code = response.code();
                myApplication.showLog("code", "" + code);

                //    Toast.makeText(getActivity(), "Respose Code " + code, Toast.LENGTH_SHORT).show();

                if (response.code() == 500) {

                    Toast.makeText(getActivity(), "Server Side Error", Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 200) {

                    data myResp = response.body();
                    boolean error = myResp.isError();

                    if (!error) {

                        String imageurl = myResp.getResult();

                        myApplication.showLog("respose", imageurl);
                        //     Toast.makeText(getActivity(), "Url" + imageurl, Toast.LENGTH_SHORT).show();

                        AddVehicalesDocument(imageurl, type, v_id, rnd);


                    } else {
                        MyApplication.getInstance().hideDialog();

                        Toast.makeText(getActivity(), "Error For Uploading Documnent", Toast.LENGTH_SHORT).show();
                    }


                } else {

                    MyApplication.getInstance().hideDialog();

                    Toast.makeText(getActivity(), "Error Some Fatch Data", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(retrofit2.Call<data> call, Throwable t) {

                MyApplication.getInstance().hideDialog();

              //  if (t instanceof TimeoutError || t instanceof NoConnectionError) {
                    myApplication.ErrorSnackBar(getActivity());
              //  }

            }
        });

    }

    private void AddVehicalesDocument(final String imageurl, final String type, final String v_id, final String imgname) {


        String tag_string_req = "req_delete_vehicle";

        myApplication.DialogMessage("Add  Document...");
        myApplication.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_DOC, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                myApplication.hideDialog();

                myApplication.showLog("respodse", response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "onResponse: " + jObj.toString());

                    // Check for error node in json
                    if (!error) {
                        Toast.makeText(getActivity(),
                                "Add Document Successfull... ", Toast.LENGTH_LONG).show();

                        // parsing the user profile information
                        JSONObject profileObj = jObj.getJSONObject("result");

                        int id = profileObj.getInt("id");

                        myApplication.showLog("image id", "" + id);

                        SaveImageIntoDatabase(id, imageurl, type, v_id, imgname);

                        // DeleteContactToDatabase(contactid);
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getActivity(),
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                myApplication.ErrorSnackBar((Activity) getActivity());
                myApplication.hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("url", imageurl);
                stringMap.put("type", type);
                if (documenttype == "licence") {

                } else {
                    stringMap.put("vehicleId", v_id);
                }

                return stringMap;

//                vehicleNo
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
                myApplication.showLog(TAG, String.valueOf("passed auth"));
                return headers;

            }


        };
        // Adding request to request queue
        myApplication.addToRequestQueue(strReq, tag_string_req);


    }

    private void SaveImageIntoDatabase(int id, String imageurl, String type, String v_id, String imgname) {


        //       String rnd = "Licence" + GenerteRandomNumber();
//        SendToServer(encoded, rnd);


        imageAdapter.saveImageToDatabase(String.valueOf(id), imageurl, type, v_id, imgname);
        ImageRecyclerview.setAdapter(imageAdapter);
        results = realm.where(UserImages.class).findAll();
        imageAdapter.notifyDataSetChanged();

        imageDetails.addChangeListener(new RealmChangeListener<RealmResults<UserImages>>() {
            @Override
            public void onChange(RealmResults<UserImages> element) {
                imageAdapter.notifyDataSetChanged();
            }
        });

        myApplication.showLog(TAG, "preview " + results.size());
        myApplication.showLog(TAG, "capture");
        if (results.size() == 1) {
            SetImagesViews();
            SetDocumentDetails();
        }

    }

    @Subscribe
    public void onEvent(Message messageCar) {
        Log.e("car datata", messageCar.getMsg());
      //  Toast.makeText(getActivity(), messageCar.getMsg(), Toast.LENGTH_SHORT).show();

        selection_type = messageCar.getMsg();

        SetImagesViews();
        SetDocumentDetails();

    }




    @Subscribe
    public void onEvent(ImageMessage message) {
        Log.e("car datata", message.getDocumenttype() + message.getVehicle_id());
      //  Toast.makeText(getActivity(), message.getDocumenttype(), Toast.LENGTH_SHORT).show();

        documenttype = message.getDocumenttype();
        vehicle_id = message.getVehicle_id();

        pic_gallery_camera = message.getPic_gallery_camera();

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
    public void onResume() {
        super.onResume();
        imageAdapter.notifyDataSetChanged();
    }
}
