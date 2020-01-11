package com.darknet.bvw.chat.annotation;

import androidx.annotation.StringRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 用于注解消息的长按菜单项
 * <p>
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageContextMenuItem {
    String title();

    @StringRes int titleResId() default 0;

    /**
     * 用来唯一表示菜单项
     *
     * @return
     */
    String tag();

    int priority() default 0;

    /**
     * 是否需要二次确认
     *
     * @return
     */
    boolean confirm() default false;

    /**
     * 二次确认的提示，只有当{@link #confirm()}为true时有用
     *
     * @return
     */
    String confirmPrompt() default "";

    @StringRes int confirmPromptResId() default 0;
}
