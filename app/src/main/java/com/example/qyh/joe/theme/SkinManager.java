package com.example.qyh.joe.theme;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.qyh.joe.theme.attr.SkinView;
import com.example.qyh.joe.theme.callback.ISkinChangedListener;
import com.example.qyh.joe.theme.callback.ISkinChangingCallback;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SkinManager {
    private Context context;
    private Resources resources;
    private ResourceManager resourceManager;
    private PrefUtils prefUtils;

    private boolean usePlugin;
    /**
     * 换肤资源后缀
     */
    private String suffix = "";
    private String curPluginPath;
    private String curPluginPkg;


    private Map<ISkinChangedListener, List<SkinView>> iSkinChangedListenerListHashMap = new HashMap<>();
    private List<ISkinChangedListener> iSkinChangedListenerArrayList = new ArrayList<>();

    private SkinManager() {
    }

    private static class SingletonHolder {
        static SkinManager sInstance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return SingletonHolder.sInstance;
    }


    public void init(Context context) {
        this.context = context.getApplicationContext();
        prefUtils = new PrefUtils(this.context);

        String skinPluginPath = prefUtils.getPluginPath();
        String skinPluginPkg = prefUtils.getPluginPkgName();
        suffix = prefUtils.getSuffix();
        if (TextUtils.isEmpty(skinPluginPath))
            return;
        File file = new File(skinPluginPath);
        if (!file.exists()) return;
        try {
            loadPlugin(skinPluginPath, skinPluginPkg, suffix);
            curPluginPath = skinPluginPath;
            curPluginPkg = skinPluginPkg;
        } catch (Exception e) {
            prefUtils.clear();
            e.printStackTrace();
        }
    }


    private void loadPlugin(String skinPath, String skinPkgName, String suffix) throws Exception {
        //checkPluginParams(skinPath, skinPkgName);
        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
        addAssetPath.invoke(assetManager, skinPath);

        Resources superRes = context.getResources();
        resources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        resourceManager = new ResourceManager(resources, skinPkgName, suffix);
        usePlugin = true;
    }

    private boolean checkPluginParams(String skinPath, String skinPkgName) {
        return (!(TextUtils.isEmpty(skinPath) || TextUtils.isEmpty(skinPkgName)));
    }

    private void checkPluginParamsThrow(String skinPath, String skinPkgName) {
        if (!checkPluginParams(skinPath, skinPkgName)) {
            throw new IllegalArgumentException("skinPluginPath or skinPkgName can not be empty ! ");
        }
    }

    public void removeAnySkin() {
        clearPluginInfo();
        notifyChangedListeners();
    }

    public boolean needChangeSkin() {
        return usePlugin || !TextUtils.isEmpty(suffix);
    }

    public ResourceManager getResourceManager() {
        if (!usePlugin) {
            resourceManager = new ResourceManager(context.getResources(), context.getPackageName(), suffix);
        }
        return resourceManager;
    }

    /**
     * 应用内换肤，传入资源区别的后缀
     *
     * @param suffix
     */
    public void changeSkin(String suffix) {
        clearPluginInfo();//clear before
        this.suffix = suffix;
        prefUtils.putPluginSuffix(suffix);
        notifyChangedListeners();
    }

    private void clearPluginInfo() {
        curPluginPath = null;
        curPluginPkg = null;
        usePlugin = false;
        suffix = null;
        prefUtils.clear();
    }

    private void updatePluginInfo(String skinPluginPath, String pkgName, String suffix) {
        prefUtils.putPluginPath(skinPluginPath);
        prefUtils.putPluginPkg(pkgName);
        prefUtils.putPluginSuffix(suffix);
        curPluginPkg = pkgName;
        curPluginPath = skinPluginPath;
        this.suffix = suffix;
    }

    public void changeSkin(final String skinPluginPath, final String pkgName, ISkinChangingCallback callback) {
        changeSkin(skinPluginPath, pkgName, "", callback);
    }

    /**
     * 根据suffix选择插件内某套皮肤，默认为""
     *
     * @param skinPluginPath
     * @param pkgName
     * @param suffix
     * @param callback
     */
    public void changeSkin(final String skinPluginPath, final String pkgName, final String suffix, ISkinChangingCallback callback) {
        if (callback == null)
            callback = ISkinChangingCallback.DEFAULT_SKIN_CHANGING_CALLBACK;
        final ISkinChangingCallback skinChangingCallback = callback;

        skinChangingCallback.onStart();
        checkPluginParamsThrow(skinPluginPath, pkgName);

        if (skinPluginPath.equals(curPluginPath) && pkgName.equals(curPluginPkg)) {
            return;
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    loadPlugin(skinPluginPath, pkgName, suffix);
                } catch (Exception e) {
                    e.printStackTrace();
                    skinChangingCallback.onError(e);
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                try {
                    updatePluginInfo(skinPluginPath, pkgName, suffix);
                    notifyChangedListeners();
                    skinChangingCallback.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    skinChangingCallback.onError(e);
                }

            }
        }.execute();
    }

    public void addSkinView(ISkinChangedListener listener, List<SkinView> skinViews) {
        iSkinChangedListenerListHashMap.put(listener, skinViews);
    }

    public List<SkinView> getSkinViews(ISkinChangedListener listener) {
        return iSkinChangedListenerListHashMap.get(listener);
    }

    public void apply(ISkinChangedListener listener) {
        List<SkinView> skinViews = getSkinViews(listener);

        if (skinViews == null) return;
        for (SkinView skinView : skinViews) {
            skinView.apply();
        }
    }

    public void addChangedListener(ISkinChangedListener listener) {
        iSkinChangedListenerArrayList.add(listener);
    }

    public void removeChangedListener(ISkinChangedListener listener) {
        iSkinChangedListenerArrayList.remove(listener);
        iSkinChangedListenerListHashMap.remove(listener);
    }

    public void notifyChangedListeners() {
        for (ISkinChangedListener listener : iSkinChangedListenerArrayList) {
            listener.onSkinChanged();
        }
    }
}
