package selenium.Stefano;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {
	
	public static void main(String []args) {
		
		System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/webzeco/index.jsp");
		//email
		driver.findElement(By.xpath("//*[@id=\"usr\"]")).sendKeys("stefano@gmail.com");
		//password
		driver.findElement(By.xpath("//*[@id=\"psw\"]")).sendKeys("cccc");
		//submit email and password
		driver.findElement(By.xpath("/html/body/section/div/div/div[1]/div/form/button")).click();
		
		//Add shampoo product to cart
		driver.findElement(By.xpath("/html/body/section[3]/div/div[2]/div[2]/div/div[1]/form/div[2]/input[1]")).click();
		
		driver.findElement(By.xpath("/html/body/nav/div/form/ul/li[7]/a")).click();
		
		//check total price
		WebElement TxtBoxContent = driver.findElement(By.xpath("/html/body/section[1]/div/div/div[1]/div/div[2]/h6/span"));

     	System.out.println(TxtBoxContent.getText());
		driver.close();
	}
}
