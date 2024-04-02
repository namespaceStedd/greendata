package alex.greendata.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// AAA: Arrange Act Assert
@Service
public class UnitDelete {

    //   Закрытие вклада
    @Test
    public void closedContribution() throws IOException {

        // given - предустановки, данные, переменные
        List<Object> oldContributions = UnitSupport.listRequest("GetContributions?sort=asc");
        String customer = "New Name Customer", customerHttp = customer.replace(" ", "%20");
        String bank = "Bank B", bankHttp = bank.replace(" ", "%20");
        int percentage = 14, duration = 21;
        UnitSupport.mapRequest("DeleteContribution?customer=" + customerHttp + "&bank=" + bankHttp + "&percentage=" + percentage + "&duration=" + duration);
        List<Object> deletedContributions = UnitSupport.listRequest("GetContributions?sort=asc");
        List<Object> searchDeletedContribution = UnitSupport.listRequest("GetContributionByBank?sort=asc&name=" + bankHttp);
        boolean isDeleted = true;


        // when - вызов тестируемого кода
        int lastSize = oldContributions.size(), size = deletedContributions.size();

        for (int i = 0; i < searchDeletedContribution.size(); i++) {
            Map<String, String> map = (HashMap<String, String>) searchDeletedContribution.get(i);
            String deletedCustomer = map.get("customer") != null ? map.get("percentage") : "-";
            String deletedBank = map.get("bank") != null ? map.get("percentage") : "-";
            int deletedPercentage = Integer.parseInt(map.get("percentage") != null ? map.get("percentage") : "1");
            int deletedDuration = Integer.parseInt(map.get("duration") != null ? map.get("duration") : "1");

            if (deletedCustomer.equals(customer) && deletedBank.equals(bank) && deletedPercentage == percentage && deletedDuration == duration)
                isDeleted = false;
        }


        // then - проверка результатов кода
        Assertions.assertNotNull(lastSize);
        Assertions.assertNotNull(size);
        Assertions.assertEquals(lastSize - 1, size);
        Assertions.assertEquals(isDeleted, true);
    }

    //   Редактирование клиента
    @Test
    public void deletedCustomer() throws IOException {

        // given - предустановки, данные, переменные
        List<Object> oldCustomers = UnitSupport.listRequest("GetCustomers?sort=asc");
        String name = "New Name Customer", nameHttp = name.replace(" ", "%20");

        UnitSupport.mapRequest("DeleteCustomer?name=" + nameHttp);
        List<Object> deletedCustomers = UnitSupport.listRequest("GetCustomers?sort=asc");
        Map<String, String> searchDeletedCustomer = UnitSupport.mapRequest("GetCustomerByName?name=" + nameHttp);
        boolean isDeleted = false;


        // when - вызов тестируемого кода
        int lastSize = oldCustomers.size(), size = deletedCustomers.size();
        String updatedName = searchDeletedCustomer.get("name");

        if (updatedName == null)
            isDeleted = true;


        // then - проверка результатов кода
        Assertions.assertNotNull(lastSize);
        Assertions.assertNotNull(size);
        Assertions.assertEquals(lastSize - 1, size);
        Assertions.assertEquals(isDeleted, true);
    }

    //   Редактирование банка
    @Test
    public void deleteBank() throws IOException {

        // given - предустановки, данные, переменные
        List<Object> oldBanks = UnitSupport.listRequest("GetBanks?sort=asc&byWhat=bic");
        String name = "Bank B", nameHttp = name.replace(" ", "%20");
        UnitSupport.mapRequest("DeleteBank?name=" + nameHttp);
        List<Object> deletedBanks = UnitSupport.listRequest("GetBanks?sort=asc&byWhat=bic");
        Map<String, String> searchDeletedBank = UnitSupport.mapRequest("GetBankByName?name=" + nameHttp);
        boolean isDeleted = false;


        // when - вызов тестируемого кода
        int lastSize = oldBanks.size(), size = deletedBanks.size();
        String deletedName = searchDeletedBank.get("name");
        if (deletedName == null)
            isDeleted = true;


        // then - проверка результатов кода
        Assertions.assertNotNull(lastSize);
        Assertions.assertNotNull(size);
        Assertions.assertEquals(lastSize - 1, size);
        Assertions.assertEquals(isDeleted, true);
    }

}