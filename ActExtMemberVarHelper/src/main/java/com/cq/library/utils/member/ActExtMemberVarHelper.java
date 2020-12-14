package com.cq.library.utils.member;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;

/**
 * createBy Eshel
 * createTime: 2019/5/27 17:15
 * desc: 为Activity添加额外成员变量使用
 */
public class ActExtMemberVarHelper {

	private static final ActExtMemberVarHelperImpl impl = new ActExtMemberVarHelperImpl();
	private static final ActExtMemberVarSupportHelperImpl supportImpl = new ActExtMemberVarSupportHelperImpl();

	public static void clear(Activity activity, String key){
		if(activity instanceof FragmentActivity){
			supportImpl.clear((FragmentActivity) activity, key);
			return;
		}
		impl.clear(activity, key);
	}

	public static void set(Activity activity, String key, Object value){
		if(activity instanceof FragmentActivity){
			supportImpl.set((FragmentActivity) activity, key, value);
			return;
		}
		impl.set(activity, key, value);
	}

	public static <T> T get(Activity activity, String key, Class<T> type){
		if(activity instanceof FragmentActivity){
			return supportImpl.get((FragmentActivity) activity, key, type);
		}
		return impl.get(activity, key, type);
	}

	static ActivityMemberVarExtSupportFragment getSupportFragment(FragmentActivity activity){
		return supportImpl.getFragment(activity);
	}
}
