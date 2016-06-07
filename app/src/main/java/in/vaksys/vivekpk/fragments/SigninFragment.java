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
import in.vaksys.vivekpk.dbPojo.EmergencyContact;
import in.vaksys.vivekpk.dbPojo.InsuranceCompanies;
import in.vaksys.vivekpk.dbPojo.Users;
import in.vaksys.vivekpk.dbPojo.VehicleDetails;
import in.vaksys.vivekpk.dbPojo.VehicleModels;
import in.vaksys.vivekpk.extras.AppConfig;
import in.vaksys.vivekpk.extras.MyApplication;
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
    private TextView tvErrorPhoneNo, tvErrorPassword, forgotPassword;
    private Button btnSignIn;
    boolean isFormValid = true;
    private Realm realm;
    RealmAsyncTask realmAsyncTask;
    String mContactNo, mPassword;
    MyApplication myApplication;
    VehicleModels vehicleModels;
    InsuranceCompanies insuranceCompanies;
    VehicleDetails vehicleDetails;
    EmergencyContact emergencyContact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signin, container, false);

        etPhoneNo = (EditText) rootView.findViewById(R.id.et_phoneNo);
        etPassword = (PasswordEditText) rootView.findViewById(R.id.et_password);
        tvErrorPhoneNo = (TextView) rootView.findViewById(R.id.tv_errorPhoneNo);
        tvErrorPassword = (TextView) rootView.findViewById(R.id.tv_errorPassword);
        btnSignIn = (Button) rootView.findViewById(R.id.btn_signin);
        forgotPassword = (TextView) rootView.findViewById(R.id.Forgot_passwrod11);

        myApplication = MyApplication.getInstance();
        realm = Realm.getDefaultInstance();

        myApplication.createDialog(getActivity(), false);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication.hideKeyboard(getActivity());
                submitForm();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ForgotPassWordActivity.class));
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


        signIn(mContactNo, mPassword);

    }

    private void signIn(final String mContactNo, final String mPassword) {
        String tag_string_req = "req_login";

        myApplication.DialogMessage("Loging in...");
        myApplication.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SIGNIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
//                myApplication.hideDialog();

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
                        myApplication.hideDialog();

                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getActivity(),
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (JSONException e) {
                    myApplication.hideDialog();
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
//        myApplication.showDialog();
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
//        myApplication.hideDialog();
        LodingModels();

    }

    private void LodingModels() {
        myApplication.DialogMessage("Loading Models...");
//        myApplication.showDialog();
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, AppConfig.URL_SPINNER_VEHICLE_MODELS, new Response.Listener<JSONObject>() {
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

                        LoadingInsuranceCompanies();

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

    private void LoadingInsuranceCompanies() {
        myApplication.DialogMessage("Loading Insurance Companies...");
//        myApplication.showDialog();
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, AppConfig.URL_GET_INSURANCE_COMPANY,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                setAreaSpinner();
                        try {

                            boolean error = response.getBoolean("error");
                            if (!error) {
                                realm.beginTransaction();
                                // Getting JSON Array node
                                JSONArray results1 = response.getJSONArray("result");

                                insuranceCompanies = realm.createObject(InsuranceCompanies.class);

                                insuranceCompanies.setInsuranceId(0);
                                insuranceCompanies.setInsuranceName("Select Company");
                                insuranceCompanies.setInsuranceCreatedAt("31131");
                                insuranceCompanies.setInsuranceUpdatedAt("21232");

                                for (int i = 0; i < results1.length(); i++) {

                                    JSONObject jsonObject = results1.getJSONObject(i);
                                    int id = jsonObject.getInt("id");
                                    String InsuranceName = jsonObject.getString("name");
                                    String createdAt = jsonObject.getString("createdAt");
                                    String updatedAt = jsonObject.getString("updatedAt");

                                    insuranceCompanies = realm.createObject(InsuranceCompanies.class);

                                    insuranceCompanies.setInsuranceId(id);
                                    insuranceCompanies.setInsuranceName(InsuranceName);
                                    insuranceCompanies.setInsuranceCreatedAt(createdAt);
                                    insuranceCompanies.setInsuranceUpdatedAt(updatedAt);

                                }
                                realm.commitTransaction();
//                                myApplication.hideDialog();

                                LoadingUserVehicles();


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

    private void LoadingUserVehicles() {
        myApplication.DialogMessage("Loading User Vehicles...");
//        myApplication.showDialog();
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, AppConfig.URL_GET_USER_VEHICLE,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                setAreaSpinner();
                        try {

                            boolean error = response.getBoolean("error");
                            if (!error) {
                                realm.beginTransaction();
                                // Getting JSON Array node
                                JSONArray results1 = response.getJSONArray("result");

                                for (int i = 0; i < results1.length(); i++) {

                                    JSONObject jsonObject = results1.getJSONObject(i);
                                    int id = jsonObject.getInt("id");
                                    String VehicleName = jsonObject.getString("name");
                                    int modelId = jsonObject.getInt("modelId");
                                    String vehicleNo = jsonObject.getString("vehicleNo");
                                    String insuranceCompany = jsonObject.getString("insuranceCompany");
                                    String insuranceExpDate = jsonObject.getString("insuranceExpDate");
                                    String pollutionExpDate = jsonObject.getString("pollutionExpDate");
                                    String service_exp_date = jsonObject.getString("service_exp_date");
                                    String note = jsonObject.getString("note");
                                    String type = jsonObject.getString("type");
//                                    String createdAt = jsonObject.getString("createdAt");
//                                    String updatedAt = jsonObject.getString("updatedAt");

                                    vehicleDetails = realm.createObject(VehicleDetails.class);

                                    vehicleDetails.setVehicleId(id);
                                    vehicleDetails.setName(VehicleName);
                                    vehicleDetails.setVehicleModelID(modelId);
                                    vehicleDetails.setVehicleNo(vehicleNo);
                                    vehicleDetails.setInsuranceCompany(insuranceCompany);
                                    vehicleDetails.setInsuranceExpireDate(insuranceExpDate);
                                    vehicleDetails.setPollutionExpireDate(pollutionExpDate);
                                    vehicleDetails.setServiceExpireDate(service_exp_date);
                                    vehicleDetails.setNote(note);
                                    vehicleDetails.setType(type);


                                }
                                realm.commitTransaction();
                                myApplication.hideDialog();

                                LoadingEmergenyContact();


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

    private void LoadingEmergenyContact() {
        myApplication.DialogMessage("Loading Emergency Contact...");
//        myApplication.showDialog();


        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, AppConfig.URL_EMERGENY_CONTACT,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                setAreaSpinner();
                        try {

                            boolean error = response.getBoolean("error");
                            if (!error) {
                                realm.beginTransaction();
                                // Getting JSON Array node
                                JSONArray results1 = response.getJSONArray("result");
                                myApplication.showLog(TAG, "" + results1.length());

                                if (results1.length() > 0) {
                                    for (int i = 0; i < results1.length(); i++) {

                                        JSONObject jsonObject = results1.getJSONObject(i);
                                        int id = jsonObject.getInt("id");
                                        String name = jsonObject.getString("name");
                                        String phone = jsonObject.getString("phone");

                                        emergencyContact = realm.createObject(EmergencyContact.class);

                                        emergencyContact.setId(id);
                                        emergencyContact.setContactName(name);
                                        emergencyContact.setPhoneNumber(phone);

                                    }
                                    realm.commitTransaction();
                                    myApplication.hideDialog();

                                    LoadingInstallation();


                                }
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


    private void LoadingInstallation() {
        myApplication.DialogMessage("Loading Installation...");
//        myApplication.showDialog();
        final StringRequest installationRequest = new StringRequest(Request.Method.POST, AppConfig.URL_INSTALLATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "onResponse: " + jObj.toString());

                    if (!error) {
                        Toast.makeText(getActivity(),
                                "Installation Successfull... ", Toast.LENGTH_LONG).show();

                        // parsing the user profile information
                        JSONObject installationObj = jObj.getJSONObject("result");

                        int id = installationObj.getInt("id");
                        String deviceToken = installationObj.getString("deviceToken");
                        String deviceType = installationObj.getString("deviceType");
                        String createdAt = installationObj.getString("createdAt");
                        String updatedAt = installationObj.getString("updatedAt");
                        realm = Realm.getDefaultInstance();

                        realm.beginTransaction();
                        Installation installation = realm.createObject(Installation.class);

                        installation.setDeviceType(deviceToken);
                        installation.setDeviceType(deviceType);
                        installation.setInstallationId(id);
                        installation.setCreatedAt(createdAt);
                        installation.setUpdatedAt(updatedAt);

                        realm.commitTransaction();

                        LoadingSubscription(id);
                        //startActivity(new Intent(getActivity(), HomeActivity.class));

                    } else {
                        myApplication.hideDialog();

                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getActivity(),
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                        return;
                    }

                } catch (JSONException e) {
                    myApplication.hideDialog();
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myApplication.ErrorSnackBar(getActivity());
                myApplication.hideDialog();
                return;
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("deviceToken", "abc");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
                return headers;
            }
        };
        myApplication.addToRequestQueue(installationRequest);
    }

    private void LoadingSubscription(final int installId) {
        myApplication.DialogMessage("Loading Subscription...");
//        myApplication.showDialog();
        final StringRequest subscriptionRequest = new StringRequest(Request.Method.POST, AppConfig.URL_SUBSCRIPTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "onResponse: " + jObj.toString());

                    if (!error) {
                        Toast.makeText(getActivity(),
                                "Subscription Successfull... ", Toast.LENGTH_LONG).show();

                        // parsing the user profile information
                        JSONObject subscriptionObj = jObj.getJSONObject("result");

                        int idSub = subscriptionObj.getInt("userId");
                        int installationId = subscriptionObj.getInt("installationId");
                        String createdAtSub = subscriptionObj.getString("createdAt");
                        String updatedAtSub = subscriptionObj.getString("updatedAt");
                        /*realm = Realm.getDefaultInstance();

                        realm.beginTransaction();
                        Installation installation = realm.createObject(Installation.class);

                        installation.setDeviceType(deviceToken);
                        installation.setDeviceType(deviceType);
                        installation.setInstallationId(id);
                        installation.setCreatedAt(createdAt);
                        installation.setUpdatedAt(updatedAt);

                        realm.commitTransaction();*/


                        startActivity(new Intent(getActivity(), HomeActivity.class));

                    } else {
                        myApplication.hideDialog();

                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getActivity(),
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                        return;
                    }

                } catch (JSONException e) {
                    myApplication.hideDialog();
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myApplication.ErrorSnackBar(getActivity());
                myApplication.hideDialog();
                return;
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("installationId", String.valueOf(installId));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
                return headers;
            }
        };
        myApplication.addToRequestQueue(subscriptionRequest);
    }

    @Override
    public void onStop() {
        realm.close();

        super.onStop();
        // Remember to close the Realm instance when done with it.
        // TODO: 19-05-2016 handle realm.close();
    }

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
