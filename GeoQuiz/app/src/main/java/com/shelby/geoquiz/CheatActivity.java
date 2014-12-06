package com.shelby.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CheatActivity extends ActionBarActivity {

    public static final String EXTRA_ANSWER_IS_TRUE = "com.shelby.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.shelby.geoquiz.answer_shown";
    private static final String TAG = "CheatActivity";
    private static final String KEY_ANSWER = "answer";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK,  data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        Log.d(TAG, "onCreate(Bundle) called");

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);

        if (savedInstanceState == null) {
            // initial entry
            Log.d(TAG, "savedInstanceState is null");
            setAnswerShownResult(false);
            mAnswerTextView.setText("");
        }
        else {
            Log.d(TAG, "savedInstanceState is not null");
            mAnswerTextView.setText(savedInstanceState.getString(KEY_ANSWER, ""));
            if (mAnswerTextView.getText().toString().equals("")) {
                setAnswerShownResult(false);
            } else {
                setAnswerShownResult(true);
            }
        }

        //setAnswerShownResult(false);
        //mAnswerTextView.setText("");

        mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cheat, menu);
        return true;
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState(Bundle) called");
        savedInstanceState.putString(KEY_ANSWER, mAnswerTextView.getText().toString());
    }
}
