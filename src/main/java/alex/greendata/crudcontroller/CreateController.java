package alex.greendata.crudcontroller;

import alex.greendata.support.*;
import alex.greendata.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class CreateController {

    JSONConverter jsonConverter = new JSONConverter();
    TablesRelationships tablesRelationships = new TablesRelationships();
    @Autowired
    private CustomerRepository customers;
    @Autowired
    private OrganizationFormRepository organizationForms;
    @Autowired
    private BankRepository banks;
    @Autowired
    private ContributionRepository contributions;

    //
    //      СОЗДАНИЕ КЛИЕНТА!!!
    //
    @GetMapping("/CreateCustomer")
    public ResponseEntity<Object> createCustomer(@RequestParam String name, @RequestParam String shortName, @RequestParam String address, @RequestParam String organizationForm) {

        Map<String, Object> createdCustomers = new HashMap<String, Object>();
        Iterable<Customer> customer = customers.findAll();

        boolean isExist = false;
        for(Customer currentCustomer:customer) {
            if (name.equals(currentCustomer.getName()) || shortName.equals(currentCustomer.getShortname())) {
                isExist = true;
                break;
            }
        }

        if (!isExist) {
            Customer newCustomer = new Customer(name, shortName, address, tablesRelationships.findIdOrganizationForm(organizationForm, organizationForms));
            customers.save(newCustomer);
            createdCustomers = jsonConverter.customerToJson(newCustomer, organizationForms);
        }
        else {
            createdCustomers = jsonConverter.isExist(name);
        }

        return new ResponseEntity<>(createdCustomers, HttpStatus.OK);
    }

    //
    //      СОЗДАНИЕ БАНКА!!!
    //
    @GetMapping("/CreateBank")
    public ResponseEntity<Object> createBank(@RequestParam String name, @RequestParam int bic) {

        Map<String, Object> createdBank = new HashMap<String, Object>();
        Iterable<Bank> bank = banks.findAll();

        boolean isExist = false;
        for(Bank currentBank:bank) {
            if (name.equals(currentBank.getName()) || bic == currentBank.getBic()) {
                isExist = true;
                break;
            }
        }

        if (!isExist) {
            Bank newBank = new Bank(name, bic);
            banks.save(newBank);
            createdBank = jsonConverter.bankToJson(newBank);
        }
        else {
            createdBank = jsonConverter.isExist(name, bic);
        }

        return new ResponseEntity<>(createdBank, HttpStatus.OK);
    }

    //
    //      ОТКРЫТИЕ ВКЛАДА!!!
    //
    @GetMapping("/CreateContribution")
    public ResponseEntity<Object> createContribution(@RequestParam String customer, @RequestParam String bank, @RequestParam String date, @RequestParam int percentage, @RequestParam int duration) {

        Map<String, Object> createdContribution = new HashMap<String, Object>();
        Date opening = new Date(1648483200000L);
        try {
            opening = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) { }

        Contribution newContribution = new Contribution(
                tablesRelationships.findIdCustomer(customer, customers),
                tablesRelationships.findIdBank(bank, banks),
                opening, percentage, duration);
        contributions.save(newContribution);
        createdContribution = jsonConverter.contributionToJson(newContribution, customers, banks);

        return new ResponseEntity<>(createdContribution, HttpStatus.OK);
    }

}
