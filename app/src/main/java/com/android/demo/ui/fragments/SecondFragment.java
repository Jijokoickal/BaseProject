package com.android.demo.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.demo.R;
import com.android.demo.ui.fragments.base.AbstractBaseFragment;

/**
 *
 */
public class SecondFragment extends AbstractBaseFragment {

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.fragment_second, container, false);

        initViews(returnView);

        return returnView;
    }

    private void initViews(View returnView) {

    }
}
