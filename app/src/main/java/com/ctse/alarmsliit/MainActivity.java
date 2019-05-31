package com.ctse.alarmsliit;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity {
    private ListAdapter todoListAdapter;
    private SQLHelper SQLHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLHelper = new SQLHelper(MainActivity.this);
        sqLiteDatabase = SQLHelper.getReadableDatabase();
        updateTodoList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
            Intent intent  = new Intent(MainActivity.this, Alarm.class);
            startActivity(intent);
            return true;

            default:
                return false;
        }
    }

    public void updateTodoList() {

        Cursor cursor = sqLiteDatabase.query(SQLHelper.TABLE_NAME,
                new String[]{SQLHelper._ID, SQLHelper.COL1_TASK, SQLHelper.COL2_TASK},
                null, null, null, null, null);

        todoListAdapter = new SimpleCursorAdapter(
                this,
                R.layout.alarmtask,
                cursor,
                new String[]{SQLHelper.COL2_TASK, SQLHelper.COL1_TASK},
                new int[]{R.id.newTime, R.id.alarm},0

        );
        this.setListAdapter(todoListAdapter);
    }

    public void onDoneButtonClick(View view) {
        View v = (View) view.getParent();
        TextView alarm = (TextView) v.findViewById(R.id.alarm);
        TextView time = (TextView) v.findViewById(R.id.newTime);
        String alarmItem = alarm.getText().toString();
        String timeItem = time.getText().toString();

        String deleteTodoItemSql = "DELETE FROM " + SQLHelper.TABLE_NAME +
                " WHERE " + SQLHelper.COL1_TASK + " = '" + alarmItem + "'" + " AND " + SQLHelper.COL2_TASK + " = '" + timeItem + "'";

        SQLHelper = new SQLHelper(MainActivity.this);
        SQLiteDatabase sqlDB = SQLHelper.getWritableDatabase();
        sqlDB.execSQL(deleteTodoItemSql);
        updateTodoList();
    }
}
