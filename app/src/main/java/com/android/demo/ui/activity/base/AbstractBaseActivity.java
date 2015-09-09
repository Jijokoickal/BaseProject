package com.android.demo.ui.activity.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.demo.R;
import com.android.demo.ui.fragments.base.AbstractBaseFragment;

/**
 *
 */
public class AbstractBaseActivity extends FragmentActivity {

    private FragmentManager fm;
    private AbstractBaseFragment mCurrFragment;

    /**
     * back stack changed listener for the fragments in activities.
     */
    private final FragmentManager.OnBackStackChangedListener onBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            FragmentManager.BackStackEntry backEntry;
            if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
                backEntry = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1);
                String str = backEntry.getName();

                AbstractBaseFragment fragment = (AbstractBaseFragment) getSupportFragmentManager().findFragmentByTag(str);

                if (fragment != null) {
                    mCurrFragment = fragment;
                }
            } else {
                finish();
            }
        }

    };


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * common method to  add/replace fragments. From the fragments/activity
     * we are calling this method through callback.
     *
     * @param fragment
     * @param addToBackStack
     */
    public void addFragment(final Fragment fragment, final boolean addToBackStack) {

        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        getSupportFragmentManager().addOnBackStackChangedListener(onBackStackChangedListener);
        AbstractBaseFragment mPrevFragment = mCurrFragment;
        this.mCurrFragment = (AbstractBaseFragment) fragment;

        if (mPrevFragment == null) {
            mPrevFragment = mCurrFragment;
        }


        mFragmentTransaction.setCustomAnimations(R.anim.enter_from_right,
                R.anim.exit_to_left, R.anim.enter_from_left,
                R.anim.exit_to_right);

        if (mPrevFragment != null) {
            mFragmentTransaction.replace(R.id.fragmentContainer, fragment, fragment.getClass().getName());
        } else {
            mFragmentTransaction.add(R.id.fragmentContainer, fragment, fragment.getClass().getName());
        }

//        mFragmentTransaction.remove(fragment);

        if (addToBackStack) {
            mFragmentTransaction.addToBackStack(fragment.getClass().getName());
        }

        mFragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();

    }

    protected void removeFragment(final Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }
}
