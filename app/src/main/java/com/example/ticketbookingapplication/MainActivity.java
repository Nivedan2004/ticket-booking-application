package com.example.ticketbookingapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView dateView, timeView;
    EditText source, destination;
    Button dateButton, timeButton, bookTicket;
    ToggleButton acToggle;
    SwitchCompat singleLady;
    int year, month, day, hour, minute;

    String src, destn, switchText;
    int ticketPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateView = findViewById(R.id.date_text);
        timeView = findViewById(R.id.time_text);
        source = findViewById(R.id.source_text);
        destination = findViewById(R.id.destination_text);
        dateButton = findViewById(R.id.date_button);
        timeButton = findViewById(R.id.time_button);
        bookTicket = findViewById(R.id.bookTicket_button);
        acToggle = findViewById(R.id.ac_toggle);
        singleLady = findViewById(R.id.singlelady_switch);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateView.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR);
                minute = c.get(Calendar.MINUTE);
                TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeView.setText(hourOfDay + ":" + minute);
                    }
                };
                TimePickerDialog tpd = new TimePickerDialog(MainActivity.this, mTimeSetListener, hour, minute, false);
                tpd.show();
            }
        });

        bookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                src = source.getText().toString();
                destn = destination.getText().toString();

                // Check if "Single Lady" option is selected
                if (singleLady.isChecked()) {
                    switchText = "Single Lady";
                    ticketPrice = 49; // Price for Single Lady
                } else {
                    switchText = "General";
                    ticketPrice = 50; // Price for General
                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Confirm ticket?");
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // After confirmation, ask for payment
                        showPaymentDialog(ticketPrice);
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Optional behavior when "No" is clicked
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    private void showPaymentDialog(int price) {
        AlertDialog.Builder paymentDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        paymentDialogBuilder.setTitle("Payment Required");

        // Show the price based on user category
        paymentDialogBuilder.setMessage("You need to pay ₹" + price + " for the ticket.");

        paymentDialogBuilder.setPositiveButton("Pay ₹" + price, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Payment Successful!\n" + "Ticket Booked\nFrom: " + src + "\nTo: " + destn + "\n" + switchText + "\n" + acToggle.getText() + " Coach", Toast.LENGTH_LONG).show();
            }
        });

        paymentDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Payment Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog paymentDialog = paymentDialogBuilder.create();
        paymentDialog.show();
    }
}
