import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class GoTest {
	public GoTest()
	{
		try {
			FileReader fr = new FileReader("C:\\Users\\Administrator\\Desktop\\uniprot-soybean.txt");  
	 		BufferedReader br = new BufferedReader(fr); 
//        	PrintWriter out = new PrintWriter(new FileWriter("C:\\Users\\Administrator\\Desktop\\excel.xls"));
	 		String str="";
	 		int lineNum=0;
			while((str=br.readLine())!=null && lineNum<1500){
				lineNum++;
				System.out.println(str);
	 			
	 		}
		
	 		br.close();
	 		fr.close();
		} catch (Exception e) {
		System.out.println("读取txt文档内容时，发生异常！");
		e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new GoTest();

	}

}
