
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Vector;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.ontology.*;

public class OntAnalysis  {
	
//    public static final String strDriver = "com.mysql.jdbc.Driver"; //path of driver class  
//    public static final String strURL = "jdbc:mysql://localhost/ontodb"; // URL of database  
//    public static final String strUser = "root"; //database user id  
//    public static final String strPassWord = "sql123"; //database password  
//    public static final String strDB =  "MySQL"; //database type  
	
	/* 函数功能说明
	 * 
	 * 创建本体模型，然后读取.owl文件，加载模型
	 * 
	 * */
	public OntModel createOnt(String owlpath) {

        OntModel ontmodel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);      
        try {
			ontmodel.read(new FileInputStream(owlpath), ""); // 读取文件，加载模型
		} catch (FileNotFoundException e) {
			System.out.println("读取失败" + e.getMessage());
		}
		return  ontmodel;
	}

		/*函数功能说明
		 * 
		 * 获取owl文件中所有的class,内容包括：
		 * 类URI,类名,类描述类型,类描述值
		 * 
		 */
		public void getAllClasses(String owlpath) {
			OntModel ontModel = this.createOnt(owlpath);
			String str;
			// 列出所有的class
		    
			for (Iterator allclass = ontModel.listClasses(); allclass.hasNext();) {
				
				OntClass ontclass = (OntClass) allclass.next();
				if(!ontclass.isAnon())
				{
				String classstr = ontclass.toString();
//				OntClassDTO dto = new OntClassDTO();;
//				System.out.println(dto.getC_URI());
				System.out.print("类URI：" + classstr + "   ");
				str = classstr.substring(classstr.indexOf("obo/"));
				System.out.print("类名：" + str + "   ");
				
				
				/**
				 * 父类
				
				if (!ontclass.listSuperClasses().hasNext()) {
								
					System.out.println("类描述类型：无");
				} else {
					for (Iterator supclasses = ontclass.listSuperClasses(); supclasses.hasNext();) {
						OntClass supclass = (OntClass) supclasses.next();
						String supclasstr = null;
						if(supclass.isRestriction())
							{
								supclasstr= supclass.asRestriction().getOnProperty().toString();
								supclasstr="OnProperty:"+supclasstr.substring(supclasstr.indexOf("obo/"));
								if(supclass.asRestriction().isSomeValuesFromRestriction())
								{
									String somevaluefrom= supclass.asRestriction().asSomeValuesFromRestriction().getSomeValuesFrom().toString();
									somevaluefrom= somevaluefrom.substring(somevaluefrom.indexOf("obo/"));
									supclasstr= supclasstr+ "; SomeValuesFrom:" + somevaluefrom;
								}
								str= supclasstr;
							}
						else 
							{
								supclasstr = supclass.toString();
								str = supclasstr.substring(supclasstr.indexOf("obo/"));
							}
					
						System.out.print("类描述类型：subClassOf   ");
						System.out.println("类描述值：" + str);
						
					}
				}
				 */
				
				for (Iterator it = ontclass.listSuperClasses(); it.hasNext();)

				{

				          OntClass sp = (OntClass) it.next();

				          String strx = ontclass.getModel().getGraph().getPrefixMapping().shortForm(ontclass.getURI())+ "'s superClass is " ; // 获取URI

				          String strSP = sp.getURI();

				          try{ // 另一种简化处理URI的方法

				                 strx = strx + ":" + strSP.substring(strSP.indexOf("obo/"));

				                 System.out.println("     Class" +strx);

				          }catch( Exception e ){}

				} // super class ends

				for(Iterator ipp = ontclass.listDeclaredProperties(); ipp.hasNext();){

			          OntProperty p = (OntProperty)ipp.next();
			          if(ontclass.hasProperty(p))
			        	  System.out.println("     associated property: " + p.getLocalName()+  "  "+ ontclass.getPropertyValue(p).toString());

			          }// property ends

				
				for(Iterator ipp = ontclass.listRDFTypes(true); ipp.hasNext();){

			          Resource p = (Resource)ipp.next();
			        	  System.out.println(p.toString());

			          }
//				 System.out.println(ontclass.listp.getLabel("label").toString());
//				if (ontclass.asDatatypeProperty().) 
//				{
//					for (Iterator types = ontclass.listRDFTypes(true); types.hasNext();) {
//						OntResource ontres= (OntResource) types.next();
//						System.out.println(ontres.getLabel(null));
//					}
//				}
				}
			}

		}

		/* 函数功能说明
		 * 
		 * 获取owl文件中所有的属性,内容包括：
		 * 属性URI,属性名,属性,定义域,值域
		 * 
		 * */
		public void getAllProperties(String owlpath) {
			OntModel ontMdel = this.createOnt(owlpath);
			String str;
//			OntProtyDAO dao = new OntProtyDAO();
			
			/** 列出所有的对象属性			**/
			if (!ontMdel.listObjectProperties().hasNext()) {
				
				System.out.println("属性：无");
			} else {
				
			for (Iterator allobjpry = ontMdel.listObjectProperties(); allobjpry
					.hasNext();) {

				OntProperty objpry = (OntProperty) allobjpry.next();
				
				// 属性URI
				String objprystr = objpry.toString();
				System.out.print("属性URI：" + objprystr + "  ");
				// 属性名
				str = objprystr.substring(objprystr.indexOf("#") + 1);
				System.out.print("属性值：" + str + " 属性：OP ");
				// 属性定义域
				String domain = objpry.getDomain().toString();
				String domainstr = domain.substring(domain.indexOf("#") + 1);
				System.out.print("定义域 ：" + domainstr);
				// 属性值域
				String range = objpry.getRange().toString();
				String rangestr = range.substring(range.indexOf("#") + 1);
				System.out.println("  值域：" + rangestr);

			}
			}
			

			// 列出所有的数据属性
			if (!ontMdel.listDatatypeProperties().hasNext()) {
				
				System.out.println("data属性：无");
			} else {
			for (Iterator alldatapry = ontMdel.listDatatypeProperties(); alldatapry
					.hasNext();) {

				OntProperty datapry = (OntProperty) alldatapry.next();
				// 属性URI
				String dataprystr = datapry.toString();
				System.out.print("属性URI：" + dataprystr + "  ");
				// 属性名
				str = dataprystr.substring(dataprystr.indexOf("#") + 1);
				System.out.print("属性值：" + str + " 属性：DP ");
				// 属性定义域
				String domain = datapry.getDomain().toString();
				String domainstr = domain.substring(domain.indexOf("#") + 1);
				System.out.print("定义域 ：" + domainstr);
				// 属性值域
				String range = datapry.getRange().toString();
				String rangestr = range.substring(range.indexOf("#") + 1);
				System.out.println("  值域：" + rangestr);
				System.out.println(dataprystr );
				System.out.println(str);
				System.out.println(domainstr);
				System.out.println(rangestr);
			}
			}
		}

		/* 函数功能说明
		 * 
		 * 列出所有的属性特征,内容包括：
		 * 属性URI、属性名、特征类型、特征值
		 * 
		 * */
		public void getAllProcharac(String owlpath) {
			OntModel ontMdel = this.createOnt(owlpath);
			String str;
			// 列出所有的对象属性
			for (Iterator allobjpry = ontMdel.listObjectProperties(); allobjpry.hasNext();) {

				String info = null;
				OntProperty objpry = (OntProperty) allobjpry.next();
				OntProperty objinverof = objpry.getInverseOf();
				
				if(objinverof!=null){
				info = "属性URI ："+objpry+"\n属  性  名  :"+objpry.toString().substring(objpry.toString().indexOf("#")+1)
						+"\n特征类型 :inverseOf  "+"\n特  征  值  ："+ objinverof.toString().substring(objinverof.toString().indexOf("#")+1);
				System.out.println(info);
				}
			}
			}
		
		
		/* 函数功能说明
		 * 
		 * 列出所有的实例,内容包括：
		 * 实例URI、实例名、类URI、属性URI、属性值
		 * 
		 * */
		public void getAllIndividuals(String owlpath) {
			OntModel ontModel = this.createOnt(owlpath);
			String str;

			// 迭代出本体文件中所有的实例
			for (Iterator allIndivs = ontModel.listIndividuals(); allIndivs.hasNext();) {
				Individual indiv = (Individual) allIndivs.next();
				//对象属性命名空间
				String namespace = indiv.toString().substring(0,indiv.toString().indexOf("#") + 1);
				// 实例所属的类
				OntClass classOfIndiv = indiv.getOntClass();
				
				//实例所属类的所有属性
				for (Iterator classPryOfIndivs = classOfIndiv.listProperties(); classPryOfIndivs.hasNext();) 
				{
					OntProperty classPryOfIndiv = (OntProperty) classPryOfIndivs.next();
					System.out.println(classPryOfIndivs.next());
					String classPryOfIndivstr = classPryOfIndiv.toString();
					String info = null;
					info = "实例URI:"
							+ indiv
							+ " 实例名："
							+ indiv.toString().substring(indiv.toString().indexOf("#") + 1) + "  实例所属类："
							+ classOfIndiv
							+" 属性URI："
							+classPryOfIndivstr;
					
					// 获取这个实例的属性值
					if (indiv.getPropertyValue(classPryOfIndiv) != null) {
						String pryValueOfIndiv = indiv.getPropertyValue(classPryOfIndiv).toString();
						/*判断对象属性或数据属性
						 * 如果实例属性值中包括"^^"和"#"，则为数据属性，否则为对象属性
						 */
						if (pryValueOfIndiv.contains("^^")&&pryValueOfIndiv.contains("#")) {
							info = info 
									+ "  属性值："
									+ pryValueOfIndiv.substring(0, pryValueOfIndiv.indexOf("^^"));
						}else{
							info = info
									+ "  属性值："
									+ pryValueOfIndiv.substring(pryValueOfIndiv.indexOf("#") + 1);
						}
					} else {
						info = info + "   无属性值";
					}
					//输出实例信息
					System.out.println(info);
				}
		
			}

		}

		public static void main(String[] args) {
			String owlpath = "C:\\Users\\Administrator\\Desktop\\mstest.owl";
			System.out.println("--------------------------------------------列表1    类------------------------------");
			new OntAnalysis().getAllClasses(owlpath);
//			System.out.println("--------------------------------------------列表2    属性-----------------------------");
//			new OntAnalysis().getAllProperties(owlpath);
//			System.out.println("--------------------------------------------列表3   属性特征--------------------------");
//			new OntAnalysis().getAllProcharac(owlpath);
//			System.out.println("--------------------------------------------列表4    属性约束--------------------------");
//			new OntAnalysis().getAllProcharac(owlpath);
//			System.out.println("--------------------------------------------列表5    实例------------------------------");
//			new OntAnalysis().getAllIndividuals(owlpath);
			
		}

	}