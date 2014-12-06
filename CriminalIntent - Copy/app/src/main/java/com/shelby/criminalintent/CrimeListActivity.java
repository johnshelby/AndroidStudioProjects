package com.shelby.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by John on 11/9/2014.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
