package in.vaksys.vivekpk.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import in.vaksys.vivekpk.R;


public class BikeFragment extends Fragment {

    Spinner spSelectmake, spCarModel;

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

        spSelectmake = (Spinner) rootView.findViewById(R.id.sp_selectMake);
        spCarModel = (Spinner) rootView.findViewById(R.id.sp_selectModel);

        List<String> selectBrand = new ArrayList<String>();
        selectBrand.add("Select Brand");
        selectBrand.add("Audi");
        selectBrand.add("BMW");
        selectBrand.add("Jaguar");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, selectBrand);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spSelectmake.setAdapter(dataAdapter1);
        spSelectmake.setSelection(0);

        List<String> carModel = new ArrayList<String>();
        carModel.add("Select Model");
        carModel.add("Audi A7");
        carModel.add("BMW");
        carModel.add("Jaguar");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, carModel);

        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spCarModel.setAdapter(dataAdapter2);
        spCarModel.setSelection(0);

        return rootView;
    }

}
