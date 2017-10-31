package com.example.asus.parkingidiots;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.parkingidiots.MainActivity;
import com.example.asus.parkingidiots.R;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Post> dataSet;



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewTime;
        ImageView post_photo;
        TextView post_desc;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.person_name);
            this.textViewTime = (TextView) itemView.findViewById(R.id.time);
            this.post_photo = (ImageView) itemView.findViewById(R.id.post_photo);
            this.post_desc = (TextView) itemView.findViewById(R.id.post_description);
        }
    }

    public CustomAdapter(ArrayList<Post> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewTime = holder.textViewTime;
        TextView postDescription = holder.post_desc;
        ImageView post_photo = holder.post_photo;

        textViewName.setText(dataSet.get(listPosition).getUserName());
        textViewTime.setText(dataSet.get(listPosition).getDate());
        postDescription.setText(dataSet.get(listPosition).getAuthorText());
        try {
            final String url = "http://missho-testing.aspone.cz" + dataSet.get(listPosition).getImg() ;
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
            post_photo.setImageBitmap(bitmap);
            post_photo.setScaleType(ImageView.ScaleType.FIT_CENTER);
            post_photo.setAdjustViewBounds(true);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //imageView.setImageResource(dataSet.get(listPosition).getImage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}