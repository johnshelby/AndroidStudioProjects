package com.shelby.android.healthviz;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

public class PatientFragment extends Fragment {
    public static final String EXTRA_PATIENT_ID = "healthviz.PATIENT_ID";
    public static final String CCD_PERSONAL = "personal";
    public static final String CCD_ALERTS = "alerts";
    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;

    Patient mPatient;
    EditText mTitleField;
    Button mDateButton;
    CheckBox mSolvedCheckBox;

    public static PatientFragment newInstance(UUID patientId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_PATIENT_ID, patientId);

        PatientFragment fragment = new PatientFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final String TAG = "PatientFragment onCreate";
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.patient_title_label);

        UUID patientId = (UUID)getArguments().getSerializable(EXTRA_PATIENT_ID);
        mPatient = PatientLab.get(getActivity()).getPatient(patientId);
        setHasOptionsMenu(true);

        FragmentManager fm = getChildFragmentManager();
        Fragment personalFragment = fm.findFragmentById(R.id.fragmentPersonal);
        if (personalFragment == null) {
            Log.d(TAG, "Adding personal fragment");
            personalFragment = new PersonalFragment();
            Bundle args = new Bundle();
            args.putSerializable(EXTRA_PATIENT_ID, patientId);
            personalFragment.setArguments(args);
            fm.beginTransaction()
                .add(R.id.fragmentPatient, personalFragment)
                .commit();
        }

        Fragment alertsFragment = fm.findFragmentById(R.id.fragmentAlerts);
        if (alertsFragment == null) {
            Log.d(TAG, "Adding alerts fragment");
            alertsFragment = new AlertsFragment();
            Bundle args = new Bundle();
            args.putSerializable(EXTRA_PATIENT_ID, patientId);
            alertsFragment.setArguments(args);
            fm.beginTransaction()
                .add(R.id.fragmentPatient, alertsFragment)
                .commit();
        }
    }
    
    public void updateDate() {
        mDateButton.setText(mPatient.getDate().toString());
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patient, parent, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }   

        mTitleField = (EditText)v.findViewById(R.id.patient_title);
        mTitleField.setText(mPatient.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //mPatient.setTitle(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        /*
        mDateButton = (Button)v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                    .newInstance(mPatient.getDate());
                dialog.setTargetFragment(PatientFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });
        
        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mPatient.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // set the crime's solved property
                mPatient.setSolved(isChecked);
            }
        });
        */

        return v; 
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mPatient.setDate(date);
            updateDate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } 
    }
}
