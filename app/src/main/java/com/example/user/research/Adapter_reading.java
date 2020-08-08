package com.example.user.research;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Adapter_reading extends RecyclerView.Adapter<Adapter_reading.Viewholder_reading> {
    private Context mcontext;
    private List<Model_reading> muploads;

    public Adapter_reading(Context mcontext, List<Model_reading> muploads) {
        this.mcontext = mcontext;
        this.muploads = muploads;
    }

    @NonNull
    @Override
    public Viewholder_reading onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mcontext).inflate(R.layout.card_view_reading,viewGroup,false);

        return new Viewholder_reading(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder_reading viewholder_reading, int i) {
       Model_reading mr=muploads.get(i);

       viewholder_reading.tv1.setText(mr.getBpm());
//       viewholder_reading.tv2.setText(mr.getHum());
       viewholder_reading.tv3.setText(mr.getTemp());
    }

    @Override
    public int getItemCount() {
        return muploads.size();
    }

    public class Viewholder_reading extends RecyclerView.ViewHolder {
        TextView tv1,tv2,tv3;
        public Viewholder_reading(@NonNull View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.textView10);
            tv2 = itemView.findViewById(R.id.textView11);
            tv3 = itemView.findViewById(R.id.textView9);

        }
    }

}
