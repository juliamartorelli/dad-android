package com.example.julia.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.julia.myapplication.Adapter.MenuListAdapter;
import com.example.julia.myapplication.Base.Preferences;
import com.example.julia.myapplication.Model.NavigationItem;
import com.example.julia.myapplication.Model.User;
import com.example.julia.myapplication.Util.ActionBarDrawerToggle;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawer;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.navigation_view)
    protected NavigationView navigationView;

    @BindView(R.id.text_view_name)
    protected TextView textViewName;

    @BindView(R.id.text_view_email)
    protected TextView textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_container);
        ButterKnife.bind(this);

        toolbar.setTitleTextColor(Color.WHITE);

        User user = Preferences.getInstance().getCurrentUser();
        textViewName.setText(user.getName());
        textViewEmail.setText(user.getEmail());

        buildSideMenu();
        callFragment(R.id.placeholder, new EventListFragment());
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void buildSideMenu() {

        setSupportActionBar(toolbar);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        final ArrayList<NavigationItem> navigationItems = new ArrayList<>();

        navigationItems.add(new NavigationItem("Perfil", R.drawable.ic_cast_dark));
        navigationItems.add(new NavigationItem("Histórico de compras", R.drawable.ic_cast_dark));
        navigationItems.add(new NavigationItem("Cadastrar evento", R.drawable.ic_cast_dark));
        navigationItems.add(new NavigationItem("Cadastrar localidade", R.drawable.ic_cast_dark));
        navigationItems.add(new NavigationItem("Sair", R.drawable.ic_cast_dark));

        final ListView menuList = (ListView) findViewById(R.id.navigation_menu_list);
        MenuListAdapter adapter = new MenuListAdapter(this, navigationItems);
        try {
            if (menuList != null) {
                menuList.setAdapter(adapter);
                menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(final AdapterView<?> parent, View view, final int position, final long id) {

                        drawer.closeDrawer(navigationView);
                        toggle.runWhenIdle(new Runnable() {
                            @Override
                            public void run() {

                                final Intent intent;

                                switch (position) {
                                    case 0:
                                        //callFragment(R.id.placeholder, new UserProfileFragment());
                                        break;
                                    case 1:
                                        break;
                                    case 2:
                                        intent = new Intent(MenuDrawerActivity.this, CreateEventActivity.class);
                                        startActivity(intent);
                                        break;
                                    case 3:
                                        break;
                                    case 4:
                                        intent = new Intent(MenuDrawerActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        Preferences.getInstance().clear();
                                        break;
                                }
                            }
                        });
                        drawer.closeDrawers();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void callFragment(final int idPlaceHolder, final Fragment fragment) {

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(idPlaceHolder, fragment);
        fragmentTransaction.commit();
    }
}
