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
public class UpdateController {

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
    //      РЕДАКТИРОВАНИЕ КЛИЕНТА!!!
    //
    @GetMapping("/EditCustomer")
    public ResponseEntity<Object> editCustomer(@RequestParam String oldName, @RequestParam String name, @RequestParam String shortName, @RequestParam String address, @RequestParam String organizationForm) {

        Map<String, Object> editedCustomer = new HashMap<String, Object>();
        Iterable<Customer> customer = customers.findAll();
        Customer editCustomer = null;

        for(Customer currentCustomer:customer) {
            if (oldName.equals(currentCustomer.getName()) || oldName.equals(currentCustomer.getShortname())) {
                editCustomer = currentCustomer;
                break;
            }
        }

        if (editCustomer != null) {
            editCustomer.setName(name);
            editCustomer.setShortname(shortName);
            editCustomer.setAddress(address);
            editCustomer.setOrganizationForm(tablesRelationships.findIdOrganizationForm(organizationForm, organizationForms));
            customers.save(editCustomer);
            editedCustomer = jsonConverter.customerToJson(editCustomer, organizationForms);
        }
        else {
            editedCustomer = jsonConverter.isNotExist();
        }

        return new ResponseEntity<>(editedCustomer, HttpStatus.OK);
    }

    //
    //      РЕДАКТИРОВАНИЕ БАНКА!!!
    //
    @GetMapping("/EditBank")
    public ResponseEntity<Object> editBank(@RequestParam String oldBank, @RequestParam String name, @RequestParam int bic) {

        Map<String, Object> editedBank = new HashMap<String, Object>();
        Iterable<Bank> bank = banks.findAll();
        Bank editBank = null;

        int oldBic = -111111111;
        try {
            oldBic = Integer.valueOf(oldBank);
        } catch(Exception exception) { };

        for(Bank currentBank:bank) {
            if (oldBank.equals(currentBank.getName()) || oldBic == currentBank.getBic()) {
                editBank = currentBank;
                break;
            }
        }

        if (editBank != null) {
            editBank.setName(name);
            editBank.setBic(bic);
            banks.save(editBank);
            editedBank = jsonConverter.bankToJson(editBank);
        }
        else {
            editedBank = jsonConverter.isNotExist();
        }

        return new ResponseEntity<>(editedBank, HttpStatus.OK);
    }

    //
    //      ПЕРЕОТКРЫТИЕ ВКЛАДА!!!
    //
    @GetMapping("/EditContribution")
    public ResponseEntity<Object> editContribution(@RequestParam String oldCustomer, @RequestParam String oldBank, @RequestParam int oldPercentage, @RequestParam int oldDuration, @RequestParam String customer, @RequestParam String bank, @RequestParam int percentage, @RequestParam int duration) {

        Map<String, Object> editedContribution = new HashMap<String, Object>();
        Iterable<Contribution> contribution = contributions.findAll();
        Contribution editContribution = null;

        for(Contribution currentContribution:contribution) {
            if (currentContribution.getCustomer() == tablesRelationships.findIdCustomer(oldCustomer, customers)
                    && currentContribution.getBank() == tablesRelationships.findIdBank(oldBank, banks)
                    && currentContribution.getPercentage() == oldPercentage
                    && currentContribution.getDuration() == oldDuration) {
                editContribution = currentContribution;
                break;
            }
        }

        if (editContribution != null) {
            editContribution.setCustomer(tablesRelationships.findIdCustomer(customer, customers));
            editContribution.setBank(tablesRelationships.findIdBank(bank, banks));
            editContribution.setPercentage(percentage);
            editContribution.setDuration(duration);
            contributions.save(editContribution);
            editedContribution = jsonConverter.contributionToJson(editContribution, customers, banks);
        }
        else {
            editedContribution = jsonConverter.isNotExist();
        }

        return new ResponseEntity<>(editedContribution, HttpStatus.OK);
    }

}
