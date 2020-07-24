package com.cq.library.utils.member;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * createBy Eshel
 * createTime: 2019/5/27 17:08
 * desc: Activity 成员变量扩展 Fragment
 */
public class ActivityMemberVarExtSupportFragment extends Fragment {

	private final Map<String, Object> memberVars;
	private final SparseArray<ActivityResult> activityResultCache;

	public ActivityMemberVarExtSupportFragment() {
		memberVars = new HashMap<>(8);
		activityResultCache = new SparseArray<>(8);
	}

	public void put(String key, Object value){
		memberVars.put(key, value);
	}

	public <T> T get(String key, Class<T> type){
		try {
			Object value = memberVars.get(key);
			return (T) value;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public void saveActivityResult(int requestCode, ActivityResult result){
		activityResultCache.put(requestCode, result);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ActivityResult activityResult = activityResultCache.get(requestCode);
		if(activityResult != null){
			activityResultCache.remove(requestCode);
			activityResult.onResult(resultCode, data);
		}
	}
}
