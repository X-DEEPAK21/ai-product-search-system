package com.ai.AiSearch.entity;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class RealUser {

   private  Long id;
   private  String name;
   private  String email;
   private Gender gender;
   private Role role;
   private String password;
   private LocalDateTime created_At;
   private LocalDateTime updated_At;

  /*  @OneToMany(mappedBy = "user")
    private List<Conversation> conversations;*/

   /* @PrePersist
    public void onCreation(){
        this.created_At=LocalDateTime.now();
    }
    @PreUpdate
    public void onUpdate(){
        this.updated_At=LocalDateTime.now();
    }*/


}
