package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.CardInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
    private final SelenideElement debitButton = $("[class='button button_size_m button_theme_alfa-on-white']");
    private final SelenideElement creditButton = $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']");


    public DebitPage chooseDebitPage() {
        debitButton.click();
        return new DebitPage();
    }

    public CreditPage chooseCreditPage() {
        creditButton.click();
        return new CreditPage();
    }
}