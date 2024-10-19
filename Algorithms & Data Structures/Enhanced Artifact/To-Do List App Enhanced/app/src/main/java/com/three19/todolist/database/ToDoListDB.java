package com.three19.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.three19.todolist.model.ToDo;

import java.util.LinkedList;

// This method implements the CRUD functionality for the SQLite DatabaseA
public class ToDoListDB extends DBConnection {

    public ToDoListDB(Context context) {
        super(context);
    }

    // This function allows the add button to save tasks to the database
    public ToDo add(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ToDo toDo = new ToDo();
        toDo.setName(name);

        ContentValues values = toDo.getContentValuesToAdd();
        Long id = db.insert("todolist", null, values);
        db.close();

        toDo.setId(id.intValue());

        return toDo;
    }

    public void update(ToDo toDo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = toDo.getContentValuesToUpdate();
        db.update("todolist", values, "id = ?", new String[]{String.valueOf(toDo.getId())});
        db.close();
    }

    public void remove(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM todolist WHERE id = "+ id);
        db.close();
    }

    /*
    public ToDo getDetails(int id) {
        String selectQuery = "SELECT * FROM campsites WHERE id = "+ id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        ToDo toDo = new ToDo();
        toDo.parse(cursor);

        db.close();
        return toDo;
    }
     */

    // This retrieves the linked list from the database
    public LinkedList<ToDo> getList() {
        // The list below is the old ArrayList setup for storing tasks
        // List<ToDo> toDoList = new ArrayList<>();

        // This is the new linked list setup
        LinkedList<ToDo> toDoLinked = new LinkedList<ToDo>();

        String selectQuery = "SELECT * FROM todolist";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ToDo toDo = new ToDo();
                toDo.parse(cursor);
                toDoLinked.add(toDo);
            } while (cursor.moveToNext());
        }

        db.close();
        return toDoLinked;
    }
}
