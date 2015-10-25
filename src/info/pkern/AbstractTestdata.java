package info.pkern;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.regex.Pattern;

/*
 * TODO Better use a interface? Use a factory method to create the instances and force the InputStream as parameter!
 */
public abstract class AbstractTestdata {
	
	private final Pattern numberOfExptectedResultLines = Pattern.compile("_expected#\\d+");
	private int testNr;
	private boolean containsExptectedResults = false;

	
	public abstract <T extends AbstractTestdata> T newInstance(Scanner scanner);
	public abstract Object getExpected();
	public abstract String getExpectedString();
	public abstract String getGridForSimulation();
	
	public boolean containsExptectedResults() {
		return containsExptectedResults;
	}

	public int getTestNr() {
		return testNr;
	}
	
	protected Integer followedByExptectedResultLines(Scanner scanner) {
		if (scanner.hasNext(numberOfExptectedResultLines)) {
			String expectedResults = scanner.next(numberOfExptectedResultLines);
			return Integer.valueOf(expectedResults.split("#")[1]);
		} else {
			return null;
		}
	}
	
	protected void setContainsExptectedResults() {
		containsExptectedResults = true;
	}
	protected void setTestNr(int testNr) {
		this.testNr = testNr;
	}
}
