package com.example.tp5.controller;

import com.example.tp5.model.Address;
import com.example.tp5.model.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Calendar;


@Controller
public class NewAddress {
@GetMapping("/NewAddress")
    public String newAddress(Model model){
    Address address=new Address();
    model.addAttribute("address",address);
     return "address";
    }
    @Autowired
    AddressRepository addressRepository;
    @PostMapping("/NewAddress")
    public String addNewAddress( Model model){
        model.addAttribute("allAddresses", addressRepository.findAll());

        return "addresses";
    }
}
