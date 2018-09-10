package us.globalpay.manhattan.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Josué Chávez on 10/09/2018.
 */
public class ButtonAnimator
{
    private static final String TAG = ButtonAnimator.class.getSimpleName();

    private static ButtonAnimator singleton;
    private Context mContext;

    public static synchronized ButtonAnimator getInstance(Context context)
    {
        if (singleton == null)
        {
            singleton = new ButtonAnimator(context);
        }
        return singleton;
    }

    private ButtonAnimator(Context context)
    {
        this.mContext = context;
    }

    public void animateButton(View view)
    {
        try
        {
            ImageView image = (ImageView) view;
            Drawable drawableNormal = image.getDrawable().getCurrent();
            Drawable drawablePressed = image.getDrawable().getConstantState().newDrawable();
            drawablePressed.mutate();
            drawablePressed.setColorFilter(Color.argb(50, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);

            StateListDrawable listDrawable = new StateListDrawable();
            listDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed);
            listDrawable.addState(new int[]{}, drawableNormal);
            image.setImageDrawable(listDrawable);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
