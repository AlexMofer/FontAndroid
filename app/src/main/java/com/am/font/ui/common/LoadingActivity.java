/* ###WS@M Project:PDFelement-Android ### */
package com.am.font.ui.common;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.am.appcompat.app.AppCompatActivity;
import com.am.appcompat.app.AppCompatDialogFragment;
import com.am.font.ui.R;

/**
 * 带载入状态的Activity
 * Created by Xiang Zhicheng on 2021/4/27.
 */
public abstract class LoadingActivity extends AppCompatActivity {

    private static final String TAG_LOADING = "LoadingActivity.TAG_LOADING";
    private boolean mLoading = false;

    public LoadingActivity() {
    }

    public LoadingActivity(int contentLayoutId) {
        super(contentLayoutId);
    }

    /**
     * 显示载入对话框
     */
    public void showLoading(@Nullable String message) {
        if (mLoading)
            return;
        mLoading = true;
        final FragmentManager manager = getSupportFragmentManager();
        final Fragment fragment = manager.findFragmentByTag(TAG_LOADING);
        if (fragment instanceof InnerLoadingDialogFragment)
            return;
        InnerLoadingDialogFragment.newInstance(message).show(manager, TAG_LOADING);
    }

    /**
     * 显示载入对话框
     */
    public void showLoading(@StringRes int message) {
        showLoading(getString(message));
    }

    /**
     * 显示载入对话框
     */
    public void showLoading() {
        showLoading(null);
    }

    /**
     * 关闭载入对话框
     */
    public void dismissLoading() {
        if (!mLoading)
            return;
        mLoading = false;
        final FragmentManager manager = getSupportFragmentManager();
        final Fragment fragment = manager.findFragmentByTag(TAG_LOADING);
        if (fragment instanceof InnerLoadingDialogFragment)
            ((InnerLoadingDialogFragment) fragment).dismissAllowingStateLoss();
    }

    /**
     * 延迟关闭对话框
     *
     * @param delayMillis 延迟时间
     */
    public void postDelayedDismissLoading(long delayMillis) {
        getWindow().getDecorView().postDelayed(this::dismissLoading, delayMillis);
    }

    /**
     * 判断是否正在显示载入对话框
     *
     * @return 正在显示载入对话框时返回true
     */
    public boolean isLoading() {
        return mLoading;
    }

    /**
     * 内部载入对话框Fragment
     */
    public static final class InnerLoadingDialogFragment extends AppCompatDialogFragment {

        private static final String KEY_MESSAGE = "message";

        public InnerLoadingDialogFragment() {
            super(R.layout.dlg_common_loading);
            setStyle(STYLE_NO_TITLE, R.style.Style_OpenType_Dialog_Transparent);
        }

        public static InnerLoadingDialogFragment newInstance(@Nullable String message) {
            final InnerLoadingDialogFragment fragment = new InnerLoadingDialogFragment();
            final Bundle args = new Bundle();
            args.putString(KEY_MESSAGE, message);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            setCancelable(false);
            requireDialog().setCanceledOnTouchOutside(false);
//            final Bundle args = getArguments();
//            if (args != null) {
//                final String message = args.getString(KEY_MESSAGE);
//                if (message != null) {
//                    view.findViewById(R.id.mcl_iv_image).setBackgroundColor(Color.TRANSPARENT);
//                    final TextView text = view.findViewById(R.id.dcl_tv_message);
//                    text.setText(message);
//                    view.setBackgroundResource(R.drawable.bg_common_loading_message);
//                }
//            }
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            final FragmentActivity activity = getActivity();
            if (activity instanceof LoadingActivity && !((LoadingActivity) activity).isLoading())
                dismissAllowingStateLoss();
        }
    }
}
