package com.oasis.library.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="reservations") public class Reservation {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; private Long bookId; private Long memberId;
 private String bookTitle; private String memberName; private LocalDateTime createdAt=LocalDateTime.now(); private String status="WAITING";
 public Reservation(){} public Reservation(Long b,Long m,String t,String n){bookId=b;memberId=m;bookTitle=t;memberName=n;}
 public Long getId(){return id;} public String getBookTitle(){return bookTitle;} public String getMemberName(){return memberName;}
 public LocalDateTime getCreatedAt(){return createdAt;} public String getStatus(){return status;}
}
