package com.darknet.bvw.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.darknet.bvw.R;
import com.darknet.bvw.view.RoundImageView;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .fitCenter()
                .placeholder(R.mipmap.img_default)
                .into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        return new RoundImageView(context);
    }
}