package org.ingue.mall.repository;

import org.ingue.mall.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    User findUserByUserId(String userId);
}
