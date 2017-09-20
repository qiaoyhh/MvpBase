package com.example.qyh.joe.theme.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qyh.joe.theme.ResourceManager;
import com.example.qyh.joe.theme.SkinManager;


public enum SkinAttrType {
    BACKGROUND("background") {
        @Override
        public void apply(View view, String resName) {
            Drawable drawable = getResourceManager().getDrawableByName(resName);
            if (drawable == null) return;
            view.setBackgroundDrawable(drawable);
        }
    }, COLOR("textColor") {
        @Override
        public void apply(View view, String resName) {
            ColorStateList colorStateList = getResourceManager().getColorStateList(resName);
            if (colorStateList == null) return;
            ((TextView) view).setTextColor(colorStateList);
        }
    }, SRC("src") {
        @Override
        public void apply(View view, String resName) {
            if (view instanceof ImageView) {
                Drawable drawable = getResourceManager().getDrawableByName(resName);
                if (drawable == null) return;
                ((ImageView) view).setImageDrawable(drawable);
            }

        }
    };

    String attrType;

    SkinAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getAttrType() {
        return attrType;
    }


    public abstract void apply(View view, String resName);

    public ResourceManager getResourceManager() {
        return SkinManager.getInstance().getResourceManager();
    }
}
