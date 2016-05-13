package in.vaksys.vivekpk.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;

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
import in.vaksys.vivekpk.service.HttpService;

public class VerifyOtpActivity extends AppCompatActivity {

    private static final String TAG = "VerifyOTP";
    @Bind(R.id.et_otp)
    EditText etOtp;
    @Bind(R.id.tv_resend)
    TextView tvResend;
    @Bind(R.id.iv_verify)
    ImageView ivVerify;
    @Bind(R.id.tv_instruction)
    TextView tvInstruction;
    @Bind(R.id.tv_optError)
    TextView tvOptError;
    @Bind(R.id.tv_verified)
    TextView tvVerified;
    @Bind(R.id.btn_verify)
    Button btnVerify;
    @Bind(R.id.tv_notReceive)
    TextView tvNotReceive;
    @Bind(R.id.tv_editNumber)
    TextView tvEditNumber;
    private Toolbar mToolbar;
    String number;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        number = sharedPreferences.getString("mobile", "");

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);
//        mToolbar.setLogo(R.drawable.ic_action_name1);


    }

    @OnClick({R.id.tv_resend, R.id.btn_verify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_resend:
                if (!number.equals("")) {
                    resendOTP();
                }
                break;
            case R.id.btn_verify:
                if (!number.equals("")) {
                    verifyOtp();
                }
                break;
        }
    }

    private void resendOTP() {
        String tag_string_req = "req_resend_otp";

        pDialog.setMessage("Resending OTP request ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_RESEND_OTP, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    Log.e(TAG, "onResponse: " + jObj.toString());
                    Toast.makeText(MyApplication.getInstance(), "Success ", Toast.LENGTH_LONG).show();

                    /*boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(MyApplication.getInstance(),
                                "Unexpected Error Occure... " + errorMsg, Toast.LENGTH_LONG).show();
                        return;
                    }*/
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(MyApplication.getInstance(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                ErrorSnackBar();
                hideDialog();
                return;
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
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void verifyOtp() {
        String otp = etOtp.getText().toString().trim();

        if (!otp.isEmpty()) {
            Intent grapprIntent = new Intent(getApplicationContext(), HttpService.class);
            grapprIntent.putExtra("mobile", number);
            grapprIntent.putExtra("otp", otp);
            startService(grapprIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void ErrorSnackBar() {
        Snackbar.with(VerifyOtpActivity.this)
                .type(SnackbarType.MULTI_LINE)
                .text("Check Internet Connection")
                .actionLabel("Done")
                .actionColor(Color.CYAN)
                .actionListener(new ActionClickListener() {
                    @Override
                    public void onActionClicked(Snackbar snackbar) {
                        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                    }
                })
                .duration(Snackbar.SnackbarDuration.LENGTH_LONG)
                .swipeToDismiss(false)
                .show(VerifyOtpActivity.this);
    }
}
