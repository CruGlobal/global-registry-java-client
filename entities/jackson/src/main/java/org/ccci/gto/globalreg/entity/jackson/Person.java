
package org.ccci.gto.globalreg.entity.jackson;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "account_number",
    "active_status",
    "birth_day",
    "birth_month",
    "cru_employee",
    "date_joined_staff",
    "first_name",
    "funding_source",
    "gender",
    "hr_status_code",
    "is_staff",
    "lake_hart_mail_code",
    "last_name",
    "location_code",
    "location_work",
    "marital_status",
    "middle_name",
    "name_address_edit_flag",
    "original_hire_date",
    "paygroup",
    "preferred_name",
    "ministry_code",
    "ministry_descr",
    "suffix",
    "title",
    "sub_ministry_code",
    "sub_ministry_descr",
    "email_address",
    "phone_number",
    "authentication",
    "address",
    "birth_year",
    "birth_date",
    "country_of_residence",
    "employment_country",
    "language",
    "job_status",
    "dept_code",
    "dept_descr",
    "job_code",
    "job_descr",
    "supervisor_emplid",
    "client_updated_at",
    "client_integration_id"
})
public class Person {

    @JsonProperty("id")
    private String id;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("active_status")
    private String activeStatus;
    @JsonProperty("birth_day")
    private Integer birthDay;
    @JsonProperty("birth_month")
    private Integer birthMonth;
    @JsonProperty("cru_employee")
    private Boolean cruEmployee;
    @JsonProperty("date_joined_staff")
    private String dateJoinedStaff;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("funding_source")
    private String fundingSource;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("hr_status_code")
    private String hrStatusCode;
    @JsonProperty("is_staff")
    private Boolean isStaff;
    @JsonProperty("lake_hart_mail_code")
    private String lakeHartMailCode;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("location_code")
    private String locationCode;
    @JsonProperty("location_work")
    private String locationWork;
    @JsonProperty("marital_status")
    private String maritalStatus;
    @JsonProperty("middle_name")
    private String middleName;
    @JsonProperty("name_address_edit_flag")
    private String nameAddressEditFlag;
    @JsonProperty("original_hire_date")
    private String originalHireDate;
    @JsonProperty("paygroup")
    private String paygroup;
    @JsonProperty("preferred_name")
    private String preferredName;
    @JsonProperty("ministry_code")
    private String ministryCode;
    @JsonProperty("ministry_descr")
    private String ministryDescr;
    @JsonProperty("suffix")
    private String suffix;
    @JsonProperty("title")
    private String title;
    @JsonProperty("sub_ministry_code")
    private String subMinistryCode;
    @JsonProperty("sub_ministry_descr")
    private String subMinistryDescr;
    @JsonProperty("email_address")
    private EmailAddress emailAddress;
    @JsonProperty("phone_number")
    private PhoneNumber phoneNumber;
    @JsonProperty("authentication")
    private Authentication authentication;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("birth_year")
    private Integer birthYear;
    @JsonProperty("birth_date")
    private String birthDate;
    @JsonProperty("country_of_residence")
    private String countryOfResidence;
    @JsonProperty("employment_country")
    private String employmentCountry;
    @JsonProperty("language")
    private String language;
    @JsonProperty("job_status")
    private String jobStatus;
    @JsonProperty("dept_code")
    private String deptCode;
    @JsonProperty("dept_descr")
    private String deptDescr;
    @JsonProperty("job_code")
    private String jobCode;
    @JsonProperty("job_descr")
    private String jobDescr;
    @JsonProperty("supervisor_emplid")
    private String supervisorEmplid;
    @JsonProperty("client_updated_at")
    private String clientUpdatedAt;
    @JsonProperty("client_integration_id")
    private String clientIntegrationId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Person() {
    }

    /**
     * 
     * @param originalHireDate
     * @param supervisorEmplid
     * @param lastName
     * @param jobStatus
     * @param gender
     * @param jobCode
     * @param language
     * @param paygroup
     * @param suffix
     * @param title
     * @param countryOfResidence
     * @param clientUpdatedAt
     * @param ministryCode
     * @param deptDescr
     * @param lakeHartMailCode
     * @param subMinistryCode
     * @param emailAddress
     * @param nameAddressEditFlag
     * @param employmentCountry
     * @param id
     * @param preferredName
     * @param cruEmployee
     * @param authentication
     * @param birthDay
     * @param locationWork
     * @param address
     * @param isStaff
     * @param accountNumber
     * @param birthDate
     * @param clientIntegrationId
     * @param birthMonth
     * @param firstName
     * @param phoneNumber
     * @param activeStatus
     * @param dateJoinedStaff
     * @param birthYear
     * @param subMinistryDescr
     * @param middleName
     * @param fundingSource
     * @param hrStatusCode
     * @param locationCode
     * @param ministryDescr
     * @param jobDescr
     * @param maritalStatus
     * @param deptCode
     */
    public Person(String id, String accountNumber, String activeStatus, Integer birthDay, Integer birthMonth, Boolean cruEmployee, String dateJoinedStaff, String firstName, String fundingSource, String gender, String hrStatusCode, Boolean isStaff, String lakeHartMailCode, String lastName, String locationCode, String locationWork, String maritalStatus, String middleName, String nameAddressEditFlag, String originalHireDate, String paygroup, String preferredName, String ministryCode, String ministryDescr, String suffix, String title, String subMinistryCode, String subMinistryDescr, EmailAddress emailAddress, PhoneNumber phoneNumber, Authentication authentication, Address address, Integer birthYear, String birthDate, String countryOfResidence, String employmentCountry, String language, String jobStatus, String deptCode, String deptDescr, String jobCode, String jobDescr, String supervisorEmplid, String clientUpdatedAt, String clientIntegrationId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.activeStatus = activeStatus;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.cruEmployee = cruEmployee;
        this.dateJoinedStaff = dateJoinedStaff;
        this.firstName = firstName;
        this.fundingSource = fundingSource;
        this.gender = gender;
        this.hrStatusCode = hrStatusCode;
        this.isStaff = isStaff;
        this.lakeHartMailCode = lakeHartMailCode;
        this.lastName = lastName;
        this.locationCode = locationCode;
        this.locationWork = locationWork;
        this.maritalStatus = maritalStatus;
        this.middleName = middleName;
        this.nameAddressEditFlag = nameAddressEditFlag;
        this.originalHireDate = originalHireDate;
        this.paygroup = paygroup;
        this.preferredName = preferredName;
        this.ministryCode = ministryCode;
        this.ministryDescr = ministryDescr;
        this.suffix = suffix;
        this.title = title;
        this.subMinistryCode = subMinistryCode;
        this.subMinistryDescr = subMinistryDescr;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.authentication = authentication;
        this.address = address;
        this.birthYear = birthYear;
        this.birthDate = birthDate;
        this.countryOfResidence = countryOfResidence;
        this.employmentCountry = employmentCountry;
        this.language = language;
        this.jobStatus = jobStatus;
        this.deptCode = deptCode;
        this.deptDescr = deptDescr;
        this.jobCode = jobCode;
        this.jobDescr = jobDescr;
        this.supervisorEmplid = supervisorEmplid;
        this.clientUpdatedAt = clientUpdatedAt;
        this.clientIntegrationId = clientIntegrationId;
    }

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The accountNumber
     */
    @JsonProperty("account_number")
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * 
     * @param accountNumber
     *     The account_number
     */
    @JsonProperty("account_number")
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * 
     * @return
     *     The activeStatus
     */
    @JsonProperty("active_status")
    public String getActiveStatus() {
        return activeStatus;
    }

    /**
     * 
     * @param activeStatus
     *     The active_status
     */
    @JsonProperty("active_status")
    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    /**
     * 
     * @return
     *     The birthDay
     */
    @JsonProperty("birth_day")
    public Integer getBirthDay() {
        return birthDay;
    }

    /**
     * 
     * @param birthDay
     *     The birth_day
     */
    @JsonProperty("birth_day")
    public void setBirthDay(Integer birthDay) {
        this.birthDay = birthDay;
    }

    /**
     * 
     * @return
     *     The birthMonth
     */
    @JsonProperty("birth_month")
    public Integer getBirthMonth() {
        return birthMonth;
    }

    /**
     * 
     * @param birthMonth
     *     The birth_month
     */
    @JsonProperty("birth_month")
    public void setBirthMonth(Integer birthMonth) {
        this.birthMonth = birthMonth;
    }

    /**
     * 
     * @return
     *     The cruEmployee
     */
    @JsonProperty("cru_employee")
    public Boolean getCruEmployee() {
        return cruEmployee;
    }

    /**
     * 
     * @param cruEmployee
     *     The cru_employee
     */
    @JsonProperty("cru_employee")
    public void setCruEmployee(Boolean cruEmployee) {
        this.cruEmployee = cruEmployee;
    }

    /**
     * 
     * @return
     *     The dateJoinedStaff
     */
    @JsonProperty("date_joined_staff")
    public String getDateJoinedStaff() {
        return dateJoinedStaff;
    }

    /**
     * 
     * @param dateJoinedStaff
     *     The date_joined_staff
     */
    @JsonProperty("date_joined_staff")
    public void setDateJoinedStaff(String dateJoinedStaff) {
        this.dateJoinedStaff = dateJoinedStaff;
    }

    /**
     * 
     * @return
     *     The firstName
     */
    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The first_name
     */
    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @return
     *     The fundingSource
     */
    @JsonProperty("funding_source")
    public String getFundingSource() {
        return fundingSource;
    }

    /**
     * 
     * @param fundingSource
     *     The funding_source
     */
    @JsonProperty("funding_source")
    public void setFundingSource(String fundingSource) {
        this.fundingSource = fundingSource;
    }

    /**
     * 
     * @return
     *     The gender
     */
    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    /**
     * 
     * @param gender
     *     The gender
     */
    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 
     * @return
     *     The hrStatusCode
     */
    @JsonProperty("hr_status_code")
    public String getHrStatusCode() {
        return hrStatusCode;
    }

    /**
     * 
     * @param hrStatusCode
     *     The hr_status_code
     */
    @JsonProperty("hr_status_code")
    public void setHrStatusCode(String hrStatusCode) {
        this.hrStatusCode = hrStatusCode;
    }

    /**
     * 
     * @return
     *     The isStaff
     */
    @JsonProperty("is_staff")
    public Boolean getIsStaff() {
        return isStaff;
    }

    /**
     * 
     * @param isStaff
     *     The is_staff
     */
    @JsonProperty("is_staff")
    public void setIsStaff(Boolean isStaff) {
        this.isStaff = isStaff;
    }

    /**
     * 
     * @return
     *     The lakeHartMailCode
     */
    @JsonProperty("lake_hart_mail_code")
    public String getLakeHartMailCode() {
        return lakeHartMailCode;
    }

    /**
     * 
     * @param lakeHartMailCode
     *     The lake_hart_mail_code
     */
    @JsonProperty("lake_hart_mail_code")
    public void setLakeHartMailCode(String lakeHartMailCode) {
        this.lakeHartMailCode = lakeHartMailCode;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The last_name
     */
    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 
     * @return
     *     The locationCode
     */
    @JsonProperty("location_code")
    public String getLocationCode() {
        return locationCode;
    }

    /**
     * 
     * @param locationCode
     *     The location_code
     */
    @JsonProperty("location_code")
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    /**
     * 
     * @return
     *     The locationWork
     */
    @JsonProperty("location_work")
    public String getLocationWork() {
        return locationWork;
    }

    /**
     * 
     * @param locationWork
     *     The location_work
     */
    @JsonProperty("location_work")
    public void setLocationWork(String locationWork) {
        this.locationWork = locationWork;
    }

    /**
     * 
     * @return
     *     The maritalStatus
     */
    @JsonProperty("marital_status")
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * 
     * @param maritalStatus
     *     The marital_status
     */
    @JsonProperty("marital_status")
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * 
     * @return
     *     The middleName
     */
    @JsonProperty("middle_name")
    public String getMiddleName() {
        return middleName;
    }

    /**
     * 
     * @param middleName
     *     The middle_name
     */
    @JsonProperty("middle_name")
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * 
     * @return
     *     The nameAddressEditFlag
     */
    @JsonProperty("name_address_edit_flag")
    public String getNameAddressEditFlag() {
        return nameAddressEditFlag;
    }

    /**
     * 
     * @param nameAddressEditFlag
     *     The name_address_edit_flag
     */
    @JsonProperty("name_address_edit_flag")
    public void setNameAddressEditFlag(String nameAddressEditFlag) {
        this.nameAddressEditFlag = nameAddressEditFlag;
    }

    /**
     * 
     * @return
     *     The originalHireDate
     */
    @JsonProperty("original_hire_date")
    public String getOriginalHireDate() {
        return originalHireDate;
    }

    /**
     * 
     * @param originalHireDate
     *     The original_hire_date
     */
    @JsonProperty("original_hire_date")
    public void setOriginalHireDate(String originalHireDate) {
        this.originalHireDate = originalHireDate;
    }

    /**
     * 
     * @return
     *     The paygroup
     */
    @JsonProperty("paygroup")
    public String getPaygroup() {
        return paygroup;
    }

    /**
     * 
     * @param paygroup
     *     The paygroup
     */
    @JsonProperty("paygroup")
    public void setPaygroup(String paygroup) {
        this.paygroup = paygroup;
    }

    /**
     * 
     * @return
     *     The preferredName
     */
    @JsonProperty("preferred_name")
    public String getPreferredName() {
        return preferredName;
    }

    /**
     * 
     * @param preferredName
     *     The preferred_name
     */
    @JsonProperty("preferred_name")
    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    /**
     * 
     * @return
     *     The ministryCode
     */
    @JsonProperty("ministry_code")
    public String getMinistryCode() {
        return ministryCode;
    }

    /**
     * 
     * @param ministryCode
     *     The ministry_code
     */
    @JsonProperty("ministry_code")
    public void setMinistryCode(String ministryCode) {
        this.ministryCode = ministryCode;
    }

    /**
     * 
     * @return
     *     The ministryDescr
     */
    @JsonProperty("ministry_descr")
    public String getMinistryDescr() {
        return ministryDescr;
    }

    /**
     * 
     * @param ministryDescr
     *     The ministry_descr
     */
    @JsonProperty("ministry_descr")
    public void setMinistryDescr(String ministryDescr) {
        this.ministryDescr = ministryDescr;
    }

    /**
     * 
     * @return
     *     The suffix
     */
    @JsonProperty("suffix")
    public String getSuffix() {
        return suffix;
    }

    /**
     * 
     * @param suffix
     *     The suffix
     */
    @JsonProperty("suffix")
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * 
     * @return
     *     The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The subMinistryCode
     */
    @JsonProperty("sub_ministry_code")
    public String getSubMinistryCode() {
        return subMinistryCode;
    }

    /**
     * 
     * @param subMinistryCode
     *     The sub_ministry_code
     */
    @JsonProperty("sub_ministry_code")
    public void setSubMinistryCode(String subMinistryCode) {
        this.subMinistryCode = subMinistryCode;
    }

    /**
     * 
     * @return
     *     The subMinistryDescr
     */
    @JsonProperty("sub_ministry_descr")
    public String getSubMinistryDescr() {
        return subMinistryDescr;
    }

    /**
     * 
     * @param subMinistryDescr
     *     The sub_ministry_descr
     */
    @JsonProperty("sub_ministry_descr")
    public void setSubMinistryDescr(String subMinistryDescr) {
        this.subMinistryDescr = subMinistryDescr;
    }

    /**
     * 
     * @return
     *     The emailAddress
     */
    @JsonProperty("email_address")
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    /**
     * 
     * @param emailAddress
     *     The email_address
     */
    @JsonProperty("email_address")
    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * 
     * @return
     *     The phoneNumber
     */
    @JsonProperty("phone_number")
    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 
     * @param phoneNumber
     *     The phone_number
     */
    @JsonProperty("phone_number")
    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 
     * @return
     *     The authentication
     */
    @JsonProperty("authentication")
    public Authentication getAuthentication() {
        return authentication;
    }

    /**
     * 
     * @param authentication
     *     The authentication
     */
    @JsonProperty("authentication")
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    /**
     * 
     * @return
     *     The address
     */
    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    @JsonProperty("address")
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The birthYear
     */
    @JsonProperty("birth_year")
    public Integer getBirthYear() {
        return birthYear;
    }

    /**
     * 
     * @param birthYear
     *     The birth_year
     */
    @JsonProperty("birth_year")
    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    /**
     * 
     * @return
     *     The birthDate
     */
    @JsonProperty("birth_date")
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * 
     * @param birthDate
     *     The birth_date
     */
    @JsonProperty("birth_date")
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * 
     * @return
     *     The countryOfResidence
     */
    @JsonProperty("country_of_residence")
    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    /**
     * 
     * @param countryOfResidence
     *     The country_of_residence
     */
    @JsonProperty("country_of_residence")
    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    /**
     * 
     * @return
     *     The employmentCountry
     */
    @JsonProperty("employment_country")
    public String getEmploymentCountry() {
        return employmentCountry;
    }

    /**
     * 
     * @param employmentCountry
     *     The employment_country
     */
    @JsonProperty("employment_country")
    public void setEmploymentCountry(String employmentCountry) {
        this.employmentCountry = employmentCountry;
    }

    /**
     * 
     * @return
     *     The language
     */
    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    /**
     * 
     * @param language
     *     The language
     */
    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * 
     * @return
     *     The jobStatus
     */
    @JsonProperty("job_status")
    public String getJobStatus() {
        return jobStatus;
    }

    /**
     * 
     * @param jobStatus
     *     The job_status
     */
    @JsonProperty("job_status")
    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * 
     * @return
     *     The deptCode
     */
    @JsonProperty("dept_code")
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * 
     * @param deptCode
     *     The dept_code
     */
    @JsonProperty("dept_code")
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    /**
     * 
     * @return
     *     The deptDescr
     */
    @JsonProperty("dept_descr")
    public String getDeptDescr() {
        return deptDescr;
    }

    /**
     * 
     * @param deptDescr
     *     The dept_descr
     */
    @JsonProperty("dept_descr")
    public void setDeptDescr(String deptDescr) {
        this.deptDescr = deptDescr;
    }

    /**
     * 
     * @return
     *     The jobCode
     */
    @JsonProperty("job_code")
    public String getJobCode() {
        return jobCode;
    }

    /**
     * 
     * @param jobCode
     *     The job_code
     */
    @JsonProperty("job_code")
    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    /**
     * 
     * @return
     *     The jobDescr
     */
    @JsonProperty("job_descr")
    public String getJobDescr() {
        return jobDescr;
    }

    /**
     * 
     * @param jobDescr
     *     The job_descr
     */
    @JsonProperty("job_descr")
    public void setJobDescr(String jobDescr) {
        this.jobDescr = jobDescr;
    }

    /**
     * 
     * @return
     *     The supervisorEmplid
     */
    @JsonProperty("supervisor_emplid")
    public String getSupervisorEmplid() {
        return supervisorEmplid;
    }

    /**
     * 
     * @param supervisorEmplid
     *     The supervisor_emplid
     */
    @JsonProperty("supervisor_emplid")
    public void setSupervisorEmplid(String supervisorEmplid) {
        this.supervisorEmplid = supervisorEmplid;
    }

    /**
     * 
     * @return
     *     The clientUpdatedAt
     */
    @JsonProperty("client_updated_at")
    public String getClientUpdatedAt() {
        return clientUpdatedAt;
    }

    /**
     * 
     * @param clientUpdatedAt
     *     The client_updated_at
     */
    @JsonProperty("client_updated_at")
    public void setClientUpdatedAt(String clientUpdatedAt) {
        this.clientUpdatedAt = clientUpdatedAt;
    }

    /**
     * 
     * @return
     *     The clientIntegrationId
     */
    @JsonProperty("client_integration_id")
    public String getClientIntegrationId() {
        return clientIntegrationId;
    }

    /**
     * 
     * @param clientIntegrationId
     *     The client_integration_id
     */
    @JsonProperty("client_integration_id")
    public void setClientIntegrationId(String clientIntegrationId) {
        this.clientIntegrationId = clientIntegrationId;
    }

    @Override
    public String toString() {
        return new StringBuilder("Person{")
            .append("id='").append(id).append('\'')
            .append(", accountNumber='").append(accountNumber).append('\'')
            .append(", activeStatus='").append(activeStatus).append('\'')
            .append(", birthDay=").append(birthDay)
            .append(", birthMonth=").append(birthMonth)
            .append(", cruEmployee=").append(cruEmployee)
            .append(", dateJoinedStaff='").append(dateJoinedStaff).append('\'')
            .append(", firstName='").append(firstName).append('\'')
            .append(", fundingSource='").append(fundingSource).append('\'')
            .append(", gender='").append(gender).append('\'')
            .append(", hrStatusCode='").append(hrStatusCode).append('\'')
            .append(", isStaff=").append(isStaff)
            .append(", lakeHartMailCode='").append(lakeHartMailCode).append('\'')
            .append(", lastName='").append(lastName).append('\'')
            .append(", locationCode='").append(locationCode).append('\'')
            .append(", locationWork='").append(locationWork).append('\'')
            .append(", maritalStatus='").append(maritalStatus).append('\'')
            .append(", middleName='").append(middleName).append('\'')
            .append(", nameAddressEditFlag='").append(nameAddressEditFlag).append('\'')
            .append(", originalHireDate='").append(originalHireDate).append('\'')
            .append(", paygroup='").append(paygroup).append('\'')
            .append(", preferredName='").append(preferredName).append('\'')
            .append(", ministryCode='").append(ministryCode).append('\'')
            .append(", ministryDescr='").append(ministryDescr).append('\'')
            .append(", suffix='").append(suffix).append('\'')
            .append(", title='").append(title).append('\'')
            .append(", subMinistryCode='").append(subMinistryCode).append('\'')
            .append(", subMinistryDescr='").append(subMinistryDescr).append('\'')
            .append(", emailAddress=").append(emailAddress)
            .append(", phoneNumber=").append(phoneNumber)
            .append(", authentication=").append(authentication)
            .append(", address=").append(address)
            .append(", birthYear=").append(birthYear)
            .append(", birthDate='").append(birthDate).append('\'')
            .append(", countryOfResidence='").append(countryOfResidence).append('\'')
            .append(", employmentCountry='").append(employmentCountry).append('\'')
            .append(", language='").append(language).append('\'')
            .append(", jobStatus='").append(jobStatus).append('\'')
            .append(", deptCode='").append(deptCode).append('\'')
            .append(", deptDescr='").append(deptDescr).append('\'')
            .append(", jobCode='").append(jobCode).append('\'')
            .append(", jobDescr='").append(jobDescr).append('\'')
            .append(", supervisorEmplid='").append(supervisorEmplid).append('\'')
            .append(", clientUpdatedAt='").append(clientUpdatedAt).append('\'')
            .append(", clientIntegrationId='").append(clientIntegrationId).append('\'')
            .append('}').toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(accountNumber, person.accountNumber) &&
                Objects.equals(activeStatus, person.activeStatus) &&
                Objects.equals(birthDay, person.birthDay) &&
                Objects.equals(birthMonth, person.birthMonth) &&
                Objects.equals(cruEmployee, person.cruEmployee) &&
                Objects.equals(dateJoinedStaff, person.dateJoinedStaff) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(fundingSource, person.fundingSource) &&
                Objects.equals(gender, person.gender) &&
                Objects.equals(hrStatusCode, person.hrStatusCode) &&
                Objects.equals(isStaff, person.isStaff) &&
                Objects.equals(lakeHartMailCode, person.lakeHartMailCode) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(locationCode, person.locationCode) &&
                Objects.equals(locationWork, person.locationWork) &&
                Objects.equals(maritalStatus, person.maritalStatus) &&
                Objects.equals(middleName, person.middleName) &&
                Objects.equals(nameAddressEditFlag, person.nameAddressEditFlag) &&
                Objects.equals(originalHireDate, person.originalHireDate) &&
                Objects.equals(paygroup, person.paygroup) &&
                Objects.equals(preferredName, person.preferredName) &&
                Objects.equals(ministryCode, person.ministryCode) &&
                Objects.equals(ministryDescr, person.ministryDescr) &&
                Objects.equals(suffix, person.suffix) &&
                Objects.equals(title, person.title) &&
                Objects.equals(subMinistryCode, person.subMinistryCode) &&
                Objects.equals(subMinistryDescr, person.subMinistryDescr) &&
                Objects.equals(emailAddress, person.emailAddress) &&
                Objects.equals(phoneNumber, person.phoneNumber) &&
                Objects.equals(authentication, person.authentication) &&
                Objects.equals(address, person.address) &&
                Objects.equals(birthYear, person.birthYear) &&
                Objects.equals(birthDate, person.birthDate) &&
                Objects.equals(countryOfResidence, person.countryOfResidence) &&
                Objects.equals(employmentCountry, person.employmentCountry) &&
                Objects.equals(language, person.language) &&
                Objects.equals(jobStatus, person.jobStatus) &&
                Objects.equals(deptCode, person.deptCode) &&
                Objects.equals(deptDescr, person.deptDescr) &&
                Objects.equals(jobCode, person.jobCode) &&
                Objects.equals(jobDescr, person.jobDescr) &&
                Objects.equals(supervisorEmplid, person.supervisorEmplid) &&
                Objects.equals(clientUpdatedAt, person.clientUpdatedAt) &&
                Objects.equals(clientIntegrationId, person.clientIntegrationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, activeStatus, birthDay, birthMonth, cruEmployee, dateJoinedStaff, firstName, fundingSource, gender, hrStatusCode, isStaff, lakeHartMailCode, lastName, locationCode, locationWork, maritalStatus, middleName, nameAddressEditFlag, originalHireDate, paygroup, preferredName, ministryCode, ministryDescr, suffix, title, subMinistryCode, subMinistryDescr, emailAddress, phoneNumber, authentication, address, birthYear, birthDate, countryOfResidence, employmentCountry, language, jobStatus, deptCode, deptDescr, jobCode, jobDescr, supervisorEmplid, clientUpdatedAt, clientIntegrationId);
    }
}
