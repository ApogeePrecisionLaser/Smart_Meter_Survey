/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    private List<String> fileList;
    private static String OUTPUT_ZIP_FILE = "";
    private static String SOURCE_FOLDER = ""; // SourceFolder path

    public void zipIt(String zipFile) {
        byte[] buffer = new byte[1024];
        String source = "";
        String sourceFolder = "";
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            try {
                source = SOURCE_FOLDER.substring(SOURCE_FOLDER.lastIndexOf("\\") + 1, SOURCE_FOLDER.length());
            } catch (Exception e) {
                source = SOURCE_FOLDER;
            }
            fos = new FileOutputStream(OUTPUT_ZIP_FILE);
            zos = new ZipOutputStream(fos);

            System.out.println("Output to Zip : " + OUTPUT_ZIP_FILE);
            FileInputStream in = null;
            sourceFolder=source.substring(source.lastIndexOf("/"));


            for (String file : this.fileList) {
                System.out.println("File Added : " + file);
                String folder=file.replaceAll("\\", "_");
                folder=folder.split("_")[0];
                String zFile=new File(zipFile+ File.separator + file).getName();
                ZipEntry ze = new ZipEntry(sourceFolder+"\\"+folder+"\\"+zFile);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(zipFile + File.separator + file);
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                }catch(Exception e){
                    System.out.println("");
                } finally {
                    in.close();
                }
            }

            zos.closeEntry();
            System.out.println("Folder successfully compressed");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateFileList(File node) {

        // add file only
        if (node.isFile()) {
            fileList.add(generateZipEntry(node.toString()));

        }

        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename : subNote) {
                generateFileList(new File(node, filename));
            }
        }
    }

    private String generateZipEntry(String file) {
        return file.substring(SOURCE_FOLDER.length() + 1, file.length());
    }


    public  String getOUTPUT_ZIP_FILE() {
        return OUTPUT_ZIP_FILE;
    }

    public void setOUTPUT_ZIP_FILE(String OUTPUT_ZIP_FILE) {
        ZipUtils.OUTPUT_ZIP_FILE = OUTPUT_ZIP_FILE;
    }

    public String getSOURCE_FOLDER() {
        return SOURCE_FOLDER;
    }

    public void setSOURCE_FOLDER(String SOURCE_FOLDER) {
        ZipUtils.SOURCE_FOLDER = SOURCE_FOLDER;
    }

    public ZipUtils() {
        fileList = new ArrayList<String>();
    }
    
}
