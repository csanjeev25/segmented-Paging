import java.util.*;
import java.io.*;

class segmentedPaging{
	final double memorySize = 65536;
	final double pageSize = 4096;
	final int pageNoSize = 4;
	private int relativeAddr;
	private String binaryRelAddr;
	String str, offset1;
	private String phyAddr;
	private int decPhyAddr;
	private int decPageNo;
	int segNo,segOffset;
	String segN, segOff;
	private int pageOffset;
	int codeSegmentPageTable[] = {0,5,2,8,12,98,34,23,12,34,65,23,7,64,34,23};
	int dataSegmentPageTable[] = {1,4,2,7,23,5,4,65,43,12,64,86,34,67,45,32};
	int stackSegmentPageTable[] = {2,3,6,3,8,5,3,7,5,8,1,4,3,2,7,5};
	int extraSegmentPageTable[] = {5,3,1,7,53,2,7,5,3,8,9,4,2,5,4,1};
	HashMap<String,String> segmentTable = new HashMap<String,String>();
	public static void main(String[] args){
		int i, l;
		Random rand = new Random();
		segmentedPaging sp = new segmentedPaging();
		Scanner sc = new Scanner(System.in);
		System.out.println("Memory Size : 64 kB");
		for(i = 0;i < 4;i++){
			String j = Integer.toBinaryString(i);
			if(j.length() < 4)
				while(j.length() < 4)
					j = "0" + j;
			String k = Integer.toBinaryString(rand.nextInt(300));
			if(k.length() < 12)
				while(k.length() < 12)
					k = '0' + k;
			sp.segmentTable.put(j,k);
		}
		i = 0;
		int m = 0,l1 = 0;
		Iterator it = sp.segmentTable.entrySet().iterator();
		System.out.println("Segmnet No\tBasePageAddress\tSegmentLimit");
		while(it.hasNext()){
			HashMap.Entry<String,String> pair = (HashMap.Entry<String,String>)it.next();
			System.out.print("\n" + i++ + "\t");
			System.out.print("\t" + pair.getKey() + "\t\t" + pair.getValue() + "\n");
		}
		System.out.println("\nData Segment Page Table");
		for(int t = 0; t < 8 ; t++){
			System.out.print(t);
			System.out.print("\t" + sp.dataSegmentPageTable[t] + "\n");
		}
		System.out.println("\nCode Segment Page Table");
		for(int t = 0; t < 8 ; t++){
			System.out.print(t);
			System.out.print("\t" + sp.codeSegmentPageTable[t] + "\n");
		}
		System.out.println("\nStack Segment Page Table");
		for(int t = 0; t < 8 ; t++){
			System.out.print(t);
			System.out.print("\t" + sp.stackSegmentPageTable[t] + "\n");
		}
		System.out.println("\nExtra Segment Page Table" + "\n");
		for(int t = 0; t < 8 ; t++){
			System.out.print(t);
			System.out.print("\t" + sp.extraSegmentPageTable[t] + "\n");
		}
		System.out.println("Enter relative address");
		sp.relativeAddr = sc.nextInt();
		sp.binaryRelAddr = Integer.toBinaryString(sp.relativeAddr);
		while(sp.binaryRelAddr.length() < 16)
			sp.binaryRelAddr = "0" + sp.binaryRelAddr;
		
		System.out.println("Relative Address in Decimal = " + sp.relativeAddr);
		System.out.println("Relative Address in Binary = " + sp.binaryRelAddr);
		
		sp.segN = sp.binaryRelAddr.substring(0,4);
		sp.segOff = sp.binaryRelAddr.substring(5,16);
		
		sp.segNo = Integer.parseInt(sp.segN);
		sp.segOffset = Integer.parseInt(sp.segOff);
		System.out.println("Segment in Binary : " + sp.segN);
		System.out.println("Segment off in Binary : " + sp.segOff);
		System.out.println("Segment Number : " + Integer.parseInt(sp.segN,2));
		System.out.println("Segment Offset : " + Integer.parseInt(sp.segOff,2));
		
		Iterator it1 = sp.segmentTable.entrySet().iterator();
		while(it1.hasNext()){
			HashMap.Entry<String,String> pair = (HashMap.Entry)it1.next();
			String s1 = (String)pair.getKey();
			if(s1.equals(sp.segN)){
				String of = (String)pair.getValue();
				int of1 = Integer.parseInt(of,2);
				if(sp.segOffset > of1){
					System.out.println("Its a trap error");
					System.exit(0);
				}
				String pageStr = sp.segOff.substring(0,sp.pageNoSize);
				int pageNo = Integer.parseInt(pageStr);
				int decPageN = Integer.parseInt(pageStr,2);
				
				System.out.println("Page No of given relative address is " + pageNo);
				System.out.println("Page No of given relative address in decimal is " + decPageN);
				
				switch(pageNo){
					case 0 : System.out.println("Its data segment");
								String f = Integer.toBinaryString(sp.dataSegmentPageTable[pageNo]);
								System.out.println(f);
								String g = f + sp.segOff;
								System.out.println(g);
								while(g.length() < 16)
									g = '0' + g;
								int val = Integer.parseInt(g,2);
								System.out.println("Physical address of given Logical Address : " + g);
								System.out.println("Physical address of given Logical Address in Decimal : " + val);
								break;
					case 1 : System.out.println("Its code segment");
								String f1 = Integer.toBinaryString(sp.codeSegmentPageTable[pageNo]);
								String g1 = f1 + sp.segOff;
								while(g1.length() < 16)
									g1 = '0' + g1;
								int val1 = Integer.parseInt(g1,2);
								System.out.println("Physical address of given Logical Address : " + g1);
								System.out.println("Physical address of given Logical Address in Decimal : " + val1);
								break;
					case 2 : System.out.println("Its stack segment");
								String f2 = Integer.toBinaryString(sp.stackSegmentPageTable[pageNo]);
								String g2 = f2 + sp.segOff;
								while(g2.length() < 16)
									g2 = '0' + g2;
								int val2 = Integer.parseInt(g2,2);
								System.out.println("Physical address of given Logical Address : " + g2);
								System.out.println("Physical address of given Logical Address in Decimal : " + val2);
								break;
					case 3 : System.out.println("Its extra segment");
								String f3 = Integer.toBinaryString(sp.extraSegmentPageTable[pageNo]);
								String g3 = f3 + sp.segOff;
								while(g3.length() < 16)
									g3 = '0' + g3;
								int val3 = Integer.parseInt(g3,2);
								System.out.println("Physical address of given Logical Address : " + g3);
								System.out.println("Physical address of given Logical Address in Decimal : " + val3);
								break;
				}
			}
		}
	}
}
				
				
		
		
		
		
		
			
			
		
		
		
		
		
		
