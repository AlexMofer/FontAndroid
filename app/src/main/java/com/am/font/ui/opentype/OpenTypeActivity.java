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
package com.am.font.ui.opentype;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.am.font.ui.R;
import com.am.font.ui.common.LoadingActivity;

/**
 * OpenType
 */
public class OpenTypeActivity extends LoadingActivity implements OpenTypeView,
        OpenTypePickerDialog.OnPickerListener, OpenTypeViewHolder.OnViewHolderListener {

    private static final String EXTRA_PATH = "acom.am.font.ui.extra.PATH";
    private final OpenTypePresenter mPresenter =
            new OpenTypePresenter().setViewHolder(getViewHolder());
    private final OpenTypeAdapter mAdapter = new OpenTypeAdapter(mPresenter, this);
    private OpenTypePickerDialog mPicker;
    private AlertDialog mInfo;

    public OpenTypeActivity() {
        super(R.layout.activity_opentype);
    }

    public static void start(Context context, String path) {
        context.startActivity(
                new Intent(context, OpenTypeActivity.class).putExtra(EXTRA_PATH, path));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(R.id.ot_toolbar);
        final RecyclerView content = findViewById(R.id.ot_content);
        content.setAdapter(mAdapter);
        showLoading();
        mPresenter.parse(getIntent().getStringExtra(EXTRA_PATH));
    }

    @Override
    protected void onToolbarMenuUpdate(@NonNull Menu menu) {
        super.onToolbarMenuUpdate(menu);
        final MenuItem item = menu.findItem(R.id.ot_collection);
        item.setVisible(mPresenter.isCollection());
    }

    @Override
    protected boolean onToolbarMenuItemClick(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.ot_collection) {
            if (mPresenter.isCollection())
                showPicker();
            return true;
        }
        return super.onToolbarMenuItemClick(item);

    }

    // View
    @Override
    public void onParseFailure() {
        dismissLoading();
        Toast.makeText(this, R.string.ot_toast_parse_failure,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onParseSuccess(boolean isCollection) {
        dismissLoading();
        mAdapter.notifyDataSetChanged();
        if (isCollection) {
            invalidateOptionsMenu();
            // 显示字体选择对话框
            showPicker();
        }
    }

    private void showPicker() {
        if (mPicker == null)
            mPicker = new OpenTypePickerDialog(this, mPresenter, this);
        mPicker.notifyDataSetChanged();
        mPicker.show();
    }

    // Listener
    @Override
    public void onItemPicked(int position) {
        mPicker.dismiss();
        mPresenter.setCollectionItem(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Object item) {
        if (mInfo == null)
            mInfo = new AlertDialog.Builder(this)
                    .setPositiveButton(android.R.string.ok, null)
                    .create();
        mInfo.setTitle(mPresenter.getItemLabel(item));
        mInfo.setMessage(mPresenter.getItemInfo(item));
        mInfo.show();
    }
}
