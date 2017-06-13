
public class SplitClass {

	
	
	
	@Test
	public void signup(){
		//String text1 = "Actions|URL";
		String string = "004-034556";
		String[] parts = string.split("-");
		String part1 = parts[0]; // 004
		String part2 = parts[1]; 
		System.out.println(part1);
		
	}
}
