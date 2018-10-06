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
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import us.globalpay.manhattan.R;

/**
 * Created by Josué Chávez on 10/09/2018.
 */
public class ButtonAnimator
{
    private static final String TAG = ButtonAnimator.class.getSimpleName();


    public static void shadowButton(View view)
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

    public static void floatingButton(Context context, View view)
    {
        final ImageView imageButton = (ImageView) view;
        final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
        imageButton.startAnimation(myAnim);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        BounceInterpolator interpolator = new BounceInterpolator(0.1, 15);
        myAnim.setInterpolator(interpolator);

        imageButton.startAnimation(myAnim);
    }

    private static int adjustAlpha(int color, float factor)
    {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

}
