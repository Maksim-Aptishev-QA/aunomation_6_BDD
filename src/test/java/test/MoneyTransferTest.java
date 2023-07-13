package test;

import data.DataHelper;
import page.DashboardPage;
import page.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static data.DataHelper.getFirstCardNumber;
import static data.DataHelper.getSecondCardNumber;
import static page.DashboardPage.firstCardPushButton;
import static page.DashboardPage.secondCardPushButton;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MoneyTransferTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        var LoginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = LoginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var DashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void transferFromTheFirstCardToTheSecond() {
        int amount = 7000;
        var DashboardPage = new DashboardPage();
        var firstCardInitialAmount = DashboardPage.getFirstCardBalance();
        var secondCardInitialAmount = DashboardPage.getSecondCardBalance();
        var transactionPage = secondCardPushButton();
        transactionPage.transferMoney(amount, getFirstCardNumber());
        var firstCardEndBalance = firstCardInitialAmount - amount;
        var secondCardEndBalance = secondCardInitialAmount + amount;
        assertEquals(firstCardEndBalance, DashboardPage.getFirstCardBalance());
        assertEquals(secondCardEndBalance, DashboardPage.getSecondCardBalance());

    }

    @Test
    void transferFromTheSecondCardToTheFirst() {
        int amount = 7000;
        var DashboardPage = new DashboardPage();
        var firstCardInitialAmount = DashboardPage.getFirstCardBalance();
        var secondCardInitialAmount = DashboardPage.getSecondCardBalance();
        var transactionPage = firstCardPushButton();
        transactionPage.transferMoney(amount, getSecondCardNumber());
        var secondCardEndBalance = secondCardInitialAmount - amount;
        var firstCardEndBalance = firstCardInitialAmount + amount;
        assertEquals(firstCardEndBalance, DashboardPage.getFirstCardBalance());
        assertEquals(secondCardEndBalance, DashboardPage.getSecondCardBalance());

    }
    @Test
    void transferToTheSameCard() {
        int amount = 8000;
        var dashboardPage = new DashboardPage();
        var firstCardInitialAmount = dashboardPage.getFirstCardBalance();
        var secondCardInitialAmount = dashboardPage.getSecondCardBalance();
        var transactionPage = firstCardPushButton();
        transactionPage.transferMoney(amount, getFirstCardNumber());
        transactionPage.invalidCard();
    }

    @Test
        void transferMoreBalance() {
        int amount = 15000;
        var dashboardPage = new DashboardPage();
        var firstCardInitialAmount = dashboardPage.getFirstCardBalance();
        var secondCardInitialAmount = dashboardPage.getSecondCardBalance();
        var transactionPage = secondCardPushButton();
        transactionPage.transferMoney(amount, getFirstCardNumber());
        transactionPage.errorLimit();

    }


}