package org.ingue.mall.comment.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@JsonPropertyOrder({"commentId", "commentContent", "commentRecommend", "posting",
        "user", "createAt", "updateAt", "developer"})
public class Comments extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String commentContent;

    private int commentRecommend;

    @ManyToOne
    @JsonBackReference
    private Postings posting;

    @ManyToOne
    private Users user;

    public Comments(String commentContent) {
        this.commentContent = commentContent;
    }

    public void setPosting(Postings posting) {
        this.posting = posting;
        posting.getCommentsSet().add(this);
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
