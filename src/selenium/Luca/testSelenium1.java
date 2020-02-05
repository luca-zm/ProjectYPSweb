package selenium.Luca;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class testSelenium1 {
	public static void main(String []args) {
		
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/webzeco/index.jsp");
		//email
		driver.findElement(By.xpath("//*[@id=\"usr\"]")).sendKeys("ttt");
		//password
		driver.findElement(By.xpath("//*[@id=\"psw\"]")).sendKeys("ttt");
		//submit email and password
		driver.findElement(By.xpath("/html/body/section/div/div/div[1]/div/form/button")).click();
		
		//click on userprofile navbar
		driver.findElement(By.xpath("/html/body/nav/div/form/ul/li[4]/a")).click();
		
		//check name on userprofile --> ttt
		WebElement TxtBoxContent = driver.findElement(By.xpath("/html/body/section[1]/div/div/div[1]/div/div/p[1]"));

     	System.out.println(TxtBoxContent.getText());
		driver.close();
	}
}
