package com.example.asus.parkingidiots;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Post> dataSet;



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewTime;
        TextView totalLikes;
        TextView totalViews;
        ImageView post_photo;
        TextView post_desc;
        ImageView buttonLike;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.person_name);
            this.textViewTime = (TextView) itemView.findViewById(R.id.time);
            this.post_photo = (ImageView) itemView.findViewById(R.id.post_photo);
            this.post_desc = (TextView) itemView.findViewById(R.id.post_description);
            this.totalLikes = (TextView) itemView.findViewById(R.id.post_totalLikes);
            this.totalViews = (TextView) itemView.findViewById(R.id.post_totalComments);
            this.buttonLike = (ImageView) itemView.findViewById(R.id.button_like);
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
        final TextView totalLikes= holder.totalLikes;
        TextView totalViews = holder.totalViews;
        ImageView post_photo = holder.post_photo;
        final ImageView buttonLike = holder.buttonLike;
        buttonLike.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                buttonLike.setBackgroundResource(R.drawable.like2);
                String likes = totalLikes.getText().toString();
                Integer likeNumber = Integer.parseInt(likes);
                ++likeNumber;
                totalLikes.setText(likeNumber.toString());
            }
        });
        if(dataSet.get(listPosition)  == null)
        {
            return;
        }

        Long date = Long.parseLong(dataSet.get(listPosition).getDate().replaceAll("\\D+",""));
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        if(dataSet.get(listPosition) != null ) {
            textViewName.setText(dataSet.get(listPosition).getUserName());
            textViewTime.setText(df.format((new Date(date))));
            postDescription.setText(dataSet.get(listPosition).getAuthorText());
            totalLikes.setText(dataSet.get(listPosition).getLikes().toString());
            totalViews.setText(dataSet.get(listPosition).getViews().toString());
            try {
                final String url = "http://missho-testing.aspone.cz" + dataSet.get(listPosition).getImg();
                Picasso.with(holder.post_photo.getContext())
                        .load(url)
                        .resize(400, 300) // resizes the image to these dimensions (in pixel)
                        .centerCrop()
                        .into(post_photo);

                post_photo.setScaleType(ImageView.ScaleType.FIT_CENTER);
                post_photo.setAdjustViewBounds(true);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //imageView.setImageResource(dataSet.get(listPosition).getImage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}