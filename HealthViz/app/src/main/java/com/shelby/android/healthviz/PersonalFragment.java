package com.shelby.android.healthviz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by John on 11/29/2014.
 */
public class PersonalFragment extends Fragment {
    private Patient mPatient;
    private final String TAG = "PersonalFragment";
    Button mPersonalButton;
    TextView mNameField;
    TextView mGenderField;
    TextView mBirthDateField;
    TextView mAgeField;
    TextView mMaritalStatusField;
    TextView mReligiousAffiliationField;
    TextView mRaceField;
    TextView mEthnicGroupField;
    TextView mStreetAddressField;
    TextView mCityField;
    TextView mStateField;
    TextView mCountryField;
    TextView mPostalCodeField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPatient = new Patient();
        UUID patientId = (UUID)getArguments().getSerializable(PatientFragment.EXTRA_PATIENT_ID);
        mPatient = PatientLab.get(getActivity()).getPatient(patientId);
        mPatient.parseCCD(PatientFragment.CCD_PERSONAL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_personal, parent, false);

        mPersonalButton = (Button)v.findViewById(R.id.personalSection_button);
        mPersonalButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Personal button clicked", Toast.LENGTH_SHORT).show();

                View parent = (View)v.getParent();
                TableLayout tableLayout = (TableLayout)parent.findViewById(R.id.personalSection_layout);
                if (tableLayout.getVisibility() == (View.VISIBLE)) {
                    tableLayout.setVisibility(View.GONE);
                }
                else {
                    tableLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        mNameField = (TextView)v.findViewById(R.id.patient_name);
        mNameField.setText(mPatient.toString());

        mGenderField = (TextView)v.findViewById(R.id.patient_gender);
        if (mPatient.getGender().equals("")) {
            TextView mGenderLabel = (TextView) v.findViewById(R.id.patient_gender_label);
            mGenderLabel.setVisibility(View.GONE);
            mGenderField.setVisibility(View.GONE);
        }
        else {
            mGenderField.setText(mPatient.getGender());
        }


        Date d = new Date(0);
        mBirthDateField = (TextView)v.findViewById(R.id.patient_birthDate);
        mAgeField = (TextView)v.findViewById(R.id.patient_age);
        if (mPatient.getBirthDate().equals(d)) {
            // has not been initialized
            TextView mBirthDateLabel = (TextView) v.findViewById(R.id.patient_birthDate_label);
            mBirthDateLabel.setVisibility(View.GONE);
            mBirthDateField.setVisibility(View.GONE);

            TextView mAgeLabel = (TextView) v.findViewById(R.id.patient_age_label);
            mAgeLabel.setVisibility(View.GONE);
            mAgeField.setVisibility(View.GONE);
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("LLLL d, y", Locale.US);
            mBirthDateField.setText(sdf.format(mPatient.getBirthDate()));
            mAgeField.setText(CcdDecode.decodeAge(mPatient.getBirthDate(), mPatient.getContext()));
        }


        mMaritalStatusField = (TextView) v.findViewById(R.id.patient_maritalStatus);
        if (mPatient.getMaritalStatusCode().equals("")) {
            TextView mMaritalStatusLabel = (TextView) v.findViewById(R.id.patient_maritalStatus_label);
            mMaritalStatusLabel.setVisibility(View.GONE);
            mMaritalStatusField.setVisibility(View.GONE);
        }
        else {
            mMaritalStatusField.setText(CcdDecode.decodeMaritalStatus(mPatient.getMaritalStatusCode()));
        }

        mRaceField = (TextView) v.findViewById(R.id.patient_race);
        if (mPatient.getRaceCode().equals("")) {
            TextView mRaceLabel = (TextView) v.findViewById(R.id.patient_race_label);
            mRaceLabel.setVisibility(View.GONE);
            mRaceField.setVisibility(View.GONE);
        }
        else {
            mRaceField.setText(CcdDecode.decodeRaceEthnicity(mPatient.getRaceCode()));
        }

        mEthnicGroupField = (TextView) v.findViewById(R.id.patient_ethnicGroup);
        if (mPatient.getEthnicGroupCode().equals("")) {
            TextView mEthnicGroupLabel = (TextView) v.findViewById(R.id.patient_ethnicGroup_label);
            mEthnicGroupLabel.setVisibility(View.GONE);
            mEthnicGroupField.setVisibility(View.GONE);
        }
        else {
            mEthnicGroupField.setText(CcdDecode.decodeRaceEthnicity(mPatient.getEthnicGroupCode()));
        }

        mReligiousAffiliationField = (TextView)v.findViewById(R.id.patient_religiousAffiliation);
        if (mPatient.getReligiousAffiliationCode().equals("")) {
            TextView mReligiousAffiliationLabel = (TextView) v.findViewById(R.id.patient_religiousAffiliation_label);
            mReligiousAffiliationLabel.setVisibility(View.GONE);
            mReligiousAffiliationField.setVisibility(View.GONE);
        }
        else {
            mReligiousAffiliationField.setText(CcdDecode.decodeReligiousAffiliation(mPatient.getReligiousAffiliationCode()));
        }


        mStreetAddressField = (TextView)v.findViewById(R.id.patient_streetAddress);
        if (mPatient.getStreetAddress().equals("")) {
            TextView mAddressLabel = (TextView) v.findViewById(R.id.patient_address_label);
            mAddressLabel.setVisibility(View.GONE);
            mStreetAddressField.setVisibility(View.GONE);
        }
        else {
            /*
            String u = mPatient.getAddrUseToString();
            if (! u.equals("")) {
                u = " (" + u + ")";
            }
            */
            mStreetAddressField.setText(mPatient.getStreetAddress());
        }

        mCityField = (TextView)v.findViewById(R.id.patient_city);
        if (mPatient.getCity().equals("")) {
            TextView mCityLabel = (TextView) v.findViewById(R.id.patient_city_label);
            mCityLabel.setVisibility(View.GONE);
            mCityField.setVisibility(View.GONE);
        }
        else {
            mCityField.setText(mPatient.getCity());
        }

        mStateField = (TextView)v.findViewById(R.id.patient_state);
        mStateField.setText(mPatient.getState());
        if (mPatient.getCity().equals("")) {
            TextView mCityLabel = (TextView) v.findViewById(R.id.patient_city_label);
            mCityLabel.setVisibility(View.GONE);
            mCityField.setVisibility(View.GONE);
        }
        else {
            mCityField.setText(mPatient.getCity());
        }

        mCountryField = (TextView)v.findViewById(R.id.patient_country);
        if (mPatient.getCountry().equals("")) {
            TextView mCountryLabel = (TextView) v.findViewById(R.id.patient_country_label);
            mCountryLabel.setVisibility(View.GONE);
            mCountryField.setVisibility(View.GONE);
        }
        else {
            mCountryField.setText(mPatient.getCountry());
        }

        mPostalCodeField = (TextView)v.findViewById(R.id.patient_postalCode);
        mPostalCodeField.setText(mPatient.getPostalCode());

        return v;
    }
}

