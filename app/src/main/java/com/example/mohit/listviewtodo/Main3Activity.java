package com.example.mohit.listviewtodo;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    final String TITLE_KEY="title_name";
    final String CONTENT_KEY="content_name";
    final String DATE_KEY="date";
    final String TIME_KEY="time";

    public int year, month, day;
    public int yearFinal, monthFinal, dayFinal;
    public int hour, minute;
    public int hourFinal,minuteFinal;

    public String date;
    public String time;

    EditText editText1,editText2;
    Button button1;
    ImageView imageView1,imageView2;
    TextView textViewDate,textViewTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        button1=findViewById(R.id.button);
        button1.setOnClickListener(this);

        editText1=findViewById(R.id.titleedittext);
        editText2=findViewById(R.id.contentedittext);

        imageView1=findViewById(R.id.dateImageViewId);
        imageView2=findViewById(R.id.timeImageViewId);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);

        Snackbar.make(imageView1, "Add Your Todo Here...", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        
        textViewDate=findViewById(R.id.timeTextViewId);
        textViewTime=findViewById(R.id.dateTextViewId);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.dateImageViewId||view.getId()==R.id.timeImageViewId)
        {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(calendar.YEAR);
            month = calendar.get(calendar.MONTH);
            day = calendar.get(calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(Main3Activity.this,
                    Main3Activity.this, year, month, day);
            datePickerDialog.show();
        }
        else if(view.getId()==R.id.button)
        {
            String title = editText1.getText().toString();
            String content = editText2.getText().toString();
            if (title != null && content != null && date != null)
            {
                Intent intent = new Intent();
                intent.putExtra(TITLE_KEY, title);
                intent.putExtra(CONTENT_KEY, content);
                intent.putExtra(DATE_KEY, date);
                intent.putExtra(TIME_KEY, time);

                AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);

                Intent intent1=new Intent(this,MyReceiver.class);
                intent1.putExtra("title",title);

                PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent1,0);

                Calendar calendar=Calendar.getInstance();
                calendar.set(yearFinal,monthFinal-1,dayFinal,hourFinal,minuteFinal);

                long alarmTime=calendar.getTimeInMillis();
                
                alarmManager.set(AlarmManager.RTC_WAKEUP,alarmTime,pendingIntent);
                
                setResult(1, intent);
                Toast.makeText(this, "Notification Set for this time: "+time+"," +
                        " On: "+date, Toast.LENGTH_LONG).show();

                finish();
            }
            if (title == null) {
                Toast.makeText(this, "Please write Content", Toast.LENGTH_SHORT).show();
                editText2.setError("Fill");
            }
            if (content == null) {
                Toast.makeText(this, "Please write Title", Toast.LENGTH_SHORT).show();
                editText1.setError("Fill");
            }
            if(date==null){
                Toast.makeText(this, "Please Set Date And Time", Toast.LENGTH_SHORT).show();
                textViewTime.setError("Fill");
                textViewDate.setError("Fill");
            }
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
    {
        yearFinal=i;
        monthFinal=i1+1;
        dayFinal=i2;

        if(dayFinal<10)
        {
            date="0"+dayFinal+"/";
        }
        else
        {
            date=dayFinal+"/";
        }
        if(monthFinal<10)
        {
            date=date+"0"+monthFinal+"/";
        }
        else
        {
            date=date+monthFinal+"/";
        }
        date=date+yearFinal;
        
        textViewDate.setText(date);

        Calendar calendar=Calendar.getInstance();
        hour=calendar.get(Calendar.HOUR_OF_DAY);
        minute=calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog=new TimePickerDialog(Main3Activity.this,
                                            Main3Activity.this,hour,minute,true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1)
    {
        hourFinal=i;
        minuteFinal=i1;

        if(hourFinal<10)
        {
            time="0"+hourFinal+":";
        }
        else
        {
            time=hourFinal+":";
        }
        if(minuteFinal<10)
        {
            time=time+"0"+minuteFinal;
        }
        else
        {
            time=time+minuteFinal;
        }
        
        textViewTime.setText(time);
    }
}