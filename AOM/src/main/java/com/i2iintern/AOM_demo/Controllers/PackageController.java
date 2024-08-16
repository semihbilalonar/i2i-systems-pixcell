package com.i2iintern.AOM_demo.Controllers;

import java.io.IOException;

import java.sql.SQLException;

import java.util.List;

import com.i2iintern.AOM_demo.Connecters.VoltDbWrapper;
import com.i2iintern.AOM_demo.Entities.PackageEntity;
import com.i2iintern.AOM_demo.Model.PackageBalance;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.voltdb.client.ProcCallException;


import com.i2iintern.AOM_demo.Repository.PackageRepository;

@RestController
@RequestMapping("/api/package")
@CrossOrigin
public class PackageController {
    private final PackageRepository packageRepository = new PackageRepository();
    private VoltDbWrapper voltDbWrapper = new VoltDbWrapper();

    @GetMapping("/getAllPackages")
    public ResponseEntity<List<PackageBalance>> getAllPackages() {
        try {
            List<PackageBalance> packages = packageRepository.getAllPackages(); // packageRepository'den çağırılıyor
            return new ResponseEntity<>(packages, HttpStatus.OK);
        } catch (IOException | ProcCallException | InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPackageDetailsByMsisdn/{MSISDN}")
    public ResponseEntity<List<PackageBalance>> getPackageDetailsByMsisdn(@PathVariable String MSISDN) {
        try {
            List<PackageBalance> packages = packageRepository.getPackageDetailsByMsisdn(MSISDN);
            return new ResponseEntity<>(packages, HttpStatus.OK);
        } catch (IOException | ProcCallException | InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPackageById/{packageId}")
    public ResponseEntity<PackageBalance> getPackageById(@PathVariable int packageId) {
        try {
            PackageBalance packageBalance = packageRepository.GetPackageById(packageId);
            return new ResponseEntity<>(packageBalance, HttpStatus.OK);
        } catch (IOException | ProcCallException | InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}