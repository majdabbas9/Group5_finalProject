package il.cshaifasweng.OCSFMediatorExample.client;

import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.annotation.JsonFormat;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import java.util.List;
import java.util.stream.Collectors;

public class WordGeneratorFile {
    public static String examsPath=FileSystemView.getFileSystemView().getHomeDirectory().getPath()+"\\exams\\";
    public List<String> readLines(File filename) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        List<String> lines = bufferedReader.lines().filter(Objects::nonNull).collect(Collectors.toList());
        bufferedReader.close();
        return lines;
    }
    //Create Word
    public static void createWord(List<Exam_Question> questions, List<Integer>points, String courseName, String teacherName, String fileName) throws IOException {
        if (!Paths.get(examsPath).toFile().exists()) Files.createDirectories(Paths.get(examsPath));
        XWPFDocument doc=new XWPFDocument();

        XWPFParagraph title = doc.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setText(courseName);
        titleRun.setColor("009933");
        titleRun.setBold(true);
        titleRun.setFontFamily("Courier");
        titleRun.setFontSize(20);

        XWPFParagraph grade = doc.createParagraph();
        XWPFRun gradeRun = grade.createRun();
        gradeRun.setText("grade = ");
        gradeRun.setColor("0000FF");
        gradeRun.setBold(true);
        gradeRun.setFontFamily("Courier");
        gradeRun.setFontSize(18);

        int i=0,size=questions.size();
        while (i<size)
        {
            XWPFParagraph tmpPar=doc.createParagraph();
            XWPFRun tmpRun=tmpPar.createRun();
            tmpRun.setText(questions.get(i).getQuestion().getQuestionTitle(questions.get(i).getPoints(),i+1));
            tmpRun.setFontSize(18);

            XWPFParagraph tmpPar2=doc.createParagraph();
            XWPFRun tmpRun2=tmpPar2.createRun();
            tmpRun2.setText(questions.get(i).getQuestion().questionsString());
            tmpRun2.setFontSize(16);
            i++;
        }
        XWPFParagraph end = doc.createParagraph();
        end.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun endRun = end.createRun();
        endRun.setText("Good Luck! "+teacherName);
        endRun.setColor("009933");
        endRun.setBold(true);
        endRun.setFontFamily("Courier");
        endRun.setFontSize(20);

        String folder=examsPath;String file=fileName+".docx";
        File f=new File(folder);
        if(!f.exists())
        {
            f.mkdirs();
        }
        FileOutputStream out =new FileOutputStream(new File(folder+file));
        doc.write(out);doc.close();out.close();
    }
    public  static void   createWordTest(List<String> strings) throws IOException {
        if (!Paths.get(examsPath).toFile().exists()) Files.createDirectories(Paths.get(examsPath));
        XWPFDocument doc=new XWPFDocument();

        XWPFParagraph title = doc.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setText("hi");
        titleRun.setColor("009933");
        titleRun.setBold(true);
        titleRun.setFontFamily("Courier");
        titleRun.setFontSize(20);

        int i=0,size=strings.size();
        while (i<size)
        {
            XWPFParagraph tmpPar=doc.createParagraph();
            XWPFRun tmpRun=tmpPar.createRun();
            tmpRun.setText(strings.get(i));
            tmpRun.setFontSize(18);
            i++;
        }

        XWPFParagraph end = doc.createParagraph();
        end.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun endRun = end.createRun();
        endRun.setText("Good Luck!");
        endRun.setColor("009933");
        endRun.setBold(true);
        endRun.setFontFamily("Courier");
        endRun.setFontSize(20);

        String folder=examsPath;String file="temp"+".docx";
        File f=new File(folder);
        if(!f.exists())
        {
            f.mkdirs();
        }
        FileOutputStream out =new FileOutputStream(new File(folder+file));
        doc.write(out);doc.close();out.close();
    }

    public static void main(String[] args) throws ParseException, IOException {
        List<String> strings=new ArrayList<>();
        strings.add("hi");strings.add("hhh");
        createWordTest(strings);
        copyFileUsingApacheCommonsIO("temp","temp225758");
        openWord("temp");
    }
    public static void copyFileUsingApacheCommonsIO(String path1,String path2) throws IOException {
        try {
            if (!Paths.get(examsPath).toFile().exists()) Files.createDirectories(Paths.get(examsPath));
            // Create a new document for the destination file
            XWPFDocument destinationDoc = new XWPFDocument();

            String folder=examsPath;String file=path2+".docx";
            File f=new File(folder);
            if(!f.exists())
            {
                f.mkdirs();
            }
            FileOutputStream out =new FileOutputStream(new File(folder+file));
            // Open the source file
            FileInputStream sourceFile = new FileInputStream(examsPath+path1+".docx");
            XWPFDocument sourceDoc = new XWPFDocument(sourceFile);


            // Copy the content from the source document to the destination document
            destinationDoc.getDocument().getBody().set(sourceDoc.getDocument().getBody().copy());
            // Save the destination document to a file

            destinationDoc.write(out);destinationDoc.close();out.close();
            System.out.println("Word file copied successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void openWord(String path)
    {
        Desktop desktop = Desktop.getDesktop();
        try {
            File f = new File(examsPath+path+".docx");
            desktop.open(f);  // opens application (MSWord) associated with .doc file
        }
        catch(Exception ex) {
            // WordDocument.this is to refer to outer class's instance from inner class
        }
    }
    }

