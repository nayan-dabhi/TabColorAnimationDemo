package com.nd.colortabanimationdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomHeader extends FrameLayout {
    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.imageContainer)
    FrameLayout imageContainer;

    private ColorDrawable overlayColorDrawable;
    private float maxOffset;
    private int currentOffset;

    public CustomHeader(Context context) {
        super(context);
        init();
    }

    public CustomHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CustomHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_header, this, true);
        ButterKnife.bind(this);

        initColors();
        image.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                image.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                setMaxOffset();
            }
        });
    }

    private void initColors() {
        overlayColorDrawable = new ColorDrawable(getResources().getColor(R.color.primary));
    }

    public void onOffsetChanged(int offset) {
        currentOffset = offset;
        applyColor();
    }

    public void setColor(int color) {
        prepareOverlayColor(color);
        applyColor();
    }

    private void prepareOverlayColor(int color) {
        overlayColorDrawable.setColor(color);
        overlayColorDrawable.setAlpha(0);
        imageContainer.setForeground(overlayColorDrawable);
    }

    private void applyColor() {
        if (overlayColorDrawable != null) {
            overlayColorDrawable.setAlpha((int) getAnimationValue(currentOffset, 64, 255));
            imageContainer.setForeground(overlayColorDrawable);
        }
    }

    private void setMaxOffset() {
        maxOffset = (image.getHeight() - getMinimumHeight()) * -1;
    }

    private float getAnimationValue(float offset, float from, float to) {
        return intervalMap(offset, 0, maxOffset, from, to);
    }

    private float intervalMap(float value, float a, float b, float c, float d) {
        return (value - a) * (d - c) / (b - a) + c;
    }
}
