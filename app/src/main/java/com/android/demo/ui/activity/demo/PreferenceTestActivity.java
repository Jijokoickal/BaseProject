package com.android.demo.ui.activity.demo;

import android.os.Bundle;

import com.android.demo.R;
import com.android.demo.ui.activity.base.AbstractBaseActivity;
import com.android.demo.ui.fragments.FirstFragment;

/**
 * Created by Admin on 08-09-2015.
 */
public class PreferenceTestActivity extends AbstractBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base_activity);

        setUpInitialFragment();
    }

    private void setUpInitialFragment() {
        FirstFragment fragment = FirstFragment.newInstance();
        addFragment(fragment, true);
    }
}


