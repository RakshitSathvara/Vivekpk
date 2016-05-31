package in.vaksys.vivekpk.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Harsh on 26-05-2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.AdapterHolder> {

    private final Context context;
    private final RealmResults<VehicleDetails> detailses;
    MyApplication myApplication;
    private static final String TAG = "RecyclerViewAdapter";
    private Realm realm;
    private String modelSpinnItem;
    private String myid;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private String SelectedDate;
    SimpleDateFormat sdf;
    VehicleDetails details;
    AdapterHolder viewHolder;

    //    Calendar newDate;

    public RecyclerViewAdapter(Context context, RealmResults<VehicleDetails> detailses) {

        this.myApplication = MyApplication.getInstance();
        realm = Realm.getDefaultInstance();
        this.context = context;
        this.detailses = detailses;
        myApplication.createDialog((Activity) context, false);
        sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
//        setDateTimeField();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
//        newDate = Calendar.getInstance();
        //  SelectedDate = dateFormatter.format(newDate.getTime());
//        setDateTimeField();
    }

    private void setupInsuranceSpinner(Spinner mSpinner) {

        RealmResults<InsuranceCompanies> results = realm.where(InsuranceCompanies.class).findAll();

        mySpinnerAdapterInsurance mySpinnerAdapterCity = new mySpinnerAdapterInsurance(context, results, "bike");
        mSpinner.setAdapter(mySpinnerAdapterCity);

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
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.insurance_policy_with_vihicle, null);
        viewHolder = new AdapterHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterHolder holder, int position) {
        details = detailses.get(position);
        VehicleModels vehicleModels = realm.where(VehicleModels.class).equalTo("id", details.getVehicleModelID()).findFirst();

        holder.VehicleNumber.setText(details.getVehicleNo());
        holder.VehicleBrand.setText(vehicleModels.getManufacturerName());
        holder.VehicleModel.setText(vehicleModels.getModel());

        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.SelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SelectfromDate();
                SetData(holder);
            }
        });
        setupInsuranceSpinner(holder.mSpinner);
        //   holder.date.setText(myApplication.getmDate());
    }

    private void SetData(final AdapterHolder holder) {
        Date d = null;
        final Calendar newCalendar = Calendar.getInstance();

        newCalendar.add(Calendar.DAY_OF_MONTH, 26);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        String formattedDate = sdf.format(newCalendar.getTime());
        try {
            d = sdf.parse(formattedDate);
        } catch (ParseException e) {
            Log.e(TAG, "SelectfromDate: " + e);
        }
        fromDatePickerDialog = new DatePickerDialog(context, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
                newCalendar.set(year, monthOfYear, dayOfMonth);
                holder.date.setText(dateFormatter.format(newCalendar.getTime()));
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
        @Bind(R.id.tv_detailvehicleNumber)
        TextView VehicleNumber;
        @Bind(R.id.tv_detailvehicleBrand)
        TextView VehicleBrand;
        @Bind(R.id.tv_detailvehicleNumberModel)
        TextView VehicleModel;
        @Bind(R.id.img_vehicelDetailEdit)
        ImageView EditButton;
        @Bind(R.id.sp_insuranceCompany)
        Spinner mSpinner;
        @Bind(R.id.tv_date_en)
        TextView date;
        @Bind(R.id.btn_insurance_details_expiry_cancel)
        Button btnCancel;
        @Bind(R.id.btn_insurance_details_expiry_setAlertDetail)
        Button btnConfirm;
        @Bind(R.id.select_expiry_date_insurance)
        LinearLayout SelectDate;

        AdapterHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
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
