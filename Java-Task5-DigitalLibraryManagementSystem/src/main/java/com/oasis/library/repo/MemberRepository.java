package com.oasis.library.repo;
import com.oasis.library.model.Member; import org.springframework.data.jpa.repository.JpaRepository;
public interface MemberRepository extends JpaRepository<Member,Long>{}
