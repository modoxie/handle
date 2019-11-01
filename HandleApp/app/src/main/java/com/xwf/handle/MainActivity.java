package com.xwf.handle;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xwf.handle.fragment.HuoJuFragment;
import com.xwf.handle.tr.TJob;
import com.xwf.handle.utils.MyViewHolder;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    LinkedList<String> title;
    HashMap<String, Fragment> uis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText et = findViewById(R.id.et);
        Button bt = findViewById(R.id.bt);
        LinearLayout ll = findViewById(R.id.ll_ip);
        RecyclerView rv = findViewById(R.id.rv);
        SharedPreferences spf = getSharedPreferences("mspf", MODE_PRIVATE);
        String sip = spf.getString("ip", null);
        if (sip != null) {
            et.setText(sip);
        }
        bt.setOnClickListener(v -> {
            String ip = et.getText().toString();
            if (ip.length() < 1 || ip.trim().length() < 1) {
                Toast.makeText(this, "请输入ip", Toast.LENGTH_SHORT).show();
                return;
            }
            new Thread(() -> {
                try {
                    Socket socket = new Socket(ip, 5366);
                    TJob.getInstance().start(socket);
                    bt.post(() -> {
                        ll.setVisibility(View.GONE);
                        rv.setVisibility(View.VISIBLE);
                        SharedPreferences.Editor edit = spf.edit();
                        edit.putString("ip", ip);
                        edit.apply();
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        });
        title = new LinkedList<>();
        uis = new HashMap<>();
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 1;
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(new RecyclerView.Adapter<MyViewHolder>() {
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(rv.getContext()).inflate(R.layout.rv_text, parent, false);
                MyViewHolder holder = new MyViewHolder(view);
                holder.itemView.setOnClickListener(v -> {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    //选择fragment替换的部分
                    Fragment fragment = uis.get(title.get((Integer) v.getTag()));
                    if (fragment == null) {
                        fragment = createFragment(title.get((Integer) v.getTag()));
                    }
                    uis.put(title.get((Integer) v.getTag()), fragment);
                    ft.replace(R.id.fragment, fragment);
                    ft.commit();
                    rv.setVisibility(View.GONE);
                });
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                ((TextView) holder.itemView).setText(title.get(position));
                holder.itemView.setTag(position);
            }

            @Override
            public int getItemCount() {
                return title.size();
            }
        });
        title.add("火炬之光");
        Objects.requireNonNull(rv.getAdapter()).notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        TJob.getInstance().stop();
        super.onDestroy();
    }

    private Fragment createFragment(String title) {
        switch (title) {
            case "火炬之光":
                return new HuoJuFragment();
        }
        return null;
    }
}
