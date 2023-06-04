package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.annotation.JsonFormat;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import org.apache.poi.xwpf.usermodel.*;

import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import java.io.BufferedReader;
import java.io.FileReader;
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

        int i=0,size=questions.size();
        while (i<size)
        {
            XWPFParagraph tmpPar=doc.createParagraph();
            XWPFRun tmpRun=tmpPar.createRun();
            tmpRun.setText(questions.get(i).getQuestion().getQuestionTitle(points.get(i),i+1));
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

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = df.parse("2023-10-25 23:30");
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, 60);
        String newDate = df.format(cal.getTime());
        System.out.println(newDate);
    }
}
