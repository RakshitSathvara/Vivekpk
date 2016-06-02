package in.vaksys.vivekpk.extras;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harsh on 26-05-2016.
 */
public class VolleyHelper {
    MyApplication myApplication;
    private static final String TAG = "VolleyHelper";
    Activity activity;

    public VolleyHelper(Activity context) {

        this.myApplication = MyApplication.getInstance();
        this.activity = context;
        myApplication.createDialog(context, false);
    }

    public void AddVehicle(final String type, final String name, final int modelid, final String vehicle_number, String insuranceCompany,
                           String insurace_exp_date, String pollution_exp_date,
                           String service_exp_date, String note) {

        String tag_string_req = "req_login";

        myApplication.DialogMessage("Loging in...");
        myApplication.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
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
                        JSONObject profileObj = jObj.getJSONObject("result");

                        String fname = profileObj.getString("firstName");
                        String lname = profileObj.getString("lastName");
                        String email = profileObj.getString("email");
                        String apikey = profileObj.getString("apiKey");
                        int status = profileObj.getInt("status");
                        String phone = profileObj.getString("phone");
                        String createdAt = profileObj.getString("createdAt");
                        String updatedAt = profileObj.getString("updatedAt");

                        Log.e(TAG, "" + fname + " " + lname + " " + email + " " + apikey
                                + " " + status + " " + phone + " " + createdAt + " " + updatedAt);
//                        SaveIntoDatabase(fname, lname, email, apikey, status, phone, createdAt, updatedAt, mPassword);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(activity,
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(activity, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                myApplication.ErrorSnackBar(activity);
                myApplication.hideDialog();
                return;
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("modelId", String.valueOf(modelid));
                params.put("vehicleNo", vehicle_number);
                params.put("type", type);

                return params;
            }
        };
        // Adding request to request queue
        myApplication.addToRequestQueue(strReq, tag_string_req);

    }

    public void UpdateVehicle(final String VehicleId, final String name, String insuranceCompany,
                              String insurace_exp_date, String pollution_exp_date,
                              String service_exp_date, String note) {

        String tag_string_req = "req_login";

        myApplication.DialogMessage("Loging in...");
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
                        JSONObject profileObj = jObj.getJSONObject("result");

                        String fname = profileObj.getString("firstName");
                        String lname = profileObj.getString("lastName");
                        String email = profileObj.getString("email");
                        String apikey = profileObj.getString("apiKey");
                        int status = profileObj.getInt("status");
                        String phone = profileObj.getString("phone");
                        String createdAt = profileObj.getString("createdAt");
                        String updatedAt = profileObj.getString("updatedAt");

                        Log.e(TAG, "" + fname + " " + lname + " " + email + " " + apikey
                                + " " + status + " " + phone + " " + createdAt + " " + updatedAt);
//                        SaveIntoDatabase(fname, lname, email, apikey, status, phone, createdAt, updatedAt, mPassword);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(activity,
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(activity, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                myApplication.ErrorSnackBar(activity);
                myApplication.hideDialog();
                return;
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
//                params.put("modelId", String.valueOf(modelid));
//                params.put("vehicleNo", vehicle_number);
//                params.put("type", type);

                return params;
            }
        };
        // Adding request to request queue
        myApplication.addToRequestQueue(strReq, tag_string_req);

    }
}
