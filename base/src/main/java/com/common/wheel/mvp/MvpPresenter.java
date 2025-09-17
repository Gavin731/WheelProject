package com.common.wheel.mvp;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import java.lang.ref.WeakReference;

/**
 * @author: zenglinggui
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2018/11/30     zenglinggui       v1.0.0        create
 **/
public abstract class MvpPresenter<V> implements IPresenter<V> {

    protected String TAG="MvpPresenter";

    protected WeakReference<V> view = null;

    abstract public void initView();

    @Override
    public void setView(V view) {
        this.view = new WeakReference<>(view);
    }

    @Nullable
    public V getView() {
        return this.view == null ? null : this.view.get();
    }

    public boolean isViewInit() {
        return this.view != null && this.view.get() != null;
    }


    @Override
    public void onCreate(LifecycleOwner owner) {
        Log.e(TAG, "------------------presenter:onCreate");
    }

    @Override
    public void onStart(LifecycleOwner owner) {
        Log.e(TAG,"------------------presenter:onStart");
    }

    @Override
    public void onResume(LifecycleOwner owner) {
        Log.e(TAG,"------------------presenter:onResume");
    }

    @Override
    public void onPause(LifecycleOwner owner) {
        Log.e(TAG,"------------------presenter:onPause");
    }

    @Override
    public void onStop(LifecycleOwner owner) {
        Log.e(TAG,"------------------presenter:onStop");
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        Log.e(TAG,"------------------presenter:onDestroy");
        if (this.view != null) {
            this.view.clear();
            this.view = null;
        }
    }
}
