package data.repository;

import org.junit.Before;
import org.junit.Test;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.repository.InMemoryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class InMemoryRepositoryTest {
    private InMemoryRepository<SupplierEntity>  repository;

    @Before
    public void setUp() {
        repository = new InMemoryRepository<>();

        repository.importData(List.of(
                new SupplierEntity("Supplier A", "123-456-7890", "supplier_a@email.com"),
                new SupplierEntity("Supplier B", "987-654-3210", "supplier_b@email.com"),
                new SupplierEntity("Supplier C", "555-555-5555", "supplier_c@email.com")));

    }

    @Test
    public void testFindOne() {
        HashMap<String, Object> query = new HashMap<>();
        query.put("name", "Supplier A");

        SupplierEntity supplier = repository.findOne(query);

        assertEquals("Supplier A", supplier.getName());
        assertEquals("123-456-7890", supplier.getPhoneNumber());
        assertEquals("supplier_a@email.com", supplier.getEmail());
    }

    @Test
    public void testFindOneReturnsNull() {
        HashMap<String, Object> query = new HashMap<>();
        query.put("name", "Nonexistent Supplier");

        SupplierEntity supplier = repository.findOne(query);

        assertNull("Supplier should not exist", supplier);
    }

    @Test
    public void testAddSupplier() {
        SupplierEntity newSupplier = new SupplierEntity("Supplier D", "123-456-7890", "supplier_d@email.com");

        boolean created = repository.create(newSupplier);
        assertTrue("New supplier should be added successfully", created);


        HashMap<String, Object> query = new HashMap<>();
        query.put("name", "Supplier D");

        SupplierEntity supplier = repository.findOne(query);

        assertEquals("Supplier D", supplier.getName());
    }

    @Test
    public void testDeleteSupplier() {

        SupplierEntity newSupplier = new SupplierEntity("Supplier D", "123-456-7890", "supplier_d@email.com");
        repository.create(newSupplier);

        HashMap<String, Object> query = new HashMap<>();
        query.put("name", "Supplier D");

        boolean deleted = repository.deleteOne(query);
        assertTrue("Supplier D should be deleted successfully", deleted);

        SupplierEntity supplier = repository.findOne(query);
        assertNull("Supplier D should no longer exist", supplier);
    }

    @Test
    public void testDeleteSupplierReturnsFalse(){
        HashMap<String, Object> query = new HashMap<>();
        query.put("name", "Nonexistent Supplier");
        assertFalse("Deleting a nonexistent supplier should return false", repository.deleteOne(query));
    }

    @Test
    public void testFindMany() {
        SupplierEntity newSupplier1 = new SupplierEntity("Supplier Z", "123-456-7890", "supplier_d@email.com");
        SupplierEntity newSupplier2 = new SupplierEntity("Supplier Z", "123-456-7890", "supplier_d@email.com");
        SupplierEntity newSupplier3 = new SupplierEntity("Supplier Z", "123-456-7890", "supplier_d@email.com");

        repository.create(newSupplier1);
        repository.create(newSupplier2);
        repository.create(newSupplier3);


        HashMap<String, Object> query = new HashMap<>();
        query.put("name", "Supplier Z");

        Stream<SupplierEntity> suppliers = repository.findMany(query);
        assertEquals(3, suppliers.count());

        assertEquals(
                "List should equal all Supplier Z entries",
                repository.findMany(query).toList(), List.of(
                        newSupplier1,
                        newSupplier2,
                        newSupplier3
                )
        );

    }

    @Test
    public void testFindManyReturnsEmptyStream() {
        HashMap<String, Object> query = new HashMap<>();
        query.put("name", "Nonexistent Supplier");

        Stream<SupplierEntity> suppliers = repository.findMany(query);
        assertEquals(0, suppliers.count());
    }

    @Test
    public void testDeleteMany(){
        SupplierEntity newSupplier1 = new SupplierEntity("Supplier Z",  "123-456-7890", "supplier_d@email.com");
        SupplierEntity newSupplier2 = new SupplierEntity("Supplier Z",  "123-456-7890", "supplier_d@email.com");
        SupplierEntity newSupplier3 = new SupplierEntity("Supplier Z", "123-456-7890", "supplier_d@email.com");

        repository.create(newSupplier1);
        repository.create(newSupplier2);
        repository.create(newSupplier3);


        HashMap<String, Object> query = new HashMap<>();
        query.put("name", "Supplier Z");

        int deletedCount = repository.deleteMany(query);
        assertEquals(3, deletedCount);

        Stream<SupplierEntity> suppliers = repository.findMany(query);
        assertEquals(0, suppliers.count());
    }

    @Test
    public void testDeleteManyReturnsZero(){
        HashMap<String, Object> query = new HashMap<>();
        query.put("name", "Nonexistent Supplier");
        assertEquals(0, repository.deleteMany(query));
    }

    @Test
    public void testFindAll() {
        Stream<SupplierEntity> suppliers = repository.findAll();
        assertEquals("Supplier list should not be empty", 3, suppliers.count());
    }
}