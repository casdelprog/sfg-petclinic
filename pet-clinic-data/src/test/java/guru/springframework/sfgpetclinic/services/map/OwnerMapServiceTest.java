package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    final long ownerId = 1L;
    final String lastName = "Smith";
    OwnerMapService service;

    @BeforeEach
    void setUp() {
        service = new OwnerMapService(new PetTypeMapService(), new PetMapService());

        service.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet= service.findAll();
        assertEquals(ownerId, ownerSet.size());
    }

    @Test
    void deleteById() {
        service.deleteById(ownerId);
        assertEquals(0, service.findAll().size());
    }

    @Test
    void delete() {
        service.delete(service.findById(ownerId));
        assertEquals(0, service.findAll().size());
    }

    @Test
    void saveExistingId() {
        long id = 2l;
        Owner owner2 = Owner.builder().id(id).build();

        Owner savedOwner = service.save(owner2);

        assertEquals(id, savedOwner.getId());
    }

    @Test
    void saveNoId() {

        Owner savedOwner = service.save(Owner.builder().build());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());

    }

    @Test
    void findById() {
        Owner owner = service.findById(ownerId);
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void findByLastName() {
        Owner smith = service.findByLastName(lastName);

        assertNotNull(smith);
        assertEquals(ownerId, smith.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner smith = service.findByLastName("foo");
        assertNull(smith);
    }
}