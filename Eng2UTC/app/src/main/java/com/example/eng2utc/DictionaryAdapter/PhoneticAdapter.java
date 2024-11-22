package com.example.eng2utc.DictionaryAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eng2utc.Dictionary.Phonetics;
import com.example.eng2utc.R;

import java.util.List;

public class PhoneticAdapter extends RecyclerView.Adapter<PhoneticAdapter.PhoneticViewHolder> {

    private Context context;
    private List<Phonetics> phoneticList;

    public PhoneticAdapter(Context context, List<Phonetics> phoneticList) {
        this.context = context;
        this.phoneticList = phoneticList;
    }
    public void setPhoneticList(List<Phonetics> phoneticList) {
        this.phoneticList = phoneticList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhoneticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_phonetic, parent, false); // Anh xa layout list_phonetic.xml
        return new PhoneticViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneticViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt_phonetic.setText(phoneticList.get(position).getText());
        holder.btn_phonetic_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(phoneticList.get(position).getAudio());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    // Xử lý lỗi tại đây
                    Toast.makeText(context, "Could not play the audio", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return phoneticList.size();
    }

    public class PhoneticViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_phonetic;
        private ImageView btn_phonetic_audio;

        public PhoneticViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_phonetic = itemView.findViewById(R.id.txt_phonetic);
            btn_phonetic_audio = itemView.findViewById(R.id.btn_phonetic_audio);
        }
    }
}
