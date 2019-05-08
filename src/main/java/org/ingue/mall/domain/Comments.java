package org.ingue.mall.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "commentId")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String commentContent;

    private int commentRecommend;

    @ManyToOne
    private Postings posting;

    @ManyToOne
    private Users user;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Column(nullable = false)
    private String developer;

    public Comments(String commentContent) {
        this.commentContent = commentContent;
    }

    public void setPosting(Postings posting) {
        this.posting = posting;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
