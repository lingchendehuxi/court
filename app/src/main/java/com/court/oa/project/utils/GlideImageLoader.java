package com.court.oa.project.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by sunguoqing on 2018/8/28.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        //Picasso 加载图片简单用法
        if (path instanceof Drawable) {
            imageView.setImageDrawable((Drawable) path);
        } else {
            Picasso.with(context).load((String) path).into(imageView);
        }
    }
}