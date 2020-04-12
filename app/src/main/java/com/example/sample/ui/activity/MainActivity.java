package com.example.sample.ui.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.sample.R;
import com.example.sample.ui.fragments.DashBoardFragment;
import com.example.sample.ui.fragments.HomeFragment;
import com.example.sample.ui.fragments.SettingFragment;
import com.example.sample.utils.BaseBackPressedListener;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation)BottomNavigationView navigation;

    protected BaseBackPressedListener.OnBackPressedListener onBackPressedListener;

    int[][] states = new int[][]{
            new int[]{-android.R.attr.state_checked},  // unchecked
            new int[]{android.R.attr.state_checked},   // checked
            new int[]{}                                // default
    };

    int[] colors = new int[]{
            Color.parseColor("#002D52"),
            Color.parseColor("#002D52"),
            Color.parseColor("#002D52"),
    };

    int[] colors2 = new int[]{
            Color.parseColor("#ffffff"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#ffffff"),
    };


    private ColorStateList navigationViewColorStateList = new ColorStateList(states, colors);
    private ColorStateList navigationViewColorStateListDefault = new ColorStateList(states, colors2);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            ButterKnife.bind(this);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            navigation.setItemIconTintList(null);
            addBottomNavigationItems();
            removePaddingFromNavigationItem();

           // navigation.setVisibility(View.GONE);
            if (findViewById(R.id.fragment_container) != null) {
                loadFragment(new HomeFragment());
            }
        }catch (Exception e){
            System.out.println("======================");
        }
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
    }

    private void addBottomNavigationItems(){
        Menu menu = navigation.getMenu();

        menu.add(Menu.NONE, R.id.navigation_home , Menu.NONE, getString(R.string.title_home)).setIcon(R.drawable.ic_home_black_24dp);

        menu.add(Menu.NONE, R.id.navigation_accessories , Menu.NONE, getString(R.string.title_dashboard)).setIcon(R.drawable.ic_dashboard_black_24dp);

        menu.add(Menu.NONE, R.id.navigation_settings , Menu.NONE, getString(R.string.title_notifications)).setIcon(R.drawable.ic_notifications_black_24dp);

    }

    public void setFragment(Fragment fragment) {
        if (fragment == null) return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commitAllowingStateLoss();
    }

    public void addFragment(Fragment fragment, String TAG) {
        if (fragment == null) return;
        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
        fragTransaction.setCustomAnimations(R.anim.slide_in_left, 0, 0, R.anim.slide_out_left);
        fragTransaction.add(R.id.fragment_container, fragment, TAG);
        fragTransaction.addToBackStack(TAG);
        fragTransaction.commitAllowingStateLoss();
    }


    public void removePaddingFromNavigationItem() {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);

        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            View activeLabel = item.findViewById(R.id.largeLabel);
            if (activeLabel instanceof TextView) {
                activeLabel.setPadding(0, 0, 0, 0);
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Menu menu = navigation.getMenu();



                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        navigation.setBackground(getResources().getDrawable(R.drawable.bottom_tab_transparent_bg));
                        item.setTitle("");
                        menu.getItem(1).setTitle(getString(R.string.title_dashboard));
                        menu.getItem(2).setTitle(getString(R.string.title_home));

                        item.setIcon(R.drawable.ic_home_black_24dp);
                        menu.getItem(1).setIcon(R.drawable.ic_dashboard_black_24dp);
                        menu.getItem(2).setIcon(R.drawable.ic_notifications_black_24dp);

                        navigation.setItemTextColor(navigationViewColorStateListDefault);
                        clearBackStack();
                        loadFragment(new HomeFragment());
                        return true;
                    case R.id.navigation_accessories:

                        navigation.setBackground(getResources().getDrawable(R.drawable.bottom_tab_white_bg));
                        menu.getItem(0).setTitle(getString(R.string.title_home));
                        item.setTitle("");
                        menu.getItem(2).setTitle(getString(R.string.title_notifications));

                        item.setIcon(R.drawable.ic_notifications_black_24dp);
                        menu.getItem(0).setIcon(R.drawable.ic_dashboard_black_24dp);
                        menu.getItem(2).setIcon(R.drawable.ic_home_black_24dp);

                        navigation.setItemTextColor(navigationViewColorStateList);

                        loadFragment(new DashBoardFragment());
                        return true;
                    case R.id.navigation_settings:
                        navigation.setBackground(getResources().getDrawable(R.drawable.bottom_tab_white_bg));
                        menu.getItem(0).setTitle(getString(R.string.title_home));
                        menu.getItem(1).setTitle(getString(R.string.title_notifications));
                        item.setTitle("");

                        item.setIcon(R.drawable.ic_notifications_black_24dp);
                        menu.getItem(0).setIcon(R.drawable.ic_home_black_24dp);
                        menu.getItem(1).setIcon(R.drawable.ic_dashboard_black_24dp);

                        navigation.setItemTextColor(navigationViewColorStateList);

                        loadFragment(new SettingFragment());
                        return true;

            }
            return false;
        }
    };

    public void clearBackStack(){
        FragmentManager fm = this.getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeTab(int id){
        navigation.setSelectedItemId(id);
    }

    public void setOnBackPressedListener(BaseBackPressedListener.OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }



}
