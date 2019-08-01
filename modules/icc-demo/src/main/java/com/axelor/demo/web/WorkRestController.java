package com.axelor.demo.web;

import com.axelor.db.mapper.Mapper;
import com.axelor.demo.db.DemoWork;
import com.axelor.demo.service.WorkService;
import com.axelor.rpc.Request;
import com.axelor.rpc.Response;
import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import com.icc.common.util.collection.MapUtils;
import com.icc.common.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Path("/rs/work")
public class WorkRestController {
    private static Logger log = LoggerFactory.getLogger(WorkRestController.class);

    @Inject
    private WorkService workService;

    @GET
    @Path("search")
    public Response search() {
        Response response = new Response();
        response.setData(workService.query());
        return response;
    }

    @POST
    @Path("add") // {"data":{"name":"zs","date":"2019-07-19","hours":"10:10"}}
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Request request) {
        log.info("request -> {}", JsonUtils.toJson(request));
        Response response = new Response();
        Map<String, Object> data = request.getData();
        DemoWork bean = Mapper.toBean(DemoWork.class, data);
        workService.save(bean);
        return response;
    }

    @POST
    @Path("update") // {"data":{"id":1,"name":"ls","date":"2019-07-18","hours":"09:10"}}
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Request request) {
        log.info("request -> {}", JsonUtils.toJson(request));
        Response response = new Response();
        Map<String, Object> data = request.getData();
        DemoWork bean = Mapper.toBean(DemoWork.class, data);
        workService.update(bean);
        return response;
    }

    @POST
    @Path("delete") // {"data":{"id":1}}
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(Request request) {
        log.info("request -> {}", JsonUtils.toJson(request));
        Response response = new Response();
        Map<String, Object> data = request.getData();
        workService.delete(MapUtils.getLong(data, "id"));
        return response;
    }
}
