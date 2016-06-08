package in.vaksys.vivekpk.fragments;


import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.adapter.EmissionDetailsRecyclerViewAdapter;
import in.vaksys.vivekpk.adapter.EmisssionRecyclerViewAdapter;
import in.vaksys.vivekpk.dbPojo.VehicleDetails;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.model.Message;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmissionFragment extends Fragment {

    private LinearLayout linearVehicle, linearAddVehicle, linearExpiryDate;
    private Button btn_addVehicle, btn_setAlert;
    private TextView tvDate;

    private DatePickerDialog fromDatePickerDialog;

    private SimpleDateFormat dateFormatter;
    private String SelectedDate;
    public static final String TAG = "DATE";

    EmissionDetailsRecyclerViewAdapter detailsRecyclerViewAdapter;
    EmisssionRecyclerViewAdapter emisssionAdapter;
    RecyclerView EmissionRecyclerview;
    RecyclerView EmissionDetailsRecyclerview;
    RealmResults<VehicleDetails> results;
    RealmResults<VehicleDetails> detailsesResults;
    private MyApplication myApplication;
    private Realm realm;


    public EmissionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_emission, container, false);

        myApplication = MyApplication.getInstance();
        realm = Realm.getDefaultInstance();


        EmissionRecyclerview = (RecyclerView) rootView.findViewById(R.id.EmissionEditRecyclerView);
        EmissionDetailsRecyclerview = (RecyclerView) rootView.findViewById(R.id.EmissionRecyclerView);
        SetInsurance();
        SetInsuranceDetails();
        /*tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectfromDate();
            }

            *//*@Override
            public boolean onTouch(View v, MotionEvent event) {
                SelectfromDate();
                return true;
            }
*//*
        });*/
        btn_addVehicle = (Button) rootView.findViewById(R.id.btn_addVehicle);
        btn_setAlert = (Button) rootView.findViewById(R.id.btn_expiry_date_setAlert);

       /* btn_addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearVehicle.setVisibility(View.VISIBLE);
                linearAddVehicle.setVisibility(View.GONE);
                linearExpiryDate.setVisibility(View.VISIBLE);
            }
        });

        btn_setAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.remind_me);

//                public void callbackMethod(final View view){
//                    Toast.makeText(this,((RadioButton) view).getText(), Toast.LENGTH_SHORT).show();
//                }

                //  callbackMethod(v);

                RadioGroup radioGroup;
                RadioButton sound, vibration, silent;
                radioGroup = (RadioGroup) dialog.findViewById(R.id.myRadioGroup);

                sound = (RadioButton) dialog.findViewById(R.id.radio1);
                vibration = (RadioButton) dialog.findViewById(R.id.radio2);
                silent = (RadioButton) dialog.findViewById(R.id.radio3);

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        if (checkedId == R.id.radio1) {
                            Toast.makeText(getActivity(), "radio1",
                                    Toast.LENGTH_SHORT).show();
                        } else if (checkedId == R.id.radio2) {
                            Toast.makeText(getActivity(), "radio2",
                                    Toast.LENGTH_SHORT).show();
                        } else if (checkedId == R.id.radio3) {
                            Toast.makeText(getActivity(), "radio3",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                Button btn_done = (Button) dialog.findViewById(R.id.btn_done);
                btn_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Reminder Set", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
            }
        });
*/
        return rootView;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    Calendar c = Calendar.getInstance();

    private void SelectfromDate() {
        c.add(Calendar.DAY_OF_MONTH, 26);
        String formattedDate = sdf.format(c.getTime()); // current date
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
    }

    private void SetInsuranceDetails() {
        EmissionDetailsRecyclerview.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        EmissionDetailsRecyclerview.setLayoutManager(manager);
        detailsesResults = realm.where(VehicleDetails.class).notEqualTo("PollutionExpireDate", "").findAll();
        myApplication.showLog(TAG, "inside details" + detailsesResults.size());
        if (results.size() > -1) {
            detailsRecyclerViewAdapter = new EmissionDetailsRecyclerViewAdapter(getActivity(), detailsesResults);
            EmissionDetailsRecyclerview.setHasFixedSize(true);
            EmissionDetailsRecyclerview.setItemAnimator(new DefaultItemAnimator());
            EmissionDetailsRecyclerview.setNestedScrollingEnabled(false);
            EmissionDetailsRecyclerview.setAdapter(detailsRecyclerViewAdapter);
        } else {
            myApplication.showLog(TAG, "details");
        }
    }

    private void SetInsurance() {
        myApplication.showLog(TAG, "innerview");

        EmissionRecyclerview.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        EmissionRecyclerview.setLayoutManager(manager);
        results = realm.where(VehicleDetails.class).equalTo("PollutionExpireDate", "").findAll();
        myApplication.showLog(TAG, "inside viwe" + results.size());
        if (results.size() > -1) {
            emisssionAdapter = new EmisssionRecyclerViewAdapter(getActivity(), results);
            EmissionRecyclerview.setHasFixedSize(true);
            EmissionRecyclerview.setItemAnimator(new DefaultItemAnimator());
            EmissionRecyclerview.setNestedScrollingEnabled(false);
            EmissionRecyclerview.setAdapter(emisssionAdapter);
        } else {
            myApplication.showLog(TAG, "innerview222222");
        }
    }


    @Subscribe
    public void onEvent(Message messageCar) {
        Log.e("car datata", messageCar.getMsg());
        Toast.makeText(getActivity(), messageCar.getMsg(), Toast.LENGTH_SHORT).show();
    }


//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        bus.unregister(this);
//    }

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

//    @Override
//    public void onResume() {
//        super.onResume();
//        bus.register(this);
//    }


    public void callbackMethod(View v) {
//        Toast.makeText(getActivity(),((RadioButton) v).getText(), Toast.LENGTH_SHORT).show();

        Log.e("fdhgyud", "edfhuf");
    }
}
