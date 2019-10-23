package com.election.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.election.data.BloomFilter;

public class ElectionCount {
	HashMap<String, String> electionMap = new HashMap<String, String>();
	BloomFilter bf = null;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public void readFile() {
		BufferedReader objReader = null;
		try {
			LOGGER.info("Bloom Filter in progress Adding Valid Voters from file....");
			String strCurrentLine;
			objReader = new BufferedReader(new FileReader("D:/Problem2/validVotersList.txt"));
			// int bloomSetSize = (int) objReader.lines().count();
			// LOGGER.info(" The no of voters " + bloomSetSize);
			bf = new BloomFilter(50000, 36);
			while ((strCurrentLine = objReader.readLine()) != null) {
				bf.add(strCurrentLine.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objReader != null)
					objReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void addElection() {
		BufferedReader objReader = null;
		String strCurrentLine;
		String voterId = null;
		String candidateId = null;

		try {

			LOGGER.setLevel(Level.FINE);
			LOGGER.info("Adding valid voter id and candidate id is in progress ..... ");
			objReader = new BufferedReader(new FileReader("D:/Problem2/votersCandList.txt"));
			while ((strCurrentLine = objReader.readLine()) != null) {
				if (strCurrentLine != null && (strCurrentLine.length() > 1 && strCurrentLine.length() <= 6)
						|| strCurrentLine.length() > 6) {
					voterId = strCurrentLine.substring(0, 6);
					checkVoterLength(voterId);
				}
				if (strCurrentLine != null && strCurrentLine.length() > 6) {
					candidateId = strCurrentLine.substring(7, strCurrentLine.length()).trim();
					checkCandidateLength(candidateId);
				}
				if (bf.contains(voterId)) {
					electionMap.put(voterId, candidateId);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objReader != null)
					objReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	public boolean checkCandidateLength(String candidateId) {
		boolean checkCandidateId = false;
		if (candidateId != null && candidateId.length() != 3) {
			LOGGER.info("Invalid Candidate Id [ " + candidateId + " ] Length , Should be 3 ");
		} else
			checkCandidateId = true;

		return checkCandidateId;
	}

	public boolean checkVoterLength(String voterId) {
		boolean checkvoterId = false;
		if (voterId != null && voterId.length() != 6) {
			LOGGER.info("Invalid Voter Id [ " + voterId + " ] Length , Should be 6 ");
		} else
			checkvoterId = true;

		return checkvoterId;
	}

	public boolean findCandidateID(String voterId) {
		boolean checkVoterId = false;
		try {
			if (electionMap.containsKey(voterId)) {
				checkVoterId = true;
				LOGGER.info(" The candidate Id is [ " + electionMap.get(voterId) + " ] ");
			} else {
				LOGGER.info(" Invalid voter Id " + " [ " + voterId + " ] ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkVoterId;
	}

	public Integer voteCount(String candidateId) {
		boolean candidateCheck = false;
		Integer count = 0;
		try {
			for (Map.Entry<String, String> electioneobj : electionMap.entrySet()) {
				if (electioneobj.getValue().equals(candidateId)) {
					count++;
					candidateCheck = true;
				}
			}

			if (candidateCheck) {
				LOGGER.info("The total no of votes recieved is [ " + count + " ] ");
			} else {
				LOGGER.info("Invalid CandidateID " + "[ " + candidateId + " ] ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	public static void main(String[] args) {
		ElectionCount electionCount = new ElectionCount();
		electionCount.readFile();
		electionCount.addElection();
		Scanner scan = new Scanner(System.in);
		char ch;
		/* Perform bloom filter operations */
		do {
			LOGGER.info("\nElection Operations Enter 1 or 2 \n");
			LOGGER.info("1. VoterId ");
			LOGGER.info("2. CandidateId");
			int choice = scan.nextInt();
			switch (choice) {
			case 1:
				LOGGER.info(" Enter voterId \n");
				scan.nextLine().trim();
				String voterId = scan.nextLine().trim();
				// LOGGER.info(findCandidateID(scan.nextLine().trim()));
				electionCount.findCandidateID(voterId);
				break;
			case 2:
				LOGGER.info(" Enter candidateId to get the no of vote recieved by him/her \n");
				scan.nextLine().trim();
				String candidateId = scan.nextLine().trim();
				electionCount.voteCount(candidateId);
				break;
			default:
				LOGGER.info("Wrong Entry \n ");
				break;
			}
			LOGGER.info("\nDo you want to continue (Type y or n) \n");
			ch = scan.next().charAt(0);
		} while (ch == 'Y' || ch == 'y');
	}
}
