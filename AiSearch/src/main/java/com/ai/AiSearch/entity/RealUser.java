package com.ai.AiSearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Table(
        name = "users", indexes = {
                @Index(name = "idx_user_email", columnList = "email")
        }
)
@Entity
public class RealUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",nullable = false)
    private  Long id;

    @Column(nullable = false)
   private  String name;

    @Column(nullable = false, unique = true)
   private  String email;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private Gender gender;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private Role role;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "is_verified")
    private boolean isVerified=true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Auth provider = Auth.LOCAL;

   @JsonIgnore
   private String password;

   @Column(name = "created_at")
   private LocalDateTime createdAt;
    @Column(name = "verified_at")
   private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "userId")
    private List<Conversation> conversations;

    @PrePersist
    public void onCreation(){
        this.createdAt=LocalDateTime.now();
    }
    @PreUpdate
    public void onUpdate(){
        this.updatedAt=LocalDateTime.now();
    }


}
