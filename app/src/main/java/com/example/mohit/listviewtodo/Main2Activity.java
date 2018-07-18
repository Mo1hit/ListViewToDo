package com.example.mohit.listviewtodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    EditText editText1,editText2;
    Button button;
    Intent intent;
    final String TITLE_KEY="title_name";
    final String CONTENT_KEY="content_name";
    final String POSITION_KEY="position_string";

    String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editText1=findViewById(R.id.titleedittext);
        editText2=findViewById(R.id.contentedittext);
        
        button=findViewById(R.id.button);
        button.setOnClickListener(this);
        
        intent=getIntent();
        String title=intent.getStringExtra(TITLE_KEY);
        String content=intent.getStringExtra(CONTENT_KEY);
        position=intent.getStringExtra(POSITION_KEY);
        
        editText1.setText(title);
        editText2.setText(content);
    }

    @Override
    public void onClick(View view) 
    {
        String title=editText1.getText().toString();
        String content=editText2.getText().toString();
        if(title!=null&&content!=null)
        {
            Intent intent=new Intent();
            intent.putExtra(TITLE_KEY,title);
            intent.putExtra(CONTENT_KEY,content);
            intent.putExtra(POSITION_KEY,position);
            setResult(1,intent);
            finish();
        }
        else
        {
            Toast.makeText(this, "Long Press To Delete", Toast.LENGTH_SHORT).show();
        }
    }
}