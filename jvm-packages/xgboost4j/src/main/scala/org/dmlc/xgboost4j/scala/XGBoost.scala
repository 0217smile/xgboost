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

package org.dmlc.xgboost4j.scala

import _root_.scala.collection.JavaConverters._
import org.dmlc.xgboost4j.{IEvaluation, IObjective, XGBoost => JXGBoost}

object XGBoost {

  def train(params: Map[String, AnyRef], dtrain: DMatrix, round: Int,
            watches: Map[String, DMatrix], obj: IObjective, eval: IEvaluation): Booster = {
    val jWatches = watches.map{case (name, matrix) => (name, matrix.jDMatrix)}
    val xgboostInJava = JXGBoost.train(params.asJava, dtrain.jDMatrix, round, jWatches.asJava,
      obj, eval)
    new ScalaBoosterImpl(xgboostInJava)
  }

  def crossValiation(
      params: Map[String, AnyRef],
      data: DMatrix,
      round: Int,
      nfold: Int,
      metrics: Array[String],
      obj: EvalTrait,
      eval: ObjectiveTrait): Array[String] = {
    JXGBoost.crossValiation(params.asJava, data.jDMatrix, round, nfold, metrics,
      obj.asInstanceOf[IObjective], eval.asInstanceOf[IEvaluation])
  }

  def initBoostModel(params: Map[String, AnyRef], dMatrixs: Array[DMatrix]): Booster = {
    val xgboostInJava = JXGBoost.initBoostingModel(params.asJava, dMatrixs.map(_.jDMatrix))
    new ScalaBoosterImpl(xgboostInJava)
  }

  def loadBoostModel(params: Map[String, AnyRef], modelPath: String): Booster = {
    val xgboostInJava = JXGBoost.loadBoostModel(params.asJava, modelPath)
    new ScalaBoosterImpl(xgboostInJava)
  }
}
