package in.vaksys.vivekpk.extras;

import java.util.Map;

import in.vaksys.vivekpk.model.data;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

/**
 * Created by Harsh on 01-06-2016.
 */
public interface ResetApi {


    @Multipart
    @POST("upload")
  //  Call<DeleteData> getTasks(@Header("Authorization") String auuth,@QueryMap Map<String, String> map);

    Call<data> getTasks(@Header("Authorization") String auuth, @Part MultipartBody.Part file);



}


//public interface FileUploadService {
//    @Multipart
//    @POST("upload")
//    Call<data> upload(@Part("description") RequestBody description,
//                              @Part MultipartBody.Part file);
//}