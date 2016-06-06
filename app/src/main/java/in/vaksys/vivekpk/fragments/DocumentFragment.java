package in.vaksys.vivekpk.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.adapter.ImageAdapter;
import in.vaksys.vivekpk.dbPojo.UserImages;
import in.vaksys.vivekpk.extras.MyApplication;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentFragment extends Fragment {
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String TAG = "DocumentFragment";
    private static final String IMAGE_DIRECTORY_NAME = "EzyRidePhotos";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static String timeStamp, myImageUrl;
    public Uri fileUri;
    public int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;
    MyApplication myApplication;
    UserImages userImages;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageAdapter imageAdapter;
    RecyclerView ImageRecyclerview;
    RealmResults<UserImages> results;
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
    private Uri filePath;
    private Realm realm;
    //private MultiStateToggleButton multiStateToggleButton;
    private EventBus bus = EventBus.getDefault();

    private static final String IMGUR_CLIENT_ID = "...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public static DocumentFragment newInstance(int index) {
        DocumentFragment fragment = new DocumentFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_document, container, false);

        BindViews(view);
        SetImagesViews();
        linearAddGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
      /*  multiStateToggleButton = (MultiStateToggleButton) rootView.findViewById(R.id.mstb_vehicleChoice);
        multiStateToggleButton.enableMultipleChoice(false);
        multiStateToggleButton.setValue(0);
        //multiStateToggleButton.setColorRes(R.color.cardview_dark_background, R.color.cardview_dark_background);

        multiStateToggleButton.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                Log.e("MSTB", "onValueChanged: " + value);
                switch (value) {
                    case 0:
                        Toast.makeText(getActivity(), "Car Selected..", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "Bike Selected..", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getActivity(), "Please S" +
                                "elect any..", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
*/


       /* btnRegisterVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearAddDrivingLicense.setVisibility(View.GONE);
                linearYourDrivingLicense.setVisibility(View.GONE);
                linearRegisterVehicle.setVisibility(View.GONE);
                linearVehicleDetailsListRaw.setVisibility(View.GONE);

            }
        });

        imgAddGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearAddDrivingLicense.setVisibility(View.GONE);
                linearYourDrivingLicense.setVisibility(View.VISIBLE);
                linearRegisterVehicle.setVisibility(View.GONE);
                linearVehicleDetailsListRaw.setVisibility(View.VISIBLE);

            }
        });*/
        return view;
    }

    private void SetImagesViews() {
        myApplication.showLog(TAG, "innerview");

        ImageRecyclerview.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        ImageRecyclerview.setLayoutManager(manager);
        results = realm.where(UserImages.class).findAll();
        myApplication.showLog(TAG, "inside viwe" + results.size());
        if (results.size() > -1) {
            myApplication.showLog(TAG, "innerview11111");
            linearYourDrivingLicense.setVisibility(View.VISIBLE);
            imageAdapter = new ImageAdapter(getActivity(), results);
            ImageRecyclerview.setAdapter(imageAdapter);
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
        imgEdit = (ImageView) view.findViewById(R.id.img_edit);
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

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
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
        myApplication.showLog(TAG, " " + resultCode + " " + requestCode + " " + data.toString());
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == -1) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
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
                filePath = data.getData();


                String imagePath = getRealPathFromURI(filePath);
                calculateFileSize(imagePath);
                try {
                    results = realm.where(UserImages.class).findAll();
                    if (results.size() < 5) {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                        Send(bitmap);
                        /*String encoded = BitmapToString(bitmap);

                        SendToServer(encoded);
                        imageAdapter.saveImageToDatabase(encoded);
//                        myApplication.showLog(TAG, "preview " + results.size());
//                        myApplication.showLog(TAG, "pick");
                        if (results.size() == 1) {
                            SetImagesViews();
                        }*/
                    } else {
                        Toast.makeText(getActivity(),
                                "Sorry! You can't add more then 4 Driving Licences.", Toast.LENGTH_SHORT)
                                .show();
                    }
                } catch (IOException e) {
                    Toast.makeText(getActivity(),
                            "Sorry! Failed to Select image", Toast.LENGTH_SHORT)
                            .show();
                }
            } else if (resultCode == 0) {
                Toast.makeText(getActivity(),
                        "You cancelled image Selcetion", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        Uri contentUri = Uri.parse(String.valueOf(contentURI));

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            if (Build.VERSION.SDK_INT > 19) {
                // Will return "image:x*"
                String wholeID = DocumentsContract.getDocumentId(contentUri);
                // Split at colon, use second item in the array
                String id = wholeID.split(":")[1];
                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";

                cursor = getActivity().getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, sel, new String[]{id}, null);
            } else {
                cursor = getActivity().getContentResolver().query(contentUri,
                        projection, null, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String path = null;
        try {
            int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index).toString();
            cursor.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return path;
    }

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

//    private void SendToServer(String bitmap, String rnd) {
//        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, AppConfig.URL_SPINNER, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
////                setAreaSpinner();
//                try {
//
//                    boolean error = response.getBoolean("error");
//                    if (!error) {
//                        realm.beginTransaction();
//                        // Getting JSON Array node
//                        JSONArray results1 = response.getJSONArray("result");
//
//                        vehicleModels = realm.createObject(VehicleModels.class);
//
//                        vehicleModels.setId(0);
//                        vehicleModels.setManufacturerName("Select Brand");
//                        vehicleModels.setModel("Select Model");
//                        vehicleModels.setType("");
//                        vehicleModels.setCreatedAt("31131");
//                        vehicleModels.setUpdatedAt("21232");
//
//                        for (int i = 0; i < results1.length(); i++) {
//
//                            JSONObject jsonObject = results1.getJSONObject(i);
//                            int id = jsonObject.getInt("id");
//                            String manufacturerName = jsonObject.getString("manufacturerName");
//                            String model = jsonObject.getString("model");
//                            String type = jsonObject.getString("type");
//                            String createdAt = jsonObject.getString("createdAt");
//                            String updatedAt = jsonObject.getString("updatedAt");
//
//                            vehicleModels = realm.createObject(VehicleModels.class);
//
//                            vehicleModels.setId(id);
//                            vehicleModels.setManufacturerName(manufacturerName);
//                            vehicleModels.setModel(model);
//                            vehicleModels.setType(type);
//                            vehicleModels.setCreatedAt(createdAt);
//                            vehicleModels.setUpdatedAt(updatedAt);
//
//                        }
//                        realm.commitTransaction();
//                        myApplication.hideDialog();
//
//                        startActivity(new Intent(getActivity(), HomeActivity.class));
//
//
//                    } else {
//                        String errorMsg = response.getString("message");
//                        Toast.makeText(getActivity(),
//                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
//                        myApplication.hideDialog();
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    myApplication.hideDialog();
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                myApplication.hideDialog();
//                //Toast.makeText(getApplicationContext(), "Responce : " + error, Toast.LENGTH_LONG).show();
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    myApplication.ErrorSnackBar(getActivity());
//                }
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting parameters to login url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("password", mPassword);
//                params.put("phone", mContactNo);
//
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
//                return headers;
//            }
//        };
//        myApplication.addToRequestQueue(request);
//    }

    private String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private void previewCapturedImage() {
        results = realm.where(UserImages.class).findAll();
        if (results.size() < 5) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                        options);
                Send(bitmap);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(),
                    "Sorry! You can't add more then 4 Driving Licences.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void Send(Bitmap bitmap) {
        String encoded = BitmapToString(bitmap);
        String rnd = "Licence" + GenerteRandomNumber();
//        SendToServer(encoded, rnd);

        SendImage();


        imageAdapter.saveImageToDatabase(BitmapToString(bitmap), rnd);
        results = realm.where(UserImages.class).findAll();
        /*myApplication.showLog(TAG, "preview " + results.size());
        myApplication.showLog(TAG, "capture");*/
        if (results.size() == 1) {
            SetImagesViews();
        }
    }

    private void SendImage() {


    }

    OkHttpClient client = new OkHttpClient();

    public void run() throws Exception {
        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"file\""),
                        RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .url("https://api.imgur.com/3/image")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());
    }

    private int GenerteRandomNumber() {
        Random r = new Random();
        return r.nextInt(9999 - 1000) + 1000;
    }


}
