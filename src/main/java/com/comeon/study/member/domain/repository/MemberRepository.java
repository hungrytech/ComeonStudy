package com.comeon.study.member.domain.repository;

import com.comeon.study.member.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
}
