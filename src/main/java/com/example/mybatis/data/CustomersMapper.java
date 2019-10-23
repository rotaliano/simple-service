package com.example.mybatis.data;

import com.example.model.Customers;

public interface CustomersMapper {

    Customers selectCustomers(int customers_id);
}
