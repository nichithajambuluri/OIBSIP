package com.oasis.reservation;
import com.oasis.reservation.database.DatabaseInitializer;import com.oasis.reservation.ui.LoginFrame;import com.oasis.reservation.util.Theme;import javax.swing.*;
public class Main{public static void main(String[]a){SwingUtilities.invokeLater(()->{Theme.installGlobal();try{DatabaseInitializer.initialize();}catch(Exception e){JOptionPane.showMessageDialog(null,e.getMessage(),"Startup error",JOptionPane.ERROR_MESSAGE);return;}new LoginFrame().setVisible(true);});}}
