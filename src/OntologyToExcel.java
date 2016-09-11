import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class OntologyToExcel {

	public OntologyToExcel()
	{
		try {
			FileReader fr = new FileReader("C:\\Users\\Administrator\\Desktop\\SOY_1.ont");  
	 		BufferedReader br = new BufferedReader(fr); 
        	PrintWriter out = new PrintWriter(new FileWriter("C:\\Users\\Administrator\\Desktop\\excel.xls"));
	 		String str="";
			String name = "";
			String is_a = "";
			String def = "";
			boolean hasname =false;
			boolean hasis_a =false;
			boolean hasdef =false;
			while((str=br.readLine())!=null){

	 			if(str.startsWith("[Term]")&&hasname &&hasdef &&hasis_a)
	 				{
	 					out.println(name+"\t"+is_a+"\t"+def);
	 					hasname =false;
	 					hasis_a =false;
	 					hasdef =false;
	 				}
	 			if(str.startsWith("[Term]") &&hasname &&hasis_a && !hasdef)
	 				{
	 					out.println(name+"\t"+is_a);
	 					hasname =false;
	 					hasis_a =false;
	 					hasdef =false;
	 				}
	 			if(str.startsWith("[Term]") &&hasname &&hasdef && !hasis_a)
	 				{
	 					out.println(name+"\t"+"\t"+def);
	 					hasname =false;
	 					hasis_a =false;
	 					hasdef =false;
	 				}
	 			if(str.startsWith("[Term]")&&hasname && !hasdef && !hasis_a)
	 				{
	 					out.println(name);
	 					hasname =false;
	 					hasis_a =false;
	 					hasdef =false;
	 				}
	 			
	 			if(str.startsWith("name:")&& !hasname) //&& !name.equalsIgnoreCase(str.substring(5)))
	 				{
	 					name=str.substring(5);
	 					hasname=true;
	 				}
	 			if(str.startsWith("is_a:")&& !hasis_a)
	 				{
	 					is_a=str.substring(5);
	 					hasis_a=true;
	 				}
	 			if(str.startsWith("def:")&& !hasdef)
	 				{
	 					def=str.substring(4);
	 					hasdef=true;
	 				}
	 		}
		
	 		out.close();
	 		br.close();
	 		fr.close();
		} catch (Exception e) {
		System.out.println("读取txt文档内容时，发生异常！");
		e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
    	new OntologyToExcel();
    }
}	