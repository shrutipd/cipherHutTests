// Go to this URL and enable allow access to less secure apps https://myaccount.google.com/lesssecureapps?pli=1

package com.test.cipherhut;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.testng.Assert;
import org.testng.annotations.*;
import com.opencsv.CSVWriter;

import static org.testng.Assert.*;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;


public class LoginTest {
	
  private WebDriver driver;
  private static EmailUtils emailUtils;
  
  
  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
	  System.setProperty("webdriver.chrome.driver","C:/chromedriver_win32/chromedriver.exe");
	   driver = new ChromeDriver();
	   driver.manage().window().maximize(); 
       driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
  }

  @Test
  public void testLogin() throws Exception {

    driver.get("https://www.cbanx.com/login");
    driver.findElement(By.xpath("/html/body/app-root/div/app-login/section/div[2]/div/div/div/app-login-form/div/form/div/ul[2]/li/input")).sendKeys("1944");
    driver.findElement(By.xpath("/html/body/app-root/section/div/div/div[2]/button[2]")).click();
    driver.findElement(By.xpath("/html/body/app-root/div/app-login/section/div[2]/div/div/div/app-login-form/div/form/div/ul[3]/li/input")).sendKeys("Cipherhut@123");
   // driver.findElement(By.xpath("/html/body/app-root/div/app-login/section/div[2]/div/div/div/app-login-form/div/form/div/ul[5]/li/button")).sendKeys(Keys.ENTER);
    driver.findElement(By.className("login-btn")).click();
    
    Thread.sleep(10000);
    String otp=getOTP();
    
   // /html/body/app-root/div/app-login/section/div[2]/div/div/div/app-o-auth/div/form/div/ul[2]/li/input
    driver.findElement(By.xpath("/html/body/app-root/div/app-login/section/div[2]/div/div/div/app-o-auth/div/form/div/ul[2]/li/input")).sendKeys(otp);
    driver.findElement(By.xpath("/html/body/app-root/div/app-login/section/div[2]/div/div/div/app-o-auth/div/form/div/ul[4]/li/button")).click();
    Thread.sleep(10000);
    driver.findElement(By.xpath("//*[@id=\"exchange-top-bar\"]/div[1]/div[1]/app-currency-pairs/div[1]/ul/li"));
    
    /*UNCOMMENT BELOW BASED ON NEEDS to run all tests in a single test*/  
	   // clickCrytpoTag();
	   
	    //generateBalanceCSV("ActualBalance.csv");
	   // System.out.println(compareBalanceWithExpected("ExpectedBalance.csv","ActualBalance.csv"));
    
  
  }
  
  @Test (dependsOnMethods="testLogin")
  public void generateCSVPairs() throws IOException {
	  
	  clickCrytpoTag();
	  
  }
  
  
  @Test (dependsOnMethods="generateCSVPairs")
  public void checkBalance() throws InterruptedException, IOException {
	  
	  generateBalanceCSV("ActualBalance.csv");
	  Assert.assertEquals(true,compareBalanceWithExpected("ExpectedBalance.csv","ActualBalance.csv"));
	  
	  
  }
  
  @AfterClass
  public void close() {
	  driver.close();
  }
  
  
  
  private void clickCrytpoTag() throws IOException {
	  System.out.println("Click tag");
	  List<WebElement>currPair= driver.findElements(By.cssSelector("ul.marketPairBalance.panel.panel-default.justify-content-md-center"));
	  System.out.println("No of CryptoCurrPairs"+currPair.size());
	  for(WebElement curr:currPair) {
		  
		   curr.click();
		   WebElement pair= curr.findElement(By.cssSelector("li:nth-child(n)"));
		  // System.out.println(pair.getText());		   
		   generateCSV(pair.getText()+".csv");		   
	 }
	  
  }
  
  private void generateCSV(String fileName) throws IOException {
	  
	  List<WebElement>currency = driver.findElements(By.className("order-book-history-list"));
	  //List<String[]> data = new ArrayList<String[]>(); 
      FileWriter outputfile = new FileWriter(fileName); 
	  
      // create CSVWriter object filewriter object as parameter 
      CSVWriter writer = new CSVWriter(outputfile); 
	  for(WebElement cur : currency) {		  
		 String cryptoName=cur.findElement(By.className("coin-name")).getText();
		 String coinValue=cur.findElement(By.className("coin-value")).getText();
		 String coinProfit = cur.findElement(By.className("coin-profit")).getText();		 
		 String line = cryptoName+","+coinValue+","+coinProfit;
		 String []linedata= line.split(",");
		 //data.add(new String[] {"\r\n"});
		 writer.writeNext(linedata);
	  }
	  
	 writer.close();	  
  }
  
  public void generateBalanceCSV(String fileName) throws InterruptedException, IOException {
	  
	  driver.findElement(By.partialLinkText("Funds")).click();
	  Thread.sleep(20000);
	  List<WebElement>balances= driver.findElements(By.cssSelector("li.f-cb.fun"));
	  //System.out.println("Size of Balances "+balances.size());
	
	  FileWriter outputfile = new FileWriter(fileName);
	  CSVWriter writer = new CSVWriter(outputfile);
	  
	  for(WebElement balance :balances){
		 
		 String line = "";
		 List<WebElement> pairs= balance.findElements(By.tagName("div"));
		  //System.out.println("Size of row is "+pairs.size());
		  for(WebElement pair:pairs) {
			
			  //System.out.println(pair.getText());
			  line+=pair.getText()+",";
			 		  
	   }
		 String lineToWrite=line.substring(0, line.length()-1);
			 String[] linedata=lineToWrite.split(",");
			 writer.writeNext(linedata);
	  }
	  writer.close();
	  
	  
  }
  
  private boolean compareBalanceWithExpected(String expectedCSVFile,String actualBalanceFile) {
	  
	  Path p1 = Paths.get(expectedCSVFile);
      Path p2 = Paths.get(actualBalanceFile);

      try{
        List<String> listF1 = Files.readAllLines(p1);
        List<String> listF2 = Files.readAllLines(p2);
        return (listF2.containsAll(listF1));

        }catch(IOException ie) {
            ie.getMessage();
            return false;
        }
    
  }
  private String getOTP() {
	  
	   String OTP=null;
	    try {
	    	
	     
	     emailUtils=new EmailUtils(EmailUtils.EmailFolder.INBOX);
	      System.out.println(emailUtils.getNumberOfMessages());
	      Message message =emailUtils.getLatestMessage();
	      System.out.println(message.getSubject());
	      String messageContent=message.getContent().toString();
	      Pattern p = Pattern.compile("<span style=\"color: #06b8ef;font-weight: bolder;\">\\d+</span>");
	      Matcher n = p.matcher(messageContent);
	      if (n.find()) {
	           String pinMsg=n.group(0); 
	           int lastIndex=pinMsg.lastIndexOf("<");
	           int firstIndex=pinMsg.indexOf(">");
	           System.out.println(pinMsg.substring(firstIndex, lastIndex).substring(1));
	           
	           OTP=pinMsg.substring(firstIndex, lastIndex).substring(1);
	           System.out.println("OTP is "+OTP);
	      }
	    	      
	    } catch (Exception e) {
	      e.printStackTrace();
	      Assert.fail(e.getMessage());
	    }
	    return OTP;
	  }
  
}
