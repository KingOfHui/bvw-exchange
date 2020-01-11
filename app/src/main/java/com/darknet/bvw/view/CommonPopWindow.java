//package com.darknet.bvw.view;
//
//import android.util.TypedValue;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.PopupWindow;
//
//public class CommonPopWindow extends PopupWindow {
//
//    public static final int INDEX = 3;
//    public static final int MESSAGE = 2;
//    public static final int SHARE = 1;
//    public static final int FEEDBACK = 4;
//    public static final int REPORT = 5;
//
//    public View mTargeView;
//
//    int xoff;
//    OnSeletedListener onSeletedListener;
//
////    @BindView(R.id.service_popwindow_index)
////    TextView tvIndex;
//
//
//    public CommonPopWindow(View view, int width) {
//        super(view, width, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        this.xoff = (int) (width - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 37,
//                view.getContext().getResources().getDisplayMetrics()));
//        this.setTouchable(true);
//        this.setOutsideTouchable(true);
//        this.setBackgroundDrawable(new ColorDrawable(0x00000000));
//        this.getContentView().setFocusable(true);
//    }
//
//    public void show() {
//        if (mTargeView == null) {
//            return;
//        }
//        tvIndex.setVisibility(View.GONE);
//        layoutMessage.setVisibility(View.GONE);
//        tvShareView.setVisibility(View.GONE);
//        tvFeedbackView.setVisibility(View.GONE);
//        tvReportView.setVisibility(View.GONE);
//        for (int type : types) {
//            switch (type) {
//                case INDEX:
//                    tvIndex.setVisibility(View.VISIBLE);
//                    break;
//                case MESSAGE:
//                    layoutMessage.setVisibility(View.VISIBLE);
//                    if (UserCache.getInstance().getUser() != null) {
//                        updateMsgTotalNum(UnreadMessageNumCache.getInstance().getUnreadImNumCount()
//                                + UnreadMessageNumCache.getInstance().getUnreadMsgCount());
//
//                    }
//                    break;
//                case SHARE:
//                    tvShareView.setVisibility(View.VISIBLE);
//                    break;
//                case FEEDBACK:
//                    tvFeedbackView.setVisibility(View.VISIBLE);
//                case REPORT:
//                    tvReportView.setVisibility(View.VISIBLE);
//                    break;
//            }
//        }
//        try {
//            showAsDropDown(mTargeView, -xoff, 0);
//        } catch (Exception e) {
//        }
//    }
//
//    @OnClick(R.id.service_popwindow_message)
//    void onMessage() {
//        ZbjClickManager.getInstance().insertNormalLog(new ClickElement("more", "消息"));
//        dismiss();
//        onSeletedListener.onMessage();
//    }
//
//
//    @OnClick(R.id.service_popwindow_index)
//    void onIndex() {
//        ZbjClickManager.getInstance().insertNormalLog(new ClickElement("more", "首页"));
//        dismiss();
//        onSeletedListener.onIndex();
//    }
//
//    @OnClick(R.id.service_popwindow_share)
//    void onShare() {
//        ZbjClickManager.getInstance().insertNormalLog(new ClickElement("more", "分享"));
//        dismiss();
//        onSeletedListener.onShare();
//    }
//
//    @OnClick(R.id.service_popwindow_feedback)
//    void onFeedBack() {
//        ZbjClickManager.getInstance().insertNormalLog(new ClickElement("more", "意见反馈"));
//        dismiss();
//        onSeletedListener.onFeedBack();
//    }
//
//    @OnClick(R.id.service_popwindow_jubao)
//    void onReposrt() {
//        ZbjClickManager.getInstance().insertNormalLog(new ClickElement("more", "举报"));
//        dismiss();
//        onSeletedListener.onReport();
//    }
//
//    public void setOnSeletedListener(OnSeletedListener onSeletedListener) {
//        this.onSeletedListener = onSeletedListener;
//    }
//
//
//
//    public interface OnSeletedListener {
//
//        void oneView();
//
//        void twoView();
//
//        void threeView();
//    }
//
//
//    /**
//     * 请求接口
//     */
//    public CommonPopWindow addShow(int type) {
//        types.add(type);
//        return this;
//    }
//
//    public CommonPopWindow addTargeView(View targeView) {
//        mTargeView = targeView;
//        return this;
//    }
//
//    public View getTargeView() {
//        return mTargeView;
//    }
//
//
////    /**
////     * 隐藏/显示首页 <BR/>
////     * true：显示 <BR/>
////     * false：隐藏
////     */
////    public void showIndex(boolean isShow) {
////        if (isShow) {
////            tvIndex.setVisibility(View.VISIBLE);
////        } else {
////            tvIndex.setVisibility(View.GONE);
////        }
////    }
//
//
//
//
//
//
//}
