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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.maksim88.passwordedittext.PasswordEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.activities.HomeActivity;
import in.vaksys.vivekpk.dbPojo.Users;
import in.vaksys.vivekpk.extras.AppConfig;
import in.vaksys.vivekpk.extras.MyApplication;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signin, container, false);

        etPhoneNo = (EditText) rootView.findViewById(R.id.et_phoneNo);
        etPassword = (PasswordEditText) rootView.findViewById(R.id.et_password);
        tvErrorPhoneNo = (TextView) rootView.findViewById(R.id.tv_errorPhoneNo);
        tvErrorPassword = (TextView) rootView.findViewById(R.id.tv_errorPassword);
        btnSignIn = (Button) rootView.findViewById(R.id.btn_signin);

        MyApplication.getInstance().createDialog(getActivity(), false);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().hideKeyboard(getActivity());
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

        MyApplication.getInstance().DialogMessage("Loging in...");
        MyApplication.getInstance().showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SIGNIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                MyApplication.getInstance().hideDialog();

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
                MyApplication.getInstance().ErrorSnackBar(getActivity());
                MyApplication.getInstance().hideDialog();
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
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void SaveIntoDatabase(final String fname, final String lname, final String email, final String apikey,
                                  final int status, final String phone, final String createdAt, final String updatedAt, final String password) {
        MyApplication.getInstance().DialogMessage("Setting Up Profile...");
        MyApplication.getInstance().showDialog();
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
                MyApplication.getInstance().hideDialog();
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                MyApplication.getInstance().hideDialog();
                Log.e(TAG, "execute: Error" + error);
            }
        });
*/
        RealmResults<Users> results = realm.where(Users.class).findAll();
        Log.e(TAG, "SaveIntoDatabase: " + results.size());
        Toast.makeText(getActivity(), "Setup Complete", Toast.LENGTH_LONG).show();
        MyApplication.getInstance().hideDialog();
        startActivity(new Intent(getActivity(), HomeActivity.class));

    }

    @Override
    public void onStop() {
        super.onStop();
        // Remember to close the Realm instance when done with it.
        realm.close();
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
