package org.terasoluna.gfw.common.logging;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class DBLogCleaner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBLogCleaner.class);

    private long savedPeriodMinutes = TimeUnit.MINUTES.toMinutes(10);

    private NamedParameterJdbcTemplate jdbcTemplate;

    public void setSavedPeriodMinutes(long savedPeriodMinutes) {
        this.savedPeriodMinutes = savedPeriodMinutes;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Transactional
    public void cleanup() {

        if (savedPeriodMinutes < 0) {
            return;
        }

        // calculate cutoff date.
        Date cutoffDate = new Date(System.currentTimeMillis() - (TimeUnit.MINUTES.toMillis(savedPeriodMinutes)));

        // logging begin.
        LOGGER.info("Begin cleanup. cutoffDate is '{}'.", cutoffDate);

        MapSqlParameterSource queryParameters = new MapSqlParameterSource();
        queryParameters.addValue("cutoffDateMillis", cutoffDate.getTime());
        Long maxEventId = jdbcTemplate.queryForObject(
                "SELECT MAX(event_id) FROM logging_event WHERE timestmp < :cutoffDateMillis", queryParameters,
                Long.class);

        if (maxEventId != null) {
            MapSqlParameterSource deleteParameters = new MapSqlParameterSource();
            deleteParameters.addValue("eventId", maxEventId);
            jdbcTemplate.update("DELETE FROM logging_event_exception WHERE event_id <= :eventId", deleteParameters);
            jdbcTemplate.update("DELETE FROM logging_event_property WHERE event_id <= :eventId", deleteParameters);
            jdbcTemplate.update("DELETE FROM logging_event WHERE event_id <= :eventId", deleteParameters);
        }

        LOGGER.info("Finished cleanup.");

    }
}
