package id.ac.ui.cs.mobileprogramming.edwardpga.wisecompanion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    final static CompanionFragment companionFragment = new CompanionFragment();
    final static AchievementFragment achievementFragment = new AchievementFragment();
    final static ConfigurationFragment configurationFragment = new ConfigurationFragment();
    static String currFragmentTag;
    private static boolean is_paused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (currFragmentTag==null){
        exchangeFragment("achievement", "companion", companionFragment);
//        }

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                exchangeFragment(currFragmentTag, "companion", companionFragment);
                return false;
            }
        });
        navView.getMenu().getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                exchangeFragment(currFragmentTag, "achievement", achievementFragment);
                return false;
            }
        });
        navView.getMenu().getItem(2).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                exchangeFragment(currFragmentTag, "configuration", configurationFragment);
                return false;
            }
        });

    }

    private void exchangeFragment(String befTag, String afTag, Fragment fragment){
        if (fragmentManager.findFragmentByTag(afTag) != null){
            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag(afTag)).commit();
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_host, fragment, afTag).commit();
        }if (befTag != afTag && fragmentManager.findFragmentByTag(befTag) != null){
            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(befTag)).commit();
        }
        currFragmentTag = afTag;
    }

    @Override
    protected void onPause() {
        super.onPause();
        is_paused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        is_paused = false;
    }

    public static boolean isPaused(){
        return is_paused;
    }
}