package in.vaksys.vivekpk.extras;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import in.vaksys.vivekpk.adapter.CarBikeRecyclerViewAdapter;
import in.vaksys.vivekpk.dbPojo.UserImages;
import in.vaksys.vivekpk.dbPojo.VehicleDetails;
import in.vaksys.vivekpk.model.ClaimMessage;
import in.vaksys.vivekpk.model.data;
import in.vaksys.vivekpk.service.MyReceiver;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Harsh on 26-05-2016.
 */
public class VolleyHelper {
    MyApplication myApplication;
    private static final String TAG = "VolleyHelper";
    Activity activity;
    private Realm realm;
    private String BLANK = "";
    CarBikeRecyclerViewAdapter adapter;
    Date date;
    private RealmResults<UserImages> results;
    OkHttpClient client;
    String nofidate, remindertypr, vehicalno, nId ,ServiceExpDate;
    private PendingIntent pendingIntent;

    private String apikey;

    public VolleyHelper(Activity context) {

        this.myApplication = MyApplication.getInstance();
        this.activity = context;
        myApplication.createDialog(context, false);
        adapter = new CarBikeRecyclerViewAdapter(activity);

        client = new OkHttpClient();
        apikey = myApplication.getApikey();

    }

    //call this
    public void AddVehicle(final String type, final int modelid, final String vehicle_number, final int brandposition, final int modelpostion) {

        String tag_string_req = "req_add_vehicle";

        myApplication.DialogMessage("Adding Vehicle...");
        myApplication.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_USER_VEHICLE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
//                myApplication.hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "onResponse: " + jObj.toString());

                    // Check for error node in json
                    if (!error) {

                        // parsing the user profile information
                        if (jObj.has("result")) {
                            Toast.makeText(activity, "Vehicle Add Successfull ... ", Toast.LENGTH_LONG).show();

                            JSONObject profileObj = jObj.getJSONObject("result");
                            int VehicleId = profileObj.getInt("id");

                            SaveIntoDatabase(BLANK, VehicleId, modelid, vehicle_number, type, BLANK, BLANK, BLANK, BLANK,brandposition,modelpostion);
                        }

                    } else {
                        // Error in login. Get the error message
                        myApplication.hideDialog();

                        EventBus eventBus = EventBus.getDefault();
//                        eventBus.post(new ClaimMessage(vehicle_number, realm.where(VehicleModels.class)
//                                .equalTo("id", modelid).findFirst().getManufacturerName()));
                        myApplication.showLog(TAG, String.valueOf(modelid));
                        eventBus.post(new ClaimMessage(vehicle_number, modelid));

                        String errorMsg = jObj.getString("message");
                        Toast.makeText(activity,
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    myApplication.hideDialog();


                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(activity, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                myApplication.ErrorSnackBar(activity);
                myApplication.hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "");
                params.put("modelId", String.valueOf(modelid));
                params.put("vehicleNo", vehicle_number);
                params.put("type", type);
                params.put("insuranceCompany", "");
                params.put("insuranceExpDate", "");
                params.put("pollutionExpDate", "");
                params.put("service_exp_date", "");
                params.put("note", "");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", apikey);
                return headers;

            }
        };
        // Adding request to request queue
        myApplication.addToRequestQueue(strReq, tag_string_req);

    }


    public void UpdateVehicle(final int VehicleId, final int modelid, final String insuranceCompany, final String insurace_exp_date,
                              final String pollution_exp_date, final String service_exp_date, final String note, final String notificationDate ,final String Remindertypr) {

        String tag_string_req = "req_update_vehicle";

        myApplication.DialogMessage("Updating Vehicle...");
        myApplication.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.PUT,
                AppConfig.URL_ADD_USER_VEHICLE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                myApplication.hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "onResponse: " + jObj.toString());

                    // Check for error node in json
                    if (!error) {
                        Toast.makeText(activity,
                                "Update Successfull... ", Toast.LENGTH_LONG).show();

                        // parsing the user profile information

                        UpdateIntoDatabase(BLANK, VehicleId, modelid, insuranceCompany, insurace_exp_date, pollution_exp_date, service_exp_date, note, notificationDate,Remindertypr);


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(activity,
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(activity, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                myApplication.ErrorSnackBar(activity);
                myApplication.hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(VehicleId));
                params.put("name", "");
                params.put("modelId", String.valueOf(modelid));
                params.put("insuranceCompany", insuranceCompany);
                params.put("insuranceExpDate", insurace_exp_date);
                params.put("pollutionExpDate", pollution_exp_date);
                params.put("service_exp_date", service_exp_date);
                params.put("note", note);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", apikey);
                return headers;

            }
        };
        // Adding request to request queue
        myApplication.addToRequestQueue(strReq, tag_string_req);

    }

    public void DeleteVehicle(final int VehicleId) {

        String tag_string_req = "req_delete_vehicle";

        myApplication.DialogMessage("Deleting Vehicle...");
        myApplication.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.DELETE,
                AppConfig.URL_DELETE_USER_VEHICLE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                myApplication.hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "onResponse: " + jObj.toString());

                    // Check for error node in json
                    if (!error) {
                        Toast.makeText(activity,
                                "Delete Successfull... ", Toast.LENGTH_LONG).show();

                        // parsing the user profile information
//                        JSONObject profileObj = jObj.getJSONObject("result");

                        DeleteIntoDatabase(VehicleId);
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(activity,
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(activity, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                myApplication.ErrorSnackBar(activity);
                myApplication.hideDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", apikey);
                headers.put("id", String.valueOf(VehicleId));
                myApplication.showLog(TAG, String.valueOf("passed auth"));
                return headers;

            }
        };
        // Adding request to request queue
        myApplication.addToRequestQueue(strReq, tag_string_req);

    }


    private void SaveIntoDatabase(final String name, final int vehicleId, final int modelID, final String VehicleNumber,
                                  final String type, String insuranceCompany, String ins_exp_date, String poll_exp_date,
                                  String serv_exp_date, int brandposition, int modelpostion) {
        myApplication.DialogMessage("Setting Up Vehicle...");
//        myApplication.showDialog();
/*
        realmAsyncTask = realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {*/
        realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        VehicleDetails user = realm.createObject(VehicleDetails.class);

        user.setName(name);
        user.setVehicleId(vehicleId);
        user.setVehicleModelID(modelID);
        user.setVehicleNo(VehicleNumber);
        user.setType(type);
        user.setInsuranceCompany(insuranceCompany);
        user.setInsuranceExpireDate(ins_exp_date);
        user.setPollutionExpireDate(poll_exp_date);
        user.setServiceExpireDate(serv_exp_date);
        user.setBrandposi(brandposition);
        user.setModelposi(modelpostion);

        realm.commitTransaction();

        RealmResults<VehicleDetails> results = realm.where(VehicleDetails.class).findAll();
        Log.e(TAG, "SaveIntoDatabase: " + results.size());

//        Toast.makeText(activity, "Setup Complete", Toast.LENGTH_LONG).show();
        myApplication.hideDialog();
//        adapter.notifyDataSetChanged();

    }


    private void UpdateIntoDatabase(final String name, final int vehicleId, final int ModelId, String insuranceCompany, String ins_exp_date, String poll_exp_date,
                                    String serv_exp_date, String note, String NotificationDate, String remindertypr) {
        myApplication.DialogMessage("Setting Up Vehicle...");
        myApplication.showDialog();

        realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        VehicleDetails user = realm.where(VehicleDetails.class).equalTo("VehicleId", vehicleId).findFirst();

        user.setName(name);
        user.setVehicleModelID(ModelId);
        user.setInsuranceCompany(insuranceCompany);
        user.setInsuranceExpireDate(ins_exp_date);
        user.setPollutionExpireDate(poll_exp_date);
        user.setServiceExpireDate(serv_exp_date);
        user.setNotificationDate(NotificationDate);
        user.setReminderType(remindertypr);
        user.setNote(note);

        realm.commitTransaction();

        RealmResults<VehicleDetails> results = realm.where(VehicleDetails.class).findAll();


        Log.e(TAG, "SaveIntoDatabase: " + results.size());

//        Toast.makeText(activity, "Setup Complete", Toast.LENGTH_LONG).show();
        myApplication.hideDialog();
        SetNotification(vehicleId,serv_exp_date);

    }

    private void SetNotification(int vehicleId, String serv_exp_date) {


        realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        RealmResults<VehicleDetails> results = realm.where(VehicleDetails.class).equalTo("VehicleId", vehicleId).findAll();

        //  VehicleDetails userw = realm.where(VehicleDetails.class).findFirst();

        for (VehicleDetails c : results) {

//            Log.d("results1", c.getName());

            nofidate = c.getNotificationDate();
            //
            ServiceExpDate = c.getServiceExpireDate();
            String expdate = c.getInsuranceExpireDate();
            String exdate1 = c.getPollutionExpireDate();
            remindertypr = c.getReminderType();
            vehicalno = c.getVehicleNo();
            nId = String.valueOf(c.getVehicleId());

        }

        realm.commitTransaction();

        myApplication.showLog("ServiceExpDate=====>", ServiceExpDate+ "\n" + serv_exp_date) ;

        myApplication.showLog("nofidate=====>", nofidate + "\n"  + ServiceExpDate + "\n"  +serv_exp_date );

        SendNotification(remindertypr, vehicalno, nofidate, nId,ServiceExpDate);
       // SendNotification(remindertypr, vehicalno, nofidate, nId,ServiceExpDate);

//        myApplication.showLog("ServiceExpDate=====>", ServiceExpDate);


    }

    private void SendNotification(String typr, String vehicalno, String nofidate, String nId, String serviceExpDate) {

     myApplication.showLog("SendNotification=====>", serviceExpDate);

        String myStrDate = nofidate;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = format.parse(myStrDate);
            System.out.println(date);
            System.out.println(format.format(date));

            myApplication.showLog("ddjhgdyhcg", format.format(date));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




        Calendar cc = dateToCalendar(date);
        System.out.println("nofi date" + cc.getTimeInMillis());

        myApplication.showLog("not;;;;;;;;;;--->", "" + cc.getTimeInMillis());





        Intent myIntent = new Intent(activity, MyReceiver.class);
        myIntent.putExtra("type", typr);
        myIntent.putExtra("vehicalno", vehicalno);
        myIntent.putExtra("nofidate", serviceExpDate);
        myIntent.putExtra("nId", nId);
        pendingIntent = PendingIntent.getBroadcast(activity, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, cc.getTimeInMillis(), pendingIntent);


    }

    private void DeleteIntoDatabase(final int vehicleId) {
        myApplication.DialogMessage("Setting Up Vehicle...");
        myApplication.showDialog();

        realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        VehicleDetails user = realm.where(VehicleDetails.class).equalTo("VehicleId", vehicleId).findFirst();

        user.deleteFromRealm();

        realm.commitTransaction();

        RealmResults<VehicleDetails> results = realm.where(VehicleDetails.class).findAll();
        Log.e(TAG, "SaveIntoDatabase: " + results.size());

//        Toast.makeText(activity, "Setup Complete", Toast.LENGTH_LONG).show();
        myApplication.hideDialog();

    }

    private Calendar dateToCalendar(Date date) {
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    private void UpdoadDocumentImage(){

    }


    public void uploadwithRetrofit(String realPath, final String documenttype, final String img_id, final String v_id) {

        MyApplication.getInstance().DialogMessage("Upload Documents...");
        MyApplication.getInstance().showDialog();

        final String imgname = realPath.substring(realPath.lastIndexOf("/") + 1);
        MyApplication.getInstance().showLog("name", imgname);

        final String rnd = "Licence" + String.valueOf(GenerteRandomNumber());

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), new File(realPath));

        // MultipartBody.Part is used to send also the actual file name

        //note: file=key , vishal = iamgennnnname(randam) , reuestfile = pick image url

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", rnd, requestFile);
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

        retrofit2.Call<data> call = resetApi.getTasks(apikey, body);

        call.enqueue(new retrofit2.Callback<data>() {
            @Override
            public void onResponse(retrofit2.Call<data> call, retrofit2.Response<data> response) {

                MyApplication.getInstance().hideDialog();

                int code = response.code();
                MyApplication.getInstance().showLog("code", "" + code);

                //    Toast.makeText(getActivity(), "Respose Code " + code, Toast.LENGTH_SHORT).show();

                if (response.code() == 500) {

                    Toast.makeText(activity, "Server Side Error", Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 200) {

                    data myResp = response.body();
                    boolean error = myResp.isError();

                    if (!error) {

                        String imageurl = myResp.getResult();

                        MyApplication.getInstance().showLog("respose", imageurl);
                        //     Toast.makeText(getActivity(), "Url" + imageurl, Toast.LENGTH_SHORT).show();

                        AddVehicalesDocument(imageurl, documenttype, img_id, rnd, v_id);


                    } else {
                        MyApplication.getInstance().hideDialog();

                        Toast.makeText(activity, "Error For Uploading Documnent", Toast.LENGTH_SHORT).show();
                    }


                } else {

                    MyApplication.getInstance().hideDialog();

                    Toast.makeText(activity, "Error Some Fatch Data", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(retrofit2.Call<data> call, Throwable t) {

                MyApplication.getInstance().hideDialog();

//             String msg =  t.getMessage().toString();

                if (t instanceof TimeoutError || t instanceof NoConnectionError) {
                    MyApplication.getInstance().ErrorSnackBar(activity);
                }

            }
        });

    }


    public void AddVehicalesDocument(final String imageurl, final String documenttype, final String img_id, final String imgname, final String v_id) {


        String tag_string_req = "req_delete_vehicle";

        MyApplication.getInstance().DialogMessage("Update  Document...");
        MyApplication.getInstance().showDialog();

        StringRequest strReq = new StringRequest(Request.Method.PUT,
                AppConfig.URL_UPDATE_DOC, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                MyApplication.getInstance().hideDialog();

                MyApplication.getInstance().showLog("respodse", response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "onResponse: " + jObj.toString());

                    // Check for error node in json
                    if (!error) {
                        Toast.makeText(activity,
                                "Document updated... ", Toast.LENGTH_LONG).show();

                        // parsing the user profile information


                        UpdateImageIntoDatabase(img_id, imageurl, imgname);

                        // DeleteContactToDatabase(contactid);
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(activity,
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(activity, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                MyApplication.getInstance().ErrorSnackBar((Activity)activity);
                MyApplication.getInstance().hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("id", img_id);
                stringMap.put("url", imageurl);


                return stringMap;

//                vehicleNo
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", apikey);
                MyApplication.getInstance().showLog(TAG, String.valueOf("passed auth"));
                return headers;

            }


        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);


    }

    private void UpdateImageIntoDatabase(String img_id, String imageurl, String name) {
        realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        UserImages userImages = realm.where(UserImages.class).equalTo("id", img_id).findFirst();

        userImages.setImageName(name);
        userImages.setImagesurl(imageurl);

        realm.commitTransaction();

//        results.addChangeListener(new RealmChangeListener<RealmResults<UserImages>>() {
//            @Override
//            public void onChange(RealmResults<UserImages> element) {
//                imageAdapter.notifyDataSetChanged();
//            }
//        });

    }

    private int GenerteRandomNumber() {
        Random r = new Random();
        return r.nextInt(9999 - 1000) + 1000;
    }

    public void DeleteContact(final String img_id, final Context context) {


        String tag_string_req = "req_delete_vehicle";

        MyApplication.getInstance().DialogMessage("Deleting Document...");
        MyApplication.getInstance().showDialog();

        StringRequest strReq = new StringRequest(Request.Method.DELETE,
                AppConfig.URL_DELETE_DOC, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    // Log.e(TAG, "onResponse: " + jObj.toString());

                    // Check for error node in json
                    if (!error) {
                        MyApplication.getInstance().hideDialog();
                        Toast.makeText(context,
                                "Delete Successfull... ", Toast.LENGTH_LONG).show();

                        // parsing the user profile information
//                        JSONObject profileObj = jObj.getJSONObject("result");

                        DeleteContactToDatabase(img_id);
                    } else {
                        MyApplication.getInstance().hideDialog();
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(context,
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    MyApplication.getInstance().hideDialog();
                    e.printStackTrace();
                    Toast.makeText(context, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                MyApplication.getInstance().ErrorSnackBar((Activity) context);
                MyApplication.getInstance().hideDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", apikey);
                headers.put("id", String.valueOf(img_id));
                // myApplication.showLog(TAG, String.valueOf("passed auth"));
                return headers;

            }
        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void DeleteContactToDatabase(final String img_id) {
        realm = Realm.getDefaultInstance();

        realm.beginTransaction();


        final UserImages userI = realm.where(UserImages.class).equalTo("id", img_id).findFirst();

        userI.deleteFromRealm();

        realm.commitTransaction();
//
//        final RealmResults<EmergencyContact> results = realm.where(EmergencyContact.class).findAll();
//        Log.e(TAG, "SaveIntoDatabase: " + results.size());
//


        myApplication.hideDialog();

    }


}
