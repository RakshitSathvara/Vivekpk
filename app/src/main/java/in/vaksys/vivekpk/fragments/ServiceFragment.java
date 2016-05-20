package in.vaksys.vivekpk.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.vaksys.vivekpk.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {

    private TextView tvDate;

    private DatePickerDialog fromDatePickerDialog;

    private SimpleDateFormat dateFormatter;
    private String SelectedDate;
    public static final String TAG = "DATE";
    private LinearLayout linearVehicle, linearAddVehicle, linearServiceDueDate;
    private Button btn_addVehicle, btn_setAlert;


    public ServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_service, container, false);

        /*multiStateToggleButton = (MultiStateToggleButton) rootView.findViewById(R.id.mstb_servicevehicleChoice);
        multiStateToggleButton.enableMultipleChoice(false);
        multiStateToggleButton.setValue(0);
        //multiStateToggleButton.setColorRes(R.color.cardview_dark_background, R.color.cardview_dark_background);

        multiStateToggleButton.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                Log.e("MSTB", "onValueChanged: " + value);
                switch (value) {
                    case 0:
                        Toast.makeText(getActivity(), "Car Selected..", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "Bike Selected..", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getActivity(), "Please S" +
                                "elect any..", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });*/

        tvDate = (TextView) rootView.findViewById(R.id.tv_date);

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

        linearVehicle = (LinearLayout) rootView.findViewById(R.id.linearVehicleDetails);
        linearServiceDueDate = (LinearLayout) rootView.findViewById(R.id.linearServiceDueDate);
        linearAddVehicle = (LinearLayout) rootView.findViewById(R.id.linearAddVehicle);

        btn_addVehicle = (Button) rootView.findViewById(R.id.btn_addVehicle);
        btn_setAlert = (Button) rootView.findViewById(R.id.btn_setAlert);

        btn_addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearVehicle.setVisibility(View.VISIBLE);
                linearAddVehicle.setVisibility(View.GONE);
                linearServiceDueDate.setVisibility(View.VISIBLE);
            }
        });

        btn_setAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.remind_me);

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
        fromDatePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SelectedDate = dateFormatter.format(newDate.getTime());
                tvDate.setText(SelectedDate);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

}
