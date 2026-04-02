package me.jonas.kviring.bookvault_api.Book;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.jonas.kviring.bookvault_api.Author.AuthorRepository;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository; 
  private final AuthorRepository authorRepository;


  //Return all books with Authors name
  public List<BookResponseDTO> getAllBooks() {
    List<Book> list = bookRepository.findAll();
    List<BookResponseDTO> finalList = new ArrayList<>();
    for(Book book: list){
      finalList.add(new BookResponseDTO( book.getId(), book.getTitle(), book.getIsbn(), book.getAuthor().getName()));
    }

    return finalList;
  }

  //Return single Book, with Authors name
  public BookResponseDTO getBook(Long id) {
    Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalStateException("Book doesnt exist"));
    return new BookResponseDTO(book.getId(), book.getTitle(), book.getIsbn(), book.getAuthor().getName());
  }

  //Get every Book title
  public List<String> getAllBookTitles(){
    List<BookResponseDTO> list=getAllBooks();
    return list.stream().map(BookResponseDTO::getTitle).toList();
  }

  //Get the ISBN with Book title
  public String getISBN(String title){
    Book book=  bookRepository.findByTitle(title);
    return book.getIsbn();

  }

  //Create a book
  public void createBook(BookDTO bookDTO){
    Book book= new Book(bookDTO.getTitle(), bookDTO.getIsbn(), authorRepository.findById(bookDTO.getAuthorId()).orElseThrow(()-> new IllegalStateException("Author doesnt exist")));
    bookRepository.save(book);
  }

  //Delete Book
  public void deleteBook(Long id){
    bookRepository.deleteById(id);
  }

  //Patch Book
  public void patchBook(Long id, BookDTO bookDTO){
    Book book= bookRepository.findById(id).orElseThrow(() -> new IllegalStateException("Book doesnt exist!"));
    if(bookDTO.getIsbn()!=null){
      book.setIsbn(bookDTO.getIsbn());
    }
    if(bookDTO.getTitle()!=null){
      book.setTitle(bookDTO.getTitle());
    }
    bookRepository.save(book);
  }
}
