package in.vaksys.vivekpk.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.adapter.ServiceDetailsRecyclerViewAdapter;
import in.vaksys.vivekpk.adapter.ServiceRecyclerViewAdapter;
import in.vaksys.vivekpk.dbPojo.VehicleDetails;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.model.Message;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {


    private static final String TAG = "ServiceFragment";
    private Button btn_addVehicle;

    ServiceRecyclerViewAdapter serviceRecyclerViewAdapter;
    ServiceDetailsRecyclerViewAdapter detailsRecyclerViewAdapter;
    RecyclerView ServiceRecyclerview;
    RecyclerView ServiceDetailsRecyclerview;
    RealmResults<VehicleDetails> results;
    RealmResults<VehicleDetails> detailsesResults;
    private MyApplication myApplication;
    private Realm realm;

    public ServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_service, container, false);


        ServiceRecyclerview = (RecyclerView) rootView.findViewById(R.id.ServiceEditRecyclerView);
        ServiceDetailsRecyclerview = (RecyclerView) rootView.findViewById(R.id.ServiceDetailsRecyclerView);
        myApplication = MyApplication.getInstance();
        realm = Realm.getDefaultInstance();
        btn_addVehicle = (Button) rootView.findViewById(R.id.btn_addVehicle);

        SetInsurance();
        SetInsuranceDetails();

        return rootView;
    }


    private void SetInsuranceDetails() {
        ServiceDetailsRecyclerview.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ServiceDetailsRecyclerview.setLayoutManager(manager);
        detailsesResults = realm.where(VehicleDetails.class).notEqualTo("ServiceExpireDate", "").findAll();
        myApplication.showLog(TAG, "inside details" + detailsesResults.size());
        if (results.size() > -1) {
            detailsRecyclerViewAdapter = new ServiceDetailsRecyclerViewAdapter(getActivity(), detailsesResults);
            ServiceDetailsRecyclerview.setHasFixedSize(true);
            ServiceDetailsRecyclerview.setItemAnimator(new DefaultItemAnimator());
            ServiceDetailsRecyclerview.setNestedScrollingEnabled(false);
            ServiceDetailsRecyclerview.setAdapter(detailsRecyclerViewAdapter);
        } else {
            myApplication.showLog(TAG, "details");
        }
    }

    private void SetInsurance() {
        myApplication.showLog(TAG, "innerview");

        ServiceRecyclerview.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ServiceRecyclerview.setLayoutManager(manager);
        results = realm.where(VehicleDetails.class).equalTo("ServiceExpireDate", "").findAll();
        myApplication.showLog(TAG, "inside viwe" + results.size());
        if (results.size() > -1) {
            serviceRecyclerViewAdapter = new ServiceRecyclerViewAdapter(getActivity(), results);
            ServiceRecyclerview.setAdapter(serviceRecyclerViewAdapter);
        } else {
            myApplication.showLog(TAG, "innerview222222");
        }
    }

    @Subscribe
    public void onEvent(Message messageCar) {
        Log.e("car datata", messageCar.getMsg());
        Toast.makeText(getActivity(), messageCar.getMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
