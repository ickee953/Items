/**
 * © Panov Vitaly 2023 - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Panov Vitaly <vetalpanov@gmail.com>, September 2022
 */

package com.online.items.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    //private final String UPLOAD_DIR = "/home/tomcat/uploads";

    //@Value("${upload.dir}")
    //private String uploadDir;
    private Path root;

    @Autowired
    public FileService(@Value(value = "${upload.dir}") String uploadDir){
        root = Paths.get( uploadDir ).toAbsolutePath();
    }

    public void init() {
        try {
            Files.createDirectory(root);

            StringBuilder sb = new StringBuilder();
            sb.append(root);
            sb.append(" created.\n");

        } catch (IOException e) {
            if( e instanceof FileAlreadyExistsException ){
                StringBuilder sb = new StringBuilder();
                sb.append("File: ");
                sb.append(root.toUri());
                sb.append(" already exist.\n");

            } else {
                StringBuilder sb = new StringBuilder("Could not initialize folder for upload: ");
                sb.append( root ).append("\n");

                throw new RuntimeException( sb.toString() );
            }
        }
    }

    /**
     * Return url string to uploaded item picture
     *
     * @param file
     * @return
     */
    public String uploadFile(MultipartFile file, String fileName) {
        //Path root = Paths.get( uploadDir ).toAbsolutePath();
        try {
            Files.copy(file.getInputStream(), root.resolve(fileName));

            return fileName;
        } catch (Exception e) {
            if( e instanceof FileAlreadyExistsException){
                try {
                    Files.delete( root.resolve(fileName) );
                } catch (IOException ex) {
                    ex.printStackTrace();

                    throw new RuntimeException( ex.getMessage() );
                }
                return uploadFile( file, fileName );
            } else {
                String message = "Could not store the file. Error: " + e.getMessage();

                throw new RuntimeException( message );
            }
        }
    }

    public Resource load(String filename) {
        //Path root = Paths.get( uploadDir ).toAbsolutePath();
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
