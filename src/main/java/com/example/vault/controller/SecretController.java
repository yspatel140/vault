package com.example.vault.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SecretController {

    @Autowired
    private VaultTemplate vaultTemplate;

    @RequestMapping(value = "fetch", method= RequestMethod.GET)
    public String fetchFromVault() {
        VaultResponse vaultResponse = vaultTemplate.read("secret/data/springdemo");
        return "Data from vault --> " + vaultResponse.getData() ;
    }

    @RequestMapping(value = "store", method= RequestMethod.GET)
    public String storeIntoVault() {
        Map<String, String> map = new HashMap<>();
        map.put("password","pwd");
        map.put("username","uname");

        VaultKeyValueOperations keyValue = vaultTemplate.opsForKeyValue("secret", VaultKeyValueOperationsSupport.KeyValueBackend.versioned());
        keyValue.put("springdemo", map);

        return "Data stored into vault";
    }
}
