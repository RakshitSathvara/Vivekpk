package in.vaksys.vivekpk.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.List;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.extras.PreferenceHelper;
import in.vaksys.vivekpk.fragments.SigninFragment;
import in.vaksys.vivekpk.fragments.SignupFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    PreferenceHelper prefs;
    private LinearLayout linearLayout1, linearLayout2, linearLayout3;
    private CheckBox checkBox;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }*/

        setContentView(R.layout.activity_main);

        prefs = new PreferenceHelper(MainActivity.this);

        if (prefs.isConfigure()) {

            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
            finish();

        }

        /*linearLayout1 = (LinearLayout) findViewById(R.id.linerFiveDay);
        linearLayout2 = (LinearLayout) findViewById(R.id.linerOneFiveDay);
        linearLayout3 = (LinearLayout) findViewById(R.id.linerTwoFiveDay);

        checkBox = (CheckBox) findViewById(R.id.checkbox1);

        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);*/


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Log.e(TAG, "onCreate: " + checkPlayServices());
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
//                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();

                new AlertDialog.Builder(this)
                        .setTitle("Google play Service not Available")
                        .setMessage("This app won't run without Google Play services, which are missing from your phone.")
                        .setCancelable(false)
                        .setPositiveButton("Done",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        finish();
                                    }
                                }
                        ).create().show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    /*@Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.linerFiveDay:
                checkBox.isChecked();
                break;


        }
    }*/

    private void setupViewPager(ViewPager viewPager) {

        adapter.addFragment(new SigninFragment(), "Sign In");
        adapter.addFragment(new SignupFragment(), "Sign Up");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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
