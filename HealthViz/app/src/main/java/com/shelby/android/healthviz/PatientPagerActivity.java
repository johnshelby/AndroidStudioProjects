package com.shelby.android.healthviz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.UUID;

public class PatientPagerActivity extends FragmentActivity {
    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        final ArrayList<Patient> patients = PatientLab.get(this).getPatients();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return patients.size();
            }
            @Override
            public Fragment getItem(int pos) {
                UUID patientId =  patients.get(pos).getId();
                return PatientFragment.newInstance(patientId);
            }
        }); 

        UUID patientId = (UUID)getIntent().getSerializableExtra(PatientFragment.EXTRA_PATIENT_ID);
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getId().equals(patientId)) {
                mViewPager.setCurrentItem(i);
                break;
            } 
        }
    }
}
