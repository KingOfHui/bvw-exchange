package com.darknet.bvw.activity.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BidIntroActivity;
import com.darknet.bvw.activity.BidZhenMaActivity;
import com.darknet.bvw.activity.BidZhenMaTwoActivity;
import com.darknet.bvw.activity.HardwareMineralActivity;
import com.darknet.bvw.activity.ImageActivity;
import com.darknet.bvw.activity.ZhenLieActivity;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.response.BidStateResponse;
import com.darknet.bvw.util.Language;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.util.language.SPUtil;
import com.darknet.bvw.view.BidDialogView;
import com.darknet.bvw.view.BidTwoDialogView;
import com.darknet.bvw.view.ZhenPopWindow;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.darknet.bvw.util.language.LocalManageUtil.getSystemLocale;

public class FindFragment extends Fragment implements View.OnClickListener {

	private ImageView imgSetting;
	private ImageView imgSettingTwo;

	private ImageView bidView;
	private ImageView modelView;

	private ImageView leaderSignView;

	private LinearLayout oneLayout;
	private LinearLayout twoLayout;
	private LinearLayout threeLayout;
	private LinearLayout fourLayout;
	private LinearLayout fiveLayout;
	private LinearLayout sixLayout;


//    private BaseActivity baseActivity;

	//该页面是否准备完毕
	private boolean isPrepared;
	//是否已加载过懒加载
	private boolean isLayLoad;

	private Activity activity;

	//是否开通bid
	private int isOpenSign = 0;

	/**
	 * 是否是毁灭者
	 */
	private boolean isLeader;
	private LinearLayout mFindSevenLayout;
	private LinearLayout mFindEightLayout;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		activity = getActivity();
	}

	public FindFragment() {
	}

//    public FindFragment(BaseActivity bactivity) {
////        super(bactivity);
//        baseActivity = bactivity;
//    }


	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_find, container, false);
		initView(view);
		return view;
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		isPrepared = true;
		lazyLoad();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		lazyLoad();
	}

	//懒加载
	private void lazyLoad() {
		if (getUserVisibleHint() && isPrepared) {
			Log.e("xxxxxxxx", ".....lazyLoad...do...FindFragment....");
			isLeader();
		}
	}


	private void initView(View view) {
		imgSetting = view.findViewById(R.id.imgSetting);
		imgSettingTwo = view.findViewById(R.id.imgSetting_two);
		leaderSignView = view.findViewById(R.id.imgSetting_bid_leader_sign_view);


		bidView = view.findViewById(R.id.find_bid_view);
		modelView = view.findViewById(R.id.find_bid_model_view);
		oneLayout = view.findViewById(R.id.find_one_layout);
//		twoLayout = view.findViewById(R.id.find_two_layout);
		threeLayout = view.findViewById(R.id.find_three_layout);
		fourLayout = view.findViewById(R.id.find_four_layout);
		fiveLayout = view.findViewById(R.id.find_five_layout);
		sixLayout = view.findViewById(R.id.find_six_layout);
		mFindSevenLayout = view.findViewById(R.id.find_seven_layout);
		mFindEightLayout = view.findViewById(R.id.find_eight_layout);

		int lanType = SPUtil.getInstance(activity).getSelectLanguage();
		if (lanType == 1) {
			bidView.setImageResource(R.mipmap.find_item_big_sign);
			modelView.setImageResource(R.mipmap.find_item_model_sign);
		} else if (lanType == 0) {
			bidView.setImageResource(R.mipmap.find_item_big_sign_en);
			modelView.setImageResource(R.mipmap.find_item_model_sign_en);
		} else {
			try {
				String language = getStystemLanguage();
				if ("zh".equals(language)) {
					bidView.setImageResource(R.mipmap.find_item_big_sign);
					modelView.setImageResource(R.mipmap.find_item_model_sign);
				} else if ("en".equals(language)) {
					bidView.setImageResource(R.mipmap.find_item_big_sign_en);
					modelView.setImageResource(R.mipmap.find_item_model_sign_en);
				} else {
					bidView.setImageResource(R.mipmap.find_item_big_sign_en);
					modelView.setImageResource(R.mipmap.find_item_model_sign_en);
				}
			} catch (Exception e) {
				e.printStackTrace();
				bidView.setImageResource(R.mipmap.find_item_big_sign_en);
				modelView.setImageResource(R.mipmap.find_item_model_sign_en);
			}
		}
		bidView.setOnClickListener(this);
		imgSetting.setOnClickListener(this);

		oneLayout.setOnClickListener(this);
//		twoLayout.setOnClickListener(this);
		threeLayout.setOnClickListener(this);
		fourLayout.setOnClickListener(this);
		fiveLayout.setOnClickListener(this);
		sixLayout.setOnClickListener(this);
		modelView.setOnClickListener(this);
		mFindSevenLayout.setOnClickListener(this);
		mFindEightLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.find_bid_view:
				Intent bidInfo = new Intent(activity, BidIntroActivity.class);
				startActivity(bidInfo);
				break;
			case R.id.imgSetting:
//                showPopWindow();
//                showPop();
				showPopWindow();
				break;
			case R.id.find_one_layout:
				//硬件矿机
				/*switch (Language.readFromConfig()) {
					case CHINA:
						ImageActivity.start(getContext(), R.mipmap.img_bif_cn);
						break;
					case ENGLISH:
						ImageActivity.start(getContext(), R.mipmap.img_bif_en);
						break;
				}*/
				startActivity(new Intent(activity, HardwareMineralActivity.class));


//                if (isOpenSign == 1) {
//                    //开通了bid
//                  Intent fenlieIntent =  new Intent(activity, FindFenLieActivity.class);
//                  startActivity(fenlieIntent);
//                } else {
//                    //没有开通bid
//                    new BidDialogView().showTips(activity, getString(R.string.find_invest_notice));
//                }


//                Toast.makeText(activity, getString(R.string.find_no_open), Toast.LENGTH_SHORT).show();
				break;
			/*case R.id.find_two_layout:
				Toast.makeText(activity, getString(R.string.find_no_open), Toast.LENGTH_SHORT).show();
				break;*/
			case R.id.find_three_layout:
				Toast.makeText(activity, getString(R.string.find_no_open), Toast.LENGTH_SHORT).show();
				break;
			case R.id.find_four_layout:
				Toast.makeText(activity, getString(R.string.find_no_open), Toast.LENGTH_SHORT).show();
				break;
			case R.id.find_five_layout:
				Toast.makeText(activity, getString(R.string.find_no_open), Toast.LENGTH_SHORT).show();
				break;
			case R.id.find_six_layout:
			case R.id.find_seven_layout:
			case R.id.find_eight_layout:
				Toast.makeText(activity, getString(R.string.find_no_open), Toast.LENGTH_SHORT).show();
				break;
			case R.id.find_bid_model_view:
				Toast.makeText(activity, getString(R.string.find_no_open), Toast.LENGTH_SHORT).show();
				break;
		}
	}


	private void showPop() {
		ZhenPopWindow zhenPopWindow = new ZhenPopWindow(activity, new ZhenPopWindow.ClickListener() {
			@Override
			public void deleteListener() {

			}

			@Override
			public void changeListener() {

			}
		});

	}


	//获取bid状态
	private void getPublicAddressTwo() {

		ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

		String privateKey = walletModel.getPrivateKey();
		String addressVals = walletModel.getAddress();

		String msg = "" + System.currentTimeMillis();
		String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

//        showDialog(getString(R.string.load_data));

		OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.FIND_BID_STATE_URL)
				.tag(activity)
				.headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
				.execute(new StringCallback() {
					@Override
					public void onSuccess(Response<String> backresponse) {
						if (backresponse != null) {
							String backVal = backresponse.body();
							if (backVal != null) {
								Gson gson = new Gson();
								try {
									BidStateResponse response = gson.fromJson(backVal, BidStateResponse.class);
									if (response != null && response.getCode() == 0) {
										if (response.getData() != null) {

											showStateTwo(response.getData());
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}

					@Override
					public void onFinish() {
						super.onFinish();
//                        dismissDialog();
					}
				});
	}

	//获取bid状态
	private void getPublicAddress() {

		ETHWalletModel walletModel = WalletDaoUtils.getCurrent();

		String privateKey = walletModel.getPrivateKey();
		String addressVals = walletModel.getAddress();

		String msg = "" + System.currentTimeMillis();
		String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

//        showDialog(getString(R.string.load_data));

		OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.FIND_BID_STATE_URL)
				.tag(activity)
				.headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
				.execute(new StringCallback() {
					@Override
					public void onSuccess(Response<String> backresponse) {
						if (backresponse != null) {
							String backVal = backresponse.body();
							if (backVal != null) {
								Gson gson = new Gson();
								try {
									BidStateResponse response = gson.fromJson(backVal, BidStateResponse.class);
									if (response != null && response.getCode() == 0) {
										if (response.getData() != null) {

											showState(response.getData());
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}

					@Override
					public void onFinish() {
						super.onFinish();
//                        dismissDialog();
					}
				});
	}


	private void showStateTwo(BidStateResponse.BidStateModel bidStateModel) {

		if (bidStateModel.getStatus() == 0 || bidStateModel.getStatus() == 2) {
			isOpenSign = 0;
			//未开通
			stateLidType = 0;
			stateRidType = 0;
			changeNoOpenState();

		} else if (bidStateModel.getStatus() == 2) {
			isOpenSign = 0;
			//未开通
			stateLidType = 0;
			stateRidType = 0;
			changeOpeningState();
		} else if (bidStateModel.getStatus() == 1) {
			isOpenSign = 1;

			changeOpenState();
			//已开通
			if (bidStateModel.getLid() != null && bidStateModel.getLid().trim().length() > 0) {
				stateLidType = 1;
				lid = bidStateModel.getLid();
			} else {
				stateLidType = 3;
			}
			if (bidStateModel.getRid() != null && bidStateModel.getRid().trim().length() > 0) {
				stateRidType = 2;
				rid = bidStateModel.getRid();
			} else {
				stateRidType = 4;
			}
		}

	}


	private void changeOpenState() {
		if (isLeader) {
			return;
		}
		imgSettingTwo.setVisibility(View.VISIBLE);
		int type = SPUtil.getInstance(activity).getSelectLanguage();
		if (type == 1) {
			imgSettingTwo.setImageResource(R.mipmap.bid_open_state_sign_img);
		} else {
			imgSettingTwo.setImageResource(R.mipmap.bid_open_state_sign_img_en);
		}
	}


	private void changeOpeningState() {
		if (isLeader) {
			return;
		}
		imgSettingTwo.setVisibility(View.VISIBLE);
		int type = SPUtil.getInstance(activity).getSelectLanguage();
		if (type == 1) {
			imgSettingTwo.setImageResource(R.mipmap.bid_opening_state_sign_img);
		} else {
			imgSettingTwo.setImageResource(R.mipmap.bid_opening_state_sign_img_en);
		}
	}


	private void changeNoOpenState() {
		if (isLeader) {
			return;
		}
		imgSettingTwo.setVisibility(View.VISIBLE);
		int type = SPUtil.getInstance(activity).getSelectLanguage();
		if (type == 1) {
			imgSettingTwo.setImageResource(R.mipmap.bid_noopen_state_sign_img);
		} else {
			imgSettingTwo.setImageResource(R.mipmap.bid_noopen_state_sign_img_en);
		}
	}


	private String lid;
	private String rid;

	// 0 未开通，1开通有梯阵码，2 有像阵码,3无梯阵码，4,无像阵码
	private int stateLidType = 0;
	private int stateRidType = 0;

	private void showState(BidStateResponse.BidStateModel bidStateModel) {

		if (bidStateModel.getStatus() == 0 || bidStateModel.getStatus() == 2) {
			//未开通
			stateLidType = 0;
			stateRidType = 0;
			changeNoOpenState();
		} else if (bidStateModel.getStatus() == 1) {
			changeOpenState();
			//已开通
			if (bidStateModel.getLid() != null && bidStateModel.getLid().trim().length() > 0) {
				stateLidType = 1;
				lid = bidStateModel.getLid();
			} else {
				stateLidType = 3;
			}
			if (bidStateModel.getRid() != null && bidStateModel.getRid().trim().length() > 0) {
				stateRidType = 2;
				rid = bidStateModel.getRid();
			} else {
				stateRidType = 4;
			}
		}
	}


	PopupWindow window;

	private void showPopWindow() {
		View inflate = LayoutInflater.from(getContext()).inflate(R.layout.pop_select_layout, null);
		window = new PopupWindow(inflate, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);

		LinearLayout tizhenLayout = (LinearLayout) inflate.findViewById(R.id.tizhen_layout);
		LinearLayout xiangzhenLayout = (LinearLayout) inflate.findViewById(R.id.xiangzhen_layout);
		LinearLayout liezhenLayout = (LinearLayout) inflate.findViewById(R.id.liezhen_layout);


//        window.setAnimationStyle(R.style.pop_shop_anim);
		// 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        window.setBackgroundDrawable(dw);
		window.setOutsideTouchable(true);
//        showUp(imgSetting);
//        window.update();
		window.showAsDropDown(imgSetting, 50, 0);

		tizhenLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (stateLidType == 1) {
					Intent tiIntent = new Intent(activity, BidZhenMaActivity.class);
					tiIntent.putExtra("lid", lid);
					startActivity(tiIntent);
					window.dismiss();

				} else {
					new BidDialogView().showTips(activity, getString(R.string.find_invest_notice));
					window.dismiss();
				}

			}
		});

		xiangzhenLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (stateRidType == 0) {
					//
					new BidDialogView().showTips(activity, getString(R.string.find_invest_notice));
					window.dismiss();
				} else if (stateRidType == 4) {
					//邀请
					new BidTwoDialogView().showTips(activity, getString(R.string.find_xiang_notice));
					window.dismiss();
				} else if (stateRidType == 2) {
					Intent xiangIntent = new Intent(activity, BidZhenMaTwoActivity.class);
					xiangIntent.putExtra("rid", rid);
					startActivity(xiangIntent);
					window.dismiss();
				}

//                new BidDialogView().showTips(baseActivity, getString(R.string.find_xiang_notice));
//                return;

			}
		});


		liezhenLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (stateLidType == 1) {
//                    Intent lieDuiIntent = new Intent(activity, WebLieDuiActivity.class);
//                    startActivity(lieDuiIntent);

					Intent zhenIntent = new Intent(activity, ZhenLieActivity.class);
					startActivity(zhenIntent);

					window.dismiss();

				} else {
					new BidDialogView().showTips(activity, getString(R.string.find_invest_notice));
					window.dismiss();
				}


			}
		});

	}


	private void showUp(View v) {
		v.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		int measuredHeight = v.getMeasuredHeight();
		int[] location = new int[2];
		imgSetting.getLocationOnScreen(location);
//        window.showAsDropDown(imgSetting);
		window.showAtLocation(imgSetting, Gravity.NO_GRAVITY, 0, location[1] - measuredHeight);
	}


	public String getStystemLanguage() {
		String language = getSystemLocale(activity).getLanguage();
		return language;
	}

	/**
	 * 是否是领导
	 */
	private void isLeader() {
		ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
		String privateKey = walletModel.getPrivateKey();
		String addressVals = walletModel.getAddress();
		String msg = "" + System.currentTimeMillis();
		String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);

		OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.BVW_STATE_URL)
				.tag(this)
				.headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
				.execute(new StringCallback() {
					@Override
					public void onSuccess(Response<String> backresponse) {
						Log.d("FindFragment", ConfigNetWork.JAVA_API_URL + UrlPath.BVW_STATE_URL + ":" + backresponse.body());
						try {
							Gson gson = new Gson();
							BaseResponse<String> response = gson.fromJson(backresponse.body(), new TypeToken<BaseResponse<String>>() {
							}.getType());
							if (response.getCode() == 0) {
								String data = response.getData();
								if ("true".equals(data)) {
									isLeader = true;
									leaderSignView.setVisibility(View.VISIBLE);
									imgSettingTwo.setVisibility(View.GONE);
									int lanType = SPUtil.getInstance(activity).getSelectLanguage();
									if (lanType == 1) {
										leaderSignView.setImageResource(R.mipmap.resurrection_cn);
									} else {
										leaderSignView.setImageResource(R.mipmap.resurrection);
									}
								} else {
									isLeader = false;
									leaderSignView.setVisibility(View.GONE);
								}
								getPublicAddressTwo();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onError(Response<String> response) {
					}
				});
	}

}
