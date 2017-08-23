package com.fmi.twitter.mining.util;

import java.io.Serializable;
import java.util.Comparator;

import scala.Tuple2;

public class TupleComparator implements Comparator<Tuple2<String, Integer>>, Serializable {

    public static TupleComparator getInstance(){
        return new TupleComparator();
    }

    @Override
    public int compare(Tuple2<String, Integer> o1, Tuple2<String, Integer> o2) {
        return o2._2() - o1._2();
    }
}
