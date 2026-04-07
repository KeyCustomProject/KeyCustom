package com.ho.edcustom.entity;


import com.fasterxml.jackson.databind.JsonNode;
import com.ho.edcustom.DTO.KeyCaps;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BaseItem {
    String email;

    String title;

    String barebonecolor; //베어본

    String keyboardtype;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    KeyCaps keycapcolor;

    String switchcolor;  //스위치

    @Column(name = "imageurl")
    String imageUrl;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
}
