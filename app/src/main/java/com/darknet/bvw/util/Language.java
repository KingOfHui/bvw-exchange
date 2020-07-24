package com.darknet.bvw.util;

import com.darknet.bvw.MyApp;
import com.darknet.bvw.util.language.LocalManageUtil;
import com.darknet.bvw.util.language.SPUtil;

/**
 * Created by guoshiwen on 2020/7/24.
 */
public enum Language {

	CHINA("CN", 1), ENGLISH("EN", 0);

	Language(String name, int code) {
		this.name = name;
		this.code = code;
	}

	public String name;
	public int code;

	public static Language readFromConfig(){
		int language = SPUtil.getInstance(MyApp.getInstance()).getSelectLanguage();
		if(language == Language.CHINA.code) {
			return Language.CHINA;
		}else if(language == Language.ENGLISH.code){
			return Language.ENGLISH;
		}
		return getDefault();
	}

	/**
	 * 改变语言, 在中文和英文之间切换
	 */
	public static void change(){
		Language language = readFromConfig();
		switch (language) {
			case ENGLISH:
				CHINA.switchTo();
				break;
			case CHINA:
				ENGLISH.switchTo();
				break;
		}
	}

	public Language next(){
		switch (this){
			case ENGLISH:
				return CHINA;
			case CHINA:
				return ENGLISH;
			default:
				return getDefault();
		}
	}

	/**
	 * 切换到目标语言
	 */
	public void switchTo(){
		LocalManageUtil.saveSelectLanguage(MyApp.getInstance(), code);
	}

	public static Language getDefault(){
		return Language.ENGLISH;
	}


}
