package com.hamitmizrak.javase.files;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

public class FilePathData {

    // Variable
    private String id;
    private String pathFileName;
    private String pathDirectoryName;
    private String url;
    private File file;
    private Date systemCreatedDate;

    // Parametresiz Constructor
    public FilePathData() {
        this.id = UUID.randomUUID().toString();
        this.systemCreatedDate = new Date(System.currentTimeMillis());
        pathFileName = "\\log.txt";
        // C:\io\techcareer\full_3
        pathDirectoryName = FilePathUrl.MY_FILE_PATH_URL;
        // C:\\io\\techcareer\\full_3\\log.txt
        url = pathDirectoryName.concat(pathFileName);
        file = new File(url);
        try {
            // Böyle bir dosya var mı?
            if (file.createNewFile()) {
                System.out.println(pathFileName + "Böyle bir dosya yoktur ve oluşturuldu.");
                System.out.println("Permission: Okunabilinir mi ?" + file.canRead() + " yazılabilinir mi? " + file.canWrite() + " Çalıştırılabilinir mi " + file.canExecute());
                // toString
                System.out.println("ID: " + this.id + " URL: " + this.url + " Hash Code: " + file.hashCode());
                // logFileWriter();// Writer
                // logFileReader();// Reader
               // fileIsDelete(); // Delete
            } else {
                String fileName = pathFileName + "Böyle bir dosya var tekrardan oluşturulmadı.";
                System.out.println(fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } //end constructor

    // toString
    @Override
    public String toString() {
        return "FilePathData{" +
                "id='" + id + '\'' +
                ", pathFileName='" + pathFileName + '\'' +
                ", pathDirectoryName='" + pathDirectoryName + '\'' +
                ", url='" + url + '\'' +
                ", file=" + file +
                ", systemCreatedDate=" + systemCreatedDate +
                '}';
    }

    // file Date Locale
    private String localeDateTime(){
        Locale locale= new Locale("tr","TR");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMMM-yyyy",locale);
        Date date=new Date();
        String changeDate=simpleDateFormat.format(date);
        return changeDate;
    }

    // File Writer
    public void logFileWriter(String email, String password) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.url, true))) {
            String data="[ "+ localeDateTime()+" ] "+email+" "+password;
            bufferedWriter.write(data+"\n");
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } //end Writer

    // File Reader
    public void logFileReader() {
        String rows; // okunan satır
        StringBuilder stringBuilder = new StringBuilder();
        String builderToString;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.url))) {
            while ((rows = bufferedReader.readLine()) != null) {
                stringBuilder.append(rows);
            }
            builderToString=stringBuilder.toString();
            System.out.println("OKUNDU:" +builderToString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } //end Reader

    // File Delete
    public void fileIsDelete(){
        Scanner klavye=new Scanner(System.in);
        char chooise;
        System.out.println(pathFileName+" bu dosyayı silmek ister misiniz ? E / H");
        chooise=klavye.nextLine().charAt(0);
        if(chooise=='E' || chooise=='e'){
            file.exists();
        }else{
            System.out.println(pathFileName+ "Silinmedi");
        }
    }

}// end class FilePathData
