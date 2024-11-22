package com.example.eng2utc.Firebase;

import com.example.eng2utc.Model.UserAnswer;
import com.example.eng2utc.Model.UserTest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseController {

    private DatabaseReference databaseReference;

    public FirebaseController() {
        // Khởi tạo Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    // Phương thức để lấy dữ liệu từ bảng cụ thể
    public void getDataFromTable(String tableName, FirebaseDataCallback callback) {
        databaseReference.child(tableName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    callback.onSuccess(dataSnapshot);
                } else {
                    callback.onFailure("Không tìm thấy dữ liệu");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    // Phương thức lấy dữ liệu từ bảng từ vựng
    public void getVocabularyData(FirebaseDataCallback callback) {
        getDataFromTable("VOCABULARY", callback);
    }

    // Phương thức lấy dữ liệu từ bảng level
    public void getLevelData(FirebaseDataCallback callback) {
        getDataFromTable("CERT_LEVEL", callback);
    }

    // Phương thức lấy từ vựng A2
    public void getA2VocabularyData(final FirebaseDataCallback callback) {
        databaseReference.child("VOCABULARY").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    callback.onSuccess(dataSnapshot); // Gọi onSuccess với DataSnapshot
                } else {
                    callback.onFailure("Không tìm thấy dữ liệu");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    // Phương thức lấy dữ liệu
    public void getData(String nameData, FirebaseDataCallback callback) {
        databaseReference.child(nameData).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    callback.onSuccess(dataSnapshot); // Gọi onSuccess với DataSnapshot
                } else {
                    callback.onFailure("Không tìm thấy dữ liệu");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    // Method to add UserTest data to Firebase
    public void addUserTest(UserTest userTest, FirebaseDataCallback callback) {
        String userTestId = databaseReference.child("USER_TEST").push().getKey();
        if (userTestId != null) {
            userTest.setUSER_TEST_ID(userTestId);
            databaseReference.child("USER_TEST").child(userTestId).setValue(userTest)
                    .addOnSuccessListener(aVoid -> callback.onSuccess(null))
                    .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
        } else {
            callback.onFailure("Failed to generate unique key for USER_TEST");
        }
    }

    public void addUserAnswer(UserAnswer userAnswer, FirebaseDataCallback callback) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("USER_ANSWER");
        String userAnswerId = userAnswer.getUSER_ANSWER_ID();
        databaseReference.child(userAnswerId).setValue(userAnswer)
                .addOnSuccessListener(aVoid -> callback.onSuccess(null))
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }
}
