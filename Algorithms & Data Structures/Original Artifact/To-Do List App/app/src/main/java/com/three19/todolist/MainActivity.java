package com.three19.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.three19.todolist.database.ToDoListDB;
import com.three19.todolist.model.ToDo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ToDoListDB toDoListDB;
    List<ToDo> arrayList;
    ToDoListAdapter adapter;
    ToDo selectedToDo;
    int selectedPosition;
    Integer indexVal;
    String item;
    EditText txtName;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (EditText) findViewById(R.id.txtName);

        toDoListDB = new ToDoListDB(this);
        arrayList = toDoListDB.getList();

        adapter = new ToDoListAdapter(this, (ArrayList<ToDo>) arrayList);

        ListView listView = (ListView) findViewById(R.id.lstView);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                removeItemFromList(position);
                return true;
            }
        });
        // This section will allow the user to click an item and update its text
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedToDo = arrayList.get(position);
                selectedPosition = position;
                txtName.setText(selectedToDo.getName());

                findViewById(R.id.txtName);
                item = parent.getItemAtPosition(position).toString();
                indexVal = position;
                Toast.makeText(MainActivity.this, "Existing Task Selected",
                        Toast.LENGTH_SHORT).show();

                Button updateBtn = (Button) findViewById(R.id.btnAdd);
                updateBtn.setText("Update");
                updateBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        EditText txtName = (EditText) findViewById(R.id.txtName);
                        String name = txtName.getText().toString();
                        ToDo selectedToDo = arrayList.get(indexVal);
                        selectedToDo.setName(name);
                        toDoListDB.update(selectedToDo);
                        arrayList.set(indexVal, selectedToDo);
                        adapter.notifyDataSetChanged();
                        txtName.setText("");
                        Toast.makeText(MainActivity.this, "Task Updated.",
                                Toast.LENGTH_SHORT).show();
                        updateBtn.setText("Add");

                        addBtn = (Button) findViewById(R.id.btnAdd);
                        addBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                EditText txtName = (EditText) findViewById(R.id.txtName);
                                String name = txtName.getText().toString();

                                if (name.trim().length() > 0) {
                                    ToDo toDo = toDoListDB.add(name);

                                    arrayList.add(toDo);
                                    adapter.notifyDataSetChanged();

                                    txtName.setText("");
                                }

                            }
                        });
                    }
                });
            }
        });

            addBtn = (Button) findViewById(R.id.btnAdd);
            addBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    EditText txtName = (EditText) findViewById(R.id.txtName);
                    String name = txtName.getText().toString();

                    if (name.trim().length() > 0) {
                        ToDo toDo = toDoListDB.add(name);

                        arrayList.add(toDo);
                        adapter.notifyDataSetChanged();

                        txtName.setText("");
                    }

                }
            });


        Button clearBtn = (Button) findViewById(R.id.btnClear);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reset();
            }
        });

        Button allBtn = (Button) findViewById(R.id.btnAll);
        allBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AllTasksActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void removeItemFromList(final int position)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToDo toDo = arrayList.get(position);

                arrayList.remove(position);
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();

                toDoListDB.remove(toDo.getId());
                reset();
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    protected void reset() {
        txtName.setText("");

        // add an appropriate addBtn() call here

        selectedToDo = null;
        selectedPosition = -1;
    }
}

class ToDoListAdapter extends ArrayAdapter<ToDo>
{
    public ToDoListAdapter(Context context, ArrayList<ToDo> toDoList) {
        super(context, 0, toDoList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ToDo toDo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(android.R.id.text1);
        name.setText(toDo.getName());

        return convertView;
    }
}