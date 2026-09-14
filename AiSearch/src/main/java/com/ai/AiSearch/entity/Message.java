package com.ai.AiSearch.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id",nullable = false)
    Long   id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    Conversation conversationId;

   String content;

   LocalDateTime createdAt;
    @PrePersist
    public void onCreation(){
        this.createdAt=LocalDateTime.now();
    }
}
