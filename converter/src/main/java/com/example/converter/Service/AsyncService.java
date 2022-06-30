package com.pdfread.converter.Service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AsyncService {

    @Value("${tesdata.folder}")
    public String tesdataDir;

    @Autowired
    ConvertService service;

    @Async()
    public void convertOCR(String filePath, String folderPath) throws TesseractException {
        System.out.println("Execute method asynchronously - " + Thread.currentThread().getName());


        File file;
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(tesdataDir);
        tesseract.setLanguage("kor");

        Pattern pattern = Pattern.compile("(PE﹕)+.*\\S");
        Matcher matcher;

        file = new File(filePath);
        String result = tesseract.doOCR(file);
        matcher = pattern.matcher(result);
        while(matcher.find()) {
            file.renameTo(new File(folderPath + "/" + matcher.group().substring(5) + "." + service.getFileExtension(file)));
        }
        service.count++;
        System.out.println(service.count);
    }

}
