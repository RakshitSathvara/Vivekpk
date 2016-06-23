package in.vaksys.vivekpk.dbPojo;

import io.realm.RealmObject;

/**
 * Created by Harsh on 09-05-2016.
 */
public class VehicleDetails extends RealmObject {

    private String UserID;

    private int VehicleId;

    private String name;

    private String NotificationDate;

    private String ReminderType;

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

    private String type;

    private String note;

    private int brandposi;
    private int modelposi;

    public int getBrandposi() {
        return brandposi;
    }

    public void setBrandposi(int brandposi) {
        this.brandposi = brandposi;
    }

    public int getModelposi() {
        return modelposi;
    }

    public void setModelposi(int modelposi) {
        this.modelposi = modelposi;
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

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
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

    public String getReminderType() {
        return ReminderType;
    }

    public void setReminderType(String reminderType) {
        ReminderType = reminderType;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
