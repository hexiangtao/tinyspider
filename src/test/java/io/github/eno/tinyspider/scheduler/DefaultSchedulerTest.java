package io.github.eno.tinyspider.scheduler;

import io.github.eno.tinyspider.http.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultSchedulerTest {

    private DefaultScheduler scheduler;

    @BeforeEach
    void setUp() {
        scheduler = new DefaultScheduler();
    }

    @Test
    void testPushAndPoll() {
        Request request = new Request("http://example.com");
        scheduler.push(request);
        assertTrue(scheduler.hasNext());
        Request polledRequest = scheduler.poll();
        assertEquals(request, polledRequest);
        assertFalse(scheduler.hasNext());
    }

    @Test
    void testDuplicateRequest() {
        Request request1 = new Request("http://example.com");
        Request request2 = new Request("http://example.com");
        scheduler.push(request1);
        scheduler.push(request2);
        assertTrue(scheduler.hasNext());
        scheduler.poll();
        assertFalse(scheduler.hasNext());
    }
}
