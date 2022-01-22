package com.comeon.study.member.domain.repository;

import com.comeon.study.member.domain.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {

    Optional<Member> findMemberByEmail(String email);
}
