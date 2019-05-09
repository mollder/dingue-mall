package org.ingue.mall.repository;

import org.ingue.mall.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findUsersByUserEmail(String userEmail);
}
