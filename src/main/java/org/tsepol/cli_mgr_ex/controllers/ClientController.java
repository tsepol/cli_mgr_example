package org.tsepol.cli_mgr_ex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tsepol.cli_mgr_ex.exceptions.ResourceAlreadyExistsException;
import org.tsepol.cli_mgr_ex.exceptions.ResourceNotFoundException;
import org.tsepol.cli_mgr_ex.models.Client;
import org.tsepol.cli_mgr_ex.repository.ClientRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/list")
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    @GetMapping("/search")
    public List<Client> getClientsWithName(@RequestParam String name, @RequestParam(required = false) Boolean exact){
        //default find not exact match (contains
        if( exact == null || exact ){
            return clientRepository.findClientsContainsName(name);
        }
        return clientRepository.findByName(name);
    }

    @GetMapping("/get/{nif}")
    public Client getClientByNif(@PathVariable String nif) {
        return clientRepository.findByNif(nif);
    }

    @PostMapping("/add")
    public Client addClient(@RequestParam Boolean upsert, @Valid @RequestBody Client client) {
        //default upsert disabled
        if( ( upsert == null || !upsert ) && clientRepository.existsById(client.getNif())) {
            throw new ResourceAlreadyExistsException("Client with NIF "+client.getNif()+" already exists");
        }
        return clientRepository.save(client);
    }

    @PutMapping("/update")
    public Client updateClient( @Valid @RequestBody Client client) {
        if(!clientRepository.existsById(client.getNif())) {
            throw new ResourceNotFoundException("Client not found with id " + client.getNif());
        }
        return clientRepository.save(client);
    }

    @DeleteMapping("/delete/{clientId}")
    public String deleteAnswer(@PathVariable String clientId) {
        if(!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("Question not found with id " + clientId);
        }

        clientRepository.delete(clientRepository.findByNif(clientId));

        return clientId+" was Deleted";
    }

}
