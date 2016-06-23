package in.vaksys.vivekpk.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vishal on 22/06/2016.
 */
public class demo {

    /**
     * error : true
     * message : Sorry, phone number already registered
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
