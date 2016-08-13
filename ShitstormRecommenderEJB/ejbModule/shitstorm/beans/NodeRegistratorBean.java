package shitstorm.beans;

import javax.ejb.Stateless;

import kip.tools.InfluenceDiagramNetwork;

@Stateless
public class NodeRegistratorBean {
	
	// Register the nodes by diagramName and the path
	public String registerNodesForInfluenceDiagram(String refProcess, String pathToInfluenceDiagram) {
		InfluenceDiagramNetwork net = new InfluenceDiagramNetwork();
		
//		File file = new File("shitstorm.txt");
		// //Sfile.getParentFile().mkdirs();
		// try {
		// file.createNewFile();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// boolean exists = file.exists();
		// net.readFile("shitstorm.xdsl");
		// net.updateBeliefs();
		// String nodeName = "ZA_Smtb_1";
		// double x = net.getNodeValue(nodeName)[0];
		// System.out.println(nodeName + " with value " + x);
		return "registration successfull";
	}

}
