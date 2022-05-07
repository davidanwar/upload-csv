package com.abs.uploadcsv.service;

import com.abs.uploadcsv.entity.BookEntity;
import com.abs.uploadcsv.repository.BookRepository;
import com.abs.uploadcsv.utility.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<BookEntity> save(MultipartFile file) {
        try {
            List<BookEntity> books = CSVHelper.csvToBook(file.getInputStream());
            return bookRepository.saveAll(books);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BookEntity> findAll() {
        return bookRepository.findAll();
    }
}
