package com.example.mohit.listviewtodo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import static android.content.DialogInterface.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
                                                    AdapterView.OnItemLongClickListener {

    ListView listView;
    ArrayList<ToDo> todos;
    AdapterTODO todo;
    final String TITLE_KEY="title_name";
    final String CONTENT_KEY="content_name";
    final String POSITION_KEY="position_string";
    final String DATE_KEY="date";
    final String TIME_KEY="time";

    ToDoOpenHelper openHelper;
    SQLiteDatabase database;
    Toolbar toolbar;
    ImportantButton importantButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listView);
        todos=new ArrayList<>();

        importantButton=new ImportantButton() {
            @Override
            public void isImportant(View view,int position) {
                
            }
        };

        todo=new AdapterTODO(this,todos,importantButton);
        listView.setAdapter(todo);

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        openHelper=ToDoOpenHelper.getInstance(this);//rather than "this" use -> getApplicationContext
                                                    // Used in the ToDo class itself
        database=openHelper.getWritableDatabase();

//        int amountGreaterThan=0;
//        String[] selectionArgument={amountGreaterThan+""};

//        String[] columns={Contract.TODO.COLUMN_ID,Contract.TODO.COLUMN_CONTENT,
//                Contract.TODO.COLUMN_TITLE, Contract.TODO.COLUMN_TIME, Contract.TODO.COLUMN_DATE};

        Cursor cursor=database.query(Contract.TODO.TABLE_NAME,null,null,
                                null,null,null,null);

        while(cursor.moveToNext())
        {
            int id=cursor.getInt(cursor.getColumnIndex(Contract.TODO.COLUMN_ID));
            String title=cursor.getString(cursor.getColumnIndex(Contract.TODO.COLUMN_TITLE));
            String content=cursor.getString(cursor.getColumnIndex(Contract.TODO.COLUMN_CONTENT));
            String time=cursor.getString(cursor.getColumnIndex(Contract.TODO.COLUMN_TIME));
            String date=cursor.getString(cursor.getColumnIndex(Contract.TODO.COLUMN_DATE));

            ToDo toDo=new ToDo();
            toDo.setId(id);
            toDo.setTitle(title);
            toDo.setContent(content);
            toDo.setTime(time);
            toDo.setDate(date);
            todos.add(toDo);
        }
        todo.notifyDataSetChanged();
        cursor.close();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Main3Activity.class);
                startActivityForResult(intent,2);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==R.id.showImportant)
        {
            //TODO Make Another Activity to show Important
        }
        return true;
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, View view, int i, long l)
    {
        final int position=i;
        Intent intent=new Intent(this,Main2Activity.class);
        intent.putExtra(TITLE_KEY,todos.get(position).getTitle());
        intent.putExtra(CONTENT_KEY,todos.get(position).getContent());
        intent.putExtra(POSITION_KEY,""+position);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode==1)
            {
                String positionString=data.getStringExtra(POSITION_KEY);
                String title=data.getStringExtra(TITLE_KEY);
                String content=data.getStringExtra(CONTENT_KEY);
                int position=Integer.parseInt(positionString);

                openHelper=ToDoOpenHelper.getInstance(this);
                database=openHelper.getWritableDatabase();
                ContentValues contentValues=new ContentValues();
                contentValues.put(Contract.TODO.COLUMN_CONTENT,content);
                contentValues.put(Contract.TODO.COLUMN_TITLE,title);
                database.update(Contract.TODO.TABLE_NAME,contentValues,
                        Contract.TODO.COLUMN_ID+" = "+todos.get(position).getId(),
                                                                null);

                ToDo temp=new ToDo();
                temp.setTitle(title);
                temp.setContent(content);
                todos.set(position,temp);
                todo.notifyDataSetChanged();
            }
            else
            {

            }
        }
        else if(requestCode==2)
        {
            if(resultCode==1)
            {
                String title=data.getStringExtra(TITLE_KEY);
                String content=data.getStringExtra(CONTENT_KEY);
                String time=data.getStringExtra(TIME_KEY);
                String date=data.getStringExtra(DATE_KEY);
                ToDo temp=new ToDo();
                temp.setTitle(title);
                temp.setContent(content);
                temp.setTime(time);
                temp.setDate(date);

                openHelper=ToDoOpenHelper.getInstance(this);
                database=openHelper.getWritableDatabase();
                ContentValues contentValues=new ContentValues();
                contentValues.put(Contract.TODO.COLUMN_TITLE,title);
                contentValues.put(Contract.TODO.COLUMN_CONTENT,content);
                contentValues.put(Contract.TODO.COLUMN_TIME,time);
                contentValues.put(Contract.TODO.COLUMN_DATE,date);
                long i=database.insert(Contract.TODO.TABLE_NAME,null,contentValues);

                todos.add(temp);
                todo.notifyDataSetChanged();
            }
            else
            {

            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        final int position=i;
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Confirm?");
        builder.setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(MainActivity.this, position+"th item deleted", Toast.LENGTH_SHORT).show();
                database.delete(Contract.TODO.TABLE_NAME,Contract.TODO.COLUMN_ID+" = "+todos.get(position).getId(),null);
                todos.remove(position);
                todo.notifyDataSetChanged();
            }
        });
        builder.create().show();
        return true;
    }

}