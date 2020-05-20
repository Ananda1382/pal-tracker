package io.pivotal.pal.tracker;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    Map<Long, TimeEntry> objectMap = new TreeMap<>();
    long idCounter = 1;
    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry createdTtimeEntry = new TimeEntry(idCounter,timeEntry.getProjectId(),timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        objectMap.put(createdTtimeEntry.getId(),createdTtimeEntry);
        idCounter++;
        return createdTtimeEntry;
    }

    public TimeEntry find(long id) {

        TimeEntry objectFound = objectMap.get(id);
        return objectFound;
    }

    public List<TimeEntry> list() {
        List<Long> keyList = new ArrayList(objectMap.keySet());
        keyList.forEach(System.out::println);
        List<TimeEntry> returnList = new ArrayList<>();
       for(long i : keyList) {
           returnList.add(objectMap.get(i));
       }
        return returnList;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {

        TimeEntry objectTobeUpdated = find(id);
        if(objectTobeUpdated!=null) {
            TimeEntry updatedEntry = new TimeEntry(objectTobeUpdated.getId(),
                    timeEntry.getProjectId(), timeEntry.getUserId(),
                    timeEntry.getDate(), timeEntry.getHours());
            objectMap.put(updatedEntry.getId(), updatedEntry);
            return updatedEntry;
        }else{
            TimeEntry updatedEntry = null;
            return updatedEntry;
        }

    }

    public void delete(long id) {
        //TimeEntry toBeDeleted = find(id);
        //objectMap.remove(toBeDeleted.getId());
        objectMap.remove(id);

    }
}
