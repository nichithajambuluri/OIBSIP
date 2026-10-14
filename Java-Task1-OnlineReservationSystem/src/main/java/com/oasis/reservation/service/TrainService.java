package com.oasis.reservation.service;
import com.oasis.reservation.dao.TrainDAO;
import com.oasis.reservation.model.Train;
import java.util.List;

public class TrainService {
    private final TrainDAO dao = new TrainDAO();
    public Train findTrain(int number) throws Exception { return dao.findByNumber(number); }
    public List<Train> findAll() throws Exception { return dao.findAll(); }
}
