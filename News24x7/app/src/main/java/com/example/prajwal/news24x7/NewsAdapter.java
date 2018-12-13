package com.example.prajwal.news24x7;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import com.bumptech.glide.Glide;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private Context mContext;
    private List<News> cardList;

    public NewsAdapter(Context mContext, List<News> cardList)
    {
        this.mContext = mContext;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder item, int i) {
        final News currentItem=cardList.get(i);
        item.mTitle.setText(currentItem.getmTitle());
        item.mSource.setText(currentItem.getmSource());
        String desc=currentItem.getmDescription();
        if(desc!=null&&desc!="null")
            item.mDesc.setText(desc);
        String date=currentItem.getmDate();
        if(date!=null){
            StringBuilder newDate=new StringBuilder();
            newDate.append(date);
            newDate.deleteCharAt(10);
            newDate.deleteCharAt(18);
            newDate.insert(10,' ');
            newDate.insert(10,' ');
            newDate.insert(10,' ');
            newDate.append(" UTC");
            item.mDate.setText(newDate.toString());
        }
        String imgUri = currentItem.getmImageUrl();
        if(imgUri!=null){
            Glide.with(mContext).load(imgUri).into(item.mImage);
        }

        item.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri=currentItem.getmUrl();
                Uri url =Uri.parse(uri);
                Intent intent =new Intent(Intent.ACTION_VIEW);
                intent.setData(url);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return cardList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mSource;
        public TextView mDesc;
        public TextView mDate;
        public ImageView mImage;
        public View view;

        public MyViewHolder(View view){
            super(view);
            mImage = (ImageView) view.findViewById(R.id.image);
            mDate = (TextView) view.findViewById(R.id.date);
            mDesc = (TextView) view.findViewById(R.id.desc);
            mSource = (TextView) view.findViewById(R.id.source);
            mTitle = (TextView) view.findViewById(R.id.title);
            this.view = view;
        }
    }

    
}
