package com.dcnoris.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dcnorris
 */
@Service
@Transactional  // ensures this job is run in a transaction
public class ScheduleMetricLoggingService {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleMetricLoggingService.class);
    private int ADVISORY_LOCK_ID = 3141592; // first seven digits of π (pi)

    @Autowired
    private JobUtils jobUtils;

    // every minute
    @Scheduled(cron = "0 * * * * ?")
    public void executeJob() {
        if (jobUtils.tryAcquireDatabaseLock(ADVISORY_LOCK_ID)) {
            LOG.info("Testing");
            try {
                Thread.sleep(2000); // a little delay to ensure lock is held long enough for all nodes
            } catch (InterruptedException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        } else {
            LOG.info("Skipping as this job is running on another node");
        }
    }
}
