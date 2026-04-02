package me.jonas.kviring.bookvault_api.Book;
//Zuständig für Rückgabe der Bücher, tauscht autor objekt durch Autor namen aus
//Sonst würde beim laden des Autorenobjektes die Liste für einen Loop sorgen

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class BookResponseDTO {
  
  private Long id;
  private String title;
  private String isbn;
  private String autorName;
}
