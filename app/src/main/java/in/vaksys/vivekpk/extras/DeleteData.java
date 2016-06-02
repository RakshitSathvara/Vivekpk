package in.vaksys.vivekpk.extras;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Harsh on 01-06-2016.
 */
public class DeleteData {

    /**
     * error : false
     * message : user_vehicles deleted successfully
     */

    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String message;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
