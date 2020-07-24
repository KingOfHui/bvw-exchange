package com.cq.library.utils.member;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/5/27 17:03
 * <br>desc: 用于Activity传参数跳转
 */
public class IntentHelper {

	private static final int MIN_REQUEST_CODE = 64000;
	private static final int MAX_REQUEST_CODE = 65000;

	private static int REQUEST_CODE = MIN_REQUEST_CODE;
	public static final int defaultAnim = -1;

	private static int inAnim = defaultAnim;
	private static int outAnim = defaultAnim;

	private static Handler mHandler = new Handler(Looper.getMainLooper());

	private IntentHelper(){}

	public static void init(int inAnim, int outAnim){
		IntentHelper.inAnim = inAnim;
		IntentHelper.inAnim = outAnim;
	}

	public static void startActivityForResult(@NonNull final FragmentActivity activity
			, @NonNull final Intent intent, @NonNull final ActivityResult result){
		final ActivityMemberVarExtSupportFragment fragment = ActExtMemberVarHelper.getSupportFragment(activity);
		if(!fragment.isAdded()){
			mHandler.post(() -> startActivityForResultImpl(activity, intent, getRequestCode(), result, fragment));
		}else {
			startActivityForResultImpl(activity, intent, getRequestCode(), result, fragment);
		}
	}

	public static void startActivityForResult(@NonNull final FragmentActivity activity
			, @NonNull final Intent intent, final int requestCode, @NonNull final ActivityResult result){
		final ActivityMemberVarExtSupportFragment fragment = ActExtMemberVarHelper.getSupportFragment(activity);
		if(!fragment.isAdded()){
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					startActivityForResultImpl(activity, intent, requestCode, result, fragment);
				}
			});
		}else {
			startActivityForResultImpl(activity, intent, requestCode, result, fragment);
		}
	}

	private static int getRequestCode(){
		REQUEST_CODE++;
		if(REQUEST_CODE == MAX_REQUEST_CODE){
			REQUEST_CODE = MIN_REQUEST_CODE;
		}
		return REQUEST_CODE;
	}

	private static void startActivityForResultImpl(@NonNull FragmentActivity activity, @NonNull Intent intent, int requestCode, @NonNull ActivityResult result, ActivityMemberVarExtSupportFragment fragment) {
		fragment.saveActivityResult(requestCode, result);
		fragment.startActivityForResult(intent, requestCode);
		if(inAnim != defaultAnim && outAnim != defaultAnim) {
			activity.overridePendingTransition(inAnim, outAnim);
		}
	}
}
