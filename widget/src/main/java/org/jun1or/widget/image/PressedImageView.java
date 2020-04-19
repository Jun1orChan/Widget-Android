package org.jun1or.widget.image;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;

public class PressedImageView extends android.support.v7.widget.AppCompatImageView {

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
            getDrawable().setColorFilter(Color.argb(130, 0, 0, 0),
                    PorterDuff.Mode.SRC_ATOP);
        } else {
            getDrawable().setColorFilter(Color.argb(0, 0, 0, 0),
                    PorterDuff.Mode.SRC_ATOP);
            invalidate();
        }
    }


}
