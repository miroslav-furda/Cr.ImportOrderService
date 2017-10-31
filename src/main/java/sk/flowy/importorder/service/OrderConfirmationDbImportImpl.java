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
import sk.flowy.importorder.repository.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Log4j
@Service
public class OrderConfirmationDbImportImpl implements OrderConfirmationDbImport {

    private ClientRepository clientRepository;
    private SupplierRepository supplierRepository;
    private EanRepository eanRepository;
    private ProductRepository productRepository;
    private OrderProductRepository orderProductRepository;
    private OrderRepository orderRepository;
    private Order order;

    @Autowired
    public OrderConfirmationDbImportImpl(ClientRepository clientRepository,
                                         SupplierRepository supplierRepository,
                                         EanRepository eanRepository,
                                         ProductRepository productRepository,
                                         OrderProductRepository orderProductRepository,
                                         OrderRepository orderRepository) {
        this.clientRepository = clientRepository;
        this.supplierRepository = supplierRepository;
        this.eanRepository = eanRepository;
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean importFile(File file) {
        try {
            CSVReader csvFile = new CSVReader(new FileReader(file));
            CsvToBean csv = new CsvToBean();


            List<ImportCsvFile> list = csv.parse(setColumMapping(), csvFile);
            if (list != null) {
                OrderProduct orderProduct = this.saveDataForConfirmation(list);
                if (orderProduct == null) {
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            log.error("Input file is not csv file ", e);
            throw new CsvRuntimeException(String.format("Input file is not csv file %s", e.getMessage()));
        } catch (RuntimeException e) {
            log.error("Import CSV file contains null or empty values ", e);
            throw new CsvRuntimeException("Error parsing CSV file");
        }
    }

    private ColumnPositionMappingStrategy setColumMapping() {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(ImportCsvFile.class);
        String[] columns = new String[]{"clientIco", "clientName", "supplierIco", "supplierName", "ean", "productCount", "price"};
        strategy.setColumnMapping(columns);
        return strategy;
    }


    @Transactional
    OrderProduct saveDataForConfirmation(List<ImportCsvFile> list) {
        for (ImportCsvFile importImportCsvFile : list) {
            if (StringUtils.isNotEmpty(importImportCsvFile.getClientName()) || StringUtils.isNotEmpty(importImportCsvFile.getEan()) || StringUtils.isNotEmpty(importImportCsvFile.getSupplierName())) {

                List<Client> clients = clientRepository.findByIco(importImportCsvFile.getClientIco());
                if (CollectionUtils.isEmpty(clients)) {
                    log.info("client is not found in database");
                    return null;
                }

                List<Supplier> suppliers = supplierRepository.findByIco(importImportCsvFile.getSupplierIco());
                if (CollectionUtils.isEmpty(clients)) {
                    log.info("supplier is not found in database");
                    return null;
                }


                if (clients.size() == suppliers.size()) {
                    //create new order
                    clients.forEach(client -> {
                        suppliers.forEach(supplier -> {
                            this.order = new Order();
                            order.setClientId(client.getId().intValue());
                            order.setSupplierId(supplier.getId().intValue());
                            order.setName("todo");
                            this.order = orderRepository.save(order);
                        });
                    });
                }


                Ean ean = eanRepository.findByValue(importImportCsvFile.getEan());
                if (ean == null) {
                    log.info("ean is not found in database");
                    return null;
                } else {
                    Product product = productRepository.findByEans(Arrays.asList(ean));
                    if (product != null) {
                        ean.setProduct(product);

                        OrderProduct orderProduct = new OrderProduct();
                        orderProduct.setCount(importImportCsvFile.getProductCount());
                        orderProduct.setPrise(importImportCsvFile.getPrice());
                        orderProduct.setConfirmation(1);
                        if (product.getId() != null) {
                            orderProduct.setProductId(product.getId().intValue());
                        }
                        if (order != null) {
                            orderProduct.setOrderId(order.getId().intValue());
                        }
                        return orderProductRepository.save(orderProduct);
                    }
                }
            }
        }
        return null;
    }

}
