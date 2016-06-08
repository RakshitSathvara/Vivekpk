package in.vaksys.vivekpk.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import in.vaksys.vivekpk.activities.HomeActivity;
import in.vaksys.vivekpk.activities.MainActivity;
import in.vaksys.vivekpk.activities.ResetPasswordActivity;
import in.vaksys.vivekpk.dbPojo.Users;
import in.vaksys.vivekpk.extras.AppConfig;
import in.vaksys.vivekpk.extras.MyApplication;
import io.realm.Realm;

/**
 * Created by Harsh on 07-01-2016.
 */
public class HttpService extends IntentService {

    private static String TAG = HttpService.class.getSimpleName();
    private Realm realm;

    public HttpService() {
        super(HttpService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String number = intent.getStringExtra("mobile");
            String otp = intent.getStringExtra("otp");
            verifyOtp(number, otp);
        }
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

                        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                        if (sharedPreferences.getBoolean("change", false)) {
                            UpdateUserIntoDatabase(sharedPreferences.getString("mFname", ""), sharedPreferences.getString("mLname", ""),
                                    sharedPreferences.getString("mEmail", ""), sharedPreferences.getString("number", ""),
                                    sharedPreferences.getString("mPassnew", ""), sharedPreferences.getString("mPassold", ""));
                            sharedPreferences.edit().putBoolean("change", false).apply();
                            return;
                        }
                        if (sharedPreferences.getBoolean("reset", false)) {
                            sharedPreferences.edit().putBoolean("reset", false).apply();
                            Intent intent = new Intent(HttpService.this, ResetPasswordActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            return;
                        }
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        SetHome();

                    } else {
                        String message = responseObj.getString("message");
                        Toast.makeText(getApplicationContext(), "Error : " + message, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
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
        MyApplication.getInstance().addToRequestQueue(strReq);
    }

    private void SetHome() {
        Intent intent = new Intent(HttpService.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
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
        SetHome();
        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Setup Complete", Toast.LENGTH_LONG).show();
    }

}