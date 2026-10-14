package com.oasis.reservation.service;

import com.oasis.reservation.dao.ReservationDAO;
import com.oasis.reservation.model.Reservation;
import com.oasis.reservation.model.Train;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReservationService {
    private final ReservationDAO dao = new ReservationDAO();
    private final PnrGenerator generator = new PnrGenerator();

    public Reservation book(String passenger,Train train,String classType,LocalDate date) throws Exception {
        if (passenger==null || passenger.trim().length()<2) throw new IllegalArgumentException("Enter a valid passenger name.");
        if (train==null) throw new IllegalArgumentException("Please enter a valid train number.");
        if (classType==null || classType.isBlank()) throw new IllegalArgumentException("Please select a class type.");
        if (date==null || !date.isAfter(LocalDate.now())) throw new IllegalArgumentException("Journey date must be a future date.");

        for (int i=0;i<10;i++) {
            Reservation r = new Reservation(0,generator.generate(),passenger.trim(),train.trainNumber(),
                    train.trainName(),classType,date.toString(),train.source(),train.destination());
            try {
                dao.insert(r);
                return r;
            } catch (SQLException e) {
                if (!e.getMessage().toLowerCase().contains("unique")) throw e;
            }
        }
        throw new IllegalStateException("Could not generate a unique PNR. Please try again.");
    }

    public Reservation findByPnr(String pnr) throws Exception {
        if (pnr==null || !pnr.matches("\\d{10}")) throw new IllegalArgumentException("Enter a valid 10-digit PNR.");
        return dao.findByPnr(pnr);
    }

    public void cancel(String pnr) throws Exception {
        if (!dao.deleteByPnr(pnr)) throw new IllegalStateException("Reservation was not found.");
    }

    public java.util.List<Reservation> findAll() throws Exception {
        return dao.findAll();
    }
}
