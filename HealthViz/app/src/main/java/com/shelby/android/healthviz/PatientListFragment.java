package com.shelby.android.healthviz;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PatientListFragment extends ListFragment {
    private ArrayList<Patient> mPatients;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.patients_title);
        mPatients = PatientLab.get(getActivity()).getPatients();
        PatientAdapter adapter = new PatientAdapter(mPatients);
        setListAdapter(adapter);
        setRetainInstance(true);
        mSubtitleVisible = false;
    }
    
    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {   
            if (mSubtitleVisible) {
                getActivity().getActionBar().setSubtitle(R.string.subtitle);
            }
        }
        
        return v;
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        // get the Patient from the adapter
        Patient c = ((PatientAdapter)getListAdapter()).getItem(position);
        // start an instance of PatientPagerActivity
        Intent i = new Intent(getActivity(), PatientPagerActivity.class);
        i.putExtra(PatientFragment.EXTRA_PATIENT_ID, c.getId());
        startActivityForResult(i, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((PatientAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_patient_list, menu);
        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible && showSubtitle != null) {
            showSubtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @TargetApi(11)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Patient patient = new Patient();
                PatientLab.get(getActivity()).addPatient(patient);
                Intent i = new Intent(getActivity(), PatientActivity.class);
                i.putExtra(PatientFragment.EXTRA_PATIENT_ID, patient.getId());
                startActivityForResult(i, 0);
                return true;
            case R.id.menu_item_show_subtitle:
            	if (getActivity().getActionBar().getSubtitle() == null) {
                    getActivity().getActionBar().setSubtitle(R.string.subtitle);
                    mSubtitleVisible = true;
                    item.setTitle(R.string.hide_subtitle);
            	}  else {
            		getActivity().getActionBar().setSubtitle(null);
            		 mSubtitleVisible = false;
            		item.setTitle(R.string.show_subtitle);
            	}
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } 
    }

    private class PatientAdapter extends ArrayAdapter<Patient> {
        public PatientAdapter(ArrayList<Patient> patients) {
            super(getActivity(), android.R.layout.simple_list_item_1, patients);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.list_item_patient, null);
            }

            // configure the view for this patient
            Patient p = getItem(position);

            TextView titleTextView =
                (TextView)convertView.findViewById(R.id.patient_list_item_titleTextView);
            titleTextView.setText(p.getTitle());
            TextView xmlTextView =
                (TextView)convertView.findViewById(R.id.patient_list_item_xmlTextView);
            xmlTextView.setText("CCD: " + p.getXmlFileName());
            TextView dateTextView =
                (TextView)convertView.findViewById(R.id.patient_list_item_dateTextView);
            dateTextView.setText(p.getDate().toString());
            CheckBox solvedCheckBox =
                (CheckBox)convertView.findViewById(R.id.patient_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(p.isSolved());

            return convertView;
        }
    }
}

