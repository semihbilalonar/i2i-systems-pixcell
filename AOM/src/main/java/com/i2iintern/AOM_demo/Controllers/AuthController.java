package com.i2iintern.AOM_demo.Controllers;

//import com.i2iintern.AOM_demo.Configuration.HazelcastMWOperation;
import com.i2iintern.AOM_demo.Connecters.VoltDbWrapper;
import com.i2iintern.AOM_demo.Entities.CustomerEntity;
import com.i2iintern.AOM_demo.Entities.Login;
import com.i2iintern.AOM_demo.Repository.BalanceRepository;
import com.i2iintern.AOM_demo.Repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private CustomerRepository customerRepository = new CustomerRepository();
    private BalanceRepository balanceRepository = new BalanceRepository();
    private VoltDbWrapper voltDbWrapper = new VoltDbWrapper();

    @GetMapping("login/{MSISDN}/{PASSWORD}")
    public ResponseEntity loginCheck(@PathVariable String MSISDN, @PathVariable String PASSWORD) throws SQLException, ClassNotFoundException {
        Login login = new Login(MSISDN, PASSWORD);
        return customerRepository.login(login);
    }

    @PostMapping("/registerWithPackage")
    public ResponseEntity<String> registerCustomerWithPackage(@RequestBody Map<String, String> registerCustomerRequestBody) {
        String MSISDN = registerCustomerRequestBody.get("MSISDN");
        String NAME = registerCustomerRequestBody.get("NAME");
        String SURNAME = registerCustomerRequestBody.get("SURNAME");
        String EMAIL = registerCustomerRequestBody.get("EMAIL");
        String PASSWORD = registerCustomerRequestBody.get("PASSWORD");
        String SECURITY_KEY = registerCustomerRequestBody.get("SECURITY_KEY");
        String PACKAGE_ID = registerCustomerRequestBody.get("PACKAGE_ID");

        // Boş veya null değerleri kontrol et
        if (MSISDN == null || NAME == null || SURNAME == null || EMAIL == null || PASSWORD == null || SECURITY_KEY == null || PACKAGE_ID == null) {
            return new ResponseEntity<>("All fields are required.", HttpStatus.BAD_REQUEST);
        }

        try {
            CustomerEntity newCustomer = new CustomerEntity();
            newCustomer.setMsisdn(Long.parseLong(MSISDN));
            newCustomer.setName(NAME);
            newCustomer.setSurname(SURNAME);
            newCustomer.setEmail(EMAIL);
            newCustomer.setPassword(PASSWORD);
            newCustomer.setSecurity_key(SECURITY_KEY);
            newCustomer.setPackageId(Long.parseLong(PACKAGE_ID));


            voltDbWrapper.InsertCustomerWithPackage(Long.parseLong(MSISDN),NAME
                    ,SURNAME,EMAIL,PASSWORD,"A",SECURITY_KEY, Integer.parseInt(PACKAGE_ID));



            //hazelcastMWOperation.put(MSISDN,"test");


            return customerRepository.createCustomerWithPackage(newCustomer);

        } catch (NumberFormatException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Customer registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/forgotPassword/{MSISDN}/{securityKey}")
    public ResponseEntity<String> forgotPassword(@PathVariable long MSISDN, @PathVariable String securityKey) {
        try {
            String storedSecurityKey = voltDbWrapper.getUserSecurityKey(MSISDN);

            if (storedSecurityKey != null && storedSecurityKey.equals(securityKey)) {
                return ResponseEntity.ok("Successful login."+MSISDN);
            } else {
                return ResponseEntity.badRequest().body("Invalid security key!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error while processing the request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    }





