package in.vaksys.vivekpk.dbPojo;

import io.realm.RealmObject;

/**
 * Created by Harsh on 09-05-2016.
 */
public class VehicleDetails extends RealmObject {

    private String UserAPiKey;

    private int VehicleId;

    private String NotificationDate;

    private String ReminderTpe;
    // TODO: 30-05-2016 remove this column and relevent activities like in signin fragment
    private String VehicleBrandName;

    private int VehicleModelID;

    private String VehicleNo;

    private String RcNo;

    private String EngineType;

    private String year;

    private String InsuranceCompany;

    private String InsuranceExpireDate;

    private String PollutionExpireDate;

    private String ServiceExpireDate;

    private String RcRenewDate;

    private String mDate;

    public String getVehicleBrandName() {
        return VehicleBrandName;
    }

    public void setVehicleBrandName(String vehicleBrandName) {
        VehicleBrandName = vehicleBrandName;
    }

    public int getVehicleModelID() {
        return VehicleModelID;
    }

    public void setVehicleModelID(int vehicleModelID) {
        VehicleModelID = vehicleModelID;
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

    public String getNotificationDate() {
        return NotificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        NotificationDate = notificationDate;
    }

    public int getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(int vehicleId) {
        VehicleId = vehicleId;
    }

    public String getReminderTpe() {
        return ReminderTpe;
    }

    public void setReminderTpe(String reminderTpe) {
        ReminderTpe = reminderTpe;
    }

    public String getServiceExpireDate() {
        return ServiceExpireDate;
    }

    public void setServiceExpireDate(String serviceExpireDate) {
        ServiceExpireDate = serviceExpireDate;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }
}
