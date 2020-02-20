package ke.co.nathanaccess.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import ke.co.nathanaccess.R;
import ke.co.nathanaccess.ui.data.DataDisplay;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class SearchActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    ConstraintLayout mConstraintLayout;

    float mBtnHeight, mBtnWidth;

    // Spinner to select data type
    Spinner mDataSpinner;

    // Start and end day texts and temp store
    TextView mStartText, mEndText, mBox;

    // Search buttons
    Button mSearchBtn;

    // Arrow for aesthetics
    ImageView arrow;

    // Temporary date store
    String mDate = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConstraintLayout = findViewById(R.id.search_layout);
        setContentView(R.layout.activity_search);

        arrow = findViewById(R.id.search_arrow);
        arrow.setEnabled(FALSE);
        arrow.setVisibility(View.INVISIBLE);

        // Search button
        mSearchBtn = findViewById(R.id.search_button);
        mSearchBtn.setEnabled(FALSE);
        mSearchBtn.setVisibility(View.INVISIBLE);

        mBtnHeight = mSearchBtn.getY();
        mBtnWidth = mSearchBtn.getX();

        // Assign text boxes to module variables
        mStartText = findViewById(R.id.startDate);
        mStartText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new CalenderFragment();
                datePicker.show(getSupportFragmentManager(), "Calender Fragment");
                mBox = mStartText;
            }
        });

        mDataSpinner.setPrompt("Pick data type");

        mEndText = findViewById(R.id.endDate);
        mEndText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new CalenderFragment();
                datePicker.show(getSupportFragmentManager(), "Calender Fragment");
                mBox = mEndText;
            }
        });

        mStartText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mEndText.setVisibility(View.VISIBLE);
                mEndText.setEnabled(TRUE);
                arrow.setVisibility(View.VISIBLE);
                arrow.setEnabled(TRUE);
            }
        });

        mEndText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSearchBtn.setVisibility(View.VISIBLE);
                mSearchBtn.setEnabled(TRUE);

                //TODO: animate the arrow to hint at data being searched for
            }
        });


        // Set spinner adapter
        mDataSpinner = findViewById(R.id.dataTypeSpinner);

        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this, R.array.data_types, android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDataSpinner.setAdapter(spinAdapter);

        // Add spinner listener on selection
        mDataSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDataSpinner.setPrompt(parent.getItemAtPosition(position).toString());
                showViews(mDataSpinner.getPrompt().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDataSpinner.setPrompt("Pick data type");
            }
        });

        // Use button
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Get data from API when final date is selected store it in data variable and send it in extra "data"
                Intent showData = new Intent(SearchActivity.this, DataDisplay.class);
                showData.putExtra("startDate", mStartText.getText().toString());
                showData.putExtra("endDate", mEndText.getText().toString());
                showData.putExtra("dataType", mDataSpinner.getPrompt().toString());
                // add data here
                showData.putExtra("data", "");
                startActivity(showData);
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String yr = Integer.toString(year);
        String mon = Integer.toString(month);
        String day = Integer.toString(dayOfMonth);

        String date = yr+"-"+mon+"-"+day+" ";
        //String mDate = yr+"-"+mon+"-"+day+" ";
        mBox.setText(date);

        DialogFragment datePicker = new ClockFragment();
        datePicker.show(getSupportFragmentManager(), "Clock Fragment");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String hr = Integer.toString(hourOfDay);
        String min = Integer.toString(minute);

        if(min.length() == 1){
            min = "0"+min;
        }

        if(hr.length() == 1){
            hr = "0"+hr;
        }

        String date = mBox.getText().toString() + hr+":"+min+":"+":00";

        mBox.setText(date);
    }

    private void showViews(String choice){
        if(choice.equals("Validation")){
            mStartText.setVisibility(View.GONE);
            mStartText.setEnabled(FALSE);
            mEndText.setVisibility(View.GONE);
            mEndText.setEnabled(FALSE);
            arrow.setVisibility(View.GONE);
            arrow.setEnabled(FALSE);
            mSearchBtn.setVisibility(View.VISIBLE);
            mSearchBtn.setEnabled(TRUE);

            float layoutMaxHeight = mConstraintLayout.getMaxHeight()/2;
            float layoutMaxWidth = mConstraintLayout.getMaxWidth()/2;

            float widthOffset = mSearchBtn.getWidth()/2;
            float heightOffset = mSearchBtn.getHeight()/2;

            mSearchBtn.setX(layoutMaxWidth - widthOffset);
            mSearchBtn.setY(layoutMaxHeight - heightOffset);

        } else if (choice.equals("Cash Movement")){
            if(!mStartText.isEnabled()){
                mStartText.setVisibility(View.VISIBLE);
                mStartText.setEnabled(TRUE);
                mSearchBtn.setX(mBtnWidth);
                mSearchBtn.setY(mBtnHeight);
                mSearchBtn.setVisibility(View.GONE);
                mSearchBtn.setEnabled(FALSE);
            }
        } else if (choice.equals("Vehicle Movement")) {
            if(!mStartText.isEnabled()){
                mStartText.setVisibility(View.VISIBLE);
                mStartText.setEnabled(TRUE);
                mSearchBtn.setX(mBtnWidth);
                mSearchBtn.setY(mBtnHeight);
                mSearchBtn.setVisibility(View.GONE);
                mSearchBtn.setEnabled(FALSE);
            }
        }
    }


}
