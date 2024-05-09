package com.aroska.fifa.filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileSystemTest {
	
	public static void main(String[] args) {
		Path p1 = Paths.get("C:\\testFiles\\test.txt");
		Path p2 = Paths.get("C:\\testFiles\\newDir\\test.txt");
		try {
//			Files.createDirectories(p2);
			Files.createFile(p2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();
		
	}
}
