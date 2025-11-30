package example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UnitTestS02 {
    @Test
    public void runTest() {
        testAccountMethods();
        testBankTransfer();
        testBankTransferLowBalance();
        testWithBlockedAccount();
        testGetSumAllAccounts();
    }

    @Test
    public void testAccountMethods() {
        Account accountA = new Account();
        accountA.setMoney(200_000);
        accountA.setAccNumber("AccNumber");
        accountA.changeIsBlocked(true);

        assertEquals(200_000L, accountA.getMoney());
        assertTrue(accountA.getIsBlocked());
    }

    @Test
    public void testBankTransfer() {
        Bank bank = new Bank();
        bank.setAccounts(2); // 1 и 2

        bank.transfer("1", "2", 40_000L);

        assertEquals(160_000L, bank.getBalance("1"));
        assertEquals(240_000L, bank.getBalance("2"));
    }

    @Test
    public void testBankTransferLowBalance() {
        Bank bank = new Bank();

        bank.setAccounts(2);

        bank.transfer("1", "2", 300_000L);

        assertEquals(200_000L, bank.getBalance("1"));
        assertEquals(200_000L, bank.getBalance("2"));
    }

    @Test
    public void testWithBlockedAccount() {
        Bank bank = new Bank();
        bank.setAccounts(2);

        bank.accounts.get("1").changeIsBlocked(true);

        bank.transfer("1", "2", 200_000L);

        assertEquals(200_000L, bank.getBalance("1")); // деньги не списались
        assertEquals(200_000L, bank.getBalance("2"));
    }

    @Test
    public void testGetSumAllAccounts() {
        Bank bank = new Bank();
        bank.setAccounts(3);

        assertEquals(600_000L, bank.getSumAllAccounts());
    }
}