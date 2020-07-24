package com.cq.library.utils.member;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2019/11/21 10:48
 * <br>desc: 参考Glide的实现
 */
public class ActExtMemberVarSupportHelperImpl implements Handler.Callback, IActExtMemberVarHelper<FragmentActivity> {

	public static final String TAG_ACT_EXT_MEMBER_VAR = "TAG_ACT_EXT_MEMBER_VAR_SUPPORT";

	private final Map<FragmentManager, ActivityMemberVarExtSupportFragment> pendingActivityMemberVarExtFragments =
			new HashMap<FragmentManager, ActivityMemberVarExtSupportFragment>();

	private final Handler mHandler = new Handler(Looper.getMainLooper(), this);

	private static final int ID_REMOVE_FRAGMENT_MANAGER = 1;

	public void clear(FragmentActivity activity, String key){
		if(activity == null || key == null)
			return;
		getFragment(activity).put(key, null);
	}

	public void set(FragmentActivity activity, String key, Object value){
		if(activity == null || key == null)
			return;
		getFragment(activity).put(key, value);
	}

	public <T> T get(FragmentActivity activity, String key, Class<T> type){
		if(activity == null || key == null)
			return null;
		return getFragment(activity).get(key, type);
	}

	ActivityMemberVarExtSupportFragment getFragment(FragmentActivity activity){
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		ActivityMemberVarExtSupportFragment fragment = null;

		try {
			fragment = (ActivityMemberVarExtSupportFragment) fragmentManager.findFragmentByTag(TAG_ACT_EXT_MEMBER_VAR);
		}catch (Exception e){
			e.printStackTrace();
		}

		try {
			if(fragment == null){
				fragment = pendingActivityMemberVarExtFragments.get(fragmentManager);
				if(fragment == null) {
					fragment = new ActivityMemberVarExtSupportFragment();
					pendingActivityMemberVarExtFragments.put(fragmentManager, fragment);
					fragmentManager.beginTransaction().add(fragment, TAG_ACT_EXT_MEMBER_VAR).commitAllowingStateLoss();
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
				ActivityMemberVarExtSupportFragment remove = pendingActivityMemberVarExtFragments.remove(fm);
				return remove != null;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
