﻿package com.jianglibo.wx.crawl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.jianglibo.wx.util.DirectoryUtil;

public class CrawlProcesses {

	private static Pattern unjarPtn = Pattern.compile(".*WARN fs\\.FileUtil: Failed.*\\[([^\\]]+)\\].*");
	
	private static Pattern errorLinePtn = Pattern.compile("^\\d{2}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2} ERROR .*");
	
	private static Pattern lastexitPtn = Pattern.compile(".*LASTEXITCODE:(.+):EDOCTIXETSAL.*");
	
	private static Logger log = LoggerFactory.getLogger(CrawlProcesses.class);
	
	private static Charset cachedCharset = null;
	
	private static synchronized Charset getCachedCharset(Charset detected) {
		if (detected != null) {
			cachedCharset = detected;
		}
		return cachedCharset;
	}

	
	private Path unjarRoot;
	
	@Value("${myapp.unjarRoot}")
	public void setUnjarRoot(String unjarRootStr) {
		this.unjarRoot = Paths.get(unjarRootStr);
		if (!Files.exists(this.unjarRoot)) {
			this.unjarRoot = Paths.get(System.getProperty("java.io.tmpdir"));
		}
	}
	
	public static CrawlStepProcess newStep(Path projectRoot, List<String> optlist) {
		List<String> cmdList = new ArrayList<>();
		cmdList.add("powershell");
		cmdList.add("-F");
		cmdList.add(projectRoot.resolve("src/bin/nutch.ps1").toAbsolutePath().normalize().toString());
		cmdList.addAll(optlist);
		return new CrawlStepProcess(projectRoot.toAbsolutePath().normalize(), cmdList.toArray(new String[]{}));
	}


	
	public static class CrawlStepProcess implements Callable<Integer> {
		
		private final Path projectRoot;
		
		private final Path pblog;
		
		private final String[] cmds;
		
		private Path unjarPath;
		
		private int exitCode;
		
		private final List<String> errorLines = new ArrayList<>();
		
		private List<String> allLines = new ArrayList<>();
		
		public CrawlStepProcess(Path projectRoot, String...cmds) {
			String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuuMMddHHmmss"));
			projectRoot = projectRoot.toAbsolutePath().normalize();
			this.projectRoot = projectRoot;
			this.pblog = projectRoot.resolve("logs").resolve("step-" + now + ".log");
			this.cmds = cmds;
		}

		@Override
		public Integer call() throws Exception {
			 ProcessBuilder pb =
					   new ProcessBuilder(cmds);
			 pb.directory(projectRoot.toFile());
			 pb.redirectErrorStream(true);
			 if (!Files.exists(pblog.getParent())) {
				 Files.createDirectories(pblog.getParent());
			 }
			 pb.redirectOutput(Redirect.to(pblog.toFile()));
			 log.info("starting invoking cmd: {}", String.join(" ", cmds));
			 Process p = pb.start();
			 p.waitFor();
			 postExecute();
			 return p.exitValue();
		}
		
		private void postExecute() {
			allLines = readAllLines();
			if (!allLines.isEmpty()) {
				deleteUnjarIfNotDeleted();
				detectErrors();
				detectExitCode();
			}
		}
		
		
		public List<String> readAllLines() {
			Charset cs = getCachedCharset(null);
			if ( cs != null) {
				try {
					return Files.readAllLines(pblog, cs);
				} catch (IOException e) {
					log.error("cannot parse log file {} by {} charseter set", pblog.toString(), cs.name());
				}
			}
			
			for(Charset cs1 : Charset.availableCharsets().values()) {
				try {
					List<String> lines = Files.readAllLines(pblog, cs1);
					getCachedCharset(cs1);
					return lines;
				} catch (Exception e) {
					log.error("cannot parse log file {} by {} charseter set", pblog.toString(), cs1.name());
				}
			}
			return new ArrayList<>();
		}
		
		private void detectExitCode() {
			Optional<String> maybeLastExitcode = allLines.stream().filter(line -> lastexitPtn.matcher(line).matches()).findFirst();
			if (maybeLastExitcode.isPresent()) {
				Matcher m = lastexitPtn.matcher(maybeLastExitcode.get());
				if (m.matches()) {
					setExitCode(Integer.valueOf(m.group(1)));
				}
			}
		}

		private void detectErrors() {
			errorLines.addAll(allLines.stream().filter(line -> errorLinePtn.matcher(line).matches()).collect(Collectors.toList()));
		}
		
		private void deleteUnjarIfNotDeleted() {
			Optional<String> mayBeMatched = allLines.stream().filter(line -> unjarPtn.matcher(line).matches()).findFirst();
			if (mayBeMatched.isPresent()) {
				Matcher m = unjarPtn.matcher(mayBeMatched.get());
				if (m.matches()) {
					Path jarp = Paths.get(m.group(1));
					unjarPath = DirectoryUtil.findMiddlePathStartsWith(jarp, "hadoop-unjar");
					try {
						DirectoryUtil.deleteRecursiveIgnoreFailed(unjarPath);
					} catch (FileNotFoundException e) {
						log.error("cannot unjar folder {}", unjarPath.toString());
					}
				}
			}
		}

		public Path getProjectRoot() {
			return projectRoot;
		}

		public Path getPblog() {
			return pblog;
		}

		public Path getUnjarPath() {
			return unjarPath;
		}

		public List<String> getErrorLines() {
			return errorLines;
		}

		public int getExitCode() {
			return exitCode;
		}

		public void setExitCode(int exitCode) {
			this.exitCode = exitCode;
		}
	}
	
	
	// --- whole crawl ---
	
	private static CrawlProcessBuilder newCrawlBuilder(Path projectRoot, String crawlId, int limit) {
		 return new CrawlProcessBuilder(projectRoot, crawlId, limit);
	}

	
	private static class CrawlProcessBuilder {
		private final String crawlId;
		private final int limit;
		private String seedDir;
		private int numSlaves = 1;
		private boolean forceFetching;
		private int addDays = 0;
		
		private final Path projectRoot;
		
		public CrawlProcessBuilder(Path projectRoot, String crawlId, int limit) {
			super();
			this.projectRoot = projectRoot;
			this.crawlId = crawlId;
			this.limit = limit;
		}

		public CrawlProcessBuilder seedDir(String seedDir) {
			this.seedDir = seedDir;
			return this;
		}
		public CrawlProcessBuilder numSlaves(int numSlaves) {
			this.numSlaves = numSlaves;
			return this;
		}
		public CrawlProcessBuilder forceFetching(boolean forceFetching) {
			this.forceFetching = forceFetching;
			return this;
		}
		public CrawlProcessBuilder addDays(int addDays) {
			this.addDays = addDays;
			return this;
		}
		public CrawlStepProcess build() {
			List<String> cmdList = new ArrayList<>();
			cmdList.add("powershell");
			cmdList.add("-F");
			cmdList.add(projectRoot.resolve("src/bin/crawl.ps1").toAbsolutePath().normalize().toString());
			cmdList.add("-CRAWL_ID");
			cmdList.add(crawlId);
			cmdList.add("-LIMIT");
			cmdList.add(String.valueOf(limit));
			if (addDays > 0) {
				cmdList.add("-ADD_DAYS");
				cmdList.add(String.valueOf(addDays));
			}
			if (seedDir == null || seedDir.trim().isEmpty()) {
				cmdList.add("-SKIP_INJECT");
			} else {
				cmdList.add("-SEEDDIR");
				cmdList.add(seedDir);
			}
			if (forceFetching) {
				cmdList.add("-FORCE_FETCHING");
			}
			cmdList.add("-NUM_SLAVES");
			cmdList.add(String.valueOf(numSlaves));
			CrawlStepProcess crawl = new CrawlStepProcess(projectRoot, cmdList.toArray(new String[]{}));
			return crawl;
		}
	}
}
