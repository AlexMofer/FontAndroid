/*
 * Copyright (C) 2018 AlexMofer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.am.font.ui.font;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.am.font.ui.R;

/**
 * 字体家族名选择对话框
 */
class FontFamilyPickerDialog extends AlertDialog implements AdapterView.OnItemClickListener {

    private final FontDataAdapter mDataAdapter;
    private final Adapter mAdapter;
    private final TextView mMessage;
    private final OnPickerListener mListener;

    FontFamilyPickerDialog(Context context, FontDataAdapter adapter,
                           OnPickerListener listener) {
        super(context);
        mDataAdapter = adapter;
        mAdapter = new Adapter(adapter);
        mListener = listener;
        setTitle(R.string.font_picker_title);
        final View content = View.inflate(context, R.layout.dlg_font_picker, null);
        mMessage = content.findViewById(R.id.dfp_tv_message);
        final ListView names = content.findViewById(R.id.dfp_lv_names);
        names.setAdapter(mAdapter);
        names.setOnItemClickListener(this);
        setView(content);
    }

    void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
        mMessage.setText(getContext().getString(R.string.font_picker_message,
                mDataAdapter.getDefaultFamilyName()));
    }

    // Listener
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mListener.onItemPicked(mAdapter.getItem(position));
    }

    public interface OnPickerListener {
        void onItemPicked(String item);
    }

    private static class Adapter extends BaseAdapter {

        private final FontDataAdapter mAdapter;

        Adapter(FontDataAdapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public int getCount() {
            return mAdapter.getFamilyNameOrAliasCount();
        }

        @Override
        public String getItem(int position) {
            return mAdapter.getFamilyNameOrAlias(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_opentype_font, parent, false);
            }
            (((TextView) convertView)).setText(getItem(position));
            convertView.setActivated(mAdapter.isFamilyAlias(position));
            return convertView;
        }
    }
}
