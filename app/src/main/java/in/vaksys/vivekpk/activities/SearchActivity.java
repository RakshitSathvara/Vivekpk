package in.vaksys.vivekpk.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.dbPojo.VehicleModels;
import in.vaksys.vivekpk.extras.AppConfig;
import in.vaksys.vivekpk.extras.MyApplication;
import io.realm.Realm;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SearchActivity";
    //    ArrayAdapter<String> adapter;
//    EditText inputSearch;
//    ArrayList<HashMap<String, String>> productList;
//    LinearLayout linear_contactSearch, linearVehicleDetails, linear_reasonContact, linear_searchInvite,
//            linear_trafficSignal, linear_towAway, linear_reportAccident;
//    ImageView imageView;
//    Spinner spinner_select_value;
//    private ListView listView;
    private Toolbar toolbar;
    private EditText searchVehicle;
    private Button btnSearchVehicle, btnInviteFriendSearchActivity;
    private View one, two, three, four;
    private String getDataSearchVehicle;
    MyApplication myApplication;
    private int userID;
    private int modelID;
    Realm realm;
    private TextView tvDetailvehicleNumberGen, tvDetailvehicleBrandGen, tvDetailvehicleNumberModelGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        myApplication = MyApplication.getInstance();

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        realm = Realm.getDefaultInstance();

        one = findViewById(R.id.view_vehicleSearch);
        two = findViewById(R.id.view_noVehicleFound);
        three = findViewById(R.id.view_searchVehicleData);
        four = findViewById(R.id.view_reasonsForContacting);

        searchVehicle = (EditText) findViewById(R.id.et_search_searchVehicleNumber);
        btnSearchVehicle = (Button) findViewById(R.id.btn_search_searchVehicle);
        tvDetailvehicleNumberGen = (TextView) findViewById(R.id.tv_detailvehicleNumber_gen);
        tvDetailvehicleBrandGen = (TextView) findViewById(R.id.tv_detailvehicleBrand_gen);
        tvDetailvehicleNumberModelGen = (TextView) findViewById(R.id.tv_detailvehicleNumberModel_gen);
        btnInviteFriendSearchActivity = (Button) findViewById(R.id.btn_inviteFriend_searchActivity);


        two.setVisibility(View.GONE);
        three.setVisibility(View.GONE);
        four.setVisibility(View.GONE);

        btnSearchVehicle.setOnClickListener(this);
        btnInviteFriendSearchActivity.setOnClickListener(this);

//        spinner_select_value = (Spinner) findViewById(R.id.spinner_select_value);
//        spinner_select_value.setVisibility(View.GONE);
//
//        /*imageView = (ImageView) findViewById(R.id.img_search);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SearchActivity.this, SearchActivity.class));
//            }
//        });*/
//
//        // Listview Data
//        String products[] = {"k0059745", "k0edf745", "sadfdf745", "ktrsf745", "av0890e745",
//                "ks059565", "k00fdg745",
//                "a00f59745", "ght59745", "k00fdgh45", "kdfg59745"};
//
//        listView = (ListView) findViewById(R.id.lv_searchContact);
//        inputSearch = (EditText) findViewById(R.id.et_searchVehicleNumber);
//
//        linear_contactSearch = (LinearLayout) findViewById(R.id.linear_contactSearch);
//        linearVehicleDetails = (LinearLayout) findViewById(R.id.linearVehicleDetails);
//        linear_reasonContact = (LinearLayout) findViewById(R.id.linear_reasonContact);
//        linear_searchInvite = (LinearLayout) findViewById(R.id.linear_searchInvite);
//
//
//        linear_trafficSignal = (LinearLayout) findViewById(R.id.linear_trafficSignal);
//        linear_towAway = (LinearLayout) findViewById(R.id.linear_towAway);
//        linear_reportAccident = (LinearLayout) findViewById(R.id.linear_reportAccident);
//
//        linear_trafficSignal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Dialog dialogSignal = new Dialog(SearchActivity.this);
//                dialogSignal.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialogSignal.setContentView(R.layout.dialog_search_signal_jumping);
//
//                dialogSignal.show();
//            }
//        });
//
//        linear_towAway.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Dialog linear_towAway = new Dialog(SearchActivity.this);
//                linear_towAway.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                linear_towAway.setContentView(R.layout.dialog_search_tow_away);
//
//                linear_towAway.show();
//            }
//        });
//
//
//        linear_reportAccident.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Dialog linear_reportAccident = new Dialog(SearchActivity.this);
//                linear_reportAccident.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                linear_reportAccident.setContentView(R.layout.dialog_search_accident_report);
//
//                linear_reportAccident.show();
//            }
//        });
//
//
//        // Adding items to listview
//        adapter = new ArrayAdapter<String>(this, R.layout.contact_list_raw, R.id.product_name, products);
//        listView.setAdapter(adapter);
//
//        inputSearch.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
//                // When user changed the Text
//                SearchActivity.this.adapter.getFilter().filter(cs);
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
//                                          int arg3) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                // TODO Auto-generated method stub
//            }
//        });
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                if (position == 2) {
//                    linear_contactSearch.setVisibility(View.GONE);
//                    linearVehicleDetails.setVisibility(View.GONE);
//                    linear_reasonContact.setVisibility(View.GONE);
//                    linear_searchInvite.setVisibility(View.VISIBLE);
//                } else if (position == 5) {
//                    linear_contactSearch.setVisibility(View.GONE);
//                    linearVehicleDetails.setVisibility(View.GONE);
//                    linear_reasonContact.setVisibility(View.GONE);
//                    linear_searchInvite.setVisibility(View.VISIBLE);
//                } else if (position == 7) {
//                    linear_contactSearch.setVisibility(View.GONE);
//                    linearVehicleDetails.setVisibility(View.GONE);
//                    linear_reasonContact.setVisibility(View.GONE);
//                    linear_searchInvite.setVisibility(View.VISIBLE);
//                } else if (position == 9) {
//                    linear_contactSearch.setVisibility(View.GONE);
//                    linearVehicleDetails.setVisibility(View.GONE);
//                    linear_reasonContact.setVisibility(View.GONE);
//                    linear_searchInvite.setVisibility(View.VISIBLE);
//                } else {
//                    linear_contactSearch.setVisibility(View.GONE);
//                    linearVehicleDetails.setVisibility(View.VISIBLE);
//                    linear_reasonContact.setVisibility(View.VISIBLE);
//                    linear_searchInvite.setVisibility(View.GONE);
//                }
//
//            }
//        });

    }

  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();
        linear_contactSearch.setVisibility(View.VISIBLE);
        linearVehicleDetails.setVisibility(View.GONE);
        linear_reasonContact.setVisibility(View.GONE);
        linear_searchInvite.setVisibility(View.GONE);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(SearchActivity.this, HomeActivity.class));
                return true;
            case R.id.navSearchToolbar:
                // Single menu item is selected do something
                // Ex: launching new activity/screen or show alert message
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setGravity(Gravity.TOP | Gravity.END);
                dialog.setContentView(R.layout.menu_list);

                LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.linear_help_toolbar);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(new Intent(SearchActivity.this, WebViewActivity.class));
                    }
                });


                dialog.show();
                return true;

            case R.id.searchSearchToolbar:
                startActivity(new Intent(SearchActivity.this, SearchActivity.class));
                return true;

            case R.id.notificationSearchToolbar:
                startActivity(new Intent(SearchActivity.this, NotificationActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search_searchVehicle:
                getDataSearchVehicle = searchVehicle.getText().toString();
                if (getDataSearchVehicle != null) {
                    getSearchVehicleData(getDataSearchVehicle);
                } else {
                    Toast.makeText(SearchActivity.this, "Please enter vehicle number", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_inviteFriend_searchActivity:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.\n https://play.google.com/store/apps/details?id=com.whatsapp&hl=en" );
                //sendIntent.putExtra(Intent.EXTRA_HTML_TEXT, "https://play.google.com/store/apps/details?id=com.whatsapp&hl=en");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
        }
    }

    private void getSearchVehicleData(final String data) {


        myApplication.DialogMessage("Loading Vehicles Data...");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.URL_SEARCH_VEHICLE + "?vehicleNo=" + data, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                myApplication.showLog(TAG, response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    myApplication.showLog(TAG, "" + error);

                    if (!error) {

                        three.setVisibility(View.VISIBLE);
                        four.setVisibility(View.VISIBLE);
                        one.setVisibility(View.GONE);

                        JSONArray jsonObj = jObj.getJSONArray("result");
                        for (int i = 0; i < jsonObj.length(); i++) {
                            JSONObject obj = jsonObj.getJSONObject(i);
                            userID = obj.getInt("userId");
                            modelID = obj.getInt("modelId");
                        }


                        myApplication.showLog(TAG, "User-id: " + String.valueOf(userID) +
                                "Model-id: " + String.valueOf(modelID));


                        VehicleModels vehicleDetails = realm.where(VehicleModels.class).equalTo("id", modelID).findFirst();

                        myApplication.showLog(TAG, "model name:" + vehicleDetails.getModel());
                        myApplication.showLog(TAG, "brand name:" + vehicleDetails.getManufacturerName());
                        myApplication.showLog(TAG, "vehicle no :" + data);
                        tvDetailvehicleNumberModelGen.setText(vehicleDetails.getModel());
                        tvDetailvehicleBrandGen.setText(vehicleDetails.getManufacturerName());
                        tvDetailvehicleNumberGen.setText(data);


                        //myApplication.showLog(TAG, String.valueOf(subscribId));
                    } else {
                        two.setVisibility(View.VISIBLE);
                        one.setVisibility(View.GONE);
                    }
                    ;

                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "52d8c0efea5039cd0d778db7521889cf");
                return headers;
            }
        };

        myApplication.addToRequestQueue(stringRequest);
    }
}
