package com.oasis.reservation.util;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;import javax.swing.border.*;import java.awt.*;

public final class Theme {
 public static final Color NAVY=Color.decode("#0b3558"),BLUE=Color.decode("#006bff"),SLATE=Color.decode("#476788"),CLOUD=Color.decode("#f6f8fb"),PAPER=Color.WHITE,PEBBLE=Color.decode("#f0f3f8"),HAIRLINE=Color.decode("#d4e0ed"),SUCCESS=Color.decode("#168a5b"),ERROR=Color.decode("#c63b3b"),GOLD=Color.decode("#b7791f");
 private Theme(){}
 public static Font font(int s,int style){return new Font("SansSerif",style,s);}
 public static JLabel heading(String t,int s){JLabel l=new JLabel(t);l.setForeground(NAVY);l.setFont(font(s,Font.BOLD));return l;}
 public static JLabel body(String t){JLabel l=new JLabel(t);l.setForeground(SLATE);l.setFont(font(13,Font.PLAIN));return l;}
 public static JButton primaryButton(String t){JButton b=new JButton(t);b.setFont(font(13,Font.BOLD));b.setForeground(PAPER);b.setBackground(BLUE);b.setFocusPainted(false);b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));b.putClientProperty("JButton.buttonType","roundRect");b.setBorder(new EmptyBorder(10,18,10,18));return b;}
 public static JButton secondaryButton(String t){JButton b=new JButton(t);b.setFont(font(13,Font.BOLD));b.setForeground(NAVY);b.setBackground(PAPER);b.setFocusPainted(false);b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));b.putClientProperty("JButton.buttonType","roundRect");return b;}
 public static void styleField(JTextField f){f.setFont(font(14,Font.PLAIN));f.setForeground(NAVY);f.setBackground(PEBBLE);f.putClientProperty("JTextField.placeholderText","");f.putClientProperty("JComponent.roundRect",true);f.setBorder(new CompoundBorder(new LineBorder(HAIRLINE,1),new EmptyBorder(8,12,8,12)));}
 public static void styleCombo(JComboBox<?> c){c.setFont(font(14,Font.PLAIN));c.setForeground(NAVY);c.setBackground(PEBBLE);c.putClientProperty("JComponent.roundRect",true);c.setBorder(new EmptyBorder(2,6,2,6));}
 public static JPanel card(){JPanel p=new JPanel();p.setBackground(PAPER);p.setBorder(new CompoundBorder(new LineBorder(HAIRLINE,1),new EmptyBorder(20,20,20,20)));return p;}
 public static JPanel softCard(){JPanel p=new JPanel();p.setBackground(PEBBLE);p.setBorder(new EmptyBorder(16,16,16,16));return p;}
 public static void installGlobal(){FlatLightLaf.setup();UIManager.put("Component.arc",12);UIManager.put("Button.arc",12);UIManager.put("TextComponent.arc",10);UIManager.put("ScrollBar.width",10);UIManager.put("Table.rowHeight",34);}
}
