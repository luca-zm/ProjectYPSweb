package selenium.Davide;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class SeleniumTest1 {
	
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver",
				"Drivers/chromedriver");
			WebDriver driver=new ChromeDriver();
			driver.get("http://localhost:8080/webzeco/index.jsp");
			
			//Insert email
			driver.findElement(By.xpath("//*[@id=\"usr\"]")).sendKeys("mailtest");
			//Insert password
			driver.findElement(By.xpath("//*[@id=\"psw\"]")).sendKeys("passtest");
			//Submit
			driver.findElement(By.xpath("/html/body/section/div/div/div[1]/div/form/button")).click();
			//Navbar Button [Map]
			driver.findElement(By.xpath("/html/body/nav/div/form/ul/li[5]/a")).click();
			//Title of page #1
			WebElement title = driver.findElement(By.xpath("/html/body/section/div/div/div[1]/div/h3"));
			//Content of page [Info of collectionPoint] #2
			WebElement content = driver.findElement(By.xpath("/html/body/section/div/div/div[1]/div/table/tbody/tr[3]/td[2]"));
			
			System.out.println("Printing :" + title.getText()); //#1
			System.out.println("Printing :" + content.getText()); //#2
			
			
		
			
	}

}
