package org.ingue.mall.repository;

import org.ingue.mall.domain.Postings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingsRepository extends JpaRepository<Postings, Long> {
}
