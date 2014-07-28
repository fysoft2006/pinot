package com.linkedin.pinot.index.block.intarray.utils;

import com.linkedin.pinot.index.common.BlockValIterator;
import com.linkedin.pinot.index.common.Constants;
import com.linkedin.pinot.segments.v1.segment.utils.HeapCompressedIntArray;
import com.linkedin.pinot.segments.v1.segment.utils.IntArray;


public class UnSortedBlockValSet {

  /**
   * 
   * @param values
   * @return
   */
  public static BlockValIterator getDefaultIterator(final IntArray intArray, final int start,
      final int end) {
    return new BlockValIterator() {
      int counter = start;

      @Override
      public boolean reset() {
        counter = start;
        return true;
      }

      @Override
      public int nextVal() {
        if (counter < end)
          return intArray.getInt(counter++);
        return Constants.EOF;
      }

      @Override
      public int currentValId() {
        return intArray.getInt(counter);
      }

      @Override
      public int currentDocId() {
        return counter;
      }
    };
  }

  /**
   * 
   * @param start
   * @param end
   * @param values
   * @return
   */
  public static BlockValIterator getRangeMatchIterator(final IntArray intArray, final int start, final int end,
      final int rangeStart, final int rangeEnd) {
    return new BlockValIterator() {
      int counter = start;

      @Override
      public boolean reset() {
        counter = start;
        return true;
      }

      @Override
      public int nextVal() {
        while (counter < end) {
          int val = intArray.getInt(counter);
          if (val >= start & val <= end) {
            counter++;
            return val;
          }
          counter++;
        }
        return Constants.EOF;
      }

      @Override
      public int currentValId() {
        return intArray.getInt(counter);
      }

      @Override
      public int currentDocId() {
        return counter;
      }
    };
  }

  /**
   * Currently not taking dictionary into account, in reality there will be a dictionary
   * @param valueToLookup
   * @param values
   * @return
   */
  public static BlockValIterator getNoEqualsMatchIterator(final int valueToLookup, final IntArray intArray,
      final int start, final int end) {
    return new BlockValIterator() {
      int counter = start;

      @Override
      public boolean reset() {
        counter = start;
        return true;
      }

      @Override
      public int nextVal() {
        while (counter < end) {
          int val = intArray.getInt(counter);
          if (valueToLookup != val) {
            counter++;
            return val;
          }
          counter++;
        }
        return Constants.EOF;
      }

      @Override
      public int currentValId() {
        return intArray.getInt(counter);
      }

      @Override
      public int currentDocId() {
        return counter;
      }
    };
  }

  /**
   * Currently not taking dictionary into account, in reality there will be a dictionary
   * @param valueToLookup
   * @param values
   * @return
   */
  public static BlockValIterator getEqualityMatchIterator(final int valueToLookup, final IntArray intArray,
      final int start, final int end) {
    return new BlockValIterator() {
      int counter = start;

      @Override
      public boolean reset() {
        counter = start;
        return true;
      }

      @Override
      public int nextVal() {
        while (counter < end) {
          int val = intArray.getInt(counter);
          if (valueToLookup == val) {
            counter++;
            return val;
          }
          counter++;
        }
        return Constants.EOF;
      }

      @Override
      public int currentValId() {
        return intArray.getInt(counter);
      }

      @Override
      public int currentDocId() {
        return counter;
      }
    };
  }
}