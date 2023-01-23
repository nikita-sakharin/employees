package com.github.nikita.sakharin.employees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import lombok.Data;
import lombok.Value;

import static java.util.Collections.unmodifiableList;
import static java.lang.Math.max;

@Value
public class EmployeeHierarchy {
    Map<Long, EmployeeInfo> map = new HashMap<>();
    long depth;
    Employee least;

    public EmployeeHierarchy(final Iterable<Employee> employees) {
        final Queue<Long> leaders = new ArrayDeque<>();
        for (final var employee : employees) {
            final var id = employee.getId();
            final var supervisor = employee.getSupervisor();
            if (supervisor != null)
                map.computeIfAbsent​(
                    supervisor.getId(),
                    key -> new EmployeeInfo(supervisor)
                ).getSubordinates().add(employee);
            else
                leaders.add​(id);

            map.computeIfAbsent​(id, key -> new EmployeeInfo(employee));
        }

        assert !leaders.isEmpty();
        long maxRank = 0;
        var withMaxRank = map.get(leaders.element()).getEmployee();
        while (!leaders.isEmpty()) {
            final var id = leaders.remove();
            final var employee = map.get(id);
            final var rank = map.get(id).getRank();
            maxRank = max(maxRank, rank + 1);
            for (final var subordinate : employee.getSubordinates()) {
                final var subordinateId = subordinate.getId();
                map.get(subordinateId).setRank(rank + 1L);
                leaders.add(subordinateId);
                withMaxRank = subordinate;
            }
            
        }
        depth = maxRank;
        least = withMaxRank;
    }


    public final Employee get(final long id) {
        final var value = map.get(id);
        return value == null ? null : value.getEmployee();
    }

    public final List<Employee> getSubordinates(final long id) {
        final var value = map.get(id);
        return value == null ? null : unmodifiableList(value.getSubordinates());
    }

    @Data
    private static class EmployeeInfo {
        private final Employee employee;
        private long rank;
        private final List<Employee> subordinates = new ArrayList<>();

        public EmployeeInfo(final Employee employee) {
            this.employee = employee;
            rank = employee.getSupervisor() == null ? 0 : -1;
        }
    }
}

