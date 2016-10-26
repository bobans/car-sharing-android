package rs.elfak.bobans.carsharing.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import org.joda.time.DateTime;

import java.util.Stack;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class ViewUtils {

    public static void setFont(View view, Typeface font) {
        Stack<View> stack = new Stack<>();
        stack.add(view);
        while (!stack.isEmpty()) {
            View current = stack.pop();
            if (current instanceof TextView) {
                ((TextView) current).setTypeface(font);
            } else if (current instanceof ViewGroup) {
                for (int i=0; i<((ViewGroup) current).getChildCount(); i++) {
                    stack.push(((ViewGroup) current).getChildAt(i));
                }
            }
        }
    }

    public static void showDateTimePickerDialog(final Context context, final DateTime initialDateTime, final OnDateTimeSetListener onDateTimeSetListener) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (onDateTimeSetListener != null) {
                            onDateTimeSetListener.OnDateTimeSet(year, month, dayOfMonth, hourOfDay, minute);
                        }
                    }
                }, initialDateTime.getHourOfDay(), initialDateTime.getMinuteOfHour(), true);
                timePickerDialog.setCancelable(false);
                timePickerDialog.show();
            }
        }, initialDateTime.getYear(), initialDateTime.getMonthOfYear(), initialDateTime.getDayOfMonth());
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }

    public interface OnDateTimeSetListener {
        void OnDateTimeSet(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour);
    }

}
