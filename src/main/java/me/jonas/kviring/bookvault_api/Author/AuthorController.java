package me.jonas.kviring.bookvault_api.Author;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

  private final AuthorService authorService;

  

  //Every Author with their Books
  @GetMapping("/books") 
  public List<Author> getAuthors() {
    return authorService.getAllAuthors();
  }

  //Every Author without their Books
  @GetMapping()
  public List<AuthorDTO> getAuthorsName() {
    return authorService.getAllAuthorsNames();
    
  }

  //Single Author with his Books
  @GetMapping("/{id}/books")
  public Author getAuthorBooks(@PathVariable Long id){
    return authorService.getAuthor(id);
  }

  //Single Author without his Books
  @GetMapping("/{id}")
  public AuthorDTO getAuthor(@PathVariable Long id){
    return authorService.getAuthorBook(id);
  }

  //Create Author
  @PostMapping()
  public void createAuthor(@RequestBody Author author){
    authorService.createAuthor(author);
  }

  //Delete Author and all his Books, because of cascade remove
  @DeleteMapping("/{id}")
  public void deleteAuthor(@PathVariable Long id){
    authorService.deleteAuthor(id);
  }

  //Update Author name
  @PatchMapping("/{id}")
  public void patchAuthor(@PathVariable Long id, @RequestBody Author author){
    authorService.patchAuthor(id, author);
  }
}
