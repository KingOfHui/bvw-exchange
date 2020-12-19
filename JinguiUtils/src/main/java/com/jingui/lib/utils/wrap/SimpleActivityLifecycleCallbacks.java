package com.jingui.lib.utils.wrap;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/4/18 22:34
 */
public abstract class SimpleActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

	}

	@Override
	public void onActivityStarted(Activity activity) {

	}

	@Override
	public void onActivityResumed(Activity activity) {

	}

	@Override
	public void onActivityPaused(Activity activity) {

	}

	@Override
	public void onActivityStopped(Activity activity) {

	}

	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

	}

	@Override
	public void onActivityDestroyed(Activity activity) {

	}
}
