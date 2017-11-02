package sk.flowy.importorder.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvRuntimeException;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.flowy.importorder.model.*;
import sk.flowy.importorder.repository.ClientRepository;
import sk.flowy.importorder.repository.EanRepository;
import sk.flowy.importorder.repository.OrderProductRepository;
import sk.flowy.importorder.repository.SupplierRepository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


@Log4j
@Service
@Transactional
public class OrderConfirmationDbImportImpl implements OrderConfirmationDbImport {

    private EanRepository eanRepository;
    private OrderProductRepository orderProductRepository;
    private ClientRepository clientRepository;
    private SupplierRepository supplierRepository;

    @Autowired
    public OrderConfirmationDbImportImpl(EanRepository eanRepository, OrderProductRepository orderProductRepository, SupplierRepository supplierRepository, ClientRepository clientRepository) {
        this.eanRepository = eanRepository;
        this.orderProductRepository = orderProductRepository;
        this.clientRepository = clientRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public boolean importFile(File file) {
        try {
            List<ImportCsvFile> list = new CsvToBeanBuilder<ImportCsvFile>(new FileReader(file))
                    .withType(ImportCsvFile.class)
                    .build().parse();

            if (CollectionUtils.isNotEmpty(list)) {
                OrderProduct orderProduct = this.saveDataForConfirmation(list);
                return orderProduct != null;
            }
            return false;
        } catch (IOException e) {
            log.error("Input file is not csv file ", e);
            throw new CsvRuntimeException(String.format("Input file is not csv file %s", e.getMessage()));
        }
    }

    private OrderProduct saveDataForConfirmation(List<ImportCsvFile> list) {
        for (ImportCsvFile importImportCsvFile : list) {
            if (checkIfVariableIsNull(importImportCsvFile)) {
                Ean ean = eanRepository.findByValue(importImportCsvFile.getEan());
                if (ean == null) {
                    log.info("ean is not found in database");
                    return null;
                } else {
                    Order order = new Order();
                    Product product = ean.getProduct();
                    if (product != null) {
                        Client client = clientRepository.findByIco(importImportCsvFile.getClientIco());
                        if (client == null) {
                            log.info("client is not found in database");
                            return null;
                        }
                        order.setClient(client);

                        Supplier supplier = supplierRepository.findByIco(importImportCsvFile.getSupplierIco());
                        if (supplier == null) {
                            log.info("supplier is not found in database");
                            return null;
                        }
                        if (CollectionUtils.isNotEmpty(product.getSuppliers())) {
                            if (!product.getSuppliers().contains(supplier)) {
                                product.getSuppliers().add(supplier);
                            }
                        }


                        order.setName(importImportCsvFile.getOrderName());
                        order.setSupplier(supplier);

                        OrderProduct orderProduct = new OrderProduct();
                        orderProduct.setProduct(product);
                        orderProduct.setCount(importImportCsvFile.getProductCount());
                        orderProduct.setPrice(importImportCsvFile.getPrice());
                        orderProduct.setOrder(order);

                        if (CollectionUtils.isNotEmpty(order.getOrdersProducts())) {
                            if (!order.getOrdersProducts().contains(orderProduct))
                                order.getOrdersProducts().add(orderProduct);
                        }

                        if (CollectionUtils.isNotEmpty(client.getOrders())) {
                            if (!client.getOrders().contains(order)) {
                                client.getOrders().add(order);
                            }
                        }

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
