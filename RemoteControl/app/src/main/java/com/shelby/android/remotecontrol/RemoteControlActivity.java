package com.shelby.android.remotecontrol;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.Window;


public class RemoteControlActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new RemoteControlFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
