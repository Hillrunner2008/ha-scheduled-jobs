package com.dcnoris.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.sql.Types;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dcnorris
 */
@Transactional
@Component
public class JobUtils {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Tries to acquire an advisory lock in the database using the provided lock key.
     *
     * @param lockKey The key for the lock.
     * @return true if the lock was successfully acquired, false otherwise.
     */
    public boolean tryAcquireDatabaseLock(int lockKey) {
        int[] argTypes = {Types.INTEGER};
        return jdbcTemplate.queryForObject(
                "SELECT pg_try_advisory_xact_lock(?);",
                new Object[]{lockKey},
                argTypes,
                Boolean.class
        );
    }
}
