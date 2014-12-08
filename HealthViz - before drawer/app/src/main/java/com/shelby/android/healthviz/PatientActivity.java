package com.shelby.android.healthviz;

import android.support.v4.app.Fragment;

import java.util.UUID;

public class PatientActivity extends SingleFragmentActivity {
	@Override
    protected Fragment createFragment() {
        UUID patientId = (UUID)getIntent()
            .getSerializableExtra(PatientFragment.EXTRA_PATIENT_ID);
        return PatientFragment.newInstance(patientId);
    }
}
