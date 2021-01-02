package id.ac.ui.cs.mobileprogramming.edwardpga.wisecompanion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

public class CompanionFragment extends Fragment {
    private CompanionViewModel mCompanionViewModel;
    private CompanionModel mCompanion;
    Intent timerIntent;

    String language;
    private final int ONE_YEAR = 60; //seconds

    private ImageView companionView;
    private Button feedBtn;
    private Button askBtn;
    private Button resetBtn;
    private ProgressBar hungerBar;
    private ProgressBar affectionBar;
    private TextView companionNameView;
    private TextView ageView;
    private TextView realAgeView;


    public CompanionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCompanionViewModel = new CompanionViewModel(getActivity().getApplication());
//        mCompanionViewModel.newCompanion(new CompanionModel("Farras", 10000, 1000));

        mCompanion = mCompanionViewModel.getAllCompanionModel().get(0);
        long startingAge = mCompanion.getAge();

        timerIntent = new Intent(getActivity(), TimerService.class);
        timerIntent.putExtra("startingCounter", startingAge);
        getActivity().startService(timerIntent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_companion, container, false);
        language = Locale.getDefault().getDisplayLanguage();

        companionView = root.findViewById(R.id.companionView);
        Glide.with(this).load(R.drawable.animated_cat).into(companionView);

        companionNameView = root.findViewById(R.id.companion_name);
        ageView = root.findViewById(R.id.ageValue);
        realAgeView = root.findViewById(R.id.realAgeView);
        hungerBar = root.findViewById(R.id.hungerBar);

        feedBtn = root.findViewById(R.id.feedBtn);
        feedBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int currHungerLvl = (int) mCompanion.getHungerLevel();
                mCompanionViewModel.setHungerLevel(mCompanion, Math.min(hungerBar.getMax(), currHungerLvl+800));
                timerIntent.putExtra("startingHungerLvl", mCompanion.getHungerLevel());
                hungerBar.setProgress((int) mCompanion.getHungerLevel());
            }
        });

        askBtn = root.findViewById(R.id.askBtn);
        affectionBar = root.findViewById(R.id.affectionBar);

        resetBtn = root.findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCompanionViewModel.setHungerLevel(mCompanion, hungerBar.getMax());
                mCompanionViewModel.setAffectionLevel(mCompanion, 0);
                mCompanionViewModel.setAge(mCompanion, 0);
                getActivity().stopService(timerIntent);

                timerIntent = new Intent(getActivity(), TimerService.class);
                timerIntent.putExtra("startingCounter", (long) 0);
                getActivity().startService(timerIntent);
            }
        });

        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCompanionViewModel.getAllCompanion().observe(getViewLifecycleOwner(), new Observer<List<CompanionModel>>() {
            @Override
            public void onChanged(@Nullable final List<CompanionModel> companions) {
                mCompanion = companions.get(0);

                long age = mCompanion.getAge();
                ageView.setText(getResources().getString(R.string.companion_age, (age/ONE_YEAR)));

                hungerBar.setProgress((int) companions.get(0).getHungerLevel());
                affectionBar.setProgress((int) companions.get(0).getAffectionLevel());

            }
        });

        String companion_name = mCompanion.getName();
        companionNameView.setText(companion_name);

        getActivity().registerReceiver(br, new IntentFilter(TimerService.TIMER_BROADCAST));
    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long counter = intent.getLongExtra("counter", -1);
            mCompanionViewModel.setAge(mCompanion, counter);

            realAgeView.setText(getResources().getString(R.string.real_time_age, counter));

            long stackedHunger = intent.getLongExtra("stackedHunger", 100000);
            long newHungerLvl = addLongValue(mCompanion.getHungerLevel(), -stackedHunger);
            mCompanionViewModel.setHungerLevel(mCompanion, Math.max(0, newHungerLvl));

            long stackedAffection = intent.getLongExtra("stackedAffection", 100000);
            long newAffectionLvl = addLongValue(mCompanion.getAffectionLevel(), stackedAffection);
            mCompanionViewModel.setAffectionLevel(mCompanion, Math.min(Math.max(0, newAffectionLvl), 10000));

        }
    };

    static {
        System.loadLibrary("native-lib");
    }
    public native long addLongValue(long val1, long val2);
}