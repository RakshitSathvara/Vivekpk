package in.vaksys.vivekpk.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.fragments.MainTabFragment;

public class HomeActivity extends AppCompatActivity {

    private MainTabFragment currentFragment;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private FragmentManager fragmentManager;
    private AHBottomNavigation bottomNavigation;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentManager = getSupportFragmentManager();

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
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.reminder, R.drawable.ic_action_verify, R.color.color_tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.documents, R.drawable.ic_action_verify, R.color.color_tab_1);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.emergency_contact, R.drawable.ic_action_verify, R.color.color_tab_1);

        bottomNavigationItems.add(item);
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
//        bottomNavigation.setForceTitlesDisplay(true);
//        bottomNavigation.setColored(true);

        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setAccentColor(Color.parseColor("#FF000000"));
        bottomNavigation.setInactiveColor(Color.parseColor("#FFFFFF"));
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));

//        bottomNavigation.setCurrentItem(3);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                Toast.makeText(HomeActivity.this, " " + position, Toast.LENGTH_SHORT).show();
                if (position == 1) {
                    bottomNavigation.setNotification(0, 1);

                }

                if (!wasSelected) {
                    currentFragment = MainTabFragment.newInstance(position);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, currentFragment)
                            .commit();
                } else if (position > 0) {
//                    currentFragment.refresh();
                }
            }
        });

        currentFragment = MainTabFragment.newInstance(0);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, currentFragment)
                .commit();

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
        return super.onCreateOptionsMenu(menu);

    }
}
