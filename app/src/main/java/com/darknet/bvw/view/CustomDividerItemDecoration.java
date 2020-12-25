package com.darknet.bvw.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName CustomDividerItemDecoration
 * @Description 自定义RecyclerView分割线
 * @Author dinghui
 * @Date 2020/12/22 0022 15:45
 */
public class CustomDividerItemDecoration extends RecyclerView.ItemDecoration{

	protected boolean drawStart;//在第一条上份绘制分割线
	protected boolean drawEnd;//在最后一条上份绘制分割线
	protected int color = Color.TRANSPARENT;//分割线颜色, 默认透明色
	protected int mOrientation = Builder.VERTICAL;
	private int paddingStart;
	private int paddingEnd;
	private List<Integer> skips;

	protected Drawable mDivider;
	protected Rect mBounds = new Rect();

	protected CustomDividerItemDecoration() {
		super();
	}

	private void setSpace(final int spacePx) {
		setDrawable(new ColorDrawable(color){
			@Override
			public int getIntrinsicHeight() {
				return spacePx;
			}

			@Override
			public int getIntrinsicWidth() {
				return spacePx;
			}
		});
	}

	public void setDrawable(@NonNull Drawable drawable) {
		mDivider = drawable;
	}

	public void setOrientation(int orientation) {
		mOrientation = orientation;
	}

	@Override
	public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
		//在第一条的前边绘制分割线
		if(mOrientation == Builder.VERTICAL){
			drawVertical(canvas, parent);
		}else {
			drawHorizontal(canvas, parent);
		}
		// 绘制结束
//		super.onDraw(canvas, parent, state);
	}

	private void drawHorizontal(Canvas canvas, RecyclerView parent){
		canvas.save();
		int top;
		int bottom;
		//noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
		if (parent.getClipToPadding()) {
			top = parent.getPaddingTop();
			bottom = parent.getHeight() - parent.getPaddingBottom();
			canvas.clipRect(parent.getPaddingLeft(), top, parent.getWidth() - parent.getPaddingRight(),
					bottom);
		} else {
			top = 0;
			bottom = parent.getHeight();
		}

		top += paddingStart;
		bottom -= paddingEnd;

		final int childCount = parent.getChildCount();

		for (int i = 0; i < childCount; i++) {
			final View child = parent.getChildAt(i);

			if(i == 0 && drawStart){
				parent.getDecoratedBoundsWithMargins(child, mBounds);
				int right = mBounds.right + Math.round(child.getLeft());
				int left = right - mDivider.getIntrinsicWidth();
				mDivider.setBounds(left, top, right, bottom);
				mDivider.draw(canvas);
			}

			if(i != childCount - 1 || drawEnd) {
				RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
				if(layoutManager != null && (skips == null || skips.isEmpty() || !skips.contains(i))) {
					layoutManager.getDecoratedBoundsWithMargins(child, mBounds);
					final int right = mBounds.right + Math.round(child.getTranslationX());
					final int left = right - mDivider.getIntrinsicWidth();
					mDivider.setBounds(left, top, right, bottom);
					mDivider.draw(canvas);
				}
			}
		}
		canvas.restore();
	}

	private void drawVertical(Canvas canvas, RecyclerView parent) {
		canvas.save();
		int left;
		int right;
		//noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
		if (parent.getClipToPadding()) {
			left = parent.getPaddingLeft();
			right = parent.getWidth() - parent.getPaddingRight();
			canvas.clipRect(left, parent.getPaddingTop(), right,
					parent.getHeight() - parent.getPaddingBottom());
		} else {
			left = 0;
			right = parent.getWidth();
		}

		left += paddingStart;
		right -= paddingEnd;

		final int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View child = parent.getChildAt(i);

			if(i == 0 && drawStart){
				parent.getDecoratedBoundsWithMargins(child, mBounds);
				final int bottom = mBounds.top + Math.round(child.getTop());
				final int top = bottom - mDivider.getIntrinsicHeight();
				mDivider.setBounds(left, top, right, bottom);
				mDivider.draw(canvas);
			}

			if((i != childCount - 1 && (skips == null || skips.isEmpty() || !skips.contains(i))) || drawEnd) {
				parent.getDecoratedBoundsWithMargins(child, mBounds);
				final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
				final int top = bottom - mDivider.getIntrinsicHeight();
				mDivider.setBounds(left, top, right, bottom);
				mDivider.draw(canvas);
			}
		}

		canvas.restore();
	}

	@Override
	public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
		super.getItemOffsets(outRect, view, parent, state);
		int nowPosition = parent.getChildAdapterPosition(view);
		if(mDivider == null)
			return;
		if(nowPosition == 0){
			int space;

			if (mOrientation == Builder.VERTICAL) {
				space = drawStart ? mDivider.getIntrinsicHeight() : 0;
				outRect.top = space;
//				outRect.right = space;
//				outRect.set(outRect.left, space, outRect.right, space);
			} else {
				space = drawStart ? mDivider.getIntrinsicWidth() : 0;
				outRect.left = space;
//				outRect.set(space, outRect.top, outRect.right, outRect.bottom);
			}
		}

		if(parent.getAdapter() != null && nowPosition == parent.getAdapter().getItemCount() - 1){
			int space;

			if (mOrientation == Builder.VERTICAL) {
				space = drawEnd ? mDivider.getIntrinsicHeight() : 0;
				outRect.bottom = space;
			} else {
				space = drawEnd ? mDivider.getIntrinsicWidth() : 0;
				outRect.right = space;
			}
		}else {
			if (mOrientation == Builder.VERTICAL) {
				outRect.bottom = mDivider.getIntrinsicHeight();
			} else {
				outRect.right = mDivider.getIntrinsicWidth();
			}
		}
	}

	public static class Builder{

		public static final int VERTICAL = DividerItemDecoration.VERTICAL;
		public static final int HORIZONTAL = DividerItemDecoration.HORIZONTAL;
		private CustomDividerItemDecoration divider;
		private int space;
		private int color;

		private int paddingStart;
		private int paddingEnd;

		private List<Integer> skips;

		public Builder() {
			divider = new CustomDividerItemDecoration();
		}

		public Builder setDrawable(@NonNull Drawable drawable) {
			divider.setDrawable(drawable);
			return this;
		}

		public Builder setSpace(int spacePx){
			space = spacePx;
			return this;
		}

		/**
		 * 设置分割线size
		 * @param spacePx 分割线厚度
		 * @param paddingStartPx 左/上的padding
		 * @param paddingEndPx 右/下的padding
		 *                     orientation 为竖直方向时padding为左右
		 *                     orientation 为水平方向时padding为上下
		 */
		public Builder setSpace(int spacePx, int paddingStartPx, int paddingEndPx){
			space = spacePx;
			this.paddingStart = paddingStartPx;
			this.paddingEnd = paddingEndPx;
			return this;
		}

		public Builder setColor(int color){
			this.color = color;
			return this;
		}

		public Builder setOrientation(int orientation) {
			divider.setOrientation(orientation);
			return this;
		}

		/**
		 * 是否在第一条前边绘制分割线
		 * 默认不绘制
		 */
		public Builder drawStart(){
			divider.drawStart = true;
			return this;
		}

		/**
		 * 是否在最后一条后边绘制分割线
		 * 默认不绘制
		 */
		public Builder drawEnd(){
			divider.drawEnd = true;
			return this;
		}

		public CustomDividerItemDecoration build(){
			divider.color = color;
			divider.setSpace(space);
			divider.paddingStart = paddingStart;
			divider.paddingEnd = paddingEnd;
			divider.skips = skips;
			return divider;
		}

		/**
		 * 跳过 position
		 * 在添加 Header 的情况下, 如果不想在header下绘制分割线, 可以调用 skip(0)
		 * @param positions 要跳过的 position, 可传多个
		 */
		public Builder skip(@NonNull Integer... positions) {
			skips = Arrays.asList(positions);
			return this;
		}
	}
}
