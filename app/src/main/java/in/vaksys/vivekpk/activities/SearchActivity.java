package in.vaksys.vivekpk.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

import in.vaksys.vivekpk.R;

public class SearchActivity extends AppCompatActivity {

    private ListView listView;
    private Toolbar toolbar;
    ArrayAdapter<String> adapter;
    EditText inputSearch;
    ArrayList<HashMap<String, String>> productList;

    LinearLayout linear_contactSearch, linearVehicleDetails, linear_reasonContact, linear_searchInvite,
            linear_trafficSignal, linear_towAway, linear_reportAccident;
    ImageView imageView;
    Spinner spinner_select_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        spinner_select_value = (Spinner) findViewById(R.id.spinner_select_value);
        spinner_select_value.setVisibility(View.GONE);

        /*imageView = (ImageView) findViewById(R.id.img_search);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, SearchActivity.class));
            }
        });*/

        // Listview Data
        String products[] = {"k0059745", "k0edf745", "sadfdf745", "ktrsf745", "av0890e745",
                "ks059565", "k00fdg745",
                "a00f59745", "ght59745", "k00fdgh45", "kdfg59745"};

        listView = (ListView) findViewById(R.id.lv_searchContact);
        inputSearch = (EditText) findViewById(R.id.et_searchVehicleNumber);

        linear_contactSearch = (LinearLayout) findViewById(R.id.linear_contactSearch);
        linearVehicleDetails = (LinearLayout) findViewById(R.id.linearVehicleDetails);
        linear_reasonContact = (LinearLayout) findViewById(R.id.linear_reasonContact);
        linear_searchInvite = (LinearLayout) findViewById(R.id.linear_searchInvite);


        linear_trafficSignal = (LinearLayout) findViewById(R.id.linear_trafficSignal);
        linear_towAway = (LinearLayout) findViewById(R.id.linear_towAway);
        linear_reportAccident = (LinearLayout) findViewById(R.id.linear_reportAccident);

        linear_trafficSignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogSignal = new Dialog(SearchActivity.this);
                dialogSignal.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogSignal.setContentView(R.layout.dialog_search_signal_jumping);

                dialogSignal.show();
            }
        });

        linear_towAway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog linear_towAway = new Dialog(SearchActivity.this);
                linear_towAway.requestWindowFeature(Window.FEATURE_NO_TITLE);
                linear_towAway.setContentView(R.layout.dialog_search_tow_away);

                linear_towAway.show();
            }
        });


        linear_reportAccident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog linear_reportAccident = new Dialog(SearchActivity.this);
                linear_reportAccident.requestWindowFeature(Window.FEATURE_NO_TITLE);
                linear_reportAccident.setContentView(R.layout.dialog_search_accident_report);

                linear_reportAccident.show();
            }
        });


        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.contact_list_raw, R.id.product_name, products);
        listView.setAdapter(adapter);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                SearchActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 2) {
                    linear_contactSearch.setVisibility(View.GONE);
                    linearVehicleDetails.setVisibility(View.GONE);
                    linear_reasonContact.setVisibility(View.GONE);
                    linear_searchInvite.setVisibility(View.VISIBLE);
                } else if (position == 5) {
                    linear_contactSearch.setVisibility(View.GONE);
                    linearVehicleDetails.setVisibility(View.GONE);
                    linear_reasonContact.setVisibility(View.GONE);
                    linear_searchInvite.setVisibility(View.VISIBLE);
                } else if (position == 7) {
                    linear_contactSearch.setVisibility(View.GONE);
                    linearVehicleDetails.setVisibility(View.GONE);
                    linear_reasonContact.setVisibility(View.GONE);
                    linear_searchInvite.setVisibility(View.VISIBLE);
                } else if (position == 9) {
                    linear_contactSearch.setVisibility(View.GONE);
                    linearVehicleDetails.setVisibility(View.GONE);
                    linear_reasonContact.setVisibility(View.GONE);
                    linear_searchInvite.setVisibility(View.VISIBLE);
                } else {
                    linear_contactSearch.setVisibility(View.GONE);
                    linearVehicleDetails.setVisibility(View.VISIBLE);
                    linear_reasonContact.setVisibility(View.VISIBLE);
                    linear_searchInvite.setVisibility(View.GONE);
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        linear_contactSearch.setVisibility(View.VISIBLE);
        linearVehicleDetails.setVisibility(View.GONE);
        linear_reasonContact.setVisibility(View.GONE);
        linear_searchInvite.setVisibility(View.GONE);
    }

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
}
