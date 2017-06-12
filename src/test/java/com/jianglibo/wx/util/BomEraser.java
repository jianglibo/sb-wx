package com.jianglibo.wx.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class BomEraser {
	
	private void printme(Object o) {
		System.out.println(o);
	}

	@Test
	public void t() throws IOException {
		Path cur = Paths.get(".").toAbsolutePath().normalize();
		
		Files.walk(cur).filter(p -> Files.isRegularFile(p)).filter(p -> p.getFileName().toString().endsWith(".java")).forEach(
				p -> {
					try {
						byte[] bs = Files.readAllBytes(p);
						if (bs.length > 2 && bs[0] == -17 && bs[1] == -69 && bs[2] == -65) {
							byte[] nbs = new byte[bs.length - 3]; 
							System.arraycopy(bs, 3, nbs, 0, nbs.length);
							Files.write(p, nbs);
						}
//						List<String> lines = Files.readAllLines(p, StandardCharsets.UTF_8);
//						Files.write(p, lines, StandardCharsets.UTF_8);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					}
				);
	}
}
