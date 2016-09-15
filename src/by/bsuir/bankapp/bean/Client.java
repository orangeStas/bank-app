package by.bsuir.bankapp.bean;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;

public class Client implements Serializable {
    private int idClient;
    private String secName;
    private String name;
    private String surName;
    private java.sql.Date birthday;
    private boolean sex;
    private String passportSeries;
    private String passportNumber;
    private String passportPlace;
    private Date passportDate;
    private String passportId;
    private String birthPlace;
    private String address;
    private String homePhone;
    private String phone;
    private String email;
    private boolean pensioner;
    private boolean military;

    public boolean isMilitary() {
        return military;
    }

    public void setMilitary(boolean military) {
        this.military = military;
    }

    private int income;

    private List<String> familyMembers;
    private List<String> ills;
    private List<String> nationalities;
    private List<String> livingCities;

    public List<String> getLivingCities() {
        return livingCities;
    }

    public void setLivingCities(List<String> livingCities) {
        this.livingCities = livingCities;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPassportPlace() {
        return passportPlace;
    }

    public void setPassportPlace(String passportPlace) {
        this.passportPlace = passportPlace;
    }

    public Date getPassportDate() {
        return passportDate;
    }

    public void setPassportDate(Date passportDate) {
        this.passportDate = passportDate;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public boolean isPensioner() {
        return pensioner;
    }

    public void setPensioner(boolean pensioner) {
        this.pensioner = pensioner;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public List<String> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<String> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public List<String> getIlls() {
        return ills;
    }

    public void setIlls(List<String> ills) {
        this.ills = ills;
    }

    public List<String> getNationalities() {
        return nationalities;
    }

    public void setNationalities(List<String> nationalities) {
        this.nationalities = nationalities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (sex != client.sex) return false;
        if (pensioner != client.pensioner) return false;
        if (military != client.military) return false;
        if (income != client.income) return false;
        if (secName != null ? !secName.equals(client.secName) : client.secName != null) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (surName != null ? !surName.equals(client.surName) : client.surName != null) return false;
        if (passportSeries != null ? !passportSeries.equals(client.passportSeries) : client.passportSeries != null)
            return false;
        if (passportNumber != null ? !passportNumber.equals(client.passportNumber) : client.passportNumber != null)
            return false;
        if (passportPlace != null ? !passportPlace.equals(client.passportPlace) : client.passportPlace != null)
            return false;
        if (passportId != null ? !passportId.equals(client.passportId) : client.passportId != null) return false;
        if (birthPlace != null ? !birthPlace.equals(client.birthPlace) : client.birthPlace != null) return false;
        if (address != null ? !address.equals(client.address) : client.address != null) return false;
        if (homePhone != null ? !homePhone.equals(client.homePhone) : client.homePhone != null) return false;
        if (phone != null ? !phone.equals(client.phone) : client.phone != null) return false;
        if (email != null ? !email.equals(client.email) : client.email != null) return false;
        if (familyMembers != null ? !familyMembers.equals(client.familyMembers) : client.familyMembers != null)
            return false;
        if (ills != null ? !ills.equals(client.ills) : client.ills != null) return false;
        if (nationalities != null ? !nationalities.equals(client.nationalities) : client.nationalities != null)
            return false;
        return livingCities != null ? new HashSet<>(livingCities).equals(new HashSet<>(client.livingCities)) : client.livingCities == null;

    }

    @Override
    public int hashCode() {
        int result = secName != null ? secName.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surName != null ? surName.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (sex ? 1 : 0);
        result = 31 * result + (passportSeries != null ? passportSeries.hashCode() : 0);
        result = 31 * result + (passportNumber != null ? passportNumber.hashCode() : 0);
        result = 31 * result + (passportPlace != null ? passportPlace.hashCode() : 0);
        result = 31 * result + (passportDate != null ? passportDate.hashCode() : 0);
        result = 31 * result + (passportId != null ? passportId.hashCode() : 0);
        result = 31 * result + (birthPlace != null ? birthPlace.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (homePhone != null ? homePhone.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (pensioner ? 1 : 0);
        result = 31 * result + (military ? 1 : 0);
        result = 31 * result + income;
        result = 31 * result + (familyMembers != null ? familyMembers.hashCode() : 0);
        result = 31 * result + (ills != null ? ills.hashCode() : 0);
        result = 31 * result + (nationalities != null ? nationalities.hashCode() : 0);
        result = 31 * result + (livingCities != null ? livingCities.hashCode() : 0);
        return result;
    }
}
