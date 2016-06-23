package in.vaksys.vivekpk.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.adapter.ReminderAdpter;
import in.vaksys.vivekpk.adapter.SetContactAdpter;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.model.notificationdata;

public class NotificationActivity extends AppCompatActivity {

    Spinner spinner_select_value;
    private Toolbar toolbar;
    private RecyclerView recyclerView_reminder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        toolbar = (Toolbar) findViewById(R.id.NotificationToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        spinner_select_value = (Spinner) findViewById(R.id.spinner_select_value);
        spinner_select_value.setVisibility(View.GONE);

        recyclerView_reminder = (RecyclerView) findViewById(R.id.recycle_reminder_data);

        Intent intent = getIntent();

        String type = intent.getStringExtra("type");
        String vehicalno = intent.getStringExtra("vehicalno");
        String nofidate = intent.getStringExtra("nofidate");
        String nId = intent.getStringExtra("nId");

      MyApplication.getInstance().showLog("type",type);

//        List<String> strings = new ArrayList<>();
//        strings.add(type);
//        strings.add(vehicalno);
//        strings.add(nofidate);

//        List<notificationdata> list = new ArrayList<>();
//
//        notificationdata dd = new notificationdata();
//        dd.setRemndertype(type);
//        dd.setExpdate(nofidate);
//        dd.setVehecalno(vehicalno);
//
//        list.add(dd);



        MyApplication.getInstance().showLog("NotificationActivity", type + vehicalno + nofidate + nId);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(NotificationActivity.this);
        recyclerView_reminder.setLayoutManager(manager);

        ReminderAdpter setContactAdpter = new ReminderAdpter(NotificationActivity.this, type,vehicalno,nofidate);
        recyclerView_reminder.setAdapter(setContactAdpter);
        setContactAdpter.notifyDataSetChanged();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.notification_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.navToolbar:
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
                        startActivity(new Intent(NotificationActivity.this, WebViewActivity.class));
                    }
                });


                dialog.show();
                return true;

            case R.id.searchToolbar:
                startActivity(new Intent(NotificationActivity.this, SearchActivity.class));
                return true;

            case R.id.notificationToolbar:
                startActivity(new Intent(NotificationActivity.this, NotificationActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
