package com.example.eng2utc.TestFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.example.eng2utc.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ResultWordTestFragment extends BottomSheetDialogFragment {
    private static final String ARG_IS_CORRECT = "isCorrect";
    private boolean isCorrect;
    private OnNextListener onNextListener;
    private ImageView btnNext;

    public static ResultWordTestFragment newInstance(boolean isCorrect) {
        ResultWordTestFragment fragment = new ResultWordTestFragment();
        fragment.isCorrect = isCorrect;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setCancelable(false); // Ngăn chặn việc nhấn bên ngoài để đóng
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_word_test, container, false);

        btnNext = view.findViewById(R.id.btn_next_word_test);
        TextView tvResult = view.findViewById(R.id.result_text_view);
        tvResult.setText(isCorrect ? "Correct!" : "Incorrect!");

        changeBackgroundColor(view, isCorrect);

        btnNext.setOnClickListener(v -> {
            if (onNextListener != null) {
                onNextListener.onNext();
            }
            dismiss(); // Đóng BottomSheet sau khi nhấn Next
        });

        return view;
    }

    private void changeBackgroundColor(View view, boolean isCorrect) {
        int color = isCorrect ? R.color.green : R.color.red;
        int bgColor = isCorrect ? R.color.light_green : R.color.light_red;
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), color));
        btnNext.setBackgroundColor(ContextCompat.getColor(requireContext(), bgColor));
    }

    public void setOnNextListener(OnNextListener listener) {
        this.onNextListener = listener;
    }

    public interface OnNextListener {
        void onNext();
    }
}
