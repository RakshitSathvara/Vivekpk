package in.vaksys.vivekpk.dbPojo;

import io.realm.RealmObject;

/**
 * Created by Harsh on 26-05-2016.
 */
public class InsuranceCompanies extends RealmObject {

    private int InsuranceId;

    private String InsuranceName;

    private String InsuranceCreatedAt;

    private String InsuranceUpdatedAt;

    public int getInsuranceId() {
        return InsuranceId;
    }

    public void setInsuranceId(int insuranceId) {
        InsuranceId = insuranceId;
    }

    public String getInsuranceName() {
        return InsuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        InsuranceName = insuranceName;
    }

    public String getInsuranceCreatedAt() {
        return InsuranceCreatedAt;
    }

    public void setInsuranceCreatedAt(String insuranceCreatedAt) {
        InsuranceCreatedAt = insuranceCreatedAt;
    }

    public String getInsuranceUpdatedAt() {
        return InsuranceUpdatedAt;
    }

    public void setInsuranceUpdatedAt(String insuranceUpdatedAt) {
        InsuranceUpdatedAt = insuranceUpdatedAt;
    }
}
