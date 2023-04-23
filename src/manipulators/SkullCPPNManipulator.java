package manipulators;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import picbreeder.ActivationFunctions;
import picbreeder.TWEANN;
import picbreeder.TWEANNGenotype;
import picbreeder.TWEANNGenotype.LinkGene;
import picbreeder.TWEANNGenotype.NodeGene;

public class SkullCPPNManipulator extends CPPNManipulator {
	public SkullCPPNManipulator() {
		super(loadSkull());
	}

	private static TWEANN loadSkull() {
		String fileName = "576_Skull.xml";
		File source = new File("images"+File.separator+fileName);
		TWEANN result = null;
		try {
			result = getSavedTWEANN(source);
		} catch (ParserConfigurationException e) {
			throw new IllegalStateException("loading Skull led to ParserConfigurationException");
		} catch (SAXException e) {
			throw new IllegalStateException("loading Skull led to SAXException");
		} catch (IOException e) {
			throw new IllegalStateException("loading Skull led to IOException");
		}
		return result;
	}
	
	
	/**
	 * Given a valid input File, this method will return the TWEANN based on
	 * the xml contents. Uses original Picbreeder XML representation.
	 * 
	 * @param inputFile File that corresponds with the path to the xml file
	 * @throws ParserConfigurationException xml file was not configured correctly
	 * @throws SAXException 
	 * @throws IOException File not found
	 */
	private static TWEANN getSavedTWEANN(File inputFile)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
		
        TWEANNGenotype tg = new TWEANNGenotype();
        
        int inputs = 0;
        
        NodeList nList = doc.getElementsByTagName("node");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            //System.out.println("\nCurrent Element :" + nNode.getNodeName() + ":" + nNode.getAttributes().getNamedItem("type").getNodeValue());
            String type = nNode.getAttributes().getNamedItem("type").getNodeValue();
            NodeList subList = nNode.getChildNodes();
            long innovation = Long.parseLong(subList.item(1).getAttributes().getNamedItem("id").getNodeValue());
            String activation = subList.item(3).getFirstChild().getNodeValue();
            //System.out.println(type + ":" + innovation + ":" + activation);
            if(type.equals("in")) {
            	NodeGene n = tg.nodes.get(inputs++);
            	n.innovation = innovation;
            	n.ftype = getFType(activation);
            } else if(type.equals("hidden")) {
            	NodeGene newGene = TWEANNGenotype.newNodeGene(getFType(activation), TWEANN.Node.NTYPE_HIDDEN, innovation);
            	// Adding the gene here may not be the right order
            	//System.out.println("Adding "+activation+":hidden:"+innovation+ " at "+tg.outputStartIndex());
            	tg.nodes.add(tg.outputStartIndex(), newGene);
            } else if(type.equals("out")) {
            	String label = nNode.getAttributes().getNamedItem("label").getNodeValue();
            	NodeGene n;
            	if(label.equals("ink") || label.equals("brightness")) {
            		n = tg.nodes.get(tg.nodes.size() - 1);
                } else if(label.equals("saturation")) {
            		n = tg.nodes.get(tg.nodes.size() - 2);
                } else { // hue
            		n = tg.nodes.get(tg.nodes.size() - 3);
                }
            	n.innovation = innovation;
            	n.ftype = getFType(activation);
            }
        }
                
        tg.links.clear();
        NodeList linkList = doc.getElementsByTagName("link");
        for (int temp = 0; temp < linkList.getLength(); temp++) {
            Node linkNode = linkList.item(temp);
            NodeList subList = linkNode.getChildNodes();
            long innovation = Long.parseLong(subList.item(1).getAttributes().getNamedItem("id").getNodeValue());
            long sourceInnovation = Long.parseLong(subList.item(3).getAttributes().getNamedItem("id").getNodeValue());
            long targetInnovation = Long.parseLong(subList.item(5).getAttributes().getNamedItem("id").getNodeValue());
            double weight = Double.parseDouble(subList.item(7).getFirstChild().getNodeValue());
            //System.out.println(innovation + ":" + sourceInnovation + ":" + targetInnovation + ":" + weight);
            LinkGene lg = TWEANNGenotype.newLinkGene(sourceInnovation, targetInnovation, weight, innovation, false);
            tg.links.add(lg);
        }

//        System.out.println("BEFORE");
//     //   System.out.println(tg.toString());
//        
//        for(LinkGene lg: tg.links) {
////        	System.out.println("Source = " + lg.sourceInnovation);
////        	System.out.println("Target = " + lg.targetInnovation);
//        }
               
//        System.out.println(tg.toGraphViz(PicbreederTask.staticSensorLabels(), PicbreederTask.staticOutputLabels()));
        
        // Get nodes in right order according to the links
        // deleting specific nodes??? 
        TWEANNGenotype.sortNodeGenesByLinkConnectivity(tg);
       
        //moveInputToEnd(tg);
        
//        System.out.println("AFTER");
      //  System.out.println(tg.toString());
        
//        System.out.println("-------------------------------------------------");
        
//        System.out.println(tg.toGraphViz(PicbreederTask.staticSensorLabels(), PicbreederTask.staticOutputLabels()));
        
//        for(LinkGene lg: tg.links) {
////        	System.out.println("Source = " + lg.sourceInnovation);
////        	System.out.println("Target = " + lg.targetInnovation);
//        }
        
//        DrawingPanel panel = new DrawingPanel(800, 800, "Network");
		TWEANN network = tg.getPhenotype();
//		network.draw(panel, true, false);
//		
//        // Now show the image
//		//BufferedImage image = GraphicsUtil.imageFromCPPN(network, SIZE, SIZE);
//		double scale = 1.0;
//		BufferedImage image = GraphicsUtil.imageFromCPPN(network, SIZE, SIZE, ArrayUtil.doubleOnes(network.numInputs()), -1, scale, 0, 0, 0);
//		DrawingPanel picture = GraphicsUtil.drawImage(image, "Image", SIZE, SIZE);
//		// Wait for user
//		String result = MiscUtil.waitForReadStringAndEnterKeyPress();
//		if(!result.trim().equals("")) {
//			// Save the image
//			GraphicsUtil.saveImage(image, result.trim());
//		}
//		picture.dispose();
//		panel.dispose();
		
		return network;
	}
	
	/**
	 * Moves all output nodes to the end of the list
	 * @param t Neural network that represents a CPPN
	 */
	public static void moveInputToEnd(TWEANNGenotype t) {
		int numberOfNodesMoved = 0;
		for(int i = 0; i < t.nodes.size() - numberOfNodesMoved; i++) { // not sure about the num of nodes moved...
			if(t.nodes.get(i).ntype == TWEANN.Node.NTYPE_OUTPUT) { // if t is an output node, move to the end
				NodeGene removed = t.nodes.remove(i); // removes the one in that position
				t.nodes.add(removed); // adds this to the end of the list
				numberOfNodesMoved++; // increment counter
			}
		}
    }
	
	/**
	 * Given a string with a valid activation function name, return the
	 * activation function that corresponds with the string. If it is invalid,
	 * it will throw an IllegalArgumentException.
	 * 
	 * @param name String that contains the valid 
	 * @return Activation function that corresponds with the String name
	 * @throws IllegalArgumentException if the String does not contain valid
	 * 				activation function
	 */
	public static int getFType(String name) {
		switch(name) {
		case "identity(x)":
			return ActivationFunctions.FTYPE_ID;
		case "gaussian(x)":
			return ActivationFunctions.FTYPE_FULLGAUSS;
		case "sin(x)":
			return ActivationFunctions.FTYPE_SINE;
		case "cos(x)":
			return ActivationFunctions.FTYPE_COS;
		case "sigmoid(x)":
			return ActivationFunctions.FTYPE_FULLSIGMOID;
		default:
			throw new IllegalArgumentException("Invalid activation function: " + name);
		}
	}
}
