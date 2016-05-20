package in.vaksys.vivekpk.dbPojo;

import io.realm.RealmObject;

/**
 * Created by Harsh on 09-05-2016.
 */
public class VehicleDetails extends RealmObject {

    private String UserAPiKey;

    private String VehicleName;

    private String ModelId;

    private String VehicleNo;

    private String RcNo;

    private String EngineType;

    private String year;

    private String InsuranceCompany;

    private String InsuranceExpireDate;

    private String PollutionExpireDate;

    private String RcRenewDate;

    public String getVehicleName() {
        return VehicleName;
    }

    public void setVehicleName(String vehicleName) {
        VehicleName = vehicleName;
    }

    public String getModelId() {
        return ModelId;
    }

    public void setModelId(String modelId) {
        ModelId = modelId;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }

    public String getRcNo() {
        return RcNo;
    }

    public void setRcNo(String rcNo) {
        RcNo = rcNo;
    }

    public String getEngineType() {
        return EngineType;
    }

    public void setEngineType(String engineType) {
        EngineType = engineType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getInsuranceCompany() {
        return InsuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        InsuranceCompany = insuranceCompany;
    }

    public String getInsuranceExpireDate() {
        return InsuranceExpireDate;
    }

    public void setInsuranceExpireDate(String insuranceExpireDate) {
        InsuranceExpireDate = insuranceExpireDate;
    }

    public String getPollutionExpireDate() {
        return PollutionExpireDate;
    }

    public void setPollutionExpireDate(String pollutionExpireDate) {
        PollutionExpireDate = pollutionExpireDate;
    }

    public String getRcRenewDate() {
        return RcRenewDate;
    }

    public void setRcRenewDate(String rcRenewDate) {
        RcRenewDate = rcRenewDate;
    }

    public String getUserAPiKey() {
        return UserAPiKey;
    }

    public void setUserAPiKey(String userAPiKey) {
        UserAPiKey = userAPiKey;
    }
}
