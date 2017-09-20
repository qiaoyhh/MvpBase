package com.example.qyh.joe.theme.attr;

import android.content.Context;
import android.util.AttributeSet;

import com.example.qyh.joe.theme.SkinConfig;

import java.util.ArrayList;
import java.util.List;


public class SkinAttrSupport {
    public static List<SkinAttr> getSkinAttrs(AttributeSet attrs, Context context) {
        List<SkinAttr> skinAttrs = new ArrayList<>();
        SkinAttr skinAttr;
        for (int index = 0; index < attrs.getAttributeCount(); index++) {
            String attrName = attrs.getAttributeName(index);
            String attrValue = attrs.getAttributeValue(index);

            SkinAttrType attrType = getSupprotAttrType(attrName);
            if (attrType == null) continue;

            if (attrValue.startsWith("@")) {
                int id = Integer.parseInt(attrValue.substring(1));
                String entryName = context.getResources().getResourceEntryName(id);

                //  L.e("entryName = " + entryName);
                if (entryName.startsWith(SkinConfig.ATTR_PREFIX)) {
                    skinAttr = new SkinAttr(attrType, entryName);
                    skinAttrs.add(skinAttr);
                }
            }
        }
        return skinAttrs;
    }

    private static SkinAttrType getSupprotAttrType(String attrName) {
        for (SkinAttrType attrType : SkinAttrType.values()) {
            if (attrType.getAttrType().equals(attrName))
                return attrType;
        }
        return null;
    }
}
