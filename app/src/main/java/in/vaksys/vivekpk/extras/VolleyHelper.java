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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import in.vaksys.vivekpk.adapter.CarBikeRecyclerViewAdapter;
import in.vaksys.vivekpk.dbPojo.VehicleDetails;
import in.vaksys.vivekpk.model.ClaimMessage;
import in.vaksys.vivekpk.service.NotifyService;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;

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
    OkHttpClient client;
    String nofidate, typr, vehicalno, nId;

    public VolleyHelper(Activity context) {

        this.myApplication = MyApplication.getInstance();
        this.activity = context;
        myApplication.createDialog(context, false);
        adapter = new CarBikeRecyclerViewAdapter(activity);

        client = new OkHttpClient();

    }

    public void AddVehicle(final String type, final int modelid, final String vehicle_number) {

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

                            SaveIntoDatabase(BLANK, VehicleId, modelid, vehicle_number, type, BLANK, BLANK, BLANK, BLANK);
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
                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
                return headers;

            }
        };
        // Adding request to request queue
        myApplication.addToRequestQueue(strReq, tag_string_req);

    }


    public void UpdateVehicle(final int VehicleId, final int modelid, final String insuranceCompany, final String insurace_exp_date,
                              final String pollution_exp_date, final String service_exp_date, final String note, final String notificationDate) {

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

                        UpdateIntoDatabase(BLANK, VehicleId, modelid, insuranceCompany, insurace_exp_date, pollution_exp_date, service_exp_date, note, notificationDate);


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
                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
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
                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
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
                                  String serv_exp_date) {
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

        realm.commitTransaction();

        RealmResults<VehicleDetails> results = realm.where(VehicleDetails.class).findAll();
        Log.e(TAG, "SaveIntoDatabase: " + results.size());

//        Toast.makeText(activity, "Setup Complete", Toast.LENGTH_LONG).show();
        myApplication.hideDialog();
//        adapter.notifyDataSetChanged();

    }


    private void UpdateIntoDatabase(final String name, final int vehicleId, final int ModelId, String insuranceCompany, String ins_exp_date, String poll_exp_date,
                                    String serv_exp_date, String note, String NotificationDate) {
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
        user.setNote(note);

        realm.commitTransaction();

        RealmResults<VehicleDetails> results = realm.where(VehicleDetails.class).findAll();




        Log.e(TAG, "SaveIntoDatabase: " + results.size());

//        Toast.makeText(activity, "Setup Complete", Toast.LENGTH_LONG).show();
        myApplication.hideDialog();
        SetNotification(vehicleId);

    }

    private void SetNotification(int vehicleId) {


        realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        RealmResults<VehicleDetails> results = realm.where(VehicleDetails.class).equalTo("VehicleId", vehicleId).findAll();

        //  VehicleDetails userw = realm.where(VehicleDetails.class).findFirst();

        for (VehicleDetails c : results) {

//            Log.d("results1", c.getName());

            nofidate = c.getNotificationDate();
            //
            typr = c.getType();
            vehicalno = c.getVehicleNo();
            nId = String.valueOf(c.getVehicleId());

        }


        realm.commitTransaction();
        myApplication.showLog("nofidate=====>", nofidate);

        SendNotification(typr, vehicalno, nofidate, nId);

    }

    private void SendNotification(String typr, String vehicalno, String nofidate, String nId) {

      //  myApplication.showLog("nofidate=====>", nofidate);

        String myStrDate = nofidate;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
           date = format.parse(myStrDate);
            System.out.println(date);
            System.out.println(format.format(date));

            myApplication.showLog("ddjhgdyhcg",format.format(date));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        Calendar c = Calendar.getInstance();
//        try {
//            c.setTime(sdf.parse(nofidate));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//       /// -days mens 5 day befor reminder set
//        sdf = new SimpleDateFormat("dd-MM-yyyy");
//        Date resultdate = new Date(c.getTimeInMillis());
//
//        System.out.println(resultdate);

//        String dtStart = nofidate;
//        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//        try {
//            date = format.parse(dtStart);
//            System.out.println(date);
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        Calendar cc = dateToCalendar(date);
        System.out.println("nofi date"+cc.getTimeInMillis());

        myApplication.showLog("not;;;;;;;;;;--->",""+ cc.getTimeInMillis());


//
//        Calendar current  = Calendar.getInstance();
//        System.out.println("Current time => " + current .getTime());
//
//        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
//        String formattedDate = df.format(current .getTime());
//        System.out.println("curerrrent date" +formattedDate);

//        if(cc.compareTo(current) <= 0){
//            //The set Date/Time already passed
//            myApplication.showLog("notification date",""+ cc.getTime());

//        }else{
            AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(activity, NotifyService.class);
            PendingIntent pIntent = PendingIntent.getBroadcast(activity, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.set(AlarmManager.RTC_WAKEUP, MyApplication.c.getTimeInMillis(), pIntent);

            Bundle bundle = new Bundle();
            bundle.putString("type", typr);
            bundle.putString("vehicalno", vehicalno);
            bundle.putString("nofidate", nofidate);
            bundle.putString("nId", nId);
            intent.putExtras(bundle);
            activity.startService(intent);






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

}
