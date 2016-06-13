package in.vaksys.vivekpk.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class ResetPasswordActivity extends AppCompatActivity {

    @Bind(R.id.et_code_reset)
    EditText pass1;
    @Bind(R.id.et_contactNo_reset)
    EditText pass2;
    @Bind(R.id.btn_continue_reset_password_reset)
    Button btnContinueResetPasswordReset;
    MyApplication myApplication;
    private static final String TAG = "ForgotPassWordActivity";
    Realm realm;
    private String ContactNo;
    private int VarificationCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();
        Users users = realm.where(Users.class).findFirst();

        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        ContactNo = sharedPreferences.getString("mobile", "");

        myApplication = MyApplication.getInstance();
        myApplication.createDialog(this, false);
    }

    @OnClick(R.id.btn_continue_reset_password_reset)
    public void onClick() {
        if (!validatePassword()) {
            return;
        }
        if (!validatePassword2()) {
            return;
        }
        if (!(pass1.getText().toString().equals(pass2.getText().toString()))) {
            return;
        }
        UpdateUser(pass1.getText().toString(), ContactNo, VarificationCode);
    }

    public void UpdateUser(final String Password, final String phoneNumber, final int VarificationCode) {

        String tag_string_req = "req_update_user";

        myApplication.DialogMessage("Updating User...");
        myApplication.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.PUT,
                AppConfig.URL_RESET_PASSWORD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "onResponse: " + jObj.toString());

                    // Check for error node in json
                    if (!error) {


                        Toast.makeText(ResetPasswordActivity.this, "Password Successfully Updated", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(ResetPasswordActivity.this, MainActivity.class));
                        finish();
                    } else {
                        // Error in login. Get the error message
                        myApplication.hideDialog();
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(ResetPasswordActivity.this,
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    myApplication.hideDialog();
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(ResetPasswordActivity.this, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                myApplication.ErrorSnackBar(ResetPasswordActivity.this);
                myApplication.hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> FinalParams = new HashMap<String, String>();
                FinalParams.put("password", Password);
                FinalParams.put("phone", phoneNumber);
                FinalParams.put("verificationCode", String.valueOf(VarificationCode));

                return FinalParams;
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

    private boolean validatePassword() {
        if (pass1.getText().toString().trim().isEmpty()) {
            pass1.setError(getString(R.string.err_msg_password));
            requestFocus(pass1);
            return false;
        }
        if (pass1.length() < 7) {
            pass1.setError(getString(R.string.err_valid_password));
            requestFocus(pass1);
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePassword2() {
        if (pass2.getText().toString().trim().isEmpty()) {
            pass2.setError(getString(R.string.err_msg_password));
            requestFocus(pass2);
            return false;
        }
        if (pass2.length() < 7) {
            pass2.setError(getString(R.string.err_valid_password));
            requestFocus(pass2);
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
