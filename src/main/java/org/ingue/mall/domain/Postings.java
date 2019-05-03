package org.ingue.mall.domain;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor
public class Postings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postingId;

    private String postingTitle;

    private String postingContent;

    private int postingRecommend;

    private String boardName;

    @OneToMany(mappedBy = "posting")
    private Set<Comments> commentsSet = new HashSet<>();

    @ManyToOne
    private Users user;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt;

    private String developer;


}
