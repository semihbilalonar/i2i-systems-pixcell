package com.i2iintern.AOM_demo.Controllers;

import com.i2iintern.AOM_demo.Entities.CustomerEntity;
import com.i2iintern.AOM_demo.Entities.Login;
import com.i2iintern.AOM_demo.Repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/customer")
@CrossOrigin
public class CustomerController {
    private CustomerRepository customerRepository = new CustomerRepository();

    @GetMapping("/getAllCustomers")
    public List<CustomerEntity> getAll() throws ClassNotFoundException, SQLException {
        return customerRepository.getAllCustomers();
    }

    @GetMapping("/getCustomerByMsisdn/{MSISDN}")
    public List<CustomerEntity> getCustomerByMsisdn(@PathVariable String MSISDN) throws SQLException, ClassNotFoundException {
        return customerRepository.getCustomerByMsisdn(MSISDN);
    }



}

