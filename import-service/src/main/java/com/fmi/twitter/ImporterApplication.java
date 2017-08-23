package com.fmi.twitter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@SpringBootApplication
public class ImporterApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ImporterApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

    }
}
