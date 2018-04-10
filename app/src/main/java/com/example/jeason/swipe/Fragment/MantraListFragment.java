package com.example.jeason.swipe.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jeason on 2018/3/6.
 */

public class MantraListFragment extends BaseListFragment {
    private String TAG = MantraListFragment.class.getSimpleName();

    public static MantraListFragment newInstance(String[] mantraList) {
        MantraListFragment fragment = new MantraListFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_LAYOUT_MANAGER, LayoutManagerType.LINEAR_LAYOUT_MANAGER);
        args.putStringArray(DATASET,mantraList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestory");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v(TAG, "onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "onDetach");
    }


}
