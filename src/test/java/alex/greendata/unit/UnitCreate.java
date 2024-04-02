package alex.greendata.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// AAA: Arrange Act Assert
@Service
public class UnitCreate {

    //   Создание клиента
    @Test
    public void createCustomer() throws IOException {

        // given - предустановки, данные, переменные
        List<Object> oldCustomers = UnitSupport.listRequest("GetCustomers?sort=asc");
        String name = "Another One New Customer", shortName = "NewCustom", address = "NeWest";
        String nameHttp = name.replace(" ", "%20"),
                shortNameHttp = shortName.replace(" ", "%20"),
                addressHttp = address.replace(" ", "%20");
        UnitSupport.mapRequest("CreateCustomer?name=" + nameHttp + "&shortName=" + shortNameHttp + "&address=" + addressHttp + "&organizationForm=");
        List<Object> newCustomers = UnitSupport.listRequest("GetCustomers?sort=asc");
        Map<String, String> searchNewCustomer = UnitSupport.mapRequest("GetCustomerByName?name=" + shortNameHttp);


        // when - вызов тестируемого кода
        int lastSize = oldCustomers.size(), size = newCustomers.size();
        String newName = searchNewCustomer.get("name");
        String newShortName = searchNewCustomer.get("shortname");
        String newAddress = searchNewCustomer.get("address");


        // then - проверка результатов кода
        Assertions.assertNotNull(lastSize);
        Assertions.assertNotNull(size);
        Assertions.assertEquals(lastSize + 1, size);

        Assertions.assertNotNull(newName);
        Assertions.assertEquals(newName, name);
        Assertions.assertNotNull(newShortName);
        Assertions.assertEquals(newShortName, shortName);
        Assertions.assertNotNull(newAddress);
        Assertions.assertEquals(newAddress, address);
    }

    //   Создание банка
    @Test
    public void createBank() throws IOException {

        // given - предустановки, данные, переменные
        List<Object> oldBanks = UnitSupport.listRequest("GetBanks?sort=asc&byWhat=bic");
        String name = "Bank A", nameHttp = name.replace(" ", "%20");
        int bic = 736261943;
        UnitSupport.mapRequest("CreateBank?name=" + nameHttp + "&bic=" + bic);
        List<Object> newBanks = UnitSupport.listRequest("GetBanks?sort=asc&byWhat=bic");
        Map<String, String> searchNewBank = UnitSupport.mapRequest("GetBankByName?name=" + nameHttp);


        // when - вызов тестируемого кода
        int lastSize = oldBanks.size(), size = newBanks.size();
        String newName = searchNewBank.get("name");
        int newBic = Integer.parseInt(searchNewBank.get("bic"));


        // then - проверка результатов кода
        Assertions.assertNotNull(lastSize);
        Assertions.assertNotNull(size);
        Assertions.assertEquals(lastSize + 1, size);

        Assertions.assertNotNull(newName);
        Assertions.assertEquals(newName, name);
        Assertions.assertNotNull(newBic);
        Assertions.assertEquals(newBic, bic);
    }

    //   Открытие вклада
    @Test
    public void openContribution() throws IOException {

        // given - предустановки, данные, переменные
        List<Object> oldContributions = UnitSupport.listRequest("GetContributions?sort=asc");
        String customer = "Another One New Customer", customerHttp = customer.replace(" ", "%20");
        String bank = "Bank A", bankHttp = bank.replace(" ", "%20");
        String nowadays = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        int percentage = 15, duration = 18;
        UnitSupport.mapRequest("CreateContribution?customer=" + customerHttp + "&bank=" + bankHttp + "&date=" + nowadays + "&percentage=" + percentage + "&duration=" + duration);
        List<Object> newContributions = UnitSupport.listRequest("GetContributions?sort=asc");
        List<Object> searchNewContribution = UnitSupport.listRequest("GetContributionsByClientName?sort=asc&name=" + customerHttp);
        boolean isOpened = false;


        // when - вызов тестируемого кода
        int lastSize = oldContributions.size(), size = newContributions.size();

        for (int i = 0; i < searchNewContribution.size(); i++) {
            Map<String, String> map = (HashMap<String, String>) searchNewContribution.get(i);
            String newCustomer = map.get("customer");
            String newBank = map.get("bank");
            int newPercentage = Integer.parseInt(map.get("percentage"));
            int newDuration = Integer.parseInt(map.get("duration"));

            if (newCustomer.equals(customer) && newBank.equals(bank) && newPercentage == percentage && newDuration == duration)
                isOpened = true;
        }


        // then - проверка результатов кода
        Assertions.assertNotNull(lastSize);
        Assertions.assertNotNull(size);
        Assertions.assertEquals(lastSize + 1, size);
        Assertions.assertEquals(isOpened, true);
    }

}