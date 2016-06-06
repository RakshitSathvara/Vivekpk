package in.vaksys.vivekpk.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.adapter.InsuranceDetailsRecyclerViewAdapter;
import in.vaksys.vivekpk.adapter.InsuranceRecyclerViewAdapter;
import in.vaksys.vivekpk.dbPojo.VehicleDetails;
import in.vaksys.vivekpk.extras.MyApplication;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsuranceFragment extends Fragment {

    public static final String TAG = "DATE";

    private Button btn_addVehicle;

    InsuranceRecyclerViewAdapter imageAdapter;
    InsuranceDetailsRecyclerViewAdapter detailsRecyclerViewAdapter;
    RecyclerView InsuranceRecyclerview;
    RecyclerView InsuranceDetailsRecyclerview;
    RealmResults<VehicleDetails> results;
    RealmResults<VehicleDetails> detailsesResults;
    private MyApplication myApplication;
    private Realm realm;


    public InsuranceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_insurance, container, false);

        btn_addVehicle = (Button) rootView.findViewById(R.id.btn_addVehicle);

        InsuranceRecyclerview = (RecyclerView) rootView.findViewById(R.id.InsuranceEditRecyclerView);
        InsuranceDetailsRecyclerview = (RecyclerView) rootView.findViewById(R.id.InsuranceDetailsRecyclerView);
        myApplication = MyApplication.getInstance();
        realm = Realm.getDefaultInstance();

        btn_addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        SetInsurance();
        SetInsuranceDetails();

        return rootView;
    }

    private void SetInsuranceDetails() {
        InsuranceDetailsRecyclerview.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        InsuranceDetailsRecyclerview.setLayoutManager(manager);
        detailsesResults = realm.where(VehicleDetails.class).notEqualTo("InsuranceCompany", "").findAll();
        myApplication.showLog(TAG, "inside details" + detailsesResults.size());
        if (results.size() > -1) {
            detailsRecyclerViewAdapter = new InsuranceDetailsRecyclerViewAdapter(getActivity(), detailsesResults);
            InsuranceDetailsRecyclerview.setAdapter(detailsRecyclerViewAdapter);
        } else {
            myApplication.showLog(TAG, "details");
        }
    }
 /*
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    Calendar c = Calendar.getInstance();

   private void SelectfromDate() {
        c.add(Calendar.DAY_OF_MONTH, 26);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        String formattedDate = sdf.format(c.getTime());
        Date d = null;
        try {
            d = sdf.parse(formattedDate);
        } catch (ParseException e) {
            Log.e(TAG, "SelectfromDate: " + e);
        }
        fromDatePickerDialog.getDatePicker().setMinDate(d.getTime());
        fromDatePickerDialog.show();
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SelectedDate = dateFormatter.format(newDate.getTime());
                tvDate.setText(SelectedDate);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }*/

    private void SetInsurance() {
        myApplication.showLog(TAG, "innerview");

        InsuranceRecyclerview.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        InsuranceRecyclerview.setLayoutManager(manager);
        results = realm.where(VehicleDetails.class).equalTo("InsuranceCompany", "").findAll();
        myApplication.showLog(TAG, "inside viwe" + results.size());
        if (results.size() > -1) {
            imageAdapter = new InsuranceRecyclerViewAdapter(getActivity(), results);
            InsuranceRecyclerview.setAdapter(imageAdapter);
        } else {
            myApplication.showLog(TAG, "innerview222222");
        }
    }
}
