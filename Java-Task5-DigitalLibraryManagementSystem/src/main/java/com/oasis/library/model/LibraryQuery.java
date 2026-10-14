package com.oasis.library.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="library_queries") public class LibraryQuery {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; private String name; private String email;
 @Column(length=2000) private String message; private LocalDateTime createdAt=LocalDateTime.now(); private boolean resolved;
 public LibraryQuery(){} public LibraryQuery(String n,String e,String m){name=n;email=e;message=m;}
 public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;}
 public String getMessage(){return message;} public LocalDateTime getCreatedAt(){return createdAt;}
 public boolean isResolved(){return resolved;} public void setResolved(boolean v){resolved=v;}
}
