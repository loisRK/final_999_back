package com.spring.diary.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.spring.diary.entity.ChatEntity;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ChatRepository {

	public static final String COLLECTION_NAME="chatting";
	
	public List<ChatEntity> getChattings() throws ExecutionException, InterruptedException {
        List<ChatEntity> list = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            list.add(document.toObject(ChatEntity.class));
        }
        return list;
    }
	
}
