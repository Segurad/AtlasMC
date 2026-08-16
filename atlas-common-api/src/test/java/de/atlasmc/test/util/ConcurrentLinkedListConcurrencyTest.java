package de.atlasmc.test.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.atlasmc.util.ConcurrentLinkedList;

class ConcurrentLinkedListConcurrencyTest {

    private ConcurrentLinkedList<Integer> newIntegerList() {
        return new ConcurrentLinkedList<>();
    }


    private void await(
            CountDownLatch latch) {

        assertDoesNotThrow(() -> {
            assertTrue(
                    latch.await(
                            30,
                            TimeUnit.SECONDS),
                    "Timed out waiting for workers");
        });
    }


    private void shutdown(
            ExecutorService executor) {

        executor.shutdown();

        assertDoesNotThrow(() -> {
            assertTrue(
                    executor.awaitTermination(
                            30,
                            TimeUnit.SECONDS),
                    "Executor did not terminate");
        });
    }


    private void assertBasicInvariants(ConcurrentLinkedList<Integer> list) {

        int count = 0;

        for (Integer _ : list) {
            count++;
        }

        assertEquals(
                count,
                list.size());

        assertEquals(
                list.size(),
                list.toArray().length);

        assertEquals(
                list.isEmpty(),
                list.size() == 0);

        if (list.isEmpty()) {
            assertNull(list.getHead());
            assertNull(list.getTail());
        } else {
            assertNotNull(list.getHead());
            assertNotNull(list.getTail());
        }
    }


    // ------------------------------------------------------------------------
    // Concurrent add()
    // ------------------------------------------------------------------------

    @Test
    @DisplayName("Concurrent add from multiple threads")
    void testConcurrentAdd() {

        ConcurrentLinkedList<Integer> list =
                newIntegerList();

        int threads = 8;
        int additionsPerThread = 10_000;

        ExecutorService executor =
                Executors.newFixedThreadPool(threads);

        CountDownLatch start =
                new CountDownLatch(1);

        CountDownLatch done =
                new CountDownLatch(threads);


        Set<Integer> expected =
                ConcurrentHashMap.newKeySet();


        for (int t = 0; t < threads; t++) {

            final int threadId = t;

            executor.submit(() -> {

                try {
                    start.await();

                    for (int i = 0;
                         i < additionsPerThread;
                         i++) {

                        int value =
                                threadId * additionsPerThread + i;

                        expected.add(value);

                        list.add(value);
                    }

                } catch (Exception e) {
                    fail(e);

                } finally {
                    done.countDown();
                }

            });
        }


        start.countDown();

        await(done);

        shutdown(executor);


        assertEquals(
                threads * additionsPerThread,
                list.size());


        assertTrue(
                list.contains(0));

        assertTrue(
                list.contains(additionsPerThread));

        assertTrue(
                list.contains(
                        threads * additionsPerThread - 1));


        assertBasicInvariants(list);
    }



    // ------------------------------------------------------------------------
    // Concurrent addFirst()
    // ------------------------------------------------------------------------

    @Test
    @DisplayName("Concurrent addFirst from multiple threads")
    void testConcurrentAddFirst() {

        ConcurrentLinkedList<Integer> list =
                newIntegerList();

        int threads = 8;
        int additionsPerThread = 5_000;


        ExecutorService executor =
                Executors.newFixedThreadPool(threads);


        CountDownLatch start =
                new CountDownLatch(1);

        CountDownLatch done =
                new CountDownLatch(threads);


        Set<Integer> expected =
                ConcurrentHashMap.newKeySet();


        for (int t = 0; t < threads; t++) {

            final int threadId = t;

            executor.submit(() -> {

                try {
                    start.await();

                    for (int i = 0;
                         i < additionsPerThread;
                         i++) {

                        int value =
                                threadId * additionsPerThread + i;

                        expected.add(value);

                        list.addFirst(value);
                    }

                } catch (Exception e) {
                    fail(e);

                } finally {
                    done.countDown();
                }

            });
        }


        start.countDown();

        await(done);

        shutdown(executor);


        assertEquals(
                threads * additionsPerThread,
                list.size());


        for (Integer value : expected) {
            assertTrue(
                    list.contains(value),
                    "Missing value " + value);
        }


        assertBasicInvariants(list);
    }



    // ------------------------------------------------------------------------
    // Concurrent remove()
    // ------------------------------------------------------------------------

    @Test
    @DisplayName("Concurrent remove different ranges")
    void testConcurrentRemove() {

        ConcurrentLinkedList<Integer> list =
                newIntegerList();


        int elements = 20_000;

        for (int i = 0; i < elements; i++) {
            list.add(i);
        }


        int threads = 4;

        ExecutorService executor =
                Executors.newFixedThreadPool(threads);


        CountDownLatch start =
                new CountDownLatch(1);

        CountDownLatch done =
                new CountDownLatch(threads);


        for (int t = 0; t < threads; t++) {

            final int threadId = t;

            executor.submit(() -> {

                try {

                    start.await();


                    int startValue =
                            threadId *
                            (elements / threads);

                    int endValue =
                            startValue +
                            (elements / threads);


                    for (int i = startValue;
                         i < endValue;
                         i++) {

                        list.remove(i);
                    }


                } catch (Exception e) {

                    fail(e);

                } finally {

                    done.countDown();
                }

            });
        }


        start.countDown();

        await(done);

        shutdown(executor);


        assertEquals(
                0,
                list.size());


        assertTrue(list.isEmpty());

        assertNull(list.getHead());
        assertNull(list.getTail());


        assertBasicInvariants(list);
    }

    // ------------------------------------------------------------------------
    // Concurrent add/remove
    // ------------------------------------------------------------------------

    @Test
    @DisplayName("Concurrent add and remove operations")
    void testConcurrentAddAndRemove() {

        ConcurrentLinkedList<Integer> list =
                new ConcurrentLinkedList<>();

        int threads = 8;
        int operations = 5000;


        ExecutorService executor =
                Executors.newFixedThreadPool(threads);


        CountDownLatch start =
                new CountDownLatch(1);

        CountDownLatch done =
                new CountDownLatch(threads);


        AtomicInteger successfulAdds =
                new AtomicInteger();

        AtomicInteger successfulRemoves =
                new AtomicInteger();


        for (int t = 0; t < threads; t++) {

            final int threadId = t;


            executor.submit(() -> {

                try {

                    start.await();


                    for (int i = 0;
                         i < operations;
                         i++) {

                        int value =
                                threadId * operations + i;


                        list.add(value);

                        successfulAdds.incrementAndGet();


                        if (list.remove(value)) {
                            successfulRemoves.incrementAndGet();
                        }
                    }


                } catch (Exception e) {

                    fail(e);

                } finally {

                    done.countDown();
                }

            });
        }


        start.countDown();

        await(done);

        shutdown(executor);


        assertEquals(
                successfulAdds.get()
                        - successfulRemoves.get(),
                list.size());


        assertBasicInvariants(list);
    }



    // ------------------------------------------------------------------------
    // Concurrent iteration
    // ------------------------------------------------------------------------

    @Test
    @DisplayName("Concurrent iteration while modifying")
    void testConcurrentIteration() {

        ConcurrentLinkedList<Integer> list =
                new ConcurrentLinkedList<>();


        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }


        ExecutorService executor =
                Executors.newFixedThreadPool(2);


        CountDownLatch start =
                new CountDownLatch(1);

        CountDownLatch done =
                new CountDownLatch(2);


        AtomicInteger iterations =
                new AtomicInteger();


        executor.submit(() -> {

            try {

                start.await();


                for (int r = 0; r < 100; r++) {

                    ConcurrentLinkedList.LinkedListIterator<Integer> iterator =
                            list.iterator();


                    int guard = 0;


                    while (iterator.hasNext()) {

                        iterator.next();

                        iterations.incrementAndGet();


                        guard++;

                        assertTrue(
                                guard < 100_000,
                                "Iterator appears stuck");
                    }
                }


            } catch (Exception e) {

                fail(e);

            } finally {

                done.countDown();
            }

        });



        executor.submit(() -> {

            try {

                start.await();


                for (int i = 0; i < 5000; i++) {

                    list.add(i + 10000);

                    if (i % 2 == 0) {
                        list.remove(i + 10000);
                    }
                }


            } catch (Exception e) {

                fail(e);

            } finally {

                done.countDown();
            }

        });



        start.countDown();

        await(done);

        shutdown(executor);


        assertTrue(
                iterations.get() > 0);


        assertBasicInvariants(list);
    }



    // ------------------------------------------------------------------------
    // Concurrent clear()
    // ------------------------------------------------------------------------

    @Test
    @DisplayName("Concurrent clear while adding")
    void testConcurrentClear() {

        ConcurrentLinkedList<Integer> list =
                new ConcurrentLinkedList<>();


        ExecutorService executor =
                Executors.newFixedThreadPool(2);


        CountDownLatch start =
                new CountDownLatch(1);

        CountDownLatch done =
                new CountDownLatch(2);


        executor.submit(() -> {

            try {

                start.await();


                for (int i = 0; i < 1000; i++) {

                    list.clear();
                }


            } catch (Exception e) {

                fail(e);

            } finally {

                done.countDown();
            }

        });



        executor.submit(() -> {

            try {

                start.await();


                for (int i = 0; i < 10000; i++) {

                    list.add(i);
                }


            } catch (Exception e) {

                fail(e);

            } finally {

                done.countDown();
            }

        });



        start.countDown();

        await(done);

        shutdown(executor);



        // Verify list can still be used
        list.clear();

        list.add(123);

        assertEquals(
                1,
                list.size());

        assertEquals(
                Integer.valueOf(123),
                list.getHead());

        assertEquals(
                Integer.valueOf(123),
                list.getTail());


        assertBasicInvariants(list);
    }



    // ------------------------------------------------------------------------
    // Concurrent contains()
    // ------------------------------------------------------------------------

    @Test
    @DisplayName("Concurrent contains while adding and removing")
    void testConcurrentContains() {

        ConcurrentLinkedList<Integer> list =
                new ConcurrentLinkedList<>();


        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }


        int threads = 6;


        ExecutorService executor =
                Executors.newFixedThreadPool(threads);


        CountDownLatch start =
                new CountDownLatch(1);

        CountDownLatch done =
                new CountDownLatch(threads);


        for (int t = 0; t < threads; t++) {


            final int thread =
                    t;


            executor.submit(() -> {

                try {

                    start.await();


                    for (int i = 0;
                         i < 5000;
                         i++) {


                        int value =
                                (thread * 5000) + i;


                        list.add(value);


                        list.contains(value);


                        list.remove(value);


                        list.contains(value);
                    }


                } catch (Exception e) {

                    fail(e);

                } finally {

                    done.countDown();
                }

            });
        }


        start.countDown();

        await(done);

        shutdown(executor);


        assertBasicInvariants(list);
    }



    // ------------------------------------------------------------------------
    // Stress test
    // ------------------------------------------------------------------------

    @Test
    @DisplayName("Large list stress test")
    void testStressLargeList() {

        ConcurrentLinkedList<Integer> list =
                new ConcurrentLinkedList<>();


        int amount = 100_000;


        for (int i = 0; i < amount; i++) {
            list.add(i);
        }


        assertEquals(
                amount,
                list.size());


        int count = 0;

        for (Integer _ : list) {
            count++;
        }


        assertEquals(
                amount,
                count);


        assertEquals(
                amount,
                list.toArray().length);



        for (int i = 0; i < amount; i += 2) {

            assertTrue(
                    list.remove(i));
        }


        assertEquals(
                amount / 2,
                list.size());


        assertBasicInvariants(list);
    }
}