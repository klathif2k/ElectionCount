Instruction to run ElectionCount

1. Unzip Problem2 folder in D drive on windows OS.
2. Please check DataStructure src folder , Two folder\packages are added 
   2.1 com.election.controller -->ElectionCount.java,ElectionCountTest.java 
   2.2 com.election.data       -->BloomFilter.java
3. Please verify Eclipse mars,oxyzen ide is present in the system otherwise install from eclipse site https://www.eclipse.org/downloads/ .
4. Download Junit-4.2 jar from google
5. Make Sure that Jdk 1.8 of Java 8 is installed on the machine .
6. Create Sample Java Project in Eclipse IDE or directly import DataStructure Project . 
7. Import ElectionCount.java file on Sample Java Project or Copy the ElectionCount.java file on the sample project .
8. Right Click on File and Click as Run as Java Application (Alt+Shift+X,j)
9. Once Program Runs it shows following operation on screen 

   Bloom Filter in progress Adding Valid Voters from file....
   Adding valid voter id and candidate id is in progress ..... 

   Election Operations

1. VoterId 
2. CandidateId


10. Enter Option 1 and provide valid voter id to get the Candidate Id .
11. Enter Option 2 and provide the Candidate Id to get the vote count recieved by him\her .

12. Test Cases Covered
========================
1. Checking voterId Length
2. Checking candidate Length
3. Checking valid and invalid voterId and CandidateId
4. Getting Candidate id for respective voterId 
5. Getting no of votes recieved by Candidate Id .

