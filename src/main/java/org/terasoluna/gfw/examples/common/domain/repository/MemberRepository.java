package org.terasoluna.gfw.examples.common.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.terasoluna.gfw.examples.common.domain.model.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}
