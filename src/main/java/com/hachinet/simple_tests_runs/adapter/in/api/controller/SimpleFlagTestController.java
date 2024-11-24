package com.hachinet.simple_tests_runs.adapter.in.api.controller;

import com.hachinet.simple_tests_runs.adapter.in.api.controller.response.ListaFlagResponse;
import com.hachinet.simple_tests_runs.infra.utils.MapUtils;
import dev.openfeature.sdk.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flag")
public class SimpleFlagTestController {


    private final OpenFeatureAPI openFeatureAPI;

    @Autowired
    public SimpleFlagTestController(OpenFeatureAPI OFApi) {
        this.openFeatureAPI = OFApi;
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

        Structure defaultObject = Structure.mapToStructure(new HashMap<>());

        Map<String, Object> mapTest = client.getObjectValue("basic-object", new Value(defaultObject)).asStructure().asObjectMap();

        List<Map<String, Object>> listaDeMaps = (List<Map<String, Object>>) MapUtils.pathValue("lista", mapTest);

        String todasOrigens = listaDeMaps.stream().map(maps -> maps.get("origem").toString()).collect(Collectors.joining(", "));

        response.setMensagem(response.getMensagem() +" : "+ todasOrigens);

        return ResponseEntity.ok(response);
    }
}
