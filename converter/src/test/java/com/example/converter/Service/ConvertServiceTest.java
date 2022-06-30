package com.pdfread.converter.Service;

import net.sourceforge.tess4j.TesseractException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvertServiceTest {

    @Value("${save.folder}")
    public String uploadDir;

    @Value("${tesdata.folder}")
    public String tesdataDir;

    public String folderPath;

    @Autowired
    public ConvertService convertService;

    @Test
    public void getProperties() {
        assertThat(uploadDir).isEqualTo("c:/uploads");
        System.out.println(uploadDir);
    }

    @Test
    public void renameFileFromExistFile() throws IOException, TesseractException {

        String folderId = "수기파일";
        convertService.renameFileOCR(folderId);

    }

}