package us.globalpay.manhattan.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * Created by Josué Chávez on 10/09/2018.
 */
public class ButtonAnimator
{
    private static final String TAG = ButtonAnimator.class.getSimpleName();


    public static void animateButton(View view)
    {
        final ImageView imageButton = (ImageView) view;
        final ValueAnimator colorAnim = ObjectAnimator.ofFloat(0f, 1f);
        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float mul = (Float) animation.getAnimatedValue();
                int alphaOrange = adjustAlpha(Color.argb(50, 0, 0, 0), mul);
                imageButton.setColorFilter(alphaOrange, PorterDuff.Mode.SRC_ATOP);
                if (mul == 0.0) {
                    imageButton.setColorFilter(null);
                }
            }
        });

        colorAnim.setDuration(120);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.setRepeatCount(1);
        colorAnim.start();

    }

    private static int adjustAlpha(int color, float factor)
    {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    /*public void animateButton2(View view)
    {
        try
        {
            Animation animation = new AlphaAnimation(1, (float)0.5); // Change alpha from fully visible to invisible
            animation.setDuration(200); // duration - half a second
            animation.setInterpolator(new LinearInterpolator()); // do not alter
            // animation
            // rate
            animation.setRepeatCount(Animation.INFINITE); // Repeat animation
            // infinitely
            animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the
            // end so the button will
            // fade back in
            view.startAnimation(animation);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }*/
}
