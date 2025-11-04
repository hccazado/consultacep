import com.byu.consultacep.exceptions.InvalidDataException;
import com.byu.consultacep.models.Address;
import com.byu.consultacep.models.Cep;
import com.byu.consultacep.models.CepAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;

static void main() {
    String input;
    List<Cep> cepList = new LinkedList<>();
    List<Address> addressList = new LinkedList<>();
    int menu = 0;
    Scanner reader = new Scanner(System.in);

    System.out.println("Welcome Consulta(search) CEP");
    System.out.println("This app request details about your CEP (Brazilian zip code) from 'Via CEP' a free webservice.");

    while (menu != -1){
        System.out.println("---------Consulta CEP---------");
        System.out.println("1 - Inform a CEP");
        System.out.println("2 - Print your CEPs");
        System.out.println("3 - Inform an Address");
        System.out.println("4 - Print your Addresses");
        System.out.println("99 - Exit");
        System.out.print("Select an Option: ");
        menu = reader.nextInt();

        switch(menu){
            case 1:
                System.out.print("Inform your CEP: ");
                input = reader.next();
                Cep cep = searchCep(input);
                if(!cep.getCep().isEmpty()){
                    cepList.add(cep);
                    System.out.println("Added cep");
                }
                break;
            case 2:
                System.out.println(cepList);
                break;
            case 3:
                System.out.print("Inform your CEP: ");
                input = reader.next();
                System.out.print("Inform house number: ");
                int number = reader.nextInt();
                Address Address = new Address(input ,number);
                addressList.add(Address);
                System.out.println("Added address");
                break;
            case 4:
                System.out.println(addressList);
                break;
            case 99:
                writeFile(addressList, cepList);
                System.out.println("Thanks for using the app!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option!");
                break;
        }
    }
}

static void writeFile (List<Address> addressList, List<Cep> cepList){
    //writing the cep and addresses to the file addresses.txt
    try {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .create();
        FileWriter writer = new FileWriter("addresses.txt");
        writer.write(gson.toJson(cepList));
        writer.write(gson.toJson(addressList));
        writer.close();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

static String buildURL(String cep){
    //returning the complete url
    String baseCEPURL = "http://viacep.com.br/ws/";
    String resultFormat = "/json/";
    return baseCEPURL+cep+resultFormat;
}

static Cep newCep (String api){
    //instantiates a new CEP object from the data stored in the record CepAPI
    Gson gson = new Gson();
    CepAPI cepApi = gson.fromJson(api, CepAPI.class);
    return new Cep(cepApi);
}

static Cep searchCep (String cep){
    //request the user's cep resource from the webservice and return a cep object from the body string.
    String url = buildURL(cep);
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
    return newCep(result);
}
