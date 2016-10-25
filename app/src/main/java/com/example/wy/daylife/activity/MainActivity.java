package com.example.wy.daylife.activity;

import android.graphics.RadialGradient;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wy.daylife.Interface.Constants;
import com.example.wy.daylife.R;
import com.example.wy.daylife.adapter.GroupListAdapter;
import com.example.wy.daylife.base.BaseActivity;
import com.example.wy.daylife.base.BaseFragment;
import com.example.wy.daylife.costumview.CircleImageView;
import com.example.wy.daylife.fragment.HomeFragment;
import com.example.wy.daylife.fragment.NotificationFragment;
import com.example.wy.daylife.fragment.PersonFragment;
import com.example.wy.daylife.fragment.SearchFragment;
import com.example.wy.daylife.tools.AccessTokenKeeper;
import com.example.wy.daylife.tools.GroupTool;
import com.example.wy.daylife.tools.UserTool;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.legacy.GroupAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.Group;
import com.sina.weibo.sdk.openapi.models.GroupList;
import com.sina.weibo.sdk.openapi.models.User;
import com.sina.weibo.sdk.utils.LogUtil;

import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    private static String TAG="MainActivity";

    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.main_draw)
    public DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    @Bind(R.id.main_face)
    public CircleImageView face;
    @Bind(R.id.main_name)
    public TextView name;
    @Bind(R.id.main_listview)
    public ListView group;
    @Bind(R.id.main_home)
    public RadioButton home;
    @Bind(R.id.main_notify)
    public RadioButton notification;
    @Bind(R.id.main_search)
    public RadioButton search;
    @Bind(R.id.main_person)
    public RadioButton person;
    @Bind(R.id.main_fragment_group)
    public RadioGroup main_group;

    private static User userInfo;
    private ImageLoaderConfiguration config;
    private ImageLoader loader;
    private List<Group> groups;
    private BaseFragment fragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        new UserTool(this).getUser(new UserTool.UserCallBack() {
            @Override
            public void getUser(User user) {
                userInfo=user;
                name.setText(user.screen_name);
                config= new ImageLoaderConfiguration.Builder(MainActivity.this).build();
                loader=ImageLoader.getInstance();
                loader.init(config);
                loader.displayImage(user.avatar_hd,face);
            }
        });

        new GroupTool(this).getGroup(new GroupTool.GroupCallBack() {
            @Override
            public void getGroup(GroupList groupList) {
                groups=groupList.groupList;
                group.setAdapter(new GroupListAdapter(MainActivity.this,groups));
            }
        });
    }


    @Override
    public void initEvent() {

        fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment).commit();

        main_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.main_home:
                        fragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment)
                                .commit();
                        break;
                    case R.id.main_notify:
                        fragment = new NotificationFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
                        break;
                    case R.id.main_search:
                        fragment = new SearchFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment)
                                .commit();
                        break;
                    case R.id.main_person:
                        fragment = new PersonFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment)
                                .commit();
                        break;
                    default:
                        break;
                }

            }
        });

    }

    @SuppressWarnings("deprecation")
    @Override
    public void initView() {

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.abc_action_bar_home_description,R.string.abc_action_bar_home_description_format);
        drawerToggle.syncState();

        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    public void initTheme() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}