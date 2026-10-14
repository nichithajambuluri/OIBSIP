package com.oasis.reservation.ui;

import com.oasis.reservation.model.Reservation;
import com.oasis.reservation.service.ReservationService;
import com.oasis.reservation.util.Theme;
import javax.swing.*;
import java.awt.*;

public class CancellationPanel extends JPanel {
    private final JTextField pnr=new JTextField();
    private final JTextArea details=new JTextArea();
    private final ReservationService service=new ReservationService();
    private Reservation current;

    public CancellationPanel(){ setOpaque(false); setBorder(BorderFactory.createEmptyBorder(10,18,24,18)); build(); }

    private void build(){
        JPanel card=Theme.card(); card.setLayout(new BorderLayout(0,18));
        JPanel title=new JPanel(new GridLayout(2,1,0,4)); title.setOpaque(false);
        title.add(Theme.heading("Find or cancel a booking",28));
        title.add(Theme.body("Enter the 10-digit PNR to retrieve the complete reservation."));
        card.add(title,BorderLayout.NORTH);

        JPanel search=new JPanel(new BorderLayout(10,0)); search.setOpaque(false); Theme.styleField(pnr);
        JButton fetch=Theme.primaryButton("Fetch Booking"); fetch.addActionListener(e->fetch());
        search.add(pnr,BorderLayout.CENTER); search.add(fetch,BorderLayout.EAST);

        details.setEditable(false); details.setFont(Theme.font(14,Font.PLAIN)); details.setForeground(Theme.NAVY);
        details.setBackground(Theme.PEBBLE); details.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        details.setLineWrap(true); details.setWrapStyleWord(true);
        JScrollPane scroll=new JScrollPane(details); scroll.setBorder(BorderFactory.createLineBorder(Theme.HAIRLINE));

        JPanel center=new JPanel(new BorderLayout(0,14)); center.setOpaque(false); center.add(search,BorderLayout.NORTH); center.add(scroll,BorderLayout.CENTER);
        card.add(center,BorderLayout.CENTER);

        JPanel bottom=new JPanel(new BorderLayout()); bottom.setOpaque(false);
        bottom.add(Theme.body("Cancellation permanently removes the reservation."),BorderLayout.WEST);
        JButton cancel=Theme.secondaryButton("Cancel Ticket"); cancel.setForeground(Theme.ERROR); cancel.addActionListener(e->cancel());
        bottom.add(cancel,BorderLayout.EAST); card.add(bottom,BorderLayout.SOUTH);
        setLayout(new BorderLayout()); add(card,BorderLayout.CENTER);
    }

    private void fetch(){
        try{
            current=service.findByPnr(pnr.getText().trim());
            if(current==null){ details.setText(""); JOptionPane.showMessageDialog(this,"No reservation found for that PNR.","Booking not found",JOptionPane.WARNING_MESSAGE); return; }
            details.setText(format(current));
        }catch(Exception e){ current=null; details.setText(""); JOptionPane.showMessageDialog(this,e.getMessage(),"Invalid PNR",JOptionPane.WARNING_MESSAGE); }
    }

    private void cancel(){
        if(current==null){ JOptionPane.showMessageDialog(this,"Fetch a valid booking before cancelling.","No booking selected",JOptionPane.WARNING_MESSAGE); return; }
        int choice=JOptionPane.showConfirmDialog(this,"Are you sure you want to cancel PNR "+current.pnr()+"?","Confirm cancellation",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if(choice!=JOptionPane.YES_OPTION) return;
        try{
            service.cancel(current.pnr());
            JOptionPane.showMessageDialog(this,"Reservation "+current.pnr()+" has been cancelled.","Cancellation successful",JOptionPane.INFORMATION_MESSAGE);
            current=null; pnr.setText(""); details.setText("");
        }catch(Exception e){ JOptionPane.showMessageDialog(this,e.getMessage(),"Cancellation failed",JOptionPane.ERROR_MESSAGE); }
    }

    private String format(Reservation r){
        return "BOOKING DETAILS\n\nPNR: "+r.pnr()+"\nPassenger: "+r.passengerName()+
                "\n\nTrain: "+r.trainNumber()+" - "+r.trainName()+"\nClass: "+r.classType()+
                "\nJourney Date: "+r.journeyDate()+"\n\nFrom: "+r.source()+"\nTo: "+r.destination();
    }
}
