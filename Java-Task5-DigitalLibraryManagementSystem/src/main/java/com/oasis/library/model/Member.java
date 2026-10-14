package com.oasis.library.model;
import jakarta.persistence.*;
@Entity @Table(name="members") public class Member {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name; @Column(unique=true,nullable=false) private String email;
 private String phone; private String role="MEMBER";
 public Member(){} public Member(String n,String e,String p,String r){name=n;email=e;phone=p;role=r;}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
 public String getRole(){return role;} public void setRole(String v){role=v;}
}
