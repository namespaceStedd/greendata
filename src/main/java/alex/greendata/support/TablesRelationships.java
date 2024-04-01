package alex.greendata.support;

import alex.greendata.database.*;

public class TablesRelationships {

    public int findIdOrganizationForm(String organizationForm, OrganizationFormRepository organizationForms) {
        Iterable<OrganizationForm> findOrganizationForm = organizationForms.findAll();

        int numberOrgForm = -1;
        for (OrganizationForm currentOrganizationForm:findOrganizationForm) {
            if(organizationForm.equals(currentOrganizationForm.getName())) {
                numberOrgForm = currentOrganizationForm.getId();
                break;
            }
        }

        return numberOrgForm;
    }

    public String findNameOrganizationForm(int id, OrganizationFormRepository organizationForms) {
        Iterable<OrganizationForm> findOrganizationForm = organizationForms.findAll();

        String nameOrgForm = "-";
        for (OrganizationForm currentOrganizationForm:findOrganizationForm) {
            if(id == currentOrganizationForm.getId()) {
                nameOrgForm = currentOrganizationForm.getName();
                break;
            }
        }

        return nameOrgForm;
    }

    public int findIdCustomer(String name, CustomerRepository customers) {
        Iterable<Customer> findCustomer = customers.findAll();

        int numberCustomer = -1;
        for (Customer currentCustomer:findCustomer) {
            if(name.equals(currentCustomer.getName()) || name.equals(currentCustomer.getShortname())) {
                numberCustomer = currentCustomer.getId();
                break;
            }
        }

        return numberCustomer;
    }

    public String findNameCustomer(int id, CustomerRepository customers) {
        Iterable<Customer> findCustomer = customers.findAll();

        String nameCustomer = "-";
        for (Customer currentCustomer:findCustomer) {
            if(id == currentCustomer.getId()) {
                nameCustomer = currentCustomer.getName();
                break;
            }
        }

        return nameCustomer;
    }

    public int findIdBank(String name, BankRepository banks) {
        Iterable<Bank> findBank = banks.findAll();

        int numberBank = -1;
        for (Bank currentBank:findBank) {
            if(name.equals(currentBank.getName())) {
                numberBank = currentBank.getId();
                break;
            }
        }

        return numberBank;
    }

    public String findNameBank(int id, BankRepository banks) {
        Iterable<Bank> findBank = banks.findAll();

        String nameBank = "-";
        for (Bank currentBank:findBank) {
            if(id == currentBank.getId()) {
                nameBank = currentBank.getName();
                break;
            }
        }

        return nameBank;
    }
}
