package org.ingue.mall.posting;

import org.ingue.mall.posting.domain.Postings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepository extends JpaRepository<Postings, Long> {
}
