package com.example.thuchanh1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuchanh1.adapter.CongViecAdapter;
import com.example.thuchanh1.model.CongViec;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CongViecAdapter.CongViecItemListener {
    private ListView listView;
    private List<CongViec> congViecs = new ArrayList<>();
    private Button dateButton, addButton, updateButton;
    private EditText txtTen, txtNoidung;
    private TextView txtNgay;
    private RadioGroup groupGioiTinh;
    private int position; // Item click position

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        dateButton = findViewById(R.id.button);
        txtNgay = findViewById(R.id.textView7);
        addButton = findViewById(R.id.button2);
        updateButton = findViewById(R.id.button3);
        txtTen = findViewById(R.id.editTextTextPersonName);
        txtNoidung = findViewById(R.id.editTextTextPersonName2);

        groupGioiTinh = findViewById(R.id.radioGroup2);

        // listView
        initData();
        CongViecAdapter congViecAdapter = new CongViecAdapter(this, congViecs);
        congViecAdapter.setCongViecItemListener(this);
        listView.setAdapter(congViecAdapter);

        // datePickerDialog
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int cy = c.get(Calendar.YEAR);
                int cm = c.get(Calendar.MONTH);
                int cd = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                                txtNgay.setText(d + "/" + m + "/" + y);
                            }
                        }, cy, cm, cd);
                dialog.show();
            }
        });

        //Add CV
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = txtTen.getText().toString();
                String noidung = txtNoidung.getText().toString();
                String ngay = txtNgay.getText().toString();
                RadioButton buttonGioitinh = findViewById(groupGioiTinh.getCheckedRadioButtonId());
                String gioitinh = buttonGioitinh.getText().toString();

                if (ten.isEmpty() || noidung.isEmpty() || ngay.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    CongViec cv = new CongViec(ten, noidung, gioitinh, ngay);
                    congViecs.add(cv);
                    congViecAdapter.notifyDataSetChanged();
                }

                hideKeyboard();
                updateButton.setEnabled(false);
            }
        });

        //Update CV
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = txtTen.getText().toString();
                String noidung = txtNoidung.getText().toString();
                String ngay = txtNgay.getText().toString();
                RadioButton buttonGioitinh = findViewById(groupGioiTinh.getCheckedRadioButtonId());
                String gioitinh = buttonGioitinh.getText().toString();

                if (ten.isEmpty() || noidung.isEmpty() || ngay.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    CongViec cv = new CongViec(ten, noidung, gioitinh, ngay);
                    congViecs.set(position, cv);
                    congViecAdapter.notifyDataSetChanged();
                }

                hideKeyboard();
                updateButton.setEnabled(false);
            }
        });

    }

    //listView onItemClicked
    @Override
    public void onItemClicked(View v, int position) {
        updateButton.setEnabled(true);
        this.position = position;

        CongViec congViec = congViecs.get(position);
        txtTen.setText(congViec.getTen());
        txtNoidung.setText(congViec.getNoidung());
        txtNgay.setText(congViec.getNgayhoanthanh());
        if (congViec.getGioitinh().equalsIgnoreCase("Nam")) {
            groupGioiTinh.check(R.id.radioButton);
        } else {
            groupGioiTinh.check(R.id.radioButton2);
        }
    }

    private void initData(){
        CongViec cv1 = new CongViec("Viec 1", "Noi dung 1", "Nam", "1/3/2023");
        CongViec cv2 = new CongViec("Viec 2", "Noi dung 2", "Nữ", "2/3/2023");
        CongViec cv3 = new CongViec("Viec 3", "Noi dung 3", "Nữ", "3/3/2023");
        CongViec cv4 = new CongViec("Viec 4", "Noi dung 4", "Nam", "4/3/2023");

        congViecs.add(cv1);
        congViecs.add(cv2);
        congViecs.add(cv3);
        congViecs.add(cv4);
        congViecs.add(cv1);
        congViecs.add(cv2);
        congViecs.add(cv3);
        congViecs.add(cv4);
    }

    private void hideKeyboard(){
        //Hide keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}