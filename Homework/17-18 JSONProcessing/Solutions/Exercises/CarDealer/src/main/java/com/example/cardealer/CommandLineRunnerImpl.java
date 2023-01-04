package com.example.cardealer;

import com.example.cardealer.model.dto.*;
import com.example.cardealer.service.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private static final String OUTPUT_PATH = "src/main/resources/out/";
    private static final String ORDERED_CUSTOMERS = "ordered-customers.json";
    private static final String TOYOTA_CARS = "toyota-cars.json";
    private static final String LOCAL_SUPPLIERS = "local-suppliers.json";
    private static final String CARS_AND_PARTS = "cars-and-parts.json";
    private static final String CUSTOMERS_TOTAL_SALES = "customers-total-sales.json";
    private static final String SALES_DISCOUNTS = "sales-discounts.json";

    private final static int COUNT_OF_SALES = 15;
    private final static String TOYOTA = "Toyota";

    private final CarService carService;
    private final CustomerService customerService;
    private final PartService partService;
    private final SupplierService supplierService;
    private final SaleService saleService;
    private final Gson gson;

    @Autowired
    public CommandLineRunnerImpl(CarService carService, CustomerService customerService,
                                 PartService partService, SupplierService supplierService,
                                 SaleService saleService, Gson gson) {

        this.carService = carService;
        this.customerService = customerService;
        this.partService = partService;
        this.supplierService = supplierService;
        this.saleService = saleService;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
        // this.seedData();

        this.orderedCustomers();
        this.carsFromMakeToyota();
        this.localSuppliers();
        this.carsWithTheirListOfParts();
        this.totalSalesByCustomer();
        this.salesWithAppliedDiscount();
    }

    private void seedData() throws IOException {
        this.supplierService.seedSuppliers();
        this.partService.seedParts();
        this.customerService.seedCustomers();
        this.carService.seedCars();
        this.saleService.generateRandomSales(COUNT_OF_SALES);
    }

    private void orderedCustomers() throws IOException {
        List<CustomerWithSalesDto> orderedCustomers = this.customerService.getOrderedCustomers();
        String json = gson.toJson(orderedCustomers);

        this.write(OUTPUT_PATH + ORDERED_CUSTOMERS, json);
    }

    private void carsFromMakeToyota() throws IOException {
        List<CarWithIdDto> carDtos = this.carService.findAllByMake(TOYOTA);
        String json = gson.toJson(carDtos);

        this.write(OUTPUT_PATH + TOYOTA_CARS, json);
    }

    private void localSuppliers() throws IOException {
        List<SupplierWithCountOfPartsDto> localSuppliers =
                this.supplierService.findAllLocalSuppliers();

        String json = gson.toJson(localSuppliers);

        this.write(OUTPUT_PATH + LOCAL_SUPPLIERS, json);
    }

    private void carsWithTheirListOfParts() throws IOException {
        List<CarWithPartsDto> carsWithPartsDtos =
                this.carService.findAllCarsWithTheirParts();

        String json = gson.toJson(carsWithPartsDtos);

        this.write(OUTPUT_PATH + CARS_AND_PARTS, json);
    }

    private void totalSalesByCustomer() throws IOException {
        List<CustomerPurchasesInfoDto> customersInfoDto =
                this.customerService.findAllCustomersWithAtLeastOneCar();

        String json = gson.toJson(customersInfoDto);

        this.write(OUTPUT_PATH + CUSTOMERS_TOTAL_SALES, json);
    }

    private void salesWithAppliedDiscount() throws IOException {
        List<SaleInfoDto> saleInfoDtos = this.saleService.getSalesInfo();

        String json = gson.toJson(saleInfoDtos);

        this.write(OUTPUT_PATH + SALES_DISCOUNTS, json);
    }

    private void write(String path, String content) throws IOException {
        Files.write(
                Path.of(path),
                Collections.singleton(content)
        );
    }
}
