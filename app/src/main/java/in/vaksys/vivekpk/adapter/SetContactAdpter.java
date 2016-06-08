package in.vaksys.vivekpk.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.dbPojo.EmergencyContact;
import in.vaksys.vivekpk.extras.AppConfig;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.fragments.EmergencyFragment;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;

/**
 * Created by patel on 30-05-2016.
 */
public class SetContactAdpter extends RecyclerView.Adapter<SetContactAdpter.AdapterHolder> {
    private static final String TAG = "SetContactAdpter";
    private Context context;

    private Realm realm;
    public static ProgressDialog MyDialog;
    private RealmResults<EmergencyContact> data;
    AdapterHolder viewHolder;
    MyApplication myApplication;
    OkHttpClient client;


    public SetContactAdpter(Context context, RealmResults<EmergencyContact> data) {

        realm = Realm.getDefaultInstance();
        this.context = context;
        this.data = data;
        this.myApplication = MyApplication.getInstance();
        client = new OkHttpClient();
    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_contact_number, parent, false);
        viewHolder = new AdapterHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterHolder holder, int position) {

        final EmergencyContact emergencyContact = data.get(position);


        holder.tv_contactName.setText(emergencyContact.getContactName());
        holder.tv_contactNumber.setText(emergencyContact.getPhoneNumber());

        final int id = emergencyContact.getId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                myApplication.showLog("id", "" + id);

//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//                alertDialogBuilder.setTitle("Sure Want to Delete this Contact??")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
                //   DeleteVehicle((Activity) context, Integer.parseInt(holder.VehicleIDHiddden.getText().toString()));
                DeleteContact(id, context);
//                                data.addChangeListener(new RealmChangeListener<RealmResults<EmergencyContact>>() {
//                    @Override
//                    public void onChange(RealmResults<EmergencyContact> element) {
//                        notifyDataSetChanged();
//
//                        EmergencyFragment emergencyFragment = new EmergencyFragment();
//                        emergencyFragment.switchUI();
//                    }
//                });
                //          dialog.dismiss();

                //              }
                //         })
                //         .setNegativeButton("No", new DialogInterface.OnClickListener() {
                //            public void onClick(DialogInterface dialog, int id) {
                //                dialog.cancel();
                //           }
                ///       });

                //   AlertDialog alertDialog = alertDialogBuilder.create();
                //   alertDialog.show();


//                MyApplication.getInstance().showLog("id", String.valueOf(id));
//                DeleteContact(id, context, v);
////                VolleyHelper helper = new VolleyHelper((Activity) context);
////                helper.DeleteContact(id);
//                data.addChangeListener(new RealmChangeListener<RealmResults<EmergencyContact>>() {
//                    @Override
//                    public void onChange(RealmResults<EmergencyContact> element) {
//                        notifyDataSetChanged();
//
//                        EmergencyFragment emergencyFragment = new EmergencyFragment();
//                        emergencyFragment.switchUI();
//                    }
//                });


            }
        });


    }


    public void DeleteContact(final int contactid, final Context context) {
        MyApplication.getInstance().DialogMessage("Deleting Contact...");
        MyApplication.getInstance().showDialog();


        String tag_string_req = "req_delete_vehicle";

        myApplication.DialogMessage("Deleting Contact...");
        myApplication.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.DELETE,
                AppConfig.URL_EMERGENY_CONTACT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                myApplication.hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "onResponse: " + jObj.toString());

                    // Check for error node in json
                    if (!error) {
                        Toast.makeText(context,
                                "Delete Successfull... ", Toast.LENGTH_LONG).show();

                        // parsing the user profile information
//                        JSONObject profileObj = jObj.getJSONObject("result");

                        DeleteContactToDatabase(contactid);
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(context,
                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(context, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                myApplication.ErrorSnackBar((Activity) context);
                myApplication.hideDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
                headers.put("id", String.valueOf(contactid));
                myApplication.showLog(TAG, String.valueOf("passed auth"));
                return headers;

            }
        };
        // Adding request to request queue
        myApplication.addToRequestQueue(strReq, tag_string_req);

    }


    @Override
    public int getItemCount() {
        return (null != data ? data.size() : 0);
    }

    public class AdapterHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_contactName)
        TextView tv_contactName;

        @Bind(R.id.tv_contactNumber)
        TextView tv_contactNumber;


        AdapterHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    private void DeleteContactToDatabase(final int contactid) {


        realm.beginTransaction();


        final EmergencyContact emergencyContact1 = realm.where(EmergencyContact.class).equalTo("id", contactid).findFirst();

        emergencyContact1.deleteFromRealm();

        realm.commitTransaction();

        final RealmResults<EmergencyContact> results = realm.where(EmergencyContact.class).findAll();
        Log.e(TAG, "SaveIntoDatabase: " + results.size());

        data.addChangeListener(new RealmChangeListener<RealmResults<EmergencyContact>>() {
            @Override
            public void onChange(RealmResults<EmergencyContact> element) {
                notifyDataSetChanged();
            }
        });

        if (results.size() == 0) {
            EmergencyFragment.EmergencyRecyclerview.setVisibility(View.GONE);
            EmergencyFragment.btnContactOne.setVisibility(View.VISIBLE);
            EmergencyFragment.btnContactTwo.setVisibility(View.VISIBLE);
        }

        if (results.size() == 1) {
            EmergencyFragment.EmergencyRecyclerview.setVisibility(View.VISIBLE);
            EmergencyFragment.btnContactTwo.setVisibility(View.VISIBLE);
        }

        myApplication.hideDialog();

    }


}
