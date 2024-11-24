package com.example.eng2utc.DictionaryAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eng2utc.Dictionary.Definition;
import com.example.eng2utc.R;

import java.util.List;

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionAdapter.DefinitionViewHolder>
{
    private Context context;
    private List<Definition> definitionList;
    public DefinitionAdapter(Context context, List<Definition> definitionList) {
        this.context = context;
        this.definitionList = definitionList;
    }
    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_definitions, parent, false); // Anh xa layout list_definition.xml
        return new DefinitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
    holder.text_definition.setText("Definition: " + definitionList.get(position).getDefinition());
    holder.text_example.setText("Example: " + definitionList.get(position).getExample());

    StringBuilder synonyms = new StringBuilder();
    StringBuilder antonyms = new StringBuilder();

    synonyms.append(definitionList.get(position).getSynonyms());
    antonyms.append(definitionList.get(position).getAntonyms());

    holder.text_synonyms.setText(synonyms);
    holder.text_antonyms.setText(antonyms);

    holder.text_antonyms.setSelected(true);
    holder.text_synonyms.setSelected(true);

    }

    @Override
    public int getItemCount() {
        return definitionList.size();
    }

    public class DefinitionViewHolder extends RecyclerView.ViewHolder {
        private TextView text_definition,
                text_example,
                text_synonyms,
                text_antonyms;
        public DefinitionViewHolder(@NonNull View itemView) {
            super(itemView);
            text_definition = itemView.findViewById(R.id.txt_definition);
            text_example = itemView.findViewById(R.id.txt_example);
            text_synonyms = itemView.findViewById(R.id.txt_synonyms);
            text_antonyms = itemView.findViewById(R.id.txt_antonyms);

        }
    }
}
