package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 21/09/2018.
 */
public class AuthenticateReqBody
{
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("MiddleName")
    @Expose
    private String middleName;
    @SerializedName("DeviceID")
    @Expose
    private String deviceID;
    @SerializedName("Url")
    @Expose
    private String url;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("ProfileID")
    @Expose
    private String profileID;
    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("AuthenticationProvider")
    @Expose
    private String authenticationProvider;

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getDeviceID()
    {
        return deviceID;
    }

    public void setDeviceID(String deviceID)
    {
        this.deviceID = deviceID;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getProfileID()
    {
        return profileID;
    }

    public void setProfileID(String profileID)
    {
        this.profileID = profileID;
    }

    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    public String getAuthenticationProvider()
    {
        return authenticationProvider;
    }

    public void setAuthenticationProvider(String authenticationProvider)
    {
        this.authenticationProvider = authenticationProvider;
    }
}