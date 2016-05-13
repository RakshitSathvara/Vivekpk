package in.vaksys.vivekpk.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.fragments.DocumentFragment;
import in.vaksys.vivekpk.fragments.EmergencyFragment;
import in.vaksys.vivekpk.fragments.MainTabFragment;
import in.vaksys.vivekpk.fragments.ReminderTabFragment;


public class HomeActivity extends AppCompatActivity {

    private MainTabFragment currentFragment;
    private DocumentFragment documentFragment;
    private ReminderTabFragment reminderTabFragment;
    private EmergencyFragment emergencyFragment;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private FragmentManager fragmentManager;
    private AHBottomNavigation bottomNavigation;
    private Toolbar toolbar;
    ImageView img, notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentManager = getSupportFragmentManager();

       /* notification = (ImageView) findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
            }
        });

        img = (ImageView) findViewById(R.id.img_search);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
            }
        });*/

        initUI();
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
//        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#F6B332"));
        AHBottomNavigationItem item = new AHBottomNavigationItem(R.string.home, R.drawable.ic_action_verify, R.color.color_tab_1);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.reminder, R.drawable.reminder_can_we_help, R.color.color_tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.documents, R.drawable.settings_documents, R.color.color_tab_1);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.emergency_contact, R.drawable.emergency_contac_activet_tab, R.color.color_tab_1);

        bottomNavigationItems.add(item);
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigation.setForceTitlesDisplay(true);
//        bottomNavigation.setColored(true);

        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setAccentColor(Color.parseColor("#FFFFFF"));
        bottomNavigation.setInactiveColor(Color.parseColor("#FF000000"));
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));

        bottomNavigation.setCurrentItem(0);
        currentFragment = MainTabFragment.newInstance(0);
        reminderTabFragment = ReminderTabFragment.newInstance(1);
        documentFragment = documentFragment.newInstance(2);
        emergencyFragment = EmergencyFragment.newInstance(3);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, currentFragment)
                .commit();
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                Toast.makeText(HomeActivity.this, " " + position + " " + wasSelected, Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, currentFragment)
                            .commit();
                }
                else if (position == 1) {
//                    bottomNavigation.setNotification(0, 1);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, reminderTabFragment)
                            .commit();
                }
                else if (position == 2) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, documentFragment)
                            .commit();
                }
                else if (position == 3) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, emergencyFragment)
                            .commit();
                }
                else if (!wasSelected) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, currentFragment)
                            .commit();
                }/*  else if (position > 0) {
//                    currentFragment.refresh();
                }*/
            }
        });



       /* final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomNavigation.setNotification(16, 1);
                Snackbar.make(bottomNavigation, "Snackbar with bottom navigation",
                        Snackbar.LENGTH_SHORT).show();
            }
        }, 3000);*/
    }

    /**
     * Update the bottom navigation colored param
     */
    public void updateBottomNavigationColor(boolean isColored) {
        bottomNavigation.setColored(isColored);
    }

    /**
     * Return if the bottom navigation is colored
     */
    public boolean isBottomNavigationColored() {
        return bottomNavigation.isColored();
    }

    public int getBottomNavigationNbItems() {
        return bottomNavigation.getItemsCount();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    *//**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     *//*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
*/
}
