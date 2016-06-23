package in.vaksys.vivekpk.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.dbPojo.InsuranceCompanies;
import in.vaksys.vivekpk.dbPojo.VehicleDetails;
import in.vaksys.vivekpk.dbPojo.VehicleModels;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.extras.VolleyHelper;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Harsh on 26-05-2016.
 */
public class InsuranceDetailsRecyclerViewAdapter extends RecyclerView.Adapter<InsuranceDetailsRecyclerViewAdapter.AdapterHolder> {

    private static final String BLANK = "";
    private final Context context;
    private final RealmResults<VehicleDetails> detailses;
    MyApplication myApplication;
    private static final String TAG = "InsuranceRecyclerViewAdapter";
    private Realm realm;
    private String modelSpinnItem;
    private String myid;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    SimpleDateFormat sdf;
    VehicleDetails details;
    AdapterHolder viewHolder;
    Dialog confirm;
    private Dialog confirm1;
    private int setId;
    private String NotificationDate;

    //    Calendar newDate;

    public InsuranceDetailsRecyclerViewAdapter(Context context, RealmResults<VehicleDetails> detailses) {

        this.myApplication = MyApplication.getInstance();
        realm = Realm.getDefaultInstance();
        this.context = context;
        this.detailses = detailses;
        myApplication.createDialog((Activity) context, false);

        sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.insurance_details, parent, false);
        viewHolder = new AdapterHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterHolder holder, int position) {
        details = detailses.get(position);
        final VehicleModels vehicleModels = realm.where(VehicleModels.class).equalTo("id", details.getVehicleModelID()).findFirst();
        final InsuranceCompanies insuranceCompanies = realm.where(InsuranceCompanies.class).equalTo("InsuranceId", Integer.parseInt(details.getInsuranceCompany())).findFirst();

        holder.VehicleIDHiddden.setText(String.valueOf(details.getVehicleId()));

        holder.VehicleNumber.setText(details.getVehicleNo());
        holder.VehicleBrand.setText(vehicleModels.getManufacturerName());
        holder.VehicleModel.setText(vehicleModels.getModel());
        holder.InsurancePolicyName.setText(insuranceCompanies.getInsuranceName());
        holder.InsurancePolicyExpiryDate.setText(details.getInsuranceExpireDate());

        detailses.addChangeListener(new RealmChangeListener<RealmResults<VehicleDetails>>() {
            @Override
            public void onChange(RealmResults<VehicleDetails> element) {
                notifyDataSetChanged();
            }
        });

        holder.EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm = new Dialog(context);
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                final View layout = inflater.inflate(R.layout.enter_car_details_edit, null, false);
                confirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
                confirm.setContentView(R.layout.insurance_policy);
                confirm.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button btnCancel = (Button) confirm.findViewById(R.id.btn_insurance_expiry_cancel);
                Button btnSetAlert = (Button) confirm.findViewById(R.id.btn_insurance_expiry_setAlert);

                final TextView DatePicker = (TextView) confirm.findViewById(R.id.tv_date);
                Spinner mSpinner = (Spinner) confirm.findViewById(R.id.sp_insuranceCompany_det);

                mSpinner.setSelection(vehicleModels.getId());
                setupInsuranceSpinner(mSpinner,insuranceCompanies.getInsuranceId());
//// TODO: 23/06/2016 insrance edit date value 
                DatePicker.setText(details.getInsuranceExpireDate());
                DatePicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetData(DatePicker);
                    }
                });


                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirm.dismiss();
                    }
                });
                btnSetAlert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setId = 5;
                        if ((Integer.parseInt(myid) == -1) || DatePicker.getText().toString().equalsIgnoreCase("Expiry Date")) {
                            Toast.makeText(context, "Please fill the data", Toast.LENGTH_SHORT).show();
                        } else {
                            confirm1 = new Dialog(context);
                            confirm1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            confirm1.setContentView(R.layout.remind_me);
                            confirm1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            RadioGroup radioGroup;
                            RadioButton sound, vibration, silent;
                            radioGroup = (RadioGroup) confirm1.findViewById(R.id.myRadioGroup);

                            sound = (RadioButton) confirm1.findViewById(R.id.radio1);
                            vibration = (RadioButton) confirm1.findViewById(R.id.radio2);
                            silent = (RadioButton) confirm1.findViewById(R.id.radio3);

                            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    if (checkedId == R.id.radio1) {
                                        setId = 1;
//                                        Toast.makeText(context, "radio1",
//                                                Toast.LENGTH_SHORT).show();
                                    } else if (checkedId == R.id.radio2) {
                                        setId = 2;
//                                        Toast.makeText(context, "radio2",
//                                                Toast.LENGTH_SHORT).show();
                                    } else if (checkedId == R.id.radio3) {
                                        setId = 3;
//                                        Toast.makeText(context, "radio3",
//                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            Button btn_done = (Button) confirm1.findViewById(R.id.btn_done);

                            btn_done.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (!(setId == 0)) {
                                        if (setId == 1) {
                                            String seldate = DatePicker.getText().toString();
                                            myApplication.showLog("set daet------------>", seldate);
                                            NotificationDate = myApplication.ChanageDate(DatePicker.getText().toString(), 5);
                                            myApplication.showLog("reminder date 5------------->", NotificationDate);
                                        } else if (setId == 2) {
                                            NotificationDate = myApplication.ChanageDate(DatePicker.getText().toString(), 15);
                                            myApplication.showLog("reminder date 15------------->", NotificationDate);

                                        } else if (setId == 3) {
                                            NotificationDate = myApplication.ChanageDate(DatePicker.getText().toString(), 25);
                                            myApplication.showLog("reminder date 25------------->", NotificationDate);
                                        }
                                    } else {
                                        Toast.makeText(context, "Please Select Reminder.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    myApplication.showLog("date------------->", DatePicker.getText().toString());

                                    myApplication.showLog("reminder date------------->", NotificationDate);

                                 //   notificationsend(NotificationDate);

//                                    String date = NotificationDate;
//                                    String[] items1 = date.split("-");
//                                    String date1 = (items1[0]);
//                                    String month = (items1[1]);
//                                    String year = (items1[2]);
//
//                                    String dateInString = NotificationDate;  // Start date
//                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//                                    Calendar c = Calendar.getInstance();
//                                    try {
//                                        c.setTime(sdf.parse(dateInString));
//                                    } catch (ParseException e) {
//                                        e.printStackTrace();
//                                    }
//                                    c.add(Calendar.DATE, -7);
//                                    sdf = new SimpleDateFormat("dd/MM/yyyy");
//                                    Date resultdate = new Date(c.getTimeInMillis());
//                                    dateInString = sdf.format(resultdate);
//                                    System.out.println("String date:"+dateInString);
//                                    myApplication.showLog("split date-->", String.valueOf(dateInString));

//                                    myApplication.showLog("split date-->", "" + date1 + month + year);
//                                    Calendar endar = Calendar.getInstance();
//                                    endar.add(, -5);
//
//                                    String dtStart = NotificationDate;
//                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                                    try {
//                                        Date datew = format.parse(dtStart);
//                                        endar.add(endar.d, 0);
//                                        System.out.println(datew);
//                                    } catch (ParseException e) {
//                                        // TODO Auto-generated catch block
//                                        e.printStackTrace();
//                                    }
//                                    endar.add(Calendar.DAY_OF_MONTH, 0);
//                                    endar.add(Calendar.YEAR, 0);
//                                    SimpleDateFormat d =  new SimpleDateFormat("dd-MM-yyyy", Locale.US);
//                                    String dddd = d.format(endar.getTime());
//                                    myApplication.showLog("split date -->", dddd);

//                                    Calendar c = Calendar.getInstance();
//                                    // Set the calendar NOTE: this will not change the hour, minute, second
//                                    c.add(Calendar.DATE, -5);


                                    AddUpdateReminder(holder.VehicleIDHiddden.getText().toString(),
                                            details.getVehicleModelID(), myid, DatePicker.getText().toString()
                                            , BLANK, BLANK, BLANK, NotificationDate,"Insurance Reminder");
                                    confirm1.dismiss();
                                }
                            });
                            confirm1.show();
                        }
                    }
                });
                confirm.show();
            }
        });

    }

    private void notificationsend(String notificationDate) {


    }

    private void SetData(final TextView date) {
        Date d = null;
        final Calendar newCalendar = Calendar.getInstance();

        newCalendar.add(Calendar.DAY_OF_MONTH, 0);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        String formattedDate = sdf.format(newCalendar.getTime());
        try {
            d = sdf.parse(formattedDate);
        } catch (ParseException e) {
//            Log.e(TAG, "SelectfromDate: " + e);
        }
        fromDatePickerDialog = new DatePickerDialog(context, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
                newCalendar.set(year, monthOfYear, dayOfMonth);
                //  newCalendar.add(Calendar.DATE, -5);
                date.setText(dateFormatter.format(newCalendar.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromDatePickerDialog.getDatePicker().setMinDate(d.getTime());

        fromDatePickerDialog.show();
    }

    private void setupInsuranceSpinner(Spinner mSpinner, int manufacturerName) {


        RealmResults<InsuranceCompanies> results = realm.where(InsuranceCompanies.class).findAll();

        mySpinnerAdapterInsurance mySpinnerAdapterCity = new mySpinnerAdapterInsurance(context, results, "bike");
        mSpinner.setAdapter(mySpinnerAdapterCity);
        mSpinner.setSelection(manufacturerName);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modelSpinnItem = ((TextView) view.findViewById(R.id.rowText)).getText().toString();
                myid = ((TextView) view.findViewById(R.id.rowid)).getText().toString();
                myApplication.showLog(TAG, "You have selected " + modelSpinnItem + " " + myid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText(context, "You have selected Nothing ..", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != detailses ? detailses.size() : 0);

    }

    public class AdapterHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_detailvehicleNumber_det)
        TextView VehicleNumber;
        @Bind(R.id.tv_detailvehicleBrand_det)
        TextView VehicleBrand;
        @Bind(R.id.tv_detailvehicleNumberModel_det)
        TextView VehicleModel;
        @Bind(R.id.img_vehicelDetailEdit_det)
        ImageView EditButton;
        @Bind(R.id.tv_companyName_det)
        TextView InsurancePolicyName;
        @Bind(R.id.tv_date_det)
        TextView InsurancePolicyExpiryDate;
        @Bind(R.id.vehcileIdHidden_dets)
        TextView VehicleIDHiddden;

        AdapterHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void AddUpdateReminder(String VehicleId, int vehicleModelID, String InsuranceCompany,
                                   String Ins_exp_date, String Poll_exp_date, String Serv_exp_date, String Note, String notificationDate, String Remindertypr) {

        VolleyHelper helper = new VolleyHelper((Activity) context);
        helper.UpdateVehicle(Integer.parseInt(VehicleId), vehicleModelID, InsuranceCompany, Ins_exp_date, Poll_exp_date, Serv_exp_date, Note, notificationDate,Remindertypr);
        detailses.addChangeListener(new RealmChangeListener<RealmResults<VehicleDetails>>() {
            @Override
            public void onChange(RealmResults<VehicleDetails> element) {
                notifyDataSetChanged();
            }
        });
        confirm.dismiss();

    }
   /* private void SelectfromDate() {


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

        final Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(context, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                SelectedDate = sdf.format(c.getTime());
                myApplication.showLog(TAG, SelectedDate);
                viewHolder.date.setText(SelectedDate);
                //  myApplication.setmDate(SelectedDate);
            }

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }*/
}
