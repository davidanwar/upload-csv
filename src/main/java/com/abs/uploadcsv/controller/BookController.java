package com.abs.uploadcsv.controller;

import com.abs.uploadcsv.dto.ResponseData;
import com.abs.uploadcsv.entity.BookEntity;
import com.abs.uploadcsv.service.BookService;
import com.abs.uploadcsv.utility.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/")
    public ResponseEntity<?> findAllBook() {
        ResponseData response = new ResponseData();
        try {
            List<BookEntity> books = bookService.findAll();
            response.setStatus(true);
            response.getMessages().add("Sukses Mendapat Data");
            response.setPayload(books);
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            response.setStatus(false);
            response.getMessages().add("Failed " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> uploadBooks(@RequestParam("file") MultipartFile file) {
        ResponseData response = new ResponseData();

        if (!CSVHelper.isCsvFormat(file)) {
            response.setStatus(false);
            response.getMessages().add("File bukan CSV");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            List<BookEntity> books = bookService.save(file);
            response.setStatus(true);
            response.getMessages().add("Upload File Successfully " + file.getOriginalFilename());
            response.setPayload(books);
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            response.setStatus(false);
            response.getMessages().add("Failed " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
