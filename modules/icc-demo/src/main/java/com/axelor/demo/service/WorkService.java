package com.axelor.demo.service;

import com.axelor.db.Query;
import com.axelor.demo.db.DemoWork;
import com.axelor.demo.db.repo.DemoWorkRepository;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkService {
  private static final Logger log = LoggerFactory.getLogger(WorkService.class);

  @Inject private DemoWorkRepository demoWorkRepo;

  public void start(String id) {
    log.info("id => {}", id);
  }

  public List<DemoWork> query() {
    Query<DemoWork> query = demoWorkRepo.all();
    return query.fetch();
  }

  @Transactional(rollbackOn = Exception.class)
  public Long save(DemoWork work) {
    return demoWorkRepo.save(work).getId();
  }

  @Transactional(rollbackOn = Exception.class)
  public void update(DemoWork work) {
    DemoWork dbWork = demoWorkRepo.find(work.getId());
    dbWork.setName(work.getName());
    dbWork.setDate(work.getDate());
    dbWork.setHours(work.getHours());
    demoWorkRepo.save(dbWork);
  }

  @Transactional(rollbackOn = Exception.class)
  public void delete(Long id) {
    DemoWork dbWork = demoWorkRepo.find(id);
    demoWorkRepo.remove(dbWork);
  }
}
