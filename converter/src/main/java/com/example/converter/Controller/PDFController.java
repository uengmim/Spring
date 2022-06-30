package com.pdfread.converter.Controller;

import com.pdfread.converter.Service.ConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;

@Controller
public class PDFController {

    @Autowired
    private ConvertService convertService;

    @Autowired
    HttpSession session;

    @GetMapping("/")
    public String mainPage() {
        return "main";
    }

    @PostMapping("/checkAlive")
    @ResponseBody
    public String sessionCheck() {

        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet) {
            if (thread.getName().startsWith("ThreadPoolTaskScheduler")) {
                System.out.println(thread.getName() + " " + thread.getState());
                if(thread.getState().toString().equals("WAITING")) {
                    return "dead";
                }
               return "alive";
            }
        }
        return "dead";
    }



    @PostMapping("/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("pdfFiles")MultipartFile[] pdfFiles, @RequestParam("category") String category) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime time = LocalDateTime.now();
        String currentTime = time.format(formatter);

        String sessionID = session.getId() + currentTime;
        String result = sessionID;
        if(!Objects.isNull(pdfFiles)){
            /*저장 하기*/
            convertService.saveFile(pdfFiles, sessionID);
            /**
             * CATEGORY
             * 1.OCR
             * 2.PDF
             * **/
            /*변환 하기*/
            if(category.equals("OCR") && !convertService.renameFileOCRAsync(sessionID)) {
                result = "convertFail";
            }else if(category.equals("PDF") && !convertService.renameFilePDF(sessionID)) {
                result = "convertFail";
            }
        }
        return result;
    }

    @GetMapping("/download/{sessionId}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable("sessionId") String sessionId) {
        String zipFilePath = convertService.makeZipFile(sessionId);
        FileInputStream fis = null;
        File file = new File(zipFilePath);
        try {
           fis = new FileInputStream(file);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        InputStreamResource resource = new InputStreamResource(fis);

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/zip"))
                .body(resource);

    }

}
