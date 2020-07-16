package com.irvan.sqlcrud_18111167_irvanmaulana;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {
    List<Employee> employeeList;
    SQLiteDatabase mDatabase;
    ListView listViewEmployees;
    EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        listViewEmployees = (ListView) findViewById(R.id.listViewEmployee);
        employeeList = new ArrayList<>();

        //Buka Database SQLite
        mDatabase = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);

        //panggil method ini untuk menampilkan pada list
        showEmployeesFormDatabase();
    }

    private void showEmployeesFormDatabase(){
        //buat Query untuk memanggil data pada table employees
        Cursor cursorEmployees = mDatabase.rawQuery("SELECT * FROM employees", null);

        //juka kursor memiliki beberapa data
        if(cursorEmployees.moveToFirst()){
            //lakukan perulangan untuk membaca data berikutnya
            do{
                //Ambil data ke daftar karyawan berupa list
                employeeList.add(new Employee(
                        cursorEmployees.getInt(0),
                        cursorEmployees.getString(1),
                        cursorEmployees.getString(2),
                        cursorEmployees.getString(3),
                        cursorEmployees.getDouble(4)
                ));
            }while (cursorEmployees.moveToNext());
        }
        //tutup kursor
        cursorEmployees.close();

        //buat objek adapter
        adapter = new EmployeeAdapter(this, R.layout.list_layout_employee, employeeList, mDatabase);

        //Tambah data pada adapter ke listView
        listViewEmployees.setAdapter(adapter);
    }
}
