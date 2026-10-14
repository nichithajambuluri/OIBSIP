package com.oasis.library.config;
import com.oasis.library.model.*; import com.oasis.library.repo.*; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*;
@Configuration public class SeedData {
 @Bean CommandLineRunner seed(BookRepository b,MemberRepository m){return args->{
 if(b.count()==0){b.save(new Book("Atomic Habits","James Clear","9780735211292","Personal Growth",5));b.save(new Book("The Psychology of Money","Morgan Housel","9789390166268","Finance",4));b.save(new Book("Clean Code","Robert C. Martin","9780132350884","Technology",3));b.save(new Book("Ikigai","Héctor García & Francesc Miralles","9780143130727","Wellness",6));b.save(new Book("The Alchemist","Paulo Coelho","9780062315007","Fiction",5));b.save(new Book("Deep Work","Cal Newport","9781455586691","Productivity",2));b.save(new Book("Wings of Fire","A. P. J. Abdul Kalam","9788173711466","Biography",4));b.save(new Book("The Hobbit","J. R. R. Tolkien","9780547928227","Fantasy",3));b.save(new Book("Sapiens","Yuval Noah Harari","9780062316097","History",3));}
 if(m.count()==0){m.save(new Member("Demo Reader","reader@lumina.local","9876543210","MEMBER"));m.save(new Member("Library Admin","admin@lumina.local","9000000000","ADMIN"));}
 };}
}
