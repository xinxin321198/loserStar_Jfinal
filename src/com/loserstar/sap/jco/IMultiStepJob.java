package com.loserstar.sap.jco;

public interface IMultiStepJob {
	public boolean runNextStep();  
	  
    String getName();  
  
    public void cleanUp();
}
