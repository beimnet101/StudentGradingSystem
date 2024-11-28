package org.example.service;

import org.example.Dto.req.LoanReq;
import org.example.Dto.res.BookResDto;
import org.example.Dto.res.CatagoryRes;
import org.example.Dto.res.StatusDto;
import org.example.dao.BookDao;
import org.example.dao.CatagoryDao;
import org.example.dao.LoanedbookDao;
import org.example.model.Book;
import org.example.model.Loanedbook;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
@Stateless
public class LibraryUserService {
    @EJB
   BookDao bookDao;
    @EJB
    CatagoryDao catagoryDao;
    @EJB
    LoanedbookDao loanedbookDao;

    public List<BookResDto> listAllBook(Integer start,Integer end){

        BookResDto bookResDto = new BookResDto();
        List<BookResDto> bookResDtoList = new ArrayList<>();
        List<Book> bookList = this.bookDao.listAll(start,end);
        Loanedbook loanedbook =new Loanedbook();
        if (bookList.size() != 0) {
         /*   bookResDto.setCount(0);
            bookResDto.setBookStatus(loanedbook.getLoanstatus());
            //bookResDto.setStatusDto(new StatusDto(true, "empty list"));

            //return bookResDto;*/

            bookList.forEach(book -> {
                BookResDto bookResDto1 = new BookResDto();
                bookResDto1.setTitle(book.getTitle());
                bookResDto1.setBookStatus(loanedbook.getLoanstatus());
                bookResDto1.setCatagory(book.getCatagory());


                bookResDtoList.add(bookResDto1);

            });
        }
        /*bookResDto.setCount(bookList.size());
        bookResDto.setBookStatus(loanedbook.getLoanstatus());*/
        //bookResDto.setStatusDto(new StatusDto(true,"successfull"));
        return bookResDtoList;

    }

    public List<Book> findByCatagory(String catagory) {
        List<Book> catagoryResList = new ArrayList<>();
        List<Book> catagoryList = catagoryDao.findByCatagory(catagory);
        catagoryList.forEach(catagory1 -> {

            CatagoryRes catagoryRes1 = new CatagoryRes();
            catagoryRes1.setTitle(catagory1.getTitle());
            catagoryResList.add(catagory1);

        });
        return catagoryResList;
    }
    public StatusDto orderBook(LoanReq loanReq, String title){

        Book book = bookDao.findByTitle(title);
        //Loanedbook loanedBook1 = loanedbookDao.findByStatus(book);
        Loanedbook loanedBook1 =new Loanedbook();
        if(loanedBook1.getLoanstatus().equals("available")) {
           // loanedBook1.setBook(loanReq.getBook());
            loanedBook1.setDateloaned(loanReq.getDateLoaned());
            //loanedBook1.setDatereturned(loanReq.getDateReturned());

            loanedbookDao.create(loanedBook1);
            return new StatusDto(true,"available");
        }
        return new StatusDto(false,"not available");


    }
}
