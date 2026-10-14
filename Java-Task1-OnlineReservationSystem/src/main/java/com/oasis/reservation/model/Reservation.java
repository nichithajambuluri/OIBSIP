package com.oasis.reservation.model;
public record Reservation(int id, String pnr, String passengerName, int trainNumber,
                          String trainName, String classType, String journeyDate,
                          String source, String destination) {}
