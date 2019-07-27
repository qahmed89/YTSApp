package com.example.firebasecompletproject;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.github.florent37.glidepalette.GlidePalette;

/**
 * Authored by Mohamed Fathy on 08 Mar, 2018.
 * Contact: muhamed.gendy@gmail.com
 */

@SuppressWarnings("unchecked") public class GlideUtil {

    private enum Type {
        ITEM, YOUTUBE, POSTER
    }

    public static void loadImg(Context context, String url, ImageView target) {
        Glide.with(context).load(Uri.parse(url)).into(target);
    }

    public static void loadItemImg(Context context, MoiveAdapter.MyViewHolder holder, String url) {
        Glide.with(context)
                .load(url)
                .listener(getPalette(Type.ITEM, holder, null, null, url))
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);
    }

    public static void loadYouTubeThumb(Context context, CollapsingToolbarLayout toolbar,
                                        ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .listener(getPalette(Type.YOUTUBE, null, toolbar, null, url))
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);
    }

    public static void loadPoster(Context context, CardView card, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .listener(getPalette(Type.POSTER, null, null, card, url))
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);
    }

    private static GlidePalette getPalette(Type type,  MoiveAdapter.MyViewHolder holder,
                                           CollapsingToolbarLayout toolbar, CardView card, String url) {
        GlidePalette glidePalette = GlidePalette.with(url).use(GlidePalette.Profile.VIBRANT);
        if (holder != null) {
            glidePalette.intoBackground(holder.linearLayout, GlidePalette.Swatch.RGB)
                    .intoTextColor(holder.name, GlidePalette.Swatch.BODY_TEXT_COLOR)
                    .intoTextColor(holder.year, GlidePalette.Swatch.BODY_TEXT_COLOR)
                    .intoTextColor(holder.genre, GlidePalette.Swatch.BODY_TEXT_COLOR);
        }
        glidePalette.crossfade(true)
                .intoCallBack(palette -> {
                    if (palette != null) {
                        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                        Palette.Swatch mutedSwatch = palette.getMutedSwatch();
                        if (vibrantSwatch != null) {
                            switch (type) {
                                case ITEM:
                                    if (holder != null) {
                                        holder.cardView.setCardBackgroundColor(vibrantSwatch.getRgb());
                                        if (vibrantSwatch.getRgb() == Color.TRANSPARENT && mutedSwatch != null) {
                                            holder.cardView.setCardBackgroundColor(mutedSwatch.getRgb());
                                        }
                                    }
                                    break;
                                case YOUTUBE:
                                    toolbar.setBackgroundColor(vibrantSwatch.getRgb());
                                    toolbar.setContentScrimColor(vibrantSwatch.getRgb());
                                    if (vibrantSwatch.getRgb() == Color.TRANSPARENT && mutedSwatch != null) {
                                        toolbar.setBackgroundColor(mutedSwatch.getRgb());
                                        toolbar.setContentScrimColor(mutedSwatch.getRgb());
                                    }
                                    break;
                                case POSTER:
                                    card.setCardBackgroundColor(vibrantSwatch.getRgb());
                                    if (vibrantSwatch.getRgb() == Color.TRANSPARENT && mutedSwatch != null) {
                                        card.setCardBackgroundColor(mutedSwatch.getRgb());
                                    }
                                    break;
                            }
                        }
                    }
                });
        return glidePalette;
    }
}

