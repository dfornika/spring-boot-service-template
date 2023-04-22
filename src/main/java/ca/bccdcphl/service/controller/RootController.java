package ca.bccdcphl.service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController()
public class RootController {
    private static final Logger log = LoggerFactory.getLogger(AggregateController.class);

    @Autowired
    public RootController(){

    }

    @GetMapping(value="/")
    public CollectionModel<Object> getRoot() {
        Collection<Object> emptyCollection = Collections.emptySet();
        CollectionModel<Object> model = CollectionModel.of(emptyCollection);
        Link aggregatesLink = Link.of("/aggregates", "aggregates");

        return model;
    }
}
