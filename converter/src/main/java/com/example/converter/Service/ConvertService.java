package com.pdfread.converter.Service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ConvertService {
    @Autowired
    AsyncService asyncService;

    @Value("${save.folder}")
    public String uploadDir;

    @Value("${tesdata.folder}")
    public String tesdataDir;

    public String folderPath;

    public int count;

    /*업로드 기능 테스트 완료*/
    public boolean saveFile(MultipartFile[] multipartFiles, String folderID) {
        folderPath = uploadDir + "/" + folderID;
        try {
            if(!new File(folderPath).exists()){
                new File(folderPath).mkdir();
            }
            int fileCount = multipartFiles.length;
            for(int i = 0; i < fileCount; i++) {
                String originalName = multipartFiles[i].getOriginalFilename();
                String savePath = folderPath + "/"+originalName;
                File file = new File(savePath);
                multipartFiles[i].transferTo(file);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /*문서 타입이 스캔본일 경우*/
    /*public boolean renameFileOCR(String folderID) {
        folderPath = uploadDir + "/" + folderID;
        File file;
        try{
            Stream<Path> walk = Files.walk(Paths.get(folderPath));
            List<String> list = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            list.forEach(System.out::println); //파일 풀 패스 경로

            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(tesdataDir);
            tesseract.setLanguage("kor");

            Pattern pattern = Pattern.compile("(PE﹕)+.*\\S");
            Matcher matcher;

            for(int i = 0; i < list.size(); i++) {
                file = new File(list.get(i));
                String result = tesseract.doOCR(file);
                //System.out.println(result); //파일 본문 내용 출력
                matcher = pattern.matcher(result);
                while(matcher.find()){
                    //System.out.println(matcher.group()); //Pattern과 매칭되는 부분 출력
                    file.renameTo(new File(folderPath + "/" + matcher.group().substring(5) + "." + getFileExtension(file)));
                }
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
            return false;
        } catch(TesseractException te) {
            te.printStackTrace();
            return  false;
        }

        return true;
    }*/

    public boolean renameFileOCRAsync(String folderID) {
        count = 0;
        folderPath = uploadDir + "/" + folderID;
        File file;
        try{
            Stream<Path> walk = Files.walk(Paths.get(folderPath));
            List<String> list = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            list.forEach(System.out::println); //파일 풀 패스 경로

            for(int i = 0; i < list.size(); i++) {
               asyncService.convertOCR(list.get(i),folderPath);

            }

            while(true) {
               if(count == list.size()) {
                   break;
               }
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
            return false;
        } catch(TesseractException te) {
            te.printStackTrace();
            return false;
        }

        return true;
    }


    /*문서 타입이 일반 PDF일 경우*/
    public boolean renameFilePDF(String folderID) {
        folderPath = uploadDir + "/" + folderID;

        try{
            Stream<Path> walk = Files.walk(Paths.get(folderPath));
            List<String> list = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            File file;

            Pattern pattern = Pattern.compile("(TYPE:)+.*\\S");
            Matcher matcher;
            PDDocument document;
            for(int  i = 0; i < list.size(); i++) {
                file = new File(list.get(i));

                document = PDDocument.load(file);
                document.getClass();
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper textStripper = new PDFTextStripper();

                String pdfFileInText = textStripper.getText(document);
                document.close();
                /*본문 내용 전부 출력*/
                //System.out.println(pdfFileInText);
                matcher = pattern.matcher(pdfFileInText);
                while(matcher.find()) {
                    //System.out.println(matcher.group());
                    file.renameTo(new File(folderPath + "/" + matcher.group().substring(7) + "." + getFileExtension(file)));
                }

            }

        } catch(IOException ie) {
            ie.printStackTrace();
            return false;
        }

       return true;
    }

    public String makeZipFile(String folderID) {
        String zipFilePath = folderPath + "/" + folderID + ".zip";
        if(new File(zipFilePath).exists())
            return zipFilePath;
        try{
            Stream<Path> walk = Files.walk(Paths.get(folderPath));
            List<String> list = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            //list에 사용자 폴더의 모든 파일의 풀 패스 경로가 담겨 있다.
            FileOutputStream fos = new FileOutputStream(zipFilePath);
            ZipOutputStream zos = new ZipOutputStream(fos);

            for(String srcFile : list) {
                File fileToZip = new File(srcFile);
                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                zos.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while((length = fis.read(bytes)) >= 0) {
                    zos.write(bytes, 0, length);
                }
                fis.close();
            }
            zos.close();
            fos.close();

        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        return zipFilePath;
    }

    public String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }

    /*일 배치로 새벽에 파일 삭제하기 */
    @Scheduled(cron="0 0 4 * * *") //매일 새벽 4시에 폴더 일괄 삭제
    public void deleteFileBatch() {
        try{
            Stream<Path> walk = Files.walk(Paths.get(uploadDir));
            List<String> list = walk.filter(Files::isDirectory)
                    .map(x -> x.toString()).collect(Collectors.toList());
            for( int i = 1; i < list.size(); i++) {
                System.out.println(list.get(i));
                FileUtils.deleteDirectory(new File(list.get(i)));
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /*파일 저장하지 않고 시도 했으나 에러 발생 - 미사용 코드*/
    public void renameFile(MultipartFile[] multipartFiles) throws IOException, TesseractException {
        int fileCount = multipartFiles.length;
        File file;
        Tesseract tesseract = new Tesseract();
        for(int i = 0 ; i < fileCount; i++) {
            file = convertMF(multipartFiles[i]);


            tesseract.setDatapath(uploadDir);
            String result = tesseract.doOCR(file);
            System.out.println(result);

/*          PDF TEXT CONTENT READING

            PDDocument document = PDDocument.load(file);
            document.getClass();
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);

            PDFTextStripper textStripper = new PDFTextStripper();

            String pdfFileInText = textStripper.getText(document);
            System.out.println(pdfFileInText);*/

        }

    }
    /*temp영역에 저장된 상태의 multipart를 file타입으로 변경 - 미사용 코드*/
    public File convertMF(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }

}
