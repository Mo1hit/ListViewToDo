package com.example.mohit.listviewtodo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterTODO extends ArrayAdapter
{
    ArrayList<ToDo> todos;
    LayoutInflater layoutInflater;
    ImportantButton importantButton;

    public AdapterTODO(@NonNull Context context, ArrayList<ToDo> todos,ImportantButton importantButton)
    {
        super(context, 0,todos);
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.todos=todos;
        this.importantButton=importantButton;
    }

    @Override
    public int getCount()
    {
        return todos.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View output=convertView;
        if(output==null)
        {
            output=layoutInflater.inflate(R.layout.todo_row_layout,parent,false);
            TextViewsTODO textViewsTODO=new TextViewsTODO();
            textViewsTODO.titleview=output.findViewById(R.id.titleid);
            textViewsTODO.contentview=output.findViewById(R.id.contentid);
            textViewsTODO.timeview=output.findViewById(R.id.timeid);
            textViewsTODO.dateview=output.findViewById(R.id.dateid);
            output.setTag(textViewsTODO);
        }
        TextViewsTODO textViewsTODO=(TextViewsTODO) output.getTag();
        textViewsTODO.titleview.setText(todos.get(position).getTitle());
        textViewsTODO.contentview.setText(todos.get(position).getContent());
        textViewsTODO.dateview.setText(todos.get(position).getDate());
        textViewsTODO.timeview.setText(todos.get(position).getTime());

        return output;
    }
}