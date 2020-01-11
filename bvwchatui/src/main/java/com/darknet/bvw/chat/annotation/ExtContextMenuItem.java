package com.darknet.bvw.chat.annotation;

import androidx.annotation.StringRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 那么直接响应；如果有多个，则弹出菜单，让进一步选择
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtContextMenuItem {
    String title() default "";

    @StringRes int titleResId() default 0;
}
