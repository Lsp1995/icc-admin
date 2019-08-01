package com.axelor.demo.web;

import com.google.inject.servlet.RequestScoped;
import com.icc.common.util.collection.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Path("/rs/demo")
public class DemoRestController {
    private static Logger log = LoggerFactory.getLogger(DemoRestController.class);

    @GET
    @Path("get/{id}")
    public Map get(@PathParam("id") Long id, @QueryParam("name") String name) {
        return MapUtils.toMap("id", id, "name", name);
    }

    @POST
    @Path("post")
    public Map post(@FormParam("id") Long id, @FormParam("name") String name) {
        return MapUtils.toMap("id", id, "name", name);
    }
}
