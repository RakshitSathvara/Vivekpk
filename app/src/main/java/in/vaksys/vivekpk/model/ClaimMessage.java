package in.vaksys.vivekpk.model;

/**
 * Created by patel on 26-05-2016.
 */
public class ClaimMessage {
    private String CarNo;

    private int model;

    public String getCarNo() {
        return CarNo;
    }

    public ClaimMessage(String CarNo, int model) {
        this.CarNo = CarNo;
        this.model = model;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }
}
