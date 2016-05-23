package in.vaksys.vivekpk.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.adapter.mySpinnerAdapterBrand;
import in.vaksys.vivekpk.adapter.mySpinnerAdapterModel;
import in.vaksys.vivekpk.dbPojo.VehicleModels;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.pojo.ModelPojo;
import io.realm.Realm;
import io.realm.RealmResults;


public class BikeFragment extends Fragment {

    Spinner spSelectmake, spCarModel;
    private static final String TAG = "BikeFragment";
    private Gson gson;
    ModelPojo modelPojo;
    private String modelSpinnItem;
    private Realm realm;

    MyApplication myApplication;


    public static BikeFragment newInstance(int index) {
        BikeFragment fragment = new BikeFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bike, container, false);
        myApplication = MyApplication.getInstance();

        spSelectmake = (Spinner) rootView.findViewById(R.id.sp_selectMake);
        spCarModel = (Spinner) rootView.findViewById(R.id.sp_selectModel);


        realm = Realm.getDefaultInstance();
        MyApplication.getInstance().createDialog(getActivity(), false);

       /* List<String> selectBrand = new ArrayList<String>();
        selectBrand.add("Select Brand");
        selectBrand.add("Audi");
        selectBrand.add("BMW");
        selectBrand.add("Jaguar");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, selectBrand);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spSelectmake.setAdapter(dataAdapter1);
        spSelectmake.setSelection(0);*/

//        List<String> carModel = new ArrayList<String>();
//        carModel.add("Select Model");
//        carModel.add("Audi A7");
//        carModel.add("BMW");
//        carModel.add("Jaguar");
//        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, carModel);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spCarModel.setAdapter(dataAdapter2);
//        spCarModel.setSelection(0);
        LodingBrand();
        LodingModel();


        return rootView;
    }

    private void LodingModel() {

        // // TODO: 5/20/2016  add vishal
        RealmResults<VehicleModels> results = realm.where(VehicleModels.class).findAll();

        mySpinnerAdapterModel mySpinnerAdapterCity = new mySpinnerAdapterModel(getActivity(), results, "bike");
        spCarModel.setAdapter(mySpinnerAdapterCity);

        spCarModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modelSpinnItem = ((TextView) getActivity().findViewById(R.id.rowText)).getText().toString();
                String myid = ((TextView) getActivity().findViewById(R.id.rowid)).getText().toString();
                Toast.makeText(getActivity(), "You have selected " + modelSpinnItem + " " + myid, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "You have selected Nothing ..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LodingBrand() {

        RealmResults<VehicleModels> results = realm.where(VehicleModels.class).findAll();

        mySpinnerAdapterBrand mySpinnerAdapterCity = new mySpinnerAdapterBrand(getActivity(), results, "bike");
        spSelectmake.setAdapter(mySpinnerAdapterCity);

        spSelectmake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modelSpinnItem = ((TextView) getActivity().findViewById(R.id.rowText)).getText().toString();
                String myid = ((TextView) getActivity().findViewById(R.id.rowid)).getText().toString();
                Toast.makeText(getActivity(), "You have selected " + modelSpinnItem + " " + myid, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "You have selected Nothing ..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
