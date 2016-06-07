package in.vaksys.vivekpk.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
import io.realm.Realm;

public class DiactivateAccountActivity extends AppCompatActivity {

    @Bind(R.id.tv_deactivateMy_account_number)
    TextView tvDeactivateMyAccountNumber;
    @Bind(R.id.et_deactive_my_account)
    EditText etDeactiveMyAccount;
    @Bind(R.id.tv_confirm_deactivation_my_account)
    TextView tvConfirmDeactivationMyAccount;
    private Toolbar toolbar;
    private String Oldpassword;
    Realm realm;
    MyApplication myApplication;
    private static final String TAG = "DiactivateAccountActivity";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diactivate_account);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();

        Users users = realm.where(Users.class).findFirst();

        tvDeactivateMyAccountNumber.setText(users.getPhoneNo());
        Oldpassword = users.getPassword();
        myApplication = MyApplication.getInstance();

        myApplication.createDialog(this, false);

        sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);

        toolbar = (Toolbar) findViewById(R.id.deactivateToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(DiactivateAccountActivity.this, HomeActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.tv_confirm_deactivation_my_account)
    public void onClick() {
        if (!etDeactiveMyAccount.getText().toString().isEmpty()) {
            if (etDeactiveMyAccount.getText().toString().equals(Oldpassword)) {
                DeActivateAcc(etDeactiveMyAccount.getText().toString());
            } else {
                Toast.makeText(DiactivateAccountActivity.this, "Make sure that the Password you have entered is Correct", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(DiactivateAccountActivity.this, "Please Provide Password.", Toast.LENGTH_SHORT).show();
        }
    }

    private void DeActivateAcc(final String passowrd) {

        myApplication.DialogMessage("Deactivating user account...");
        myApplication.showDialog();

        StringRequest request = new StringRequest(Request.Method.DELETE, AppConfig.URL_UPDATE_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                myApplication.hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    myApplication.showLog(TAG, jObj.toString());

                    // Check for error node in json
                    if (!error) {
                        Toast.makeText(DiactivateAccountActivity.this,
                                "Delete Successfull... ", Toast.LENGTH_LONG).show();

                        DeleteDataBase();

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(DiactivateAccountActivity.this,
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(DiactivateAccountActivity.this, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myApplication.ErrorSnackBar(DiactivateAccountActivity.this);
                myApplication.hideDialog();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
                headers.put("password", passowrd);
                return headers;
            }
        };
        myApplication.addToRequestQueue(request);

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
                PreferenceHelper helper = new PreferenceHelper(DiactivateAccountActivity.this);
                helper.clearAllPrefs();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(DiactivateAccountActivity.this, "Account Deactivate Succesfully ...", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DiactivateAccountActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(DiactivateAccountActivity.this, "There is some Error in Deactivation.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
