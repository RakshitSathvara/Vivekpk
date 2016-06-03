package in.vaksys.vivekpk.extras;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import in.vaksys.vivekpk.adapter.CarRecyclerViewAdapter;
import in.vaksys.vivekpk.dbPojo.VehicleDetails;
import in.vaksys.vivekpk.model.ClaimMessage;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by Harsh on 26-05-2016.
 */
public class VolleyHelper {
    MyApplication myApplication;
    private static final String TAG = "VolleyHelper";
    Activity activity;
    private Realm realm;
    private String BLANK = "";
    CarRecyclerViewAdapter adapter;

    OkHttpClient client;

    public VolleyHelper(Activity context) {

        this.myApplication = MyApplication.getInstance();
        this.activity = context;
        myApplication.createDialog(context, false);
        adapter = new CarRecyclerViewAdapter(activity);

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
                              final String pollution_exp_date, final String service_exp_date, final String note) {

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
                                "Login Successfull... ", Toast.LENGTH_LONG).show();

                        // parsing the user profile information

                        UpdateIntoDatabase(BLANK, VehicleId, modelid, insuranceCompany, insurace_exp_date, pollution_exp_date, service_exp_date, note);


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
        myApplication.DialogMessage("Deleting Vehicle...");
        myApplication.showDialog();

        RequestBody formBody = new FormBody.Builder()
                .add("id", String.valueOf(VehicleId))
                .build();


        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(AppConfig.URL_DELETE_USER_VEHICLE)
                .delete(formBody)
                .addHeader("Authorization", "52d8c0efea5039cd0d778db7521889cf")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                myApplication.ErrorSnackBar(activity);
                myApplication.hideDialog();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {

                myApplication.hideDialog();

//                myApplication.showLog(TAG, String.valueOf(response.code()));
                myApplication.showLog(TAG, response.body().string().toString());

//                System.out.print(response.body().string());
               /* if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    try {
                        Reader aa = response.body().charStream();

                        Gson gson = new Gson();

                        DeleteData response11 = gson.fromJson(aa, DeleteData.class);

                        Log.e(TAG, "onResponse: " + response11.getMessage());
                    } catch (JsonSyntaxException | JsonIOException e) {
                        e.printStackTrace();
                    }
*/

                try {
//                    String aa = (response.body().string()).replaceAll("\\s+", "");
                        /*JsonObject aaa = new JsonObject();
                        aaa.add("ad",aa);
                        JsonParser parser = new JsonParser();
                        JsonObject o = parser.parse(aa).getAsJsonObject();
*/
                    JSONObject jObj = new JSONObject(response.body().string().toString());

                    myApplication.showLog(TAG, jObj.toString());

                    Gson gson = new Gson();

                    DeleteData response11 = gson.fromJson((response.body().string()).replaceAll("\\s+", ""), DeleteData.class);

                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "onResponse: " + response11.getMessage());

                    // Check for error node in json
                    if (!error) {
                        Toast.makeText(activity,
                                "Delete Successfull... ", Toast.LENGTH_LONG).show();

                        DeleteIntoDatabase(VehicleId);
                    } else {
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(activity,
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
//                    Toast.makeText(activity, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

/*
        String tag_string_req = "req_delete_vehicle";

        myApplication.DialogMessage("Deleting Vehicle...");
        myApplication.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.DELETE,
                AppConfig.URL_TEMP, new Response.Listener<String>() {

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

//                        DeleteIntoDatabase(VehicleId);
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
                params.put("id", String.valueOf(81));
                myApplication.showLog(TAG, String.valueOf("passed " + VehicleId));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "99742a0bbcf11b9ac6b10e90b3a76f34");
                myApplication.showLog(TAG, String.valueOf("passed auth"));
                return headers;

            }
        };
        // Adding request to request queue
        myApplication.addToRequestQueue(strReq, tag_string_req);
*/

    }

    public void DeleteContact(final int contactid) {
        myApplication.DialogMessage("Deleting Contact...");
        myApplication.showDialog();

        RequestBody formBody = new FormBody.Builder()
                .add("id", String.valueOf(contactid))
                .build();


        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(AppConfig.URL_EMERGENY_CONTACT)
                .delete(formBody)
                .addHeader("authorization", "99742a0bbcf11b9ac6b10e90b3a76f34")
                .build();


        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                myApplication.ErrorSnackBar(activity);
                myApplication.hideDialog();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                myApplication.hideDialog();

                myApplication.showLog(TAG, String.valueOf(response.code()));
                myApplication.showLog(TAG, String.valueOf(response.body().string()));


                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    try {
                        String aa = response.body().string().trim();
                        JSONObject jObj = new JSONObject(aa);
                        myApplication.showLog(TAG, jObj.toString());

                        boolean error = jObj.getBoolean("error");
                        Log.e(TAG, "onResponse: " + jObj.toString());

                        // Check for error node in json
                        if (!error) {
                            Toast.makeText(activity,
                                    "Delete Successfull... ", Toast.LENGTH_LONG).show();

                            DeleteIntoDatabase(contactid);
                        } else {
                            String errorMsg = jObj.getString("message");
                            Toast.makeText(activity,
                                    "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
//                    Toast.makeText(activity, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

///*
//        String tag_string_req = "req_delete_vehicle";
//
//        myApplication.DialogMessage("Deleting Vehicle...");
//        myApplication.showDialog();
//
//        StringRequest strReq = new StringRequest(Request.Method.DELETE,
//                AppConfig.URL_TEMP, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                myApplication.hideDialog();
//
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    boolean error = jObj.getBoolean("error");
//                    Log.e(TAG, "onResponse: " + jObj.toString());
//
//                    // Check for error node in json
//                    if (!error) {
//                        Toast.makeText(activity,
//                                "Delete Successfull... ", Toast.LENGTH_LONG).show();
//
//                        // parsing the user profile information
////                        JSONObject profileObj = jObj.getJSONObject("result");
//
////                        DeleteIntoDatabase(VehicleId);
//                    } else {
//                        // Error in login. Get the error message
//                        String errorMsg = jObj.getString("message");
//                        Toast.makeText(activity,
//                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    // JSON error
//                    e.printStackTrace();
//                    Toast.makeText(activity, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                Log.e(TAG, "Login Error: " + error.getMessage());
//                myApplication.ErrorSnackBar(activity);
//                myApplication.hideDialog();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting parameters to login url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id", String.valueOf(81));
//                myApplication.showLog(TAG, String.valueOf("passed " + VehicleId));
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", "99742a0bbcf11b9ac6b10e90b3a76f34");
//                myApplication.showLog(TAG, String.valueOf("passed auth"));
//                return headers;
//
//            }
//        };
//        // Adding request to request queue
//        myApplication.addToRequestQueue(strReq, tag_string_req);
//*/

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

        Toast.makeText(activity, "Setup Complete", Toast.LENGTH_LONG).show();
        myApplication.hideDialog();
//        adapter.notifyDataSetChanged();

    }


    private void UpdateIntoDatabase(final String name, final int vehicleId, final int ModelId, String insuranceCompany, String ins_exp_date, String poll_exp_date,
                                    String serv_exp_date, String note) {
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
        user.setNote(note);

        realm.commitTransaction();

        RealmResults<VehicleDetails> results = realm.where(VehicleDetails.class).findAll();
        Log.e(TAG, "SaveIntoDatabase: " + results.size());

        Toast.makeText(activity, "Setup Complete", Toast.LENGTH_LONG).show();
        myApplication.hideDialog();

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

        Toast.makeText(activity, "Setup Complete", Toast.LENGTH_LONG).show();
        myApplication.hideDialog();

    }
}
