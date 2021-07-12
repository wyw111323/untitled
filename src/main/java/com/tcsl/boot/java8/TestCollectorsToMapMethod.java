package com.tcsl.boot.java8;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collector.Characteristics.*;
import static java.util.stream.Collectors.*;

public class TestCollectorsToMapMethod {

    List<Transaction> transactions;

    @Before
    public void init() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        this.transactions = transactions;
    }

    @Test
    public void test01() {
//            List<com.tcsl.boot.java8.StudentDTO> studentDTOS = new ArrayList<>();
//            studentDTOS.add(new com.tcsl.boot.java8.StudentDTO(1,"xixi"));
//            studentDTOS.add(new com.tcsl.boot.java8.StudentDTO(1,null));
//            studentDTOS.add(new com.tcsl.boot.java8.StudentDTO(3,"maomi"));
//
//            Map<Integer, String> collect1 = studentDTOS.stream()
//                    .collect(Collectors.toMap(com.tcsl.boot.java8.StudentDTO::getCode, studentDTO -> Optional.ofNullable(studentDTO.getName()).orElse("the value null"),(k1, k2) -> k2));
//
//            Map<Integer, String> collect2 = studentDTOS.stream()
//                .collect(HashMap::new, (key, value) -> key.put(value.getCode(), value.getName()), HashMap::putAll);
//
//        for (Map.Entry<Integer, String> integerStringEntry : collect1.entrySet()) {
//            System.out.println(" use Optional  key:" + integerStringEntry.getKey() + "-> value: " + integerStringEntry.getValue());
//        }
//
//        for (Map.Entry<Integer, String> integerStringEntry : collect2.entrySet()) {
//            System.out.println("use impl key:" + integerStringEntry.getKey() + "-> value: " + integerStringEntry.getValue());
//        }

        List<Integer> integers1 = Arrays.asList(1, 2, 3, 4);
        List<Integer> integers2 = Arrays.asList(4, 5, 6, 7);

//        integers1.stream().flatMap(v -> integers2.stream().distinct()).forEach(System.out::println);

//        Stream.of(integers1,integers2).flatMap(Collection::stream).distinct().peek(System.out::println).forEach(System.out::println);
    }

    @Test
    public void test02() {

        transactions.stream().filter(v -> v.getYear() == 2011).sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList()).forEach(System.out::println);
    }

    @Test
    public void testGroupBy() {
        transactions.stream().map(Transaction::getTrader).distinct().collect(groupingBy(Trader::getCity)).forEach((k, v) -> System.out.println(k + v));
    }

    @Test
    public void test03() {
        transactions.stream().map(Transaction::getTrader).map(Trader::getName).distinct().sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.toList()).forEach(print());
    }

    @Test
    public void test06() {
        transactions.stream().map(Transaction::getTrader).map(Trader::getName).distinct().sorted(String.CASE_INSENSITIVE_ORDER).reduce((s1, s2) -> s1 + "," + s2).ifPresent(print());
    }

    @Test
    public void test04() {
        transactions.stream().map(Transaction::getTrader).distinct().filter(v -> v.getCity().equals("Milan")).findAny().ifPresent(print());
    }

    @Test
    public void test05() {
        System.out.println(transactions.stream().filter(v -> v.getTrader().getCity().equals("Cambridge")).peek(print()).map(Transaction::getValue).reduce(0, Integer::sum));
    }

    @Test
    public void test07() {
        transactions.stream().collect(groupingBy(transaction -> {
            if (transaction.getValue() <= 400) return CaloricLevel.DIET;
            else if (transaction.getValue() <= 700) return CaloricLevel.NORMAL;
            else return CaloricLevel.FAT;
        }, mapping(Transaction::getTrader, groupingBy(Trader::getName)))).forEach(printBi());
    }

    @Test
    // 测试质数
    public void test8() {
         IntStream.rangeClosed(2, 100).boxed()
                .collect(new PrimeNumbersCollector()).forEach(print());
    }

    public <T> Consumer<T> print() {
        return System.out::println;
    }

    public <K, V> BiConsumer<K, V> printBi() {
        return (k, v) -> System.out.println(k + " " + v);
    }

    public enum CaloricLevel {DIET, NORMAL, FAT}

    /**
     * 自定义实现查找质数
     */
    static class PrimeNumbersCollector implements Collector<Integer,Map<Boolean,List<Integer>>,List<Integer>> {

        @Override
        public Supplier<Map<Boolean, List<Integer>>> supplier() {
            return () -> new HashMap<Boolean, List<Integer>>() {{
                put(true,new ArrayList<>());
                put(false,new ArrayList<>());
            }};
        }

        @Override
        public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
            return (Map<Boolean, List<Integer>> acc, Integer next) -> {
                acc.get(isPrime(acc.get(true),next)).add(next);
            };
        }

        @Override
        public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
             return (Map<Boolean, List<Integer>> map1,
                           Map<Boolean, List<Integer>> map2) -> {
                map1.get(true).addAll(map2.get(true));
                map1.get(false).addAll(map2.get(false));
                return map1;
            };
        }

        @Override
        public Function<Map<Boolean, List<Integer>>, List<Integer>> finisher() {


            return (Map<Boolean, List<Integer>> k ) -> {
                Set<Boolean> booleans = k.keySet();
                Collection<List<Integer>> values = k.values();
                return null;
            };
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH,CONCURRENT,UNORDERED));
        }
    }
    public static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
        int i = 0;
        for (A item : list) {
            if (!p.test(item)) {
                return list.subList(0, i);
            }
            i++;
        }
        return list;
    }

    public static boolean isPrime(List<Integer> primes, int candidate){
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return takeWhile(primes, i -> i <= candidateRoot)
                .stream()
                .noneMatch(p -> candidate % p == 0);
    }
}
