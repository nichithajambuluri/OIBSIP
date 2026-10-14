package com.oasis.library.repo;
import com.oasis.library.model.Loan; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface LoanRepository extends JpaRepository<Loan,Long>{List<Loan> findByStatusOrderByDueDateAsc(String status);}
