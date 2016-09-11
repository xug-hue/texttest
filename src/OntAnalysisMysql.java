
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Vector;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.ontology.*;

public class OntAnalysisMysql  {
	
//    public static final String strDriver = "com.mysql.jdbc.Driver"; //path of driver class  
//    public static final String strURL = "jdbc:mysql://localhost/ontodb"; // URL of database  
//    public static final String strUser = "root"; //database user id  
//    public static final String strPassWord = "sql123"; //database password  
//    public static final String strDB =  "MySQL"; //database type  
	
	/* ��������˵��
	 * 
	 * ��������ģ�ͣ�Ȼ���ȡ.owl�ļ�������ģ��
	 * 
	 * */
	public OntModel createOnt(String owlpath) {
/**        
		
	   // ����һ�����ݿ�����  
        IDBConnection conn = new DBConnection ( strURL, strUser, strPassWord, strDB ); 
       // �������ݿ������࣬��Ҫ�����쳣  
        try{  
      		Class.forName(strDriver);
        }catch(ClassNotFoundException e) {  
             e.printStackTrace();  
        }  
		//ʹ�����ݿ����Ӳ�������һ��ģ��������
        ModelMaker maker = ModelFactory.createModelRDBMaker(conn);
		//����һ��Ĭ��ģ��
        Model base = maker.createDefaultModel();
        OntModel ontmodel = ModelFactory.createOntologyModel(getModelSpec(maker), base);
**/
        OntModel ontmodel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);      
        try {
			ontmodel.read(new FileInputStream(owlpath), ""); // ��ȡ�ļ�������ģ��
		} catch (FileNotFoundException e) {
			System.out.println("��ȡʧ��" + e.getMessage());
		}
		return  ontmodel;
	}

/**	
	public static OntModelSpec getModelSpec(ModelMaker maker) {
	    
		 OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM);
	     spec.setImportModelMaker(maker);
	     
	     return spec;
	}
**/
		/*��������˵��
		 * 
		 * ��ȡowl�ļ������е�class,���ݰ�����
		 * ��URI,����,����������,������ֵ
		 * 
		 */
		public void getAllClasses(String owlpath) {
			OntModel ontModel = this.createOnt(owlpath);
			String str;
			// �г����е�class
		    
			for (Iterator allclass = ontModel.listClasses(); allclass.hasNext();) {
				
				OntClass ontclass = (OntClass) allclass.next();
				if(!ontclass.isAnon()){
				String classstr = ontclass.toString();
//				OntClassDTO dto = new OntClassDTO();;
//				System.out.println(dto.getC_URI());
				System.out.print("��URI��" + classstr + "   ");
				str = classstr.substring(classstr.indexOf("#") + 1);
				System.out.print("������" + str + "   ");
				if (!ontclass.listSuperClasses().hasNext()) {
								
					System.out.println("���������ͣ���");
				} else {
					for (Iterator supclasses = ontclass.listSuperClasses(); supclasses
							.hasNext();) {
						OntClass supclass = (OntClass) supclasses.next();
						String supclasstr = supclass.toString();
						str = supclasstr.substring(supclasstr.indexOf("#") + 1);
						System.out.print("���������ͣ�subClassOf   ");
						System.out.println("������ֵ��" + str);
						
						
					}
				}
				}
			}
		}

		/* ��������˵��
		 * 
		 * ��ȡowl�ļ������е�����,���ݰ�����
		 * ����URI,������,����,������,ֵ��
		 * 
		 * */
		public void getAllProperties(String owlpath) {
			OntModel ontMdel = this.createOnt(owlpath);
			String str;
//			OntProtyDAO dao = new OntProtyDAO();
			
			// �г����еĶ�������
			for (Iterator allobjpry = ontMdel.listObjectProperties(); allobjpry
					.hasNext();) {

				OntProperty objpry = (OntProperty) allobjpry.next();
				
				// ����URI
				String objprystr = objpry.toString();
				System.out.print("����URI��" + objprystr + "  ");
				// ������
				str = objprystr.substring(objprystr.indexOf("#") + 1);
				System.out.print("����ֵ��" + str + " ���ԣ�OP ");
				// ���Զ�����
				String domain = objpry.getDomain().toString();
				String domainstr = domain.substring(domain.indexOf("#") + 1);
				System.out.print("������ ��" + domainstr);
				// ����ֵ��
				String range = objpry.getRange().toString();
				String rangestr = range.substring(range.indexOf("#") + 1);
				System.out.println("  ֵ��" + rangestr);

			}
			// �г����е���������
			for (Iterator alldatapry = ontMdel.listDatatypeProperties(); alldatapry
					.hasNext();) {

				OntProperty datapry = (OntProperty) alldatapry.next();
				// ����URI
				String dataprystr = datapry.toString();
				System.out.print("����URI��" + dataprystr + "  ");
				// ������
				str = dataprystr.substring(dataprystr.indexOf("#") + 1);
				System.out.print("����ֵ��" + str + " ���ԣ�DP ");
				// ���Զ�����
				String domain = datapry.getDomain().toString();
				String domainstr = domain.substring(domain.indexOf("#") + 1);
				System.out.print("������ ��" + domainstr);
				// ����ֵ��
				String range = datapry.getRange().toString();
				String rangestr = range.substring(range.indexOf("#") + 1);
				System.out.println("  ֵ��" + rangestr);
				System.out.println(dataprystr );
				System.out.println(str);
				System.out.println(domainstr);
				System.out.println(rangestr);
			}
		}

		/* ��������˵��
		 * 
		 * �г����е���������,���ݰ�����
		 * ����URI�����������������͡�����ֵ
		 * 
		 * */
		public void getAllProcharac(String owlpath) {
			OntModel ontMdel = this.createOnt(owlpath);
			String str;
			// �г����еĶ�������
			for (Iterator allobjpry = ontMdel.listObjectProperties(); allobjpry.hasNext();) {

				String info = null;
				OntProperty objpry = (OntProperty) allobjpry.next();
				OntProperty objinverof = objpry.getInverseOf();
				
				if(objinverof!=null){
				info = "����URI ��"+objpry+"\n��  ��  ��  :"+objpry.toString().substring(objpry.toString().indexOf("#")+1)
						+"\n�������� :inverseOf  "+"\n��  ��  ֵ  ��"+ objinverof.toString().substring(objinverof.toString().indexOf("#")+1);
				System.out.println(info);
				}
			}
			}
		
		
		/* ��������˵��
		 * 
		 * �г����е�ʵ��,���ݰ�����
		 * ʵ��URI��ʵ��������URI������URI������ֵ
		 * 
		 * */
		public void getAllIndividuals(String owlpath) {
			OntModel ontModel = this.createOnt(owlpath);
			String str;

			// �����������ļ������е�ʵ��
			for (Iterator allIndivs = ontModel.listIndividuals(); allIndivs.hasNext();) {
				Individual indiv = (Individual) allIndivs.next();
				//�������������ռ�
				String namespace = indiv.toString().substring(0,indiv.toString().indexOf("#") + 1);
				// ʵ����������
				OntClass classOfIndiv = indiv.getOntClass();
				
				//ʵ�����������������
				for (Iterator classPryOfIndivs = classOfIndiv.listProperties(); classPryOfIndivs.hasNext();) 
				{
					OntProperty classPryOfIndiv = (OntProperty) classPryOfIndivs.next();
					System.out.println(classPryOfIndivs.next());
					String classPryOfIndivstr = classPryOfIndiv.toString();
					String info = null;
					info = "ʵ��URI:"
							+ indiv
							+ " ʵ������"
							+ indiv.toString().substring(indiv.toString().indexOf("#") + 1) + "  ʵ�������ࣺ"
							+ classOfIndiv
							+" ����URI��"
							+classPryOfIndivstr;
					
					// ��ȡ���ʵ��������ֵ
					if (indiv.getPropertyValue(classPryOfIndiv) != null) {
						String pryValueOfIndiv = indiv.getPropertyValue(classPryOfIndiv).toString();
						/*�ж϶������Ի���������
						 * ���ʵ������ֵ�а���"^^"��"#"����Ϊ�������ԣ�����Ϊ��������
						 */
						if (pryValueOfIndiv.contains("^^")&&pryValueOfIndiv.contains("#")) {
							info = info 
									+ "  ����ֵ��"
									+ pryValueOfIndiv.substring(0, pryValueOfIndiv.indexOf("^^"));
						}else{
							info = info
									+ "  ����ֵ��"
									+ pryValueOfIndiv.substring(pryValueOfIndiv.indexOf("#") + 1);
						}
					} else {
						info = info + "   ������ֵ";
					}
					//���ʵ����Ϣ
					System.out.println(info);
				}
		
			}

		}

		public static void main(String[] args) {
			String owlpath = "C:\\Users\\Administrator\\Desktop\\mstest.owl";
			System.out.println("--------------------------------------------�б�1    ��------------------------------");
			new OntAnalysisMysql().getAllClasses(owlpath);
//			System.out.println("--------------------------------------------�б�2    ����-----------------------------");
//			new OntAnalysisMysql().getAllProperties(owlpath);
//			System.out.println("--------------------------------------------�б�3   ��������--------------------------");
//			new OntAnalysisMysql().getAllProcharac(owlpath);
//			System.out.println("--------------------------------------------�б�4    ����Լ��--------------------------");
//			new OntAnalysisMysql().getAllProcharac(owlpath);
//			System.out.println("--------------------------------------------�б�5    ʵ��------------------------------");
//			new OntAnalysisMysql().getAllIndividuals(owlpath);
			
		}

	}