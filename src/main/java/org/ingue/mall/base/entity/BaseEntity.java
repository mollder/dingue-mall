package org.ingue.mall.base.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseEntity implements Serializable {

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    protected LocalDateTime createAt;

    @UpdateTimestamp
    @Column(nullable = false)
    protected LocalDateTime updateAt;

    protected String developer = "ingue";
}
