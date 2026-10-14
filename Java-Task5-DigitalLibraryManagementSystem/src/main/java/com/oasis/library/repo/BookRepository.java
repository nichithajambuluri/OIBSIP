package com.oasis.library.repo;
import com.oasis.library.model.Book; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface BookRepository extends JpaRepository<Book,Long>{List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCase(String t,String a,String i);}
