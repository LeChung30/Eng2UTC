package com.example.eng2utc.DictionaryAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eng2utc.Dictionary.Meaning;
import com.example.eng2utc.R;

import java.util.List;

public class MeaningAdapter extends RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder> {
    private Context context;
    private List<Meaning> meaningList;
    public MeaningAdapter(Context context, List<Meaning> meaningList) {
        this.context = context;
        this.meaningList = meaningList;
    }

    @NonNull
    @Override
    public MeaningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_meaning, parent, false);
        return new MeaningViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeaningViewHolder holder, int position) {
    holder.txt_partOfSpeech.setText("Part of speech: " + meaningList.get(position).getPartOfSpeech());
    holder.meaning_RecyclerView.setHasFixedSize(true);
    holder.meaning_RecyclerView.setLayoutManager(new GridLayoutManager(context , 1));
    DefinitionAdapter definitionAdapter = new DefinitionAdapter(context, meaningList.get(position).getDefinitions());
    holder.meaning_RecyclerView.setAdapter(definitionAdapter);

    }

    @Override
    public int getItemCount() {
        return meaningList.size();
    }

    public class MeaningViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_partOfSpeech;
        private RecyclerView meaning_RecyclerView;
        public MeaningViewHolder(@NonNull View itemView) {
            super(itemView);
            // Khởi tạo các thành phần trong layout list_meaning.xml ở đây
            txt_partOfSpeech = itemView.findViewById(R.id.txt_partOfSpeech);
            meaning_RecyclerView = itemView.findViewById(R.id.meaning_RecyclerView);

        }
    }
}
