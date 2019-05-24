package org.ingue.mall.posting.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.Sets;
import lombok.*;
import org.ingue.mall.base.entity.BaseEntity;
import org.ingue.mall.comment.domain.Comments;
import org.ingue.mall.posting.Board;
import org.ingue.mall.user.Users;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "postingId", callSuper = false)
@JsonPropertyOrder({"postingId", "postingTitle", "postingContent", "postingRecommend",
        "boardName", "commentsSet", "user", "createAt", "updateAt", "developer"})
public class Postings extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postingId;

    @Column(nullable = false)
    private String postingTitle;

    private String postingContent;

    private int postingRecommend;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Board boardName = Board.USER;

    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Comments> commentsSet = Sets.newHashSet();

    @ManyToOne
    private Users user;

    public void setUsers(Users user) {
        this.user = user;
    }

    public void addComments(Comments comment) {
        this.getCommentsSet().add(comment);
        comment.setPosting(this);
    }

    public void recommendPosting() {
        this.postingRecommend = this.postingRecommend+1;
    }
}