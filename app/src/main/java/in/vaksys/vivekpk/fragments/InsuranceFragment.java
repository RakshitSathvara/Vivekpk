package in.vaksys.vivekpk.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.model.Message;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsuranceFragment extends Fragment {

    private TextView tvDate;

    private DatePickerDialog fromDatePickerDialog;

    private SimpleDateFormat dateFormatter;
    private String SelectedDate;
    public static final String TAG = "DATE";

    private Button btn_addVehicle, btn_setAlert, btn_setAlertDetail;

    private Spinner spInsuranceCompany;
    private TextView setValue;

    private EventBus bus = EventBus.getDefault();
   // private MultiStateToggleButton multiStateToggleButton;

    private LinearLayout linearAddVehicle, linearVehicleDetails, linearInsurancePolicy, linearInsurancePolicyWithVehicle,
            linearInsuranceDetails;

    public InsuranceFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_insurance, container, false);


        setValue = (TextView) rootView.findViewById(R.id.txt_addvd);

        linearAddVehicle = (LinearLayout) rootView.findViewById(R.id.linearAddVehicle);
        linearVehicleDetails = (LinearLayout) rootView.findViewById(R.id.linearVehicleDetails);
        linearInsurancePolicy = (LinearLayout) rootView.findViewById(R.id.linearInsurancePolicy);
        linearInsurancePolicyWithVehicle = (LinearLayout) rootView.findViewById(R.id.linearInsurancePolicyWithVehicle);
        linearInsuranceDetails = (LinearLayout) rootView.findViewById(R.id.linearInsuranceDetails);

        tvDate = (TextView) rootView.findViewById(R.id.tv_date);


        spInsuranceCompany = (Spinner) rootView.findViewById(R.id.sp_insuranceCompany);

        List<String> insuranse = new ArrayList<String>();
        insuranse.add("Select Company");
        insuranse.add("Business Services");
        insuranse.add("Computers");
        insuranse.add("Education");
        insuranse.add("Personal");
        insuranse.add("Travel");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, insuranse);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spInsuranceCompany.setAdapter(dataAdapter);
        spInsuranceCompany.setSelection(0);

        setDateTimeField();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectfromDate();
            }

            /*@Override
            public boolean onTouch(View v, MotionEvent event) {
                SelectfromDate();
                return true;
            }
*/
        });

        btn_addVehicle = (Button) rootView.findViewById(R.id.btn_addVehicle);
        btn_setAlert = (Button) rootView.findViewById(R.id.btn_setAlert);
        btn_setAlertDetail = (Button) rootView.findViewById(R.id.btn_setAlertDetail);

        btn_addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearAddVehicle.setVisibility(View.GONE);
                linearVehicleDetails.setVisibility(View.VISIBLE);
                linearInsurancePolicy.setVisibility(View.VISIBLE);
                linearInsurancePolicyWithVehicle.setVisibility(View.GONE);
                linearInsuranceDetails.setVisibility(View.GONE);
            }
        });

        btn_setAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearAddVehicle.setVisibility(View.GONE);
                linearVehicleDetails.setVisibility(View.GONE);
                linearInsurancePolicy.setVisibility(View.GONE);
                linearInsurancePolicyWithVehicle.setVisibility(View.VISIBLE);
                linearInsuranceDetails.setVisibility(View.GONE);
            }
        });

        btn_setAlertDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.remind_me);

                Button btn_done = (Button) dialog.findViewById(R.id.btn_done);
                btn_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linearAddVehicle.setVisibility(View.GONE);
                        linearVehicleDetails.setVisibility(View.GONE);
                        linearInsurancePolicy.setVisibility(View.GONE);
                        linearInsurancePolicyWithVehicle.setVisibility(View.GONE);
                        linearInsuranceDetails.setVisibility(View.VISIBLE);

                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });


        return rootView;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    Calendar c = Calendar.getInstance();

    private void SelectfromDate() {
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
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SelectedDate = dateFormatter.format(newDate.getTime());
                tvDate.setText(SelectedDate);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    @Subscribe
    public void onEvent(Message messageCar){
        Log.e("car datata",messageCar.getMsg());
        Toast.makeText(getActivity(), messageCar.getMsg(), Toast.LENGTH_SHORT).show();
    }


//
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
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        bus.register(this);
//    }
}
