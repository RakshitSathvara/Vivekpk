package in.vaksys.vivekpk.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.dbPojo.Users;
import io.realm.Realm;
import io.realm.RealmResults;

public class Temp extends AppCompatActivity {

    private static final String TAG = "Temp";
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        realm = Realm.getDefaultInstance();

        RealmResults<Users> results = realm.allObjects(Users.class);
        Log.e(TAG, "onCreate: " );
        for (Users task : results) {
            Log.e(TAG, "onCreate: " + String.format("Fname : %s , Lname : %s , Email : %s , Apikey : %s , phone : %s , createdAt : %s ," +
                            " updatedAt : %s , Status : %s , UserID : %s "
                    , task.getFirstName(), task.getLastName(), task.getEmail(), task.getApiKey(), task.getPhoneNo(),
                    task.getCreatedAt(), task.getUpdatedAt(), task.getStatus(), task.getUserid()));
        }

    }
}
