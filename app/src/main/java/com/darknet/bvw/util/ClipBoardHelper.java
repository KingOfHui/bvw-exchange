package com.darknet.bvw.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import com.darknet.bvw.R;

public class ClipBoardHelper {
    public static void clickToCopy(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
        ClipData clipData = ClipData.newPlainText(null, text);
        // 把数据集设置（复制）到剪贴板
        clipboard.setPrimaryClip(clipData);
        Toast.makeText(context, context.getResources().getString(R.string.copy_bord_content), Toast.LENGTH_SHORT).show();
        TipHelper.Vibrate(context, new long[]{200, 300}, false);
    }
}
