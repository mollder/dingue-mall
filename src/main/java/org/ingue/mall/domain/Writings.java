package org.ingue.mall.domain;

import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Writings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long writingId;

    private String writingTitle;

    private String writingContent;

    private long recommendCount;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public Writings(String writingTitle, String writingContent) {
        this.writingTitle = writingTitle;
        this.writingContent = writingContent;
        this.recommendCount = 0;
    }
}
