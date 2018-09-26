package us.globalpay.manhattan.models.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josué Chávez on 26/09/2018.
 */
public class NicknameReqBody
{
    private String nickname;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public Map<String, Object> getAdditionalProperties()
    {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value)
    {
        this.additionalProperties.put(name, value);
    }
}
