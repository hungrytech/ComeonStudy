package com.comeon.study.common.config.security.refreshtoken.repository;

import com.comeon.study.common.config.security.refreshtoken.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
