package alex.greendata.crudcontroller;

import alex.greendata.support.*;
import alex.greendata.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class ReadController {

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
    //      ПОЛУЧЕНИЕ КЛИЕНТОВ!!!
    //
    @GetMapping("/GetCustomers")
    public ResponseEntity<Object> getCustomers(@RequestParam String sort) {

        List<Object> requestedCustomers = new ArrayList<>();
        Iterable<Customer> customer;
        if (sort.equals("asc"))
            customer = customers.findAll(Sort.by(Sort.Direction.ASC, "id"));
        else if (sort.equals("desc"))
            customer = customers.findAll(Sort.by(Sort.Direction.DESC, "id"));
        else
            customer = customers.findAll();

        for(Customer currentCustomer:customer) {
            requestedCustomers.add(jsonConverter.customerToJson(currentCustomer, organizationForms));
        }

        return new ResponseEntity<>(requestedCustomers, HttpStatus.OK);
    }

    @GetMapping("/GetCustomerByName")
    public ResponseEntity<Object> getCustomer(@RequestParam String name) {

        Customer requestedCustomer = null;
        Iterable<Customer> customer = customers.findAll();

        for(Customer currentCustomer:customer) {
            if(name.equals(currentCustomer.getName()) || name.equals(currentCustomer.getShortname())) {
                requestedCustomer = currentCustomer;
                break;
            }
        }

        if (requestedCustomer != null)
            return new ResponseEntity<>(jsonConverter.customerToJson(requestedCustomer, organizationForms), HttpStatus.OK);
        else
            return new ResponseEntity<>(jsonConverter.isNotExist(), HttpStatus.OK);
    }

    @GetMapping("/GetCustomersByAddress")
    public ResponseEntity<Object> getCustomersAdd(@RequestParam String sort, @RequestParam String address) {

        List<Object> requestedCustomers = new ArrayList<>();
        Iterable<Customer> customer;
        if (sort.equals("asc"))
            customer = customers.findAll(Sort.by(Sort.Direction.ASC, "address"));
        else if (sort.equals("desc"))
            customer = customers.findAll(Sort.by(Sort.Direction.DESC, "address"));
        else
            customer = customers.findAll();

        for(Customer currentCustomer:customer) {
            if(currentCustomer.getAddress().contains(address)) {
                requestedCustomers.add(jsonConverter.customerToJson(currentCustomer, organizationForms));
            }
        }

        return new ResponseEntity<>(requestedCustomers, HttpStatus.OK);
    }

    @GetMapping("/GetCustomersByOrganizationForm")
    public ResponseEntity<Object> getCustomersOrg(@RequestParam String sort, @RequestParam String organizationForm) {

        int numberOrgForm = tablesRelationships.findIdOrganizationForm(organizationForm, organizationForms);
        List<Object> requestedCustomers = new ArrayList<>();
        Iterable<Customer> customer;
        if (sort.equals("asc"))
            customer = customers.findAll(Sort.by(Sort.Direction.ASC, "organizationForm"));
        else if (sort.equals("desc"))
            customer = customers.findAll(Sort.by(Sort.Direction.DESC, "organizationForm"));
        else
            customer = customers.findAll();

        for(Customer currentCustomer:customer) {
            if(currentCustomer.getOrganizationForm() == numberOrgForm) {
                requestedCustomers.add(jsonConverter.customerToJson(currentCustomer, organizationForms));
            }
        }

        return new ResponseEntity<>(requestedCustomers, HttpStatus.OK);
    }


    //
    //      ПОЛУЧЕНИЕ БАНКОВ!!!
    //
    @GetMapping("/GetBanks")
    public ResponseEntity<Object> GetBanks(@RequestParam String sort, @RequestParam String byWhat) {

        List<Object> requestedBanks = new ArrayList<>();
        Iterable<Bank> bank;
        if (sort.equals("asc"))
            bank = banks.findAll(Sort.by(Sort.Direction.ASC, byWhat));
        else if (sort.equals("desc"))
            bank = banks.findAll(Sort.by(Sort.Direction.DESC, byWhat));
        else
            bank = banks.findAll();

        for(Bank currentBank:bank) {
            requestedBanks.add(jsonConverter.bankToJson(currentBank));
        }

        return new ResponseEntity<>(requestedBanks, HttpStatus.OK);
    }

    @GetMapping("/GetBankByName")
    public ResponseEntity<Object> GetBankByName(@RequestParam String name) {

        List<Object> requestedBanks = new ArrayList<>();
        Iterable<Bank> bank = banks.findAll();

        for(Bank currentBank:bank) {
            if(name.equals(currentBank.getName())) {
                requestedBanks.add(jsonConverter.bankToJson(currentBank));
                break;
            }
        }

        return new ResponseEntity<>(requestedBanks, HttpStatus.OK);
    }

    @GetMapping("/GetBankByBic")
    public ResponseEntity<Object> GetBankByBic(@RequestParam int bic) {

        List<Object> requestedBanks = new ArrayList<>();
        Iterable<Bank> bank = banks.findAll();

        for(Bank currentBank:bank) {
            if(currentBank.getBic() == bic) {
                requestedBanks.add(jsonConverter.bankToJson(currentBank));
                break;
            }
        }

        return new ResponseEntity<>(requestedBanks, HttpStatus.OK);
    }


    //
    //      ПОЛУЧЕНИЕ ВКЛАДОВ!!!
    //
    @GetMapping("/GetContributions")
    public ResponseEntity<Object> GetContributions(@RequestParam String sort) {

        List<Object> requestedContributions = new ArrayList<>();
        Iterable<Contribution> contribution;
        if (sort.equals("asc"))
            contribution = contributions.findAll(Sort.by(Sort.Direction.ASC, "opening"));
        else if (sort.equals("desc"))
            contribution = contributions.findAll(Sort.by(Sort.Direction.DESC, "opening"));
        else
            contribution = contributions.findAll();

        for(Contribution currentContribution:contribution) {
            requestedContributions.add(jsonConverter.contributionToJson(currentContribution, customers, banks));
        }

        return new ResponseEntity<>(requestedContributions, HttpStatus.OK);
    }

    @GetMapping("/GetContributionsByClientName")
    public ResponseEntity<Object> GetContributionsByClientName(@RequestParam String sort, @RequestParam String name) {

        List<Object> requestedContributions = new ArrayList<>();
        Iterable<Contribution> contribution;
        if (sort.equals("asc"))
            contribution = contributions.findAll(Sort.by(Sort.Direction.ASC, "customer"));
        else if (sort.equals("desc"))
            contribution = contributions.findAll(Sort.by(Sort.Direction.DESC, "customer"));
        else
            contribution = contributions.findAll();

        for(Contribution currentContribution:contribution) {
            if (currentContribution.getCustomer() == tablesRelationships.findIdCustomer(name, customers)) {
                requestedContributions.add(jsonConverter.contributionToJson(currentContribution, customers, banks));
            }
        }

        return new ResponseEntity<>(requestedContributions, HttpStatus.OK);
    }

    @GetMapping("/GetContributionByBank")
    public ResponseEntity<Object> GetContributionByBank(@RequestParam String sort, @RequestParam String name) {

        List<Object> requestedContributions = new ArrayList<>();
        Iterable<Contribution> contribution;
        if (sort.equals("asc"))
            contribution = contributions.findAll(Sort.by(Sort.Direction.ASC, "bank"));
        else if (sort.equals("desc"))
            contribution = contributions.findAll(Sort.by(Sort.Direction.DESC, "bank"));
        else
            contribution = contributions.findAll();

        for(Contribution currentContribution:contribution) {
            if (currentContribution.getBank() == tablesRelationships.findIdBank(name, banks)) {
                requestedContributions.add(jsonConverter.contributionToJson(currentContribution, customers, banks));
            }
        }

        return new ResponseEntity<>(requestedContributions, HttpStatus.OK);
    }

    @GetMapping("/GetContributionByDate")
    public ResponseEntity<Object> GetContributionByDate(@RequestParam String sort, @RequestParam String date) {

        Date opening = new Date(1648483200000L);
        try {
            opening = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) { }

        List<Object> requestedContributions = new ArrayList<>();
        Iterable<Contribution> contribution;
        if (sort.equals("asc"))
            contribution = contributions.findAll(Sort.by(Sort.Direction.ASC, "opening"));
        else if (sort.equals("desc"))
            contribution = contributions.findAll(Sort.by(Sort.Direction.DESC, "opening"));
        else
            contribution = contributions.findAll();

        for(Contribution currentContribution:contribution) {
            if (opening.equals(currentContribution.getOpening())) {
                requestedContributions.add(jsonConverter.contributionToJson(currentContribution, customers, banks));
            }
        }

        return new ResponseEntity<>(requestedContributions, HttpStatus.OK);
    }

    @GetMapping("/GetContributionByPercentage")
    public ResponseEntity<Object> GetContributionByPercentage(@RequestParam String sort, @RequestParam int percentage) {

        List<Object> requestedContributions = new ArrayList<>();
        Iterable<Contribution> contribution;
        if (sort.equals("asc"))
            contribution = contributions.findAll(Sort.by(Sort.Direction.ASC, "percentage"));
        else if (sort.equals("desc"))
            contribution = contributions.findAll(Sort.by(Sort.Direction.DESC, "percentage"));
        else
            contribution = contributions.findAll();

        for(Contribution currentContribution:contribution) {
            if (currentContribution.getPercentage() <= percentage) {
                requestedContributions.add(jsonConverter.contributionToJson(currentContribution, customers, banks));
            }
        }

        return new ResponseEntity<>(requestedContributions, HttpStatus.OK);
    }

    @GetMapping("/GetContributionByDuration")
    public ResponseEntity<Object> GetContributionByDuration(@RequestParam String sort, @RequestParam int duration) {

        List<Object> requestedContributions = new ArrayList<>();
        Iterable<Contribution> contribution;
        if (sort.equals("asc"))
            contribution = contributions.findAll(Sort.by(Sort.Direction.ASC, "duration"));
        else if (sort.equals("desc"))
            contribution = contributions.findAll(Sort.by(Sort.Direction.DESC, "duration"));
        else
            contribution = contributions.findAll();

        for(Contribution currentContribution:contribution) {
            if (currentContribution.getDuration() <= duration) {
                requestedContributions.add(jsonConverter.contributionToJson(currentContribution, customers, banks));
            }
        }

        return new ResponseEntity<>(requestedContributions, HttpStatus.OK);
    }

}
