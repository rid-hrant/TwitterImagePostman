package com.example.hrant.twitterimagepostman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hrant on 2/3/17.
 */
public class StatusAdapter extends BaseAdapter {

    private final Context mContext;
    List<String> mStatusList;

    public StatusAdapter(Context context, List<String> statusList) {
        mStatusList = statusList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mStatusList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_status, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.status_name);

        textView.setText(mStatusList.get(position));

        return convertView;
    }
}
