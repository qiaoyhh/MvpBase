package com.example.qyh.joe.theme.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;

import com.example.qyh.joe.theme.SkinManager;
import com.example.qyh.joe.theme.attr.SkinAttr;
import com.example.qyh.joe.theme.attr.SkinAttrSupport;
import com.example.qyh.joe.theme.attr.SkinView;
import com.example.qyh.joe.theme.callback.ISkinChangedListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 15/9/22.
 */
public class BaseSkinActivity extends AppCompatActivity implements ISkinChangedListener, LayoutInflaterFactory {
    static final Class<?>[] constructorSignature = new Class[]{Context.class, AttributeSet.class};
    private static final Map<String, Constructor<? extends View>> constructorMap = new ArrayMap<>();
    private final Object[] constructorArgs = new Object[2];
    private static Method createViewMethod;
    static final Class<?>[] createViewSignature = new Class[]{View.class, String.class, Context.class, AttributeSet.class};

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        LayoutInflater layoutInflater = getLayoutInflater();
        AppCompatDelegate delegate = getDelegate();
        View view = null;
        try {
            if (createViewMethod == null) {
                Method methodOnCreateView = delegate.getClass().getMethod("createView", createViewSignature);
                createViewMethod = methodOnCreateView;
            }
            Object object = createViewMethod.invoke(delegate, parent, name, context, attrs);
            view = (View) object;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        List<SkinAttr> skinAttrList = SkinAttrSupport.getSkinAttrs(attrs, context);
        if (skinAttrList.isEmpty()) {
            return view;
        }
        if (view == null) {
            view = createViewFromTag(context, name, attrs);
        }
        injectSkin(view, skinAttrList);
        return view;
    }

    private void injectSkin(View view, List<SkinAttr> skinAttrList) {
        //do some skin inject
        if (skinAttrList.size() == 0) {
            return;
        }

        List<SkinView> skinViews = SkinManager.getInstance().getSkinViews(this);
        if (skinViews == null) {
            skinViews = new ArrayList<>();
        }
        SkinManager.getInstance().addSkinView(this, skinViews);
        skinViews.add(new SkinView(view, skinAttrList));

        if (SkinManager.getInstance().needChangeSkin()) {
            SkinManager.getInstance().apply(this);
        }
    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }

        try {
            constructorArgs[0] = context;
            constructorArgs[1] = attrs;

            if (-1 == name.indexOf('.')) {
                // try the android.widget prefix first...
                return createView(context, name, "android.widget.");
            } else {
                return createView(context, name, null);
            }
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        } finally {
            // Don't retain references on context.
            constructorArgs[0] = null;
            constructorArgs[1] = null;
        }
    }

    private View createView(Context context, String name, String prefix)
            throws ClassNotFoundException, InflateException {
        Constructor<? extends View> constructor = constructorMap.get(name);

        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                Class<? extends View> clazz = context.getClassLoader().loadClass(
                        prefix != null ? (prefix + name) : name).asSubclass(View.class);

                constructor = clazz.getConstructor(constructorSignature);
                constructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(constructorArgs);
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory(layoutInflater, this);
        super.onCreate(savedInstanceState);
        SkinManager.getInstance().addChangedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().removeChangedListener(this);
    }

    @Override
    public void onSkinChanged() {
        SkinManager.getInstance().apply(this);
    }
}
