package alex.greendata.support;

import alex.greendata.database.*;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JSONConverter {

    TablesRelationships tablesRelationships = new TablesRelationships();

    public Map<String, Object> isNotExist() {
        Map<String, Object> map = new HashMap<>();
        map.put("error", "Запращиваемой информации нету в базе данных");
        return map;
    }

    public Map<String, Object> isExist(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", "Пользователь \"" + name +"\" уже есть в базе данных!");
        return map;
    }

    public Map<String, Object> isExist(String name, int addInfo) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", "Банк \"" + name +" (" + addInfo +")\" уже есть в базе данных!");
        return map;
    }

    public Map<String, Object> customerToJson(Customer customer,OrganizationFormRepository organizationForms) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", customer.getId());
        map.put("name", customer.getName());
        map.put("shortname", customer.getShortname());
        map.put("address", customer.getAddress());
        map.put("organization_form", tablesRelationships.findNameOrganizationForm(customer.getOrganizationForm(), organizationForms));
        return map;
    }

    public Map<String, Object> bankToJson(Bank bank) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", bank.getId());
        map.put("name", bank.getName());
        map.put("bic", bank.getBic());
        return map;
    }

    public Map<String, Object> contributionToJson(Contribution contribution, CustomerRepository customers, BankRepository banks) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", contribution.getId());
        map.put("customer", tablesRelationships.findNameCustomer(contribution.getCustomer(), customers));
        map.put("bank", tablesRelationships.findNameBank(contribution.getBank(), banks));
        map.put("opening", contribution.getOpening());
        map.put("percentage", contribution.getPercentage());
        map.put("duration", contribution.getDuration());
        return map;
    }
}
