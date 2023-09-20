package com.labs.virtual.labs.models.entities;

import jakarta.persistence.*;

@Entity
@Table (name = "labs_usuarios")
public class LabUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "user_id", unique = true)
    private Long userID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (!(obj instanceof LabUser)){
            return false;
        }
        LabUser compare = (LabUser) obj;
        return this.userID != null && this.userID.equals(compare.userID);
    }
}
