package com.darknet.bvw.util;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

public abstract class NoUnderlineClickSpan extends ClickableSpan {
    @Override
    public abstract void onClick(@NonNull View widget);

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }
}
