package com.example.tp5.controller;

import com.example.tp5.model.Address;
import com.example.tp5.model.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@Controller
public class AddressController {

    @Autowired
    AddressRepository addressRepository;

    @GetMapping("/adresses")
    public String showAddresses(Model model) {
        model.addAttribute("allAddresses", addressRepository.findAll());
        return "addresses";
    }
    /*@PostMapping("/adresses")
    public String addNewAddress(@ModelAttribute Address address, Model model){
        return "addresses";
    }*/

    @RequestMapping(value = "/adresses", method = RequestMethod.POST)
    public String newAddress(@ModelAttribute Address address, Model model){
        //address.setId((long)3);
        address.setCreation(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        System.out.println(address.toString());
        addressRepository.save(address);
        model.addAttribute("allAddresses", addressRepository.findAll());
        return "addresses";
    }

}
