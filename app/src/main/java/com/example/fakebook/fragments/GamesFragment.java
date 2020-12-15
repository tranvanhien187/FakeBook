package com.example.fakebook.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fakebook.R;
import com.example.fakebook.adapters.Gvbanco_adapter;

import java.util.Random;

public class GamesFragment extends Fragment {
    GridView gvbandatcuoc;
    int icons[] = {R.drawable.icon_nai, R.drawable.icon_bau, R.drawable.icon_ga, R.drawable.icon_ca, R.drawable.icon_cua, R.drawable.icon_tom};
    int tiencuocitem[];
    static int tien = 2000;
    CountDownTimer ctime;
    AnimationDrawable animation_xx1, animation_xx2, animation_xx3;
    ImageView img_xx1, img_xx2, img_xx3;
    TextView txttien, txtthoigian, txttiencuocitem;
    Button btn_vanmoi;
    Gvbanco_adapter adapter;
    int[] manghinh = new int[]{R.drawable.icon_nai, R.drawable.icon_bau, R.drawable.icon_ga, R.drawable.icon_ca, R.drawable.icon_cua, R.drawable.icon_tom};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_games, container, false);
        txttien = v.findViewById(R.id.txttien);
        txtthoigian = v.findViewById(R.id.txtthoigian);
        gvbandatcuoc = v.findViewById(R.id.gvbandatcuoc);
        img_xx1 = v.findViewById(R.id.img_xx1);
        img_xx2 = v.findViewById(R.id.img_xx2);
        img_xx3 = v.findViewById(R.id.img_xx3);
        btn_vanmoi = v.findViewById(R.id.btn_vanmoi);
        animation_xx1 = (AnimationDrawable) img_xx1.getDrawable();
        animation_xx2 = (AnimationDrawable) img_xx2.getDrawable();
        animation_xx3 = (AnimationDrawable) img_xx3.getDrawable();

        tiencuocitem = new int[6];
        adapter = new Gvbanco_adapter(getContext(), icons, tiencuocitem);
        gvbandatcuoc.setAdapter(adapter);

        if (tien > 0) {
            btn_vanmoi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    play();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Nap them tien de choi", Toast.LENGTH_LONG).show();
        }
        String stien = String.valueOf(tien);
        txttien.setText("" + stien);
        return v;
    }

    public void play() {


        ctime= new CountDownTimer(13000, 1000) {
            int time = 10;

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTick(long millisUntilFinished) {
                btn_vanmoi.setVisibility(View.INVISIBLE);
                if (time > 0) {
                    time--;
                    String stime = String.valueOf(time);
                    txtthoigian.setText("Thời gian: " + stime);

                    gvbandatcuoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            txttiencuocitem = view.findViewById(R.id.txttiencuoc);
                            if(tien>0) {
                                tiencuocitem[position] += 100;
                                String tiencuoc = String.valueOf(tiencuocitem[position]);
                                txttiencuocitem.setText(" " + tiencuoc);
                                tien -= 100;
                                String stien = String.valueOf(tien);
                                txttien.setText("" + stien);
                            }

                        }
                    });
                } else {
                    gvbandatcuoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(getActivity(),"Hết thời gian đặt cược",Toast.LENGTH_SHORT).show();
                        }
                    });
                    txtthoigian.setText("Hết giờ!");
                    img_xx1.setImageDrawable(animation_xx1);
                    img_xx2.setImageDrawable(animation_xx2);
                    img_xx3.setImageDrawable(animation_xx3);
                    animation_xx1.start();
                    animation_xx2.start();
                    animation_xx3.start();
                }
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onFinish() {
                Random random_xx = new Random();
                int r_xx1 = random_xx.nextInt(manghinh.length);
                int r_xx2 = random_xx.nextInt(manghinh.length);
                int r_xx3 = random_xx.nextInt(manghinh.length);

                img_xx1.setImageResource(manghinh[r_xx1]);
                img_xx2.setImageResource(manghinh[r_xx2]);
                img_xx3.setImageResource(manghinh[r_xx3]);
                btn_vanmoi.setVisibility(View.VISIBLE);

                for(int i=0; i<manghinh.length;i++){
                    boolean check = true;
                    int von = tiencuocitem[i];
                    adapter.tiencuoc[i]=0;
                    adapter.notifyDataSetChanged();
                    if(i == r_xx1 )
                    {
                        check=false;
                        tien+=von;
                    }
                    if(i==r_xx2)
                    {
                        check=false;
                        tien+=von;
                    }
                    if(i==r_xx3)
                    {
                        check=false;
                        tien+=von;
                    }
                    if(check==false) tien+=von;
                    String stien = String.valueOf(tien);
                    txttien.setText(""+stien);
                }
            }

        }.start();
    }
}
