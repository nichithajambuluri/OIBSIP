package com.oasis.library.repo;
import com.oasis.library.model.Reservation; import org.springframework.data.jpa.repository.JpaRepository;
public interface ReservationRepository extends JpaRepository<Reservation,Long>{}
