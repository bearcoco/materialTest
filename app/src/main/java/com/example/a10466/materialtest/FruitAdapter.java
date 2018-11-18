package com.example.a10466.materialtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by 10466 on 2018/11/15.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder>{
    private List<Fruit> mFruitList ;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View view) {
            super(view);
            cardView=(CardView) view ;
            fruitImage=view.findViewById(R.id.fruit_image);
            fruitName=view.findViewById(R.id.fruit_name);
        }
    }
    public FruitAdapter(List<Fruit> fruitList){
        mFruitList = fruitList ;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item,parent,false);
        final ViewHolder holder =new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit =mFruitList.get(position);
                Intent intent = new Intent(mContext,FruitAcitvity.class);
                intent.putExtra(FruitAcitvity.FRUIT_NAME,fruit.getName());
                intent.putExtra(FruitAcitvity.FRUIT_IMAGE_ID,fruit.getImageId());
                mContext.startActivity(intent);

            }
        });
        return holder;
        /*return new ViewHolder(view);*/
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit =mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);//使用Glide.with()方法传入一个Context、Activity或Fragment参数，
        //然后调用load()方法去加载图片，可以是一个URL地址，也可以是一个本地路径，或者是一个资源id，最后调用into()方法降图片设置到具体某一个ImageView中就可以了。

    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }



}
