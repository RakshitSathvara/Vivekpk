package in.vaksys.vivekpk.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.activities.DocumentImageGallery;
import in.vaksys.vivekpk.dbPojo.UserImages;
import in.vaksys.vivekpk.dbPojo.VehicleDetails;
import in.vaksys.vivekpk.dbPojo.VehicleModels;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.model.ImageMessage;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Harsh on 26-05-2016.
 */
public class DocumentDetailsRecyclerViewAdapter extends RecyclerView.Adapter<DocumentDetailsRecyclerViewAdapter.AdapterHolder> {

    private static final String BLANK = "";
    private final Context context;
    private final RealmResults<VehicleDetails> detailses;
    MyApplication myApplication;
    private static final String TAG = "InsuranceRecyclerViewAdapter";
    private Realm realm;
    VehicleDetails details;
    AdapterHolder viewHolder;
    private RealmResults<UserImages> userImages;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static int PICK_IMAGE_REQUEST = 1;
    Uri picUri;
    private Uri filePath;
    String realPath;




//    UserImages images;

    //    Calendar newDate;




    public DocumentDetailsRecyclerViewAdapter(Context context, RealmResults<VehicleDetails> detailses, RealmResults<UserImages> userImages) {
        this.userImages = userImages;

        this.myApplication = MyApplication.getInstance();
        realm = Realm.getDefaultInstance();
        this.context = context;
        this.detailses = detailses;
        myApplication.createDialog((Activity) context, false);


    }


    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_list_raw, null);
        viewHolder = new AdapterHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterHolder holder, int position) {
//        images = userImages.get(position);
        details = detailses.get(position);

        VehicleModels vehicleModels = realm.where(VehicleModels.class).equalTo("id", details.getVehicleModelID()).findFirst();


        holder.VehicleIDHiddden.setText(String.valueOf(details.getVehicleId()));

        holder.VehicleNumber.setText(details.getVehicleNo());
        holder.VehicleBrand.setText(vehicleModels.getManufacturerName());
        holder.VehicleModel.setText(vehicleModels.getModel());

        setupViews(holder);
        int Rc_sizeee = realm.where(UserImages.class).equalTo("ImageType", "RC")
                .equalTo("vehicleid", holder.VehicleIDHiddden.getText().toString()).findAll().size();


        int Insurance_sizeee = realm.where(UserImages.class).equalTo("ImageType", "Insurance")
                .equalTo("vehicleid", holder.VehicleIDHiddden.getText().toString()).findAll().size();
        int Emission_sizeee = realm.where(UserImages.class).equalTo("ImageType", "Emission")
                .equalTo("vehicleid", holder.VehicleIDHiddden.getText().toString()).findAll().size();
        int bills_sizeee = realm.where(UserImages.class).equalTo("ImageType", "Bills")
                .equalTo("vehicleid", holder.VehicleIDHiddden.getText().toString()).findAll().size();

        holder.Rc_count.setText(String.valueOf(Rc_sizeee));
        holder.Insurance_count.setText(String.valueOf(Insurance_sizeee));
        holder.Emission_count.setText(String.valueOf(Emission_sizeee));
        holder.Bills_count.setText(String.valueOf(bills_sizeee));

        detailses.addChangeListener(new RealmChangeListener<RealmResults<VehicleDetails>>() {
            @Override
            public void onChange(RealmResults<VehicleDetails> element) {
                notifyDataSetChanged();
                setupViews(holder);

            }
        });

        userImages.addChangeListener(new RealmChangeListener<RealmResults<UserImages>>() {
            @Override
            public void onChange(RealmResults<UserImages> element) {
                notifyDataSetChanged();
                setupViews(holder);

            }
        });

        holder.EdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // open galllery activity

                Intent i = new Intent(context, DocumentImageGallery.class);
                context.startActivity(i);
            }
        });

        holder.RC_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                opengalleryandcamera("RC", holder.VehicleIDHiddden.getText().toString());

                //type = RC
            }
        });

        holder.Insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //type = Insurance
                opengalleryandcamera("Insurance", holder.VehicleIDHiddden.getText().toString());


            }
        });
        holder.Emission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //type = Emission
                opengalleryandcamera("Emission", holder.VehicleIDHiddden.getText().toString());
            }
        });
        holder.Bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //type = Bills
                opengalleryandcamera("Bills", holder.VehicleIDHiddden.getText().toString());
            }
        });


    }

    private void opengalleryandcamera(final String type, final String Vehicle_id) {

        final Dialog confirm = new Dialog(context);
        confirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirm.setContentView(R.layout.dailog_pic_gallery);

        LinearLayout gallery = (LinearLayout) confirm.findViewById(R.id.linear_addGallery_dailog);
        LinearLayout camera = (LinearLayout) confirm.findViewById(R.id.linear_addCamera_dailog);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EventBus.getDefault().post(new ImageMessage(type, Vehicle_id, "gallery"));
                //EventBus.getDefault().post(new Message("RC"));

//                        Intent intent = new Intent(context,DocumentFragment.class);
//                        intent.setType("image/*");
//                        intent.setAction(Intent.ACTION_GET_CONTENT);

                //   myInterface.myStartActivityForResult(intent,PICK_IMAGE_REQUEST);

//                        ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), DocumentFragment.PICK_IMAGE_REQUEST);

                confirm.dismiss();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBus.getDefault().post(new ImageMessage(type, Vehicle_id, "camera"));

                confirm.dismiss();
            }
        });
        confirm.show();


    }

    private void setupViews(AdapterHolder holder) {
        int rc_size = realm.where(UserImages.class).equalTo("ImageType", "RC").findAll().size();
        int emi_size = realm.where(UserImages.class).equalTo("ImageType", "Emission").findAll().size();
        int ins_size = realm.where(UserImages.class).equalTo("ImageType", "Insurance").findAll().size();
        int bill_size = realm.where(UserImages.class).equalTo("ImageType", "Bills").findAll().size();

        if (rc_size <= 0) {
            holder.RC_view.setVisibility(View.GONE);
        }
        if (emi_size <= 0) {
            holder.Emission_view.setVisibility(View.GONE);
        }
        if (ins_size <= 0) {
            holder.Insuracne_view.setVisibility(View.GONE);
        }
        if (bill_size <= 0) {
            holder.Bills_view.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return (null != detailses ? detailses.size() : 0);

    }


    public class AdapterHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_DocumentdetailVehicelNumber)
        TextView VehicleNumber;
        @Bind(R.id.tv_DocumentdetailVehicelBrand)
        TextView VehicleBrand;
        @Bind(R.id.tv_DocumentdetailVehicelModel)
        TextView VehicleModel;
        @Bind(R.id.vehcileIdHidden_list)
        TextView VehicleIDHiddden;
        @Bind(R.id.img_document_edit)
        ImageView EdButton;
        @Bind(R.id.linear_vehicelDoc_RcBook)
        LinearLayout RC_book;
        @Bind(R.id.linear_vehicelDoc_Insurance)
        LinearLayout Insurance;
        @Bind(R.id.linear_vehicelDoc_Emission)
        LinearLayout Emission;
        @Bind(R.id.linear_vehicelDo_Bills)
        LinearLayout Bills;
        @Bind(R.id.number_rc_book)
        LinearLayout RC_view;
        @Bind(R.id.number_insurance)
        LinearLayout Insuracne_view;
        @Bind(R.id.number_emission)
        LinearLayout Emission_view;
        @Bind(R.id.number_rc_bills)
        LinearLayout Bills_view;
        @Bind(R.id.tv_rcBookCount)
        TextView Rc_count;
        @Bind(R.id.tv_inssuranceCount)
        TextView Insurance_count;
        @Bind(R.id.tv_emissionCount)
        TextView Emission_count;
        @Bind(R.id.tv_billsCount)
        TextView Bills_count;

        AdapterHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }


    }


}
