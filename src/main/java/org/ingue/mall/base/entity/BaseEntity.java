package org.ingue.mall.base.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class BaseEntity implements Serializable {

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    protected LocalDateTime createAt;

    @UpdateTimestamp
    @Column(nullable = false)
    protected LocalDateTime updateAt;

    protected String developer = "ingue";
}
