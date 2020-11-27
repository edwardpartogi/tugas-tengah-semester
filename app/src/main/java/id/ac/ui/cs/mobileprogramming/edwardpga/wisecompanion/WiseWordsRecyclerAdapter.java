package id.ac.ui.cs.mobileprogramming.edwardpga.wisecompanion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WiseWordsRecyclerAdapter extends RecyclerView.Adapter<WiseWordsRecyclerAdapter.WiseWordsViewHolder> {

    private List<WiseWords> wiseWordsList = new ArrayList<>();
    private static String language;

    @NonNull
    @Override
    public WiseWordsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wise_words_list, viewGroup, false);
        return new WiseWordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WiseWordsViewHolder wiseWordsViewHolder, int i) {
        WiseWords wiseWords = wiseWordsList.get(i);

        if (language.equals("Indonesia")) {
            wiseWordsViewHolder.wiseWordView.setText("\"" + wiseWords.getWiseWord_id() + "\"");
            wiseWordsViewHolder.catWordView.setText("\""+wiseWords.getWiseWord_cat()+"\" - bahasa kucing");
        }else {
            wiseWordsViewHolder.wiseWordView.setText("\"" + wiseWords.getWiseWord_en() + "\"");
            wiseWordsViewHolder.catWordView.setText("\""+wiseWords.getWiseWord_cat()+"\" - Cat language");
        }

    }

    @Override
    public int getItemCount() {
        return wiseWordsList.size();
    }

    public void updateData(List<WiseWords> wiseWordsList, String language) {
        this.wiseWordsList = wiseWordsList;
        this.language = language;
        notifyDataSetChanged();
    }

    static class WiseWordsViewHolder extends RecyclerView.ViewHolder {

        private TextView wiseWordView;
        private TextView catWordView;

        public WiseWordsViewHolder(@NonNull View itemView) {
            super(itemView);
            wiseWordView = itemView.findViewById(R.id.wiseWordView);
            catWordView = itemView.findViewById(R.id.catWordView);
        }
    }
}
