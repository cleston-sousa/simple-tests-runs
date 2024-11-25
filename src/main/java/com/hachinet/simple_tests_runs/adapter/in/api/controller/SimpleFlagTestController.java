package com.hachinet.simple_tests_runs.adapter.in.api.controller;

import com.hachinet.simple_tests_runs.adapter.in.api.controller.response.ListaFlagResponse;
import com.hachinet.simple_tests_runs.adapter.out.feign.DogBreedFullFeignClient;
import com.hachinet.simple_tests_runs.adapter.out.feign.response.DogBreedResponse;
import com.hachinet.simple_tests_runs.adapter.out.properties.ComplexMapProperties;
import com.hachinet.simple_tests_runs.infra.utils.MapUtils;
import dev.openfeature.sdk.*;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flag")
public class SimpleFlagTestController {


    private final OpenFeatureAPI openFeatureAPI;

    private final DogBreedFullFeignClient dogBreedFullFeignClient;


    private final ComplexMapProperties complexMapProperties;

    @Autowired
    public SimpleFlagTestController(OpenFeatureAPI OFApi, ComplexMapProperties complexMapProperties, DogBreedFullFeignClient dogBreedFullFeignClient) {

        this.openFeatureAPI = OFApi;

        this.complexMapProperties = complexMapProperties;

        this.dogBreedFullFeignClient = dogBreedFullFeignClient;
    }



    @GetMapping("/listar/{valorTeste}")
    public ResponseEntity<?> listarFlagsTeste(
            @PathVariable String valorTeste,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String nome
    ) {

        ListaFlagResponse response = new ListaFlagResponse("Meh!");

        final Client client = openFeatureAPI.getClient();

        // Evaluate welcome-message feature flag
        if (client.getBooleanValue("passa-fluxo", false)) {
            response.setMensagem("Flag ativada!");
        }else{
            response.setMensagem("Flag desativada!");
        }

        MutableContext context = new MutableContext();
        context.add("cnpj", valorTeste)
                .add("cpf", cpf)
                .add("nome", nome);

        if (client.getBooleanValue("passa-fluxo-complexo", false, context)) {
            response.setMensagem(response.getMensagem() +" : "+ "Flag complexa ativada!");
        }else{
            response.setMensagem(response.getMensagem() +" : "+ "Flag complexa desativada!");
        }

        response.setMensagem(response.getMensagem() +" : "+ client.getStringValue("mensagem-ativada", "Mensagem Padrao"));

        Map<String, Object> defaultMap = new HashMap<>();

        defaultMap.put("lista", complexMapProperties.getLista());

        Structure defaultObject = Structure.mapToStructure(defaultMap);

        Map<String, Object> mapTest = client.getObjectValue("basic-object", new Value(defaultObject)).asStructure().asObjectMap();

        List<Map<String, Object>> listaDeMaps = (List<Map<String, Object>>) MapUtils.pathValue("lista", mapTest);

//        List<Map<String, String>> listaDeMapsProp = complexMapProperties.getLista();

        String todasOrigens = listaDeMaps.stream().map(maps -> maps.get("origem").toString()).collect(Collectors.joining(", "));

//        String todasOrigensProp = listaDeMapsProp.stream().map(maps -> maps.get("origem").toString()).collect(Collectors.joining(", "));

//        response.setMensagem(response.getMensagem() + " : " + todasOrigens); //  + " : " + todasOrigensProp

        response.getObjetos().add(todasOrigens);


        Response responseFeign = dogBreedFullFeignClient.getResponseFeign();

        response.getObjetos().add(getBody(responseFeign.body()));


        DogBreedResponse responsePojo = dogBreedFullFeignClient.getResponsePojo();

        response.getObjetos().add(responsePojo);

        String stringUriObtida = client.getStringValue("ms-basico-via-flag", "http://host-via-properties");


        Response responseFeignUri = dogBreedFullFeignClient.getResponseFeign(URI.create(stringUriObtida));

        response.getObjetos().add(getBody(responseFeignUri.body()));

        Response responseFeignUriResource2 = dogBreedFullFeignClient.getResponseFeignResource2(URI.create(stringUriObtida + "/api/breeds/list/all"));

        response.getObjetos().add(getBody(responseFeignUriResource2.body()));

//        DogBreedResponse responseFeignUriResource = dogBreedFullFeignClient.getResponseFeignResource(customURI);


        return ResponseEntity.ok(response);
    }


    private String getBody(Response.Body responseBody){
        String body = "";
        //responseBody.asReader(Charset.forName("UTF-8");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody.asInputStream()))) {
             body = reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {}

        return body;
    }


}
