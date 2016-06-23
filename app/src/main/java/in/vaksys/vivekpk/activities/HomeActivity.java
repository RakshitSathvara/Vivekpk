package in.vaksys.vivekpk.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.extras.PreferenceHelper;
import in.vaksys.vivekpk.extras.SpinnerCallback;
import in.vaksys.vivekpk.fragments.BikeFragment;
import in.vaksys.vivekpk.fragments.CarFragment;
import in.vaksys.vivekpk.fragments.DocumentFragment;
import in.vaksys.vivekpk.fragments.EmergencyFragment;
import in.vaksys.vivekpk.fragments.MainTabFragment;
import in.vaksys.vivekpk.fragments.ReminderTabFragment;
import in.vaksys.vivekpk.model.Message;


public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "in activity";
    ImageView img, notification;
    LinearLayout linearHelpCenter, linearMenuMyaccount, linearMenuNews, linearMenuinvitefriend, linearRateApp, linearRefferalCode;
    int i = 0;
    MenuItem menuItem;
    Menu menu;
    TextView toolName;
    ImageView imageToolBar;
    PreferenceHelper prefs;
    private MainTabFragment currentFragment;
    private DocumentFragment documentFragment;
    private ReminderTabFragment reminderTabFragment;
    private EmergencyFragment emergencyFragment;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private FragmentManager fragmentManager;
    private AHBottomNavigation bottomNavigation;
    private Toolbar toolbar;
    private Spinner spinner_select_value;
    private CarFragment carFragment;
    private BikeFragment bikeFragment;
    private SpinnerCallback spinnerCallback;
    private EventBus bus = EventBus.getDefault();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentManager = getSupportFragmentManager();
        //    menuItem = menu.findItem(R.id.searchToolbar);
      /*  try {
            this.spinnerCallback = ((SpinnerCallback) HomeActivity.this);
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage());
        }*/


        prefs = new PreferenceHelper(HomeActivity.this);
        prefs.setConfigure(true);

        toolName = (TextView) findViewById(R.id.toolName);
        imageToolBar = (ImageView) findViewById(R.id.imageToolBar);
        ArrayList<String> list = new ArrayList<String>();

        list.add("Car");
        list.add("Bike");

        spinner_select_value = (Spinner) findViewById(R.id.spinner_select_value);

        final ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_select_value.setAdapter(spinAdapter);
        spinner_select_value.setSelection(0);
        spinner_select_value.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                item = adapter.getItemAtPosition(position).toString().toLowerCase();

                // Showing selected spinner item
///// TODO: 09/06/2016 add this line
                MyApplication.getInstance().showLog("lower case check",item.toLowerCase());

//                Toast.makeText(getApplicationContext(), "Selected  : " + item.toLowerCase(),
//                        Toast.LENGTH_LONG).show();
                SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences("harsh", Context.MODE_PRIVATE);

                SharedPreferences.Editor edit = sharedPreferences.edit();

                if (position == 0) {


                    bus.post(new Message(item));



                   /* edit.putInt("type", 0);
                    edit.apply();
                    spinnerCallback.onSpinnerCallBack();
//                    MyApplication.getInstance().setValue(0);
                    Fragment fm =fragmentManager.findFragmentByTag("harsh");
*/


                    Log.e(TAG, "onItemSelected: called");
                }
                if (position == 1) {

                    bus.post(new Message(item));
                   /* edit.putInt("type", 1);
                    edit.apply();
                    spinnerCallback.onSpinnerCallBack();

//                    MyApplication.getInstance().setValue(1);
                    MainTabFragment.newInstance(0).onRefresh1();
                    Log.e(TAG, "onItemSelected: called");*/


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        /*notification = (ImageView) findViewById(R.id.notification11);
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
        getMyData();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public String getMyData() {
        return item;
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
//        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#F6B332"));
        final AHBottomNavigationItem item0 = new AHBottomNavigationItem(R.string.home, R.drawable.ic_action_home, R.color.color_tab_1);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.reminder, R.drawable.reminder_can_we_help, R.color.color_tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.documents, R.drawable.settings_documents, R.color.color_tab_1);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.emergency_contact, R.drawable.emergency_contac_activet_tab, R.color.color_tab_1);

        bottomNavigationItems.add(item0);
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
//                Toast.makeText(HomeActivity.this, " " + position + " " + wasSelected, Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, currentFragment)
                            .commit();
                    toolbar.setLogo(R.drawable.logo_nav_landing);
                    //toolbar.setTitle("");
                    toolName.setText("");
                    spinner_select_value.setVisibility(View.GONE);
                    //  i = 0;
                    //menuItem.setVisible(false);
                } else if (position == 1) {
//                    bottomNavigation.setNotification(0, 1);

                    //   MyApplication.getInstance().showLog("spinnnner valewe",item);
                    spinner_select_value.setVisibility(View.VISIBLE);

                    ///  Bundle bundle = new Bundle();
                    //  String myMessage = item;
                    int v = spinner_select_value.getVisibility();
                    MyApplication.getInstance().showLog("spinnnner visibility :::::", "" + v);

                    if (v == 0) {
                        // Invisible

                        bus.post(new Message("car"));

                    } else {
                        //visible

                        bus.post(new Message(item));
                    }


//                    if (spinner_select_value.isActivated()) {
//                        MyApplication.getInstance().showLog("spinnnner valewe :::::", myMessage);
//                        bundle.putString("message", myMessage);
//                    } else {
//                        MyApplication.getInstance().showLog("spinnnner valewe", "car");
//                        bundle.putString("message", "car");
//                    }
                    //  reminderTabFragment.setArguments(bundle);


                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, reminderTabFragment)
                            .commit();
                    toolbar.setLogo(R.drawable.click_logo);
                    //toolbar.setTitle("Reminder");
                    toolName.setText("Reminder");
                    imageToolBar.setVisibility(View.GONE);

                    //  i = 1;
                    //menuItem.setVisible(false);


                } else if (position == 2) {


                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, documentFragment)
                            .commit();
                    toolbar.setLogo(R.drawable.click_logo);
                    //toolbar.setTitle("Documents");
                    toolName.setText("Documents");
                    imageToolBar.setVisibility(View.GONE);
                    spinner_select_value.setVisibility(View.VISIBLE);


                    //    i = 1;
                } else if (position == 3) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, emergencyFragment)
                            .commit();
                    toolbar.setLogo(R.drawable.click_logo);
                    //toolbar.setTitle("Emergency");
                    toolName.setText("Emergency");
                    imageToolBar.setVisibility(View.GONE);
                    spinner_select_value.setVisibility(View.VISIBLE);
                    //   i = 1;
                } else if (!wasSelected) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, currentFragment)
                            .commit();
                    //  i = 1;

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
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
                        startActivity(new Intent(HomeActivity.this, WebViewActivity.class));
                    }
                });

                LinearLayout linearLayout1 = (LinearLayout) dialog.findViewById(R.id.linear_menu_invitefriend);
                linearLayout1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.\n https://play.google.com/store/apps/details?id=com.whatsapp&hl=en");
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    }
                });
                linearMenuMyaccount = (LinearLayout) dialog.findViewById(R.id.linear_menu_myaccount);
                linearMenuMyaccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(new Intent(HomeActivity.this, MyAccountActivity.class));
                        finish();
                    }
                });

                linearMenuNews = (LinearLayout) dialog.findViewById(R.id.linear_menu_news);
                linearMenuNews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(new Intent(HomeActivity.this, NewsActivity.class));
                    }
                });


                linearRefferalCode = (LinearLayout) dialog.findViewById(R.id.linear_menu_referralcode);
                linearRefferalCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(new Intent(HomeActivity.this, RefferalCodeActivity.class));
                    }
                });
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                return true;

            case R.id.searchToolbar:
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
                return true;

            case R.id.notificationToolbar:
                startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        MyApplication.getInstance().ExitSnackBar(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
