package com.cq.library.utils.member;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2019/11/21 10:48
 * <br>desc: 参考Glide的实现
 */
public class ActExtMemberVarHelperImpl implements Handler.Callback, IActExtMemberVarHelper<Activity> {

	private final Map<FragmentManager, ActivityMemberVarExtFragment> pendingActivityMemberVarExtFragments =
			new HashMap<FragmentManager, ActivityMemberVarExtFragment>();

	private final Handler mHandler = new Handler(Looper.getMainLooper(), this);

	private static final int ID_REMOVE_FRAGMENT_MANAGER = 1;

	public void clear(Activity activity, String key){
		if(activity == null || key == null)
			return;
		getFragment(activity).put(key, null);
	}

	public void set(Activity activity, String key, Object value){
		if(activity == null || key == null)
			return;
		getFragment(activity).put(key, value);
	}

	public <T> T get(Activity activity, String key, Class<T> type){
		if(activity == null || key == null)
			return null;
		return getFragment(activity).get(key, type);
	}

	ActivityMemberVarExtFragment getFragment(Activity activity){
		FragmentManager fragmentManager = activity.getFragmentManager();
		ActivityMemberVarExtFragment fragment = null;

		try {
			fragment = (ActivityMemberVarExtFragment) fragmentManager.findFragmentByTag("TAG_ACT_EXT_MEMBER_VAR");
		}catch (Exception e){
			e.printStackTrace();
		}

		try {
			if(fragment == null){
				fragment = pendingActivityMemberVarExtFragments.get(fragmentManager);
				if(fragment == null) {
					fragment = new ActivityMemberVarExtFragment();
					pendingActivityMemberVarExtFragments.put(fragmentManager, fragment);
					fragmentManager.beginTransaction().add(fragment, "TAG_ACT_EXT_MEMBER_VAR").commitAllowingStateLoss();
					mHandler.obtainMessage(ID_REMOVE_FRAGMENT_MANAGER, fragmentManager).sendToTarget();
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return fragment;
	}

	@Override
	public boolean handleMessage(Message msg) {
		try {
			if (msg.what == ID_REMOVE_FRAGMENT_MANAGER) {
				FragmentManager fm = (FragmentManager) msg.obj;
				ActivityMemberVarExtFragment remove = pendingActivityMemberVarExtFragments.remove(fm);
				return remove != null;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
