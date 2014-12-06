package com.shelby.android.healthviz;

import android.support.v4.app.Fragment;

public class PatientListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PatientListFragment();
    }
}
