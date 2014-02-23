
public class Merge {
	static int []m;
	static int []n;
	static int []m_n;
	private static void init(){
		m = new int[7];
		n = new int[5];
		for(int i =0;i<m.length;i++){
			m[i]=i;
		}
		for(int i =0;i<n.length;i++){
			n[i]=i;
		}
	}
	private static void merge(){
		int size =m.length+n.length;
		m_n = new int[size];
		
		for(int i=0,j=0,z=0;i<size;i++){
			if(j!=m.length-1 && z!=n.length-1){
				if(m[j]<n[z]){
					m_n[i]=m[j];
					j++;
				}
				else {
					m_n[i]=n[z];
					z++;
				}
			}
			else if(j==m.length-1){
				i--;
				for(int a=n.length-m.length;a<n.length;a++ ){
					m_n[i]=n[a];
					i++;
				}
				break;
			}
			else if(z==n.length-1){
				i--;
				for(int a=m.length-n.length;a<m.length;a++ ){
					m_n[i]=m[a];
					i++;
				}
				break;
			}
		}
	}
	public static void main(String[]args ){
		init();
		merge();
		for(int i =0;i<m_n.length;i++){
			System.out.println(m_n[i]);
		}
	}
}
