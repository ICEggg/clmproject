package org.clm.sparkstreaming.udf;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.expressions.MutableAggregationBuffer;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.StructType;

public class MyUdf extends UserDefinedAggregateFunction{

	@Override
	public StructType bufferSchema() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataType dataType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deterministic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object evaluate(Row arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialize(MutableAggregationBuffer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public StructType inputSchema() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void merge(MutableAggregationBuffer arg0, Row arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(MutableAggregationBuffer arg0, Row arg1) {
		// TODO Auto-generated method stub
		
	}

}
