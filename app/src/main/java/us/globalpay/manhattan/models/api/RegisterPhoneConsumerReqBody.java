package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 01/10/2018.
 */
public class RegisterPhoneConsumerReqBody
{
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("countryID")
    @Expose
    private String countryID;
    @SerializedName("deviceID")
    @Expose
    private String deviceID;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }
}
