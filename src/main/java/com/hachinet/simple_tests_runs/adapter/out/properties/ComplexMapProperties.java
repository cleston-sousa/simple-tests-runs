package com.hachinet.simple_tests_runs.adapter.out.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;


@Configuration
@ConfigurationProperties(prefix = "complex")
public class ComplexMapProperties {

    private List<Map<String, String>> lista;


    public List<Map<String, String>> getLista() {
        return lista;
    }

    public void setLista(List<Map<String, String>> lista) {
        this.lista = lista;
    }
}
