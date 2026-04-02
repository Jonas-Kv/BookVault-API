package me.jonas.kviring.bookvault_api.Book;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  //Return all Books with their Author
  @GetMapping()
  public List<BookResponseDTO> getAllBooks() {
    return bookService.getAllBooks();
  }

  //Returned single Book, with its Author
  @GetMapping("/{id}")
  public BookResponseDTO getBook(@PathVariable Long id) {
    return bookService.getBook(id);
  }
  
  //Return all Book titles
  @GetMapping("/all")
  public List<String> getAllBookTitles() {
    return bookService.getAllBookTitles();
  }

  //Return ISBN of a Book, by searching for the title
  @GetMapping("/findISBN")
  public String getISBN(@RequestParam String title) {
    return bookService.getISBN(title);
  }

  //Create a Book, with the author id, which is needed to connect the Book to an Author
  @PostMapping
  public void createBook(@RequestBody BookDTO bookDTO) {
    bookService.createBook(bookDTO);
  }
  
  //Delete a Book by its id
  @DeleteMapping("/{id}")
  public void deleteBook(@PathVariable Long id){
    bookService.deleteBook(id);
  }

  //Update a Book, by its id, with the new Book data, which is sent as a BookDTO
  @PatchMapping("/{id}")
  public void patchBook(@PathVariable Long id, @RequestBody BookDTO bookDTO){
    bookService.patchBook(id, bookDTO);
  }
  
  
}
