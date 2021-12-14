package com.example.natour2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.natour2.controller.ControllerHomeAcrtivity;
import com.example.natour2.controller.ControllerLoginSignin;

public class HomeActivity2 extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;

    private final ControllerHomeAcrtivity ctrl = new ControllerHomeAcrtivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        this.getSupportActionBar().hide();

        //Assign variable
        bottomNavigation = findViewById(R.id.bottom_navigation);

        //Add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_search));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_message));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_person));


        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                showItem(item.getId());
            }
        });
        //bottomNavigation.setCount(1, "10");

        bottomNavigation.show(1, true);


        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(getApplicationContext(), "you clicked " + item.getId(), Toast.LENGTH_SHORT).show();

            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(getApplicationContext(), "you reselect " + item.getId(), Toast.LENGTH_SHORT).show();
                showItem(item.getId());
            }
        });
    }

    private void showItem(int itemID){
        switch (itemID){
            case 1: {
                ctrl.showHomeFragment(getSupportFragmentManager());
                break;
            }
            case 2: {
                ctrl.showSearchFragment(getSupportFragmentManager());
                break;
            }
            case 3: {

                break;
            }
            case 4: {
                ctrl.showProfileFragment(getSupportFragmentManager());
                break;
            }
        }
    }

}