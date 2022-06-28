package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlUtils;
import ru.netology.page.CreditPage;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCreditPage {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        SqlUtils.clearTables();
        open("http://localhost:8080");
    }

    // Кредит по данным карты: успешная операция
    @Test
    @DisplayName("Credit according to the card: successful operation")
    void creditAccordingCardSuccessfulOperation() {
        MainPage mainPage = new MainPage();
        CreditPage creditPage = mainPage.chooseCreditPage();
        creditPage.fillForm(DataHelper.getApprovedCardInfo());
        creditPage.checkSuccessNotification();
        assertEquals("APPROVED", SqlUtils.getStatusCreditPurchase());
        assertEquals(1, SqlUtils.getRowsCreditPurchase());
    }

    // Кредит по данным карты: неуспешная операция
    @Test
    @DisplayName("Credit according to the card: unsuccessful operation")
    void creditAccordingCardUnsuccessfulOperation() {
        MainPage mainPage = new MainPage();
        CreditPage creditPage = mainPage.chooseCreditPage();
        creditPage.fillForm(DataHelper.getDeclinedCardInfo());
        creditPage.checkFailNotification();
        assertEquals("DECLINE", SqlUtils.getStatusCreditPurchase());
        assertEquals(1, SqlUtils.getRowsCreditPurchase());
    }

    // Кредит по данным карты: пустые поля для заполнения
    @Test
    @DisplayName("Credit according to the card: empty fields to fill")
    void creditAccordingCardEmptyFields() {
        MainPage mainPage = new MainPage();
        CreditPage creditPage = mainPage.chooseCreditPage();
        creditPage.fillForm(DataHelper.getAllEmptyFields());
        creditPage.checkEmptyForm();
        assertEquals(0, SqlUtils.getRowsCreditPurchase());
    }

    // Кредит по данным карты: поле номер карты заполнено не полностью
    @Test
    @DisplayName("Credit according to the card: the card number field is not completely filled")
    void creditAccordingCardNumberNotCompletelyFilled() {
        MainPage mainPage = new MainPage();
        CreditPage creditPage = mainPage.chooseCreditPage();
        creditPage.fillForm(DataHelper.getShortCardNumber());
        creditPage.checkCardFail();
        assertEquals(0, SqlUtils.getRowsCreditPurchase());
    }

    // Кредит по данным карты: проверка несуществующей карты
    @Test
    @DisplayName("Credit according to the card: checking a non-existent card")
    void creditAccordingCardCheckNonExistentCard() {
        MainPage mainPage = new MainPage();
        CreditPage creditPage = mainPage.chooseCreditPage();
        creditPage.fillForm(DataHelper.getInvalidNumber());
        creditPage.checkFailNotification();
        assertEquals(0, SqlUtils.getRowsCreditPurchase());
    }

    // Кредит по данным карты: поле месяц (месяц меньше текущего)
    @Test
    @DisplayName("Credit according to the card: month field (month less than the current one)")
    void creditAccordingCardMonthField() {
        MainPage mainPage = new MainPage();
        CreditPage creditPage = mainPage.chooseCreditPage();
        creditPage.fillForm(DataHelper.getInvalidMonth());
        creditPage.checkInValMonthFail();
        assertEquals(0, SqlUtils.getRowsCreditPurchase());
    }

    // Кредит по данным карты: поле год (год меньше текущего)
    @Test
    @DisplayName("Credit according to the card: year field (a year less than the current one)")
    void creditAccordingCardYearField() {
        MainPage mainPage = new MainPage();
        CreditPage creditPage = mainPage.chooseCreditPage();
        creditPage.fillForm(DataHelper.getYearLessThanCurrent());
        creditPage.checkYearFail();
        assertEquals(0, SqlUtils.getRowsCreditPurchase());
    }

    // Кредит по данным карты: поле CVC/CVV заполнено не полностью
    @Test
    @DisplayName("Credit according to the card: the CVC / CVV field is not completely filled out")
    void creditAccordingCardFieldCvcCvv() {
        MainPage mainPage = new MainPage();
        CreditPage creditPage = mainPage.chooseCreditPage();
        creditPage.fillForm(DataHelper.getTwoDigitCvcCvv());
        creditPage.checkCvcCvvFail();
        assertEquals(0, SqlUtils.getRowsCreditPurchase());
    }

    // Кредит по данным карты: поле месяц заполнено нулевым значением
    @Test
    @DisplayName("Credit according to the card field month is filled with zero")
    void creditAccordingCardFieldMonthFilledWithZero() {
        MainPage mainPage = new MainPage();
        CreditPage creditPage = mainPage.chooseCreditPage();
        creditPage.fillForm(DataHelper.getZeroMonth());
        creditPage.checkMonthFail();
        assertEquals(0, SqlUtils.getRowsCreditPurchase());
    }

    // Кредит по данным карты: поле владельца заполнено символами
    @Test
    @DisplayName("Credit according to the card the owner field is filled with characters")
    void creditAccordingCardOwnerFieldFilledWithCharacters() {
        MainPage mainPage = new MainPage();
        CreditPage creditPage = mainPage.chooseCreditPage();
        creditPage.fillForm(DataHelper.getSymbolsCardOwner());
        creditPage.checkOwnerFail();
        assertEquals(0, SqlUtils.getRowsCreditPurchase());
    }
}
