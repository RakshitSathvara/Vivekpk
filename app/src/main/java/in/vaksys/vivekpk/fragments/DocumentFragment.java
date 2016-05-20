package in.vaksys.vivekpk.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.extras.PercentLinearLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentFragment extends Fragment {

    private Button btnRegisterVehicle, btnContinue;
    private LinearLayout linearAddDrivingLicense, linearYourDrivingLicense, linearRegisterVehicle,
            linearVehicleDetails, linearVehicleDetailsListRaw;
    private PercentLinearLayout percentEnterCarDetails;

    private ImageView img_addGallery;

    private Spinner spBrand, spVehicleModel;

    public static DocumentFragment newInstance(int index) {
        DocumentFragment fragment = new DocumentFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_document, container, false);

        btnRegisterVehicle = (Button) rootView.findViewById(R.id.btn_registerVehicle);

        spBrand = (Spinner) rootView.findViewById(R.id.sp_selectMake);

        List<String> CompanyName = new ArrayList<String>();
        CompanyName.add("Select Brand");
        CompanyName.add("BMD");
        CompanyName.add("Audi");
        CompanyName.add("Jaguar");
        CompanyName.add("Maruti Suzuki");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, CompanyName);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spBrand.setAdapter(dataAdapter);
        spBrand.setSelection(0);

        spVehicleModel = (Spinner) rootView.findViewById(R.id.sp_selectModel);

        List<String> CompanyName1 = new ArrayList<String>();
        CompanyName1.add("Select Model");
        CompanyName1.add("BMD");
        CompanyName1.add("Audi R8");
        CompanyName1.add("Jaguar");
        CompanyName1.add("Swift");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, CompanyName1);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spVehicleModel.setAdapter(dataAdapter);
        spVehicleModel.setSelection(0);

        btnContinue = (Button) rootView.findViewById(R.id.btn_continue);

        img_addGallery = (ImageView) rootView.findViewById(R.id.img_addGallery);

        linearAddDrivingLicense = (LinearLayout) rootView.findViewById(R.id.linearAddDrivingLicense);
        linearYourDrivingLicense = (LinearLayout) rootView.findViewById(R.id.linearYourDrivingLicense);
        linearRegisterVehicle = (LinearLayout) rootView.findViewById(R.id.linearRegisterVehicle);
        linearVehicleDetails = (LinearLayout) rootView.findViewById(R.id.linearVehicleDetails);
        linearVehicleDetailsListRaw = (LinearLayout) rootView.findViewById(R.id.linearVehicleDeatilsListRaw);

        percentEnterCarDetails = (PercentLinearLayout) rootView.findViewById(R.id.percentEnterCarDetails11);


        btnRegisterVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearAddDrivingLicense.setVisibility(View.GONE);
                linearYourDrivingLicense.setVisibility(View.GONE);
                linearRegisterVehicle.setVisibility(View.GONE);
                linearVehicleDetails.setVisibility(View.GONE);
                linearVehicleDetailsListRaw.setVisibility(View.GONE);

                percentEnterCarDetails.setVisibility(View.VISIBLE);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearAddDrivingLicense.setVisibility(View.VISIBLE);
                linearYourDrivingLicense.setVisibility(View.GONE);
                linearRegisterVehicle.setVisibility(View.GONE);
                linearVehicleDetails.setVisibility(View.VISIBLE);
                linearVehicleDetailsListRaw.setVisibility(View.GONE);

                percentEnterCarDetails.setVisibility(View.GONE);
            }
        });

        img_addGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearAddDrivingLicense.setVisibility(View.GONE);
                linearYourDrivingLicense.setVisibility(View.VISIBLE);
                linearRegisterVehicle.setVisibility(View.GONE);
                linearVehicleDetails.setVisibility(View.GONE);
                linearVehicleDetailsListRaw.setVisibility(View.VISIBLE);

                percentEnterCarDetails.setVisibility(View.GONE);
            }
        });
        return rootView;
    }

}
