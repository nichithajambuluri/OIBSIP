package com.oasis.library.model;
import jakarta.persistence.*; import java.time.LocalDate; import java.time.temporal.ChronoUnit;
@Entity @Table(name="loans") public class Loan {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private Long bookId; private Long memberId; private String bookTitle; private String memberName;
 private LocalDate issueDate; private LocalDate dueDate; private LocalDate returnDate; private String status="ISSUED"; private boolean finePaid;
 public Loan(){} public Loan(Long b,Long m,String bt,String mn,LocalDate i,LocalDate d){bookId=b;memberId=m;bookTitle=bt;memberName=mn;issueDate=i;dueDate=d;}
 public Long getId(){return id;} public Long getBookId(){return bookId;} public Long getMemberId(){return memberId;}
 public String getBookTitle(){return bookTitle;} public String getMemberName(){return memberName;}
 public LocalDate getIssueDate(){return issueDate;} public LocalDate getDueDate(){return dueDate;} public LocalDate getReturnDate(){return returnDate;}
 public void setReturnDate(LocalDate v){returnDate=v;} public String getStatus(){return status;} public void setStatus(String v){status=v;}
 public boolean isFinePaid(){return finePaid;} public void setFinePaid(boolean v){finePaid=v;}
 public long getFine(){LocalDate end=returnDate==null?LocalDate.now():returnDate;return Math.max(0,ChronoUnit.DAYS.between(dueDate,end))*5;}
}
