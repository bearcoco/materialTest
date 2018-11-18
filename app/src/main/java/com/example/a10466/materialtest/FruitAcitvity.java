package com.example.a10466.materialtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by 10466 on 2018/11/16.
 */

public class FruitAcitvity extends AppCompatActivity{
    public static final String FRUIT_NAME="fruit_name";
    public static final String FRUIT_IMAGE_ID="fruit_image_id";
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        Intent intent =getIntent();//在onCreate()中通过Intent获取到传入的水果名和水果图片的资源id，然后通过findViewbyId()方法拿到刚才在布局中定义的各个控件的实例
        String fruitName = intent.getStringExtra(FRUIT_NAME);
        int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID,0);
        CollapsingToolbarLayout collapsingToolbar =findViewById(R.id.collapsing_toolbar);
        ImageView fruitImageView= findViewById(R.id.fruit_image_view);
        TextView fruitContentText= findViewById(R.id.fruit_content_text);
        Toolbar toolbar =findViewById(R.id.toolbar);//接着就是Toobar的标准用法，将他作为ActionBar显示
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);//并启用HomeAsUp按钮。用于HomeAsUp按钮的默认图案就是一个箭头不需要额外设置别的图标了
        }
        collapsingToolbar.setTitle(fruitName);//接下来是填充页面内容，调用collapsingToolbar的setTitle()方法降水果名设置成当前界面的标题
        Glide.with(this).load(fruitImageId).into(fruitImageView);//然后使用Glide加载传入的水果图片，并不需要什么真实数据
        String fruitContent = generateFruitContnent(fruitName);//所以使用generateFruieContnent()方法将水果名循环拼接了500次，从而生成了一个比较长的字符串
        fruitContentText.setText(fruitContent);//并将它设置到TextView上面
    }
    private String generateFruitContnent(String fruitName){
        StringBuilder fruitContent =  new StringBuilder();
        for (int i=0;i<500;i++){
            fruitContent.append(fruitName);
        }
        return fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//onOptionsItemSelected()方法处理HomeAsUp按钮的点击事件
        switch (item.getItemId()){
            case  android.R.id.home://在点击了这个按钮，就调用finish()方法关闭当前的活动，从而返回上一个活动。
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
