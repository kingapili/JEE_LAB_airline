package pl.edu.pg.eti.s176010.airline.file;

import lombok.extern.java.Log;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

@Log
public class FileUtility {

    public static byte[] getBytes(String dir, String filename) throws IOException {
        try {
            Path path = Paths.get(dir, filename);
            return Files.readAllBytes(path);
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
            throw new IOException(ex);
        }

    }

    public static void saveFile(String dir, String filename, byte[] content) throws IOException {
        try {
            Path newFilePath = Paths.get(dir, filename);
            if(!Files.exists(newFilePath))
                Files.createFile(newFilePath);
            Files.write(newFilePath, content);
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
            throw new IOException(ex);
        }

    }

    public static void deleteFile(String dir, String filename) throws IOException {
        try {
            Path path = Paths.get(dir, filename);
            Files.deleteIfExists(path);
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
            throw new IOException(ex);
        }
    }
}
