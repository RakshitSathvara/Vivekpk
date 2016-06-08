package in.vaksys.vivekpk.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import in.vaksys.vivekpk.extras.AppConfig;
import in.vaksys.vivekpk.extras.MyApplication;

/**
 * Created by Harsh on 07-06-2016.
 */
public class ForgotPassWordActivity extends AppCompatActivity {

    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.et_contactNo)
    EditText etContactNo;
    @Bind(R.id.btn_continue_forgot_pass)
    Button btnContinueForgotPass;
    MyApplication myApplication;
    private static final String TAG = "ForgotPassWordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        ButterKnife.bind(this);

        myApplication = MyApplication.getInstance();
        myApplication.createDialog(this, false);
    }

    @OnClick(R.id.btn_continue_forgot_pass)
    public void onClick() {
        confirmDialog(etContactNo.getText().toString());
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

                    SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("reset", true);
                    editor.putString("mobile", number);
                    editor.apply();

                    startActivity(new Intent(ForgotPassWordActivity.this, VerifyOtpActivity.class));
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
                myApplication.ErrorSnackBar(ForgotPassWordActivity.this);
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


    private void confirmDialog(final String mNumber) {
        final Dialog confirm = new Dialog(this);
        confirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirm.setContentView(R.layout.confirm_dialog);

        Button btnEdit = (Button) confirm.findViewById(R.id.et_context_edit);
        Button btnSend = (Button) confirm.findViewById(R.id.et_context_send);

        final TextView number = (TextView) confirm.findViewById(R.id.tv_phoneNo);

        number.setText(etContactNo.getText().toString());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm.dismiss();
                requestFocus(etContactNo);
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm.dismiss();
                resendOTP(mNumber);
            }
        });
        confirm.show();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
