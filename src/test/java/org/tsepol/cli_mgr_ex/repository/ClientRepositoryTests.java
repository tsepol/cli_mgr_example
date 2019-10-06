package org.tsepol.cli_mgr_ex.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.tsepol.cli_mgr_ex.models.Client;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

// CATEGORIA SAVE

    @Test
    public void saveValidClient() {
        Client client = new Client();
        client.setNif("123456789");
        client = entityManager.persistAndFlush(client);
        //TODO ALL OTHER ATTRIBUTES
        assertEquals(clientRepository.findByNif(client.getNif()).getNif(),client.getNif());
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveWrongRangePhoneClient_Longer()  {
        Client client = new Client();
        client.setNif("123456789");
        client.setPhone("1234567890");
        entityManager.persistAndFlush(client);
        assert false;
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveWrongRangePhoneClient_Shorter()  {
        Client client = new Client();
        client.setNif("123456789");
        client.setPhone("23456789");
        entityManager.persistAndFlush(client);
        assert false;
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveWrongRangeNIFClient_Longer()  {
        Client client = new Client();
        client.setNif("0123456789");
        entityManager.persistAndFlush(client);
        assert false;
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveWrongRangeNIFClient_Shorter()  {
        Client client = new Client();
        client.setNif("23456789");
        entityManager.persistAndFlush(client);
        assert false;
    }

    //Test Escape Characters
    @Test(expected = ConstraintViolationException.class)
    public void saveInvalidCharAddress(){
        Client client = new Client();
        client.setNif("123456789");
        client.setAddress("Praceta Vasco da Gama, \" \\ \\\\\\root");
        entityManager.persistAndFlush(client);
        assert false;
    }

    //Test Escape Characters
    @Test(expected = ConstraintViolationException.class)
    public void saveInvalidCharName1(){
        Client client = new Client();
        client.setNif("123456789");
        client.setName("Praceta Vasco da Gama\" \\ \\\\\\root");
        entityManager.persistAndFlush(client);
        assert false;
    }

    //Test Numbers
    @Test(expected = ConstraintViolationException.class)
    public void saveInvalidCharName2(){
        Client client = new Client();
        client.setNif("123456789");
        client.setName("Vasco da Gama09");
        entityManager.persistAndFlush(client);
        assert false;
    }

//   CATEGORIA UPDATES

    @Test
    public void updateClientName() {
        Client client = new Client();
        client.setNif("123456789");
        client.setName("Tiago Lopes");
        assertEquals(client.getName(),"Tiago Lopes");
        entityManager.persistAndFlush(client);
        client.setName("Jorge Lopes");
        entityManager.persistAndFlush(client);
        assertEquals(client.getName(),"Jorge Lopes");
    }

    @Test
    public void updateClientPhone() {
        Client client = new Client();
        client.setNif("123456789");
        client.setPhone("123456789");
        assertEquals(client.getPhone(),"123456789");
        client = entityManager.persistAndFlush(client);
        client.setPhone("123456799");
        client = entityManager.persistAndFlush(client);
        assertEquals(client.getPhone(),"123456799");
    }

    @Test
    public void updateClientAddress() {
        Client client = new Client();
        client.setNif("123456789");
        client.setAddress("Tiago Lopes");
        assertEquals(client.getAddress(),"Tiago Lopes");
        client = entityManager.persistAndFlush(client);
        client.setAddress("123456778");
        entityManager.persistAndFlush(client);
        assertEquals(client.getAddress(),"123456778");
    }

    @Test(expected = PersistenceException.class)
    public void updateClientNIF() {
        Client client = new Client();
        client.setNif("123456789");
        client.setName("Tiago Lopes");
        client = entityManager.persistAndFlush(client);
        client.setNif("123456778");
        entityManager.persistAndFlush(client);
    }

    // GET CLIENTS

    @Test
    public void getAllClients() {
        Client client = new Client();
        client.setNif("123456789");
        entityManager.persistAndFlush(client);
        client = new Client();
        client.setNif("123456788");
        entityManager.persistAndFlush(client);
        client = new Client();
        client.setNif("123456787");
        entityManager.persistAndFlush(client);
        List<Client> result = clientRepository.findAll();
        assertEquals(result.size(),3);
    }

    @Test
    public void getClientsWithExactName() {
        Client client = new Client();
        client.setNif("123456789");
        client.setName("Tiago Lopes");
        entityManager.persistAndFlush(client);
        client = new Client();
        client.setNif("123456788");
        client.setName("Pedro Lopes");
        client = new Client();
        client.setNif("123456787");
        client.setName("Tiago Lopes");
        entityManager.persistAndFlush(client);
        List<Client> result = clientRepository.findByName("Tiago Lopes");
        assertEquals(result.size(),2);
    }

    public void getClientsContainsName(){
        Client client = new Client();
        client.setNif("123456789");
        client.setName("Tiago Lopes");
        entityManager.persistAndFlush(client);
        client = new Client();
        client.setNif("123456788");
        client.setName("Pedro Lopes");
        entityManager.persistAndFlush(client);
        List<Client> result = clientRepository.findClientsContainsName("Lopes");
        assertEquals(result.size(),2);

    }

    @Test
    public void getClientByNif() {
        String correct_nif = "123456789";
        Client client = new Client();
        client.setNif(correct_nif);
        client.setName("Tiago Lopes");
        entityManager.persistAndFlush(client);
        Client result = clientRepository.findByNif(correct_nif);
        assertEquals(result.getNif(),correct_nif );
    }

    //DELETE CLIENT

    @Test
    public void deleteClient() {
        //TODO
        assert false;
    }

}