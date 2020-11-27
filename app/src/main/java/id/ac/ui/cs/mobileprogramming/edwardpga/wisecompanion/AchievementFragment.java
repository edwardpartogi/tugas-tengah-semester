package id.ac.ui.cs.mobileprogramming.edwardpga.wisecompanion;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class AchievementFragment extends Fragment {

    private WiseWordsViewModel mWiseWordsViewModel;
    private RecyclerView rvWiseWords;
    private WiseWordsRecyclerAdapter wiseWordsRecyclerAdapter;

    public AchievementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWiseWordsViewModel = new WiseWordsViewModel(getActivity().getApplication());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_achievement, container, false);

        rvWiseWords = root.findViewById(R.id.wiseWordsRView);
        rvWiseWords.setLayoutManager(new LinearLayoutManager(getActivity()));

        wiseWordsRecyclerAdapter = new WiseWordsRecyclerAdapter();
        rvWiseWords.setAdapter(wiseWordsRecyclerAdapter);

        mWiseWordsViewModel.getAllWiseWords().observe(getViewLifecycleOwner(), new Observer<List<WiseWords>>() {
            @Override
            public void onChanged(@Nullable final List<WiseWords> words) {
                // Update the cached copy of the words in the adapter.
                Log.d("BAJASEEEEE", "onBindViewHolder: "+ Locale.getDefault().getDisplayLanguage());

                wiseWordsRecyclerAdapter.updateData(words, Locale.getDefault().getDisplayLanguage());

            }
        });
        return root;
    }
}