package me.jonas.kviring.bookvault_api.Author;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {
  
  private final AuthorRepository authorRepository;

  //Return all Author Object
  public List<Author> getAllAuthors() {
    return authorRepository.findAll();
  }

  //Return all Author Object without book array
  public List<AuthorDTO> getAllAuthorsNames() {

    List<Author> list =authorRepository.findAll();
    List<AuthorDTO> finalList = new ArrayList<>();
    
    for(Author element: list){
      finalList.add(new AuthorDTO(element.getId(), element.getName()));
    }

    return finalList;
  }

  //Return single Author
  public Author getAuthor(Long id) {
    return authorRepository.findById(id).orElseThrow(() -> new IllegalStateException(""));
  }

  //Return single Author without his Books
  public AuthorDTO getAuthorBook(Long id) {
    Author tempAuthor =authorRepository.findById(id).orElseThrow(() -> new IllegalStateException(""));
    AuthorDTO authorDTO = new AuthorDTO(tempAuthor.getId(), tempAuthor.getName());
    return authorDTO;
  }

  //Create an author
  public void createAuthor(Author author) {
    authorRepository.save(author);
  }

  //Delete an author with all of his books
  public void deleteAuthor(Long id) {
    authorRepository.deleteById(id);
  }

  //Patch an author-object
  public void patchAuthor(Long id, Author author) {
    Author tempAuthor = authorRepository.findById(id).orElseThrow(()-> new IllegalStateException("Author doesn´t exist"));
    tempAuthor.setName(author.getName());
    authorRepository.save(tempAuthor);
  }


  

}
