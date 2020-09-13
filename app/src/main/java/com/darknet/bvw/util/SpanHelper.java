package com.darknet.bvw.util;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.darknet.bvw.MyApp;


/**
 * createBy dinghui
 * <br>desc: SpannableString 工具
 * <br>使用方法:
 * <pre>CharSequence text =
 * 	SpanHelper.start()
 * 		.next("我是红字")
 * 		.setTextColor(Color.RED)
 * 		.next("赶快点我")
 * 		.setClickListener(new NoUnderlineClickSpan() {
 * 			@ Override
 * 			public void onClick(View widget) {
 * 				Toast.show("hahahaha");
 * 			}
 * 		})
 * 		.next("就我字号大")
 * 		.setTextSize(25)
 * 		.next("我是粗体")
 * 		.setTextStyle(Typeface.BOLD)
 *  .get();
 * <pre>
 */
public class SpanHelper {

    private SpannableStringBuilder sb;
    private SpanHelper(SpannableStringBuilder sb) {
        this.sb = sb;
    }

    /**
     * 根据传入的富文本创建 SpanHelper, 后续拼接操作将在该富文本后进行(保留传入的富文本)
     * @param sb 传入的富文本, 保留其富文本属性
     */
    public static SpanHelper startWith(SpannableStringBuilder sb){
        return new SpanHelper(sb);
    }

    /**
     * 传入一个普通字符串, 保留此字符串(无富文本属性), 并且该字符串在最前面
     * @param text SpanHelper不会处理此字符串
     */
    public static SpanHelper startWith(CharSequence text){
        return new SpanHelper(new SpannableStringBuilder(text));
    }

    public static SpanHelper start(){
        return new SpanHelper(new SpannableStringBuilder(""));
    }

    public SpanHelper just(CharSequence text){
        sb.append(text);
        return this;
    }

    /**
     * 不做特殊处理, 直接拼接至富文本中
     */
    public SpanHelper just(int text){
        sb.append(String.valueOf(text));
        return this;
    }

    public SpanHelper just(char text){
        sb.append(text);
        return this;
    }

    public SpanHelper just(float text){
        sb.append(String.valueOf(text));
        return this;
    }

    /**
     * 创建一个文本拼接对象, 此后对文本设置字号, 颜色等都只会对传入 text 生效
     * @param text 进行字号, 颜色设置的目标文本
     */
    public Builder next(CharSequence text){
        return new Builder(text);
    }

    /**
     * @return 返回拼接好的字符串
     */
    public SpannableStringBuilder get(){
        return sb;
    }

    public class Builder {
        private SpannableString text;
        private Builder(CharSequence text) {
            if(text == null)
                text = "";
            this.text = new SpannableString(text);
        }

        public Builder setTextColor(int color){
            text.setSpan(new ForegroundColorSpan(color), 0,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        public Builder setTextSize(int sizeSp){
            text.setSpan(new AbsoluteSizeSpan(EnvironmentUtil.dp2px(MyApp.getInstance().getApplicationContext(),sizeSp)), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //设置字体大小
            return this;
        }

        public Builder setClickListener(NoUnderlineClickSpan listener){
            text.setSpan(listener,0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return this;
        }

        /**
         * @see android.graphics.Typeface
         * @param textStyle {@link android.graphics.Typeface#BOLD,android.graphics.Typeface#NORMAL,android.graphics.Typeface#ITALIC}
         */
        public Builder setTextStyle(int textStyle){
            text.setSpan(new StyleSpan(textStyle), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //flag 前面后面都不包括的意思;
            return this;
        }

        public Builder setStyle(Style style){
            if(style != null){
                if(style.textColor != Style.DEFAULT_VALUE){
                    setTextColor(style.textColor);
                }
                if(style.textSize != Style.DEFAULT_VALUE){
                    setTextSize(style.textSize);
                }
                if(style.textStyle != Style.DEFAULT_VALUE){
                    setTextStyle(style.textStyle);
                }
            }
            return this;
        }

        /**
         * 设置文本属性完成后, 调用append 方法将newText()的字符串拼接至主串中
         */
        public SpanHelper append(){
            sb.append(text);
            return SpanHelper.this;
        }

        public Builder next(CharSequence text){
            append();
            return SpanHelper.this.next(text);
        }

        /**
         * 不做特殊处理, 直接拼接至富文本中
         */
        public SpanHelper just(CharSequence text){
            append();
            sb.append(text);
            return SpanHelper.this;
        }

        public SpanHelper just(int text){
            append();
            sb.append(String.valueOf(text));
            return SpanHelper.this;
        }

        public SpanHelper just(char text){
            append();
            sb.append(text);
            return SpanHelper.this;
        }

        public SpanHelper just(float text){
            append();
            sb.append(String.valueOf(text));
            return SpanHelper.this;
        }

        public SpannableStringBuilder get(){
            append();
            return sb;
        }
    }

    public static class Style {

        static final int DEFAULT_VALUE = -1;

        int textColor = DEFAULT_VALUE;
        int textSize = DEFAULT_VALUE;
        int textStyle = DEFAULT_VALUE;

        public Style(int textColor, int textSize, int textStyle) {
            this.textColor = textColor;
            this.textSize = textSize;
            this.textStyle = textStyle;
        }

        public Style() {
        }

        public Style setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Style setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Style setTextStyle(int textStyle) {
            this.textStyle = textStyle;
            return this;
        }
    }
}
