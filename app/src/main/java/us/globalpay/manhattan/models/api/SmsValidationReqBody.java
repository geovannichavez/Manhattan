package us.globalpay.manhattan.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 02/10/2018.
 */
public class SmsValidationReqBody
{
    @SerializedName("token")
    @Expose
    private String token;

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}
