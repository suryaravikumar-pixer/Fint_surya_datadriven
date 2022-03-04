package tests;

import org.testng.annotations.Test;

import net.bytebuddy.asm.Advice.Enter;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DataDrivenTest {
	static WebDriver driver;
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	static XSSFCell cell;
	static WebDriverWait wait;

	@BeforeMethod
	public void initialization() throws Exception {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		driver.get("https://login.salesforce.com/");
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

		File src = new File("C:\\eclipse-workspace\\DataDrivenSalesforce\\src\\test\\resources\\Sheets\\testData.xlsx");
		FileInputStream fis = new FileInputStream(src);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheetAt(0);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {

			cell = sheet.getRow(i).getCell(0);

			driver.findElement(By.id("username")).clear();
			driver.findElement(By.id("username")).sendKeys(cell.getStringCellValue());
			// Import data for password.
			cell = sheet.getRow(i).getCell(1);

			driver.findElement(By.id("password")).clear();
			driver.findElement(By.id("password")).sendKeys(cell.getStringCellValue());
			// To click on Login button
			driver.findElement(By.name("Login")).click();
			// validate home page title

			// subscribe
			driver.findElement(By.xpath("//button[@title='Close']")).click();
		}
	}

	@Test(priority = 1)

	public void accountsPage() throws Exception {
		System.out.println("Account script started...");
		File src = new File("C:\\eclipse-workspace\\DataDrivenSalesforce\\src\\test\\resources\\Sheets\\testData.xlsx");
		FileInputStream fis = new FileInputStream(src);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheetAt(1);
		for (int row = 0; row <= sheet.getLastRowNum(); row++) {
//			driver.navigate().refresh();

//			wait = new WebDriverWait(driver, 20);
			WebElement accountsButton = driver.findElement(By.xpath("*//span[text()='Accounts']//parent::a/.."));
//			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(accountsButton));
//			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			accountsButton.click();

//			wait = new WebDriverWait(driver, 20);
			WebElement NewButton = driver.findElement(By.xpath("//div[text()='New']//parent::a/.."));
//			WebElement element1 = wait.until(ExpectedConditions.elementToBeClickable(NewButton));
//			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element1);
			NewButton.click();

//			driver.findElement(By.xpath("//div[text()='New']//parent::a/..")).click();
			cell = sheet.getRow(row).getCell(0);
			WebElement accountField = driver.findElement(By.xpath("(//input[@role='combobox'])[3]"));
			wait = new WebDriverWait(driver, 60);
			WebElement element2 = wait.until(ExpectedConditions.elementToBeClickable(accountField));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element2);
			accountField.clear();
			accountField.sendKeys(cell.getStringCellValue());

			List<WebElement> optionsToSelect = driver
					.findElements(By.xpath("(//div[@role='listbox']//div[@class='listContent']//ul//li/a)"));

			System.out.println(optionsToSelect.size());

			for (WebElement option : optionsToSelect) {
//				System.out.println(option);
				System.out.println("company name  " + option.getText());
				if (option.getText().equals(cell.getStringCellValue())) {

//					System.out.println("Trying to select: " + option.getText());
					option.click();
					System.out.println("Account name selected form suggestion");

					break;
				}
				// validate success green popup

			}
			
			driver.findElement(By.xpath("//button[@title='Save']")).click();
			System.out.println("Account created Successfully");
			
		}

	}

//	@Test(priority = 1)
	public void contactPage() throws Exception {
		System.out.println("Contact page script starting...");
		File src = new File("C:\\eclipse-workspace\\DataDrivenSalesforce\\src\\test\\resources\\Sheets\\testData.xlsx");
		FileInputStream fis = new FileInputStream(src);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheetAt(2);
		for (int row = 0; row <= sheet.getLastRowNum(); row++) {
			cell = sheet.getRow(row).getCell(0);

			Thread.sleep(4000);
			driver.findElement(By.xpath("//*[@data-id='Contact']")).click();
			Thread.sleep(4000);
			driver.findElement(By.xpath("(//div[text()='New'])")).click();

			Thread.sleep(4000);
			WebElement lastNameInContact = driver.findElement(By.xpath("//input[@placeholder='Last Name']"));
			wait = new WebDriverWait(driver, 30);
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(lastNameInContact));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			lastNameInContact.clear();
			Thread.sleep(4000);
			lastNameInContact.sendKeys(cell.getStringCellValue());

			cell = sheet.getRow(row).getCell(1);
			WebElement accountField = driver.findElement(By.xpath("(//input[@role='combobox'])[3]"));
			accountField.clear();
			accountField.sendKeys(cell.getStringCellValue());
			Thread.sleep(2000);

			WebElement accountList = driver.findElement(By.xpath("//li[@class='slds-listbox__item']"));
			accountList.click();
			System.out.println("Contact name selected form suggestion");
			driver.findElement(By.xpath("//button[@name='SaveEdit']//parent::lightning-button/.")).click();
			System.out.println("Contact created finally");
			Thread.sleep(4000);
		}
		Thread.sleep(4000);

	}

//	@Test
	public static void notesPage() throws Exception {
		System.out.println("Contact page script starting...");
		File src = new File("C:\\eclipse-workspace\\DataDrivenSalesforce\\src\\test\\resources\\Sheets\\testData.xlsx");
		FileInputStream fis = new FileInputStream(src);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheetAt(3);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		for (int row = 0; row <= sheet.getLastRowNum(); row++) {

			driver.navigate().refresh();
			WebElement notesButton = driver.findElement(By.xpath("//span[text()='Notes']//parent::a/.."));
			wait = new WebDriverWait(driver, 150);
			js.executeScript("arguments[0].scrollIntoView();", notesButton);
			wait.until(ExpectedConditions.elementToBeClickable(notesButton));
			notesButton.click();

			WebElement newButton = driver.findElement(By.xpath("//div[@title='New']"));
			wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.elementToBeClickable(newButton));
			newButton.click();

			cell = sheet.getRow(row).getCell(0);
			WebElement titleField = driver
					.findElement(By.xpath("//input[@class='inputText notesTitle flexInput input']"));
			wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.elementToBeClickable(titleField));
//			titleField.click();
			titleField.clear();
			titleField.sendKeys(cell.getStringCellValue());

			cell = sheet.getRow(row).getCell(1);
			WebElement textField = driver.findElement(By.xpath("//div[@data-placeholder='Enter a note...']"));
			textField.clear();
			textField.sendKeys(cell.getStringCellValue());

			/*
			 * WebElement addButton = driver.findElement(By.
			 * xpath("//button[@class='slds-button slds-button--neutral relate-button uiButton']"
			 * )); // WebElement addButton =
			 * driver.findElement(By.xpath("//span[text()='Add to Records']//parent::button"
			 * )); //This will scroll the page till the element is found
			 * js.executeScript("arguments[0].scrollIntoView();", addButton);
			 * js.executeScript("window.scrollTo(0, document.body.scrollHeight)"); wait =
			 * new WebDriverWait(driver, 150); WebElement element=
			 * wait.until(ExpectedConditions.elementToBeClickable(addButton));
			 * ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
			 * element); // addButton.click();
			 * 
			 * // Thread.sleep(2000);
			 * 
			 * WebElement doneButton =
			 * driver.findElement(By.xpath("(//span[text()='Done'])[2]//parent::button/."));
			 * wait = new WebDriverWait(driver, 200); WebElement element1 =
			 * wait.until(ExpectedConditions.visibilityOf(doneButton));
			 * ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
			 * element1); // doneButton.click(); // Thread.sleep(5000);
			 * 
			 */
		}
	}

//	@Test
	public static void opportunityPage() throws Exception {
		System.out.println("opportunityPage script starting...");
		File src = new File("C:\\eclipse-workspace\\DataDrivenSalesforce\\src\\test\\resources\\Sheets\\testData.xlsx");
		FileInputStream fis = new FileInputStream(src);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheetAt(4);

		// for closing subscribe popup
		driver.findElement(By.xpath("//button[@title='Close']")).click();

		for (int row = 0; row <= sheet.getLastRowNum(); row++) {

			driver.navigate().refresh();
//			WebElement opportunittyButton = driver.findElement(By.xpath("//*[@data-id='Opportunity']"));
//			JavascriptExecutor jse = (JavascriptExecutor)driver;
//			jse.executeScript("arguments[0].click()", opportunittyButton);

			JavascriptExecutor js = (JavascriptExecutor) driver;

			WebElement opportunittyButton = driver.findElement(By.xpath("//*[@data-id='Opportunity']"));
			wait = new WebDriverWait(driver, 200);
			js.executeScript("arguments[0].click();", opportunittyButton);
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-id='Opportunity']")));
//			Thread.sleep(4000);
			opportunittyButton.click();
			System.out.println("opportunity button clicked");

			WebElement newButton = driver.findElement(By.xpath("//div[text()='New']//parent::a/.."));
			wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.elementToBeClickable(newButton));
			newButton.click();
			System.out.println("new button clicked");

			cell = sheet.getRow(row).getCell(0);
			WebElement opportunityNameField = driver.findElement(By.xpath("//input[@name='Name']"));
			wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.elementToBeClickable(opportunityNameField));
			opportunityNameField.click();
			opportunityNameField.sendKeys(cell.getStringCellValue());
			System.out.println("opportunity name entered");
			opportunityNameField.sendKeys(Keys.TAB);

			cell = sheet.getRow(row).getCell(1);
			WebElement accountNameField = driver.findElement(By.xpath("(//input[@role='combobox'])[3]"));
			wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.elementToBeClickable(accountNameField));
			accountNameField.sendKeys(cell.getStringCellValue());
			System.out.println("account name filed");

			WebElement selectedAccountName = driver
					.findElement(By.xpath("//ul[@role='group']/li[2]/lightning-base-combobox-item"));
			wait = new WebDriverWait(driver, 600);
			wait.until(ExpectedConditions.elementToBeClickable(selectedAccountName));
			selectedAccountName.click();
			System.out.println("Account name selected from the list");

			cell = sheet.getRow(row).getCell(2);
			WebElement closeDateField = driver.findElement(By.xpath("//input[@name='CloseDate']"));
			wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.visibilityOf(closeDateField));
			closeDateField.sendKeys(cell.getStringCellValue());
			System.out.println("Date entered in the filed");

			WebElement stageField = driver
					.findElement(By.xpath("(//span[@class='slds-truncate'][normalize-space()='--None--'])[2]"));
			wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.elementToBeClickable(stageField));
			stageField.click();

			WebElement selectStageFromList = driver
					.findElement(By.xpath("(//div[@role='listbox'])[4]/lightning-base-combobox-item[3]"));
			wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.elementToBeClickable(selectStageFromList));
			selectStageFromList.click();
			System.out.println("Stage selected");

			WebElement saveButton = driver.findElement(By.xpath("//button[@name='SaveEdit']"));
			wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.elementToBeClickable(saveButton));
			saveButton.click();

			driver.findElement(By.xpath("//button[@title='Close']//child::lightning-primitive-icon")).click();
			System.out.println(" toast message closed");

		}

	}

//	@Test

	public void taskPage() throws Exception {
		System.out.println("task script started...");
		File src = new File("C:\\eclipse-workspace\\DataDrivenSalesforce\\src\\test\\resources\\Sheets\\testData.xlsx");
		FileInputStream fis = new FileInputStream(src);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheetAt(5);

		for (int row = 0; row <= sheet.getLastRowNum(); row++) {
			driver.navigate().refresh();

			WebElement tasksButton = driver.findElement(By.xpath("(//span[text()='Tasks'])[1]//parent::a/.."));
			tasksButton.click();
			WebElement tasksDropDownButton = driver
					.findElement(By.xpath("//a[@title='Show one more action']//parent::div/."));
			tasksDropDownButton.click();
			WebElement newTaskButton = driver.findElement(By.xpath("//div[@role='menu']/ul/li/a"));
			newTaskButton.click();

//			wait = new WebDriverWait(driver, 100);
//			WebElement taskbutton = driver.findElement(By.xpath("//*[@data-id='Task']//a[@role='button']"));
////			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(taskbutton));
//			((JavascriptExecutor) driver).executeScript("arguments[0].click();", taskbutton);
////			wait.until(ExpectedConditions.elementToBeClickable(taskbutton)).click();

			// for drop down
//			wait = new WebDriverWait(driver, 120);
////			WebElement newTaskButton  = driver.findElement(By.xpath("(//span[text()='New Task']/../..)[1]"));
//			WebElement newTaskButton  = driver.findElement(By.xpath("(//span[text()='New Task']//parent::span/..)[1]"));
//			wait.until(ExpectedConditions.elementToBeClickable(newTaskButton)).click();

//			// New Task
//			wait = new WebDriverWait(driver, 200);
//			WebElement	newButton =driver.findElement(By.xpath("//a[@title='New Task']"));

			// Subject
			cell = sheet.getRow(row).getCell(0);
			WebElement subjectField = driver.findElement(By.xpath("(//input[@role='combobox'])[3]"));
			wait = new WebDriverWait(driver, 60);
			subjectField.clear();
			subjectField.sendKeys(cell.getStringCellValue());
			subjectField.sendKeys(Keys.ENTER);

			// save button
			driver.findElement(By.xpath("//button[@title='Save']")).click();
//			driver.findElement(By.xpath("(//span[text()='Save'])[2]")).click();
			System.out.println("Task created Successfully");

			driver.findElement(By.xpath("//button[@title='Close']//child::lightning-primitive-icon")).click();

//			driver.navigate().refresh();
		}

	}

}
