/*
 * Copyright (c) 2023.
 */

package SteeringCompanyPortal;

import Utils.DateConvert;
import com.shaft.driver.SHAFT;
import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.chrono.IslamicChronology;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

@SuppressWarnings("ALL")
public class AddNewCreationalPeriod {
    ////////////////////////////Locators//////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    By LoginBtn = By.xpath("//*[@id='uzaNav']/div[2]/div[3]/a");
    By UserNameTxt = By.id("loginForm:j_username");
    By PasswordTxt = By.id("loginForm:j_password");
    By RememberMeChkBx = By.xpath("//div[@id='loginForm:rememberme']/div[2]/span");
    By SigninBtn = By.xpath("//*[@id='loginForm']//button[1]");
    By OTPTxt = By.xpath("//div[@id='loginForm:auth-panel']//input");
    By TICLogo = By.xpath("//body/div[1]/div[1]/div[1]");
    By SwithPanelBtn = By.xpath("//body/div[1]/div[1]/div[1]/a[2]");
    By SayyerMenu = By.xpath("//*[@class='layout-menu']/li[5]/a/span[1]");
    By SayyerSubMenu = By.xpath("//*[@class='layout-menu']/li[5]/ul[@role='menu']/li[1]/a/span[1]");
    By PeriodProgramSetting = By.xpath("//ul/li[5]/ul/li[1]/ul/li[8]//span[1]");
    By OTACommissionSetting = By.xpath("//ul/li[5]/ul/li[1]/ul/li[9]//span[1]");
    //*[@id="primetable:0:j_idt834_button"]/span[2]
    By ActionsBtn = By.xpath("//*[@id='primetable_data']/tr[last()]/td[last()]//span[2]");
    By ActionsEditBtn = By.xpath("//div[20]/ul/li[2]/a/span");
    By AddOperationalPeriodBtn = By.xpath("//tr/th[last()]//button");
    By HeaderTable = By.xpath("//*[@id='period-creation-table_head']/tr");
    By OperationalPeriodNameTxt = By.id("iospDesc");
    By invStartDate = By.id("invStartDate");
    By Calender = By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='>'])[1]/following::select[1]");
    By SelectMonth = By.xpath("//body/div[7]/div/div[2]/div/div/select[1]");
    By invEndDate = By.id("invEndDate");
    By minValueRange_input = By.id("minValueRange_input");
    By Add_PeriodTemplateBtn = By.xpath("//div[2]/div[2]/div[2]/button");
    By RouteList_label = By.id("rmmId_label");
    By FirstRouteLabel = By.id("rmmId_1");
    By busType_label = By.id("busType_label");
    By busType_3 = By.id("busType_3");
    By category1 = By.xpath("//*[@id='category']/div[1]/div[1]/div/div[2]");
    By category2 = By.xpath("//*[@id='category']/div[1]/div[2]/div/div[2]");
    By category3 = By.xpath("//*[@id='category']/div[2]/div/div/div[2]");
    By minVehiclePrice = By.id("minVehiclePrice");
    By packMinValuePerc = By.id("packMinValuePerc");
    By maxValueRange = By.id("maxValueRange");
    By AddOperationalBtn = By.xpath("//form/div[2]/div[2]/button[1]");
    By AddTemplatesBtn = By.xpath("//div[3]/button[1]");
    By LastOperationPeriodRow = By.xpath("//*[@id='period-creation-table_data']/tr[last()-1]");
    ////////////////////////////////////////////////////////////////////////////////
    By LastOperationPeriodActionBtn = By.xpath("//*[@id='period-creation-table_data']/tr[last()-1]/td[last()-1]/span/button");
    By AddCreationalPeriodLastRow = By.xpath("//*[@class='ui-menu ui-menu-dynamic ui-widget ui-widget-content ui-corner-all ui-helper-clearfix ui-shadow'][last()]/ul/li[2]/a/span");
    By CreationalinvStartDate = By.id("invStartDate");
    By CreationalinvEndDate = By.id("invEndDate");
    //  By CreationalDayFrom = By.linkText("24");
    // By CreationalDayTo = By.linkText("25");
    By TimeFrom = By.id("timeFrom");
    By TimeTo = By.id("timeTo");
    By period_form_grid2 = By.xpath("//div[@id='period-form-grid_content']/div[2]/div[2]/span");
    By period_form_grid3 = By.xpath("//div[@id='period-form-grid_content']/div[3]/div");
    By CreationalminValueRange_input = By.id("minValueRange_input");
    By SelectTemplate1Btn = By.xpath("//table/tbody/tr[1]/td[last()]/button/span[1]");
    By SelectTemplate2Btn = By.xpath("//table/tbody/tr[2]/td[last()]/button/span[1]");
    By SelectTemplate3Btn = By.xpath("//table/tbody/tr[3]/td[last()]/button/span[1]");
    By AddCreationalBtn4 = By.xpath("//form/div[2]/div[2]/button[1]");
    By LastDateOperationalPeriod = By.xpath("//*[@id='period-creation-table_data']/tr[last()-1]/td[3]");
    ////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    LocalDate Today;
    DateConvert DateConverter = new DateConvert();
    DateTimeZone zone = DateTimeZone.forID("Asia/Riyadh");
    Chronology hijri = IslamicChronology.getInstance(zone);
    private SHAFT.GUI.WebDriver driver = new SHAFT.GUI.WebDriver();
    //  Actions actions = new Actions(driver.getDriver());
    private JavascriptExecutor js = (JavascriptExecutor) driver.getDriver();
    private WebDriverWait wait = new WebDriverWait(driver.getDriver(), Duration.ofSeconds(30));

    public void LogintoPortal() throws Exception {
        driver.element().click(LoginBtn);
        driver.element().waitToBeReady(UserNameTxt).type(UserNameTxt, "1199900000");
        driver.element().waitToBeReady(PasswordTxt).type(PasswordTxt, "123");
        driver.element().waitToBeReady(RememberMeChkBx).click(RememberMeChkBx);
        driver.element().waitToBeReady(SigninBtn).click(SigninBtn);
        driver.element().waitToBeReady(OTPTxt).type(OTPTxt, "1");
        driver.element().waitToBeReady(SigninBtn).click(SigninBtn);
    }

    public void GotoPeriodProgramSetting() throws Exception {
       // driver.element().waitToBeReady(TICLogo).clickAndHold(TICLogo);
        driver.element().clickAndHold(TICLogo);
        driver.element().click(SwithPanelBtn);
       // driver.element().waitToBeReady(SayyerMenu).click(SayyerMenu);
        driver.element().click(SayyerMenu);
     //   driver.element().scrollToElement(SayyerSubMenu).waitToBeReady(SayyerSubMenu).click(SayyerSubMenu);
        driver.element().scrollToElement(SayyerSubMenu).click(SayyerSubMenu);
      //  WebElement OTACommissionSettingBtn = driver.getDriver().findElement(OTACommissionSetting);
     //   js.executeScript("arguments[0].scrollIntoView();", OTACommissionSettingBtn);
       // WebElement PeriodProgramSettingBtn = driver.getDriver().findElement(PeriodProgramSetting);
       // PeriodProgramSettingBtn.click();
        driver.element().scrollToElement(PeriodProgramSetting).click(PeriodProgramSetting);

    }

    public void GotoSteeringPeriodProgramSetting() throws Exception {
      //  driver.element().waitToBeReady(ActionsBtn).click(ActionsBtn);
        driver.element().assertThat(ActionsBtn).exists();
        driver.element().click(ActionsBtn);
        //driver.element().waitToBeReady(ActionsEditBtn).click(ActionsEditBtn);
        driver.element().waitToBeReady(ActionsEditBtn);
        driver.element().click(ActionsEditBtn);

    }

    public void AddSteeringOperationalPeriodProgramSetting() throws Exception {
      //  driver.element().waitToBeReady(LastDateOperationalPeriod);
       // WebElement LastDateOperationalPeriodTxt = driver.getDriver().findElement(LastDateOperationalPeriod);
      //  js.executeScript("arguments[0].scrollIntoView();", LastDateOperationalPeriodTxt);
        driver.element().scrollToElement(LastDateOperationalPeriod);
       // driver.element().getText(LastDateOperationalPeriod);
        String LastDateOperationalPeriodString = driver.element().getText(LastDateOperationalPeriod).toString();
        String NewFromDateOperationPeriodSting = DateConverter.AddDayToHijriDate(LastDateOperationalPeriodString);
        String NewToDateOperationPeriodSting = DateConverter.AddDayToHijriDate(NewFromDateOperationPeriodSting);
        LocalDate NewFromDateOperationPeriod = DateConverter.HijriDate(NewFromDateOperationPeriodSting);
        LocalDate NewToDateOperationPeriod = DateConverter.HijriDate(NewToDateOperationPeriodSting);

        driver.element().waitToBeReady(AddOperationalPeriodBtn);
        WebElement AddOperationalPeriod = driver.getDriver().findElement(AddOperationalPeriodBtn);
        js.executeScript("arguments[0].scrollIntoView();", AddOperationalPeriod);
        driver.element().click(AddOperationalPeriodBtn);
        driver.element().type(OperationalPeriodNameTxt, "فترة تشغيل جديدة");
        driver.element().click(invStartDate);
        driver.element().click(Calender);
        driver.element().select(SelectMonth, NewFromDateOperationPeriod.toString("M/yyyy"));
        driver.element().click(By.linkText(NewFromDateOperationPeriod.toString("d")));
        driver.element().click(invEndDate);
        driver.element().click(Calender);
        driver.element().select(SelectMonth, NewToDateOperationPeriod.toString("M/yyyy"));
        driver.element().click(By.linkText(NewToDateOperationPeriod.toString("d")));
        driver.element().type(minValueRange_input, "10");
        driver.element().click(Add_PeriodTemplateBtn);
        driver.element().click(RouteList_label);
        driver.element().click(FirstRouteLabel);
        driver.element().click(busType_label);
        driver.element().click(busType_3);
      //  WebElement minVehiclePriceTxt = driver.getDriver().findElement(minVehiclePrice);
     //   js.executeScript("arguments[0].scrollIntoView();", minVehiclePriceTxt);
        driver.element().scrollToElement(minVehiclePrice);
      //  driver.getDriver().findElement(category1).click();
        driver.element().click(category1);
       // driver.getDriver().findElement(category2).click();
        driver.element().click(category2);
        // driver.getDriver().findElement(category3).click();
        driver.element().click(category3);
        driver.element().scrollToElement(minVehiclePrice);

      //  js.executeScript("arguments[0].scrollIntoView();", minVehiclePriceTxt);
        driver.element().type(packMinValuePerc, "9");
        driver.element().type(maxValueRange, "11");
        driver.element().type(minVehiclePrice, "1200");
      //  WebElement AddButton = driver.getDriver().findElement(AddOperationalBtn);
       // js.executeScript("arguments[0].scrollIntoView();", AddButton);
      //  driver.getDriver().findElement(AddTemplatesBtn).click();
        driver.element().scrollToElement(AddOperationalBtn);

        driver.element().click(AddTemplatesBtn);
        //  driver.element().click(AddOperationalBtn);  for test

        //driver.getDriver().findElement(AddOperationalBtn).click(); // for test

    }

    public void AddSteeringCreationalPeriodProgramSetting() throws Exception {
       // WebElement LastOperationPeriodRowTxt = driver.getDriver().findElement(LastOperationPeriodRow);
       // js.executeScript("arguments[0].scrollIntoView();", LastOperationPeriodRowTxt);
        driver.element().scrollToElement(LastOperationPeriodRow);
      //  driver.getDriver().findElement(LastOperationPeriodActionBtn).click();
       // driver.getDriver().findElement(AddCreationalPeriodLastRow).click();
        driver.element().click(LastOperationPeriodActionBtn);
        driver.element().click(AddCreationalPeriodLastRow);

        driver.element().click(CreationalinvStartDate);
        driver.element().click(Calender);
        driver.element().waitToBeReady(SelectMonth).select(SelectMonth, Today.toString("M/yyyy"));
        driver.element().click(By.linkText(Today.toString("d")));
        driver.element().click(CreationalinvEndDate);
        driver.element().click(Calender);
        driver.element().waitToBeReady(SelectMonth).select(SelectMonth, Today.plusDays(1).toString("M/yyyy"));
        driver.element().click(By.linkText(Today.plusDays(1).toString("d")));
        driver.element().type(TimeFrom, "00:00");
        driver.element().click(period_form_grid2);
        driver.element().type(TimeTo, "23:00");

       // WebElement period_form_grid3Btn33 = driver.getDriver().findElement(period_form_grid3);
       // js.executeScript("arguments[0].scrollIntoView();", period_form_grid3Btn33);
       // driver.element().scrollToElement(period_form_grid3);

      //  driver.getDriver().findElement(period_form_grid3).click();
     //   driver.element().click(period_form_grid3);

       // WebElement CreationalminValueRange_input1 = driver.getDriver().findElement(CreationalminValueRange_input);
       // js.executeScript("arguments[0].scrollIntoView();", CreationalminValueRange_input1);
        driver.element().scrollToElement(CreationalminValueRange_input);

       // CreationalminValueRange_input1.sendKeys("10");
        driver.element().type(CreationalminValueRange_input,"10");

     //   WebElement AddCreationalBtn44 = driver.getDriver().findElement(AddCreationalBtn4);
       // js.executeScript("arguments[0].scrollIntoView();", AddCreationalBtn44);
        driver.element().scrollToElement(AddCreationalBtn4);

        //driver.getDriver().findElement(SelectTemplate1Btn).click();
        driver.element().click(SelectTemplate1Btn);

       // wait.until(ExpectedConditions.stalenessOf(driver.getDriver().findElement(SelectTemplate2Btn)));
        //driver.getDriver().findElement(SelectTemplate2Btn).click();
        driver.element().waitToBeReady(SelectTemplate2Btn).click(SelectTemplate2Btn);
       // wait.until(ExpectedConditions.stalenessOf(driver.getDriver().findElement(SelectTemplate3Btn)));
       // driver.getDriver().findElement(SelectTemplate3Btn).click();
        driver.element().waitToBeReady(SelectTemplate3Btn).click(SelectTemplate3Btn);

       // js.executeScript("arguments[0].scrollIntoView();", AddCreationalBtn44);
        //   driver.getDriver().findElement(AddCreationalBtn4).click(); // for test
        driver.element().scrollToElement(AddCreationalBtn4);
        //driver.element().click(AddCreationalBtn4);  // for test

    }

    @Test
    public void TestAddNewCreationalPeriod() throws Exception {
        //  ConvertGregorianToHijri("2-2-2023");
        //AddDayToHijriDate("10/08/1444");
        driver.browser().navigateToURL("https://portal-demo.np.transporticonline.com/Naqaba/index.xhtml");
        LogintoPortal();
        ///////////////
        GotoPeriodProgramSetting();
        GotoSteeringPeriodProgramSetting();
        AddSteeringOperationalPeriodProgramSetting();
        AddSteeringCreationalPeriodProgramSetting();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        //   driver = new SHAFT.GUI.WebDriver();

        //js = (JavascriptExecutor) driver.getDriver();
        driver.browser().navigateToURL("https://portal-demo.np.transporticonline.com/Naqaba/index.xhtml");
        // wait = new WebDriverWait(driver.getDriver(), Duration.ofSeconds(30));
        //  actions = new Actions(driver.getDriver());
        Today = new LocalDate(hijri).plusDays(1);
        //   DateConverter=new DateConvert();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }

}
