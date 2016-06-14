package in.vaksys.vivekpk.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import in.vaksys.vivekpk.R;
import io.realm.Realm;

public class Temp extends AppCompatActivity {

    private static final String TAG = "Temp";
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
//        startService(new Intent(this, RegistrationIntentService.class));
       /* realm = Realm.getDefaultInstance();

        RealmResults<Users> results = realm.allObjects(Users.class);
        Log.e(TAG, "onCreate: " );
        for (Users task : results) {
            Log.e(TAG, "onCreate: " + String.format("Fname : %s , Lname : %s , Email : %s , Apikey : %s , phone : %s , createdAt : %s ," +
                            " updatedAt : %s , Status : %s , UserID : %s "
                    , task.getFirstName(), task.getLastName(), task.getEmail(), task.getApiKey(), task.getPhoneNo(),
                    task.getCreatedAt(), task.getUpdatedAt(), task.getStatus(), task.getUserid()));
        }
*/
        try {
            InstanceID instanceID = InstanceID.getInstance(this);

            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            SharedPreferences sharedPreferences = getSharedPreferences("device_token", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token", token);
            editor.commit();

            Log.i(TAG, "GCM Registration Token: " + token);

        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
        }
    }
}
