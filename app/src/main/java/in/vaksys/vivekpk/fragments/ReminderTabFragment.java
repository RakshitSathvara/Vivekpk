package in.vaksys.vivekpk.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.adapter.ViewPagerAdapter;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.model.Message;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReminderTabFragment extends Fragment {
    //    @BindView(R.id.tabs1)
    TabLayout tabLayout;
    //    @BindView(R.id.viewpager1)
    ViewPager viewPager;
    String spinnnerValue;
    private int[] tabIcons = {
            R.drawable.insurance_select,
            R.drawable.emission_nav,
            R.drawable.services_nav
    };
    private EventBus bus = EventBus.getDefault();

    public static ReminderTabFragment newInstance(int index) {
        ReminderTabFragment fragment = new ReminderTabFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        bus.register(this);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminder_tab, container, false);

//        spinnnerValue = getArguments().getString("item");


//        MyApplication.getInstance().showLog("value spinner", spinnnerValue);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs2);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager2);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new InsuranceFragment(), "Insurance");
        adapter.addFragment(new EmissionFragment(), "Emission");
        adapter.addFragment(new ServiceFragment(), "Services");
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    Log.e("Positon fregment 1", spinnnerValue);
                    bus.post(new Message(spinnnerValue));
                }
                if (position == 1) {
                    Log.e("Positon fregment 2", spinnnerValue);
                    bus.post(new Message(spinnnerValue));

                }

                if (position == 2) {
                    Log.e("Positon fregment 3", spinnnerValue);
                    bus.post(new Message(spinnnerValue));

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.custom_tab, null);
        tabOne.setText("Insurance");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.insurance, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);


        TextView tabTwo = (TextView) LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Emission");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.emission, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.custom_tab, null);
        tabThree.setText("Services");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.service, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }


    @Subscribe
    public void onEvent(Message messageCar) {
        Log.e("car datata", messageCar.getMsg());
        Toast.makeText(getActivity(), messageCar.getMsg(), Toast.LENGTH_SHORT).show();
        spinnnerValue = messageCar.getMsg();

    }


//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        bus.unregister(this);
//    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        bus.unregister(this);
//    }

//    @Override
//    public void onStart(){
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        bus.register(this);
//    }


//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
//    }
//
////    @Override
////    public void onResume() {
////        super.onResume();
////        EventBus.getDefault().register(this);
////    }
//
//    @Override
//    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
//        super.onDestroy();
//    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStop() {
//        EventBus.getDefault().unregister(this);
//        super.onStop();
//    }
}
