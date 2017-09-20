package com.example.qyh.joe.theme;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

public class ResourceManager {
    private static final String DEF_TYPE_DRAWABLE = "drawable";
    private static final String DEF_TYPE_COLOR = "color";
    private Resources resources;
    private String pluginPackageName;
    private String suffix;

    public ResourceManager(Resources res, String pluginPackageName, String suffix) {
        resources = res;
        this.pluginPackageName = pluginPackageName;

        if (suffix == null) {
            suffix = "";
        }
        this.suffix = suffix;
    }

    public Drawable getDrawableByName(String name) {
        try {
            name = appendSuffix(name);
            return resources.getDrawable(resources.getIdentifier(name, DEF_TYPE_DRAWABLE, pluginPackageName));
        } catch (Resources.NotFoundException e) {
            try {
                return resources.getDrawable(resources.getIdentifier(name, DEF_TYPE_COLOR, pluginPackageName));
            } catch (Resources.NotFoundException e2) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public int getColor(String name) {
        try {
            name = appendSuffix(name);
            return resources.getColor(resources.getIdentifier(name, DEF_TYPE_COLOR, pluginPackageName));

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ColorStateList getColorStateList(String name) {
        try {
            name = appendSuffix(name);
            return resources.getColorStateList(resources.getIdentifier(name, DEF_TYPE_COLOR, pluginPackageName));

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String appendSuffix(String name) {
        if (!TextUtils.isEmpty(suffix))
            return name += "_" + suffix;
        return name;
    }
}
