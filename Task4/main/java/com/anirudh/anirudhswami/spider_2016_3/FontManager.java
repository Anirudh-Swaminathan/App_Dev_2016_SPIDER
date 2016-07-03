package com.anirudh.anirudhswami.spider_2016_3;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Anirudh Swami on 03-07-2016.
 */
public class FontManager {
    public static final String ROOT = "font-awesome-4.6.3/fonts/",
            FONTAWESOME = ROOT + "fontawesome-webfont.ttf";

    public static Typeface getTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), FONTAWESOME);
    }
}
