package in.vaksys.vivekpk.extras;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;

/**
 * Created by Harsh on 01-06-2016.
 */
public interface ResetApi {



    @DELETE("userVehicle")
    Call<DeleteData> getTasks(@Header("Authorization") String auuth,@QueryMap Map<String, String> map);
}
