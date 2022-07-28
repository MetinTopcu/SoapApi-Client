import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SoapWebServiceClient {

    //This method sends a SOAP request and prints response, and parses and prints texts of all <balance> elements in the response
    public void postSOAPXML() {
        String resp = null;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sch=\"http://www.apinizer.com/xml/school\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <sch:StudentDetailsRequest>\n" +
                    "         <sch:name>Apinizer</sch:name>\n" +
                    "      </sch:StudentDetailsRequest>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            HttpClient httpclient = new DefaultHttpClient();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://localhost:9090/service/student-details");
            post.setHeader("SOAPAction", "GetData");
            post.setEntity(strEntity);

            // Execute request
            HttpResponse response = httpclient.execute(post);
            HttpEntity respEntity = response.getEntity();

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);

                //prints whole response+
                System.out.println("Succesful Client Process "+resp);

                //Regular expression to parse texts of <balance> elements in the response assuming they have no child elements
                //Matcher m = Pattern.compile("<balance>([^<]*)").matcher(resp); //groups all characters except < (tag closing character)
                //while (m.find()) {
                    //prints texts of all balances in a loop
                  //  System.out.println(m.group(1));
                //}
            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e.toString());
        }
    }

    public static void main(String[] args) {
        SoapWebServiceClient soapWebServiceClientObject = new SoapWebServiceClient();
        soapWebServiceClientObject.postSOAPXML();
    }
}