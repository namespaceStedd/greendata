package alex.greendata.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

// AAA: Arrange Act Assert
@Service
public class UnitRead {

    //   Прямой - обратный порядки
    @Test
    public void getAnyLists() throws IOException {

        // given - предустановки, данные, переменные
        List<Object> customersAsc = UnitSupport.listRequest("GetCustomers?sort=asc"),
                customersDesc = UnitSupport.listRequest("GetCustomers?sort=desc");
        List<Object> banksAsc = UnitSupport.listRequest("GetBanks?sort=asc&byWhat=bic"),
                banksDesc = UnitSupport.listRequest("GetBanks?sort=desc&byWhat=bic");
        boolean isCustomersAsc = true, isCustomersDesc = true;
        boolean isBanksAsc = true, isBanksDesc = true;


        // when - вызов тестируемого кода
        for (int i = 1; i < customersAsc.size(); i++) {
            if (!UnitSupport.isRightOrder(customersAsc, "id", i, true))
                isCustomersAsc = false;
            if (!UnitSupport.isRightOrder(customersDesc, "id", i, false))
                isCustomersDesc = false;
        }

        for (int i = 1; i < banksAsc.size(); i++) {
            if (!UnitSupport.isRightOrder(banksAsc, "bic", i, true))
                isBanksAsc = false;
            if (!UnitSupport.isRightOrder(banksDesc, "bic", i, false))
                isBanksDesc = false;
        }


        // then - проверка результатов кода
        Assertions.assertNotNull(customersAsc);
        Assertions.assertEquals(isCustomersAsc, true);
        Assertions.assertNotNull(customersDesc);
        Assertions.assertEquals(isCustomersDesc, true);

        Assertions.assertNotNull(banksAsc);
        Assertions.assertEquals(isBanksAsc, true);
        Assertions.assertNotNull(banksDesc);
        Assertions.assertEquals(isBanksDesc, true);
    }

    //   Конкретное значение
    @Test
    public void getValue() throws IOException {

        // given - предустановки, данные, переменные
        int bic = 44525974, percentage = 13, duration = 6;
        Map<String, String> bank = UnitSupport.mapRequest("GetBankByBic?bic=" + bic);
        List<Object> contributionPercentageAsc = UnitSupport.listRequest("GetContributionByPercentage?sort=asc&percentage=" + percentage),
                contributionPercentageDesc = UnitSupport.listRequest("GetContributionByPercentage?sort=desc&percentage=" + percentage);
        List<Object> contributionDurationAsc = UnitSupport.listRequest("GetContributionByDuration?sort=asc&duration=" + duration),
                contributionDurationDesc = UnitSupport.listRequest("GetContributionByDuration?sort=desc&duration=" + duration);
        boolean isContributionPercentageAsc = true, isContributionPercentageDesc = true, isPercentageLess = true;
        boolean isContributionDurationAsc = true, isContributionDurationDesc = true, isDurationLess = true;


        // when - вызов тестируемого кода
        int bankBic = Integer.parseInt(bank.get("bic"));

        for (int i = 1; i < contributionPercentageAsc.size(); i++) {
            if (!UnitSupport.isRightOrder(contributionPercentageAsc, "percentage", i, true))
                isContributionPercentageAsc = false;
            if (!UnitSupport.isRightOrder(contributionPercentageDesc, "percentage", i, false))
                isContributionPercentageDesc = false;

            if (!UnitSupport.isLess(contributionPercentageAsc, "percentage", i, percentage) || !UnitSupport.isLess(contributionPercentageDesc, "percentage", i, percentage))
                isPercentageLess = false;

        }

        for (int i = 1; i < contributionDurationAsc.size(); i++) {
            if (!UnitSupport.isRightOrder(contributionDurationAsc, "duration", i, true))
                isContributionDurationAsc = false;
            if (!UnitSupport.isRightOrder(contributionDurationDesc, "duration", i, false))
                isContributionDurationDesc = false;

            if (!UnitSupport.isLess(contributionDurationAsc, "duration", i, duration) || !UnitSupport.isLess(contributionDurationDesc, "duration", i, duration))
                isDurationLess = false;

        }


        // then - проверка результатов кода
        Assertions.assertNotNull(bankBic);
        Assertions.assertEquals(bankBic, bic);

        Assertions.assertNotNull(contributionPercentageAsc);
        Assertions.assertEquals(isContributionPercentageAsc, true);
        Assertions.assertNotNull(contributionPercentageDesc);
        Assertions.assertEquals(isContributionPercentageDesc, true);
        Assertions.assertEquals(isPercentageLess, true);

        Assertions.assertNotNull(contributionDurationAsc);
        Assertions.assertEquals(isContributionDurationAsc, true);
        Assertions.assertNotNull(contributionDurationDesc);
        Assertions.assertEquals(isContributionDurationDesc, true);
        Assertions.assertEquals(isDurationLess, true);
    }

}