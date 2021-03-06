package com.coolrandy.com.opengldemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by admin on 2016/3/11.
 */
public class ProxyActivity extends Activity {

    private static final String TAG = "ProxyActivity";
    public static final String FROM = "extra.from";
    public static final int FROM_EXTERNAL = 0;
    public static final int FROM_INTERNAL = 1;

    public static final String EXTRA_DEX_PATH = "extra.dex.path";
    public static final String EXTRA_CLASS = "extra.class";

    private String mClass;
    private String mDexPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDexPath = getIntent().getStringExtra(EXTRA_DEX_PATH);
        mClass = getIntent().getStringExtra(EXTRA_CLASS);

        Log.e("TAG", "mDexPath= " + mDexPath + ", mClass= " + mClass);//mDexPath= /mnt/sdcard/DynamicLoadHost/com.mtime-1.apk, mClass= null

        if(null == mClass){
            launchTargetActivity();
        }else {
            launchTargetActivity(mClass);
        }
    }

    @SuppressLint("NewApi")
    public void launchTargetActivity(){

        PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(mDexPath, 1);
        Log.e("TAG", "packageInfo= " + packageInfo);
        if (packageInfo.activities != null && packageInfo.activities.length > 0){

            String activityName = packageInfo.activities[0].name;
            mClass = activityName;
            launchTargetActivity(mClass);
        }

    }
    @SuppressLint("NewApi")
    public void launchTargetActivity(final String className){

        Log.e("TAG", "start launchTargetActivity, className=" + className);
        File dexOutputDir = this.getDir("dex", 0);
        final String dexOutputPath = dexOutputDir.getAbsolutePath();
        Log.e("TAG", "dexOutputPath= " + dexOutputPath); //dexOutputPath= /data/data/com.coolrandy.com.opengldemo/app_dex
        ClassLoader localClassLoader = ClassLoader.getSystemClassLoader();
        DexClassLoader dexClassLoader = new DexClassLoader(mDexPath, dexOutputPath, null, localClassLoader);

        try {
            //采用dexClassLoader去加载apk，如果没有指定class，就调起主activity，否则调起指定的class
            Class<?> localClass = dexClassLoader.loadClass(className);
            Constructor<?> localConstructor = localClass.getConstructor(new Class[]{});
            Object instance = localConstructor.newInstance(new Object[]{});
            Log.e("TAG", "instance= " + instance);
            Method setProxy = localClass.getMethod("setProxy", new Class[] {Activity.class});//java.lang.NoSuchMethodException: setProxy [class android.app.Activity]
            setProxy.setAccessible(true);
            setProxy.invoke(instance, new Object[] { this });

            Method onCreate = localClass.getDeclaredMethod("onCreate",
                    new Class[]{Bundle.class });
            onCreate.setAccessible(true);
            Bundle bundle = new Bundle();
            bundle.putInt(FROM, FROM_EXTERNAL);
            onCreate.invoke(instance, new Object[] { bundle });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
