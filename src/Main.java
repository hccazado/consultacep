import com.byu.consultacep.exceptions.InvalidDataException;
import com.byu.consultacep.models.Cep;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    String cep;
    String baseCEPURL = "http://viacep.com.br/ws/";
    String resultFormat = "/json/";
    List<Cep> cepList = new LinkedList<>();
    int menu = 0;
    Scanner reader = new Scanner(System.in);

    System.out.println("Welcome Consulta(search) CEP");
    System.out.println("This app request details about your CEP (Brazilian zip code) from 'Via CEP' a free webservice.");

    while (menu != -1){
        System.out.println("---------Consulta CEP---------");
        System.out.println("1 - Inform a CEP");
        System.out.println("2 - Print your CEPs");
        System.out.println("99 - Exit");
        System.out.print("Select an Option: ");
        menu = reader.nextInt();

        switch(menu){
            case 1:
                System.out.print("Inform your CEP: ");
                cep = reader.next();
                String url = baseCEPURL+cep+resultFormat;
                String result = consulta(url);
                if(result){
                    cepList.add(result);
                }
                System.out.println(consulta(url));

            break;
            case 2:
                System.out.println("Menu "+menu);
            break;
            case 99:
                System.out.println("Thanks for using the app!");
                System.exit(0);
            break;
            default:
                System.out.println("Invalid option!");
            break;
        }
    }

}

static Cep buildCep (String ApiCep){
    Gson gson = new Gson();

}

static String consulta (String url){
    String result = "";
    int code = 0;
    try{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
        result = response.body();
        code = response.statusCode();
        if (code != 200){
            result = null;
            throw new InvalidDataException("Invalid CEP");
        }
    } catch(IllegalArgumentException e){
        System.out.println("An error has ocurred: "+ e.getMessage());
    } catch (InvalidDataException e) {
        System.out.println("An error has ocurred: "+e.getMessage());
    } catch(Exception e) {
        System.out.println("An error has ocurred: " + e.getMessage());
    }
    return result;
}
