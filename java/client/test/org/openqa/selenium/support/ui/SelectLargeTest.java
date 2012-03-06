package org.openqa.selenium.support.ui;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.testing.Ignore;
import org.openqa.selenium.testing.JUnit4TestBase;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.testing.Ignore.Driver.ANDROID;


/**
 * Test for issue r759.
 */
public class SelectLargeTest extends JUnit4TestBase {
  @Test
  public void multipleSelectShouldBePossibleIfMulitpleAttributeEmpty() {
    driver.get(pages.formPage);

    WebElement selectElement = driver.findElement(By.name("select_empty_multiple"));

    Select selection = new Select(selectElement);
    selection.selectByIndex(1);
    selection.selectByIndex(2);

    List<WebElement> picked = selection.getAllSelectedOptions();
    assertEquals(2, picked.size());
    assertEquals("multi_2", picked.get(0).getAttribute("id"));
    assertEquals("multi_3", picked.get(1).getAttribute("id"));

    selection.deselectAll();
    assertEquals(0, selection.getAllSelectedOptions().size());
  }

  @Test
  @Ignore(value = ANDROID, reason = "Text not being trimmed properly.")
  public void selectByVisibleTextShouldNormalizeSpaces() {
    driver.get(pages.formPage);

    WebElement selectElement = driver.findElement(By.name("select_with_spaces"));
    Select selection = new Select(selectElement);

    String one = selection.getOptions().get(0).getText();
    selection.selectByVisibleText(one);
    assertEquals(one, selection.getFirstSelectedOption().getText());

    String two = selection.getOptions().get(1).getText();
    selection.selectByVisibleText(two);
    assertEquals(two, selection.getFirstSelectedOption().getText());

    String four = selection.getOptions().get(2).getText();
    System.out.println("four = " + four);
    selection.selectByVisibleText(four.trim());
    assertEquals(four, selection.getFirstSelectedOption().getText());

    String longOptionText = selection.getOptions().get(3).getText();

    System.out.println("longOptionText = " + longOptionText);

    selection.selectByVisibleText(longOptionText.trim());
    assertEquals(longOptionText, selection.getFirstSelectedOption().getText());
  }
}
