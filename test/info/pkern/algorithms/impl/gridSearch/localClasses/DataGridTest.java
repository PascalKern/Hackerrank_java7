package info.pkern.algorithms.impl.gridSearch.localClasses;

import static org.junit.Assert.*;
import info.pkern.LoggerConfiguration;
import info.pkern.TestdataHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.junit.BeforeClass;
import org.junit.Test;

public class DataGridTest {
	
	private String gridData10x10 =
			  "10 10\n"
			+ "7283455864\n"
			+ "6731158619\n"
			+ "8988242643\n"
			+ "3830589324\n"
			+ "2229505813\n"
			+ "5633845374\n"
			+ "6473530293\n"
			+ "7053106601\n"
			+ "0834282956\n"
			+ "4607924137\n";
	
	private String patternData3x4 =
			  "3\t4\n"
			+ "9505\n"
			+ "3845\n"
			+ "3530";
	
	private String gridData15x15 =
			  "15 15\n"
			+ "400453592126560\n"
			+ "114213133098692\n"
			+ "474386082879648\n"
			+ "522356951189169\n"
			+ "887109450487496\n"
			+ "252802633388782\n"
			+ "502771484966748\n"
			+ "075975207693780\n"
			+ "511799789562806\n"
			+ "404007454272504\n"
			+ "549043809916080\n"
			+ "962410809534811\n"
			+ "445893523733475\n"
			+ "768705303214174\n"
			+ "650629270887160\n";
	
	private String patternData2x2 =
			  "2\t2\n"
			+ "99\n"
			+ "99";
	
	
	@Test
	public void testGridContains() {
		InputStream in = new ByteArrayInputStream(("2\n" + gridData10x10 + patternData3x4 + "\n" + gridData15x15 + patternData2x2).getBytes());
		TestdataHandler<Testdata> inputProcessor = new TestdataHandler<Testdata>(Testdata.class, in);
		System.out.println("Test data grid 1:");
		System.out.println("Contained: " + inputProcessor.getTestdata(0).getGrid().contains(inputProcessor.getTestdata(0).getPattern()));
		System.out.println("Test data grid 2:");
		System.out.println("Contained: " + inputProcessor.getTestdata(1).getGrid().contains(inputProcessor.getTestdata(1).getPattern()));
		
	}
	

	/*
	 * Not nice because all tests after one failing will not be run!
	@Test
	public void testGridContainsWithDataFromFile() throws IOException {
		Path file = Paths.get("test", getClass().getPackage().getName().replace(".", "/"), TestdataHandler.TESTDATA_FILE_NAME);
		
		assertTrue("The file with the testdata does not exist! [file: " + file.normalize().toAbsolutePath() +"]",
				Files.exists(file, LinkOption.NOFOLLOW_LINKS));
		
		TestdataHandler<Testdata> inputHandler = new TestdataHandler<>(Testdata.class, file);
		
		String exptected;
		String actual;
		for (Testdata testdata : inputHandler.getAllTestdata()) {
			exptected = testdata.getExpectedString();
			actual = (testdata.getGrid().contains(testdata.getPattern()))?"YES":"NO";
			assertEquals("Test #" + testdata.getTestNr(), actual, exptected);
		}
	}
	*/
	
	private static TestdataHandler<Testdata> inputHandler;
	private Testdata testdata;
	
	@BeforeClass
	public static void init() throws IOException {
		LoggerConfiguration.enableSingleLineFineConsoleLogging();
		
		Path file = Paths.get("test", DataGridTest.class.getPackage().getName().replace(".", "/"), TestdataHandler.TESTDATA_FILE_NAME);
		
		assertTrue("The file with the testdata does not exist! [file: " + file.normalize().toAbsolutePath() +"]",
				Files.exists(file, LinkOption.NOFOLLOW_LINKS));
		
		inputHandler = new TestdataHandler<>(Testdata.class, file);
	}
	
	@Test
	public void testOne() {
		testdata = inputHandler.getAllTestdata().get(0);
		String exptected  = testdata.getExpectedString();
		String actual = evaluateResultString(testdata.getGrid().contains(testdata.getPattern()));
		assertEquals("Test #" + testdata.getTestNr(), actual, exptected);
	}
	@Test
	public void testTwo() {
		testdata = inputHandler.getAllTestdata().get(1);
		String exptected  = testdata.getExpectedString();
		String actual = evaluateResultString(testdata.getGrid().contains(testdata.getPattern()));
		assertEquals("Test #" + testdata.getTestNr(), actual, exptected);
	}
	@Test
	public void testThree() {
		testdata = inputHandler.getAllTestdata().get(2);
		String exptected  = testdata.getExpectedString();
		String actual = evaluateResultString(testdata.getGrid().contains(testdata.getPattern()));
		assertNotEquals("Test #" + testdata.getTestNr(), actual, exptected);
	}
	
	
	private String evaluateResultString(boolean result) {
		if (result) {
			return "YES";
		} else {
			return "NO";
		}
	}
}
