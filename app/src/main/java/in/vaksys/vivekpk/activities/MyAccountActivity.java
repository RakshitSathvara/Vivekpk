package in.vaksys.vivekpk.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import in.vaksys.vivekpk.dbPojo.Users;
import in.vaksys.vivekpk.extras.AppConfig;
import in.vaksys.vivekpk.extras.MyApplication;
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
    Dialog confirm;
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

        loadData();
        etPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialogPassWord();
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


    public void UpdateUser(final String mFname, final String mLname, final String mPass, final String mNumber, final String mEmail, final Map<String, String> finalParams) {

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

                        UpdateUserIntoDatabase(mFname, mLname, mEmail, mNumber, mPass);

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

    private void UpdateUserIntoDatabase(String mFname, String mLname, String mEmail, String mNumber, String mPass) {
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

        Toast.makeText(this, "Setup Complete", Toast.LENGTH_LONG).show();
        myApplication.hideDialog();
        onBackPressed();
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
                break;
            case R.id.btn_myaccount_save:
                submitForm();
                break;
            case R.id.btn_myaccount_logout:
                break;
            case R.id.btn_myaccount_deactiveAccount:
                break;
        }
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
            UpdateUser(mFname, mLname, mPass, mNumber, mEmail, FinalParams);
        } else {
            Toast.makeText(MyAccountActivity.this, "No Changes Detected.", Toast.LENGTH_SHORT).show();
        }
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
        }
        if (!mPass.equalsIgnoreCase(oldPassword)) {
            params.put("password", mPass);
        }
        return params;
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
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
