package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 21/09/2018.
 */
public class AuthenticateResponse
{
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("middleName")
    @Expose
    private String middleName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("countryID")
    @Expose
    private Integer countryID;
    @SerializedName("authenticationKey")
    @Expose
    private String authenticationKey;

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Integer getCountryID()
    {
        return countryID;
    }

    public void setCountryID(Integer countryID)
    {
        this.countryID = countryID;
    }

    public String getAuthenticationKey()
    {
        return authenticationKey;
    }

    public void setAuthenticationKey(String authenticationKey)
    {
        this.authenticationKey = authenticationKey;
    }

}
