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
import sk.flowy.importorder.repository.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static java.util.Collections.singletonList;
import static org.mockito.Matchers.*;
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
    private ProductRepository productRepository;
    @MockBean
    private OrderProductRepository orderProductRepository;
    @MockBean
    private OrderRepository orderRepository;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private OrderConfirmationDbImportImpl orderConfirmationDbImport;

    @Rule
    public ExpectedException exceptions = ExpectedException.none();

    @Before
    public void setup() {
        this.orderConfirmationDbImport = new OrderConfirmationDbImportImpl(clientRepository, supplierRepository, eanRepository, productRepository, orderProductRepository, orderRepository);

    }

    @Test(expected = CsvRuntimeException.class)
    public void if_import_file_is_not_csv_file_then_error_should_be_return() {

        orderConfirmationDbImport.importFile(new File("file.txt"));
    }

    @Test
    public void if_import_file_is_empty_then_method_return_false_and_save_into_database_is_not_possible() throws IOException {
        Path filePath = temporaryFolder.newFile("input.csv").toPath();

        Assert.assertFalse(orderConfirmationDbImport.importFile(filePath.toFile()));
    }

    @Test(expected = CsvRuntimeException.class)
    public void if_import_file_is_not_empty_and_import_csv_file_have_not_contains_client_ico_then_parsing_csv_file_return_error_message() throws IOException {
        Path filePath = temporaryFolder.newFile("input.csv").toPath();
        File inputCsvFile = filePath.toFile();

        ImportCsvFile importCsvFile = new ImportCsvFile();
        importCsvFile.setClientName("aaa");
        importCsvFile.setSupplierIco(123);
        importCsvFile.setSupplierName("bbb");
        importCsvFile.setEan("4062800000990");
        importCsvFile.setPrice(5.0);
        importCsvFile.setProductCount(50);
        FileUtils.writeLines(inputCsvFile, singletonList(importCsvFile));

        orderConfirmationDbImport.importFile(inputCsvFile);
        exceptions.expectMessage("Error parsing CSV file");
    }

    @Test
    public void if_client_ico_number_is_not_found_in_database_then_inport_csv_file_is_not_possible_save_into_database_and_method_return_false() throws IOException {
        Path inputCsvFile = temporaryFolder.newFile("input.csv").toPath();
        when(clientRepository.findByIco(anyInt())).thenReturn(null);

        Assert.assertFalse(orderConfirmationDbImport.importFile(inputCsvFile.toFile()));
    }

    @Test
    public void if_import_csv_file_is_correct_and_client_ico_and_supplier_ico_was_found_in_database_then_all_data_should_be_save_correct_saved_into_database_and_method_return_true() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File inputCsvFile = new File(classLoader.getResource("testCsvFile.csv").getFile());

        Client client = new Client();
        Supplier supplier = new Supplier();
        client.setId(1L);
        supplier.setId(1L);

        Ean ean = new Ean();
        ean.setId(1L);

        Product product = new Product();
        product.setId(1L);

        Order order = new Order();
        order.setId(1L);

        when(clientRepository.findByIco(anyInt())).thenReturn(singletonList(client));
        when(supplierRepository.findByIco(anyInt())).thenReturn(singletonList(supplier));
        when(eanRepository.findByValue(anyString())).thenReturn(ean);
        when(productRepository.findByEans(anyList())).thenReturn(product);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderProductRepository.save(any(OrderProduct.class))).thenReturn(new OrderProduct());


        Assert.assertTrue("Import csv data file is not saved into database", orderConfirmationDbImport.importFile(inputCsvFile));
    }

}