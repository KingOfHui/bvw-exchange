package com.darknet.bvw.util;

import com.darknet.bvw.MyApp;
import com.darknet.bvw.util.language.SPUtil;

/**
 * Created by guoshiwen on 2020/7/24.
 */
public enum Language {

	CHINA,ENGLISH;

	public static Language readFromConfig(){
		int language = SPUtil.getInstance(MyApp.getInstance()).getSelectLanguage();
		if(language == 0) {
			//中文
			return Language.CHINA;
		}
		return getDefault();
	}

	public static Language getDefault(){
		return Language.ENGLISH;
	}


}
