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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.vivekpk.R;
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
public class ServiceDetailsRecyclerViewAdapter extends RecyclerView.Adapter<ServiceDetailsRecyclerViewAdapter.AdapterHolder> {

    private static final String BLANK = "";
    private final Context context;
    private final RealmResults<VehicleDetails> detailses;
    MyApplication myApplication;
    private Realm realm;
    private static final String TAG = "ServiceDetailsRecyclerViewAdapter";
    private String modelSpinnItem;
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

    public ServiceDetailsRecyclerViewAdapter(Context context, RealmResults<VehicleDetails> detailses) {

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_details, parent,false);
        viewHolder = new AdapterHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterHolder holder, int position) {
        details = detailses.get(position);
        VehicleModels vehicleModels = realm.where(VehicleModels.class).equalTo("id", details.getVehicleModelID()).findFirst();

        holder.VehicleIDHiddden.setText(String.valueOf(details.getVehicleId()));

        holder.VehicleNumber.setText(details.getVehicleNo());
        holder.VehicleBrand.setText(vehicleModels.getManufacturerName());
        holder.VehicleModel.setText(vehicleModels.getModel());
        holder.Notes.setText(details.getNote());
        holder.ServiceExpiryDate.setText(details.getServiceExpireDate());

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
                confirm.setContentView(R.layout.service_due_date);
                confirm.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button btnCancel = (Button) confirm.findViewById(R.id.btn_service_due_date_cancel);
                Button btnSetAlert = (Button) confirm.findViewById(R.id.btn_service_due_date_setAlert);

                final TextView DatePicker = (TextView) confirm.findViewById(R.id.tv_date_serv);
                final EditText etNotes = (EditText) confirm.findViewById(R.id.et_optionalField_serv);

                DatePicker.setText(details.getServiceExpireDate());
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
                        if (etNotes.getText().toString().isEmpty() || DatePicker.getText().toString().equalsIgnoreCase("Expiry Date")) {
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
                                            NotificationDate = myApplication.ChanageDate(DatePicker.getText().toString(), 5);
                                        } else if (setId == 2) {
                                            NotificationDate = myApplication.ChanageDate(DatePicker.getText().toString(), 15);
                                        } else if (setId == 3) {
                                            NotificationDate = myApplication.ChanageDate(DatePicker.getText().toString(), 25);
                                        }
                                    } else {
                                        Toast.makeText(context, "Please Select Reminder.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    AddUpdateReminder(holder.VehicleIDHiddden.getText().toString(),
                                            details.getVehicleModelID(), BLANK, BLANK
                                            , BLANK, DatePicker.getText().toString(), etNotes.getText().toString(), NotificationDate,"Service Reminder");
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
                date.setText(dateFormatter.format(newCalendar.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromDatePickerDialog.getDatePicker().setMinDate(d.getTime());

        fromDatePickerDialog.show();
    }

    @Override
    public int getItemCount() {
        return (null != detailses ? detailses.size() : 0);

    }

    public class AdapterHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_detailvehicleNumber_serv)
        TextView VehicleNumber;
        @Bind(R.id.tv_detailvehicleBrand_serv)
        TextView VehicleBrand;
        @Bind(R.id.tv_detailvehicleNumberModel_serv)
        TextView VehicleModel;
        @Bind(R.id.img_vehicelDetailEdit_serv)
        ImageView EditButton;
        @Bind(R.id.tv_companyName_serv)
        TextView Notes;
        @Bind(R.id.tv_date_serv)
        TextView ServiceExpiryDate;
        @Bind(R.id.vehcileIdHidden_serv)
        TextView VehicleIDHiddden;

        AdapterHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void AddUpdateReminder(String VehicleId, int vehicleModelID, String InsuranceCompany,
                                   String Ins_exp_date, String Poll_exp_date, String Serv_exp_date, String Note, String notificationDate, String s) {

        VolleyHelper helper = new VolleyHelper((Activity) context);
        helper.UpdateVehicle(Integer.parseInt(VehicleId), vehicleModelID, InsuranceCompany, Ins_exp_date, Poll_exp_date, Serv_exp_date, Note, notificationDate,s);
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
