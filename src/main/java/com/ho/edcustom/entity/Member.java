package com.ho.edcustom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    String name;

    @Column(name = "imageurl")
    String imageUrl;

    @Column(unique = true)
    String nickname;

    @Column(unique = true)
    String email;

    String password;



//    public Member updatePassword(String newPassword)
//    {
//        this.password =newPassword;
//        return this;
//    }
}
