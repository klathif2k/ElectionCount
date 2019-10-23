package com.election.controller;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)	
public class ElectionCountTest {
   static ElectionCount electionCount = null;
   private final static Logger LOGGER =  
           Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 
   String voterId,CandidateId;
  
   
   public ElectionCountTest(String voterId,String candidateID){
	   this.voterId=voterId;
	   this.CandidateId=candidateID;
   }
   
   @Parameterized.Parameters
   public static Collection voterCandidateList() {
      return Arrays.asList(new String[][] {
         { "200000", "673" },
         { "2000", "552" },
         { "200008", "379" },
         { "750000", "49" },
         { "200010", "369"}
      });
   }
   
 
   @Test
   public void checkVoterLength() {
	   LOGGER.info(" Test  : Checking voter length " + "[ " + this.voterId + " ]");
	   assertEquals(true, electionCount.checkVoterLength(this.voterId));
   }
   
   @Test
   public void checkCandidateLength() {
	   LOGGER.info(" Test  : Checking candidate Length " + "[ " + this.CandidateId + " ]");
	   Assert.assertEquals(true, electionCount.checkCandidateLength(this.CandidateId));
   }
   
   
   @Test
   public void getValidCandidateId() {
	    LOGGER.info(" Test  : Getting Candidate ID for valid voterId " + "[ " + this.voterId + " ]");
	    Assert.assertEquals(true, electionCount.findCandidateID(this.voterId));
   }
   
   @Test
   public void getVoterCount() {
	   LOGGER.info(" Test  : getting Vote Count for particular candidate Id  " + "[ " + this.CandidateId + " ]");
	   Assert.assertNotNull(electionCount.voteCount(this.CandidateId));
   }
 
   public static void main(String[] args) {
	    try{
	     electionCount= new ElectionCount(); 
	     electionCount.readFile();
	     electionCount.addElection();
	     Result result = JUnitCore.runClasses(ElectionCountTest.class);
	      for (Failure failure : result.getFailures()) {
	    	 LOGGER.info(failure.toString());
	      }
//	      LOGGER.info("Result=="+result.wasSuccessful());
	   }
	    catch(Exception e){
	    	LOGGER.warning("Error : " + e.getMessage());
	    	e.printStackTrace();
	    }
 }
}