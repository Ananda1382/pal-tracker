package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

//    private DataSource dataSource;
//    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
//        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

   /* public JdbcTimeEntryRepository() {
    }*/

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(
                    "insert into time_entries (project_id, user_id, date, hours) values (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, timeEntry.getProjectId());
            stmt.setLong(2,timeEntry.getUserId());
            stmt.setDate(3, Date.valueOf(timeEntry.getDate()));
            stmt.setInt(4,timeEntry.getHours());
            return stmt;}, generatedKeyHolder);
        return find(generatedKeyHolder.getKey().longValue());
    }

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;

    @Override
    public TimeEntry find(long timeEntryId) {
//        int id = (int) timeEntryId;
//        return jdbcTemplate.queryForObject("select * from time_entries where id = ?", new Object[] {timeEntryId}, mapper);
        return jdbcTemplate.query("select * from time_entries where id = ?", new Object[] {timeEntryId}, extractor);

    }

    @Override
    public List<TimeEntry> list() {

//        List<TimeEntry> timeEntryList = jdbcTemplate.query("select * from time_entries", extractor);
//        return timeEntryList;

        return jdbcTemplate.query("select * from time_entries", mapper);


    }

    @Override
    public TimeEntry update(long timeEntryId, TimeEntry timeEntry) {

        jdbcTemplate.update("update time_entries SET project_id = ?, user_id = ?, date = ?,  hours = ? WHERE id = ?",
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                Date.valueOf(timeEntry.getDate()),
                timeEntry.getHours(),
                timeEntryId);
        return find(timeEntryId);
    }

    @Override
    public void delete(long timeEntryId) {
        jdbcTemplate.update("delete from time_entries where id =?", timeEntryId);

    }
}
