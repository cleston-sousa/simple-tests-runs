package com.hachinet.simple_tests_runs.adapter.out.feign;

import com.hachinet.simple_tests_runs.adapter.out.feign.response.DogBreedResponse;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;

@FeignClient(value = "dogbreedfull", url = "https://dog.ceo")
public interface DogBreedFullFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/breeds/list/all")
    public Response getResponseFeign();

    @RequestMapping(method = RequestMethod.GET, value = "/api/breeds/list/all")
    public DogBreedResponse getResponsePojo();

    @RequestMapping(method = RequestMethod.GET, value = "/api/breeds/list/all")
    public Response getResponseFeign(URI uri);

    @RequestMapping(method = RequestMethod.GET)
    public DogBreedResponse getResponseFeignResource(URI uri);

    @RequestMapping(method = RequestMethod.GET)
    public Response getResponseFeignResource2(URI uri);
}
