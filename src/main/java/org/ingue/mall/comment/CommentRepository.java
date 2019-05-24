package org.ingue.mall.comment;

import org.ingue.mall.comment.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Long> {
}
