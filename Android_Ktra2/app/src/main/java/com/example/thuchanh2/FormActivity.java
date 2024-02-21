package com.example.thuchanh2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thuchanh2.database.Database;
import com.example.thuchanh2.model.CongViec;

import java.util.Calendar;

public class FormActivity extends AppCompatActivity {
    Button addButton, cancelButton, dateButton, updateButton, removeButton;
    EditText txtTen, txtNoiDung;
    TextView txtNgay;
    RadioGroup tinhTrangRadioGroup;
    CheckBox congTacCheckBox;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);

        addButton = findViewById(R.id.addButton);
        cancelButton = findViewById(R.id.cancelButton);
        txtTen = findViewById(R.id.editTextTenCV);
        txtNoiDung = findViewById(R.id.editTextNoidungCV);
        txtNgay = findViewById(R.id.textViewNgay);
        dateButton = findViewById(R.id.dateButton);
        tinhTrangRadioGroup = findViewById(R.id.radioGroup);
        congTacCheckBox = findViewById(R.id.checkBox);
        updateButton = findViewById(R.id.updateButton);
        removeButton = findViewById(R.id.removeButton);

        Database db = new Database(this);

        //Intent
        Intent intent = getIntent();
        CongViec congViec = (CongViec) intent.getSerializableExtra("congviec");

        if (congViec == null) {
            updateButton.setVisibility(View.GONE);
            removeButton.setVisibility(View.GONE);
        } else {
            txtTen.setText(congViec.getTen());
            txtNoiDung.setText(congViec.getNoidung());
            txtNgay.setText(congViec.getNgayHoanThanh());
            switch (congViec.getTinhtrang()) {
                case 0:
                    tinhTrangRadioGroup.check(R.id.radioButton);
                    break;
                case 1:
                    tinhTrangRadioGroup.check(R.id.radioButton1);
                    break;
                case 2:
                    tinhTrangRadioGroup.check(R.id.radioButton2);
                    break;
            }
            congTacCheckBox.setChecked(congViec.isCongTac());

            addButton.setVisibility(View.GONE);
        }

        //Date Button
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int cy = c.get(Calendar.YEAR);
                int cm = c.get(Calendar.MONTH);
                int cd = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(FormActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                                txtNgay.setText(d + "/" + m + "/" + y);
                            }
                        }, cy, cm, cd);
                dialog.show();
            }
        });

        //Cancel Button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Add Button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = txtTen.getText().toString();
                String noidung = txtNoiDung.getText().toString();
                String ngay = txtNgay.getText().toString();
                int tinhtrang = 0;
                boolean congtac = congTacCheckBox.isChecked();

                switch (tinhTrangRadioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioButton:
                        tinhtrang = 0;
                        break;
                    case R.id.radioButton1:
                        tinhtrang = 1;
                        break;
                    case R.id.radioButton2:
                        tinhtrang = 2;
                        break;
                }

                if (ten.isEmpty() || noidung.isEmpty() || ngay.isEmpty()) {
                    Toast.makeText(FormActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    CongViec congViecAdded = new CongViec(ten, noidung, ngay, tinhtrang, congtac);
                    db.insertCV(congViecAdded);
                    finish();
                }

            }
        });

        //Update Button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = txtTen.getText().toString();
                String noidung = txtNoiDung.getText().toString();
                String ngay = txtNgay.getText().toString();
                int tinhtrang = 0;
                boolean congtac = congTacCheckBox.isChecked();

                switch (tinhTrangRadioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioButton:
                        tinhtrang = 0;
                        break;
                    case R.id.radioButton1:
                        tinhtrang = 1;
                        break;
                    case R.id.radioButton2:
                        tinhtrang = 2;
                        break;
                }

                if (ten.isEmpty() || noidung.isEmpty() || ngay.isEmpty()) {
                    Toast.makeText(FormActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    congViec.setTen(ten);
                    congViec.setNoidung(noidung);
                    congViec.setNgayHoanThanh(ngay);
                    congViec.setTinhtrang(tinhtrang);
                    congViec.setCongTac(congtac);
                    db.updateCV(congViec);
                    finish();
                }
            }
        });

        //Remove Button
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteCV(congViec.getMa());
                finish();
            }
        });
    }
}
