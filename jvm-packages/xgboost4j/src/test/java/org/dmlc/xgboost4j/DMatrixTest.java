/*
 Copyright (c) 2014 by Contributors 

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
    
 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package org.dmlc.xgboost4j;

import junit.framework.TestCase;
import org.dmlc.xgboost4j.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * test cases for DMatrix
 *
 * @author hzx
 */
public class DMatrixTest {

  @Test
  public void testCreateFromFile() throws XGBoostError {
    //create DMatrix from file
    org.dmlc.xgboost4j.DMatrix dmat = new org.dmlc.xgboost4j.DMatrix("../../demo/data/agaricus.txt.test");
    //get label
    float[] labels = dmat.getLabel();
    //check length
    TestCase.assertTrue(dmat.rowNum() == labels.length);
    //set weights
    float[] weights = Arrays.copyOf(labels, labels.length);
    dmat.setWeight(weights);
    float[] dweights = dmat.getWeight();
    TestCase.assertTrue(Arrays.equals(weights, dweights));
  }

  @Test
  public void testCreateFromCSR() throws XGBoostError {
    //create Matrix from csr format sparse Matrix and labels
    /**
     * sparse matrix
     * 1 0 2 3 0
     * 4 0 2 3 5
     * 3 1 2 5 0
     */
    float[] data = new float[]{1, 2, 3, 4, 2, 3, 5, 3, 1, 2, 5};
    int[] colIndex = new int[]{0, 2, 3, 0, 2, 3, 4, 0, 1, 2, 3};
    long[] rowHeaders = new long[]{0, 3, 7, 11};
    org.dmlc.xgboost4j.DMatrix dmat1 = new org.dmlc.xgboost4j.DMatrix(rowHeaders, colIndex, data, org.dmlc.xgboost4j.DMatrix.SparseType.CSR);
    //check row num
    System.out.println(dmat1.rowNum());
    TestCase.assertTrue(dmat1.rowNum() == 3);
    //test set label
    float[] label1 = new float[]{1, 0, 1};
    dmat1.setLabel(label1);
    float[] label2 = dmat1.getLabel();
    TestCase.assertTrue(Arrays.equals(label1, label2));
  }

  @Test
  public void testCreateFromDenseMatrix() throws XGBoostError {
    //create DMatrix from 10*5 dense matrix
    int nrow = 10;
    int ncol = 5;
    float[] data0 = new float[nrow * ncol];
    //put random nums
    Random random = new Random();
    for (int i = 0; i < nrow * ncol; i++) {
      data0[i] = random.nextFloat();
    }

    //create label
    float[] label0 = new float[nrow];
    for (int i = 0; i < nrow; i++) {
      label0[i] = random.nextFloat();
    }

    org.dmlc.xgboost4j.DMatrix dmat0 = new org.dmlc.xgboost4j.DMatrix(data0, nrow, ncol);
    dmat0.setLabel(label0);

    //check
    TestCase.assertTrue(dmat0.rowNum() == 10);
    TestCase.assertTrue(dmat0.getLabel().length == 10);

    //set weights for each instance
    float[] weights = new float[nrow];
    for (int i = 0; i < nrow; i++) {
      weights[i] = random.nextFloat();
    }
    dmat0.setWeight(weights);

    TestCase.assertTrue(Arrays.equals(weights, dmat0.getWeight()));
  }
}
