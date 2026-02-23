package com.example.livapp.model.customer;

import com.example.livapp.model.types.CustomerType;
import org.springframework.data.domain.Page;

public class CustomerDTO {

    private long id;
    private String name;
    private String region;
    private String ownerName;
    private Double latitude;
    private Double longitude;
    private Double totalRevenue ;
    private Double totalDebt;
    private String customerType ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Double getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(Double totalDebt) {
        this.totalDebt = totalDebt;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public static CustomerDTO fromEntity(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getCommune()+ ","+customer.getWilaya(),
                customer.getOwnerName(),
                customer.getLatitude(),
                customer.getLongitude(),
                customer.getTotalRevenue(),
                customer.getTotalDebt(),
                customer.getCustomerType()
        );
    }


    public static Page<CustomerDTO> toDtoPage(Page<Customer> customerPage) {
        return customerPage.map(customer -> new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getCommune()+ ","+customer.getWilaya(),
                customer.getOwnerName(),
                customer.getLatitude(),
                customer.getLongitude(),
                customer.getTotalRevenue(),
                customer.getTotalDebt(),
                customer.getCustomerType()
        ));

    }

    public CustomerDTO(long id, String name, String region, String ownerName, Double latitude, Double longitude, Double totalRevenue, Double totalDebt, String customerType) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.ownerName = ownerName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalRevenue = totalRevenue;
        this.totalDebt = totalDebt;
        this.customerType = customerType;
    }
}
