package in.vaksys.vivekpk.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.dbPojo.UserImages;
import in.vaksys.vivekpk.extras.MyApplication;
import in.vaksys.vivekpk.extras.VolleyHelper;
import in.vaksys.vivekpk.model.ReplaceImageFull;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Harsh on 27-01-2016.
 */
public class FullLiencesGalleryImageAdapter extends RecyclerView.Adapter<FullLiencesGalleryImageAdapter.ViewHolder> {


    private final Context context;
    private final RealmResults<UserImages> userImages;

    MyApplication myApplication;
    private Realm realm;
    private String apikey;

    public FullLiencesGalleryImageAdapter(Context context, RealmResults<UserImages> userImages) {
        this.context = context;
        this.userImages = userImages;
        myApplication = MyApplication.getInstance();
        realm = Realm.getDefaultInstance();

        apikey = myApplication.getApikey();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_full_liences_image, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final UserImages images = userImages.get(position);
//        holder.ImageUser.setImageBitmap(GetImageFromStream(images.getImages()));
        //  holder.ImgaeUUID.setText(images.getId());
        final String id = images.getId();

        myApplication.showLog("id----->", id);

        EventBus.getDefault().post(new ReplaceImageFull("",id, images.getImageType(), images.getVehicleid()));

       // holder.tv_imageName.setText(images.getImageName());
        Picasso.with(context).load(images.getImagesurl()).placeholder(R.drawable.splashscreen).into(holder.DocumentImage, new Callback() {
            @Override
            public void onSuccess() {

                //  Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {

//                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });


//        holder.img_full_document_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//                alertDialogBuilder.setTitle("Sure Want to Delete this Documents ??")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int ids) {
//
//                                VolleyHelper helper = new VolleyHelper((Activity) context);
//
//                                helper.DeleteContact(id,context);
//
//                                userImages.addChangeListener(new RealmChangeListener<RealmResults<UserImages>>() {
//                                    @Override
//                                    public void onChange(RealmResults<UserImages> element) {
//                                        notifyDataSetChanged();
//                                    }
//                                });
//
//                                //  DeleteContact(id, context);
//                                dialog.cancel();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//                AlertDialog alertDialog = alertDialogBuilder.create();
//                alertDialog.show();
//
//
//            }
//        });


//        holder.img_full_document_replace.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String id = images.getId();
//                myApplication.showLog("id----->", id);
//                opengalleryandcamera(id, images.getImageType(), images.getVehicleid());
//
//            }
//        });
//
//        holder.homebuttonfinish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((Activity)context).finish();
//            }
//        });

//        holder.DocumentImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(myApplication, "click", Toast.LENGTH_SHORT).show();
//                Intent f = new Intent(context,FullDocumentImage.class);
//                context.startActivity(f);
//
//
//            }
//        });
//
//        holder.tv_image_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//                alertDialogBuilder.setTitle("Sure Want to Delete this Documents ??")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int ids) {
//                                DeleteContact(id, context);
//                                dialog.cancel();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//                AlertDialog alertDialog = alertDialogBuilder.create();
//                alertDialog.show();
//
//
//            }
//        });
//
//        holder.tv_image_replace.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String id = images.getId();
//                myApplication.showLog("id----->", id);
//                opengalleryandcamera(id, images.getImageType(), images.getVehicleid());
//
//            }
//        });
    }

    private void opengalleryandcamera(final String id, final String imageType, final String vehicleid) {

        final Dialog confirm = new Dialog(context);
        confirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirm.setContentView(R.layout.dailog_pic_gallery);

        LinearLayout gallery = (LinearLayout) confirm.findViewById(R.id.linear_addGallery_dailog);
        LinearLayout camera = (LinearLayout) confirm.findViewById(R.id.linear_addCamera_dailog);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // EventBus.getDefault().post(new ImageMessage(type, Vehicle_id, "gallery"));
                /// second parameter is ----> id
                EventBus.getDefault().post(new ReplaceImageFull("gallery", id, imageType, vehicleid));
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

                //     EventBus.getDefault().post(new ImageMessage(type, Vehicle_id, "camera"));
                EventBus.getDefault().post(new ReplaceImageFull("camera", id, imageType, vehicleid));
                confirm.dismiss();
            }
        });
        confirm.show();


    }

    @Override
    public int getItemCount() {
        return (null != userImages ? userImages.size() : 0);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.img_set_lience)
        ImageView DocumentImage;
//        @Bind(R.id.img_full_document_replace)
//        TextView img_full_document_replace;
//        @Bind(R.id.img_full_document_delete)
//        TextView img_full_document_delete;
//        @Bind(R.id.img_full_document_backbutton)
//        ImageView homebuttonfinish;
//        @Bind(R.id.tv_imageName_gallery_list)
//        TextView tv_imageName;
//
//        @Bind(R.id.tv_replace_gallery_list)
//        TextView tv_image_replace;
//        @Bind(R.id.tv_delete_gallery_list)
//        TextView tv_image_delete;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

//    private void opengalleryandcamera(final String id, final String imageType, final String vehicleid) {
//
//        final Dialog confirm = new Dialog(context);
//        confirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        confirm.setContentView(R.layout.dailog_pic_gallery);
//
//        LinearLayout gallery = (LinearLayout) confirm.findViewById(R.id.linear_addGallery_dailog);
//        LinearLayout camera = (LinearLayout) confirm.findViewById(R.id.linear_addCamera_dailog);
//
//        gallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                // EventBus.getDefault().post(new ImageMessage(type, Vehicle_id, "gallery"));
//                /// second parameter is ----> id
//                EventBus.getDefault().post(new ReplaceImage("gallery", id, imageType, vehicleid));
//                //EventBus.getDefault().post(new Message("RC"));
//
////                        Intent intent = new Intent(context,DocumentFragment.class);
////                        intent.setType("image/*");
////                        intent.setAction(Intent.ACTION_GET_CONTENT);
//
//                //   myInterface.myStartActivityForResult(intent,PICK_IMAGE_REQUEST);
//
////                        ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), DocumentFragment.PICK_IMAGE_REQUEST);
//
//                confirm.dismiss();
//            }
//        });
//        camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //     EventBus.getDefault().post(new ImageMessage(type, Vehicle_id, "camera"));
//                EventBus.getDefault().post(new ReplaceImage("camera", id, imageType, vehicleid));
//                confirm.dismiss();
//            }
//        });
//        confirm.show();
//
//
//    }
//
//
//    public void DeleteContact(final String img_id, final Context context) {
//
//
//        String tag_string_req = "req_delete_vehicle";
//
//        MyApplication.getInstance().DialogMessage("Deleting Document...");
//        MyApplication.getInstance().showDialog();
//
//        StringRequest strReq = new StringRequest(Request.Method.DELETE,
//                AppConfig.URL_DELETE_DOC, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//
//
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    boolean error = jObj.getBoolean("error");
//                    // Log.e(TAG, "onResponse: " + jObj.toString());
//
//                    // Check for error node in json
//                    if (!error) {
//                        MyApplication.getInstance().hideDialog();
//                        Toast.makeText(context,
//                                "Delete Successfull... ", Toast.LENGTH_LONG).show();
//
//                        // parsing the user profile information
////                        JSONObject profileObj = jObj.getJSONObject("result");
//
//                        DeleteContactToDatabase(img_id);
//                    } else {
//                        MyApplication.getInstance().hideDialog();
//                        // Error in login. Get the error message
//                        String errorMsg = jObj.getString("message");
//                        Toast.makeText(context,
//                                "Error :" + errorMsg, Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    // JSON error
//                    MyApplication.getInstance().hideDialog();
//                    e.printStackTrace();
//                    Toast.makeText(context, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                Log.e(TAG, "Login Error: " + error.getMessage());
//                MyApplication.getInstance().ErrorSnackBar((Activity) context);
//                MyApplication.getInstance().hideDialog();
//            }
//        }) {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", apikey);
//                headers.put("id", String.valueOf(img_id));
//                // myApplication.showLog(TAG, String.valueOf("passed auth"));
//                return headers;
//
//            }
//        };
//        // Adding request to request queue
//        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
//
//    }
//
//    private void DeleteContactToDatabase(final String img_id) {
//
//
//        realm.beginTransaction();
//
//
//        final UserImages userI = realm.where(UserImages.class).equalTo("id", img_id).findFirst();
//
//        userI.deleteFromRealm();
//
//        realm.commitTransaction();
////
////        final RealmResults<EmergencyContact> results = realm.where(EmergencyContact.class).findAll();
////        Log.e(TAG, "SaveIntoDatabase: " + results.size());
////
//        userImages.addChangeListener(new RealmChangeListener<RealmResults<UserImages>>() {
//            @Override
//            public void onChange(RealmResults<UserImages> element) {
//                notifyDataSetChanged();
//            }
//        });
//
//        myApplication.hideDialog();
//
//    }

}
