package com.jianglibo.wx.webapp.authorization;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.junit.Test;

public class TestFileUploadFilter {
	
	private Path destFolder = Paths.get("abc");
	private Pattern ptn = Pattern.compile("abc/[a-z0-9]{32}(\\.txt)?");
	
	@Test
	public void t() {
		
		Path destPath = FileUploadFilter.getDestPath(destFolder, "abc.txt");
		ast(destPath);
		
		assertTrue(destPath.toAbsolutePath().toString().length() > 50);
		
		destPath = FileUploadFilter.getDestPath(destFolder, "c:\\a\\b\\c\\abc.txt");
		ast(destPath);
		
		destPath = FileUploadFilter.getDestPath(destFolder, "c:\\a\\b\\c\\abc");
		ast(destPath);
		
		destPath = FileUploadFilter.getDestPath(destFolder, "c:/a/b/c/abc");
		ast(destPath);
		
		Path a = Paths.get("c:/a");
		Path b = Paths.get("c:/a/b.txt");
		String s = a.relativize(b).toString();

		assertTrue(s, s.equals("b.txt"));
	}
	
	private void ast(Path destPath) {
		String s = destPath.toString().replaceAll("\\\\", "/");
		assertTrue(s, ptn.matcher(s).matches());
	}

}
