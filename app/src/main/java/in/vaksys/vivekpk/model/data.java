package in.vaksys.vivekpk.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vishal on 04/06/2016.
 */
public class data {


    /**
     * error : false
     * result : http://54.213.226.66/users/39/8969288f4245120e7c3870287cce0ff3_Lighthouse.jpg
     */

    @SerializedName("error")
    private boolean error;
    @SerializedName("result")
    private String result;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
