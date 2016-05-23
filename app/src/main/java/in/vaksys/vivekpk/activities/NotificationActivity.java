package in.vaksys.vivekpk.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Spinner;

import in.vaksys.vivekpk.R;

public class NotificationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    Spinner spinner_select_value;

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
                startActivity(new Intent(NotificationActivity.this, HomeActivity.class));
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
