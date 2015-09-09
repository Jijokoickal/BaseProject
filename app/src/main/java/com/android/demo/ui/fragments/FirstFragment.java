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
public class FirstFragment extends AbstractBaseFragment implements View.OnClickListener {

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(returnView);

        return returnView;
    }

    private void initViews(View returnView) {

        returnView.findViewById(R.id.btn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        SecondFragment secondFragment = SecondFragment.newInstance();
        addFragment(secondFragment, true);
    }
}
