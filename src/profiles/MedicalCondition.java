package profiles;

/**
 * used only for holding and refactoring info on acceptable medical conditions.
 * @see UI.PatientForm
 * @author marco
 *
 */
public class MedicalCondition {
	public static String[][] MCList={
		{"Flu","http://www.nhs.uk/conditions/flu/Pages/Introduction.aspx"},
		{"Cough","http://www.nhs.uk/conditions/cough/pages/introduction.aspx"},
		{"Sunburn","http://www.nhs.uk/Conditions/sunburn/Pages/introduction.aspx"}
	};
	
	public static String[] createNames(){
		String[] names= new String[MCList.length];
		for(int i =0; i<MCList.length;i++){
			names[i]=MCList[i][0];
		}
		return names;
	}
	public static String createWebsites(String in){
		String out=null;
		for(int i=0;i<MCList.length;i++){
			if(in.equals(MCList[i][0])){
				out=MCList[i][1];
			}
		}
		
		return out;
	}
	
}
