package com.axelor.demo.web;

import com.axelor.demo.service.WorkService;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class WorkController {
  private static final Logger log = LoggerFactory.getLogger(WorkController.class);

  @Inject private WorkService workService;

  public void start(ActionRequest request, ActionResponse response) {
    try {
      String id = request.getContext().get("id").toString();
      log.info("id -> {}", id);
      workService.start(id);
    } catch (Exception e) {
      response.setException(e);
    }
  }
}
