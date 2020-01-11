package com.darknet.bvw.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

public class FontUtils {

    public Typeface getLocalTypeFace(Context context) {
        //从asset 读取字体
        AssetManager mgr = context.getAssets();
        //根据路径得到Typeface
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/fangsong.");//仿宋
        return tf;
    }
}
