package sk.flowy.importorder.service;

import com.opencsv.exceptions.CsvRuntimeException;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import sk.flowy.importorder.model.*;
import sk.flowy.importorder.repository.ClientRepository;
import sk.flowy.importorder.repository.EanRepository;
import sk.flowy.importorder.repository.OrderProductRepository;
import sk.flowy.importorder.repository.SupplierRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OrderConfirmationDbImportImplTest {

    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private SupplierRepository supplierRepository;
    @MockBean
    private EanRepository eanRepository;
    @MockBean
    private OrderProductRepository orderProductRepository;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private OrderConfirmationDbImportImpl orderConfirmationDbImport;

    @Rule
    public ExpectedException exceptions = ExpectedException.none();

    @Before
    public void setup() {
        this.orderConfirmationDbImport = new OrderConfirmationDbImportImpl(eanRepository, orderProductRepository, supplierRepository, clientRepository);

    }

    @Test(expected = CsvRuntimeException.class)
    public void if_import_file_is_not_csv_file_then_error_should_be_return() {

        orderConfirmationDbImport.importFile(new File("file.txt"));
    }

    @Test
    public void if_import_file_is_empty_then_method_return_false_and_save_into_database_is_not_possible() throws IOException {
        Path filePath = temporaryFolder.newFile("input.csv").toPath();

        assertFalse(orderConfirmationDbImport.importFile(filePath.toFile()));
    }

    @Test(expected = RuntimeException.class)
    public void if_import_file_is_not_empty_and_import_csv_file_have_not_contains_client_ico_then_parsing_csv_file_return_error_message() throws IOException {
        Path filePath = temporaryFolder.newFile("input.csv").toPath();
        File inputCsvFile = filePath.toFile();

        ImportCsvFile importCsvFile = new ImportCsvFile();
        importCsvFile.setSupplierIco(123);
        importCsvFile.setEan("4062800000990");
        importCsvFile.setPrice(5.0);
        importCsvFile.setProductCount(50);
        importCsvFile.setOrderName("Objednavka c. 1");
        FileUtils.writeLines(inputCsvFile, singletonList(importCsvFile));

        orderConfirmationDbImport.importFile(inputCsvFile);
    }

    @Test
    public void if_ean_is_not_found_in_database_then_inport_csv_file_is_not_possible_save_into_database_and_method_return_false() throws IOException {
        Path inputCsvFile = temporaryFolder.newFile("input.csv").toPath();
        when(eanRepository.findByValue(anyString())).thenReturn(null);

        assertFalse(orderConfirmationDbImport.importFile(inputCsvFile.toFile()));
    }

    @Test
    public void if_import_csv_file_is_correct_and_client_ico_and_supplier_ico_was_found_in_database_then_all_data_should_be_save_correct_saved_into_database_and_method_return_true() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File inputCsvFile = new File(classLoader.getResource("testCsvFile.csv").getFile());

        Client client = new Client();
        client.setId(1L);
        client.setIco(335);

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setIco(12345678);

        Ean ean = new Ean();
        ean.setId(1L);


        Product product = new Product();
        product.setId(1L);

        product.setSuppliers(singletonList(supplier));
        ean.setProduct(product);

        supplier.setClients(singletonList(client));

        Order order = new Order();
        order.setId(1L);
        order.setOrdersProducts(Arrays.asList(new OrderProduct()));

        when(eanRepository.findByValue(anyString())).thenReturn(ean);
        when(clientRepository.findByIco(anyInt())).thenReturn(client);
        when(supplierRepository.findByIco(anyInt())).thenReturn(supplier);
        when(orderProductRepository.save(any(OrderProduct.class))).thenReturn(new OrderProduct());

        assertTrue("Import csv data file is not saved into database", orderConfirmationDbImport.importFile(inputCsvFile));
    }

}