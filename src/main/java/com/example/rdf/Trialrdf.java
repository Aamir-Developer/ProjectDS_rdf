//import com.hp.hpl.jena.graph.Triple;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import org.apache.jena.riot.RDFDataMgr;
////import org.apache.jena.riot.RDFDataMgr;
//import org.apache.jena.riot.lang.PipedRDFIterator;
//import org.apache.jena.riot.lang.PipedRDFStream;
//import org.apache.jena.riot.lang.PipedTriplesStream;
// 
///**
// *
// * @author bonn
// */
//public class Trialrdf {
// 
//    /**
//     * @param args the command line arguments
//     * @throws java.io.FileNotFoundException
//     */
//  
//    public static void main(String[] args) throws FileNotFoundException   {
//        
//  
//        final String filename = "E:\\yagoTransitiveType.ttl";
//        System.out.println(filename);
//        
//          System.out.println("Hello1");
//          PipedRDFIterator<Triple> iter = new PipedRDFIterator<>();
//           final PipedRDFStream<Triple> inputStream = new PipedTriplesStream(iter);
//         // PipedRDFStream and PipedRDFIterator need to be on different threads
//         ExecutorService executor = Executors.newSingleThreadExecutor();
// //         Create a runnable for our parser thread
//        Runnable parser;
//        parser = new Runnable() {
//           
//            @Override
//            public void run() {
//        System.out.println(filename);
////                 Call the parsing process.
//                RDFDataMgr.parse(inputStream, filename);
//                
//  
//            }
//        }; 	
////         Start the parser on another thread
//        executor.submit(parser);
// 
//        
//        while (iter.hasNext()) {
//            Triple next = iter.next();
//            System.out.println("Subject:  "+next.getSubject());
//            System.out.println("Object:  "+next.getObject());
//            System.out.println("Predicate:  "+next.getPredicate());
//            System.out.println("\n");
//        }
