package me.jonas.kviring.bookvault_api.Book;

import me.jonas.kviring.bookvault_api.Author.Author;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  
  @Setter(AccessLevel.NONE)
  private long id;

  private String title;
  private String isbn;
  
  @ManyToOne
  @JoinColumn(name="author_id", nullable = false)
  private Author author;

  public Book(String title, String isbn, Author author) {
    this.title = title;
    this.isbn = isbn;
    this.author = author;    
  }
}
