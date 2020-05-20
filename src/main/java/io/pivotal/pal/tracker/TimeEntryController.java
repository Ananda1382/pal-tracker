package io.pivotal.pal.tracker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;
    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = timeEntryRepository.create(timeEntryToCreate);
        return created(null).body(timeEntry);//ctrl+Alt+n- to getinline
    }

   /* public ResponseEntity<TimeEntry> read(long timeEntryId) {
        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);
        return ok(timeEntry);
    }*/

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {

        TimeEntry timeEntry = timeEntryRepository.find(id);
        if(timeEntry!=null) {
            return ok(timeEntry);
        }else{
            return notFound().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        /*Map<Long, TimeEntry> objectMap = new TreeMap<>();
        TimeEntry entry = new TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8)
        objectMap.put(entry.getId(), entry);
        timeEntryRepository.create(new TimeEntry(2L, 789L, 321L, LocalDate.parse("2017-01-07"), 4));

        List<Long> keyList = new ArrayList(objectMap.keySet());
        keyList.forEach(System.out::println);
        List<TimeEntry> returnList = new ArrayList<>();
        for(long i : keyList) {
            returnList.add(objectMap.get(i));
        }
        return returnList;*/


        List<TimeEntry> list = timeEntryRepository.list();
        return ok(list);
    }
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable  long id, @RequestBody TimeEntry entry) {
        TimeEntry timeEntry = timeEntryRepository.update(id, entry);
        if(timeEntry!=null) {
            return ok(timeEntry);
        }else{
            return notFound().build();
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id) {
        timeEntryRepository.delete(id);
        return noContent().build();
    }
}
