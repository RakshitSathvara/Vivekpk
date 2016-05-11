package in.vaksys.vivekpk.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.adapter.ViewPagerAdapter;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.extras.SpinnerCallback;

/**
 * Created by Harsh on 03-05-2016.
 */
public class MainTabFragment extends Fragment {

    private static final String TAG = "intab";
    //    @BindView(R.id.tabs1)
    TabLayout tabLayout;
    //    @BindView(R.id.viewpager1)
    ViewPager viewPager;
    int status = 0;
    int a = 0;

    /**
     * Create a new instance of the fragment
     */
    public static MainTabFragment newInstance(int index) {
        MainTabFragment fragment = new MainTabFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_tabs, container, false);
//        ButterKnife.bind(this, view);
//        a = 1;
        tabLayout = (TabLayout) view.findViewById(R.id.tabs1);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager1);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        /*status = MyApplication.getInstance().getValue();
        if (status == 0) {
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(1);
        }*/
        return view;
       /* if (getArguments().getInt("index", -1) == 0) {
            View view = inflater.inflate(R.layout.fragment_main_tabs, container, false);
            initDemoSettings(view);
            return view;
        } else {
            View view = inflater.inflate(R.layout.fragment_demo_list, container, false);
            initDemoList(view);
            return view;
        }*/
    }

    public void onRefresh() {
        Log.e(TAG, "onRefresh: " + a);
        if (a == 1) {
            Log.e(TAG, "onRefresh: ");
            viewPager.setCurrentItem(0);
        }
    }

    public void onRefresh1() {
        Log.e(TAG, "onRefresh1: " + a);
        if (a == 1) {
            viewPager.setCurrentItem(1);
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new CarFragment(), "Car");
        adapter.addFragment(new BikeFragment(), "Bike");
        viewPager.setAdapter(adapter);
        a++;

    }

    /*@Override
    public void onSpinnerCallBack() {
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences("harsh", Context.MODE_PRIVATE);
        int status;
        status = sharedPreferences.getInt("type", 0);
        if (status == 0) {
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(1);
        }
    }*/
}