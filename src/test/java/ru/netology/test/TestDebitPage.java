package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlUtils;
import ru.netology.page.DebitPage;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDebitPage {

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

    // Оплата по карте: успешная операция
    @Test
    @DisplayName("Card payment: successful operation")
    void cardPaymentSuccessfulOperation() {
        MainPage mainPage = new MainPage();
        DebitPage debitPage = mainPage.chooseDebitPage();
        debitPage.fillForm(DataHelper.getApprovedCardInfo());
        debitPage.checkSuccessNotification();
        assertEquals("APPROVED", SqlUtils.getStatusDebitPurchase());
        assertEquals(1, SqlUtils.getRowsDebitPurchase());
    }

    // Оплата по карте: неуспешная операция
    @Test
    @DisplayName("Card payment: unsuccessful operation")
    void cardPaymentUnsuccessfulOperation() {
        MainPage mainPage = new MainPage();
        DebitPage debitPage = mainPage.chooseDebitPage();
        debitPage.fillForm(DataHelper.getDeclinedCardInfo());
        debitPage.checkFailedNotification();
        assertEquals("DECLINED", SqlUtils.getStatusDebitPurchase());
        assertEquals(1, SqlUtils.getRowsDebitPurchase());
    }

    // Оплата по карте: пустые поля для заполнения
    @Test
    @DisplayName("Payment by card: empty fields to fill")
    void paymentCardEmptyFields() {
        MainPage mainPage = new MainPage();
        DebitPage debitPage = mainPage.chooseDebitPage();
        debitPage.fillForm(DataHelper.getAllEmptyFields());
        debitPage.checkEmptyForm();
        assertEquals(0, SqlUtils.getRowsDebitPurchase());
    }

    // Оплата по карте: поле номер карты заполнено не полностью
    @Test
    @DisplayName("Payment by card: the card number field is not completely filled")
    void paymentCardNumberNotCompletelyFilled() {
        MainPage mainPage = new MainPage();
        DebitPage debitPage = mainPage.chooseDebitPage();
        debitPage.fillForm(DataHelper.getShortCardNumber());
        debitPage.checkCardNumberFail();
        assertEquals(0, SqlUtils.getRowsDebitPurchase());
    }

    // Оплата по карте: проверка несуществующей карты
    @Test
    @DisplayName("Card payment: check for a non-existent card")
    void cardPaymentCheckNonExistentCard() {
        MainPage mainPage = new MainPage();
        DebitPage debitPage = mainPage.chooseDebitPage();
        debitPage.fillForm(DataHelper.getInvalidNumber());
        debitPage.checkFailedNotification();
        assertEquals(0, SqlUtils.getRowsCreditPurchase());
    }

    // Оплата по карте: поле месяц (месяц меньше текущего)
    @Test
    @DisplayName("Payment by card: month field (a month less than the current one)")
    void paymentCardMonthField() {
        MainPage mainPage = new MainPage();
        DebitPage debitPage = mainPage.chooseDebitPage();
        debitPage.fillForm(DataHelper.getInvalidMonth());
        debitPage.checkInValMonthFail();
        assertEquals(0, SqlUtils.getRowsDebitPurchase());
    }

    // Оплата по карте: поле год (год меньше текущего)
    @Test
    @DisplayName("Payment by card: year field (a year less than the current one)")
    void paymentCardYearField() {
        MainPage mainPage = new MainPage();
        DebitPage debitPage = mainPage.chooseDebitPage();
        debitPage.fillForm(DataHelper.getYearLessThanCurrent());
        debitPage.checkYearFail();
        assertEquals(0, SqlUtils.getRowsDebitPurchase());
    }

    // Оплата по карте: поле CVC/CVV заполнено не полностью
    @Test
    @DisplayName("Payment by card: field CVC / CVV is not completely filled")
    void paymentCardFieldCvcCvv() {
        MainPage mainPage = new MainPage();
        DebitPage debitPage = mainPage.chooseDebitPage();
        debitPage.fillForm(DataHelper.getTwoDigitCvcCvv());
        debitPage.checkCvcCvvFail();
        assertEquals(0, SqlUtils.getRowsDebitPurchase());
    }

    // Оплата по карте: поле месяц заполнено нулевым значением
    @Test
    @DisplayName("Payment by card: field month is filled with zero")
    void paymentCardFieldMonthFilledWithZero() {
        MainPage mainPage = new MainPage();
        DebitPage debitPage = mainPage.chooseDebitPage();
        debitPage.fillForm(DataHelper.getZeroMonth());
        debitPage.checkMonthFail();
        assertEquals(0, SqlUtils.getRowsDebitPurchase());
    }

    // Оплата по карте: поле владельца заполнено символами
    @Test
    @DisplayName("Payment by card: the owner field is filled with characters")
    void paymentCardOwnerFieldFilledWithCharacters() {
        MainPage mainPage = new MainPage();
        DebitPage debitPage = mainPage.chooseDebitPage();
        debitPage.fillForm(DataHelper.getSymbolsCardOwner());
        debitPage.checkOwnerFail();
        assertEquals(0, SqlUtils.getRowsDebitPurchase());
    }
}
