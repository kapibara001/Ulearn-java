import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Bank — модульные тесты (многопоточность + защита от race condition)")
public class UnitTestS02 {

    private Bank bank;
    private static final int ACCOUNTS_COUNT = 10;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.setAccounts(ACCOUNTS_COUNT); // каждый аккаунт по 200_000 руб
    }

    @Test
    @DisplayName("1. Перевод < 50_000 — проходит успешно, балансы меняются корректно")
    void smallTransfer_successful() {
        long initialFrom = bank.getBalance("0");
        long initialTo = bank.getBalance("1");

        bank.transfer("0", "1", 30_000);

        assertThat(bank.getBalance("0")).isEqualTo(initialFrom - 30_000);
        assertThat(bank.getBalance("1")).isEqualTo(initialTo + 30_000);
    }

    @Test
    @DisplayName("2. Перевод > 50_000 + isFraud()=true — счета блокируются, деньги НЕ возвращаются")
    void largeTransfer_fraudDetected_accountsBlockedAndMoneyNotRefunded() {
        // Подменяем Random, чтобы isFraud всегда возвращал true
        Bank spyBank = spy(bank);
        doReturn(true).when(spyBank).isFraud(anyString(), anyString(), anyLong());

        spyBank.transfer("2", "3", 60_000);

        assertThat(spyBank.getBalance("2")).isEqualTo(200_000 - 60_000); // деньги списались
        assertThat(spyBank.getBalance("3")).isEqualTo(200_000 + 60_000); // деньги зачислены
        assertThat(spyBank.accounts.get("2").getIsBlocked()).isTrue();
        assertThat(spyBank.accounts.get("3").getIsBlocked()).isTrue();
    }

    @Test
    @DisplayName("3. Перевод > 50_000 + isFraud()=false — перевод проходит, счета НЕ блокируются")
    void largeTransfer_noFraud_accountsNotBlocked() {
        Bank spyBank = spy(bank);
        doReturn(false).when(spyBank).isFraud(anyString(), anyString(), anyLong());

        spyBank.transfer("4", "5", 100_000);

        assertThat(spyBank.accounts.get("4").getIsBlocked()).isFalse();
        assertThat(spyBank.accounts.get("5").getIsBlocked()).isFalse();
    }

    @Test
    @DisplayName("4. Недостаточно средств — перевод отклоняется, баланс не меняется")
    void insufficientFunds_transferRejected() {
        long initialBalance = bank.getBalance("6");

        bank.transfer("6", "7", 300_000); // больше, чем есть

        assertThat(bank.getBalance("6")).isEqualTo(initialBalance);
        assertThat(bank.getBalance("7")).isEqualTo(200_000);
    }

    @Test
    @DisplayName("5. Перевод на заблокированный счёт или со заблокированного — отклоняется")
    void transferWithBlockedAccount_rejected() {
        bank.accounts.get("8").changeIsBlocked(true); // блокируем отправителя

        long balanceFrom = bank.getBalance("8");
        long balanceTo = bank.getBalance("9");

        bank.transfer("8", "9", 10_000);

        assertThat(bank.getBalance("8")).isEqualTo(balanceFrom);
        assertThat(bank.getBalance("9")).isEqualTo(balanceTo);
    }

    @Test
    @DisplayName("6. Перевод с одного и того же аккаунта на себя — пропускается (по логике кода)")
    void transferToSameAccount_skipped() {
        long balance = bank.getBalance("0");

        bank.transfer("0", "0", 50_000); // в TransferRun такой перевод пропускается через continue

        assertThat(bank.getBalance("0")).isEqualTo(balance); // ничего не изменилось
    }

    @Nested
    @DisplayName("ОБЩИЙ МНОГОПОТОЧНЫЙ ТЕСТ — главная проверка корректности")
    class ConcurrentTransfersTest {

        @Test
        @Timeout(10) // должен уложиться в 10 секунд
        @DisplayName("100 потоков × 1000 переводов → сумма всех счетов НЕ меняется (инвариант)")
        void concurrentTransfers_sumOfAllAccountsRemainsConstant() throws InterruptedException {
            long expectedTotal = bank.getSumAllAccounts(); // изначально 10 × 200000 = 2_000_000

            ExecutorService executor = Executors.newFixedThreadPool(100);
            int operationsPerThread = 1000;

            for (int i = 0; i < 100; i++) {
                executor.submit(new TransferRun(bank, ACCOUNTS_COUNT, operationsPerThread));
            }

            executor.shutdown();
            boolean finished = executor.awaitTermination(30, TimeUnit.SECONDS);
            assertThat(finished).isTrue();

            long actualTotal = bank.getSumAllAccounts();

            // Главный инвариант банковской системы: деньги не появляются из ниоткуда и не исчезают
            assertThat(actualTotal)
                    .as("Общая сумма денег в банке должна остаться прежней после любых переводов")
                    .isEqualTo(expectedTotal);
        }
    }
}