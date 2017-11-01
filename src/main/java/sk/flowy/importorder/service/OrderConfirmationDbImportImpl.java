package sk.flowy.importorder.service;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.exceptions.CsvRuntimeException;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.flowy.importorder.model.*;
import sk.flowy.importorder.repository.EanRepository;
import sk.flowy.importorder.repository.OrderProductRepository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Log4j
@Service
@Transactional
public class OrderConfirmationDbImportImpl implements OrderConfirmationDbImport {

    private EanRepository eanRepository;
    private OrderProductRepository orderProductRepository;
    private Client clientWithIco;
    private Supplier supplierWithIco;

    @Autowired
    public OrderConfirmationDbImportImpl(EanRepository eanRepository, OrderProductRepository orderProductRepository) {
        this.eanRepository = eanRepository;
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public boolean importFile(File file) {
        try {
            CSVReader csvFile = new CSVReader(new FileReader(file));
            CsvToBean csv = new CsvToBean();


            List<ImportCsvFile> list = csv.parse(setColumMapping(), csvFile);
            if (list != null) {
                OrderProduct orderProduct = this.saveDataForConfirmation(list);
                return orderProduct != null;
            }
            return false;
        } catch (IOException e) {
            log.error("Input file is not csv file ", e);
            throw new CsvRuntimeException(String.format("Input file is not csv file %s", e.getMessage()));
        }
    }

    private ColumnPositionMappingStrategy setColumMapping() {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(ImportCsvFile.class);
        String[] columns = new String[]{"clientIco", "supplierIco", "ean", "productCount", "price", "orderName"};
        strategy.setColumnMapping(columns);
        return strategy;
    }


    private OrderProduct saveDataForConfirmation(List<ImportCsvFile> list) {
        for (ImportCsvFile importImportCsvFile : list) {
            if (checkIfVariableIsNull(importImportCsvFile)) {
                Ean ean = eanRepository.findByValue(importImportCsvFile.getEan());
                if (ean == null) {
                    log.info("ean is not found in database");
                    return null;
                } else {
                    Product product = ean.getProduct();
                    if (product != null) {

                        List<Supplier> suppliers = product.getSuppliers();
                        if (CollectionUtils.isEmpty(suppliers)) {
                            log.info("supplier is not found in database");
                            return null;
                        }

                        suppliers.forEach(supp -> {
                            if (supp.getIco().equals(importImportCsvFile.getSupplierIco())) {
                                this.supplierWithIco = supp;
                            }
                        });

                        List<Client> clients = supplierWithIco.getClients();
                        if (CollectionUtils.isEmpty(clients)) {
                            log.info("client is not found in database");
                            return null;
                        }

                        clients.forEach(client -> {
                            if (client.getIco().equals(importImportCsvFile.getClientIco())) {
                                this.clientWithIco = client;
                            }
                        });

                        Order order = new Order();
                        order.setClient(clientWithIco);
                        order.setSupplier(supplierWithIco);
                        order.setName(importImportCsvFile.getOrderName());
                        order.setOrdersProducts(Arrays.asList(product));


                        OrderProduct orderProduct = new OrderProduct();
                        orderProduct.setProduct(product);
                        orderProduct.setCount(importImportCsvFile.getProductCount());
                        orderProduct.setPrice(importImportCsvFile.getPrice());
                        orderProduct.setOrder(order);
                        return orderProductRepository.save(orderProduct);
                    }

                }
                return null;
            }
        }
        return null;
    }

    private boolean checkIfVariableIsNull(ImportCsvFile importImportCsvFile) {
        if (importImportCsvFile.getClientIco() != null
                || importImportCsvFile.getSupplierIco() != null
                || StringUtils.isNotEmpty(importImportCsvFile.getEan())
                || StringUtils.isNotEmpty(importImportCsvFile.getOrderName())) {
            return true;
        }
        return false;
    }
}
