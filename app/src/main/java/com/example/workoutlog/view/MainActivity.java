package com.example.workoutlog.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    FragmentManager fragMan = getSupportFragmentManager();

    private AddLiftsFragment addLiftsFrag;
    private TodayFragment todayFrag;
    private ProgressFragment progressFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addLiftsFrag = new AddLiftsFragment();
        todayFrag = new TodayFragment();
        progressFrag = new ProgressFragment();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(addLiftsFrag);

        binding.bottomNavigation.setOnItemSelectedListener(item ->{

            int itemId = item.getItemId();
            if (itemId == R.id.nav_add_lifts) {
                replaceFragment(addLiftsFrag);
            } else if (itemId == R.id.nav_today) {
                replaceFragment(todayFrag);
            } else if (itemId == R.id.nav_progress) {
                replaceFragment(progressFrag);
            }

            return true;
        });
    }

    private void replaceFragment(Fragment frag){

        FragmentTransaction fragTrans = fragMan.beginTransaction();
        fragTrans.setCustomAnimations(androidx.navigation.ui.R.animator.nav_default_enter_anim, androidx.navigation.ui.R.animator.nav_default_exit_anim);
        fragTrans.replace(R.id.mainFrameLayout, frag);
        fragTrans.commit();
    }

}