package cm.intelso.dev.travelezi.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    TextView dateTextView;

    public SelectDateFragment(TextView tvDate){
        dateTextView = tvDate;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        populateSetDate(yy, mm+1, dd);
    }
    public void populateSetDate(int year, int month, int day) {
        String mois = "0";
        String jour = "0";
        if(month<10){
            mois += month;
        }else{
            mois = String.valueOf(month);
        }
        if(day<10){
            jour += day;
        }else{
            jour = String.valueOf(day);
        }
        dateTextView.setText(year + "-" + mois + "-" + jour);
        //dateOfBirth.setTextColor(getResources().getColor(R.color.primary));
    }

}
