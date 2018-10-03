package us.globalpay.manhattan.utils;

/**
 * Created by Josué Chávez on 03/10/2018.
 */
public class Reminder
{
    private String mTitle;
    private String mEventLocation;
    private String mDescription;
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;

    public Reminder(String title, String location, String description, int year, int  month, int dayOfMonth)
    {
        this.mTitle = title;
        this.mEventLocation = location;
        this.mDescription = description;
        this.mYear = year;
        this.mMonth = month;
        this.mDayOfMonth = dayOfMonth;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public String getEventLocation()
    {
        return mEventLocation;
    }

    public String getDescription()
    {
        return mDescription;
    }

    public int getYear()
    {
        return mYear;
    }

    public int getMonth()
    {
        return mMonth;
    }

    public int getDayOfMonth()
    {
        return mDayOfMonth;
    }

    public static class Builder
    {
        private String title;
        private String eventLocation;
        private String description;
        private int year;
        private int month;
        private int dayOfMonth;

        public Builder title(String title)
        {
            this.title = title;
            return this;
        }

        public Builder eventLocation(String eventLocation)
        {
            this.eventLocation = eventLocation;
            return this;
        }

        public Builder description(String description)
        {
            this.eventLocation = description;
            return this;
        }

        public Builder year(int year)
        {
            this.year = year;
            return this;
        }

        public Builder month(int month)
        {
            this.month = month;
            return this;
        }

        public Builder dayOfMonth(int dayOfMonth)
        {
            this.dayOfMonth = dayOfMonth;
            return this;
        }

        public Reminder createReminder()
        {
            return new Reminder(title, eventLocation, description, year, month, dayOfMonth);
        }
    }
}
