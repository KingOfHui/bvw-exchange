package com.darknet.bvw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.darknet.bvw.R;
import com.darknet.bvw.model.WordsModel;
import com.darknet.bvw.view.TypefaceTextView;

import java.util.List;

public class WordsAdapter extends BaseAdapter {

    private List<WordsModel> listModels;

    private Context context;

    public WordsAdapter(Context context, List<WordsModel> listModels) {
        this.context = context;
        this.listModels = listModels;
    }


    @Override
    public int getCount() {
        return listModels.size();
    }

    @Override
    public Object getItem(int i) {
        return listModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        WordsModel chatModel = listModels.get(i);
        ViewHolder viewHolderOne;
        if (convertView == null) {
            viewHolderOne = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.words_item_view, null);
            viewHolderOne.contentView = (TypefaceTextView) convertView.findViewById(R.id.word_item_content_view);
            convertView.setTag(viewHolderOne);
        } else {
            viewHolderOne = (ViewHolder) convertView.getTag();
        }

        viewHolderOne.contentView.setText(chatModel.getContent());

        if (chatModel.getSign() == 0) {
            viewHolderOne.contentView.setBackgroundResource(R.drawable.word_normal_bg);
        } else {
            viewHolderOne.contentView.setBackgroundResource(R.drawable.word_press_bg);
        }

        return convertView;
    }

    public final class ViewHolder {
        public TypefaceTextView contentView;
    }

}
