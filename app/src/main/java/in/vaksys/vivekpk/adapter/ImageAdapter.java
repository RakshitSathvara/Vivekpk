package in.vaksys.vivekpk.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.vivekpk.R;
import in.vaksys.vivekpk.dbPojo.UserImages;
import in.vaksys.vivekpk.extras.MyApplication;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Harsh on 27-01-2016.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {


    private final Context context;
    private final RealmResults<UserImages> userImages;
    private Realm realm;

    MyApplication myApplication;
    UserImages userImages1;

    public ImageAdapter(Context context, RealmResults<UserImages> userImages) {
        this.context = context;
        this.userImages = userImages;
        myApplication = MyApplication.getInstance();
        realm = Realm.getDefaultInstance();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_image, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        UserImages images = userImages.get(position);
//        holder.ImageUser.setImageBitmap(GetImageFromStream(images.getImages()));
        holder.ImgaeUUID.setText(images.getId());
        Picasso.with(context).load(images.getImagesurl()).placeholder(R.mipmap.ic_launcher).into(holder.ImageUser, new Callback() {
            @Override
            public void onSuccess() {

                //   Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                holder.progressBar.setVisibility(View.GONE);
                            }

            @Override
            public void onError() {

                holder.progressBar.setVisibility(View.GONE);
               // myApplication.ErrorSnackBar((Activity) context);
                //  Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

            }
        });

        holder.ImageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(myApplication, "click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Bitmap GetImageFromStream(String images) {
        myApplication.showLog("adapter ", "sakjdasdjhakjsdhkjah ");
        byte[] b = Base64.decode(images, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }


    @Override
    public int getItemCount() {
        return (null != userImages ? userImages.size() : 0);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.single_image_id)
        TextView ImgaeUUID;
        @Bind(R.id.single_image)
        ImageView ImageUser;
        @Bind(R.id.progressBar_img)
        ProgressBar progressBar;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void saveImageToDatabase(String ImageId, String ImageUrl, String ImageType, String v_id, String imgname) {

        try {
            realm.beginTransaction();
            userImages1 = realm.createObject(UserImages.class);
            userImages1.setId(ImageId);
            userImages1.setImagesurl(ImageUrl);
            userImages1.setImageType(ImageType);
            userImages1.setVehicleid(v_id);
            userImages1.setImageName(imgname);
            realm.commitTransaction();

            userImages.addChangeListener(new RealmChangeListener<RealmResults<UserImages>>() {
                @Override
                public void onChange(RealmResults<UserImages> element) {
                    notifyDataSetChanged();
                }
            });

            //   notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
