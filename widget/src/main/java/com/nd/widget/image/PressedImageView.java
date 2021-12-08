package com.nd.widget.image;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author Administrator
 */
public class PressedImageView extends AppCompatImageView {

    public PressedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public PressedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PressedImageView(Context context) {
        super(context);
    }

    @Override
    public void setPressed(boolean pressed) {

        super.setPressed(pressed);

        if (getDrawable() == null) {
            return;
        }
        if (pressed) {
            setColorFilter(getDrawable(), Color.argb(130, 0, 0, 0));
        } else {
            setColorFilter(getDrawable(), Color.argb(0, 0, 0, 0));
        }
        invalidate();
    }

    public static void setColorFilter(@NonNull Drawable drawable, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

}
