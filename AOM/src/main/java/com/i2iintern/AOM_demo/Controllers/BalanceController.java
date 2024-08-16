package com.i2iintern.AOM_demo.Controllers;

import com.i2iintern.AOM_demo.Connecters.VoltDbWrapper;
import com.i2iintern.AOM_demo.Entities.BalanceEntity;
import com.i2iintern.AOM_demo.Entities.UserBalanceEntity;
import com.i2iintern.AOM_demo.Model.CustomerBalance;
import com.i2iintern.AOM_demo.Repository.BalanceRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voltdb.client.NoConnectionsException;
import org.voltdb.client.ProcCallException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/api/balance")
@CrossOrigin
public class BalanceController {
    private BalanceRepository balanceRepository = new BalanceRepository();
    private VoltDbWrapper voltDbWrapper = new VoltDbWrapper();


    @GetMapping("/getAllBalances")
    public List<BalanceEntity> getAllBalances() throws ClassNotFoundException, SQLException {
        return balanceRepository.getAllBalances();
    }

    @GetMapping("/getRemainingCustomerBalanceByMsisdn/{MSISDN}")
    public ResponseEntity<CustomerBalance> getRemainingCustomerBalanceByMsisdn(@PathVariable Long MSISDN) {
        try {
            CustomerBalance balance = new CustomerBalance(MSISDN,
                    voltDbWrapper.getInternetBalance(MSISDN),
                    voltDbWrapper.getSmsBalance(MSISDN),
                    voltDbWrapper.getMinutesBalance(MSISDN));
            return new ResponseEntity<>(balance, HttpStatus.OK);

    } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }




}
