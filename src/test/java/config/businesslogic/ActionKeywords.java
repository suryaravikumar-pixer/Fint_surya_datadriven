//package config.businesslogic;
//
//import java.util.concurrent.TimeUnit;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.Assert;
//import org.testng.Reporter;
//
//public class ActionKeywords {
//	public static WebDriver driver;
//
//	static String sPath;
//
//	public static void openBrowser() {
//		System.setProperty("webdriver.chrome.driver",
//				System.getProperty("user.dir") + "\\src\\test\\resources\\chromedriver.exe");
//		driver = new ChromeDriver();
//	}
//
//	public static void navigate() {
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		driver.get("https://www.salesforce.com/in/");
//		driver.manage().window().maximize();
//		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
//	}
//
//	public static void loginPage() {
//		driver.findElement(By.id("username")).sendKeys("suryaravikumar5-t0re@force.com");
//		driver.findElement(By.id("password")).sendKeys(("Salesforce1997@"));
//	}
//
//	public static void navigateBack() {
//		driver.navigate().back();
//	}
//
//	public static void waitFor() throws Exception {
//		Thread.sleep(2000);
//	}
//
//	public static void closeBrowser() {
//		driver.quit();
//	}
//
//}
//
//public void accounts()throws Exception
//{
//File src=new File(Constants.Path_TestData+Constants.File_TestData);
//// Load the file.
//FileInputStream fis = new FileInputStream(src);
//// Load the workbook.
//workbook = new XSSFWorkbook(fis);
//// Load the sheet in which data is stored.
//sheet= workbook.getSheet("account");
//for(int row=1; row<=sheet.getLastRowNum(); row++)
////iterate over all the row to print the data present in each cell.
//{
//WebElement account = driver.findElement(By.xpath("//one-app-nav-bar-item-root[@data-id='Account'][@role='listitem']"));
//wait=new WebDriverWait(driver, 60);
//System.out.println(sheet.getLastRowNum());
//wait.until(ExpectedConditions.visibilityOf(account));
//account.click();
//WebElement newAccount = driver.findElement(By.xpath("//a[@title='New']//div"));
//System.out.println("Account New "+row);
//wait.until(ExpectedConditions.elementToBeClickable(newAccount));
//newAccount.click();
////search account name from excel file
//WebElement accountSearch = driver.findElement(By.xpath("(//input[@role='combobox'])[3]"));
//wait.until(ExpectedConditions.visibilityOf(accountSearch));
//cell = sheet.getRow(row).getCell(0);
//accountSearch.sendKeys(cell.getStringCellValue());
//System.out.println(accountSearch.getText());
//System.out.println("Account Searchname");
//WebElement accountName =driver.findElements(By.xpath("(//ul[@class='lookup__list visible'])[1]//li[1]"));
//wait.until(ExpectedConditions.visibilityOf(accountName));
//accountName.click();
//System.out.println("Account Name selected");
//System.out.println(accountName.getText());
////save the accountName
//WebElement save = driver.findElement(By.xpath("//button[@title='Save']"));
//wait.until(ExpectedConditions.elementToBeClickable(save)).click();
//System.out.println("Account created Success");
//FileOutputStream ActualResult=new FileOutputStream(src);
//// Message to be written in the excel sheet
//String message = "Pass";
//System.out.println("Account Created Pass");
//// Create cell where data needs to be written.
//sheet.getRow(row).createCell(2).setCellValue(message);
//// finally write content
//workbook.write(ActualResult); }
//}
