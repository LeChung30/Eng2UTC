package com.example.eng2utc.Firebase;

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
}
