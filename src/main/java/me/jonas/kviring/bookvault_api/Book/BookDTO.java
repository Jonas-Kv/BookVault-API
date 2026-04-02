package me.jonas.kviring.bookvault_api.Book;

import lombok.Getter;
import lombok.Setter;

//Zustänig für Body um Author id zu speicher und für Book in Author Objekt umzuwandeln
@Setter
@Getter
public class BookDTO {
  

  private String title;
  private String isbn;
  private Long authorId;

  
}
