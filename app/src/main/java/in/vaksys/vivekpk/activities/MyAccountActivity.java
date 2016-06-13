package in.vaksys.vivekpk.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.dbPojo.EmergencyContact;
import in.vaksys.vivekpk.dbPojo.Installation;
import in.vaksys.vivekpk.dbPojo.InsuranceCompanies;
import in.vaksys.vivekpk.dbPojo.UserImages;
import in.vaksys.vivekpk.dbPojo.Users;
import in.vaksys.vivekpk.dbPojo.VehicleDetails;
import in.vaksys.vivekpk.dbPojo.VehicleModels;
import in.vaksys.vivekpk.extras.AppConfig;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.extras.PreferenceHelper;
import in.vaksys.vivekpk.extras.VolleyHelper;
import io.realm.Realm;

public class MyAccountActivity extends AppCompatActivity {

    @Bind(R.id.et_firstName)
    EditText etFirstName;
    @Bind(R.id.et_lastName)
    EditText etLastName;
    @Bind(R.id.et_emailId)
    EditText etEmailId;
    @Bind(R.id.et_mobileNumber)
    EditText etMobileNumber;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.btn_myaccount_cancel)
    Button btnMyaccountCancel;
    @Bind(R.id.btn_myaccount_save)
    Button btnMyaccountSave;
    @Bind(R.id.btn_myaccount_logout)
    Button btnMyaccountLogout;
    @Bind(R.id.btn_myaccount_deactiveAccount)
    Button btnMyaccountDeactiveAccount;

    private Toolbar toolbar;
    private Realm realm;
    private String oldPassword;
    Dialog confirm, confirm1;
    MyApplication myApplication;
    private static final String TAG = "MyAccountActivity";
    private String mFname;
    private String mLname;
    private String mPass;
    private String mNumber;
    private String mEmail;
    private String oldFname;
    private String oldLname;
    private String oldMnumber;
    private String oldEmail;
    Map<String, String> FinalParams;
    private int i;
    SharedPreferences sharedPreferences;
    VolleyHelper helper;
    private int j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account_details);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();
        myApplication = MyApplication.getInstance();
        myApplication.createDialog(this, false);
        toolbar = (Toolbar) findViewById(R.id.myAccountToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        helper = new VolleyHelper(this);
        i = 0;
        j = 0;
        loadData();
        etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);

/*
        etPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialogPassWord();
            }
        });
*/
        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                confirmDialogPassWord();
                return true;
            }
        });
    }

    private void confirmDialogPassWord() {
        confirm = new Dialog(this, R.style.DialogTheme);
        confirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirm.setContentView(R.layout.change_password);

        Button BtnUpdate = (Button) confirm.findViewById(R.id.btn_Update_Password);
        final EditText oldPass = (EditText) confirm.findViewById(R.id.et_currentPassword);
        final EditText pass = (EditText) confirm.findViewById(R.id.et_newPassword);
        final EditText ConfirmPass = (EditText) confirm.findViewById(R.id.et_confirmPassword);

        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyPasswrod(oldPass.getText().toString(), pass.getText().toString(), ConfirmPass.getText().toString());
            }
        });

        confirm.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                // ProjectsActivity is my 'home' activity
                finish();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void VerifyPasswrod(String old, String NewPass, String CPass) {

        if (old.equals(oldPassword)) {
            if (NewPass.length() > 6) {
                if (NewPass.equals(CPass)) {
                    if (!old.equals(NewPass)) {
                        etPassword.setText(NewPass);
                        confirm.dismiss();
                    } else {
                        Toast.makeText(MyAccountActivity.this, "You Should Enter New Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MyAccountActivity.this, "New Password Doesn't match with Confirm Password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MyAccountActivity.this, getString(R.string.err_valid_password), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MyAccountActivity.this, "New Password Doesn't match with old Password", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData() {
        Users user = realm.where(Users.class).findFirst();

        etPassword.setInputType(InputType.TYPE_NULL);

        oldPassword = user.getPassword();
        oldFname = user.getFirstName();
        oldLname = user.getLastName();
        oldMnumber = user.getPhoneNo();
        oldEmail = user.getEmail();


        etFirstName.setText(oldFname);
        etLastName.setText(oldLname);
        etEmailId.setText(oldEmail);
        etMobileNumber.setText(oldMnumber);
        etPassword.setText(oldPassword);

    }

    @OnClick({R.id.btn_myaccount_cancel, R.id.btn_myaccount_save, R.id.btn_myaccount_logout, R.id.btn_myaccount_deactiveAccount})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_myaccount_cancel:
                finish();
                break;
            case R.id.btn_myaccount_save:
                submitForm();
                break;
            case R.id.btn_myaccount_logout:
                DeleteDataBase();
                break;
            case R.id.btn_myaccount_deactiveAccount:
                startActivity(new Intent(MyAccountActivity.this, DiactivateAccountActivity.class));
                break;
        }
    }

    private void DeleteDataBase() {
        realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(EmergencyContact.class);
                realm.delete(Installation.class);
                realm.delete(InsuranceCompanies.class);
                realm.delete(UserImages.class);
                realm.delete(Users.class);
                realm.delete(VehicleDetails.class);
                realm.delete(VehicleModels.class);

                sharedPreferences.edit().clear().apply();
                PreferenceHelper helper = new PreferenceHelper(MyAccountActivity.this);
                helper.clearAllPrefs();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(MyAccountActivity.this, "Logout.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MyAccountActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(MyAccountActivity.this, "There is some Error in Logout Sequence.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitForm() {
        if (!validateFirstName()) {
            return;
        }
        if (!validateLastName()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
        if (!validateNumber()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }
        UpdateInfo();
    }

    private void UpdateInfo() {
        mFname = etFirstName.getText().toString();
        mLname = etLastName.getText().toString();
        mPass = etPassword.getText().toString();
        mNumber = etMobileNumber.getText().toString();
        mEmail = etEmailId.getText().toString();

        FinalParams = new HashMap<String, String>();
        FinalParams = ValidateValues(mFname, mLname, mPass, mNumber, mEmail);
        if (FinalParams.size() > 0) {
            if (i > 0) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("change", true);
                editor.putString("mobile", mNumber);
                editor.putString("mFname", mFname);
                editor.putString("mLname", mLname);
                editor.putString("mEmail", mEmail);
                editor.putString("mPassnew", mPass);
                editor.putString("mPassold", oldPassword);

                editor.apply();
                j++;

                UpdateUser(mFname, mLname, mPass, mNumber, mEmail, FinalParams, oldPassword);
//                resendOTP(mNumber);
            } else {
                UpdateUser(mFname, mLname, mPass, mNumber, mEmail, FinalParams, oldPassword);
            }
        } else {
            Toast.makeText(MyAccountActivity.this, "No Changes Detected.", Toast.LENGTH_SHORT).show();
        }
    }

    public void UpdateUser(final String mFname, final String mLname, final String mPass, final String mNumber, final String mEmail, final Map<String, String> finalParams, final String oldPassword) {

        String tag_string_req = "req_update_user";

        myApplication.DialogMessage("Updating User...");
        myApplication.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.PUT,
                AppConfig.URL_UPDATE_USER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "onResponse: " + jObj.toString());

                    // Check for error node in json
                    if (!error) {
                        Toast.makeText(MyAccountActivity.this,
                                "Login Successfull... ", Toast.LENGTH_LONG).show();
                        // parsing the user profile information
                        if (j > 0) {
                            startActivity(new Intent(MyAccountActivity.this, VerifyOtpActivity.class));
                        } else {
                            UpdateUserIntoDatabase(mFname, mLname, mEmail, mNumber, mPass, oldPassword);
                        }

                    } else {
                        // Error in login. Get the error message
                        myApplication.hideDialog();
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(MyAccountActivity.this,
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    myApplication.hideDialog();
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(MyAccountActivity.this, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                myApplication.ErrorSnackBar(MyAccountActivity.this);
                myApplication.hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                return finalParams;
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

    private void UpdateUserIntoDatabase(String mFname, String mLname, String mEmail, String mNumber, String mPass, String oldPassword) {
        if (mPass.isEmpty()) {
            mPass = oldPassword;
        }

        realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        Users user = realm.where(Users.class).findFirst();

        user.setFirstName(mFname);
        user.setLastName(mLname);
        user.setEmail(mEmail);
        user.setPhoneNo(mNumber);
        user.setPassword(mPass);

        realm.commitTransaction();

//        Toast.makeText(this, "Setup Complete", Toast.LENGTH_LONG).show();
        myApplication.hideDialog();
        onBackPressed();
    }

    private Map<String, String> ValidateValues(String mFname, String mLname, String mPass, String mNumber, String mEmail) {
        Map<String, String> params = new HashMap<String, String>();
        if (!mFname.equalsIgnoreCase(oldFname)) {
            params.put("firstName", mFname);
        }
        if (!mLname.equalsIgnoreCase(oldLname)) {
            params.put("lastName", mLname);
        }
        if (!mEmail.equalsIgnoreCase(oldEmail)) {
            params.put("email", mEmail);
        }
        if (!mNumber.equalsIgnoreCase(oldMnumber)) {
            params.put("phone", mNumber);
            i++;
        }
        if (!mPass.equalsIgnoreCase(oldPassword)) {
            params.put("password", mPass);
        }
        return params;
    }

    private void resendOTP(final String number) {
        String tag_string_req = "req_resend_otp";

        myApplication.DialogMessage("Sending OTP request ...");
        myApplication.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_RESEND_OTP, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                myApplication.hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    Log.e(TAG, "onResponse: " + jObj.toString());
                    Toast.makeText(myApplication, "Success ", Toast.LENGTH_LONG).show();

                    confirmDialog(number);
                    /*boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(myApplication,
                                "Unexpected Error Occure... " + errorMsg, Toast.LENGTH_LONG).show();
                        return;
                    }*/
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(myApplication, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                myApplication.ErrorSnackBar(MyAccountActivity.this);
                myApplication.hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", number);

                return params;
            }
        };
        // Adding request to request queue
        myApplication.addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Posting the OTP to server and activating the user
     *
     * @param otp otp received in the SMS
     */
    private void verifyOtp(final String number, final String otp) {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_VERIFY_OTP, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);

                try {
                    JSONObject responseObj = new JSONObject(response);

                    boolean error = responseObj.getBoolean("error");

                    if (!error) {
                        Toast.makeText(getApplicationContext(), "Registration Success", Toast.LENGTH_LONG).show();

//                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        confirm.dismiss();
                    } else {
                        String message = responseObj.getString("message");
                        Toast.makeText(getApplicationContext(), "Error : " + message, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", number);
                params.put("verificationCode", otp);

                Log.e(TAG, "Posting params: " + params.toString());
                return params;
            }

        };
        // Adding request to request queue
        myApplication.addToRequestQueue(strReq);
    }

    private void confirmDialog(final String number) {
        confirm1 = new Dialog(this);
        confirm1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirm1.setContentView(R.layout.dialog_confirm_otp);

        final EditText Otp = (EditText) confirm1.findViewById(R.id.et_enterOtp_dialog_otp);

        Button btnEdit = (Button) confirm1.findViewById(R.id.btn_cancle_dialog_otp);
        Button btnSend = (Button) confirm1.findViewById(R.id.btn_confirm_dialog_otp);

        TextView Mnumber = (TextView) confirm1.findViewById(R.id.tv_phoneNo_dialog_otp);
        TextView ResendOTP = (TextView) confirm1.findViewById(R.id.tv_resend_doalog_otp);

        Mnumber.setText(number);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm1.dismiss();
                requestFocus(etMobileNumber);
            }
        });
        ResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOTP(number);
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Otp.getText().toString().isEmpty()) {
                    verifyOtp(number, Otp.getText().toString());
                } else {
                    Toast.makeText(MyAccountActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
        confirm1.show();
    }

    private boolean validateFirstName() {
        if (etFirstName.getText().toString().trim().isEmpty()) {
            etFirstName.setError(getString(R.string.err_msg_first_name));
            requestFocus(etFirstName);
            return false;
        } else {
            return true;
        }
    }

    private boolean validateLastName() {
        if (etLastName.getText().toString().trim().isEmpty()) {
            etLastName.setError(getString(R.string.err_msg_last_name));
            requestFocus(etLastName);
            return false;
        } else {
            return true;
        }
    }

    private boolean validateEmail() {
        String email = etEmailId.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            etEmailId.setError(getString(R.string.err_msg_email));
            requestFocus(etEmailId);
            return false;
        } else {
            return true;
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validateNumber() {
        if (etMobileNumber.getText().toString().trim().isEmpty()) {
            etMobileNumber.setError(getString(R.string.err_msg_number));
            requestFocus(etMobileNumber);
            return false;
        }
        if (etMobileNumber.length() != 10) {
            etMobileNumber.setError(getString(R.string.err_msg_valid_number));
            requestFocus(etMobileNumber);
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
        if (etPassword.length() < 7) {
            etPassword.setError(getString(R.string.err_valid_password));
            requestFocus(etPassword);
            return false;
        } else {
            return true;
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
