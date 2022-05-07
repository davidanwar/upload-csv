package com.abs.uploadcsv.utility;

import com.abs.uploadcsv.entity.BookEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {

    private static final String TYPE = "text/csv";
    //private static String[] HEADER = {"Id", "Title", "Description", "Price"};

    public static boolean isCsvFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        } else {
            return true;
        }
    }

    public static List<BookEntity> csvToBook(InputStream inputStream) {
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            CSVParser parser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            List<BookEntity> books = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = parser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                BookEntity book = new BookEntity();
                book.setId(Long.parseLong(csvRecord.get("Id")));
                book.setTitle(csvRecord.get("Title"));
                book.setDescription(csvRecord.get("Description"));
                book.setPrice(Double.parseDouble(csvRecord.get("Price")));
                books.add(book);
            }
            parser.close();
            return books;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
