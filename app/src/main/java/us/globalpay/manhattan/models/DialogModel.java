package us.globalpay.manhattan.models;

/**
 * Created by Josué Chávez on 24/09/2018.
 */
public class DialogModel
{
    private String Title;
    private String Content;
    private String AcceptButton;
    private String CanelButton;
    private String CustomButton;

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        Title = title;
    }

    public String getContent()
    {
        return Content;
    }

    public void setContent(String content)
    {
        Content = content;
    }

    public String getAcceptButton()
    {
        return AcceptButton;
    }

    public void setAcceptButton(String acceptButton)
    {
        AcceptButton = acceptButton;
    }

    public String getCanelButton()
    {
        return CanelButton;
    }

    public void setCanelButton(String canelButton)
    {
        CanelButton = canelButton;
    }

    public String getCustomButton()
    {
        return CustomButton;
    }

    public void setCustomButton(String customButton)
    {
        CustomButton = customButton;
    }
}
