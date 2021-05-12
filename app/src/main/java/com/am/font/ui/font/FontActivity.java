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

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.am.font.ui.R;
import com.am.font.ui.common.LoadingActivity;
import com.am.font.ui.opentype.OpenTypeActivity;

/**
 * 字体
 */
public class FontActivity extends LoadingActivity implements FontView,
        FontFamilyPickerDialog.OnPickerListener, FontViewHolder.OnViewHolderListener {

    private final FontPresenter mPresenter = new FontPresenter().setViewHolder(getViewHolder());
    private final FontAdapter mAdapter = new FontAdapter(mPresenter, this);

    private FontFamilyPickerDialog mPicker;

    public FontActivity() {
        super(R.layout.activity_font);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(R.id.font_toolbar);
        final RecyclerView content = findViewById(R.id.font_content);
        content.setAdapter(mAdapter);
        showLoading();
        mPresenter.loadConfig();
    }

    @Override
    protected void onToolbarMenuUpdate(@NonNull Menu menu) {
        super.onToolbarMenuUpdate(menu);
        final MenuItem item = menu.findItem(R.id.font_family);
        item.setVisible(mPresenter.getFamilyNameOrAliasCount() > 0);
    }

    @Override
    protected boolean onToolbarMenuItemClick(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.font_family) {
            if (mPresenter.getFamilyNameOrAliasCount() > 0)
                showPicker(true);
            return true;
        }
        return super.onToolbarMenuItemClick(item);
    }

    // View
    @Override
    public void onLoadConfigFailure() {
        dismissLoading();
        Toast.makeText(this, R.string.font_error_config, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onLoadConfigSuccess() {
        dismissLoading();
        invalidateOptionsMenu();
        showPicker(false);
    }

    @Override
    public void onLoadTypefaceCollectionFailure() {
        dismissLoading();
        Toast.makeText(this, R.string.font_error_typeface, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadTypefaceCollectionSuccess() {
        dismissLoading();
        mAdapter.notifyDataSetChanged();
    }

    // Listener
    @Override
    public void onItemPicked(String item) {
        mPicker.dismiss();
        showLoading();
        mPresenter.loadTypefaceCollection(item);
    }

    @Override
    public void onItemClick(Object item) {
        OpenTypeActivity.start(this, mPresenter.getTypefaceItemPath(item));
    }

    private void showPicker(boolean cancelable) {
        if (mPicker == null)
            mPicker = new FontFamilyPickerDialog(this, mPresenter, this);
        mPicker.notifyDataSetChanged();
        mPicker.setCancelable(cancelable);
        mPicker.setCanceledOnTouchOutside(cancelable);
        mPicker.show();
    }
}
