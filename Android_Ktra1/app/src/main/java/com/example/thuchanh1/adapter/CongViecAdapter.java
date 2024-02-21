package com.example.thuchanh1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.thuchanh1.R;
import com.example.thuchanh1.model.CongViec;

import java.util.List;

public class CongViecAdapter extends ArrayAdapter {
    private Context context;
    private List<CongViec> congViecs;
    private CongViecItemListener congViecItemListener;

    public CongViecAdapter(@NonNull Context context, @NonNull List congViecs) {
        super(context, R.layout.item, congViecs);

        this.context = context;
        this.congViecs = congViecs;
    }

    public void setCongViecItemListener(CongViecItemListener congViecItemListener) {
        this.congViecItemListener = congViecItemListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, null, true);

        ImageView gioitinh = v.findViewById(R.id.imageView);
        TextView ten = v.findViewById(R.id.textView4);
        TextView noidung = v.findViewById(R.id.textView5);
        TextView ngay = v.findViewById(R.id.textView6);

        CongViec congViec = congViecs.get(position);
        if (congViec.getGioitinh().equalsIgnoreCase("Nam")) {
            gioitinh.setImageResource(R.drawable.ic_male);
        } else {
            gioitinh.setImageResource(R.drawable.ic_female);
        }
        ten.setText(congViec.getTen());
        noidung.setText(congViec.getNoidung());
        ngay.setText(congViec.getNgayhoanthanh());

        //Remove CV
        Button removeButton = v.findViewById(R.id.button4);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                congViecs.remove(position);
                notifyDataSetChanged();
            }
        });

        // Set on item clicked
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                congViecItemListener.onItemClicked(v,position);
            }
        });

        return v;
    }

    //Item clicked sent to Main
    public interface CongViecItemListener {
        void onItemClicked(View v, int position);
    }
}

