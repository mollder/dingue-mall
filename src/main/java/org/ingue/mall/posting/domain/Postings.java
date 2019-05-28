package org.ingue.mall.posting.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
//toString은 기본적으로 사용하는 것이 좋음
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
    @JsonManagedReference
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

    //update을 할 때 이 메소드를 이용해서 사용하는 것이 좋음
    // 접근제한자는 구체적으로 !
    // 보통 트랜잭션 같으 경우에는
    public void modify(Postings source) {

    }
}