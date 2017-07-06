package com.example.fanhongyi.croopinion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment f1,f2,f3,f4,f5,f6,f7;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private TextView mUserText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        String userValue=intent.getStringExtra("u");

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        mUserText = (TextView)headerLayout.findViewById(R.id.userTextView);
        mUserText.setText(userValue);

        f1 = new NewFragment();
        f2 = new ReportFragment();
        f3 = new TopicFragment();
        f4 = new GroupFragment();
        f5 = new KeywordFragment();
        f6 = new UserFragment();
        f7 = new AboutFragment();

        Bundle bundle = new Bundle();
        bundle.putString("str", userValue);
        f6.setArguments(bundle);
        //如果transaction  commit（）过  那么我们要重新得到transaction
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_content, f5);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        // Handle navigation view item clicks here.
        //hideFragment(transaction);
        int id = item.getItemId();
        if (id == R.id.nav_new) {
            Toast.makeText(getApplicationContext(), "nav_new",
                    Toast.LENGTH_SHORT).show();
            transaction.replace(R.id.fl_content, f1);
        } else if (id == R.id.nav_report) {
            Toast.makeText(getApplicationContext(), "nav_report",
                    Toast.LENGTH_SHORT).show();
            transaction.replace(R.id.fl_content, f2);
        } else if (id == R.id.nav_keyword) {
            transaction.replace(R.id.fl_content, f5);
        } else if (id == R.id.nav_user) {
            transaction.replace(R.id.fl_content, f6);
        }
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    * 去除（隐藏）所有的Fragment
    * */
    private void hideFragment(FragmentTransaction transaction) {
        if (f1 != null) {
            transaction.hide(f1);
            //transaction.remove(f1);
        }
    }


}
