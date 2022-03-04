package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//import dataExcelUtility.Constants;
//import dataExcelUtility.ExcelUtility;

public class Datadriven {

	private static WebDriver driver;
	static XSSFWorkbook workbook;
	XSSFSheet sheet;
	XSSFCell cell;
	WebDriverWait wait;

	@BeforeTest
	public static void openBrowser() throws Exception {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);

		driver.get("https://www.salesforce.com/in/");
		
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.id("username")).sendKeys("suryaravikumar5-t0re@force.com");
		System.out.println("username Success");
		driver.findElement(By.id("password")).sendKeys("Salesforce1997@");
		System.out.println("password Success");
		driver.findElement(By.id("Login")).click();
		System.out.println("Login Success");
	}

	@Test(priority = 1)
	public void accounts() throws Exception {
		File src = new File("C:\\eclipse-workspace\\DataDrivenSalesforce\\src\\test\\resources\\Sheets\\testData.xlsx");
		// Load the file.
		FileInputStream fis = new FileInputStream(src);
		// Load the workbook.
		workbook = new XSSFWorkbook(fis);
		// Load the sheet in which data is stored.
		sheet = workbook.getSheetAt(1);
		wait = new WebDriverWait(driver, 60);
		WebElement account = driver.findElement(By.xpath("//one-app-nav-bar-item-root[@data-id='Account'][@role='listitem']"));
		account.click();
		// driver.findElement(By.xpath("//a[@title='New']//div")).click();
		for (int row = 1; row <= sheet.getLastRowNum(); row++) {
			// WebElement account =
			// driver.findElement(By.xpath("//one-app-nav-bar-item-root[@data-id='Account'][@role='listitem']"));
			// account.click();
			System.out.println(sheet.getLastRowNum());
			Thread.sleep(6000);
			driver.findElement(By.xpath("//a[@title='New']//parent::li")).click();
			cell = sheet.getRow(row).getCell(0);
			WebElement accountSearch = driver.findElement(By.xpath("(//input[@role='combobox'])[3]"));
			wait.until(ExpectedConditions.visibilityOf(accountSearch));
			accountSearch.clear();
			Thread.sleep(5000);
			accountSearch.sendKeys(cell.getStringCellValue());
			driver.findElement(By.xpath("(//li[@role='presentation']//a[@role='option'])[1]")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//button[@title='Save']")).click();
			Thread.sleep(3000);
			account.click();
			
			
			
			
		/*	wait.until(ExpectedConditions.visibilityOf(accountSearch));
			List<WebElement> accountNameSelect = driver
					.findElements(By.xpath("(//li[@role='presentation']//a[@role='option'])[1]"));
			System.out.println(accountNameSelect.size());
			System.out.println("Account search :" + row);
			for (WebElement option : accountNameSelect) {
				System.out.println("company name :" + option.getText());
				option.click();
				WebElement save = driver.findElement(By.xpath("//button[@title='Save']"));
				wait.until(ExpectedConditions.elementToBeClickable(save));
				Thread.sleep(4000);
				save.click();
				System.out.println("Account created Success");
				FileOutputStream ActualResult = new FileOutputStream(src);
				// Message to be written in the excel sheet
				String message = "Pass";
				System.out.println("Account Created Pass");
				// Create cell where data needs to be written.
				sheet.getRow(row).createCell(2).setCellValue(message);
				// finally write content
				workbook.write(ActualResult);
				Thread.sleep(4000);
				account.click();
			}*/
		}
	}
}
