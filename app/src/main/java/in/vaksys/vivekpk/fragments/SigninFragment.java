package in.vaksys.vivekpk.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.maksim88.passwordedittext.PasswordEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.activities.HomeActivity;
import in.vaksys.vivekpk.dbPojo.Users;
import in.vaksys.vivekpk.dbPojo.VehicleModels;
import in.vaksys.vivekpk.extras.AppConfig;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.extras.PreferenceHelper;
import in.vaksys.vivekpk.service.RegistrationIntentService;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;

/**
 * Created by dell980 on 5/2/2016.
 */
public class SigninFragment extends Fragment {

    private static final String TAG = "Vivekpk" + SigninFragment.class.getSimpleName();

    private EditText etPhoneNo;
    private PasswordEditText etPassword;
    private TextView tvErrorPhoneNo, tvErrorPassword;
    private Button btnSignIn;
    boolean isFormValid = true;
    private Realm realm;
    RealmAsyncTask realmAsyncTask;
    String mContactNo, mPassword;
    MyApplication myApplication;
    VehicleModels vehicleModels;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signin, container, false);

        etPhoneNo = (EditText) rootView.findViewById(R.id.et_phoneNo);
        etPassword = (PasswordEditText) rootView.findViewById(R.id.et_password);
        tvErrorPhoneNo = (TextView) rootView.findViewById(R.id.tv_errorPhoneNo);
        tvErrorPassword = (TextView) rootView.findViewById(R.id.tv_errorPassword);
        btnSignIn = (Button) rootView.findViewById(R.id.btn_signin);

        myApplication = MyApplication.getInstance();

// TODO: 5/23/2016 solve erroor by this line..
        realm = Realm.getDefaultInstance();
        myApplication.createDialog(getActivity(), false);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication.hideKeyboard(getActivity());
                submitForm();
            }
        });

        return rootView;
    }

    private void submitForm() {

        if (!validateNumber()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }
        getData();
    }

    private void getData() {

        mContactNo = etPhoneNo.getText().toString();
        mPassword = etPassword.getText().toString();

        signUp(mContactNo, mPassword);

    }

    private void signUp(final String mContactNo, final String mPassword) {
        String tag_string_req = "req_login";

        myApplication.DialogMessage("Loging in...");
        myApplication.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SIGNIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                myApplication.hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "onResponse: " + jObj.toString());

                    // Check for error node in json
                    if (!error) {
                        Toast.makeText(getActivity(),
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
                        SaveIntoDatabase(fname, lname, email, apikey, status, phone, createdAt, updatedAt, mPassword);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getActivity(),
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                myApplication.ErrorSnackBar(getActivity());
                myApplication.hideDialog();
                return;
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("password", mPassword);
                params.put("phone", mContactNo);

                return params;
            }
        };
        // Adding request to request queue
        myApplication.addToRequestQueue(strReq, tag_string_req);

    }

    private void SaveIntoDatabase(final String fname, final String lname, final String email, final String apikey,
                                  final int status, final String phone, final String createdAt, final String updatedAt, final String password) {
        myApplication.DialogMessage("Setting Up Profile...");
        myApplication.showDialog();
/*
        realmAsyncTask = realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {*/
        realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        Users user = realm.createObject(Users.class);
        user.setUserid(UUID.randomUUID().toString());
        user.setFirstName(fname);
        user.setLastName(lname);
        user.setEmail(email);
        user.setApiKey(apikey);
        user.setStatus(status);
        user.setPhoneNo(phone);
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(updatedAt);
        user.setPassword(password);
        realm.commitTransaction();
  /*          }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "execute: Success");
                myApplication.hideDialog();
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                myApplication.hideDialog();
                Log.e(TAG, "execute: Error" + error);
            }
        });
*/
        RealmResults<Users> results = realm.where(Users.class).findAll();
        Log.e(TAG, "SaveIntoDatabase: " + results.size());

        getActivity().startService(new Intent(getActivity(), RegistrationIntentService.class));

        Toast.makeText(getActivity(), "Setup Complete", Toast.LENGTH_LONG).show();
        myApplication.hideDialog();
        LodingModels();

    }

    private void LodingModels() {
        myApplication.DialogMessage("Loading Models...");
        myApplication.showDialog();
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, AppConfig.URL_SPINNER, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                setAreaSpinner();
                try {

                    boolean error = response.getBoolean("error");
                    if (!error) {
                        realm.beginTransaction();
                        // Getting JSON Array node
                        JSONArray results1 = response.getJSONArray("result");

                        vehicleModels = realm.createObject(VehicleModels.class);

                        vehicleModels.setId(0);
                        vehicleModels.setManufacturerName("Select Brand");
                        vehicleModels.setModel("Select Model");
                        vehicleModels.setType("");
                        vehicleModels.setCreatedAt("31131");
                        vehicleModels.setUpdatedAt("21232");

                        for (int i = 0; i < results1.length(); i++) {

                            JSONObject jsonObject = results1.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String manufacturerName = jsonObject.getString("manufacturerName");
                            String model = jsonObject.getString("model");
                            String type = jsonObject.getString("type");
                            String createdAt = jsonObject.getString("createdAt");
                            String updatedAt = jsonObject.getString("updatedAt");

                            vehicleModels = realm.createObject(VehicleModels.class);

                            vehicleModels.setId(id);
                            vehicleModels.setManufacturerName(manufacturerName);
                            vehicleModels.setModel(model);
                            vehicleModels.setType(type);
                            vehicleModels.setCreatedAt(createdAt);
                            vehicleModels.setUpdatedAt(updatedAt);

                        }
                        realm.commitTransaction();
                        myApplication.hideDialog();

                        startActivity(new Intent(getActivity(), HomeActivity.class));
// todo addd line by one time login
                        getActivity().finish();


                    } else {
                        String errorMsg = response.getString("message");
                        Toast.makeText(getActivity(),
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                        myApplication.hideDialog();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    myApplication.hideDialog();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myApplication.hideDialog();
                //Toast.makeText(getApplicationContext(), "Responce : " + error, Toast.LENGTH_LONG).show();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    myApplication.ErrorSnackBar(getActivity());
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
                return headers;
            }
        };
        myApplication.addToRequestQueue(request);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Remember to close the Realm instance when done with it.
        // TODO: 19-05-2016 handle realm.close();
        realm.close();
    }

//    @Override
//    public void onDestroy() {
//
//
//    }

    private boolean validateNumber() {
        if (etPhoneNo.getText().toString().trim().isEmpty()) {
            etPhoneNo.setError(getString(R.string.err_msg_number));
            requestFocus(etPhoneNo);
            return false;
        }
        if (etPhoneNo.length() != 10) {
            etPhoneNo.setError(getString(R.string.err_msg_valid_number));
            requestFocus(etPhoneNo);
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePassword() {
        if (etPassword.getText().toString().trim().isEmpty()) {
            etPassword.setError(getString(R.string.err_msg_password));
            requestFocus(etPassword);
            return false;
        }
        if (etPassword.length() < 6) {
            etPassword.setError(getString(R.string.err_valid_password));
            requestFocus(etPassword);
            return false;
        } else {
            return true;
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
