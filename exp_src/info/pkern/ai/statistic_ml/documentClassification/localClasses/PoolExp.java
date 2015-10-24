package info.pkern.ai.statistic_ml.documentClassification.localClasses;

import static org.junit.Assert.*;
import info.pkern.tools.MapUtil;
import info.pkern.tools.RecursiveSimpleFileVisitor;
import info.pkern.tools.MapUtil.SORT_ORDER;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import org.junit.Test;

public class PoolExp {

	@Test
	public void testLearn() throws Exception {

//		String basePath = "/Users/pkern/Google Drive/BOW";
		String basePath = "C:/Users/pascal/Google Drive/BOW";
		
		Path learnBase = Paths.get(basePath + "/learn_and_test/learn");
		Path testBase = Paths.get(basePath + "/learn_and_test/test");
		Pool pool = new Pool();
		pool.debug = false;
		
		FileVisitor<Path> fileProcessor = new RecursiveSimpleFileVisitor("txt");
		Files.walkFileTree(learnBase, fileProcessor);
		
		String fileContent;
		for (Path file : ((RecursiveSimpleFileVisitor) fileProcessor).getFiles()) {
			fileContent = new String(Files.readAllBytes(file)); // readAllLines(file, Charset.defaultCharset());
			BagOfWords trainBag = new BagOfWords();
			fileContent = fileContent.replaceAll("[\"]", "");
			trainBag.addWords(Arrays.asList(fileContent.split("[^\\wäöüÄÖÜß]*")));
//			trainBag.addWords(Arrays.asList(fileContent.split("[\\s,\\.;':!?]")));
			pool.learn(trainBag, file.getParent().getFileName().toString());
		}

		pool.normalizePool();
		
		System.out.println("Trained finished!");
		System.out.println("Pool structcure {docClass = number of documents}: "+pool.getClassesWithDocumentCount());
		System.out.println("Total words in pool: "+pool.getNumberOfWords());
		
		fileProcessor = new RecursiveSimpleFileVisitor("txt");
		Files.walkFileTree(testBase, fileProcessor);
		
		for (Path file : ((RecursiveSimpleFileVisitor) fileProcessor).getFiles()) {
			String fileName = file.getFileName().toString();
			String dclass = fileName.substring(0, fileName.lastIndexOf(".")).replaceAll("[0-9]", "");
			
			fileContent = new String(Files.readAllBytes(file)); // readAllLines(file, Charset.defaultCharset());
			BagOfWords testBag = new BagOfWords();
			fileContent = fileContent.replaceAll("[\"]", "");
			testBag.addWords(Arrays.asList(fileContent.split("[^\\wäöüÄÖÜß]*")));
//			testBag.addWords(Arrays.asList(fileContent.split("[\\s,\\.;':]")));
//				probability = pool.probability(testBag, dclass);
			Map<String, Float> probAllClasses = pool.probability(file.getFileName().toString(), testBag);
			MapUtil.sortByValuesDescending(probAllClasses);
//			System.out.println(dclass + " = " + file.getFileName().toString() + ", probybility: " + probability + ". Probabilities: " + probAllClasses.entrySet());
//			System.out.println(dclass + " = " + file.getFileName().toString() + "; Probabilities: " + probAllClasses.entrySet());
			System.out.println(file.getFileName().toString() + " = Probabilities: " + probAllClasses.entrySet());
		}
		
		
		
	}

	@Test
	public void mathWithFloats() {
		Float prod = new Float(1);
		Float r = new Float(2);
		prod *= r;
		assertFalse(prod.isInfinite());
		System.out.println(prod);
	}
	
	
	@Test
	public void evaluateFloatInfinity() {
		System.out.println(1.0f / 0.0f);
		System.out.println(new Float("Infinity"));
		System.out.println(Float.parseFloat("Infinity"));
		System.out.println(new Float(0.12242519153117467336066203000015));
		System.out.println(new Float(0.08145700457195284754830961450242));
		System.out.println(new Float(-1f));
	}
	
	
	@Test
	public void mathWithFloatsTwo() {
		Float test = new Float(0);
		System.out.println("Test: " + test);
		test += 1;
		System.out.println("Test: " + test);
		test += 0.2f;
		System.out.println("Test: " + test);
		test *= 1;
		System.out.println("Test: " + test);
		test *= new Float(0.5);
		System.out.println("Test: " + test);
	}
	
}