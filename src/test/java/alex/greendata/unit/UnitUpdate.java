package alex.greendata.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// AAA: Arrange Act Assert
@Service
public class UnitUpdate {

    //   Редактирование клиента
    @Test
    public void editCustomer() throws IOException {

        // given - предустановки, данные, переменные
        List<Object> oldCustomers = UnitSupport.listRequest("GetCustomers?sort=asc");
        String oldName = "Another One New Customer", name = "New Name Customer", shortName = "NeNew", address = "Absolutely New Address";
        String oldNameHttp = oldName.replace(" ", "%20"),
                nameHttp = name.replace(" ", "%20"),
                shortNameHttp = shortName.replace(" ", "%20"),
                addressHttp = address.replace(" ", "%20");

        UnitSupport.mapRequest("EditCustomer?oldName=" + oldNameHttp + "&name=" + nameHttp + "&shortName=" + shortNameHttp + "&address=" + addressHttp + "&organizationForm=");
        List<Object> updatedCustomers = UnitSupport.listRequest("GetCustomers?sort=asc");
        Map<String, String> searchUpdatedCustomer = UnitSupport.mapRequest("GetCustomerByName?name=" + nameHttp);


        // when - вызов тестируемого кода
        int lastSize = oldCustomers.size(), size = updatedCustomers.size();
        String updatedName = searchUpdatedCustomer.get("name");
        String updatedShortName = searchUpdatedCustomer.get("shortname");
        String updatedAddress = searchUpdatedCustomer.get("address");


        // then - проверка результатов кода
        Assertions.assertNotNull(lastSize);
        Assertions.assertNotNull(size);
        Assertions.assertEquals(lastSize, size);

        Assertions.assertNotNull(updatedName);
        Assertions.assertEquals(updatedName, name);
        Assertions.assertNotNull(updatedShortName);
        Assertions.assertEquals(updatedShortName, shortName);
        Assertions.assertNotNull(updatedAddress);
        Assertions.assertEquals(updatedAddress, address);
    }

    //   Редактирование банка
    @Test
    public void editBank() throws IOException {

        // given - предустановки, данные, переменные
        List<Object> oldBanks = UnitSupport.listRequest("GetBanks?sort=asc&byWhat=bic");
        String oldName = "Bank A", name = "Bank B";
        String oldNameHttp = oldName.replace(" ", "%20"), nameHttp = name.replace(" ", "%20");
        int bic = 736261944;
        UnitSupport.mapRequest("EditBank?oldBank=" + oldNameHttp + "&name=" + nameHttp + "&bic=" + bic);
        List<Object> editedBanks = UnitSupport.listRequest("GetBanks?sort=asc&byWhat=bic");
        Map<String, String> searchEditedBank = UnitSupport.mapRequest("GetBankByName?name=" + nameHttp);


        // when - вызов тестируемого кода
        int lastSize = oldBanks.size(), size = editedBanks.size();
        String editedName = searchEditedBank.get("name");
        int editedBic = Integer.parseInt(searchEditedBank.get("bic"));


        // then - проверка результатов кода
        Assertions.assertNotNull(lastSize);
        Assertions.assertNotNull(size);
        Assertions.assertEquals(lastSize, size);

        Assertions.assertNotNull(editedName);
        Assertions.assertEquals(editedName, name);
        Assertions.assertNotNull(editedBic);
        Assertions.assertEquals(editedBic, bic);
    }

    //   Переоткрытие вклада
    @Test
    public void reopenedContribution() throws IOException {

        // given - предустановки, данные, переменные
        List<Object> oldContributions = UnitSupport.listRequest("GetContributions?sort=asc");
        String customer = "New Name Customer", customerHttp = customer.replace(" ", "%20");
        String bank = "Bank B", bankHttp = bank.replace(" ", "%20");
        int oldPercentage = 15, percentage = 14, oldDuration = 18, duration = 21;
        UnitSupport.mapRequest("EditContribution?oldCustomer=" + customerHttp + "&oldBank=" + bankHttp + "&oldPercentage=" + oldPercentage + "&oldDuration=" + oldDuration +
                "&customer=" + customerHttp + "&bank=" + bankHttp + "&percentage=" + percentage + "&duration=" + duration);
        List<Object> editedContributions = UnitSupport.listRequest("GetContributions?sort=asc");
        List<Object> searchEditedContribution = UnitSupport.listRequest("GetContributionByBank?sort=asc&name=" + bankHttp);
        boolean isReopened = false;


        // when - вызов тестируемого кода
        int lastSize = oldContributions.size(), size = editedContributions.size();

        for (int i = 0; i < searchEditedContribution.size(); i++) {
            Map<String, String> map = (HashMap<String, String>) searchEditedContribution.get(i);
            String editedCustomer = map.get("customer");
            String editedBank = map.get("bank");
            int editedPercentage = Integer.parseInt(map.get("percentage"));
            int editedDuration = Integer.parseInt(map.get("duration"));

            if (editedCustomer.equals(customer) && editedBank.equals(bank) && editedPercentage == percentage && editedDuration == duration)
                isReopened = true;
        }


        // then - проверка результатов кода
        Assertions.assertNotNull(lastSize);
        Assertions.assertNotNull(size);
        Assertions.assertEquals(lastSize, size);
        Assertions.assertEquals(isReopened, true);
    }

}