package alex.greendata.crudcontroller;

import alex.greendata.support.*;
import alex.greendata.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DeleteController {

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
    //      УДАЛЕНИЕ КЛИЕНТА!!!
    //
    @GetMapping("/DeleteCustomer")
    public ResponseEntity<Object> deleteCustomer(@RequestParam String name) {

        Map<String, Object> deletedCustomer = new HashMap<String, Object>();
        Iterable<Customer> customer = customers.findAll();
        Customer deleteCustomer = null;

        for(Customer currentCustomer:customer) {
            if (name.equals(currentCustomer.getName()) || name.equals(currentCustomer.getShortname())) {
                deleteCustomer = currentCustomer;
                break;
            }
        }

        if (deleteCustomer != null) {
            customers.delete(deleteCustomer);
            deletedCustomer = jsonConverter.customerToJson(deleteCustomer, organizationForms);
        }
        else {
            deletedCustomer = jsonConverter.isNotExist();
        }

        return new ResponseEntity<>(deletedCustomer, HttpStatus.OK);
    }

    //
    //      УДАЛЕНИЕ БАНКА!!!
    //
    @GetMapping("/DeleteBank")
    public ResponseEntity<Object> deleteBank(@RequestParam String name) {

        Map<String, Object> deletedBank = new HashMap<String, Object>();
        Iterable<Bank> bank = banks.findAll();
        Bank deleteBank = null;

        int bic = -111111111;
        try {
            bic = Integer.valueOf(name);
        } catch(Exception exception) { };

        for(Bank currentBank:bank) {
            if (name.equals(currentBank.getName()) || bic == currentBank.getBic()) {
                deleteBank = currentBank;
                break;
            }
        }

        if (deleteBank != null) {
            banks.delete(deleteBank);
            deletedBank = jsonConverter.bankToJson(deleteBank);
        }
        else {
            deletedBank = jsonConverter.isNotExist();
        }

        return new ResponseEntity<>(deletedBank, HttpStatus.OK);
    }

    //
    //      ЗАКРЫТИЕ ВКЛАДА!!!
    //
    @GetMapping("/DeleteContribution")
    public ResponseEntity<Object> deleteContribution(@RequestParam String customer, @RequestParam String bank, @RequestParam int percentage, @RequestParam int duration) {

        Map<String, Object> deletedContribution = new HashMap<String, Object>();
        Iterable<Contribution> contribution = contributions.findAll();
        Contribution deleteContribution = null;

        for(Contribution currentContribution:contribution) {
            if (currentContribution.getCustomer() == tablesRelationships.findIdCustomer(customer, customers)
                    && currentContribution.getBank() == tablesRelationships.findIdBank(bank, banks)
                    && currentContribution.getPercentage() == percentage
                    && currentContribution.getDuration() == duration) {
                deleteContribution = currentContribution;
                break;
            }
        }

        if (deleteContribution != null) {
            contributions.delete(deleteContribution);
            deletedContribution = jsonConverter.contributionToJson(deleteContribution, customers, banks);
        }
        else {
            deletedContribution = jsonConverter.isNotExist();
        }

        return new ResponseEntity<>(deletedContribution, HttpStatus.OK);
    }

}
