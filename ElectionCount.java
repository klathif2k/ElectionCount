
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/* Class BloomFilter */
class BloomFilter {
	private byte[] set;
	private int keySize, setSize, size;
	private MessageDigest md;

	/* Constructor */
	public BloomFilter(int capacity, int k) {
		setSize = capacity;
		set = new byte[setSize];
		keySize = k;
		size = 0;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Error : MD5 Hash not found");
		}
	}

	/* Function to clear bloom set */
	public void makeEmpty() {
		set = new byte[setSize];
		size = 0;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Error : MD5 Hash not found");
		}
	}

	/* Function to check is empty */
	public boolean isEmpty() {
		return size == 0;
	}

	/* Function to get size of objects added */
	public int getSize() {
		return size;
	}

	/* Function to get hash - MD5 */
	private int getHash(int i) {
		md.reset();
		byte[] bytes = ByteBuffer.allocate(4).putInt(i).array();
		md.update(bytes, 0, bytes.length);
		return Math.abs(new BigInteger(1, md.digest()).intValue()) % (set.length - 1);
	}

	/* Function to add an object */
	public void add(Object obj) {
		int[] tmpset = getSetArray(obj);
		for (int i : tmpset) {
			set[i] = 1;
//			System.out.println("set[" + i + "] = " + set[i]);
		}
		size++;
	}

	/* Function to check is an object is present */
	public boolean contains(Object obj) {
		int[] tmpset = getSetArray(obj);
		for (int i : tmpset)
			if (set[i] != 1)
				return false;
		return true;
	}

	/* Function to get set array for an object */
	private int[] getSetArray(Object obj) {
		int[] tmpset = new int[keySize];
		tmpset[0] = getHash(obj.hashCode());
		//System.out.println(tmpset[0]);
		//System.out.println("The Key Size " + keySize);
		for (int i = 1; i < keySize; i++) {
			tmpset[i] = (getHash(tmpset[i - 1]));
			//System.out.println("tmpset[" + i + "] = " + tmpset[i]);
		}
		return tmpset;
	}
}

public class ElectionCount {
	static HashMap<String, String> electionMap = new HashMap<String, String>();
	static BloomFilter bf = null;

	public static void readFile() {
		BufferedReader objReader = null;
		try {
			System.out.println("Bloom Filter in progress Adding Valid Voters from file....");
			String strCurrentLine;
			objReader = new BufferedReader(new FileReader("D:/Problem2/validVotersList.txt"));
			//int bloomSetSize = (int) objReader.lines().count();
			////System.out.println(" The no of voters " + bloomSetSize);
			bf = new BloomFilter(100000, 36);
			while ((strCurrentLine = objReader.readLine()) != null) {
				//System.out.println("The voterid in validVotersList1 file ******** " + strCurrentLine.trim());
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

	public static void addElection() {
		BufferedReader objReader = null;
		String strCurrentLine;
		String voterId = null;
		String candidateId = null;

		try {
			System.out.println("Adding valid voter id and candidate id is in progress ..... ");
			objReader = new BufferedReader(new FileReader("D:/Problem2/votersCandList.txt"));
			while ((strCurrentLine = objReader.readLine()) != null) {
//				System.out.println("The voter id in validCandList1  " + strCurrentLine);
				if (strCurrentLine != null && (strCurrentLine.length() > 1 && strCurrentLine.length() <= 6)
						|| strCurrentLine.length() > 6) {
					voterId = strCurrentLine.substring(0, 6);
					//System.out.println("VoterId " + voterId);
				}
				if (strCurrentLine != null && strCurrentLine.length() > 6) {
					candidateId = strCurrentLine.substring(7, strCurrentLine.length()).trim();
					//System.out.println("candidateId " + candidateId);
				}
				//System.out.println(" The bf object " + bf + "   " + voterId);
				if (bf.contains(voterId)) {
					//System.out.println(" putting values in electionMap ##### " + voterId);
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

	public static void findCandidateID(String voterId) {
		try {
			if (electionMap.containsKey(voterId)) {
				System.out.println("The candidate Id is " + electionMap.get(voterId));
			} else {
				System.out.println("Invalid voter Id ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return 1;
	}

	public static void voteCount(String candidateId) {
		try {
			int count = 0;
			boolean candidateCheck = false;
			for (Map.Entry<String, String> electioneobj : electionMap.entrySet()) {
				//System.out.format("key: %s, value: %s%n", electioneobj.getKey(), electioneobj.getValue());
				if (electioneobj.getValue().equals(candidateId)) {
					count++;
					candidateCheck = true;
				}
			}

			if (candidateCheck) {
				System.out.println("The total no of votes recieved is " + count);
			} else {
				System.out.println("Invalid CandidateID ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		readFile();
		addElection();
		Scanner scan = new Scanner(System.in);
		// //System.out.println("Bloom Filter Test\n");

		// //System.out.println("Enter set capacity and key size");
		// BloomFilter bf = new BloomFilter(scan.nextInt() , scan.nextInt());

		char ch;
		/* Perform bloom filter operations */
		do {
			System.out.println("\nElection Operations\n");
			System.out.println("1. VoterId ");
			System.out.println("2. CandidateId");
			int choice = scan.nextInt();
			switch (choice) {
			case 1:
				System.out.println(" Enter voterId \n");
				scan.nextLine().trim();
				String voterId = scan.nextLine().trim();
				////System.out.println(findCandidateID(scan.nextLine().trim()));
				findCandidateID(voterId);
				break;
			case 2:
				System.out.println(" Enter candidateId to get the no of vote recieved by him \n");
				scan.nextLine().trim();
				String candidateId = scan.nextLine().trim();
				voteCount(candidateId);
				break;
			default:
				System.out.println("Wrong Entry \n ");
				break;
			}
			System.out.println("\nDo you want to continue (Type y or n) \n");
			ch = scan.next().charAt(0);
		} while (ch == 'Y' || ch == 'y');
	}
}
