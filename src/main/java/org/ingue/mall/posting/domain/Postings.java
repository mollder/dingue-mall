package org.ingue.mall.posting.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.ingue.mall.comment.Comments;
import org.ingue.mall.posting.Board;
import org.ingue.mall.user.Users;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "postingId")
public class Postings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postingId;

    @Column(nullable = false)
    private String postingTitle;

    private String postingContent;

    private int postingRecommend;

    @Enumerated(EnumType.STRING)
    private Board boardName = Board.USER;

    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL)
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

    @Builder
    public Postings(String postingTitle, String postingContent) {
        this.postingTitle = postingTitle;
        this.postingContent = postingContent;
        developer = "ingue";
    }

    public void setUsers(Users user) {
        this.user = user;
    }

    public void addComments(Comments comment) {
        this.getCommentsSet().add(comment);
        comment.setPosting(this);
    }
}