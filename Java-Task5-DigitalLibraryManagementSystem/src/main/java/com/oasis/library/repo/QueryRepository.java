package com.oasis.library.repo;
import com.oasis.library.model.LibraryQuery; import org.springframework.data.jpa.repository.JpaRepository;
public interface QueryRepository extends JpaRepository<LibraryQuery,Long>{}
