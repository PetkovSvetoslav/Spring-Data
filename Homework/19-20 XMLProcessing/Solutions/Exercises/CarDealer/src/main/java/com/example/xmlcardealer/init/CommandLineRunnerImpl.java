package com.example.xmlcardealer.init;

import com.example.xmlcardealer.model.dto.*;
import com.example.xmlcardealer.model.dto.seed.CarsSeedRootDto;
import com.example.xmlcardealer.model.dto.seed.CustomersSeedRootDto;
import com.example.xmlcardealer.model.dto.seed.PartsSeedRootDto;
import com.example.xmlcardealer.model.dto.seed.SuppliersSeedRootDto;
import com.example.xmlcardealer.service.*;
import com.example.xmlcardealer.util.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private static final String RESOURCES_FILES_PATH = "src/main/resources/files/";
    private static final String CARS_FILE_NAME = "cars.xml";
    private static final String CUSTOMERS_FILE_NAME = "customers.xml";
    private static final String PARTS_FILE_NAME = "parts.xml";
    private static final String SUPPLIERS_FILE_NAME = "suppliers.xml";

    private static final String OUT_PATH = RESOURCES_FILES_PATH + "out/";
    private static final String ORDERED_CUSTOMERS = "ordered-customers.xml";
    private static final String TOYOTA_CARS = "toyota-cars.xml";
    private static final String LOCAL_SUPPLIERS = "local-suppliers.xml";
    private static final String CARS_AND_PARTS = "cars-and-parts.xml";
    private static final String CUSTOMERS_TOTAL_SALES = "customers-total-sales.xml";
    private static final String SALES_DISCOUNTS = "sales-discounts.xml";

    private static final int COUNT_OF_SALES = 20;
    private static final String TOYOTA = "Toyota";

    private final CarService carService;
    private final CustomerService customerService;
    private final PartService partService;
    private final SaleService saleService;
    private final SupplierService supplierService;
    private final XmlParser xmlParser;

    @Autowired
    public CommandLineRunnerImpl(CarService carService, CustomerService customerService,
                                 PartService partService, SaleService saleService,
                                 SupplierService supplierService, XmlParser xmlParser) {

        this.carService = carService;
        this.customerService = customerService;
        this.partService = partService;
        this.saleService = saleService;
        this.supplierService = supplierService;
        this.xmlParser = xmlParser;
    }

    @Override
    public void run(String... args) throws Exception {
        //this.seedData();

        this.orderedCustomers();
        this.carsFromMakeToyota();
        this.localSuppliers();
        this.carsWithTheirListOfParts();
        this.totalSalesByCustomer();
        this.salesWithAppliedDiscount();
    }

    private void seedData() throws JAXBException {
        // Suppliers seeding
        SuppliersSeedRootDto suppliersSeedRootDto =
                xmlParser.fromFile(
                        RESOURCES_FILES_PATH + SUPPLIERS_FILE_NAME, SuppliersSeedRootDto.class
                );

        supplierService.seedSuppliers(suppliersSeedRootDto.getSuppliers());

        // Parts seeding
        PartsSeedRootDto partsSeedRootDto = xmlParser.fromFile(
                RESOURCES_FILES_PATH + PARTS_FILE_NAME, PartsSeedRootDto.class
        );

        partService.seedParts(partsSeedRootDto.getParts());

        // Cars seeding
        CarsSeedRootDto carsSeedRootDto = xmlParser.fromFile(
                RESOURCES_FILES_PATH + CARS_FILE_NAME, CarsSeedRootDto.class
        );

        carService.seedCars(carsSeedRootDto.getCars());

        // Customers seeding
        CustomersSeedRootDto customersSeedRootDto = xmlParser.fromFile(
                RESOURCES_FILES_PATH + CUSTOMERS_FILE_NAME, CustomersSeedRootDto.class
        );

        customerService.seedCustomers(customersSeedRootDto.getCustomers());

        // Sales generating
        saleService.generateRandomSales(COUNT_OF_SALES);
    }

    private void orderedCustomers() throws JAXBException {
        List<CustomerWithSalesDto> customers = customerService.getOrderedCustomers();
        CustomersWithSalesRootDto rootDto = new CustomersWithSalesRootDto(customers);

        xmlParser.writeToFile(OUT_PATH + ORDERED_CUSTOMERS, rootDto);
    }

    private void carsFromMakeToyota() throws JAXBException {
        List<CarWithIdDto> cars = carService.findAllByMake(TOYOTA);
        CarsWithIdRootDto rootDto = new CarsWithIdRootDto(cars);

        xmlParser.writeToFile(OUT_PATH + TOYOTA_CARS, rootDto);
    }

    private void localSuppliers() throws JAXBException {
        List<SupplierWithCountOfPartsDto> suppliers = supplierService.findAllLocalSuppliers();
        SuppliersWithCountOfPartsRootDto rootDto = new SuppliersWithCountOfPartsRootDto(suppliers);

        xmlParser.writeToFile(OUT_PATH + LOCAL_SUPPLIERS, rootDto);
    }

    private void carsWithTheirListOfParts() throws JAXBException {
        List<CarWithPartsDto> cars = carService.findAllCarsWithTheirParts();
        CarsWithPartsRootDto rootDto = new CarsWithPartsRootDto(cars);

        xmlParser.writeToFile(OUT_PATH + CARS_AND_PARTS, rootDto);
    }

    private void totalSalesByCustomer() throws JAXBException {
        List<CustomerPurchasesInfoDto> customers = customerService.findAllCustomersWithAtLeastOneCar();
        CustomersPurchasesInfoRootDto rootDto = new CustomersPurchasesInfoRootDto(customers);

        xmlParser.writeToFile(OUT_PATH + CUSTOMERS_TOTAL_SALES, rootDto);
    }

    private void salesWithAppliedDiscount() throws JAXBException {
        List<SaleInfoDto> sales = saleService.getSalesInfo();
        SalesInfoRootDto rootDto = new SalesInfoRootDto(sales);

        xmlParser.writeToFile(OUT_PATH + SALES_DISCOUNTS, rootDto);
    }
}
