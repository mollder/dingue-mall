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
@Getter
//좀더 이름을 구체적으로 (클래스 이름에서 createAt이나 develop을 유추해낼 수가 없음 )
public class BaseEntity implements Serializable {

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    protected LocalDateTime createAt;

    @UpdateTimestamp
    @Column(nullable = false)
    protected LocalDateTime updateAt;

    protected String developer = "ingue";
}
