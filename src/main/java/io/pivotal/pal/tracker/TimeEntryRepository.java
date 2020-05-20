package io.pivotal.pal.tracker;

import java.util.List;
import io.pivotal.pal.tracker.TimeEntry;

public interface TimeEntryRepository {
    /*public void create(TimeEntry any) {
    }

    public void find(long timeEntryId) {
    }

    public void list() {
    }

    public void update(long timeEntryId, TimeEntry expected) {
    }

    public void delete(long timeEntryId) {
    }*/

    public TimeEntry create(TimeEntry any) ;

    public TimeEntry find(long timeEntryId);

    public List<TimeEntry> list() ;

    public TimeEntry update(long timeEntryId, TimeEntry expected) ;

    public void delete(long timeEntryId) ;
}
