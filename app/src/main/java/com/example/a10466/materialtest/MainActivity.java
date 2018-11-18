package com.example.a10466.materialtest;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Fruit[] fruits={new Fruit("Apple",R.drawable.apple),new Fruit("Banana",R.drawable.banana),
            new Fruit("Orange",R.drawable.orange),new Fruit("Watermelon",R.drawable.watermelon),
            new Fruit("Pear",R.drawable.pear),new Fruit("Grape",R.drawable.grape),
            new Fruit("PineApple",R.drawable.pineapple),new Fruit("Strawberry",R.drawable.strawberry),
            new Fruit("Cherry",R.drawable.cherry),new Fruit("Mango",R.drawable.mango)};//定义了一个数组，有很多个Fruit的实例，每个实例都代表一个水果
    private List<Fruit> fruitList =new ArrayList<>();
    private FruitAdapter adapter ;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFruits();
        RecyclerView recyclerView= findViewById(R.id.recycle_view);//获取RecycleView的实例
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);//使用GridLayoutManager布局方式传入参数context，列数
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout=findViewById(R.id.swipe_refresh);//得到swipeRefreshLayout的实例
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);//setColorSchemeResources设置下拉刷新进度条的颜色
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {//onRefresh()方法应该是去网络上请求最新的数据，这里我们简单起见调用了refresheFruits()方法
                refresheFruits();
            }
        });

        FloatingActionButton fab=findViewById(R.id.fab);//悬浮按钮
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Data deleted",Snackbar.LENGTH_SHORT)//调用Snackbar的make()方法创建一个SnackBar对象，第一个参数传入一个View，当前布局
                        // 任意一个view均可我们传入的FloatingActionButton本身，而FloatingActionButton是CoordinatorLayout中的子控件，因此这个控件能被监听
                        //第二个参数传入Snackbar中显示的内容，第三个参数是Snackbar显示的时长。这些都是和Toast类似
                        .setAction("Undo", new View.OnClickListener() { //接着这里调用setAction()方法设置一个动作，从而可以和用户进行交互。文字+Undo按钮
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,"Data restored",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
               /* Toast.makeText(MainActivity.this,"FAB clicked",Toast.LENGTH_SHORT).show();*/
            }
        });
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        ActionBar actionBar =getSupportActionBar();//得到ActionBar实例
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);//让导航按钮显示出来
         actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);//设置导航按钮的图标，没有这行代码就默认箭头
        }
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initFruits(){//先是清空frultList中的数据，接着使用一个随机函数，从刚才定义个Fruit数组中随机挑选一个水果放入到FruitList当中
        // ,这样每次打开的程序看到的水果程序都会是不同的。这里选50个水果
        fruitList.clear();
        for (int i =0 ; i<50 ; i++){
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    private void refresheFruits(){
        new Thread(new Runnable() {//refresheFruits()先是开启一个线程，然后将线程沉睡两秒钟=2000毫秒
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);//之所以这么做是因为本地刷新操作速度非常快，如果不将线程沉睡的话，刷新立刻就结束了。从而看不到刷新的过程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }runOnUiThread(new Runnable() {//沉睡过后使用runOnUiThread()方法降线程切换回主线程
                    @Override
                    public void run() {
                        initFruits();//然后调用initFruits()方法重新生成数据
                        adapter.notifyDataSetChanged();//接着调用FruitAdapter的notifyDataSetChanged()方法通知数据发生了改变
                        swipeRefreshLayout.setRefreshing(false);//最后调用swipeRefreshLayout的setRefreshing()方法并传入false表示刷新时间结束，并隐藏刷新进度条。
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);// 通过getMenuInflater()方法得到MenuInflater对象，再调用他的inflate（）方法就可以给当前活动创建菜单了
        //inflate()方法接受到两个参数，第一个指定我们通过哪个资源文件创建菜单-传入R.menu.main.第二个参数用于指定我们的菜单项将添加到哪一个menu对象中，这里直接使用
        //onCreateOptionsMenu()方法传入menu参数。然后给这个方法返回true表示船舰的菜单显示出来，反之false表示创建的菜单讲无法显示。
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://HomeAsUp的按钮的id永远斗士android.R.id.home
                mDrawerLayout.openDrawer(GravityCompat.START);//调用DrawableLayout的openDrawer()方法将滑动菜单展示,传入gravity参数与XML定义的一致
                break;
            case R.id.backup:
                Toast.makeText(this,"You clicked Backup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"You clicked Delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this,"You clicked Setting",Toast.LENGTH_SHORT).show();
                break;
                default:
        }
        return true;
    }


}
