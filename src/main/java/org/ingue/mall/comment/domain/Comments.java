package org.ingue.mall.comment.domain;

import lombok.*;
import org.ingue.mall.base.entity.BaseEntity;
import org.ingue.mall.user.Users;
import org.ingue.mall.posting.domain.Postings;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(of = "commentId", callSuper = false)
public class Comments extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String commentContent;

    private int commentRecommend;

    @ManyToOne
    private Postings posting;

    @ManyToOne
    private Users user;

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
