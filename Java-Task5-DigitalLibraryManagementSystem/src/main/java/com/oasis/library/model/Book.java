package com.oasis.library.model;
import jakarta.persistence.*;
@Entity @Table(name="books") public class Book {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String title; @Column(nullable=false) private String author;
 @Column(unique=true,nullable=false) private String isbn; @Column(nullable=false) private String category;
 private int quantity; private int available;
 public Book(){} public Book(String t,String a,String i,String c,int q){title=t;author=a;isbn=i;category=c;quantity=q;available=q;}
 public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;}
 public String getAuthor(){return author;} public void setAuthor(String v){author=v;}
 public String getIsbn(){return isbn;} public void setIsbn(String v){isbn=v;}
 public String getCategory(){return category;} public void setCategory(String v){category=v;}
 public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;}
 public int getAvailable(){return available;} public void setAvailable(int v){available=v;}
}
