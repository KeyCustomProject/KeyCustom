package com.ho.edcustom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shareditems")
@SuperBuilder(toBuilder = true)
public class SharedItem extends BaseItem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shareditem_id")
    private Long id;

    private String sharedBy;

    private int likes;


    public void increase()
    {
        this.likes++;
    }
    public void decrease()
    {
        this.likes--;
    }
}
