package com.comeon.study.auth.refreshtoken.repository;

import com.comeon.study.auth.refreshtoken.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
