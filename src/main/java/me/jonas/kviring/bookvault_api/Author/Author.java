package me.jonas.kviring.bookvault_api.Author;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import me.jonas.kviring.bookvault_api.Book.Book;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Author {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(AccessLevel.NONE)
  private Long id;

  private String name;  

  @OneToMany(mappedBy = "author", cascade=CascadeType.REMOVE, orphanRemoval = true)
  @JsonIgnoreProperties("author")//To avoid infinite recursion
  private List<Book> books = new ArrayList<>(); //No NullpointerException, when trying to add a book to the list, because the list is already initialized
  public Author(String name) {
    this.name = name;
  }
}
