package com.comeon.study.member.domain.repository;

import com.comeon.study.member.domain.refreshtoken.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
