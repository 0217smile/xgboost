package ml.dmlc.xgboost4j.java;

/**
 * A mini-batch of data that can be converted to DMatrix.
 * The data is in sparse matrix CSR format.
 *
 * This class is used to support advanced creation of DMatrix from Iterator of DataBatch,
 */
public class DataBatch {
  /** The offset of each rows in the sparse matrix */
  long[] rowOffset = null;
  /** weight of each data point, can be null */
  float[] weight = null;
  /** label of each data point, can be null */
  float[] label = null;
  /** index of each feature(column) in the sparse matrix */
  int[] featureIndex = null;
  /** value of each non-missing entry in the sparse matrix */
  float[] featureValue = null;

  public DataBatch() {}

  public DataBatch(long[] rowOffset, float[] weight, float[] label, int[] featureIndex,
                   float[] featureValue) {
    this.rowOffset = rowOffset;
    this.weight = weight;
    this.label = label;
    this.featureIndex = featureIndex;
    this.featureValue = featureValue;
  }


  /**
   * Get number of rows in the data batch.
   * @return Number of rows in the data batch.
   */
  public int numRows() {
    return rowOffset.length - 1;
  }

  /**
   * Shallow copy a DataBatch
   * @return a copy of the batch
   */
  public DataBatch shallowCopy() {
    DataBatch b = new DataBatch();
    b.rowOffset = this.rowOffset;
    b.weight = this.weight;
    b.label = this.label;
    b.featureIndex = this.featureIndex;
    b.featureValue = this.featureValue;
    return b;
  }
}
